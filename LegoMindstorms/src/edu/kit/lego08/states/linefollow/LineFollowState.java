package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;


public class LineFollowState extends State {
    private static LineFollowState instance = null;
    private MotorControl motorControl = new MotorControl();

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
    }

    @Override
    public void onExit() {
        //TODO: Stop moving left
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("LineFollow", 0, 5);

        if (!SensorUtils.isColorBlack()) {
            motorControl.forward(1000);
        } else {
            requestNextState(TurnRightState.getInstance());
        }
        
    }
}
