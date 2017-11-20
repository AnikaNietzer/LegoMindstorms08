package edu.kit.lego08;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class Main {
    public static void main(String[] args) {
        LCD.drawString("Initializing...", 0, 0);
        SensorUtils.init();
        Sound.playTone(600, 20);

        State currentState = MainMenuState.getInstance();
        currentState.onEnter();
        while (true) {
            currentState.mainLoop();

            if (currentState.getNextState() != null) {
                State nextState = currentState.getNextState();
                currentState.onExit();
                nextState.onEnter();
                currentState = nextState;
            }
        }
    }

}
