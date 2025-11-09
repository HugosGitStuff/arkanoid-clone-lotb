package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;

import java.io.IOException;

public class BreakPage {
    private GamePage gamePage;

    private Picture background;
    private int score;
    private int level;
    private final String[] picturePaths = new String[] {
            "text/textIntro.png",
            "text/congratsFirstLevel.png",
            "text/congratsSecondLevel.png",
            "text/finalGame.png"};

    public void init() throws IOException, InterruptedException {
        background = new Picture(10, 10, picturePaths[level]);
        background.draw();

        if (level == 0) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            clear();
        }
    }

    public void clear() throws IOException, InterruptedException {
        background.delete();
        gamePage.init();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGamePage(GamePage gamePage) {
        this.gamePage = gamePage;
    }
}
