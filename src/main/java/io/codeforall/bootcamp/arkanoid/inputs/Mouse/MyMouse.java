package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseEventType;
import com.codeforall.simplegraphics.mouse.MouseHandler;
import io.codeforall.bootcamp.arkanoid.pages.GamePage;
import io.codeforall.bootcamp.arkanoid.pages.Page;
import io.codeforall.bootcamp.arkanoid.pages.PageState;


import java.util.*;
import java.util.List;

public class MyMouse implements MouseHandler {
    private Page page;
    private Map<String, Button> buttons;
    private GamePage gamePage;

    public MyMouse() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        buttons = new HashMap<>();
    }

    public void init() {
        createButton("start");
        createButton("continue");
        createButton("restart");
        createButton("quit");
        createButton("pause");
        createButton("scores");
    }

    public void reset() {
        List<String> itemsToRemove = new ArrayList<>(buttons.keySet());
        for (String key : itemsToRemove) {
            buttons.remove(key);
        }
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setGamePage(GamePage gamePage) {
        this.gamePage = gamePage;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY() - 30;

        if (isInside("start", x, y)) {
            page.setState(PageState.START);
        }else if (isInside("continue", x, y)) {
            page.setState(PageState.START);
        }else if (isInside("restart", x, y)) {
            page.setState(PageState.RESTART);
        }else if (isInside("quit", x, y)) {
            page.setState(PageState.QUIT);
        }else if (isInside("pause", x, y)) {
            page.setState(PageState.PAUSE);
        }else if (isInside("scores", x, y)) {
            page.setState(PageState.SCORES);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();


    }

    public boolean isInside(String message, int x, int y) {
        Button button = buttons.get(message);
        if (button == null) {
            return false;
        }
        return x >= button.getX() && x <= button.getX() + button.getWidth() &&
                y >= button.getY() && y <= button.getY() + button.getHeight();
    }

    public void createButton(String message) {
        buttons.put(message.split(" ")[0], new Button(message.toUpperCase()));
    }

    public void drawButton(String message, int x, int y) {
        buttons.get(message).init(x, y);
        buttons.get(message).draw();
    }

    public void hideButton(String message) {
        buttons.get(message).hide();
    }

    public boolean isDrawn(String button) {
        return buttons.get(button).isDrawn();
    }
}
