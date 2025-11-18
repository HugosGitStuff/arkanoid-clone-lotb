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
    private int score;
    private int level;
    private IntroPage intro;

    @Override
    public void init() {
        background = new Picture(10, 10, picturePaths[level]);
        background.draw();
        if (level == 0) {
            try {
                level++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            clear();
        } else {
            createMouseButtons();
            drawButtons();

            while (state != PageState.QUIT) {
                switch (state) {
                    case START:
                        hideButtons();
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
        gamePage.setLevel(level);
        gamePage.setScore(score);
        myMouse.reset();
        myMouse.setPage(gamePage);
        background.delete();
        gamePage.setState(PageState.RUNNING);
        gamePage.init();
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
        myMouse.createButton("start");
        myMouse.createButton("scores");
        myMouse.createButton("restart");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("start", 950, 750);
        myMouse.drawButton("scores", 750, 750);
        myMouse.drawButton("restart", 550, 750);
    }

    //@Override
    public void hideButtons() {
        myMouse.hideButton("start");
        myMouse.hideButton("scores");
        myMouse.hideButton("restart");
    }
}
