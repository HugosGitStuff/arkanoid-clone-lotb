package io.codeforall.bootcamp.arkanoid.inputs;


import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.keyboard.*;

public class NameInputRetro  implements KeyboardHandler {
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 600;
    private StringBuilder playerName = new StringBuilder();
    private Text nameText;
    private Text instructionText;
    private boolean finished = false;
    private boolean cursorVisible = true;
    public NameInputRetro() {
        // background
        Rectangle bg = new Rectangle(10, 10, SCREEN_WIDTH, SCREEN_HEIGHT);
        bg.setColor(Color.BLACK);
        bg.fill();
        // instructions
        instructionText = new Text(0, 0, "ENTER YOUR NAME");
        instructionText.setColor(Color.YELLOW);
        instructionText.grow(150, 25);
        centerText(instructionText, 220);
        instructionText.draw();
        // name text
        nameText = new Text(0, 0, "_");
        nameText.setColor(Color.YELLOW);
        nameText.grow(150, 25);
        centerText(nameText, 300);
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
    private void centerText(Text text, int y) {
        int textWidth = text.getWidth() * 18; // approximate width per char
        int x = (SCREEN_WIDTH / 2) - (textWidth / 2);
        text.translate(x - text.getX(), y - text.getY());
    }
    @Override
    public void keyPressed(KeyboardEvent e) {
        if (finished) return;
        int key = e.getKey();
        if (key == KeyboardEvent.KEY_ENTER) {
            finished = true;
            instructionText.setText("WELCOME " + playerName.toString() + "!");
            centerText(instructionText, 220);
            nameText.setText(playerName.toString());
            centerText(nameText, 300);
            return;
        }
        if (key == KeyboardEvent.KEY_BACK_SLASH && !playerName.isEmpty()) {
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
            nameText.setText(playerName.toString() + (cursorVisible ? "_" : ""));
        } else {
            nameText.setText(playerName.toString());
        }
        centerText(nameText, 300);
    }
    private void startCursorBlink() {
        Thread blink = new Thread(() -> {
            try {
                while (!finished) {
                    cursorVisible = !cursorVisible;
                    updateDisplay();
                    Thread.sleep(400);
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
            Thread.sleep(100);
        }
        System.out.println("Player name: " + input.getPlayerName());
    }
}
