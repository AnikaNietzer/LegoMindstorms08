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
    private static boolean initialized = false;

    private static final int [][] jeopardy = {
            new int[] {392, 200, 100},
            new int[] {523, 200, 80},
            new int[] {392, 200, 100},
            new int[] {261, 100, 70},
            new int[] {261, 100, 70},
            new int[] {392, 200, 100},
            new int[] {523, 200, 80},
            new int[] {392, 200, 100},
            new int[] {392, 200, 0},
            new int[] {392, 200, 100},
            new int[] {523, 200, 80},
            new int[] {392, 200, 100},
            new int[] {523, 200, 80},
            new int[] {659, 300, 100},
            new int[] {587, 100, 80},
            new int[] {523, 100, 90},
            new int[] {493, 100, 80},
            new int[] {440, 100, 75},
            new int[] {415, 100, 70},
    };

    public static void main(String[] args) {
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
        LCD.drawString("Initializing...", 0, 0);

        Thread musicThread = new Thread() {
            public void run() {
                int i = 0;
                final float speed = 1.5f;
                while (!initialized) {
                    int tone[] = jeopardy[i % jeopardy.length];
                    Sound.playTone(tone[0], (int) (speed * tone[1]), tone[2]);
                    Delay.msDelay((long) (speed * (tone[1] / 2)));
                    i++;
                }
            }
        };

        Sound.setVolume(6);
        musicThread.start();
        SensorUtils.init();
        initialized = true;

        State currentState = MainMenuState.getInstance();
        currentState.onEnter();
        Sound.setVolume(100);
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
