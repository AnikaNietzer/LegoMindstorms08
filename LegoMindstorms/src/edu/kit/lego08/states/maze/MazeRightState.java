package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;

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
        motorControl.leftTrackForward();
        blueCount = 0;
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
                Sound.playTone(100, 1000);
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
