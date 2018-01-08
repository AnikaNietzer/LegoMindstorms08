package edu.kit.lego08.states;

import edu.kit.lego08.motor_control.MotorControl;
import lejos.utility.Delay;

public class FindMiddleState extends State {

    private static FindMiddleState instance;
    private MotorControl motor;

    private FindMiddleState() {
        motor = new MotorControl();
    }

    public static FindMiddleState getInstance() {
        if (instance == null) {
            instance = new FindMiddleState();

        }
        return instance;

    }

    @Override
    public void onEnter() {
        motor.steer(-67);
        Delay.msDelay(4000);
        motor.stop(true);
        requestNextState(ColorSearchState.getInstance());
    }

    @Override
    public void onExit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void mainLoop() {
        // TODO Auto-generated method stub

    }

}
