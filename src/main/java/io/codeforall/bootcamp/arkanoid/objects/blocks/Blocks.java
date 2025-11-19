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
                return new String[]{"1.0", "1.7", "3.0", "4.1", "5.0", "3.7", "4.6", "5.2", "5.5", "5.7", "2.3", "3.4", "2.4", "3.3", "1.3", "1.4"};
            case 2:
                return new String[]{"0.0", "0.1", "0.6", "0.7", "1.7", "3.1", "3.6", "4.0", "4.2", "4.5", "4.7", "5.0", "5.1", "5.6", "5.7", "1.0", "2.3", "3.4", "2.4", "3.3", "1.3", "1.4"};
            case 3:
                return new String[]{"0.1", "0.6", "1.2", "1.5", "2.1", "2.3", "2.4", "2.6", "3.1", "3.3", "3.4", "3.6", "4.2", "4.5", "5.1", "5.6", "1.3", "1.4"};
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

        public void setImages ( int level){
            if (level == 1) {
                rollin = new Picture(400, 115, "/caracters/rollin.png");
                rollin.draw();
                legolas = new Picture(945, 530, "/avatars/legolas.png");
                legolas.draw();
                aragorn = new Picture(880, 530, "/avatars/aragorn.png");
                aragorn.draw();
            } else if (level == 2) {
                kay = new Picture(385, 115, "/caracters/kay.png");
                kay.draw();

                rollin = new Picture(925, 560, "/caracters/rollin.png");
                rollin.draw();
            } else if (level == 3) {
                easter = new Picture(385, 115, "/caracters/easter.png");
                easter.draw();
                kay = new Picture(935, 560, "/caracters/kay.png");
                kay.draw();
                rollin = new Picture(895, 560, "/caracters/rollin.png");
                rollin.draw();
            }
        }

        public void setOrcs (int randomNum){
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

        public void clear () {
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


        public Block[][] getBlockMatrix () {
            return blockMatrix;
        }

        public boolean removeBlock ( int row, int col){
            blockMatrix[row][col].hit();
            if (blockMatrix[row][col].isDead()) {
                blockMatrix[row][col].delete();
                blockMatrix[row][col] = null;
                return true;
            }
            return false;
        }

        @Override
        public void draw () {
        }
    }
