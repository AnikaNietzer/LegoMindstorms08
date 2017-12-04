package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class GapState extends State {
    private static GapState instance = null;
    private static MotorControl motorControl;
    int stateCounter = 0;

    private GapState() {
        // States shall be used as singleton
    }

    public static GapState getInstance() {
        if (instance == null) {
            instance = new GapState();
        }
        motorControl = new MotorControl();
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null);
        LCD.clear();
        LCD.drawString("Bride Gap", 0, 5);
        motorControl.forwardDistance(8);
        Button.LEDPattern(LedPattern.BLINK_FAST_YELLOW);
    }

    @Override
    public void onExit() {
        stateCounter = 0;
    }

    @Override
    public void mainLoop() {
        ColorEnum color = SensorUtils.getColor();
        if (color == ColorEnum.LINE) {
            requestNextState(LineFollowState.getInstance());
        } else if (color == ColorEnum.BACKGROUND) {
            requestNextState(TurnRightState.getInstance(TurnLeftState.getInstance(GapState.getInstance())));
        } else if (color == ColorEnum.MAZEMARKER) {
            motorControl.stop(true);
            requestNextState(MainMenuState.getInstance());
        }
        checkEnterToMainMenu();
    }

}