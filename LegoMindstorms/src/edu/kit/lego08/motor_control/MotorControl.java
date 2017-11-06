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

    public void turnRight(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the roboter 1 degree
        PController leftMotorController = new PController(motorLeft);
        PController rightMotorController = new PController(motorRight);
        motorRight.rotate(-(int)((double)angle * 5.95), true);
        motorLeft.rotate((int)((double)angle * 5.95), true);
        int time = 0;
        while (motorLeft.isMoving()) {
            Delay.msDelay(10);
            time = time + 10;
            rightMotorController.regulate(time);
            leftMotorController.regulate(time);
        }
        
    }
    
    public void turnLeft(int angle) {
     // 5.95 is factor for how much the motors have to rotate to rotate the roboter 1 degree
        PController leftMotorController = new PController(motorLeft);
        PController rightMotorController = new PController(motorRight);
        motorRight.rotate((int)((double)angle * 5.9), true);
        motorLeft.rotate(-(int)((double)angle * 5.9), true);
        int time = 0;
        while (motorLeft.isMoving()) {
            Delay.msDelay(10);
            time = time + 10;
            rightMotorController.regulate(time);
            leftMotorController.regulate(time);
        }
    }
    
    public void forward(int millis) {
        PController leftMotorController = new PController(motorLeft);
        PController rightMotorController = new PController(motorRight);
        motorRight.forward();
        motorLeft.forward();
        int time = 0;
        while (millis > 0) {
            if (millis > 10) {
                Delay.msDelay(10);
                millis = millis - 10;
                time = time + 10;
                leftMotorController.regulate(time);
                rightMotorController.regulate(time);
            } else {
                Delay.msDelay(millis);
                millis = 0;
            }
        }
        
        motorRight.stop(true);
        motorLeft.stop();
    }
    
    public void backward(int millis) {
        PController leftMotorController = new PController(motorLeft);
        PController rightMotorController = new PController(motorRight);
        motorRight.backward();
        motorLeft.backward();
        Delay.msDelay(millis);
        int time = 0;
        while (millis > 0) {
            if (millis > 10) {
                Delay.msDelay(10);
                millis = millis - 10;
                time = time + 10;
                leftMotorController.regulate(time);
                rightMotorController.regulate(time);
            } else {
                Delay.msDelay(millis);
                millis = 0;
            }
        }
        motorRight.stop(true);
        motorLeft.stop();
    }

}
