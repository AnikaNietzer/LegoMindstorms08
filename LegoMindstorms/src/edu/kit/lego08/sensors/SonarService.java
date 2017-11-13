package edu.kit.lego08.sensors;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class SonarService extends Thread {
    private boolean isRunning = true;
    private RegulatedMotor motor;
    private float distances[] = new float[3];

    public SonarService() {
        motor = new EV3MediumRegulatedMotor(MotorPort.B);
    }

    public void stopService() {
        isRunning = false;
        motor.close();
    }

    public void initPosition() {
        motor.setSpeed(100);

        motor.backward();
        while (!SensorUtils.isTouchSonarPressed()) {
            // Move until sensor is pressed
        }
        motor.stop();
        motor.forward();
        while (SensorUtils.isTouchSonarPressed()) {
            // Move until sensor is released
        }
        motor.rotate(90);
    }

    @Override
    public void run() {
        //while (isRunning) {
        //    measureAll();
        //}
    }

    public void measureAll() {
        //initPosition();

        //measure(0);
        //motor.rotate(90);
        //measure(1);
        //motor.rotate(90);
        measure(2);

        //printDistances();
    }

    private void measure(int position) {
        //Sound.playTone(200, 20);
        distances[position] = SensorUtils.getDistance();
    }

    public void printDistances() {
        LCD.clear();

        for (int i = 0; i < distances.length; i++) {
            LCD.drawString(String.format("%d: %.4f", i, distances[i]), 0, i);
        }
    }

    public float getDistance(int direction) {
        return distances[direction];
    }
}
