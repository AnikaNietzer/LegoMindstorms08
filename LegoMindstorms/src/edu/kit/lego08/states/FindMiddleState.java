package edu.kit.lego08.states;

import edu.kit.lego08.motor_control.MotorControl;

public class FindMiddleState extends State {

    private static FindMiddleState instance;
    private MotorControl motor;

    private FindMiddleState() {
        motor = new MotorControl();
        ColorSearchState.getInstance().reset();
    }

    public static FindMiddleState getInstance() {
        if (instance == null) {
            instance = new FindMiddleState();

        }
        return instance;

    }

    @Override
    public void onEnter() {
        motor.turnLeftAndWait(40);
        motor.forwardDistance(50);
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
