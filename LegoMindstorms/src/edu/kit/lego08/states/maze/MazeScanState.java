package edu.kit.lego08.states.maze;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
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
        LCD.drawString("State: Maze scan", 0, 5);

        measure();
    }

    private void measure() {
        int rotateAngle = 90;

        motorControl.forwardDistance(7);
        hasForward = SensorUtils.getColor() == ColorEnum.LINE || SensorUtils.getColor() == ColorEnum.MAZEMARKER;


        Sound.playTone(600 + (hasForward ? 100 : 0), 100);

        motorControl.turnLeftAndWait(rotateAngle);
        hasLeft = SensorUtils.getColor() == ColorEnum.LINE || SensorUtils.getColor() == ColorEnum.MAZEMARKER;
        Sound.playTone(600 + (hasLeft ? 100 : 0), 100);

        motorControl.turnRightAndWait(2 * rotateAngle);
        hasRight = SensorUtils.getColor() == ColorEnum.LINE || SensorUtils.getColor() == ColorEnum.MAZEMARKER;

    
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        if (hasRight) {
            motorControl.turnRightAndWait(90);
            requestNextState(MazeRightState.getInstance());
        } else if (hasForward) {
            requestNextState(MazeRightState.getInstance());
        } else if (hasLeft) {
            motorControl.turnLeftAndWait(90);
            requestNextState(MazeRightState.getInstance());
        } else {
            motorControl.turnRightAndWait(180);
            requestNextState(MazeRightState.getInstance());
        }
        checkEnterToMainMenu();
    }
}
