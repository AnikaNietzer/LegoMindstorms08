package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;
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
        LCD.clear();
        LCD.drawString("Turn Left", 0, 5);
        motorControl.turnLeft(90);
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.BACKGROUND && motorControl.isMoving()) {

        } else if (color == ColorEnum.LINE) {
            LineFollowState.getInstance()
                    .setLastSuccDir(TurnLeftState.getInstance(TurnRightState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND && !motorControl.isMoving()) {
            motorControl.turnRight(90);
            while (motorControl.isMoving()) {
                Delay.msDelay(5);
            }
            requestNextState(nextState);
        } else if (color == ColorEnum.BLUEMARKER) {
            motorControl.stop(true);
            requestNextState(MainMenuState.getInstance());
        } else if (SensorUtils.isTouchSonarPressed()) {
            ObstacleState.getInstance();
        }
        checkEnterToMainMenu();
    }

}
