package edu.kit.lego08.states;

import edu.kit.lego08.sensors.SonarService;
import lejos.hardware.lcd.LCD;

public class BridgeState extends State {
    private static BridgeState instance = null;
    private SonarService sonarService = new SonarService();

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
        sonarService.start();
    }

    @Override
    public void onExit() {
        sonarService.stopService();
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        LCD.clear();
        LCD.drawString("State: Brücke", 0, 5);
    }
}
