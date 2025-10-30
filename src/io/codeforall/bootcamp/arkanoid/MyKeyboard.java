package io.codeforall.bootcamp.arkanoid;

import com.codeforall.simplegraphics.keyboard.Keyboard;
import com.codeforall.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.simplegraphics.keyboard.KeyboardHandler;

public class MyKeyboard implements KeyboardHandler {

    private Keyboard keyboard;
    private Paddle paddle;

    public void init() {

        keyboard = new Keyboard(this);

        //set up the event
        KeyboardEvent goRight = new KeyboardEvent();
        KeyboardEvent goLeft = new KeyboardEvent();
        KeyboardEvent start = new KeyboardEvent();
        KeyboardEvent quit = new KeyboardEvent();
        KeyboardEvent rightRelease = new KeyboardEvent();
        KeyboardEvent leftRelease = new KeyboardEvent();


        //set the key to the event
        //Right
        goRight.setKey(KeyboardEvent.KEY_RIGHT);
        goRight.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        // Left
        goLeft.setKey(KeyboardEvent.KEY_LEFT);
        goLeft.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        start.setKey(KeyboardEvent.KEY_1);
        start.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        quit.setKey(KeyboardEvent.KEY_2);
        quit.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        rightRelease.setKey(KeyboardEvent.KEY_RIGHT);
        rightRelease.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

        leftRelease.setKey(KeyboardEvent.KEY_LEFT);
        leftRelease.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);


        //add event to the keyboard
        keyboard.addEventListener(goRight);
        keyboard.addEventListener(goLeft);
        keyboard.addEventListener(start);
        keyboard.addEventListener(quit);
        keyboard.addEventListener(rightRelease);
        keyboard.addEventListener(leftRelease);


    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT) {

            paddle.setState(PaddleState.MOVING_RIGHT);

        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT) {

            paddle.setState(PaddleState.MOVING_LEFT);

        } else if (keyboardEvent.getKey() == KeyboardEvent.KEY_1){

            //inserir metodo de dar restart ao jogo

        }else if (keyboardEvent.getKey() == KeyboardEvent.KEY_2){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);

        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (!(keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT) || !(keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT)) {
            paddle.setState(PaddleState.NOT_MOVING);
        }
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
}
