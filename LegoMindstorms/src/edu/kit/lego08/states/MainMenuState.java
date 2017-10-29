package edu.kit.lego08.states;

import edu.kit.lego08.utils.Tuple;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

import java.util.ArrayList;

public class MainMenuState extends State {
    private static MainMenuState instance = null;
    private ArrayList<Tuple<String, State>> menuEntries = new ArrayList<>();
    private int selectedState = 0;

    private MainMenuState() {
        // States shall be used as singleton

        menuEntries.add(new Tuple<String, State>("Linienfolgen", LineFollowState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Labyrinth", MazeState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Hindernisse verschieben", MoveObjectsState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Brücke", BridgeState.getInstance()));
    }

    public static MainMenuState getInstance() {
        if (instance == null) {
            instance = new MainMenuState();
        }
        return instance;
    }

    @Override
    public void onEnter() {
        requestNextState(null); // Stay in current state
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        LCD.clear();

        for (int i = 0; i < menuEntries.size(); i++) {
            if (selectedState == i) {
                LCD.drawString(" " + menuEntries.get(i).x, 0, i*5);
            } else {
                LCD.drawString(">" + menuEntries.get(i).x, 0, i*5);
            }
        }

        if (Button.LEFT.isDown()) {
            selectedState = (selectedState - 1) % menuEntries.size();
        } else if (Button.RIGHT.isDown()) {
            selectedState = (selectedState + 1) % menuEntries.size();
        } else if (Button.ENTER.isDown()) {

            while (Button.ENTER.isDown()) {
                // Wait for button release
                Delay.msDelay(10);
            }
            requestNextState(menuEntries.get(selectedState).y);
        }
    }
}
