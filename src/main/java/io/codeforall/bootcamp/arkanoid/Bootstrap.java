package io.codeforall.bootcamp.arkanoid;

import io.codeforall.bootcamp.arkanoid.inputs.MyKeyboard;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;
import io.codeforall.bootcamp.arkanoid.inputs.NameInputRetro;
import io.codeforall.bootcamp.arkanoid.inputs.ScoreSaver;
import io.codeforall.bootcamp.arkanoid.inputs.ScreenAdditions;
import io.codeforall.bootcamp.arkanoid.pages.*;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class Bootstrap implements LineListener {

    private MyMouse myMouse;
    private MyKeyboard myKeyboard;
    private ScreenAdditions screenAddons;

    private String[] songPaths = new String[]{"/soundtrack/music1.wav",
                                            "/soundtrack/GameSound.wav",
                                            "soundtrack/Oath In The Iron Sky.wav",
                                            "/soundtrack/Raven Banner.wav",
                                            "soundtrack/Ravens On The Ice.wav",
                                            "/soundtrack/Ritual of the Hollow Peak.wav"};

    private ScorePage scorePage;
    private ScoreSaver scoreSaver;
    private GamePage gamePage;
    private BreakPage breakPage;
    private IntroPage intro;
    private NameInputRetro nameInput;

    public void execute() {

        myMouse = new MyMouse();
        //myMouse.init();

        myKeyboard = new MyKeyboard();
        myKeyboard.init();

        screenAddons = new ScreenAdditions();

        scoreSaver = new ScoreSaver();

        gamePage = new GamePage();
        gamePage.setMyKeyboard(myKeyboard);
        gamePage.setScreenAddons(screenAddons);
        gamePage.setMyMouse(myMouse);
        gamePage.setState(PageState.IDLE);

        breakPage = new BreakPage();
        breakPage.setGamePage(gamePage);
        breakPage.setMyMouse(myMouse);
        breakPage.setLevel(0);
        breakPage.setState(PageState.IDLE);
        breakPage.setScreenAddons(screenAddons);
        breakPage.setScoreSaver(scoreSaver);


        intro = new IntroPage();
        intro.setBreakPage(breakPage);
        intro.setMyMouse(myMouse);
        intro.setState(PageState.IDLE);
        intro.setScoreSaver(scoreSaver);
        intro.setScreenAddons(screenAddons);

        breakPage.setIntro(intro);

        gamePage.setBreakPage(breakPage);
        gamePage.setIntro(intro);
        myMouse.setPage(intro);

        scorePage = new ScorePage();
        scorePage.setMouse(myMouse);
        breakPage.setScorePage(scorePage);
        intro.setScorePage(scorePage);

        nameInput = new NameInputRetro();
        breakPage.setNameInput(nameInput);


        playMusic(randomSong());
        intro.init();
    }

    private void playMusic(String path) {
        try {
            URL file = getClass().getResource(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file.openStream()));

            Clip musicClip = AudioSystem.getClip();
            musicClip.addLineListener(this);
            musicClip.open(audioStream);
            //musicClip.loop(Clip.LOOP_CONTINUOUSLY); // loop forever
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public String randomSong() {
        return songPaths[(int) (Math.random()* songPaths.length)];
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            playMusic(randomSong());
        }
    }
}
