package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class BridgeRightState extends State {
    private static BridgeRightState instance = null;
    private MotorControl motorControl = new MotorControl();

    private BridgeRightState() {
        // States shall be used as singleton
    }

    public static BridgeRightState getInstance() {
        if (instance == null) {
            instance = new BridgeRightState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state

        LCD.clear();
        LCD.drawString("State: Bruecke", 0, 5);
        motorControl.steerRightBackward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getDistance() > BridgeLeftState.BRIDGE_DISTANCE) {
            requestNextState(BridgeLeftState.getInstance());
        }
        checkEnterToMainMenu();
    }
}