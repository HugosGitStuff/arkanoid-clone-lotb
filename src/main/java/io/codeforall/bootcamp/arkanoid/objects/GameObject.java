package io.codeforall.bootcamp.arkanoid.objects;

import com.codeforall.simplegraphics.pictures.Picture;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Picture picture;

    public abstract void draw();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void delete() {}
}
