package io.codeforall.bootcamp.arkanoid.inputs.Mouse;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Map;

import static java.util.Map.entry;

public class Button {
    private final int width = 150;
    private final int height = 31;
    private Rectangle button;
    private String message;
//    private Text text;
    private int x;
    private int y;
    private boolean drawn;
    private Picture pic;
    private final Map<String, String> buttonImg = Map.ofEntries(
            entry("st", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/start_game_button.png"),
            entry("p", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/pause_button.png"),
            entry("scr", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/scores_button.png"),
            entry("rest", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/restart_button.png"),
            entry("q", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/quit_button.png"),
            entry("cont", "src/main/resources/buttons/cleanedButtonsFinalSmallSize/continue_button.png")
    );

    public Button(String message) {
        this.message = message;
    }

    public void init(int x, int y) {
        this.x = x;
        this.y = y;
        button = new Rectangle(this.x, this.y, width, height);

        pic = new Picture(x, y, buttonImg.get(message));

        button.setColor(Color.LIGHT_GRAY);
//        text = new Text(this.x, this.y, message);
//        text.grow(10, 10);
//        text.translate(centeredTextX(), 8);
//        text.setColor(Color.YELLOW);
    }
//
//    public int centeredTextX() {
//        return (button.getWidth() / 2 - text.getWidth() / 2) + 17;
//    }

    public void hide() {
        drawn = false;
        button.delete();
        pic.delete();
//        text.delete();
        x = 0;
        y = 0;
    }

    public void draw() {
        drawn = true;
//        text.draw();
        pic.draw();
        //button.draw();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDrawn() {
        return drawn;
    }
}

