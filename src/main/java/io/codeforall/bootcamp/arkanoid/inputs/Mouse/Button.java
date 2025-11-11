package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;

public class Button {
    private Rectangle button;
    private Text text;
    private final int width = 100;
    private final int height = 30;
    private int x;
    private int y;
    private String message;

    public Button(String message) {
        this.message = message;
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        button = new Rectangle(this.x, this.y, width, height);
        button.setColor(Color.LIGHT_GRAY);

        text = new Text(x, y, message);
        text.grow(10, 10);
        text.setColor(Color.YELLOW);
    }

    public void hide() {
        button.delete();
        text.delete();
    }

    public void draw() {
        button.draw();
        text.draw();
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
}

