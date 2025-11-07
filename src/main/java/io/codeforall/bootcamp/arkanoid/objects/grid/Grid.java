package io.codeforall.bootcamp.arkanoid.objects.grid;


import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;

public class Grid {

    public static final int PADDING_LEFT = 56;
    public static final int PADDING_TOP = 44;

    private final int cellSizeX = 100;
    private final int cellSizeY = 67;
    private int cols;
    private int rows;

    private Rectangle field;


    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void init() {
        this.field = new Rectangle(PADDING_LEFT, PADDING_TOP, cols * cellSizeX, rows * cellSizeY);
        this.field.setColor(Color.WHITE);
        //this.field.draw();
    }

    public int getWidth() {
        return this.field.getWidth();
    }

    public int getHeight() {
        return this.field.getHeight();
    }

    public int getX() {
        return this.field.getX();
    }

    public int getY() {
        return this.field.getY();
    }

    public int rowToY(int row) {
        return PADDING_TOP + cellSizeY * row;
    }

    public int columnToX(int column) {
        return PADDING_LEFT + cellSizeX * column;
    }


}
