package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class TurnRightState extends State {
    private static TurnRightState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State nextState;

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
        LCD.clear();
        LCD.drawString("Turn Right", 0, 5);
        motorControl.turnRight(90);
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
                    .setLastSuccDir(TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance())));
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND && !motorControl.isMoving()) {
            motorControl.turnLeft(90);
            while (motorControl.isMoving()) {
                Delay.msDelay(5);
            }
            requestNextState(nextState);
        } else if (color == ColorEnum.MARKER) {
            motorControl.stop(true);
            requestNextState(MainMenuState.getInstance());
        }
        checkEnterToMainMenu();
    }

}
