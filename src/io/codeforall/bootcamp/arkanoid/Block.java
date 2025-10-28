package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Block extends GameObject{

    private int x;
    private int y;
    private int width;
    private int height;
    private Picture picture;
    private Rectangle hitBox;
    private int health;
    private boolean dead;
    private int maxHealth;
    Clip musicClip;
    String orcSoundPath;

    public Block(int y, int x, int health, String imagePath, String orcSoundPath) {
        this.orcSoundPath = orcSoundPath;
        dead = false;
        maxHealth = health;
        this.health =health;
        this.x = x;
        this.y = y;
        this.picture = new Picture(this.x, this.y, imagePath);
        hitBox = new Rectangle(this.x, this.y, picture.getWidth(), picture.getHeight());
        this.width = picture.getWidth();
        this.height = picture.getHeight();
        draw();
    }

    public void hit()  {
        health--;
        if (health <= 0){
            dead = true;
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("resources/sfx/ork_dyingsound.WAV"));
                //AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Error playing music: " + e.getMessage());
            }
        }
        else{
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(orcSoundPath));
                //AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Error playing music: " + e.getMessage());
            }
        }

    }

    public boolean isDead() {
        return dead;
    }

    public void delete() {
        picture.delete();
        hitBox.delete();
    }

    public void draw() {
        picture.draw();
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
