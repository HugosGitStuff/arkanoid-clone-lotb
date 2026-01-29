package io.codeforall.bootcamp.arkanoid.objects.powerups;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;
import io.codeforall.bootcamp.arkanoid.objects.ball.Ball;
import io.codeforall.bootcamp.arkanoid.objects.paddle.Paddle;

import java.util.ArrayList;

public class PowerUp extends GameObject {
    private final Picture image;
    private final int imgNum;
    private final String[] images = new String[]{
            "powerups/paddle-lgt+_dim.png",
            "powerups/paddle-lgt-_dim.png",
            "powerups/paddle-vel+_dim.png",
            "powerups/paddle-vel-_dim.png",
            "powerups/ball-vel+_dim.png",
            "powerups/ball-vel-_dim.png",
            "powerups/ball+1.png",
            "powerups/ball+3.png",
            "powerups/ball+5.png"};

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
        return getX()  + image.getWidth() >= other.getX();
    }

    public boolean collisionBottomLeft (GameObject other){
        return getX() <= other.getX() + other.getWidth() && getY() + image.getHeight() >= other.getY();
    }

    public boolean collisionBottomRight(GameObject other){
        return getX() + image.getWidth() >= other.getX() && getY() + image.getHeight() >= other.getY();
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
               paddle.switchWidth(10);
               break;
            case 1:
                paddle.switchWidth(-10);
                break;
            case 2:
                paddle.setSpeed(paddle.getSpeed() + 2);
                break;
            case 3:
                paddle.setSpeed(paddle.getSpeed() - 2);
                break;
            case 4:
                for (Ball ball : balls) {
                    ball.setVelocityX(ball.getVelocityX() * 1.2);
                    ball.setVelocityY(ball.getVelocityY() * 1.2);
                }
                break;
            case 5:
                for (Ball ball : balls) {
                    ball.setVelocityY(ball.getVelocityY() * 0.8);
                    ball.setVelocityX(ball.getVelocityX() * 0.8);
                }
                break;
            case 6:
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/2, 700, -3, -3));
                break;
            case 7:
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/3, 700, -3, -3));
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/2, 700, 3, -3));
                balls.add(new Ball(paddle.getX() + (paddle.getWidth()*2)/3, 700, -3, -3));
                break;
            case 8:
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/6, 700, -3, -3));
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/3, 700, 3, -3));
                balls.add(new Ball(paddle.getX() + paddle.getWidth()/2, 700, -3, -3));
                balls.add(new Ball(paddle.getX() + (paddle.getWidth()*2)/3, 700, 3, -3));
                balls.add(new Ball(paddle.getX() + (paddle.getWidth()*5)/6, 700, -3, -3));
                break;
        }
        delete();
    }

    @Override
    public void draw() {
        image.draw();
    }

    @Override
    public double getHeight() {
        return image.getHeight();
    }

    @Override
    public double getWidth() {
        return image.getWidth();
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
