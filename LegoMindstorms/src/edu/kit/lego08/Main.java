package edu.kit.lego08;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Main {

    public static void main(String[] args) {
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        LCD.drawString("Initializing...", 0, 0);

        Melody musicThread = new Melody(Melody.jeopardy, 6, 160);
        musicThread.start();
        SensorUtils.init();
        musicThread.setStop(true);

        State currentState = MainMenuState.getInstance();
        currentState.onEnter();
        
        Sound.playTone(600, 20);
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
