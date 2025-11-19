package io.codeforall.bootcamp.arkanoid;

import java.io.IOException;

public class ArkanoidGame {

    public static void main(String[] args) {
        ArkanoidGame arkanoidGame = new ArkanoidGame();
        try {
            arkanoidGame.bootstrap();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void bootstrap() throws IOException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.execute();
    }
}


