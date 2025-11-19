package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.inputs.Mouse.MyMouse;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class IntroPage implements Page {
    private MyMouse myMouse;
    private BreakPage breakPage;

    private Picture background;
    private Clip musicClip;

    private PageState state;

    private Color normalColor = new Color(220, 200, 120); // gold tone
    private Color glowColor = new Color(255, 255, 180);   // lighter glow


    @Override
    public void init() {
        background = new Picture(10, 10, "introbackground/FinalIntroImageBackground.png");
        background.draw();
        playMusic("/soundtrack/AncientShadowsRising.wav");

        createMouseButtons();
        drawButtons();

        while(state != PageState.QUIT) {
            switch (state) {
                case START:
                    clear();
                    break;
                case SCORES:
                    System.out.println("Coming soon!");
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
        hideButtons();
        myMouse.reset();
        myMouse.setPage(breakPage);
        breakPage.setState(PageState.IDLE);
        breakPage.setLevel(0);
        breakPage.setScore(0);
        background.delete();
        breakPage.init();
    }

    private void playMusic(String path) {
        try {
            URL file = getClass().getResource(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file.openStream()));

            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // loop forever
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public void setBreakPage(BreakPage breakPage) {
        this.breakPage = breakPage;
    }

    public void setMyMouse(MyMouse myMouse) {
        this.myMouse = myMouse;
    }

    public void setState(PageState state) {
        this.state = state;
    }

    @Override
    public void createMouseButtons() {
        myMouse.createButton("start /space");
        myMouse.createButton("scores /h");
        myMouse.createButton("quit /q");
    }

    @Override
    public void drawButtons() {
        myMouse.drawButton("start", 550, 750);
        myMouse.drawButton("scores", 350, 750);
        myMouse.drawButton("quit", 150, 750);
    }

    //@Override
    public void hideButtons() {
        myMouse.hideButton("start");
        myMouse.hideButton("scores");
        myMouse.hideButton("quit");
    }
}
