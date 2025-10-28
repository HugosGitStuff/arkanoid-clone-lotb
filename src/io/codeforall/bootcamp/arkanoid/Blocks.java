package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.pictures.Picture;

import java.util.HashMap;

public class Blocks extends GameObject {
    private Block[][] blockMatrix;
    private int rows = 6;
    private int cols = 8;
    private Ball ball;
    private Picture rollin;
    private Picture legolas;
    private Picture aragorn;
    private Picture kay;
    private Picture easter;
    private HashMap<Integer, String[]> mobLevels;

    public void setMobLevels() {
        mobLevels = new HashMap<>();
        mobLevels.put(1, new String[]{"1.0", "1.7", "3.0", "4.1", "5.0", "3.7", "4.6", "5.2", "5.5", "5.7", "2.3", "3.4", "2.4", "3.3", "1.3", "1.4" });
        mobLevels.put(2, new String[]{"0.0", "0.1", "0.6", "0.7", "1.7", "3.1", "3.6", "4.0", "4.2", "4.5", "4.7", "5.0", "5.1", "5.6", "5.7", "1.0", "2.3", "3.4", "2.4", "3.3", "1.3", "1.4" });
        mobLevels.put(3, new String[]{"0.1", "0.6", "1.2", "1.5", "2.1", "2.3", "2.4", "2.6", "3.1", "3.3", "3.4", "3.6", "4.2", "4.5", "5.1", "5.6", "1.3", "1.4" });
    }
    //    // Matrix
    public Blocks(Grid grid, Ball ball, int level) {
        this.ball = ball;
        int health = 0;
        setMobLevels();

        if ( level == 1){
        rollin = new Picture(400, 115, "resources/caracters/rollin.png");
        rollin.draw();
        legolas = new Picture(945, 530, "resources/avatars/legolas.png");
        legolas.draw();
        aragorn = new Picture(880, 530, "resources/avatars/aragorn.png");
        aragorn.draw();
        }else if (level == 2){
            kay = new Picture(385, 115, "resources/caracters/kay.png");
        kay.draw();

        rollin = new Picture(925, 560, "resources/caracters/rollin.png");
        rollin.draw();
        } else if ( level == 3){
            easter = new Picture(385, 115, "resources/caracters/easter.png");
        easter.draw();
        kay = new Picture(935, 560, "resources/caracters/kay.png");
        kay.draw();
        rollin = new Picture(895, 560, "resources/caracters/rollin.png");
        rollin.draw();
        }

        String imagepath = "";
        String orcSoundPath = "";
        this.blockMatrix = new Block[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean isEmpty = false;
                for (int k = 0; k < mobLevels.get(level).length; k++) {
                    String placement = new String(i + "." + j);
                    if (placement.equals(mobLevels.get(level)[k])) {
                        isEmpty = true;
                        break;
                    }
                }
                if (isEmpty) {

                } else {
                    int randomNum = (int) (Math.random() * 3);
                    switch (randomNum) {
                        case 0:
                            imagepath = BlockTypes.NORMAL.getImagePath();
                            health = BlockTypes.NORMAL.getHealth();
                            orcSoundPath = BlockTypes.NORMAL.getOrcSoundPath();
                            break;
                        case 1:
                            imagepath = BlockTypes.STRONG.getImagePath();
                            health = BlockTypes.STRONG.getHealth();
                            orcSoundPath = BlockTypes.STRONG.getOrcSoundPath();
                            break;
                        case 2:
                            imagepath = BlockTypes.UNBREAKABLE.getImagePath();
                            health = BlockTypes.UNBREAKABLE.getHealth();
                            orcSoundPath = BlockTypes.UNBREAKABLE.getOrcSoundPath();
                    }
                    blockMatrix[i][j] = new Block(grid.rowToY(i), grid.columnToX(j), health, imagepath, orcSoundPath);
                }
            }
        }
    }

    public Block[][] clear() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (blockMatrix[i][j] != null) {
                    blockMatrix[i][j].delete();
                    blockMatrix[i][j] = null;
                }
            }
        }
        if (aragorn != null) {
            aragorn.delete();
        }
        if (legolas != null) {
            legolas.delete();
        }
        rollin.delete();
        if (kay != null) {
            kay.delete();
        }
        if (easter != null) {
            easter.delete();
        }
        return blockMatrix;
    }


    public Block[][] getBlockMatrix() {
        return blockMatrix;
    }

    public boolean removeBlock(int row, int col) {
        blockMatrix[row][col].hit();
        if (blockMatrix[row][col].isDead()) {
            blockMatrix[row][col].delete();
            blockMatrix[row][col] = null;
            return true;
        }
        return false;
    }

    @Override
    public void draw() {
    }
}
