package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Ellipse;
import com.codeforall.simplegraphics.pictures.Picture;

public class Ball extends GameObject {
    private double velocityX, velocityY;
    private Ellipse bola;


    public Ball(int x, int y, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.bola = new Ellipse(x, y, 20, 20);
        bola.setColor(Color.WHITE);
        picture = new Picture(x, y, "resources/ball/arkanoid-ball.png");

    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void update() {
        x += velocityX;
        y += velocityY;
        bola.translate(velocityX, velocityY);
        picture.translate(velocityX, velocityY);
    }

    public void bounce(String direction) {

        if (direction == null) return;
        switch (direction) {
            case "left":
            case "right":
                velocityX *= -1;
                break;
            case "top":
            case "bottom":
                velocityY *= -1;
                break;
        }
    }

    public void paddleBounce(GameObject paddle){
        if (getY() + (getHeight() / 2) <= paddle.getY()) {
            velocityY = velocityY * -1;

        } else if (getX() <= paddle.getX() || getX() + getWidth() >= paddle.getX() + paddle.getWidth()){
            velocityX *= -1;
        }

        velocityX += (velocityX < 0? -0.2 : 0.2);
        velocityY += (velocityY < 0? -0.2 : 0.2);
    }

    public double prevBallX(){
       return getX() - getVelocityX();
    }
    public double prevBallY(){
       return getY() - getVelocityY();
    }

    @Override
    public void draw() {
        //bola.draw();
        picture.draw();
    }

    @Override
    public int getWidth() {
        return bola.getWidth();
    }

    @Override
    public int getHeight() {
        return bola.getHeight();
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void delete(){
        bola.delete();
        picture.delete();
    }
}