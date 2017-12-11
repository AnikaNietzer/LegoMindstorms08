package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class BridgeLeftState extends State {
    private static BridgeLeftState instance = null;
    private MotorControl motorControl = new MotorControl();
    static final float BRIDGE_DISTANCE = 0.1f;

    private BridgeLeftState() {
        // States shall be used as singleton
    }

    public static BridgeLeftState getInstance() {
        if (instance == null) {
            instance = new BridgeLeftState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        motorControl.setSlowSpeed();
        motorControl.steerLeftBackward();
        Button.LEDPattern(LedPattern.STATIC_RED);
    }

    @Override
    public void onExit() {
        motorControl.backwardDistance(2);
        motorControl.stop(true);
        motorControl.setFastSpeed();
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getDistance() < BRIDGE_DISTANCE) {
            requestNextState(BridgeRightState.getInstance());
        }

        checkEnterToMainMenu();
    }
}
