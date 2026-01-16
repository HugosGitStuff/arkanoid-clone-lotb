package io.codeforall.bootcamp.arkanoid.pages;


import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import java.io.IOException;

public interface Page {

    void run() throws IOException;

    void init();

    void clear();

    void drawButtons();

    void setState(PageState pageState);

    void createButtons();

    void hideButtons();

    void setScoreSaver(ScoreSaver scoreSaver);

    void setScreenAddons(ScreenAdditions screenAddons);
}
