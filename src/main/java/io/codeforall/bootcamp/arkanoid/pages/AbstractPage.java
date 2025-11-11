package io.codeforall.bootcamp.arkanoid.pages;

public abstract class AbstractPage implements Page {
//    private MyMouse myMouse;


    protected PageState state;

    public void setState(PageState state) {
        this.state = state;
    }
}
