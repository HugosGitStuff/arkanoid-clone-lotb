package io.codeforall.bootcamp.arkanoid.objects.blocks;

import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;
import io.codeforall.bootcamp.arkanoid.objects.grid.Grid;

import java.util.Arrays;

public class Blocks extends GameObject {
    private final int rows = 6;
    private final int cols = 8;
    private Block[][] blockMatrix;
    private Picture rollin;
    private Picture legolas;
    private Picture aragorn;
    private Picture kay;
    private Picture easter;
    private String imagePath;
    private int health;
    private String orcSoundPath;


    public String[] getMobVoidPlacements(int level) {
        switch (level) {
            case 1:
                return new String[]{"1.0","1.7","3.0","4.1","5.0","3.7","4.6","5.2","5.5","5.7","2.3","3.4","2.4","3.3","1.3","1.4"};
            case 2:
                return new String[]{"0.0","0.1","0.6","0.7","1.7","3.1","3.6","4.0","4.2","4.5","4.7","5.0","5.1","5.6","5.7","1.0","2.3","3.4","2.4","3.3","1.3","1.4"};
            case 3:
                return new String[]{"0.1","0.6","1.2","1.5","2.1","2.3","2.4","2.6","3.1","3.3","3.4","3.6","4.2","4.5","5.1","5.6","1.3","1.4"};
            case 4:
                return new String[]{"1.1","1.2","1.3","1.4","1.5","1.6","2.1","2.6","3.1","3.2","3.3","3.4","3.6","4.6","5.0","5.1","5.2","5.3","5.4","5.5","5.6"}; //spiral
            case 5:
                return new String[]{"0.1","0.3","0.5","0.7","1.0","1.2","1.4","1.6","2.1","2.3","2.5","2.7","3.0","3.2","3.4","3.6","4.1","4.3","4.5","4.7","5.0","5.2","5.4","5.6"}; //chess
            case 6:
                return new String[]{"1.1","1.3","1.4","1.6","2.1","2.3","2.4","2.6","3.1","3.3","3.4","3.6","4.1","4.3","4.4","4.6"};//prison
            case 7:
                return new String[]{"0.0","0.2","0.5","0.7","2.0","2.1","2.3","2.4","2.6","2.7","4.0","4.2","4.5","4.7"}; //horizontal lines and cross
            case 8:
                return new String[]{"0.3","0.4","3.0","3.7","4.0","4.1","4.6","4.7","5.0","5.1","5.2","5.5","5.6","5.7"}; //arrow
            case 9:
                return new String[]{"1.2","1.5","3.1","3.2","3.3","3.4","3.5","3.6","4.1","4.3","4.4","4.6"}; //ogre face
            case 10:
                return new String[]{"0.1","0.6","1.1","1.3","1.4","1.6","2.3","2.4","3.3","3.4","4.1","4.3","4.4","4.6","5.1","5.6"}; //chain
            case 11:
                return new String[]{"1.1","1.2","1.3","1.4","1.5","1.6","2.1","2.6","3.1","3.6","4.1","4.2","4.3","4.4","4.5","4.6"}; //box inside box
            case 12:
                return new String[]{"0.1","0.3","0.5","0.7","1.0","1.2","1.4","1.6","3.0","3.2","3.4","3.6","4.1","4.3","4.5","4.7"}; // letter A repeat
            case 13:
                return new String[]{"0.2","0.5","1.2","1.5","2.0","2.3","2.4","2.7","3.0","3.3","3.4","3.7","4.2","4.5","5.2","5.5"}; //weird double cross
        }
        return null;
    }

    public void init(Grid grid, int level) {
        String[] mobVoidPlacements = getMobVoidPlacements(level);
        setImages(level);

        this.blockMatrix = new Block[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String placement = i + "." + j;
                if (Arrays.stream(mobVoidPlacements).noneMatch(placement::equals)) {
                    setOrcs((int) (Math.random() * 3));
                    blockMatrix[i][j] = new Block(grid.rowToY(i), grid.columnToX(j), health, imagePath, orcSoundPath);
                }
            }
        }
    }

    public void setImages(int level) {
        if (level == 1) {
            rollin = new Picture(400, 115, "/characters/rollin.png");
            rollin.draw();
            legolas = new Picture(950, 380, "/avatars/legolas.png");
            legolas.draw();
            aragorn = new Picture(885, 380, "/avatars/aragorn.png");
            aragorn.draw();
        } else if (level == 2) {
            kay = new Picture(385, 115, "/characters/kay.png");
            kay.draw();

            rollin = new Picture(935, 410, "/characters/rollin.png");
            rollin.draw();
        } else if (level == 3) {
            easter = new Picture(395, 115, "/characters/easter.png");
            easter.draw();
            kay = new Picture(950, 410, "/characters/kay.png");
            kay.draw();
            rollin = new Picture(905, 410, "/characters/rollin.png");
            rollin.draw();
        } else {
            kay = new Picture(950, 410, "/characters/kay.png");
            kay.draw();
            rollin = new Picture(905, 410, "/characters/rollin.png");
            rollin.draw();
        }
    }

    public void setOrcs(int randomNum) {
        switch (randomNum) {
            case 0:
                imagePath = BlockTypes.NORMAL.getImagePath();
                health = BlockTypes.NORMAL.getHealth();
                orcSoundPath = BlockTypes.NORMAL.getOrcSoundPath();
                break;
            case 1:
                imagePath = BlockTypes.STRONG.getImagePath();
                health = BlockTypes.STRONG.getHealth();
                orcSoundPath = BlockTypes.STRONG.getOrcSoundPath();
                break;
            case 2:
                imagePath = BlockTypes.UNBREAKABLE.getImagePath();
                health = BlockTypes.UNBREAKABLE.getHealth();
                orcSoundPath = BlockTypes.UNBREAKABLE.getOrcSoundPath();
                break;
        }
    }

    public void clear() {
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
    }


    public Block[][] getBlockMatrix() {
        return blockMatrix;
    }

    public void removeBlock(int row, int col) {
        blockMatrix[row][col].delete();
        blockMatrix[row][col] = null;
    }

    @Override
    public void draw() {
    }
}
