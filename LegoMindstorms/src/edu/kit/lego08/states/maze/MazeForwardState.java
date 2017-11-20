package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeForwardState extends State {
    private static MazeForwardState instance = null;
    private static MotorControl motorControl = new MotorControl();

    private MazeForwardState() {
        // States shall be used as singleton
    }

    public static MazeForwardState getInstance() {
        if (instance == null) {
            instance = new MazeForwardState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        motorControl.forward();
        LCD.clear();
        LCD.drawString("State: Labyrinth", 0, 5);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();

        if (SensorUtils.isColorMarker()) {
            requestNextState(MazeScanState.getInstance());
        } else if (SensorUtils.isColorBackground()) {
            requestNextState(MainMenuState.getInstance());
            Sound.playTone(300, 200);
        }
    }
}
