package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.Bootstrap;
import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.objects.ball.Ball;
import io.codeforall.bootcamp.arkanoid.objects.blocks.Block;
import io.codeforall.bootcamp.arkanoid.objects.blocks.Blocks;
import io.codeforall.bootcamp.arkanoid.objects.grid.Grid;
import io.codeforall.bootcamp.arkanoid.objects.paddle.Paddle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GamePage implements Page {
    private final int FPS = 60;
    private final Picture background = new Picture(10, 10, "gameBackground/background-final.png");
    private Ball ball;
    private Paddle paddle;
    private Grid newGrid;
    private Blocks blocks;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;
    private ScoreSaver scoreSaver;
    private MyMouse myMouse;
    private boolean gameOver;
    volatile private PageState state;
    private IntroPage intro;
    private BreakPage breakPage;
    private int score;
    private int level;
    private int numBlocksRemoved = 0;
    private double delta = 0;


    @Override
    public void init() {
        try {
            newGrid = new Grid(8, 12);
            newGrid.init();

            ball = new Ball(425, 600, 3, 3);

            background.draw();

            paddle = new Paddle(425, 725, newGrid);
            myKeyboard.setPaddle(paddle);

            blocks = new Blocks();

            createMouseButtons();
            paddle.draw();
            ball.draw();
            blocks.init(newGrid, level);
            drawButtons();
            screenAddon.initialText();
            gameOver = false;

            screenAddon.countDown();
        } catch (InterruptedException | UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        run();
    }

    public void run() {
        long lastTime = System.nanoTime();
        long timer = 0; // Needed to check FPS
        int drawCount = 0;// Needed to check FPS
        while (!gameOver) {
            if (state == PageState.IDLE || state == PageState.START) {
                lastTime = System.nanoTime();
            }

            switch (state) {
                case START:
                    myMouse.hideButton("continue");
                    myMouse.drawButton("pause", 940, 400);
                    setState(PageState.RUNNING);
                    break;


                case RUNNING:
                    long currentTime = System.nanoTime();

                    double drawInterval = (double) 1000000000 / FPS;
                    delta += (currentTime - lastTime) / drawInterval;

                    timer += (currentTime - lastTime);

                    lastTime = currentTime;


                    if (delta >= 1) {

                        paddle.update();
                        ball.update();

                        for (int i = 0; i < blocks.getBlockMatrix().length; i++) {
                            for (int j = 0; j < blocks.getBlockMatrix()[i].length; j++) {
                                Block block = blocks.getBlockMatrix()[i][j];
                                if (block != null) {
                                    if (ball.collidesWith(block)) {

                                        boolean wasAbove = ball.prevBallY() + ball.getHeight() <= block.getY();
                                        boolean wasBelow = ball.prevBallY() >= block.getY() + block.getHeight();
                                        boolean wasLeft = ball.prevBallX() + ball.getWidth() <= block.getX();
                                        boolean wasRight = ball.prevBallX() >= block.getX() + block.getWidth();

                                        if (wasAbove || wasBelow) {
                                            ball.bounce(wasAbove ? "top" : "bottom");
                                            score += 30;
                                            screenAddon.setScore(score);

                                            if (blocks.removeBlock(i, j)) {
                                                score += block.getMaxHealth() * 100;
                                                screenAddon.setScore(score);
                                                numBlocksRemoved++;
                                            }

                                        } else if (wasLeft || wasRight) {
                                            ball.bounce(wasLeft ? "left" : "right");

                                            score += 30;
                                            screenAddon.setScore(score);

                                            if (blocks.removeBlock(i, j)) {
                                                score += block.getMaxHealth() * 100;
                                                screenAddon.setScore(score);
                                                numBlocksRemoved++;
                                            }

                                        } else {
                                            if (Math.abs(ball.getVelocityY()) > Math.abs(ball.getVelocityX())) {
                                                ball.bounce(ball.getVelocityY() > 0 ? "top" : "bottom");
                                            } else {
                                                ball.bounce(ball.getVelocityX() > 0 ? "left" : "right");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        delta--;
                        drawCount++;

//                if (timer >= 1000000000) {
//                    System.out.println("FPS: " + drawCount); // Show FPS on console
//                    drawCount = 0;
//                    timer = 0;
//                }

                        if (level == 1 && numBlocksRemoved == 2) {
                            clear();

                        } else if (level == 2 && numBlocksRemoved == 4) {
                            clear();

                        } else if (level == 3 && numBlocksRemoved == 3) {
                            breakPage.setLevel(level);
                            try {
                                breakPage.init();

                                screenAddon.finalScore(score);
                                // screenAddon.scoreboard(scoreSaver.getSavedScores("/score/score.txt"));
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                scoreSaver.saveToFile("/resources/score/score.txt", scoreSaver.updateScores(LocalDate.now().format(formatter), "first game", score));

                                Thread.sleep(20000);
                                System.exit(0);
                            } catch (IOException | InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        // level 1 - 32 blocks
                        // level 2  - 26 blocks
                        // level 3  - 30 blocks

                        if (ball.collidesWith(paddle)) {
                            try {
                                screenAddon.runAudio("/sfx/ball-hit-paddle.WAV");
                                ball.paddleBounce(paddle);
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                System.out.println("Error playing music: " + e.getMessage());
                            }

                            score += 20;
                            screenAddon.setScore(score);
                        }

                        if (ball.checkWallCollision(newGrid) != null) {
                            try {
                                screenAddon.runAudio("/sfx/ball_wallhit.WAV");
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                System.out.println("Error playing music: " + e.getMessage());
                            }
                            String collision = ball.checkWallCollision(newGrid);
                            ball.bounce(collision);
                            score += 2;
                            screenAddon.setScore(score);
                        }

                        if (ball.getY() >= 770) {
                            myMouse.hideButton("pause");
                            myMouse.drawButton("restart", 940, 400);
                            screenAddon.gameOverText();
                            setState(PageState.IDLE);
                        }
                    }
                    break;

                case PAUSE:
                    myMouse.hideButton("pause");
                    myMouse.drawButton("continue", 940, 400);
                    setState(PageState.IDLE);
                    break;

                case IDLE:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case RESTART:
                    restart();
                    break;

                case QUIT:
                    System.exit(0);
            }
        }
    }

    @Override
    public void clear() {
        numBlocksRemoved = 0;
        delta = 0;
        blocks.clear();
        ball.delete();
        paddle.delete();
        level++;

        screenAddon.deleteLevelAndScore();
        screenAddon.setLevel(level);
        myMouse.setPage(breakPage);
        breakPage.setLevel(level);
        breakPage.setScore(score);
        breakPage.init();
    }

    public void setMyKeyboard(MyKeyboard myKeyboard) {
        this.myKeyboard = myKeyboard;
    }

    public void setScreenAddon(ScreenAdditions screenAddon) {
        this.screenAddon = screenAddon;
    }

    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    }

    public void setMyMouse(MyMouse myMouse) {
        this.myMouse = myMouse;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setIntro(IntroPage intro) {
        this.intro = intro;
    }

    public void setBreakPage(BreakPage breakPage) {
        this.breakPage = breakPage;
    }

    public void restart() {
        myMouse.hideButton("restart");
        myMouse.hideButton("quit");
        ball.delete();
        paddle.delete();
        background.delete();
        blocks.clear();
        screenAddon.deleteLevelAndScore();
        myMouse.reset();
        gameOver = false;

        myMouse.setPage(intro);
        intro.setState(PageState.IDLE);
        intro.init();
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("pause");
        myMouse.createButton("quit");
        myMouse.createButton("continue");
        myMouse.createButton("restart");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("pause", 940, 400);
        myMouse.drawButton("quit", 940, 450);
    }

    //@Override
    public void hideButtons() {
        myMouse.hideButton("continue");
    }

    public PageState getState() {
        return state;
    }

    public void setState(PageState state) {
        this.state = state;
    }
}
