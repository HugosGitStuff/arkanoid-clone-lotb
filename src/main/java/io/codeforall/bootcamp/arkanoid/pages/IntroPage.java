package io.codeforall.bootcamp.arkanoid.pages;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
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

    private Rectangle startBtn;
    private Rectangle optionsBtn;
    private Rectangle highScoresBtn;

    private Text startText;
    private Text optionsText;
    private Text highScoresText;

    private PageState state;

    private Color normalColor = new Color(220, 200, 120); // gold tone
    private Color glowColor = new Color(255, 255, 180);   // lighter glow


    @Override
    public void init() {
        background = new Picture(10, 10, "introbackground/FinalIntroImageBackground.png");
        background.draw();


        // Buttons (adjust to align perfectly with your image)
        startBtn = new Rectangle(530, 400, 190, 40);
//        optionsBtn = new Rectangle(530, 460, 190, 40);
//        highScoresBtn = new Rectangle(530, 520, 190, 40);

        startBtn.setColor(normalColor);
//        optionsBtn.setColor(normalColor);
//        highScoresBtn.setColor(normalColor);

        // Text labels
//        startText = new Text(310, 790, "PRESS SPACE TO START GAME");
//        optionsText = new Text(510, 473, "OPTIONS");
//        highScoresText = new Text(510, 533, "HIGH SCORES");

//        styleText(startText);
//        styleText(optionsText);
//        styleText(highScoresText);

//        drawButton(startText);
//        drawButton(optionsBtn, optionsText);
//        drawButton(highScoresBtn, highScoresText);
//
//        Mouse mouse = new Mouse(this);
//        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
//        mouse.addEventListener(MouseEventType.MOUSE_MOVED);
//
        playMusic("/soundtrack/AncientShadowsRising.wav");
//
//        Keyboard keyboard = new Keyboard(this);
//        KeyboardEvent spaceEvent = new KeyboardEvent();
//        spaceEvent.setKey(KeyboardEvent.KEY_SPACE);
//        spaceEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
//        keyboard.addEventListener(spaceEvent);
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
        myMouse.reset();
        myMouse.setPage(breakPage);
        breakPage.setState(PageState.IDLE);
        //hideButtons();
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

    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    private void styleText(Text text) {
        text.setColor(Color.WHITE);
        text.grow(10, 30);
    }

    private void drawButtonRec(Rectangle rect, Text label) {
        rect.draw();
        label.draw();
    }

    private void drawButton(Text label) {

        label.draw();
    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        int x = (int) e.getX();
//        int y = (int) e.getY();
//
//        if (isInside(startBtn, x, y)) {
//            System.out.println("Start Game clicked!");
//        } else if (isInside(optionsBtn, x, y)) {
//            System.out.println("Options clicked!");
//        } else if (isInside(highScoresBtn, x, y)) {
//            System.out.println("High Scores clicked!");
//        }
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//        int x = (int) e.getX();
//        int y = (int) e.getY();
//
//        // Glow effect on hover
//        handleHover(startBtn, startText, x, y);
//        handleHover(optionsBtn, optionsText, x, y);
//        handleHover(highScoresBtn, highScoresText, x, y);
//    }
//
//    private boolean isInside(Rectangle rect, int x, int y) {
//        return x >= rect.getX() && x <= rect.getX() + rect.getWidth()
//                && y >= rect.getY() && y <= rect.getY() + rect.getHeight();
//    }
//
 //
//    @Override
//    public void keyPressed(KeyboardEvent e) {
//        if (e.getKey() == KeyboardEvent.KEY_SPACE) {
//            finished = true;
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyboardEvent e) {
//    }
//
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
        myMouse.createButton("START");
        myMouse.createButton("SCORES");
        myMouse.createButton("QUIT");
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
