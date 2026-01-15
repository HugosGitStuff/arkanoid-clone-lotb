package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class IntroPage implements Page {
    private MyMouse myMouse;
    private BreakPage breakPage;
    private ScorePage scorePage;
    private ScoreSaver scoreSaver;
    private ScreenAdditions screenAddons;

    private Picture background;

    private PageState state;

    @Override
    public void init() {
        background = new Picture(10, 10, "introPage/background.png");
        background.draw();

        createButtons();
        drawButtons();

        run();
    }

    @Override
    public void run() {
        while(state != PageState.QUIT) {
            switch (state) {
                case START:
                    clear();
                    break;
                case SCORES:
                    hideButtons();
                    scorePage.setScreenAddons(screenAddons);
                    scorePage.setScoreSaver(scoreSaver);
                    myMouse.setPage(scorePage);
                    scorePage.setPage(this);
                    scorePage.init();
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

    @Override
    public void clear() {
        hideButtons();
        myMouse.setPage(breakPage);
        breakPage.setState(PageState.IDLE);
        breakPage.setLevel(0);
        breakPage.setScore(0);
        background.delete();
        breakPage.init();
    }

    public void setBreakPage(BreakPage breakPage) {
        this.breakPage = breakPage;
    }

    public void setMyMouse(MyMouse myMouse) {
        this.myMouse = myMouse;
    }

    public void setState(PageState state) {
        this.state = state;
    }

    @Override
    public void setScreenAddons(ScreenAdditions screenAddons) {
        this.screenAddons = screenAddons;
    }

    @Override
    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    }

    public void setScorePage(ScorePage scorePage) {
        this.scorePage = scorePage;
    }

    @Override
    public void createButtons() {
        myMouse.createButton("st");
        myMouse.createButton("scr");
        myMouse.createButton("q");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("st", background.getX() + 898, background.getY() + 627);
        myMouse.drawButton("scr", background.getX() + 898, background.getY() + 667);
        myMouse.drawButton("q", background.getX() + 898, background.getY() + 707);
    }

    @Override
    public void hideButtons() {
        myMouse.hideButton("st");
        myMouse.hideButton("scr");
        myMouse.hideButton("q");
    }
}
