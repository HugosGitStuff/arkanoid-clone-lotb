package io.codeforall.bootcamp.arkanoid.inputs;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ScreenAdditions {
    Text levNum;
    Text scoreValue;

    public void initialText() {
        Text lev = new Text(970, 160, "Level ");
        levNum = new Text(970, 220, "1");
        Text highScore = new Text(960, 300, "SCORE");
        scoreValue = new Text(965, 350,"");

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
        Text countDown = new Text(450, 490, "3");
        countDown.grow(60, 60);
        countDown.setColor(Color.YELLOW);
        countDown.draw();
        runAudio("/sfx/countdown-1.WAV");
        Thread.sleep(1000);
        runAudio("/sfx/countdown-1.WAV");
        countDown.setText("2");
        Thread.sleep(1000);
        countDown.setText("1");
        runAudio("/sfx/countdown-1.WAV");
        Thread.sleep(1000);
        runAudio("/sfx/countdown-final.WAV");
        countDown.delete();
    }

    public void runAudio(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try {
            URL file = getClass().getResource(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file.openStream()));

            Clip audio = AudioSystem.getClip();
            audio.open(audioStream);
            audio.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }

    }

    public void pressToExit() {
        Text pressToExit = new Text(450, 770, "Press 2 to exit the game. Thank you for playing!");
        pressToExit.grow(200, 20);
        pressToExit.setColor(Color.YELLOW);

        pressToExit.draw();
    }

    public void finalScore(int score) {
        Text finalScore = new Text(550, 120, "FINAL SCORE");
        finalScore.grow(50, 20);
        finalScore.setColor(Color.YELLOW);

        Text endScore = new Text(570, 170, score + " pts");
        endScore.grow(50, 20);
        endScore.setColor(Color.YELLOW);

        pressToExit();

        finalScore.draw();
        endScore.draw();

    }

    public void gameOverText() {
        Text gameIsOver = new Text(425, 400, "GAME OVER");
        gameIsOver.grow(200, 30);
        gameIsOver.setColor(Color.YELLOW);
        gameIsOver.draw();

    }

    public void scoreboard(ArrayList<String[]> savedScores) {
        int x = 400;
        int y = 550;
        Text scoreboardSign = new Text(600, 500, "SCOREBOARD");
        scoreboardSign.grow(20, 20);
        scoreboardSign.setColor(Color.YELLOW);
        scoreboardSign.draw();
        if (savedScores.isEmpty()) {
            System.out.println();
        } else {
            for (String[] score : savedScores) {
                Text positions = new Text(x, y, Arrays.stream(score).reduce("", (acc, elem) -> acc.concat(elem + "                    ")));
                positions.setColor(Color.YELLOW);
                positions.grow(150, 5);
                positions.draw();
                y += 15;

            }
        }
    }

    public void setLevel(int level) {
        levNum.setText("" + level);
    }

    public void setScore(int score) {
        scoreValue.setText( "" + score);
    }
}
