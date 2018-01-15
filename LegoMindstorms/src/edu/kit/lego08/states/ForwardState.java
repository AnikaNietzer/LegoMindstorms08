package edu.kit.lego08.states;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class ForwardState extends State {
    private static ForwardState instance = null;
    private MotorControl motorControl = new MotorControl();

    private ForwardState() {
        // States shall be used as singleton
    }

    public static ForwardState getInstance() {
        if (instance == null) {
            instance = new ForwardState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        motorControl.turnRightAndWait(20);
        motorControl.forward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getColor() == ColorEnum.BLUEMARKER) {
            requestNextState(MainMenuState.getInstance());
        } else if (SensorUtils.isTouchPressed()) {
            motorControl.turnLeftAndWait(30);
            motorControl.forward();
        }
        checkEnterToMainMenu();
    }
}
