package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class TurnLeftState extends State {
    private static TurnLeftState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State nextState;

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
        requestNextState(null);
        motorControl.turnLeft();
        Button.LEDPattern(LedPattern.STATIC_RED);
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
       if (color == ColorEnum.LINE) {
            LineFollowState.getInstance()
                    .setLastSuccDir(TurnLeftState.getInstance(TurnRightState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND && (Math.abs(SensorUtils.getGyroAngle())) >= 90) {
            motorControl.turnRight();
            while ((Math.abs(SensorUtils.getGyroAngle())) < 90) {
                Delay.msDelay(5);
            }
            requestNextState(nextState);
        } else if (color == ColorEnum.MAZEMARKER) {
            motorControl.stop(true);
            requestNextState(MainMenuState.getInstance());
        } else if (SensorUtils.isTouchSonarPressed()) {
            motorControl.stop(true);
            requestNextState(ObstacleState.getInstance());
        }
        checkEnterToMainMenu();
    }

}
