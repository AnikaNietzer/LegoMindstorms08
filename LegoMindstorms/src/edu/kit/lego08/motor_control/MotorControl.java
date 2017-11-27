package edu.kit.lego08.motor_control;

import edu.kit.lego08.sensors.SensorUtils;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class MotorControl {

    private static DifferentialPilot pilot = new DifferentialPilot(3.5f, 12.0f, Motor.A, Motor.D);

    public MotorControl() {
<<<<<<< HEAD
        pilot.setTravelSpeed(25);
=======
<<<<<<< HEAD
        pilot.setTravelSpeed(25);
=======
        pilot.setTravelSpeed(20);
>>>>>>> add code for obstacle
>>>>>>> 3b97c7a... Add stopMethods
    }

    public void leftTrackForward() {
        stop(true);

        pilot.steer(75);
    }

    public void rightTrackForward() {
        stop(true);

        pilot.steer(-75);

    }

    public void turnRight(int angle) {
        stop(true);
<<<<<<< HEAD
        SensorUtils.resetGyro();
=======
>>>>>>> 3b97c7a... Add stopMethods
        pilot.rotate(2*angle, true);

    }

    public void turnLeft(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the
        // roboter 1 degree
        stop(true);
<<<<<<< HEAD

        SensorUtils.resetGyro();
=======
>>>>>>> 3b97c7a... Add stopMethods
        pilot.rotate(-2*angle, true);

    }

    public void turnLeftAndWait(int angle) {
        stop(true);
        SensorUtils.resetGyro();
        pilot.rotateLeft();
        while (Math.abs(SensorUtils.getGyroAngle()) < angle) {
            Delay.msDelay(10);
        }
        stop(true);
        
    }

    public void turnRightAndWait(int angle) {
        stop(true);
        SensorUtils.resetGyro();
        pilot.rotateRight();
        while (Math.abs(SensorUtils.getGyroAngle()) < angle) {
            Delay.msDelay(10);
        }
        stop(true);
    }

    public void forward() {
        stop(true);

        pilot.forward();
    }

    public void forwardTimed(int millis, boolean waitForStop) {

        stop(true);
        pilot.forward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public void forwardDistance(double distance) {
        stop(true);
        pilot.travel(distance);
    }

    public void backwardDistance(double distance) {
        stop(true);
        pilot.travel(-distance);
    }

    public void backward() {
        stop(true);
        pilot.backward();

    }

    public void backwardTimed(int millis, boolean waitForStop) {
        stop(false);
        pilot.backward();
        Delay.msDelay(millis);
        stop(waitForStop);
    }

    public boolean isMoving() {
        Delay.msDelay(5);
        return pilot.isMoving();
    }

    public void stop(boolean waitForStop) {
        pilot.quickStop();
    }

}
