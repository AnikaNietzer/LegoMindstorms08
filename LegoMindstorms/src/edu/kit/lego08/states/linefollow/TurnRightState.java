package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class TurnRightState extends State {
    private static TurnRightState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State nextState;
    private int counter;

    private TurnRightState() {
        // States shall be used as singleton
    }

    public static TurnRightState getInstance(State state) {
        if (instance == null) {
            instance = new TurnRightState();
        }
        instance.nextState = state;
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        motorControl.resetGyro();
        motorControl.turnRight();
        Button.LEDPattern(LedPattern.STATIC_GREEN);
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
        } else if (color == ColorEnum.BACKGROUND && SensorUtils.getGyroAngle() > 85) {
            motorControl.turnLeft();
            while (SensorUtils.getGyroAngle() > 5) {
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
        } else if (SensorUtils.isTouchPressed()) {
            motorControl.stop(true);
            requestNextState(ObstacleState.getInstance());
        } else {
            counter = 0;
        }
        checkEnterToMainMenu();
    }

}
