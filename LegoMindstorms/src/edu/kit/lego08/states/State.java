package edu.kit.lego08.states;

import lejos.hardware.Button;

public abstract class State {
    private State nextState = null;

    void requestNextState(State nextState) {
        this.nextState = nextState;
    }

    public State getNextState() {
        return nextState;
    }

    abstract public void onEnter();
    abstract public void onExit();
    abstract public void mainLoop();

    void checkEnterToMainMenu() {
        if (Button.ENTER.isDown()) {
            requestNextState(MainMenuState.getInstance());
        }
    }
}