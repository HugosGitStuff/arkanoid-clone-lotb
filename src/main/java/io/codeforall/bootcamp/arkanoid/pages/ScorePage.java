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


    @Override
    public void init() {
        background.setColor(Color.RED);
        background.fill();
        createMouseButtons();
        drawButtons();
        setState(PageState.START);

        while (state != PageState.QUIT) {
            switch (state) {

                case START:
                    try {
                        screenAddon.scoreboard(background.getX() + 300, background.getY() + 25, scoreSaver.getSavedScores("/score/score.txt"));
                        setState(PageState.IDLE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case IDLE:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;

            }
            clear();
        }
    }

    @Override
    public void clear() {
        background.delete();
    }

    @Override
    public void createMouseButtons() {
        mouse.createButton("continue /space");
        mouse.createButton("quit /q");
    }

    @Override
    public void drawButtons() {
        mouse.drawButton("continue", 730, 650);
        mouse.drawButton("quit", 280, 650);
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

    public void setScreenAddon(ScreenAdditions screenAddon) {
        this.screenAddon = screenAddon;
    }
}
