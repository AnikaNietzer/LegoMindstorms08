package edu.kit.lego08.states;

import edu.kit.lego08.sensors.SensorUtils;
import lejos.hardware.Button;

public abstract class State {
    private State nextState = null;

    protected void requestNextState(State nextState) {
        this.nextState = nextState;
    }

    public State getNextState() {
        return nextState;
    }

    abstract public void onEnter();
    abstract public void onExit();
    abstract public void mainLoop();

    protected void checkEnterToMainMenu() {
        if (SensorUtils.isKeyPressedAndReleased(Button.ENTER)) {
            MainMenuState.getInstance().setExitedManually(true);
            requestNextState(MainMenuState.getInstance());
        } else if (SensorUtils.isKeyPressedAndReleased(Button.ESCAPE)) {
            MainMenuState.getInstance().setExitedManually(true);
            requestNextState(MainMenuState.getInstance());
        }
    }
}