package edu.kit.lego08.states;

import edu.kit.lego08.motor_control.MotorControl;
import lejos.hardware.lcd.LCD;

public class MazeState extends State {
    private static MazeState instance = null;

    private MazeState() {
        // States shall be used as singleton
    }

    public static MazeState getInstance() {
        if (instance == null) {
            instance = new MazeState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        MotorControl m = new MotorControl();
        m.forwardTimed(3000, true);
        m.turnRight(1080);
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("State: Labyrinth", 0, 5);
    }
}
