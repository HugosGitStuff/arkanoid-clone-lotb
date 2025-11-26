package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import java.io.IOException;

public class ScorePage implements Page {
    private final Rectangle background = new Rectangle(200, 100, 700, 600);
    private MyMouse mouse;
    private PageState state;
    private ScoreSaver scoreSaver;
    private ScreenAdditions screenAddon;
    private Page page;


    @Override
    public void init() {
        try {
            if (page instanceof IntroPage) {
                background.translate(-160,0);
            }
            background.setColor(Color.RED);
            background.fill();
            createButtons();
            drawButtons();
            screenAddon.scoreboard(background.getX() + 350, background.getY() + 25, scoreSaver.getSavedScores("src/main/resources/score/score.txt"));
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
        background.delete();
        hideButtons();
        screenAddon.deleteScoreboardSign();
        mouse.setPage(page);
        page.drawButtons();
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
        mouse.drawButton("cont", background.getX() + 530, 650);
        mouse.drawButton("q", background.getX() + 80, 650);
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
