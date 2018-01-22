package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class GapState extends State {
    private static GapState instance = null;
    private static MotorControl motorControl;
    private int counter;
    private int distance;
    private int stateCount;

    private GapState() {
        // States shall be used as singleton
    }

    public static GapState getInstance() {
        if (instance == null) {
            instance = new GapState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        stateCount = 0;
        counter = 0;
        distance = 11;
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        LCD.drawInt((int) SensorUtils.getGyroAngle(), 0, 5);
        if (color == ColorEnum.LINE) {
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND && (!motorControl.isMoving() || Math.abs(SensorUtils.getGyroAngle()) > 70)) {
        	motorControl.stop(true);
            motorControl.stop(true);
            newMove();
        } else if (color == ColorEnum.MAZEMARKER) {
            counter++;
            if (counter > 500) {
                motorControl.stop(true);
                requestNextState(MainMenuState.getInstance());
            }
        } else {
            counter = 0;
        }
        checkEnterToMainMenu();
    }

    private void newMove() {
        switch (stateCount%5) {
        case 0:
        	stateCount++;
            motorControl.forwardDistance(distance);
            distance = 14;
            break;
        case 1:
        	stateCount++;
            motorControl.resetGyro();
            motorControl.turnLeft();
            break;
        case 2:
        	stateCount++;
            motorControl.turnRight();
            while(SensorUtils.getGyroAngle() < -10) {
                checkEnterToMainMenu();
                if (getNextState() == MainMenuState.getInstance()) {
                    return;
                }
            }
            motorControl.stop(true);
            break;
        case 3:
        	stateCount++;
            motorControl.resetGyro();
            motorControl.turnRight();
            break;
        case 4:
        	stateCount++;
            motorControl.turnLeft();
            while(SensorUtils.getGyroAngle() > 0) {
                checkEnterToMainMenu();
                if (getNextState() == MainMenuState.getInstance()) {
                    return;
                }
            }
            motorControl.stop(true);
            break;
        }
    }

}