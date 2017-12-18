package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class MazeFindState extends State {
    private static MazeFindState instance = null;
    private static MotorControl motorControl = new MotorControl();

    private MazeFindState() {
        // States shall be used as singleton
    }

    public static MazeFindState getInstance() {
        if (instance == null) {
            instance = new MazeFindState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        motorControl.setSlowSpeed();
        motorControl.forward();
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
        motorControl.forwardDistance(10);
        motorControl.turnLeftAndWait(90);
        motorControl.setFastSpeed();
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getColor() == ColorEnum.LINE) {
            requestNextState(MazeRightState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
