package edu.kit.lego08.motor_control;

import edu.kit.lego08.motor_control.PController;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorControl {

    private static EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
    private static EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.D);

    public MotorControl() {
        motorRight.setSpeed(360);
        motorLeft.setSpeed(360);
    }

    public void leftTrackForward() {
        stop(true);

        motorLeft.forward();

    }

    public void rightTrackForward() {
        stop(true);

        motorRight.forward();

    }

    public void turnRight(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the
        // roboter 1 degree
        stop(true);
        motorRight.rotate(-(int) ((double) angle * 5.95), true);
        motorLeft.rotate((int) ((double) angle * 5.95), true);

    }

    public void turnLeft(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the
        // roboter 1 degree
        stop(true);
        motorRight.rotate((int) ((double) angle * 5.9), true);
        motorLeft.rotate(-(int) ((double) angle * 5.9), true);

    }

    public void turnLeftAndWait(int angle) {
        turnLeft(angle);
        while (isMoving()) {
            Delay.msDelay(10);
        }
    }

    public void turnRightAndWait(int angle) {
        turnRight(angle);
        while (isMoving()) {
            Delay.msDelay(10);
        }
    }

    public void forward() {
        stop(true);

        motorRight.forward();
        motorLeft.forward();

    }

    public void forwardTimed(int millis, boolean waitForStop) {

        stop(true);
        motorRight.forward();
        motorLeft.forward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public void backward() {
        stop(true);

        motorRight.backward();
        motorLeft.backward();

    }

    public void backwardTimed(int millis, boolean waitForStop) {
        stop(false);
        motorRight.backward();
        motorLeft.backward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public boolean isMoving() {
        Delay.msDelay(5);
        if (motorLeft.isMoving() || motorRight.isMoving()) {
            return true;
        } else {
            return false;
        }
    }

    public void stop(boolean waitForStop) {
        if (motorLeft.isMoving() && !motorRight.isMoving()) {
            motorLeft.stop(!waitForStop);
        } else if (!motorLeft.isMoving() && motorRight.isMoving()) {
            motorRight.stop(!waitForStop);
        } else {
            motorRight.stop(true);
            motorLeft.stop(!waitForStop);
        }
    }

}
