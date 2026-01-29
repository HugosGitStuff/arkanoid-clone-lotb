package io.codeforall.bootcamp.arkanoid.objects.ball;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Ellipse;
import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;
import io.codeforall.bootcamp.arkanoid.objects.grid.Grid;

public class Ball extends GameObject {
    private double velocityX, velocityY;
    private final Ellipse ball;


    public Ball(double x, double y, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.ball = new Ellipse(x, y, 20, 20);
        ball.setColor(Color.WHITE);
        picture = new Picture(x, y, "/ball/image.png");
        draw();
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
        ball.translate(velocityX, velocityY);
        picture.translate(velocityX, velocityY);
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

    public void directionAfterCollision(GameObject other) {
        boolean wasAbove = prevBallY() + getHeight() <= other.getY();
        boolean wasBelow = prevBallY() >= other.getY() + other.getHeight();
        boolean wasLeft = prevBallX() + getWidth() <= other.getX();
        boolean wasRight = prevBallX() >= other.getX() + other.getWidth();

        if (wasAbove || wasBelow) {
            velocityY *= -1;
        }
        if (wasLeft || wasRight) {
            velocityX *= -1;
        }
    }

    public boolean collisionTopLeft(GameObject other){
        return getX() <= other.getX() + other.getWidth() && getY() <= other.getY() + other.getHeight();
    }

    public boolean collisionTopRight(GameObject other){
        return getX()  + getWidth() >= other.getX();
    }

    public boolean collisionBottomLeft (GameObject other){
        return getX() <= other.getX() + other.getWidth() && getY() + getHeight() >= other.getY();
    }

    public boolean collisionBottomRight(GameObject other){
        return getX() + getWidth() >= other.getX() && getY() + getHeight() >= other.getY();
    }

    public boolean collidesWith(GameObject other) {
        return (collisionTopLeft(other) && collisionTopRight(other)) && (collisionBottomLeft(other) && collisionBottomRight(other));
    }

    public boolean collidesWithWall(Grid grid) {
        if (x <= grid.getX() || x + getWidth() >= grid.getX() + grid.getWidth()) {
            velocityX *= -1;
            return true;
        }
        if (y <= grid.getY() || y + getHeight() >= grid.getY() + grid.getHeight()) {
            velocityY *= -1;
            return true;
        }
        return false;
    }

    @Override
    public void draw() {
        //ball.draw();
        picture.draw();
    }

    @Override
    public double getWidth() {
        return ball.getWidth();
    }

    @Override
    public double getHeight() {
        return ball.getHeight();
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void delete(){
        ball.delete();
        picture.delete();
    }
}