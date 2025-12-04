package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Map;

public class Button {
    private final int width = 150;
    private final int height = 31;
    private Rectangle button;
    private Picture hoverPic;
    private String message;
    private int x;
    private int y;
    private boolean drawn;
    private Picture pic;
    private final Map<String, String> buttonImg = Map.of(
            "st", "/buttons/buttons/start.png",
            "p", "/buttons/buttons/pause.png",
            "scr", "/buttons/buttons/scores.png",
            "rest", "/buttons/buttons/restart.png",
            "q", "/buttons/buttons/quit.png",
            "cont", "/buttons/buttons/continue.png"
    );

    private final Map<String, String> buttonHoverImg = Map.of(
            "st", "/buttons/hoverButtons/start.png",
            "p", "/buttons/hoverButtons/pause.png",
            "scr", "/buttons/hoverButtons/scores.png",
            "rest", "/buttons/hoverButtons/restart.png",
            "q", "/buttons/hoverButtons/quit.png",
            "cont", "/buttons/hoverButtons/continue.png"
    );

    public Button(String message) {
        this.message = message;
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        button = new Rectangle(this.x, this.y, width, height);

        pic = new Picture(x, y, buttonImg.get(message));
        hoverPic = new Picture(x, y, buttonHoverImg.get(message));

        button.setColor(Color.LIGHT_GRAY);
    }


    public void hide() {
        drawn = false;
        button.delete();
        pic.delete();
        x = 0;
        y = 0;
    }

    public void draw() {
        drawn = true;
        pic.draw();
    }

    public void hoverDraw() {
        hoverPic.draw();
    }

    public void hoverDelete() {
        hoverPic.delete();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDrawn() {
        return drawn;
    }
}

