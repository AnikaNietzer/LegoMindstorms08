package edu.kit.lego08.states;

import lejos.hardware.lcd.LCD;

public class MoveObjectsState extends State {
    private static MoveObjectsState instance = null;

    private MoveObjectsState() {
        // States shall be used as singleton
    }

    public static MoveObjectsState getInstance() {
        if (instance == null) {
            instance = new MoveObjectsState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("State: Hindernis verschieben", 0, 5);
    }
}
