package io.codeforall.bootcamp.arkanoid;

import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.pages.*;

import java.io.IOException;

public class Bootstrap {

    private MyMouse myMouse;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;


    private ScorePage scorePage;
    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private BreakPage breakPage;
    private IntroPage intro;

    public void execute() {

        myMouse = new MyMouse();
        //myMouse.init();

        myKeyboard = new MyKeyboard();
        myKeyboard.init();

        screenAddon = new ScreenAdditions();

        scoreSaver = new ScoreSaver();

        gamePage = new GamePage();
        gamePage.setMyKeyboard(myKeyboard);
        gamePage.setScreenAddon(screenAddon);
        gamePage.setMyMouse(myMouse);
        gamePage.setState(PageState.IDLE);

        myMouse.setGamePage(gamePage);

        breakPage = new BreakPage();
        breakPage.setGamePage(gamePage);
        breakPage.setMyMouse(myMouse);
        breakPage.setLevel(0);
        breakPage.setState(PageState.IDLE);
        breakPage.setScreenAddons(screenAddon);
        breakPage.setScoreSaver(scoreSaver);


        intro = new IntroPage();
        intro.setBreakPage(breakPage);
        intro.setMyMouse(myMouse);
        intro.setState(PageState.IDLE);

        breakPage.setIntro(intro);

        gamePage.setBreakPage(breakPage);
        gamePage.setIntro(intro);
        myMouse.setPage(intro);

        scorePage = new ScorePage();
        scorePage.setMouse(myMouse);
        breakPage.setScorePage(scorePage);

        intro.init();
    }
}
