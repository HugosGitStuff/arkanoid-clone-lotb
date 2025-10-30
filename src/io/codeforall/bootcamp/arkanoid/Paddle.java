package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Objects;

public class Paddle extends GameObject {

    Rectangle rectangle;
    private final double speed = 10;
    private PaddleState state;
    private final Grid grid;

    public Paddle(double x, double y, Grid grid) {
        this.grid = grid;
        state = PaddleState.NOT_MOVING;
        this.x = x;
        this.y = y;
        picture = new Picture(this.x, this.y, "resources/paddle/sting-dagger-paddel2.png");
        width = 150;
        height = 22;
        rectangle = new Rectangle(this.x, this.y, width, height);
        rectangle.setColor(Color.YELLOW);
    }

    public void update() {
        if (state == PaddleState.MOVING_RIGHT) {
            if (getX() + getWidth() + (speed) <= grid.getX() + grid.getWidth()) {
                x += speed;
                rectangle.translate(speed, 0);
                picture.translate(speed, 0);
            }
        } else if (state == PaddleState.MOVING_LEFT) {
            if (picture.getX() - (speed) >= grid.getX() - 5) {
                x -= speed;
                rectangle.translate(-speed, 0);
                picture.translate(-speed, 0);
            }
        }
    }

    public void setState(PaddleState state) {
       this.state = state;
    }

    @Override
    public void draw() {
        picture.draw();
       // rectangle.draw();
    }

    @Override
    public int getWidth() {
        return rectangle.getWidth();
    }

    @Override
    public int getHeight() {
        return rectangle.getHeight();
    }
}
