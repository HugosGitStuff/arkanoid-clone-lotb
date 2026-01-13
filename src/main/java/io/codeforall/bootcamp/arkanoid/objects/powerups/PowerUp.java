package io.codeforall.bootcamp.arkanoid.objects.powerups;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;
import io.codeforall.bootcamp.arkanoid.objects.ball.Ball;
import io.codeforall.bootcamp.arkanoid.objects.paddle.Paddle;

import java.util.ArrayList;

public class PowerUp extends GameObject {
    private Picture image;
    private int imgNum;
    private final String[] images = new String[]{
            "powerups/paddle-lgt+_dim.png",
            "powerups/paddle-lgt-_dim.png",
            "powerups/paddle-vel+_dim.png",
            "powerups/paddle-vel-_dim.png",
            "powerups/ball-vel+_dim.png",
            "powerups/ball-vel-_dim.png"};

    private Paddle paddle;
    private ArrayList<Ball> balls;

    public PowerUp (double x,double y, int imgNum){
        this.x = x;
        this.y = y;
        this.imgNum = imgNum;
        image = new Picture(this.x, this.y, images[imgNum]);
        draw();
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

    public void update() {
        y += 1;
        image.translate(0,1);
    }

    public void execute() {
        switch (imgNum){
            case 0:
                System.out.println("case 1: " + paddle.getWidth());
               paddle.setWidth(paddle.getWidth() + 50);
                System.out.println(paddle.getWidth());
               break;
            case 1:
                System.out.println("case 2:" + paddle.getWidth());
                paddle.setWidth(paddle.getWidth() - 50);
                System.out.println(paddle.getWidth());
                break;
            case 2:
                System.out.println("case 3:" + paddle.getSpeed());
                paddle.setSpeed(paddle.getSpeed() + 2);
                System.out.println(paddle.getSpeed());
                break;
            case 3:
                System.out.println("case 4:" + paddle.getSpeed());
                paddle.setSpeed(paddle.getSpeed() - 2);
                System.out.println(paddle.getSpeed());
                break;
            case 4:
                for (Ball ball : balls) {
                    System.out.println("case 5:" + ball.getVelocityX());
                    ball.setVelocityX(ball.getVelocityX() * 1.2);
                    ball.setVelocityY(ball.getVelocityY() * 1.2);
                    System.out.println(ball.getVelocityX());
                }
                break;
            case 5:
                for (Ball ball : balls) {
                    System.out.println("case 6:" + ball.getVelocityX());
                    ball.setVelocityY(ball.getVelocityY() * 0.8);
                    ball.setVelocityX(ball.getVelocityX() * 0.8);
                    System.out.println(ball.getVelocityX());
                }
                break;
        }
        delete();
    }

    @Override
    public void draw() {
        image.draw();
    }

    @Override
    public void delete() {
        image.delete();
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }
}
