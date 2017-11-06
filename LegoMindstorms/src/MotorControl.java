import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorControl {

    private RegulatedMotor motorRight;
    private RegulatedMotor motorLeft;

    public MotorControl() {
        motorRight = new EV3LargeRegulatedMotor(MotorPort.A);
        motorRight.setSpeed(360);
        motorLeft = new EV3LargeRegulatedMotor(MotorPort.D);
        motorLeft.setSpeed(360);
    }

    public void turnRight(int angle) {
        // 5.95 is factor for how much the motors have to rotate to rotate the roboter 1 degree
        motorRight.rotate(-(int)((double)angle * 5.95), true);
        motorLeft.rotate((int)((double)angle * 5.95), true);
        while (motorLeft.isMoving()) {
            Delay.msDelay(50);
        }
        
    }
    
    public void turnLeft(int angle) {
     // 5.95 is factor for how much the motors have to rotate to rotate the roboter 1 degree
        motorRight.rotate((int)((double)angle * 5.9), true);
        motorLeft.rotate(-(int)((double)angle * 5.9), true);
        while (motorLeft.isMoving()) {
            Delay.msDelay(50);
        }
    }
    
    public void forward(int millis) {
        motorRight.forward();
        motorLeft.forward();
        Delay.msDelay(millis);
        motorRight.stop(true);
        motorLeft.stop();
    }
    
    public void backward(int millis) {
        motorRight.backward();
        motorLeft.backward();
        Delay.msDelay(millis);
        motorRight.stop(true);
        motorLeft.stop();
    }

}
