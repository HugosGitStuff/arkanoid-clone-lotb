package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ScreenAdditions {
    Text levNum;
    Text scoreValue;
    Clip countDownSound;

    public ScreenAdditions(int level, int score){
        levNum = new Text(970, 220, "" + level);
        scoreValue = new Text(965, 350, "" + score);

    }

    public void initialText(){
        Text lev = new Text(970, 160, "Level ");
        Text highScore = new Text(960, 300, "SCORE");

        lev.setColor(Color.YELLOW);
        lev.grow(30, 20);
        levNum.setColor(Color.YELLOW);
        levNum.grow(20, 20);
        highScore.setColor(Color.YELLOW);
        highScore.grow(30, 20);
        scoreValue.setColor(Color.YELLOW);
        scoreValue.grow(30, 20);

        lev.draw();
        levNum.draw();
        highScore.draw();
        scoreValue.draw();
    }

    public void countDown() throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        Text countDown = new Text(425, 400, "3");
        countDown.grow(60, 60);
        countDown.setColor(Color.YELLOW);
        countDown.draw();
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("resources/sfx/countdown-2.WAV"));
        countDownSound = AudioSystem.getClip();
        countDownSound.open(audioStream);
        countDownSound.start();
        Thread.sleep(1000);
        AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(new File("resources/sfx/countdown-2.WAV"));
        countDownSound = AudioSystem.getClip();
        countDownSound.open(audioStream1);
        countDownSound.start();
        countDown.setText("2");
        Thread.sleep(1000);
        countDown.setText("1");
        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(new File("resources/sfx/countdown-2.WAV"));
        countDownSound = AudioSystem.getClip();
        countDownSound.open(audioStream2);
        countDownSound.start();
        Thread.sleep(1000);
        AudioInputStream audioStream3 = AudioSystem.getAudioInputStream(new File("resources/sfx/countdown-final.WAV"));
        countDownSound = AudioSystem.getClip();
        countDownSound.open(audioStream3);
        countDownSound.start();
        countDown.delete();
    }

    public void finalScore(int score){
        Text finalScore = new Text(550, 120, "FINAL SCORE");
        finalScore.grow(50, 20);
        finalScore.setColor(Color.YELLOW);

        Text endScore = new Text(570, 170, score + " pts");
        endScore.grow(50, 20);
        endScore.setColor(Color.YELLOW);

        Text pressToExit = new Text(450, 770, "Press 2 to exit the game. Thank you for playing!");
        pressToExit.grow(200, 20);
        pressToExit.setColor(Color.YELLOW);

        finalScore.draw();
        endScore.draw();
        pressToExit.draw();
    }

    public void gameOverText(){
        Text gameIsOver = new Text(425, 400, "GAME OVER");
        gameIsOver.grow(200, 30);
        gameIsOver.setColor(Color.YELLOW);
        gameIsOver.draw();

    }

    public void setLevNum(int level){
        levNum.setText("" + level);
    }

    public void setScoreValue(int score) {
        scoreValue.setText("" + score);
    }
}
