package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.Bootstrap;
import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
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

public class GamePage {
    private Bootstrap bootstrap;
    private Ball ball;
    private Paddle paddle;
    private Grid newGrid;
    private Blocks blocks;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;
    private ScoreSaver scoreSaver;
    private boolean gameOver;

    private Picture background;

    private int score = 0;
    private int level = 1;
    private int numBlocksRemoved = 0;


    private CompletedLevelPage completedLevelPage;

    public void init() throws InterruptedException, IOException {




        newGrid = new Grid(8, 12);
        newGrid.init();

        paddle = new Paddle(425, 725, newGrid);
        paddle.draw();
        myKeyboard.setPaddle(paddle);


        ball = new Ball(425, 600, 3, 3);

        Picture background = new Picture(10, 10, "/gameBackground/background-final.png");

        background.draw();

        ball.draw();
        blocks = new Blocks(newGrid, level);
        try {
            screenAddon.countDown();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // Needed to check FPS
        int drawCount = 0;// Needed to check FPS
        boolean levelCleared = false;

        while (!levelCleared) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1){
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
                                    screenAddon.setScoreValue(score);

                                    if (blocks.removeBlock(i, j)) {
                                        score += block.getMaxHealth() * 100;
                                        screenAddon.setScoreValue(score);
                                        numBlocksRemoved++;
                                    }

                                } else if (wasLeft || wasRight) {
                                    ball.bounce(wasLeft ? "left" : "right");

                                    score += 30;
                                    screenAddon.setScoreValue(score);

                                    if (blocks.removeBlock(i, j)) {
                                        score += block.getMaxHealth() * 100;
                                        screenAddon.setScoreValue(score);
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

                if (timer >= 1000000000) {
                    System.out.println("FPS: " + drawCount); // Show FPS on console
                    drawCount = 0;
                    timer = 0;
                }

                if (level == 1 && numBlocksRemoved == 32) {
                    levelCleared = levelCleared();

                } else if (level == 2 && numBlocksRemoved == 26) {
                    levelCleared = levelCleared();

                } else if (level == 3 && numBlocksRemoved == 30) {
                    completedLevelPage.executeLevel(level);

                    screenAddon.finalScore(score);
                    // screenAddon.scoreboard(scoreSaver.getSavedScores("/score/score.txt"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    scoreSaver.saveToFile("/resources/score/score.txt", scoreSaver.updateScores(LocalDate.now().format(formatter), "first game", score));

                    Thread.sleep(20000);
                    System.exit(0);
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
                    screenAddon.setScoreValue(score);
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
                    screenAddon.setScoreValue(score);
                }

                if (ball.getY() >= 770) {
                    gameOver = true;
                    break;
                }
            }
        }
    }
    public boolean levelCleared() throws InterruptedException {
        numBlocksRemoved = 0;
        blocks.clear();
        ball.delete();

        completedLevelPage = new CompletedLevelPage();
        completedLevelPage.executeLevel(level);

        Thread.sleep(2000);

        completedLevelPage.clear();
        ball = new Ball(425, 600, 3, 3);
        screenAddon.setLevNum(level);
        level++;
        return true;
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
}
