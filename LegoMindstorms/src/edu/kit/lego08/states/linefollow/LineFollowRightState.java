package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class LineFollowRightState extends State {
    private static LineFollowRightState instance = null;

    private LineFollowRightState() {
        // States shall be used as singleton
    }

    public static LineFollowRightState getInstance() {
        if (instance == null) {
            instance = new LineFollowRightState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        //TODO: Move right
    }

    @Override
    public void onExit() {
        //TODO: Stop moving right
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("Linienfolgen: Rechts", 0, 5);

        if (SensorUtils.isColorBlack()) {
            requestNextState(LineFollowLeftState.getInstance());
        }
    }
}
