package edu.kit.lego08.states;

import edu.kit.lego08.sensors.SensorUtils;
import lejos.hardware.lcd.LCD;

public class TestState extends State {
    private static TestState instance = null;

    private TestState() {
        // States shall be used as singleton
    }

    public static TestState getInstance() {
        if (instance == null) {
            instance = new TestState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        LCD.clear();
        LCD.drawString("State: Bruecke", 0, 5);
    }

    @Override
    public void onExit() {
    }

    @Override
    public void mainLoop() {
        LCD.drawInt(SensorUtils.getColorId(), 0, 3);
        checkEnterToMainMenu();
    }
}
