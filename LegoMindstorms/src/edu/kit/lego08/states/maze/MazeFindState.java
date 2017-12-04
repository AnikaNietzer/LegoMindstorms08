package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

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
        LCD.clear();
        LCD.drawString("State: Maze", 0, 5);

        motorControl.setSlowSpeed();
        motorControl.forward();
        Button.LEDPattern(LedPattern.BLINK_FAST_YELLOW);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
        motorControl.setFastSpeed();
    }

    @Override
    public void mainLoop() {
        if (SensorUtils.getColor() != ColorEnum.BACKGROUND) {
            requestNextState(MazeRightState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
