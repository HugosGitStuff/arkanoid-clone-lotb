package io.codeforall.bootcamp.arkanoid;

import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.pages.BreakPage;
import io.codeforall.bootcamp.arkanoid.pages.GamePage;
import io.codeforall.bootcamp.arkanoid.pages.IntroPage;
import io.codeforall.bootcamp.arkanoid.pages.PageState;

import java.io.IOException;

public class Bootstrap {

    private MyMouse myMouse;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;


    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private BreakPage breakPage;
    private IntroPage intro;

    public void execute() throws InterruptedException, IOException {

        myMouse = new MyMouse();
        //myMouse.init();

        myKeyboard = new MyKeyboard();
        myKeyboard.init();

        screenAddon = new ScreenAdditions();

        scoreSaver = new ScoreSaver();

        gamePage = new GamePage();
        gamePage.setMyKeyboard(myKeyboard);
        gamePage.setScoreSaver(scoreSaver);
        gamePage.setScreenAddon(screenAddon);
        gamePage.setMyMouse(myMouse);
        gamePage.setState(PageState.IDLE);

        myMouse.setGamePage(gamePage);

        breakPage = new BreakPage();
        breakPage.setGamePage(gamePage);
        breakPage.setMyMouse(myMouse);
        breakPage.setLevel(0);
        breakPage.setState(PageState.IDLE);


        intro = new IntroPage();
        intro.setBreakPage(breakPage);
        intro.setMyMouse(myMouse);
        intro.setState(PageState.IDLE);

        gamePage.setIntro(intro);
        myMouse.setPage(intro);

        intro.init();
    }
}
