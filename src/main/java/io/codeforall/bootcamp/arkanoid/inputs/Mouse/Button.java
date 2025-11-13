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
    private boolean drawn;

    public Button(String message) {
        this.message = message;
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        button = new Rectangle(this.x, this.y, width, height);

        button.setColor(Color.LIGHT_GRAY);

        text = new Text(this.x, this.y, message);
        text.grow(10, 10);
        text.translate(centeredTextX(), 8);
        text.setColor(Color.YELLOW);

    }

       public int centeredTextX() {
        return (button.getWidth()/2 - text.getWidth()/2) + 17;
    }

    public void hide() {
        if (button != null) {
            drawn = false;
            button.delete();
            text.delete();
        }
    }

    public void draw() {
        drawn = true;
        text.draw();
        button.draw();
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

