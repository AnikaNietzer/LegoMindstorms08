package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class TurnLeftState extends State {
    private static TurnLeftState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State nextState;
    private int counter;

    private TurnLeftState() {
        // States shall be used as singleton
    }

    public static TurnLeftState getInstance(State state) {
        if (instance == null) {
            instance = new TurnLeftState();
        }
        instance.nextState = state;
        return instance;
    }

    @Override
    public void onEnter() {
        counter = 0;
        requestNextState(null);
        motorControl.resetGyro();
        motorControl.turnLeft();
        Button.LEDPattern(LedPattern.STATIC_RED);
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
                    .setLastSuccDir(TurnLeftState.getInstance(TurnRightState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND && SensorUtils.getGyroAngle() < -80) {
            motorControl.turnRight();
            while (SensorUtils.getGyroAngle() < -15) {
                checkEnterToMainMenu();
                if (getNextState() == MainMenuState.getInstance()) {
                    return;
                }
            }
            requestNextState(nextState);
        } else if (color == ColorEnum.MAZEMARKER) {
            counter++;
            if (counter > 500) {
                motorControl.stop(true);
                requestNextState(MainMenuState.getInstance());
            }
        } else if (SensorUtils.isTouchSonarPressed()) {
            motorControl.stop(true);
            requestNextState(ObstacleState.getInstance());
        } else {
            counter = 0;
        }
        checkEnterToMainMenu();
    }

}
