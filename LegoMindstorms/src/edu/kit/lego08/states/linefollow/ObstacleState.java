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
        motorControl.turnRight();
        waitForStop();
        motorControl.forwardDistance(20);
        motorControl.turnLeft();
        waitForStop();
        motorControl.forwardDistance(50);
        motorControl.turnLeft();
        waitForStop();
        motorControl.forward();
    }

    private void waitForStop() {
        while ((Math.abs(SensorUtils.getGyroAngle())) < 90) {
            if (SensorUtils.isTouchSonarPressed()) {
                requestNextState(ObstacleState.getInstance());
            }
        }
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.LINE) {
            LineFollowState.getInstance()
                    .setLastSuccDir(TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.MAZEMARKER) {
            motorControl.stop(true);
            requestNextState(MainMenuState.getInstance());
        } else if (SensorUtils.isTouchSonarPressed()) {
            requestNextState(ObstacleState.getInstance());
        }
        checkEnterToMainMenu();
    }

}