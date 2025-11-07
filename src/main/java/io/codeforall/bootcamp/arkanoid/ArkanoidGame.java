package io.codeforall.bootcamp.arkanoid;

public class ArkanoidGame {

    public static void main(String[] args) {
        ArkanoidGame arkanoidGame = new ArkanoidGame();

        arkanoidGame.bootstrap();
    }


    public void bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.execute();
    }
//
//    public void gameOver() {
//        screenAddon.gameOverText();
//        screenAddon.pressToExit();
//    }
}


