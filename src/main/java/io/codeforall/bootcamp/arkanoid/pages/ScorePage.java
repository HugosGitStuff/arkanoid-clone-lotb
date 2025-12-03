package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import java.io.IOException;

public class ScorePage implements Page {
    private final Picture background = new Picture(250, 10, "/score/FinaScores.png");
    private MyMouse mouse;
    private PageState state;
    private ScoreSaver scoreSaver;
    private ScreenAdditions screenAddon;
    private Page page;


    @Override
    public void init() {
        try {
            if (page instanceof IntroPage) {
                background.translate(-170,0);
            }
            background.draw();
            createButtons();
            drawButtons();
            screenAddon.scoreboard(background.getX() + 280, background.getY() + 180, scoreSaver.getSavedScores("src/main/resources/score/score.txt"));
            setState(PageState.PAUSE);
            run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (state != PageState.IDLE) {
            switch (state) {
                case START:
                    clear();
                    break;
                case QUIT:
                    System.exit(0);
                case PAUSE:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    @Override
    public void clear() {
        if (page instanceof IntroPage) {
            background.translate(170,0);
        }
        background.delete();
        hideButtons();
        screenAddon.deleteScoreboardSign();
        scoreSaver.clearList();
        page.drawButtons();
        mouse.setPage(page);
        page.setScoreSaver(scoreSaver);
        page.setScreenAddons(screenAddon);
        page.setState(PageState.IDLE);
        page.run();
    }

    @Override
    public void createButtons() {
        mouse.createButton("cont");
        mouse.createButton("q");
    }

    @Override
    public void hideButtons() {
        mouse.hideButton("cont");
        mouse.hideButton("q");
    }

    @Override
    public void drawButtons() {
        mouse.drawButton("cont", background.getX() + 350, 820);
        mouse.drawButton("q", background.getX() + 120, 820);
    }

    @Override
    public void setState(PageState pageState) {
        this.state = pageState;
    }

    public void setMouse(MyMouse mouse) {
        this.mouse = mouse;
    }

    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    }

    @Override
    public void setScreenAddons(ScreenAdditions screenAddon) {
        this.screenAddon = screenAddon;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
