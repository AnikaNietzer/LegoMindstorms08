package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeRightState extends State {
    private static MazeRightState instance = null;
    private static MotorControl motorControl = new MotorControl();

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
        motorControl.forward();
        LCD.clear();
        LCD.drawString("State: Right", 0, 5);

        motorControl.leftTrackForward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {

        if (SensorUtils.getColor() == ColorEnum.BACKGROUND) {
            requestNextState(MazeLeftState.getInstance());
        } if (SensorUtils.getColor() == ColorEnum.BLUEMARKER) {
            Sound.playTone(100, 1000);
            requestNextState(MainMenuState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
