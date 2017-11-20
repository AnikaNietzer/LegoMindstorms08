package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeScanState extends State {
    private static MazeScanState instance = null;
    private static MotorControl motorControl = new MotorControl();

    private MazeScanState() {
        // States shall be used as singleton
    }

    public static MazeScanState getInstance() {
        if (instance == null) {
            instance = new MazeScanState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        LCD.clear();
        LCD.drawString("State: Scan", 0, 5);

        motorControl.forwardTimed(100, false);
        Sound.playTone(600, 100);
        motorControl.turnLeft(90);
        Sound.playTone(600, 100);
        motorControl.turnLeft(180);
        Sound.playTone(600, 100);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();

        requestNextState(MainMenuState.getInstance());
    }
}
