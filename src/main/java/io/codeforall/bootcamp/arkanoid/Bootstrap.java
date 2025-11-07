package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.pages.GamePage;
import io.codeforall.bootcamp.arkanoid.pages.IntroPage;

import javax.sound.sampled.Clip;
import java.io.IOException;

public class Bootstrap {
    private int score = 0;
    private int level = 1;
    private int numBlocksRemoved = 0;

    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddon;


    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private IntroPage intro;
    private Clip musicClip;

    public void execute() throws IOException, InterruptedException {
        myKeyboard = new MyKeyboard();
        myKeyboard.init();

        screenAddon = new ScreenAdditions(level, score);
        screenAddon.initialText();

        scoreSaver = new ScoreSaver();

        intro = new IntroPage();

        Picture textIntro = new Picture(10, 10, "/text/textIntro.png");
        intro.delete();
        textIntro.draw();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        textIntro.delete();

        gamePage = new GamePage();

        gamePage.setMyKeyboard(myKeyboard);
        gamePage.setScoreSaver(scoreSaver);
        gamePage.setScreenAddon(screenAddon);

        gamePage.init();

    }


}
