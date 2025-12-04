package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.NameInputRetro;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class BreakPage implements Page {
    private final String[] picturePaths = new String[]{
            "breakPage/introLevel.png",
            "breakPage/firstLevel.png",
            "breakPage/secondLevel.png",
            "breakPage/finalLevel.png"};
    private MyMouse myMouse;
    private ScreenAdditions screenAddons;
    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private ScorePage scorePage;
    private IntroPage intro;
    private NameInputRetro nameInput;
    private PageState state;
    private Picture background;
    private int score = 0;
    private int level;
    private boolean finalLevel = false;
    private boolean addedName = false;

    @Override
    public void init() {

        if (level >= 3 && level <= 12) {
            background = new Picture(10, 10, picturePaths[2]);
        } else if (level == 13) {
            background = new Picture(10, 10, picturePaths[3]);
        } else {
            background = new Picture(10, 10, picturePaths[level]);
        }

        background.draw();
        myMouse.setGameOver(false);
        createButtons();
        drawButtons();

        setState(PageState.IDLE);
        run();
    }

    @Override
    public void run() {
        if (finalLevel) {
            try {
                screenAddons.finalScore(score);
                myMouse.hideButton("st");
                myMouse.drawButton("q", 930, 670);
                if (!addedName) {
                    String description = nameInput.init();
                    nameInput.clear();
                    addedName = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    ArrayList<String[]> updatedScores = scoreSaver.updateScores(LocalDate.now().format(formatter), description, score);
                    for (String[] score : updatedScores) {
                        System.out.println(Arrays.toString(score));
                    }
                    scoreSaver.saveToFile("src/main/resources/score/score.txt", updatedScores);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        while (state != PageState.QUIT) {
            switch (state) {
                case START:
                    hideButtons();
                    clear();
                    break;
                case SCORES:
                    hideButtons();
                    scorePage.setScreenAddons(screenAddons);
                    scorePage.setScoreSaver(scoreSaver);
                    myMouse.setPage(scorePage);
                    scorePage.setPage(this);
                    scorePage.init();
                    break;
                case RESTART:
                    screenAddons.setLevel("0");
                    screenAddons.setScore(0);
                    background.delete();
                    hideButtons();
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
        hideButtons();
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

    @Override
    public void setScreenAddons(ScreenAdditions screenAddons) {
        this.screenAddons = screenAddons;
    }

    public void setScoreSaver(ScoreSaver scoreSaver) {
        this.scoreSaver = scoreSaver;
    }

    public void setIntro(IntroPage intro) {
        this.intro = intro;
    }

    public void setScorePage(ScorePage scorePage) {
        this.scorePage = scorePage;
    }

    public void setFinalLevel(boolean finalLevel) {
        this.finalLevel = finalLevel;
    }

    public void setNameInput(NameInputRetro nameInput) {
        this.nameInput = nameInput;
    }

    @Override
    public void createButtons() {
        myMouse.createButton("st");
        myMouse.createButton("scr");
        myMouse.createButton("rest");
        myMouse.createButton("q");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("st", 930, 670);
        myMouse.drawButton("scr", 930, 710);
        myMouse.drawButton("rest", 930, 750);
    }

    @Override
    public void hideButtons() {
        myMouse.hideButton("scr");
        myMouse.hideButton("rest");
        if (myMouse.isDrawn("st")) {
            myMouse.hideButton("st");
        } else if (myMouse.isDrawn("q")){
            myMouse.hideButton("q");
        }
    }
}
