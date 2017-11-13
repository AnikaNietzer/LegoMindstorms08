package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class LineFollowState extends State {
    private static LineFollowState instance = null;
    private MotorControl motorControl = new MotorControl();

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
        requestNextState(null);
        LCD.clear();
        LCD.drawString("Follow Line", 0, 5);
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        if (SensorUtils.isColorLine()) {
            motorControl.forward(500);
        } else if (SensorUtils.isColorBackground()) {
            requestNextState(RotateState.getInstance());
        } else if (SensorUtils.isColorMarker()) {
            requestNextState(MainMenuState.getInstance());
        } else {
            throw new IllegalStateException("Color is not one of the expected colors" + SensorUtils.getColorId());
        }
        checkEnterToMainMenu();
    }
}
