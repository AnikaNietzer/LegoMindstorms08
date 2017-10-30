package edu.kit.lego08.sensors;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

public class SensorUtils {
    private SensorUtils() {
        // Utility classes should not be instantiated
    }

    public static boolean isTouchSonarPressed() {
        EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
        float[] sample = new float[touch.sampleSize()];
        touch.fetchSample(sample, 0);
        touch.close();
        return sample[0] == 1;
    }

    public static boolean isKeyPressedAndReleased(Key k) {
        if (k.isDown()) {
            while (Button.ENTER.isDown()) {
                // Wait for button release
                Delay.msDelay(10);
            }
            return true;
        } else {
            return false;
        }
    }
}
