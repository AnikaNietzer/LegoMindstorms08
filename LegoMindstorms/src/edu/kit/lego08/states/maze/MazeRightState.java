package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class MazeRightState extends State {
    private static MazeRightState instance = null;
    private static MotorControl motorControl = new MotorControl();
    private int blueCount = 0;

    private MazeRightState() {
        // States shall be used as singleton
    }

    public static MazeRightState getInstance() {
        if (instance == null) {
            instance = new MazeRightState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        motorControl.steerRight();
        blueCount = 0;
        Button.LEDPattern(LedPattern.STATIC_GREEN);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.BLUEMARKER) {
            blueCount++;
            if (blueCount > 50) {
                requestNextState(MainMenuState.getInstance());
            }
        } else if (color == ColorEnum.BACKGROUND) {
            blueCount = 0;
            requestNextState(MazeLeftState.getInstance());
        } else {
            blueCount = 0;
        }
        checkEnterToMainMenu();
    }
}
