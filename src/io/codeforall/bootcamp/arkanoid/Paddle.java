package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

public class Paddle extends GameObject {

    private final double speed = 34;
    Rectangle rectangle;

    public Paddle(double x, double y) {
        this.x = x;
        this.y = y;
        picture = new Picture(x,y, "resources/paddle/sting-dagger-paddel2.png");
        width = 150;
        height = 22;
        rectangle = new Rectangle(x,y + 5,width,height);
        rectangle.setColor(Color.YELLOW);
    }

    public void moveRight() {

        if (picture.getMaxX() + (speed) <= 866){
            rectangle.translate(speed, 0);
            picture.translate(speed,0);
            x += speed;
        }
    }

    public void moveLeft() {
        // Check if left edge - movement stays within left boundary (10)
        if (picture.getX() - (speed) >= 20) {
            picture.translate(-speed, 0);
            rectangle.translate(-speed,0);
            x -= speed;
        }
    }

    @Override
    public void draw() {
        picture.draw();
        //rectangle.draw();
    }

    @Override
    public int getWidth() { return rectangle.getWidth();}
    @Override
    public int getHeight() { return rectangle.getHeight();}
}
