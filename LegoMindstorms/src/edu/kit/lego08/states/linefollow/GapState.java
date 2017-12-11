package edu.kit.lego08.states.linefollow;

import edu.kit.lego08.motor_control.MotorControl;
import edu.kit.lego08.sensors.ColorEnum;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;
import edu.kit.lego08.utils.LedPattern;
import lejos.hardware.Button;

public class GapState extends State {
    private static GapState instance = null;
    private static MotorControl motorControl;

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
        motorControl.forwardDistance(8);
        Button.LEDPattern(LedPattern.STATIC_YELLOW);
    }

    @Override
    public void onExit() {
        motorControl.stop(true);
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