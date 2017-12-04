package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class ObstacleState extends State {
    private static ObstacleState instance = null;
    private static MotorControl motorControl;
    int stateCounter = 0;

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
        requestNextState(null);
        LCD.clear();
        LCD.drawString("Break through obstacle", 0, 5);
        motorControl.backwardTimed(500, true);
        motorControl.turnRight();
        waitForStop();
        motorControl.forwardTimed(2000, true);
        motorControl.turnLeft();
        waitForStop();
        motorControl.forwardTimed(4000, true);
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
        stateCounter = 0;
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