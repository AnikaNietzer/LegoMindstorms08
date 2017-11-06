package edu.kit.lego08.motor_control;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class PController {

    private EV3LargeRegulatedMotor motor;
    private double degreesPerMs;
    private int speed;
    
    public PController (EV3LargeRegulatedMotor motor) {
        this.motor = motor;
        this.speed = motor.getSpeed();
        this.degreesPerMs = ((double) speed / 1000.0);
        motor.resetTachoCount();
    }
    
    public void regulate(int timeInMillis) {
        double degrees = (double) motor.getTachoCount();
        double targetDegrees = degreesPerMs * timeInMillis;
        double relativeError = degrees / targetDegrees;
        motor.setSpeed((float)(speed * (double)relativeError));
    }
}
