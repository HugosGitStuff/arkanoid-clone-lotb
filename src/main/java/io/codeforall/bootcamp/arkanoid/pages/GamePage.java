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
import io.codeforall.bootcamp.arkanoid.objects.powerups.PowerUp;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePage implements Page {
    private final Picture background = new Picture(10, 10, "gamePage/background.png");
    private final int[] numBlocksArray = {32,1,1,1,1,1,1,1,1,1,1,1,1};// {32,26,30,31,26,32,32,32,33,32,33,33,33}
    private double delta = 0;
    private boolean gameOver;
    private int level;
    private int score;
    private int numBlocks;

    private ArrayList<Ball> balls;
    private Paddle paddle;
    private Grid Grid;
    private Blocks blocks;
    private ArrayList<PowerUp> powerUps;

    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;
    private MyMouse myMouse;
    volatile private PageState state;

    private IntroPage intro;
    private BreakPage breakPage;


    @Override
    public void init() {
        try {
            Grid = new Grid(8, 12);
            balls = new ArrayList<>();
            paddle = new Paddle(425, 725, Grid);
            blocks = new Blocks();
            powerUps = new ArrayList<>();
            balls.add(new Ball(465, 700, 3, -3));

            Grid.init();
            background.draw();
            myKeyboard.setPaddle(paddle);
            numBlocks = numBlocksArray[level-1];
            createButtons();
            paddle.draw();
            balls.get(0).draw();
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
                    myMouse.hideButton("cont");
                    myMouse.drawButton("p", 908, 637);
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
                        for (Ball ball1 : balls) {
                            ball1.update();
                        }

                        for (int i = 0; i < blocks.getBlockMatrix().length; i++) {
                            for (int j = 0; j < blocks.getBlockMatrix()[i].length; j++) {
                                Block block = blocks.getBlockMatrix()[i][j];
                                if (block != null) {
                                    for (Ball ball : balls) {
                                        if (ball.collidesWith(block)) {

                                            ball.directionAfterCollision(block);

                                            score += 30;
                                            screenAddon.setScore(score);

                                            if (blocks.removeBlock(i, j)) {
                                                PowerUp powerUp = new PowerUp(block.getX() + block.getWidth()/2,
                                                        block.getY() + block.getHeight()/2,
                                                        (int) (Math.random() * 5));
                                                powerUp.setBalls(balls);
                                                powerUp.setPaddle(paddle);
                                                powerUps.add(powerUp);
                                                score += block.getMaxHealth() * 100;
                                                screenAddon.setScore(score);
                                                numBlocks--;
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
                        if (numBlocks <= 0) {
                            try {
                                Thread.sleep(500);
                                clear();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        for (int i = 0; i < balls.size(); i++) {
                            Ball ball = balls.get(i);
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
                                ball.delete();
                                balls.remove(ball);
                            }
                        }

                        if (!powerUps.isEmpty()) {
                            for (int i =0; i < powerUps.size(); i++) {
                                PowerUp powerUp = powerUps.get(i);
                                powerUp.update();
                                if (powerUp.collidesWith(paddle)) {
                                    powerUp.execute();
                                    powerUps.remove(powerUp);
                                }
                                if (powerUp.getY() >= 770) {
                                    powerUp.delete();
                                    powerUps.remove(powerUp);
                                }
                            }
                        }

                        if (balls.isEmpty()) {
                            myMouse.setGameOver(true);
                            screenAddon.gameOverText();
                            setState(PageState.IDLE);
                        }
                    }
                    break;

                case PAUSE:
                    myMouse.hideButton("p");
                    myMouse.drawButton("cont", 908, 637);
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
        balls = null;
        powerUps = null;
        paddle.delete();
        background.delete();
        myMouse.hideButton("p");
        myMouse.hideButton("rest");
        myMouse.hideButton("q");
        if (level == numBlocksArray.length) {
            breakPage.setFinalLevel(true);
        }
        screenAddon.deleteLevelAndScore();
        myMouse.setPage(breakPage);
        breakPage.setLevel(level);
        breakPage.setScore(score);
        breakPage.init();
    }

    public void restart() {
        if(myMouse.isDrawn("p")) {
            myMouse.hideButton("p");
        }
        myMouse.hideButton("rest");
        myMouse.hideButton("q");
        balls = null;
        powerUps = null;
        paddle.delete();
        background.delete();
        blocks.clear();
        screenAddon.deleteLevelAndScore();
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
    public void createButtons() {
        myMouse.createButton("p");
        myMouse.createButton("q");
        myMouse.createButton("cont");
        myMouse.createButton("rest");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("p", background.getX() + 898, background.getY() + 627);
        myMouse.drawButton("rest", background.getX() + 898, background.getY() + 667);
        myMouse.drawButton("q", background.getX() + 898, background.getY() + 707);
    }

    @Override
    public void hideButtons() {
        myMouse.hideButton("cont");
    }

    @Override
    public void setScoreSaver(ScoreSaver scoreSaver) {}
}
