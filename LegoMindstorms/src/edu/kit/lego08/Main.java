package edu.kit.lego08;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Main {
    private static boolean initialized = false;

    private static final int [] jeopardy = {
            100,
            400,
            100,
            50,
            100,
            400,
            100,
    };

    public static void main(String[] args) {
        LCD.drawString("Initializing...", 0, 0);

        final Thread initThread = new Thread() {
            public void run() {
                SensorUtils.init();
                initialized = true;
            }
        };
        Thread musicThread = new Thread() {
            public void run() {
                int i = 0;
                while (!initialized) {
                    Sound.playTone(jeopardy[i % jeopardy.length], 100);
                    Delay.msDelay(100);
                    i++;
                }
            }
        };
        musicThread.start();
        initThread.start();
        try {
            initThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
