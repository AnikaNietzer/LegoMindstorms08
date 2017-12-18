package edu.kit.lego08;

import lejos.hardware.Sound;
import lejos.utility.Delay;

public class Melody extends Thread {
    public static final int [][] jeopardy = {
            new int[] {392, 2, 100},
            new int[] {523, 2, 80},
            new int[] {392, 2, 100},
            new int[] {261, 1, 70},
            new int[] {261, 1, 70},
            new int[] {392, 2, 100},
            new int[] {523, 2, 80},
            new int[] {392, 2, 100},
            new int[] {392, 2, 0},
            new int[] {392, 2, 100},
            new int[] {523, 2, 80},
            new int[] {392, 2, 100},
            new int[] {523, 2, 80},
            new int[] {659, 3, 100},
            new int[] {587, 1, 80},
            new int[] {523, 1, 90},
            new int[] {493, 1, 80},
            new int[] {440, 1, 75},
            new int[] {415, 1, 70},
    };
    private boolean stop;
    private int [][] sequence;
    private float speed;
    
    public Melody (int[][] sequence, int Volume, int bpm) {
        this.sequence = sequence;
        this.stop = false;
        this.speed = 60000.f /(float)(bpm * 2);
        Sound.setVolume(Volume);
    }
    
    public void run() {
        int i = 0;
        
        while (!stop) {
            int tone[] = sequence[i % sequence.length];
            Sound.playTone(tone[0], (int) ((speed * (float)tone[1])* (3.f/4.f)), tone[2]);
            Delay.msDelay((long) (speed * ((float)tone[1]) / 4.f));
            i++;
        }
        Sound.setVolume(100);
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
