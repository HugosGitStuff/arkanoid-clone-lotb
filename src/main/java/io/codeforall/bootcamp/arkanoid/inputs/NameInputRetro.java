package io.codeforall.bootcamp.arkanoid.inputs;


import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.keyboard.*;
import io.codeforall.bootcamp.arkanoid.pages.BreakPage;

import java.util.Scanner;

public class NameInputRetro implements KeyboardHandler {
    private final int SCREEN_WIDTH = 500;
    private final int SCREEN_HEIGHT = 50;
    private StringBuilder playerName = new StringBuilder();
    private Text nameText;
    private Text instructionText;
    private boolean finished = false;
    private boolean cursorVisible = true;
    private Rectangle bg = new Rectangle(250, 685, SCREEN_WIDTH, SCREEN_HEIGHT);

//    // Demo entry point
//    public static void main(String[] args) {
//        NameInputRetro input = new NameInputRetro();
//        input.init();
//    }

    public String init() {
        bg.setColor(Color.BLACK);
        bg.draw();

        instructionText = new Text(380, 650, "ENTER YOUR NAME (LEFT ARROW TO DELETE)");
        instructionText.setColor(Color.RED);
        instructionText.grow(150, 10);
        instructionText.draw();

        nameText = new Text(270, 700, "");
        nameText.setColor(Color.RED);
        nameText.grow(0, 25);
        nameText.draw();

        setupKeyboard();

        run();
        return playerName.toString();
    }

    public void run() {
        try {
            while (!finished) {
                updateDisplay();
                if (playerName.length() < 12) {
                    try {
                        cursorVisible = !cursorVisible;
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupKeyboard() {
        Keyboard keyboard = new Keyboard(this);
        // A–Z
        for (int i = KeyboardEvent.KEY_A; i <= KeyboardEvent.KEY_Z; i++) {
            addKeyEvent(keyboard, i);
        }
        // 0–9
        for (int i = KeyboardEvent.KEY_0; i <= KeyboardEvent.KEY_9; i++) {
            addKeyEvent(keyboard, i);
        }
        addKeyEvent(keyboard, KeyboardEvent.KEY_SPACE);
        addKeyEvent(keyboard, KeyboardEvent.KEY_LEFT);
        addKeyEvent(keyboard, KeyboardEvent.KEY_ENTER);
    }

    private void addKeyEvent(Keyboard keyboard, int key) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(event);
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        if (finished) {
            return;
        }
        int key = e.getKey();
        if (key == KeyboardEvent.KEY_ENTER) {
            nameText.setText(playerName.toString());
            instructionText.setText("NAME ACCEPTED!");
            instructionText.translate(120, 0);
            instructionText.grow(-50, 0);
            finished = true;
            return;
        }
        if (key == KeyboardEvent.KEY_LEFT && playerName.length() > 0) {
            playerName.deleteCharAt(playerName.length() - 1);
            nameText.grow(-10, 0);
            nameText.translate(-10, 0);
        } else if (playerName.length() < 16) {
            if (key >= KeyboardEvent.KEY_A && key <= KeyboardEvent.KEY_Z) {
                char c = (char) ('A' + (key - KeyboardEvent.KEY_A));
                playerName.append(c);
                nameText.translate(10, 0);
                nameText.grow(10, 0);
            } else if (key >= KeyboardEvent.KEY_0 && key <= KeyboardEvent.KEY_9) {
                char c = (char) ('0' + (key - KeyboardEvent.KEY_0));
                playerName.append(c);
                nameText.translate(10, 0);
                nameText.grow(10, 0);
            } else if (key == KeyboardEvent.KEY_SPACE) {
                playerName.append(' ');
                nameText.translate(10, 0);
                nameText.grow(10, 0);
            }
        }
        updateDisplay();
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
    }

    private void updateDisplay() {
        nameText.setText(playerName.toString() + (cursorVisible ? "|" : ""));
    }

    public void clear() {
        playerName.delete(0,playerName.length());
        instructionText.delete();
        nameText.delete();
        bg.delete();
    }
}
