package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;

public class LineFollowState extends State {
    private static LineFollowState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State lastSuccState = TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance()));
    private int counter;

    private LineFollowState() {
        // States shall be used as singleton
    }

    public static LineFollowState getInstance() {
        if (instance == null) {
            instance = new LineFollowState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        motorControl.forward();
        counter = 0;
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.BACKGROUND) {
            requestNextState(lastSuccState);
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

    public void setLastSuccDir(State state) {
        this.lastSuccState = state;
    }
}
