import lejos.hardware.Sound;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

public class SonarService extends Thread {
    private boolean isRunning = true;
    private RegulatedMotor m;

    public SonarService() {
        m = new EV3MediumRegulatedMotor(MotorPort.B);
    }

    public void stopService() {
        isRunning = false;
    }

    private void initPosition() {
        m.setSpeed(150);

        m.backward();
        while (!SensorUtils.isTouchSonarPressed()) { }
        m.stop();
        m.forward();
        while (SensorUtils.isTouchSonarPressed()) { }
        m.rotate(10);
    }

    @Override
    public void run() {
        while (isRunning) {
            initPosition();

            for (int i = 0; i < 5; i++) {
                measure(i);
                m.rotate(32);
            }
            measure(5);
        }
    }

    private void measure(int position) {
        Sound.playTone(800 + 50*position, 50);
    }
}
