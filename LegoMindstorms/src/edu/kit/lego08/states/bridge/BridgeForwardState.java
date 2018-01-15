package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;

public class BridgeForwardState extends State {
    private static final int TIME_FORWARD_MAX = 1500;
    private static BridgeForwardState instance = null;
    private MotorControl motorControl = new MotorControl();
    private long timeEntered = 0;

    private BridgeForwardState() {
        // States shall be used as singleton
    }

    public static BridgeForwardState getInstance() {
        if (instance == null) {
            instance = new BridgeForwardState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        motorControl.setSlowSpeed();
        timeEntered = System.currentTimeMillis();
        motorControl.backward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
        motorControl.setFastSpeed();
    }

    @Override
    public void mainLoop() {
        if (timeEntered + TIME_FORWARD_MAX < System.currentTimeMillis() ||
                SensorUtils.getDistance() > BridgeStartState.BRIDGE_DISTANCE) {
            motorControl.stop(true);
            requestNextState(BridgeRightState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
