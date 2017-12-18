package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class BridgeLeftState extends State {
    private static BridgeLeftState instance = null;
    private MotorControl motorControl = new MotorControl();

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
        thingsDone++;
    }

    @Override
    public void onExit() {
        motorControl.backwardDistance(2);
        motorControl.stop(true);
        motorControl.setFastSpeed();
    }

    public int thingsDone = 0;

    @Override
    public void mainLoop() {
        if (SensorUtils.getDistance() < BridgeStartState.BRIDGE_DISTANCE) {
            requestNextState(BridgeRightState.getInstance());
        } else if (SensorUtils.getDistance() < BridgeStartState.BRIDGE_DISTANCE &&
                thingsDone >= 10 && BridgeStartState.isGoingDown) {
            requestNextState(BridgeEndState.getInstance());
        }
        if (Math.abs(SensorUtils.getGyroAngle()) >= 180 && !BridgeStartState.isGoingDown) {
            BridgeStartState.isGoingDown = true;
            thingsDone = 0;
            Sound.playTone(500, 10);
        }

        checkEnterToMainMenu();
    }
}
