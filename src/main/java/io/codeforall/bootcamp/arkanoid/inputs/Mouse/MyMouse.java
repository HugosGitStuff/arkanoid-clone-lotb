package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseEventType;
import com.codeforall.simplegraphics.mouse.MouseHandler;
import io.codeforall.bootcamp.arkanoid.pages.AbstractPage;
import io.codeforall.bootcamp.arkanoid.pages.Page;
import io.codeforall.bootcamp.arkanoid.pages.PageState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyMouse implements MouseHandler {
    private Page page;
    private Map<String, Button> buttons;

    public MyMouse() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        buttons = new HashMap<>();
    }

    public void init() {
        createButton("START");
        createButton("CONTINUE");
        createButton("RESTART");
        createButton("QUIT");
        createButton("PAUSE");
        createButton("SCORES");
    }

    public void reset() {
        Set<String> buttonsKeySet = buttons.keySet();
        for (String key : buttonsKeySet) {
            buttons.remove(key);
        }
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();

        if (isInside("start", x, y)) {
            page.setState(PageState.START);
        } else if (isInside("continue", x, y)) {
            page.setState(PageState.CONTINUE);
        } else if (isInside("restart", x, y)) {
            page.setState(PageState.RESTART);
        } else if (isInside("quit", x, y)) {
            page.setState(PageState.QUIT);
        } else if (isInside("pause", x, y)) {
            page.setState(PageState.PAUSE);
        } else if (isInside("scores", x, y)) {
            page.setState(PageState.SCORES);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();


    }

    public boolean isInside(String message, int x, int y) {
        Button button = buttons.get(message.toUpperCase());
        boolean result = x >= button.getX() && x <= button.getX() + button.getWidth() &&
                y >= button.getY() && y <= button.getY() + button.getHeight();
        System.out.println(result);
        System.out.println(x);
        System.out.println(y);
        return result;
    }

    public void createButton(String message) {
        buttons.put(message, new Button(message));
    }

    public void drawButton(String message, int x, int y) {
        buttons.get(message.toUpperCase()).init(x, y);
        buttons.get(message.toUpperCase()).draw();
    }

    public void hideButton(String message) {
        buttons.get(message.toUpperCase()).hide();
    }
}
