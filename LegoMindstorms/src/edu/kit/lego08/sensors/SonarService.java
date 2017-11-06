package edu.kit.lego08.sensors;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class SonarService extends Thread {
    private boolean isRunning = true;
    private RegulatedMotor motor;
    private static final int NUM_MEASUREMENTS = 6;
    private float distances[] = new float[NUM_MEASUREMENTS];

    public SonarService() {
        motor = new EV3MediumRegulatedMotor(MotorPort.B);
    }

    public void stopService() {
        isRunning = false;
        motor.close();
    }

    private void initPosition() {
        motor.setSpeed(150);

        motor.backward();
        while (!SensorUtils.isTouchSonarPressed()) {
            // Move until sensor is pressed
        }
        motor.stop();
        motor.forward();
        while (SensorUtils.isTouchSonarPressed()) {
            // Move until sensor is released
        }
        motor.rotate(10);
    }

    @Override
    public void run() {
        while (isRunning) {
            initPosition();

            for (int i = 0; i < NUM_MEASUREMENTS; i++) {
                measure(i);
                motor.rotate(160/NUM_MEASUREMENTS);
            }
            measure(5);
            printDistances();
        }
    }

    private void measure(int position) {
        distances[position] = SensorUtils.getDistance();
    }

    public void printDistances() {
        LCD.clear();

        for (int i = 0; i < NUM_MEASUREMENTS; i++) {
            LCD.drawString(String.format("%d: %.4f", i, distances[i]), 0, i);
        }
    }

    public float getDistance(int direction) {
        return distances[direction];
    }
}
