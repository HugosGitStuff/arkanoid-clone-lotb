package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;

import java.io.IOException;

public class BreakPage extends AbstractPage{
    private GamePage gamePage;

    private Picture background;
    private int score = 0;
    private int level = 0;
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        gamePage.setLevel(level);
        gamePage.setScore(score);

        clear();
    }

    public void clear() {
        gamePage.init();
        background.delete();
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

    @Override
    public void drawButtons() {

    }

    @Override
    public void hideButtons() {

    }
}
