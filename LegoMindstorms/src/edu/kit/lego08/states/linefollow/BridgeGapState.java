package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class BridgeGapState extends State {
    private static BridgeGapState instance = null;
    private static MotorControl motorControl;
    int stateCounter = 0;

    private BridgeGapState() {
        // States shall be used as singleton
    }

    public static BridgeGapState getInstance() {
        if (instance == null) {
            instance = new BridgeGapState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        LCD.clear();
        LCD.drawString("Bride Gap", 0, 5);
    }

    @Override
    public void onExit() {
        stateCounter = 0;
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.isColorBackground()) {
            move();
            stateCounter++;
        } else if (SensorUtils.isColorLine()) {
            requestNextState(LineFollowState.getInstance());
        } else if (SensorUtils.isColorMarker()) {
            requestNextState(MainMenuState.getInstance());
        } else {
            throw new IllegalStateException("Color is not one of the expected colors" + SensorUtils.getColorId());
        }
        checkEnterToMainMenu();
    }

    private void move() {
        switch (stateCounter % 4) {
        case 0:
            motorControl.forward(1000);
            break;
        case 1:
            motorControl.turnRight(10);
            break;
        case 2:
            motorControl.turnLeft(20);
            break;
        case 3:
            motorControl.turnRight(10);
            break;
        }
    }
}
