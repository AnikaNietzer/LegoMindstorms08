package edu.kit.lego08.states;

import lejos.hardware.lcd.LCD;

public class MazeState extends State {
    private static MazeState instance = null;

    private MazeState() {
        // States shall be used as singleton
    }

    public static MazeState getInstance() {
        if (instance == null) {
            instance = new MazeState();
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
        LCD.drawString("State: Labyrinth", 0, 5);
    }
}
