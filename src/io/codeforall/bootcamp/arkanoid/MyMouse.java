package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseEventType;
import com.codeforall.simplegraphics.mouse.MouseHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MyMouse implements MouseHandler {
    private Map<String, Button> buttons;

    public MyMouse() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        buttons = new HashMap<>();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();


    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();



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


        public Button(int x, int y, String message) {
            int width = 100;
            int height = 30;
            button = new Rectangle(x, y, width, height);
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

    }

}
