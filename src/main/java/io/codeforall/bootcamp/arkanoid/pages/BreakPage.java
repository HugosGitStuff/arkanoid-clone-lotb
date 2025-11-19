package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BreakPage implements Page {
    private final String[] picturePaths = new String[]{
            "text/textIntro.png",
            "text/congratsFirstLevel.png",
            "text/congratsSecondLevel.png",
            "text/finalGame.png"};
    private MyMouse myMouse;
    private ScreenAdditions screenAddons;
    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private PageState state;
    private Picture background;
    private int score = 0;
    private int level;
    private IntroPage intro;

    @Override
    public void init() {
        background = new Picture(10, 10, picturePaths[level]);
        background.draw();

        createMouseButtons();
        drawButtons();

        if (level == 3) {
            try {
                screenAddons.finalScore(score);
                myMouse.hideButton("start");
                myMouse.drawButton("quit", 950, 750);
                // screenAddon.scoreboard(scoreSaver.getSavedScores("/score/score.txt"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                scoreSaver.saveToFile("score/score.txt", scoreSaver.updateScores(LocalDate.now().format(formatter), "first game", score));

                Thread.sleep(20000);
                System.exit(0);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        setState(PageState.IDLE);

        while (state != PageState.QUIT) {
            switch (state) {
                case START:
                    hideButtons();
                    clear();
                    break;
                case SCORES:
                    System.out.println("Coming soon...");
                    break;
                case RESTART:
                    screenAddons.setLevel("0");
                    screenAddons.setScore(0);
                    background.delete();
                    hideButtons();
                    myMouse.reset();

                    myMouse.setPage(intro);
                    intro.setState(PageState.IDLE);
                    intro.init();
                    break;
                case IDLE:
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

            }

        }
        System.exit(0);
    }

    @Override
    public void clear() {
        level++;
        screenAddons.setLevel("" + level);
        gamePage.setLevel(level);
        gamePage.setScore(score);
        myMouse.reset();
        myMouse.setPage(gamePage);
        background.delete();
        gamePage.setState(PageState.RUNNING);
        gamePage.init();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setGamePage(GamePage gamePage) {
        this.gamePage = gamePage;
    }

    public void setMyMouse(MyMouse myMouse) {
        this.myMouse = myMouse;
    }

    public void setState(PageState state) {
        this.state = state;
    }

    public void setScreenAddons(ScreenAdditions screenAddons) {
        this.screenAddons = screenAddons;
    }

    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    }

    public void setIntro(IntroPage intro) {
        this.intro = intro;
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("start /space");
        myMouse.createButton("scores /h");
        myMouse.createButton("restart /r");
        myMouse.createButton("quit /q");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("start", 950, 750);
        myMouse.drawButton("scores", 750, 750);
        myMouse.drawButton("restart", 550, 750);
    }

    //@Override
    public void hideButtons() {
            myMouse.hideButton("scores");
            myMouse.hideButton("restart");
        if(myMouse.isDrawn("start")) {
            myMouse.hideButton("start");
        } else {
           myMouse.hideButton("quit");
        }
    }
}
