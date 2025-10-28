package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.pictures.Picture;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected Picture picture;

    // metodo geral para desenhar as peças
    public abstract void draw();

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

    public String checkWallCollision(Grid grid, int canvasWidth, int canvasHeight, int objectWidth, int objectHeight) {
        if (x <= grid.getX()) return "left";
        if (x + objectWidth >= canvasWidth + grid.getX()) return "right";
        if (y <= grid.getY()) return "top";
        if (y + objectHeight >= canvasHeight) return "bottom";
        return null;
    }


    // Getters e Setters para as coordenadas e dimensões
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


}
