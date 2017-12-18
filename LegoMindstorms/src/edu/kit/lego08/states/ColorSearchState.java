package edu.kit.lego08.states;

import java.io.File;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class ColorSearchState extends State {

    private static ColorSearchState instance;
    private MotorControl motor;
    private int whiteCount;
    private int redCount;
    private int blueCount;
    private boolean whiteFound;
    private boolean redFound;
    private boolean left;
    private int turn = 45;
    private int angle = 200;
    private double time;
    private double timeGap;
    private boolean first;
    public final static int[] PIANO = new int[] { 4, 25, 500, 7000, 5 };

    private ColorSearchState() {
        left = true;
        whiteCount = 0;
        redCount = 0;
        whiteFound = false;
        redFound = false;
        angle = -160;
        time = 0;
        timeGap = 1000;
        motor = new MotorControl();
        first = true;
    }

    public static ColorSearchState getInstance() {
        if (instance == null) {
            instance = new ColorSearchState();

        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        if (first) {
            first = false;
            motor.backward();
            long time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 800) {
                checkColor();
            }
            motor.forwardDistance(15);
            while(motor.isMoving()) {
                
            }
        }
        // motor.forward();
        motor.steer(angle);
        time = System.currentTimeMillis();

        LCD.clear();
        LCD.drawString("State: Felder", 0, 5);

    }

    @Override
    public void onExit() {
        motor.stop(true);
    }

    @Override
    public void mainLoop() {
        checkEnterToMainMenu();
        /*
         * LCD.drawInt((int)timeGap, 10, 6);
         * LCD.drawInt((int)(System.currentTimeMillis() - time), 0, 6);
         */
        if (SensorUtils.isTouchSonarPressed()) {
            motor.backwardDistance(10);
            motor.turnLeft(10);
            while (motor.isMoving()) {

            }
            /*
             * if (left) { motor.backwardDistance(10); motor.turnLeft(90); while
             * (motor.isMoving()) {
             * 
             * } motor.forwardDistance(5); motor.turnLeft(90); turn = turn + 1;
             * while (motor.isMoving()) {
             * 
             * } left = false; } else { motor.backwardDistance(10);
             * motor.turnRight(90); while (motor.isMoving()) {
             * 
             * } motor.forwardDistance(5); motor.turnRight(90); turn = turn + 1;
             * while (motor.isMoving()) {
             * 
             * } left = true; }
             */
            requestNextState(ColorSearchState.getInstance());
        } else if ((System.currentTimeMillis() - time) >= timeGap) {
            motor.stop(true);
            timeGap = timeGap * 1.2;
            angle = (int) ((float) angle * 0.95);
            requestNextState(ColorSearchState.getInstance());
        }
        checkColor();
    }

    private void checkColor() {
        if (SensorUtils.getColor() == ColorEnum.LINE && whiteCount < 20) {
            whiteCount++;
        } else if (SensorUtils.getColor() == ColorEnum.LINE && whiteCount >= 20 && whiteFound == false) {
            whiteFound = true;
            if (redFound == true) {
                requestNextState(MainMenuState.getInstance());
            } else {
                requestNextState(ColorSearchState.getInstance());
            }
            motor.stop(true);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 494, 188);
            Sound.playNote(PIANO, 523, 188);
            Sound.playNote(PIANO, 587, 376);

        } else if (SensorUtils.getColor() == ColorEnum.BLUEMARKER && blueCount < 20) {
            blueCount++;
        } else if (SensorUtils.getColor() == ColorEnum.BLUEMARKER && blueCount >= 20) {
            blueCount = 0;
            motor.backwardDistance(10);
            motor.turnLeft(10);
            while (motor.isMoving()) {

            }
            requestNextState(ColorSearchState.getInstance());
        } else if (SensorUtils.getColor() == ColorEnum.MAZEMARKER && redCount < 20) {
            redCount++;
        } else if (SensorUtils.getColor() == ColorEnum.MAZEMARKER && redCount >= 20 && redFound == false) {
            redFound = true;
            if (whiteFound == true) {
                requestNextState(MainMenuState.getInstance());
            } else {
                requestNextState(ColorSearchState.getInstance());
            }
            motor.stop(true);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 494, 188);
            Sound.playNote(PIANO, 523, 188);
            Sound.playNote(PIANO, 587, 376);

        } else if (SensorUtils.getColor() == ColorEnum.BACKGROUND) {
            redCount = 0;
            whiteCount = 0;
            blueCount = 0;
        }
    }
}
