package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeLeftState extends State {
    private static MazeLeftState instance = null;
    private static MotorControl motorControl = new MotorControl();

    private MazeLeftState() {
        // States shall be used as singleton
    }

    public static MazeLeftState getInstance() {
        if (instance == null) {
            instance = new MazeLeftState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        LCD.clear();
        LCD.drawString("State: Left", 0, 5);

        motorControl.rightTrackForward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {

        if (SensorUtils.getColorBlue() == ColorEnum.LINE) {
            requestNextState(MazeRightState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
