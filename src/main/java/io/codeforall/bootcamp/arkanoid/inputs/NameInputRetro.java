package io.codeforall.bootcamp.arkanoid.inputs;


import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.keyboard.*;

import java.util.Scanner;

public class NameInputRetro  implements KeyboardHandler {
    private final int SCREEN_WIDTH = 500;
    private final int SCREEN_HEIGHT = 50;
    private StringBuilder playerName = new StringBuilder();
    private Text nameText;
    private Text instructionText;
    private boolean finished = false;
    private boolean cursorVisible = true;

    public NameInputRetro() {
        // background
        Rectangle bg = new Rectangle(200, 700, SCREEN_WIDTH, SCREEN_HEIGHT);
        bg.setColor(Color.BLACK);
        bg.draw();
        // instructions
        instructionText = new Text(200, 650, "ENTER YOUR NAME");
        instructionText.setColor(Color.YELLOW);
        instructionText.grow(150, 10);

        // name text
        nameText = new Text(200, 700, "_");
        nameText.setColor(Color.YELLOW);
        nameText.grow(150, 25);
    }

    public void drawInputText() {
        instructionText.draw();
        nameText.draw();
        setupKeyboard();
        startCursorBlink();
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
        addKeyEvent(keyboard, KeyboardEvent.KEY_BACK_SLASH);
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
        if (finished) return;
        int key = e.getKey();
        if (key == KeyboardEvent.KEY_ENTER) {
            finished = true;
            nameText.setText(playerName.toString());
            return;
        }
        if (key == KeyboardEvent.KEY_BACK_SLASH &&  playerName.length() > 0) {
            playerName.deleteCharAt(playerName.length() - 1);
        } else if (playerName.length() < 12) {
            if (key >= KeyboardEvent.KEY_A && key <= KeyboardEvent.KEY_Z) {
                char c = (char) ('A' + (key - KeyboardEvent.KEY_A));
                playerName.append(c);
            } else if (key >= KeyboardEvent.KEY_0 && key <= KeyboardEvent.KEY_9) {
                char c = (char) ('0' + (key - KeyboardEvent.KEY_0));
                playerName.append(c);
            } else if (key == KeyboardEvent.KEY_SPACE) {
                playerName.append(' ');
            }
        }
        updateDisplay();
    }

    @Override
    public void keyReleased(KeyboardEvent e) {}

    private void updateDisplay() {
        if (!finished) {
            nameText.setText(playerName.toString() + (cursorVisible ? "|" : ""));
        } else {
            nameText.setText(playerName.toString());
        }
    }

    private void startCursorBlink() {
        Thread blink = new Thread(() -> {
            try {
                while (!finished) {
                    cursorVisible = !cursorVisible;
                    updateDisplay();
                    Thread.sleep(600);
                }
            } catch (InterruptedException ignored) {}
        });
        blink.start();
    }
    public String getPlayerName() {
        return playerName.toString();
    }
    public boolean isFinished() {
        return finished;
    }
    // Demo entry point
    public static void main(String[] args) throws InterruptedException {
        NameInputRetro input = new NameInputRetro();
        while (!input.isFinished()) {
            input.drawInputText();
            Thread.sleep(400);
        }
        System.out.println("Player name: " + input.getPlayerName());
    }
}
