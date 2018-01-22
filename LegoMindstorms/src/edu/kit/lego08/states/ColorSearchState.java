package edu.kit.lego08.states;

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
        reset();
        motor = new MotorControl();

    }

    public void reset() {
        left = true;
        whiteCount = 0;
        redCount = 0;
        whiteFound = false;
        redFound = false;
        angle = -160;
        time = 0;
        timeGap = 1000;

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
            while (System.currentTimeMillis() - time < 500) {
                checkColor();
            }
            motor.forwardDistance(15);
            while (motor.isMoving()) {

            }

            motor.turnLeft(30);
            while (motor.isMoving()) {

            }

            motor.backward();
            time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 500) {
                checkColor();
            }
            motor.forwardDistance(15);
            while (motor.isMoving()) {

            }
            motor.turnRight(60);
            while (motor.isMoving()) {

            }

            motor.backward();
            time = System.currentTimeMillis();
            while (System.currentTimeMillis() - time < 500) {
                checkColor();
            }
            motor.forwardDistance(15);
            while (motor.isMoving()) {

            }
            motor.turnLeft(30);
            while (motor.isMoving()) {

            }
            motor.setColorSpeed();
        }
        // motor.forward();
        motor.steer(angle);
        time = System.currentTimeMillis();

        LCD.clear();
        LCD.drawString("State: Felder", 0, 5);
        LCD.drawString("Angle: " + angle, 0, 3);

    }

    @Override
    public void onExit() {
        if (!getNextState().equals(ColorSearchState.getInstance())) {
            motor.stop(true);
            motor.setFastSpeed();
        }
        // motor.stop(true);
    }

    @Override
    public void mainLoop() {

        /*
         * LCD.drawInt((int)timeGap, 10, 6);
         * LCD.drawInt((int)(System.currentTimeMillis() - time), 0, 6);
         */
        if (SensorUtils.isTouchPressed()) {
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
            // } else if ((System.currentTimeMillis() - time) >= timeGap) {
        } else if (Math.abs(SensorUtils.getGyroAngle()) >= 420) {
            motor.stop(true);
            SensorUtils.resetGyro();
            timeGap = timeGap * 1.17;
            if (angle < -60) {
                angle = (int) ((float) angle + 15);
            } else if (angle < -20 && angle >= -60) {
                angle = angle + 6;
            } else if (angle <= 5 && angle >= -20) {
                angle = angle + 4;
            }
            requestNextState(ColorSearchState.getInstance());
        }
        checkColor();
        checkEnterToMainMenu();
    }

    private void checkColor() {
        if (SensorUtils.getColor() == ColorEnum.LINE && whiteCount < 10) {
            whiteCount++;
            redCount = 0;

            blueCount = 0;
        } else if (SensorUtils.getColor() == ColorEnum.LINE && whiteCount >= 10 && whiteFound == false) {
            whiteFound = true;
            if (redFound == true) {
                requestNextState(MainMenuState.getInstance());
            } else {
                requestNextState(ColorSearchState.getInstance());
            }
            motor.stop(true);
            LCD.drawString("White", 0, 6);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 659, 376);
            Sound.playNote(PIANO, 494, 188);
            Sound.playNote(PIANO, 523, 188);
            Sound.playNote(PIANO, 587, 376);

        } else if (SensorUtils.getColor() == ColorEnum.BLUEMARKER && blueCount < 10) {
            redCount = 0;
            whiteCount = 0;

            blueCount++;
        } else if (SensorUtils.getColor() == ColorEnum.BLUEMARKER && blueCount >= 10) {
            blueCount = 0;

            LCD.drawString("blue", 0, 8);
            motor.backwardDistance(10);
            motor.turnLeft(10);
            while (motor.isMoving()) {

            }
            requestNextState(ColorSearchState.getInstance());
        } else if (SensorUtils.getColor() == ColorEnum.MAZEMARKER && redCount < 10) {
            redCount++;
            blueCount = 0;
            whiteCount = 0;
        } else if (SensorUtils.getColor() == ColorEnum.MAZEMARKER && redCount >= 10 && redFound == false) {
            redFound = true;
            if (whiteFound == true) {
                requestNextState(MainMenuState.getInstance());
            } else {
                requestNextState(ColorSearchState.getInstance());
            }
            motor.stop(true);

            LCD.drawString("Red", 0, 7);
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
        LCD.drawString("Weiﬂ: " + whiteCount, 0, 0);
        LCD.drawString("Blau: " + blueCount, 0, 2);
        LCD.drawString("Rot: " + redCount, 0, 1);
    }
}
