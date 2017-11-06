package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motors.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class TurnLeftState extends State {
    private static TurnLeftState instance = null;
    private static MotorControl motorControl;
    private int angle = 0;

    private TurnLeftState() {
        // States shall be used as singleton
    }

    public static TurnLeftState getInstance() {
        if (instance == null) {
            instance = new TurnLeftState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        // TODO: Move left
    }

    @Override
    public void onExit() {
        // TODO: Stop moving left
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("Turn Left", 0, 5);

        if (SensorUtils.isColorBlack() && angle < 100) {
            motorControl.turnLeft(5);
            angle += 5;
        } else if (!SensorUtils.isColorBlack()) {
            requestNextState(LineFollowState.getInstance());
        } else {
            motorControl.turnRight(angle);
            requestNextState(BridgeGapState.getInstance());
        }
    }
}
