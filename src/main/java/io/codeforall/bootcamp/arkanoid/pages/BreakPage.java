package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;

public class BreakPage implements Page {
    private final String[] picturePaths = new String[]{
            "text/textIntro.png",
            "text/congratsFirstLevel.png",
            "text/congratsSecondLevel.png",
            "text/finalGame.png"};
    private MyMouse myMouse;
    private GamePage gamePage;
    private PageState state;
    private Picture background;
    private int score = 0;
    private int level = 0;
    private IntroPage intro;

    @Override
    public void init() {
        background = new Picture(10, 10, picturePaths[level]);
        background.draw();
        if (level == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            level++;
            gamePage.setLevel(level);
            gamePage.setScore(score);

            clear();
        } else {
            createMouseButtons();
            drawButtons();

            while (state != PageState.QUIT) {
                switch (state) {
                    case START:
                        clear();
                        break;
                    case SCORES:
                        System.out.println("Coming soon...");
                        break;
                    case RESTART:
                        myMouse.setPage(intro);
                        background.delete();
                        intro.init();
                        break;
                    case IDLE:
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                }
            }

            System.exit(0);
        }
    }

    @Override
    public void clear() {
        myMouse.reset();
        myMouse.setPage(gamePage);
        gamePage.setState(PageState.RUNNING);
        gamePage.init();
        background.delete();
        //hideButtons();
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

    public void setMyMouse(MyMouse myMouse) {
        this.myMouse = myMouse;
    }

    public void setState(PageState state) {
        this.state = state;
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("START");
        myMouse.createButton("SCORE");
        myMouse.createButton("RESTART");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("start", 950, 750);
        myMouse.drawButton("score", 750, 750);
        myMouse.drawButton("restart", 550, 750);
    }

    //@Override
    public void hideButtons() {
        myMouse.hideButton("start");
        myMouse.hideButton("score");
        myMouse.hideButton("restart");
    }
}
