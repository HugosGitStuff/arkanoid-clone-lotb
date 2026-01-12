package io.codeforall.bootcamp.arkanoid.objects.powerups;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;
import io.codeforall.bootcamp.arkanoid.objects.ball.Ball;
import io.codeforall.bootcamp.arkanoid.objects.paddle.Paddle;

public class PowerUp extends GameObject {
    private final String[] images = new String[];
    private Picture image;
    private int imgNum;

    private Paddle paddle;
    private Ball ball;

    public PowerUp (double x,double y, int imgNum){
        this.x = x;
        this.y = y;
        this.imgNum = imgNum;
        image = new Picture(this.x, this.y, images[imgNum]);
    }



    public boolean collided(){
    return false;
    }

    public void execute() {
        switch (imgNum){
            case 0:
               paddle.setWidth(paddle.getWidth() + 50);
               break;
            case 1:
                paddle.setWidth(paddle.getWidth() - 50);
                break;
            case 2:
                paddle.setSpeed(paddle.getSpeed() + 2);
                break;
            case 3:
                paddle.setSpeed(paddle.getSpeed() - 2);
                break;
            case 4:
                ball.setVelocityX(ball.getVelocityX() * 1.2);
                ball.setVelocityY(ball.getVelocityY() * 1.2);
                break;
            case 5:
                ball.setVelocityY(ball.getVelocityY() * 0.8);
                ball.setVelocityX(ball.getVelocityX() * 0.8);
                break;
            case 
        }
    }

    @Override
    public void draw() {
        image.draw();
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
}
