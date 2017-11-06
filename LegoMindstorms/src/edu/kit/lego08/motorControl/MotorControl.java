package edu.kit.lego08.motorControl;
import edu.kit.lego08.motorControl.PController;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorControl {

    private EV3LargeRegulatedMotor motorRight;
    private EV3LargeRegulatedMotor motorLeft;

    public MotorControl() {
        motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
        motorRight.setSpeed(360);
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.D);
        motorLeft.setSpeed(360);
    }

    public void turnRight(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the roboter 1 degree
        PController leftMotorController = new PController(motorLeft, motorLeft.getSpeed());
        PController rightMotorController = new PController(motorRight, motorRight.getSpeed());
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
        PController leftMotorController = new PController(motorLeft, motorLeft.getSpeed());
        PController rightMotorController = new PController(motorRight, motorRight.getSpeed());
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
        PController leftMotorController = new PController(motorLeft, motorLeft.getSpeed());
        PController rightMotorController = new PController(motorRight, motorRight.getSpeed());
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
        PController leftMotorController = new PController(motorLeft, motorLeft.getSpeed());
        PController rightMotorController = new PController(motorRight, motorRight.getSpeed());
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
