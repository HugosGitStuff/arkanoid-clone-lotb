package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
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

public class GamePage implements Runnable, Page {
    private Ball ball;
    private Paddle paddle;
    private Grid newGrid;
    private Blocks blocks;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;
    private ScoreSaver scoreSaver;
    private MyMouse myMouse;
    private boolean gameOver;
    private PageState state;
    private IntroPage intro;

    private boolean paused = false;

    private BreakPage breakPage;

    private int score;
    private int level;
    private int numBlocksRemoved = 0;

    @Override
    public void init() {
        try {
        newGrid = new Grid(8, 12);
        newGrid.init();

        ball = new Ball(425, 600, 3, 3);

        Picture background = new Picture(10, 10, "gameBackground/background-final.png");
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

            screenAddon.countDown();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        run();

    }

    @Override
    public void run() {



        int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // Needed to check FPS
        int drawCount = 0;// Needed to check FPS
        boolean levelCleared = false;

        while (!levelCleared && state == PageState.RUNNING) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;


            if (delta >= 1){

                if (state == PageState.QUIT) {
                    System.exit(0);
                }

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

                if (level == 1 && numBlocksRemoved == 32) {
                    clear();

                } else if (level == 2 && numBlocksRemoved == 26) {
                    clear();

                } else if (level == 3 && numBlocksRemoved == 30) {
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
                    gameOver = true;
                    break;
                }
            }
        }

        while (state == PageState.PAUSE) {
            try {
                myMouse.hideButton("pause");
                myMouse.drawButton("CONTINUE", 950, 400);
                setPaused(true);

                Thread.sleep(50);

                if(!paused) {

                    myMouse.drawButton("PAUSE", 950, 400);
                    myMouse.hideButton("continue");
                    setState(PageState.RUNNING);
                    run();
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void clear() {
        numBlocksRemoved = 0;
        blocks.clear();
        ball.delete();
        level++;
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

    public void setState(PageState state) {
        this.state = state;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("PAUSE");
        myMouse.createButton("QUIT");
        myMouse.createButton("CONTINUE");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("pause", 950, 400);
        myMouse.drawButton("quit", 950, 450);
    }

    //@Override
    public void hideButtons() {
        myMouse.hideButton("quit");
        if (state.equals(PageState.PAUSE)) {
            myMouse.hideButton("continue");
        } else if (state.equals(PageState.RUNNING)) {
            myMouse.hideButton("pause");
        }
    }
}
