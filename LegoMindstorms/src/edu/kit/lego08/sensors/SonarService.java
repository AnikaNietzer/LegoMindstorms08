package edu.kit.lego08.sensors;

import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class SonarService extends Thread {
    private boolean isRunning = true;
    private RegulatedMotor motor;

    public SonarService() {
        //motor = new EV3MediumRegulatedMotor(MotorPort.B);
    }

    public void stopService() {
        isRunning = false;
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

            for (int i = 0; i < 5; i++) {
                measure(i);
                motor.rotate(32);
            }
            measure(5);
        }
    }

    private void measure(int position) {
        Sound.playTone(800 + 50*position, 50);
    }
}
