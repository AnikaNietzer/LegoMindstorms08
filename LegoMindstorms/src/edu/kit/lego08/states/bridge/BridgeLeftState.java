package edu.kit.lego08.states.bridge;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.SonarService;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class BridgeLeftState extends State {
    private static BridgeLeftState instance = null;
    private SonarService sonarService;
    private MotorControl motorControl = new MotorControl();

    private BridgeLeftState() {
        // States shall be used as singleton
    }

    public static BridgeLeftState getInstance() {
        if (instance == null) {
            instance = new BridgeLeftState();
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
    }

    @Override
    public void onExit() {
        sonarService.stopService();
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();

        motorControl.turnLeft(10);
        Delay.msDelay(1000);

        if (sonarService.getDistance(3) < 0.2 && sonarService.getDistance(3) < Float.MAX_VALUE) {
            requestNextState(BridgeForwardState.getInstance());
            Sound.playTone(500, 400);
        }
    }
}
