package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SonarService;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class BridgeTurnState extends State {
    private static BridgeTurnState instance = null;
    private SonarService sonarService;
    private MotorControl motorControl = new MotorControl();

    private BridgeTurnState() {
        // States shall be used as singleton
    }

    public static BridgeTurnState getInstance() {
        if (instance == null) {
            instance = new BridgeTurnState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
        sonarService = new SonarService();
        sonarService.start();

        LCD.clear();
        LCD.drawString("State: Bruecke", 0, 5);
        motorControl.backward(500);
    }

    @Override
    public void onExit() {
        sonarService.stopService();
    }

    @Override
    public void mainLoop() {
        motorControl.turnLeft(30);
        sonarService.measureAll();

        if (sonarService.getDistance(2) < 0.3) {
            Sound.playTone(500, 400);
            requestNextState(BridgeForwardState.getInstance());
        }

        checkEnterToMainMenu();
    }
}
