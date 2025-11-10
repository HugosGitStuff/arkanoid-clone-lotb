package io.codeforall.bootcamp.arkanoid.pages;

import io.codeforall.bootcamp.arkanoid.inputs.MyMouse;

public abstract class AbstractPage {
    private MyMouse mouse;

    public abstract void drawButtons();

    public abstract void hideButtons();
}
