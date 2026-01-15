package io.codeforall.bootcamp.arkanoid.objects.blocks;

import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;
import io.codeforall.bootcamp.arkanoid.objects.GameObject;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;

public class Block extends GameObject {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Picture picture;
    private final Rectangle hitBox;
    private int health;
    private boolean dead;
    private final int maxHealth;
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
        System.out.println("Took a hit!");
        if (health <= 0){
            dead = true;
            System.out.println("Dead!");
            try {

                URL file = getClass().getResource("/sfx/ork_dyingsound.WAV");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file.openStream()));

                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Error playing music: " + e.getMessage());
            }
        }
        else{
            try {

                URL file = getClass().getResource(orcSoundPath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file.openStream()));

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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
