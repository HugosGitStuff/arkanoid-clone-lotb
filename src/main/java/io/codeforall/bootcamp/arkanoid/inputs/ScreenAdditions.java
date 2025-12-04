package io.codeforall.bootcamp.arkanoid.inputs;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreenAdditions {
    private String level;
    private Text levNum;
    private Text score;
    private List<Text> positions = new ArrayList<>();


    public void initialText() {
        levNum = new Text(980, 190, level);
        score = new Text(975, 310,"");

        levNum.setColor(Color.YELLOW);
        levNum.grow(15, 20);
        score.setColor(Color.YELLOW);
        score.grow(20, 20);

        levNum.draw();
        score.draw();
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

    public void finalScore(int score) {
        Text finalScore = new Text(550, 120, "FINAL SCORE");
        finalScore.grow(50, 20);
        finalScore.setColor(Color.YELLOW);

        Text endScore = new Text(570, 170, score + " pts");
        endScore.grow(50, 20);
        endScore.setColor(Color.YELLOW);

        finalScore.draw();
        endScore.draw();

    }

    public void gameOverText() {
        Text gameIsOver = new Text(425, 400, "GAME OVER");
        gameIsOver.grow(200, 30);
        gameIsOver.setColor(Color.YELLOW);
        gameIsOver.draw();

    }

    public void scoreboard(int x, int y, ArrayList<String[]> savedScores) {
        if (savedScores.isEmpty()) {

        } else {
                int scoreNum = 1;
            for (String[] score : savedScores) {
                Text position = new Text(x - 20, y + 50, scoreNum + ". " + Arrays.stream(score).reduce("", (acc, elem) -> acc.concat(elem + "   ")));
                position.setColor(Color.YELLOW);
                position.grow(150, 8);
                position.draw();
                positions.add(position);
                scoreNum ++;
                y += 50;

            }
        }
    }

    public void deleteScoreboardSign() {
        for(Text position : positions) {
            position.delete();
        }
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setScore(int score) {
        if (this.score != null) {
            this.score.setText("" + score);
        }
    }

    public void deleteLevelAndScore() {
        levNum.delete();
        score.delete();
    }
}
