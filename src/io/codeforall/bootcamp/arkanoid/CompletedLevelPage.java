package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.pictures.Picture;

public class CompletedLevelPage {
    private Picture background;
    private int score;
    private final String[] picturePaths = new String[] {
            "src/resources/text/congratsFirstLevel.png",
            "src/resources/text/congratsSecondLevel.png",
            "src/resources/text/finalGame.png"};

    public void executeLevel(int level) {
        background = new Picture(5, 5, picturePaths[level - 1]);
        background.draw();
    }

    public void clear() {
        background.delete();
    }

    public void setScore(int score) {
        this.score = score;
    }
}
