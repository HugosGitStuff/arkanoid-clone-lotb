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

public class GamePage implements Page {
    private final Picture background = new Picture(10, 10, "gameBackground/background-final.png");
    private final int[] numBlocksArray = {32,26,30};// {32,26,30}
    private Ball ball;
    private Paddle paddle;
    private Grid Grid;
    private Blocks blocks;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;
    private MyMouse myMouse;
    private IntroPage intro;
    private BreakPage breakPage;
    private boolean gameOver;
    private int score;
    private int level;
    private double delta = 0;
    private int numBlocks;
    volatile private PageState state;


    @Override
    public void init() {
        try {
            Grid = new Grid(8, 12);
            Grid.init();

            ball = new Ball(465, 700, 3, -3);

            background.draw();

            paddle = new Paddle(425, 725, Grid);
            myKeyboard.setPaddle(paddle);

            blocks = new Blocks();
            numBlocks = numBlocksArray[level-1];

            createMouseButtons();
            paddle.draw();
            ball.draw();
            blocks.init(Grid, level);
            drawButtons();
            screenAddon.initialText();
            if (level != 1){
                screenAddon.setScore(score);
            }
            gameOver = false;

            screenAddon.countDown();
        } catch (InterruptedException | UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        run();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = 0; // Needed to check FPS
        int drawCount = 0;// Needed to check FPS
        while (!gameOver) {
            if (state == PageState.IDLE || state == PageState.START) {
                lastTime = System.nanoTime();
            }

            int FPS = 60;
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

                                        ball.directionAfterCollision(block);

                                        score += 30;
                                        screenAddon.setScore(score);

                                        if (blocks.removeBlock(i, j)) {
                                            score += block.getMaxHealth() * 100;
                                            screenAddon.setScore(score);
                                            numBlocks--;
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
                        if (numBlocks == 0) {
                            try {
                                Thread.sleep(500);
                                clear();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

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

                        if (ball.collidesWithWall(Grid)) {
                            try {

                                screenAddon.runAudio("/sfx/ball_wallhit.WAV");
                                score += 2;
                                screenAddon.setScore(score);

                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                                System.out.println("Error playing music: " + e.getMessage());
                            }
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
        delta = 0;
        blocks.clear();
        ball.delete();
        paddle.delete();
        background.delete();
        myMouse.hideButton("pause");
        myMouse.hideButton("quit");
        myMouse.reset();

        screenAddon.deleteLevelAndScore();
        myMouse.setPage(breakPage);
        breakPage.setLevel(level);
        breakPage.setScore(score);
        breakPage.init();
    }

    public void restart() {
        if(myMouse.isDrawn("pause")) {
            myMouse.hideButton("pause");
        }
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

    public void setMyKeyboard(MyKeyboard myKeyboard) {
        this.myKeyboard = myKeyboard;
    }

    @Override
    public void setScreenAddons(ScreenAdditions screenAddon) {
        this.screenAddon = screenAddon;
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

    public void setState(PageState state) {
        this.state = state;
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("pause /p");
        myMouse.createButton("quit /q");
        myMouse.createButton("continue /space");
        myMouse.createButton("restart /r");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("pause", 940, 400);
        myMouse.drawButton("quit", 940, 450);
    }

    @Override
    public void hideButtons() {
        myMouse.hideButton("continue");
    }

    @Override
    public void setScoreSaver(ScoreSaver scoreSaver) {}
}
