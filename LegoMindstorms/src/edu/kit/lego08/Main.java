package edu.kit.lego08;

import edu.kit.lego08.states.MainMenuState;
import edu.kit.lego08.states.State;

public class Main {
    public static void main(String[] args) {

        System.out.println("Started main loop");

        State currentState = MainMenuState.getInstance();
        currentState.onEnter();
        while (true) {
            currentState.mainLoop();

            if (currentState.getNextState() != null) {
                State nextState = currentState.getNextState();
                currentState.onExit();
                nextState.onEnter();
                currentState = nextState;
            }
        }
    }

}
