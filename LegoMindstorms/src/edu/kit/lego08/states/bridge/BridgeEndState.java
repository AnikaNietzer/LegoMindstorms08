package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class BridgeEndState extends State {
    private static BridgeEndState instance = null;
    private MotorControl motorControl = new MotorControl();

    private BridgeEndState() {
        // States shall be used as singleton
    }

    public static BridgeEndState getInstance() {
        if (instance == null) {
            instance = new BridgeEndState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        motorControl.setSlowSpeed();
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        motorControl.turnRightAndWait(20);
        motorControl.backward();
    }

    @Override
    public void onExit() {
        motorControl.setFastSpeed();
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getColor() == ColorEnum.BLUEMARKER) {
            requestNextState(MainMenuState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
