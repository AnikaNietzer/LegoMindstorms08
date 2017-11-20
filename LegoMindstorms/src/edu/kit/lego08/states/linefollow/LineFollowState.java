package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class LineFollowState extends State {
    private static LineFollowState instance = null;
    private MotorControl motorControl = new MotorControl();
    private State lastSuccState = TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance()));
    
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
        LCD.clear();
        LCD.drawString("Follow Line", 0, 5);
        motorControl.forward();
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.LINE) {

        } else if (color == ColorEnum.BACKGROUND) {
            requestNextState(lastSuccState);
        } else if (color == ColorEnum.MARKER) {
            requestNextState(MainMenuState.getInstance());
        }
        checkEnterToMainMenu();
    }
    
    public void setLastSuccDir(State state) {
        this.lastSuccState = state;
    }
}
