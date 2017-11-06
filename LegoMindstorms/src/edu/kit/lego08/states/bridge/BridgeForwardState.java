package edu.kit.lego08.states.bridge;

import edu.kit.lego08.sensors.SonarService;
import edu.kit.lego08.states.State;
import lejos.hardware.lcd.LCD;

public class BridgeForwardState extends State {
    private static BridgeForwardState instance = null;
    private SonarService sonarService;

    private BridgeForwardState() {
        // States shall be used as singleton
    }

    public static BridgeForwardState getInstance() {
        if (instance == null) {
            instance = new BridgeForwardState();
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
    }
}
