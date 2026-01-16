package io.codeforall.bootcamp.arkanoid.objects.blocks;

public enum BlockTypes {
    NORMAL(1, "/orcs/orc1.png","/sfx/orc_hitscream1.WAV"),
    STRONG(2, "/orcs/orc2.png", "/sfx/orcScream1.WAV"),
    UNBREAKABLE(3, "/orcs/orc3.png", "/sfx/orcScream2.WAV");

    private int health;
    private String imagePath;
    private String orcSoundPath;

    BlockTypes(int health, String imagePath, String orcSoundPath) {
        this.health = health;
        this.imagePath = imagePath;
        this.orcSoundPath = orcSoundPath;
    }

    public int getHealth() {
        return health;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getOrcSoundPath() {
        return orcSoundPath;
    }
}
