package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class BridgeStartState extends State {
    private static BridgeStartState instance = null;
    private MotorControl motorControl = new MotorControl();
    static final float BRIDGE_DISTANCE = 0.12f;
    public static boolean isGoingDown = false;

    private BridgeStartState() {
        // States shall be used as singleton
    }

    public static BridgeStartState getInstance() {
        if (instance == null) {
            instance = new BridgeStartState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        motorControl.setSlowSpeed();
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        motorControl.turnLeftAndWait(190);
        SensorUtils.resetGyro();
        motorControl.backwardDistance(25);
        isGoingDown = false;
        BridgeLeftState.getInstance().thingsDone = 0;
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        requestNextState(BridgeRightState.getInstance());
        checkEnterToMainMenu();
    }
}
