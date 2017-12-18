package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class ObstacleState extends State {
    private static ObstacleState instance = null;
    private static MotorControl motorControl;
    private int counter;

    private ObstacleState() {
        // States shall be used as singleton
    }

    public static ObstacleState getInstance() {
        if (instance == null) {
            instance = new ObstacleState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        requestNextState(null);
        motorControl.backwardDistance(5);
        motorControl.resetGyro();
        motorControl.turnRight();
        while (SensorUtils.getGyroAngle() < 50) {
            checkEnterToMainMenu();
            if (getNextState() == MainMenuState.getInstance()) {
                return;
            }
        };
        motorControl.forwardDistance(25);
        motorControl.turnLeft();
        while (SensorUtils.getGyroAngle() > 10) {
            checkEnterToMainMenu();
            if (getNextState() == MainMenuState.getInstance()) {
                return;
            }
        };
        motorControl.forwardDistance(36);
        motorControl.turnLeft();
        while (SensorUtils.getGyroAngle() > -70) {
            checkEnterToMainMenu();
            if (getNextState() == MainMenuState.getInstance()) {
                return;
            }
        };
        motorControl.forward();
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.LINE) {
            LineFollowState.getInstance()
                    .setLastSuccDir(TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.MAZEMARKER) {
            counter++;
            if (counter > 500) {
                motorControl.stop(true);
                requestNextState(MainMenuState.getInstance());
            }
        } else if (SensorUtils.isTouchSonarPressed()) {
            requestNextState(ObstacleState.getInstance());
        } else {
            counter = 0;
        }
        checkEnterToMainMenu();
    }

}