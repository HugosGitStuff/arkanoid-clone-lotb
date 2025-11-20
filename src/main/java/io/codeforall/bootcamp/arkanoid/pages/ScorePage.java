package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.graphics.Rectangle;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;

public class ScorePage implements Page{
    private final Rectangle background = new Rectangle(300, 200, 600, 800);
    private MyMouse mouse;
    private PageState state;


    @Override
    public void init() {
        background.draw();

        switch (state) {
            case IDLE:
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case RESTART:
                clear();
                break;
            case QUIT:
                System.exit(0);
                break;
        }

    }

    @Override
    public void clear() {
        background.delete();

    }

    @Override
    public void createMouseButtons() {
        mouse.createButton("continue");
        mouse.createButton("restart");
        mouse.createButton("quit");
    }

    @Override
    public void drawButtons() {
        mouse.drawButton("continue /space", 850, 750);
        mouse.drawButton("restart /r", 650, 750);
        mouse.drawButton("quit /q", 450, 750);
    }

    @Override
    public void setState(PageState pageState) {
        this.state = pageState;
    }

    public void setMouse(MyMouse mouse) {
        this.mouse = mouse;
    }
}
