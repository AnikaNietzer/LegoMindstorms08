package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class BridgeLeftState extends State {
    private static final int THINGS_DONE_UNTIL_END = 3;
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
        motorControl.setFastSpeed();
    }

    public int thingsDone = 0;

    @Override
    public void mainLoop() {
        LCD.drawString("" + Math.abs(SensorUtils.getGyroAngle()) + " " + thingsDone, 0, 0);

        if (SensorUtils.getDistance() < BridgeStartState.BRIDGE_DISTANCE) {
            requestNextState(BridgeForwardState.getInstance());
        } else if (SensorUtils.getDistance() < BridgeStartState.BRIDGE_DISTANCE &&
                thingsDone >= THINGS_DONE_UNTIL_END && BridgeStartState.isGoingDown) {
            requestNextState(BridgeEndState.getInstance());
        }
        if (Math.abs(SensorUtils.getGyroAngle()) >= 180 && !BridgeStartState.isGoingDown) {
            BridgeStartState.isGoingDown = true;
            thingsDone = 0;
            Sound.playTone(500, 50);
        }

        checkEnterToMainMenu();
    }
}
