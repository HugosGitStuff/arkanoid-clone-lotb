package io.codeforall.bootcamp.arkanoid.pages;


public interface Page {

    void init();

    void clear();

    void drawButtons();

    void hideButtons();

    void setState(PageState pageState);

    void createMouseButtons();
}
