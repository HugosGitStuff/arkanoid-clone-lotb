package io.codeforall.bootcamp.arkanoid;

public enum BlockTypes {
    NORMAL(1, "resources/orcsBlocks/orcdimensionado.png","resources/sfx/orc_hitscream1.WAV"),
    STRONG(2, "resources/orcsBlocks/orcdimensionado2.png", "resources/sfx/orc_hitscream2.WAV"),
    UNBREAKABLE(3, "resources/orcsBlocks/orcdimensionado3.png", "resources/sfx/orc_hitscream3.WAV");

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
