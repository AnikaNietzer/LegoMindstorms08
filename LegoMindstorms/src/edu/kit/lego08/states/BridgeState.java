package edu.kit.lego08.states;

import edu.kit.lego08.sensors.SonarService;
import lejos.hardware.lcd.LCD;

public class BridgeState extends State {
    private static BridgeState instance = null;
    private SonarService sonarService;

    private BridgeState() {
        // States shall be used as singleton
    }

    public static BridgeState getInstance() {
        if (instance == null) {
            instance = new BridgeState();
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
