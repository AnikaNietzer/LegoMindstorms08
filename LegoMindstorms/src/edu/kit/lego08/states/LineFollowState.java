package edu.kit.lego08.states;

import lejos.hardware.lcd.LCD;

public class LineFollowState extends State {
    private static LineFollowState instance = null;

    private LineFollowState() {
        // States shall be used as singleton
    }

    public static LineFollowState getInstance() {
        if (instance == null) {
            instance = new LineFollowState();
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
        LCD.drawString("State: Linienfolgen", 0, 5);
    }
}
