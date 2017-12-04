package edu.kit.lego08;

import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Main {
    private static boolean initialized = false;

    private static final int [][] jeopardy = {
            new int[] {392, 200},
            new int[] {523, 200},
            new int[] {392, 200},
            new int[] {261, 100},
            new int[] {261, 100},
            new int[] {392, 200},
            new int[] {523, 200},
            new int[] {392, 400},
            new int[] {392, 200},
            new int[] {523, 200},
            new int[] {392, 200},
            new int[] {523, 200},
            new int[] {659, 400},
            new int[] {587, 100},
            new int[] {523, 100},
            new int[] {493, 100},
            new int[] {440, 100},
            new int[] {415, 100},
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
                    int tone[] = jeopardy[i % jeopardy.length];
                    Sound.playTone(tone[0], tone[1]);
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
