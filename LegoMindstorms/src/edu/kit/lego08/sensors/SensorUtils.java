package edu.kit.lego08.sensors;

import lejos.hardware.Key;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SensorUtils {
    private static SensorModes ultrasonicSensor = null;
    private static EV3ColorSensor colorSensor = null;
    private static EV3TouchSensor touch = null;
    private static EV3GyroSensor gyro = null;
    private static SampleProvider angleProvider = null;

    private SensorUtils() {
        // Utility classes should not be instantiated
    }

    public static void init() {
        Thread t1 = new Thread() {
          public void run() {
              try {
                  colorSensor = new EV3ColorSensor(SensorPort.S3);
              } catch (IllegalArgumentException e) {
                  colorSensor = new EV3ColorSensor(SensorPort.S3);
              }
              try {
                  gyro = new EV3GyroSensor(SensorPort.S1);
              } catch (IllegalArgumentException e) {
                  gyro = new EV3GyroSensor(SensorPort.S1);
              }
              gyro.setCurrentMode(1); // Angle
              angleProvider = gyro.getAngleMode();
          }
        };
        t1.start();
        Thread t2 = new Thread() {
            public void run() {
                try {
                    touch = new EV3TouchSensor(SensorPort.S2);
                } catch (IllegalArgumentException e) {
                    touch = new EV3TouchSensor(SensorPort.S2);
                }
                try {
                    ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
                } catch (IllegalArgumentException e) {
                    ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
                }
            }
        };
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ultrasonicSensor == null || gyro == null || touch == null || colorSensor == null) {
            Sound.playTone(100, 1000);
            System.exit(1);
        }
    }
    
    public static void resetGyro() {
        gyro.reset();
    }
    
    public static float getGyroAngle() {
        float[] sample = new float[angleProvider.sampleSize()];
        angleProvider.fetchSample(sample, 0);
        return sample[0];
    }

    public static boolean isTouchSonarPressed() {
        float[] sample = new float[touch.sampleSize()];
        sample[0] = 0;
        touch.fetchSample(sample, 0);
        if(sample[0] == 1) {
            Sound.playTone(600, 20);
        }
        return sample[0] == 1;
    }

    public static boolean isKeyPressedAndReleased(Key k) {
        if (k.isDown()) {
            Sound.playTone(500, 20);
            while (k.isDown()) {
                // Wait for button release
                Delay.msDelay(10);
            }
            Sound.playTone(600, 20);
            return true;
        } else {
            return false;
        }
    }

    public static ColorEnum getColor() {
        int colorId = colorSensor.getColorID();
        if (colorId == Color.BLACK || colorId == Color.NONE || colorId == Color.BROWN || colorId == Color.DARK_GRAY) {
            return ColorEnum.BACKGROUND;
        } else if (colorId == Color.YELLOW || colorId == Color.WHITE || colorId == Color.LIGHT_GRAY) {
            return ColorEnum.LINE;
        } else if (colorId == Color.RED || colorId == Color.MAGENTA || colorId == Color.PINK
                || colorId == Color.ORANGE) {
            return ColorEnum.MAZEMARKER;
        } else if (colorId == Color.BLUE || colorId == Color.CYAN) {
            return ColorEnum.BLUEMARKER;
        }
        return null;
    }

    public static int getColorId() {
        return colorSensor.getColorID();
    }

    public static float getDistance() {
        SampleProvider distance = ultrasonicSensor.getMode("Distance");
        float[] sample = new float[distance.sampleSize()];
        distance.fetchSample(sample, 0);
        return sample[0];
    }
}
