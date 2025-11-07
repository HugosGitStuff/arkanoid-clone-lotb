package io.codeforall.bootcamp.arkanoid.inputs;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseEventType;
import com.codeforall.simplegraphics.mouse.MouseHandler;

import java.util.HashMap;
import java.util.Map;

public class MyMouse implements MouseHandler {
    private final Map<String, Button> buttons;

    public MyMouse() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        buttons = new HashMap<>();
        createButton(600, 750, "START");
        createButton(600, 750, "CONTINUE");
        createButton(600, 750, "RESTART");
        createButton(600, 750, "QUIT");
        createButton(600, 750, "PAUSE");
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

       if (isInside(buttons.get("start"), x, y)) {

       } else if (isInside(buttons.get("continue"), x, y)) {

       } else if (isInside(buttons.get("restart"), x, y)) {

       } else if (isInside(buttons.get("quit"), x, y)) {

       }


    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();



    }

    public boolean isInside(Button button, int x, int y) {
        return x >= button.getX() && x <= button.getX() + button.getWidth() &&
                y >= button.getY() && y <= button.getY() + button.getHeight();
    }

    public void createButton(int x, int y, String message) {
        buttons.put(message.toLowerCase(), new Button(x, y, message));
    }

    public void drawButton(String message) {
        buttons.get(message).draw();
    }

    public void hideButton(String message) {
        buttons.get(message).hide();
    }

    private class Button {
        private final Rectangle button;
        private final Text text;
        private int width = 100;
        private int height = 30;
        private int x;
        private int y;


        public Button(int x, int y, String message) {
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

}
