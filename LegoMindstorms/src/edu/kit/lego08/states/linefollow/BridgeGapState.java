package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class BridgeGapState extends State {
    private static BridgeGapState instance = null;
    private static MotorControl motorControl;
    int stateCounter = 0;

    private BridgeGapState() {
        // States shall be used as singleton
    }

    public static BridgeGapState getInstance() {
        if (instance == null) {
            instance = new BridgeGapState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        //TODO: Move left
    }

    @Override
    public void onExit() {
        //TODO: Stop moving left
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("Bride Gap", 0, 5);

        if (SensorUtils.isColorBlack()) {
            move();
            stateCounter ++;
        } else {
            requestNextState(LineFollowState.getInstance());
        }
    }
    
    private void move() {
        switch(stateCounter % 6) {
        
        case 0:
            motorControl.forward(2000);
            break;
        case 1:
            motorControl.turnRight(5);
            break;
        case 2:
            motorControl.turnRight(5);
            break;
        case 3:
            motorControl.turnLeft(15);
            break;
        case 4:
            motorControl.turnLeft(5);
            break;
        case 5:
            motorControl.turnRight(10);
            motorControl.forward(2000);
            break;
        }
    }
}
