package edu.kit.lego08.sensors;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

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
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        }
    }

    private void measure(int position) {
        Sound.playTone(800 + 50*position, 50);
        distances[position] = SensorUtils.getDistance();
        System.out.println("Measured pos="+position+" distance="+distances[position]);
    }
}
