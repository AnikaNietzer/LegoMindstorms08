package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MazeScanState extends State {
    private static MazeScanState instance = null;
    private static MotorControl motorControl = new MotorControl();
    private boolean hasRight, hasLeft, hasForward;

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

        measure();
    }

    private void measure() {
        int rotateAngle = 70;

        motorControl.forwardTimed(500, true);
        hasForward = SensorUtils.isColorLine() || SensorUtils.isColorMarker();
        Sound.playTone(600 + (hasForward?100:0), 100);

        motorControl.turnLeftAndWait(rotateAngle);
        hasLeft = SensorUtils.isColorLine() || SensorUtils.isColorMarker();
        Sound.playTone(600 + (hasLeft?100:0), 100);

        motorControl.turnRightAndWait(2*rotateAngle);
        hasRight = SensorUtils.isColorLine() || SensorUtils.isColorMarker();
        Sound.playTone(600 + (hasRight?100:0), 100);
        motorControl.turnLeftAndWait(rotateAngle);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        if (hasRight) {
            motorControl.turnRightAndWait(90);
            requestNextState(MazeForwardState.getInstance());
        } else if (hasForward) {
            requestNextState(MazeForwardState.getInstance());
        } else if (hasLeft) {
            motorControl.turnLeftAndWait(90);
            requestNextState(MazeForwardState.getInstance());
        } else {
            motorControl.turnRightAndWait(180);
            requestNextState(MazeForwardState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
