package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.mouse.Mouse;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseEventType;
import com.codeforall.simplegraphics.mouse.MouseHandler;
import io.codeforall.bootcamp.arkanoid.pages.Page;
import io.codeforall.bootcamp.arkanoid.pages.PageState;


import java.util.*;

public class MyMouse implements MouseHandler {
    private Page page;
    private Map<String, Button> buttons;
    private boolean gameOver;
    private boolean hoverDrawn = false;
    private String hoverButtonMsg;

    public MyMouse() {
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
        buttons = new HashMap<>();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY() - 30;

        if (isInside("st", x, y)) {
            page.setState(PageState.START);
        } else if (isInside("cont", x, y)) {
            page.setState(PageState.START);
        } else if (isInside("rest", x, y)) {
            page.setState(PageState.RESTART);
        } else if (isInside("q", x, y)) {
            page.setState(PageState.QUIT);
        } else if (isInside("p", x, y)) {
            if (!gameOver) {
                page.setState(PageState.PAUSE);
            }
        } else if (isInside("scr", x, y)) {
            page.setState(PageState.SCORES);
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY() - 30;

        if (isInside("st", x, y) && isDrawn("st")) {
            hoverDraw("st");
            hoverButtonMsg = "st";
        } else if (isInside("cont", x, y) && isDrawn("cont")) {
            hoverDraw("cont");
            hoverButtonMsg = "cont";
        } else if (isInside("rest", x, y) && isDrawn("rest")) {
            hoverDraw("rest");
            hoverButtonMsg = "rest";
        } else if (isInside("q", x, y) && isDrawn("q")) {
            hoverDraw("q");
            hoverButtonMsg = "q";
        } else if (isInside("p", x, y) && isDrawn("p")) {
            hoverDraw("p");
            hoverButtonMsg = "p";
        } else if (isInside("scr", x, y) && isDrawn("scr")) {
            hoverDraw("scr");
            hoverButtonMsg = "scr";
        } else if (hoverDrawn) {
            hoverDrawn = false;
            hoverDelete(hoverButtonMsg);
        }
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
        buttons.put(message, new Button(message));
    }

    public void drawButton(String message, int x, int y) {
        buttons.get(message).init(x, y);
        buttons.get(message).draw();
    }

    public void hoverDraw(String message) {
        if (!hoverDrawn) {
            buttons.get(message).hoverDraw();
            hoverDrawn = true;
        }
    }

    public void hoverDelete(String message) {
        buttons.get(message).hoverDelete();
    }

    public void hideButton(String message) {
        buttons.get(message).hoverDelete();
        buttons.get(message).hide();
    }

    public boolean isDrawn(String button) {
        return buttons.get(button).isDrawn();
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }
}
