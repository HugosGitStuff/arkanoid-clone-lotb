package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Rectangle;
import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.pages.BreakPage;
import io.codeforall.bootcamp.arkanoid.pages.GamePage;
import io.codeforall.bootcamp.arkanoid.pages.IntroPage;

import javax.sound.sampled.Clip;
import java.io.IOException;

public class Bootstrap {

    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;


    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private BreakPage breakPage;
    private IntroPage intro;

    public void execute() throws InterruptedException, IOException {

        myKeyboard = new MyKeyboard();
        myKeyboard.init();

        screenAddon = new ScreenAdditions();

        scoreSaver = new ScoreSaver();

        gamePage = new GamePage();
        gamePage.setMyKeyboard(myKeyboard);
        gamePage.setScoreSaver(scoreSaver);
        gamePage.setScreenAddon(screenAddon);

        breakPage = new BreakPage();
        breakPage.setGamePage(gamePage);
        breakPage.setLevel(0);


        intro = new IntroPage();
        intro.setBreakPage(breakPage);

        intro.init();
    }
}
