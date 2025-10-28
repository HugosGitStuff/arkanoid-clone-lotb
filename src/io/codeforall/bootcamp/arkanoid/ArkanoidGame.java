package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.pictures.Picture;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ArkanoidGame {
    private static int score;
    public static void main(String[] args) throws InterruptedException {

        MyKeyboard myKeyboard = new MyKeyboard();
        myKeyboard.init();

        score = 0;
        int level = 1;
        int FPS = 60;
        boolean gameOver = false;
        int numBlocksRemoved = 0;
        Blocks blocks;
        Clip wallHitClip;

        IntroPage intro = new IntroPage();

//--------------------------------------------------------------------------
        //começa aqui o intro do texto

        Picture textIntro = new Picture(10, 10, "resources/text/textIntro.png");
        intro.delete();
        textIntro.draw();
        Thread.sleep(1000);

        Picture background = new Picture(10, 10, "resources/gameBackground/background-final.png");
        textIntro.delete();
        background.draw();


        Paddle paddle = new Paddle(425, 725);
        paddle.draw();
        myKeyboard.setPaddle(paddle);


        Ball ball = new Ball(425, 600, 3, 3);

        Grid newGrid = new Grid(8, 12);
        newGrid.init();

        ScreenAdditions screenText = new ScreenAdditions(level, score);
        screenText.initialText();
        while (!gameOver) {
            ball.draw();
            blocks = new Blocks(newGrid, ball, level);
            try {
                screenText.countDown();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }

            double drawInterval = 1000000000 / FPS;
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
                                    screenText.setScoreValue(score);

                                    if (blocks.removeBlock(i, j)) {
                                        score += block.getMaxHealth() * 100;
                                        screenText.setScoreValue(score);
                                        numBlocksRemoved++;
                                    }

                                } else if (wasLeft || wasRight) {
                                    ball.bounce(wasLeft ? "left" : "right");

                                    score += 30;
                                    screenText.setScoreValue(score);

                                    if (blocks.removeBlock(i, j)) {
                                        score += block.getMaxHealth() * 100;
                                        screenText.setScoreValue(score);
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
                    level++;
                    numBlocksRemoved = 0;
                    blocks.clear();
                    ball.delete();
                    Picture firstLevelPic = new Picture(10, 10, "resources/text/congratsFirstLevel.png");
                    firstLevelPic.draw();
                    Thread.sleep(2500);
                    ball = new Ball(425, 600, 3, 3);
                    screenText.setLevNum(level);
                    firstLevelPic.delete();
                    levelCleared = true;
                } else if (level == 2 && numBlocksRemoved == 26) {
                    level++;
                    numBlocksRemoved = 0;
                    blocks.clear();
                    ball.delete();
                    Picture secondLevelPic = new Picture(10, 10, "resources/text/congratsSecondLevel.png");
                    secondLevelPic.draw();
                    Thread.sleep(2500);
                    ball = new Ball(425, 600, 3, 3);
                    screenText.setLevNum(level);
                    secondLevelPic.delete();
                    levelCleared = true;
                } else if (level == 3 && numBlocksRemoved == 30) {
                    Picture endPic = new Picture(10, 10, "resources/text/finalGame.png");
                    endPic.draw();

                    screenText.finalScore(score);

                    Thread.sleep(20000);
                    System.exit(0);
                }
                // level 1 - 32 blocks
                // level 2  - 26 blocks
                // level 3  - 30 blocks

                if (ball.collidesWith(paddle)) {
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("resources/sfx/ball-hit-paddle.WAV"));
                        wallHitClip = AudioSystem.getClip();
                        wallHitClip.open(audioStream);
                        wallHitClip.start();
                        ball.paddleBounce(paddle);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        System.out.println("Error playing music: " + e.getMessage());
                    }

                    score += 50;
                    screenText.setScoreValue(score);
                }
                if (ball.checkWallCollision(newGrid, newGrid.getWidth(), newGrid.getHeight(), ball.getWidth(), ball.getHeight()) != null) {
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("resources/sfx/ball_wallhit.WAV"));
                        wallHitClip = AudioSystem.getClip();
                        wallHitClip.open(audioStream);
                        wallHitClip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        System.out.println("Error playing music: " + e.getMessage());
                    }
                    String collision = ball.checkWallCollision(newGrid, newGrid.getWidth(), newGrid.getHeight(), ball.getWidth(), ball.getHeight());
                    ball.bounce(collision);
                    score += 10;
                    screenText.setScoreValue(score);
                }

                if (ball.getY() >= 770) {
                    screenText.gameOverText();
                    Thread.sleep(2000);
                    System.exit(0);
                }
            }
            }
        }
    }
}


