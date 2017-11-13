package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class RotateState extends State {
    private static RotateState instance = null;
    private static MotorControl motorControl;
    private static int[] angles;
    private static int counter = -1;

    private RotateState() {
        // States shall be used as singleton
    }

    public static RotateState getInstance() {
        if (instance == null) {
            instance = new RotateState();
        }
        motorControl = new MotorControl();
        initializeAngles();
        return instance;
    }

    private static void initializeAngles() {
        angles = new int[10];
        angles[0] = 20;
        for (int i = 1; i < angles.length - 1; i = i + 2) {
            angles[i] = -angles[i - 1] - 20;
            angles[i + 1] = angles[i - 1] + 40;
        }
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        LCD.clear();
        LCD.drawString("Rotate", 0, 5);
    }

    @Override
    public void onExit() {
        counter = -1;
    }

    private int calcOriginalPosition() {
        if ((angles[counter] / 2) % 10 != 0) {
            return -(angles[counter] + 10) / 2;
        } else {
            return -angles[counter] / 2;
        }
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.isColorBackground() && counter < angles.length) {
            counter++;
            motorControl.turnRight(angles[counter]);
        } else if (SensorUtils.isColorBackground() && counter >= angles.length) {
            motorControl.turnRight(calcOriginalPosition());
            requestNextState(BridgeGapState.getInstance());
        } else if (SensorUtils.isColorLine()) {
            requestNextState(LineFollowState.getInstance());
        } else if (SensorUtils.isColorMarker()) {
            requestNextState(MainMenuState.getInstance());
        } else {
            throw new IllegalStateException("Color is not one of the expected colors." + SensorUtils.getColorId());
        }
        checkEnterToMainMenu();

    }
}
