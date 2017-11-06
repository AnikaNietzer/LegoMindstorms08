package edu.kit.lego08.states;

import edu.kit.lego08.Config;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.linefollow.LineFollowState;
import edu.kit.lego08.utils.Tuple;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

import java.util.ArrayList;

public class MainMenuState extends State {
    private static MainMenuState instance = null;
    private ArrayList<Tuple<String, State>> menuEntries = new ArrayList<>();
    private int selectedState = 0;

    private MainMenuState() {
        // States shall be used as singleton

        menuEntries.add(new Tuple<String, State>("Linienfolgen", LineFollowState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Labyrinth", MazeState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Verschieben", MoveObjectsState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Bruecke", BridgeState.getInstance()));
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
        redraw();
    }

    @Override
    public void onExit() {

    }

    @Override
    public void mainLoop() {
        if (SensorUtils.isKeyPressedAndReleased(Button.UP)) {
            selectedState = (selectedState - 1 + menuEntries.size()) % menuEntries.size();
            redraw();
        } else if (SensorUtils.isKeyPressedAndReleased(Button.DOWN)) {
            selectedState = (selectedState + 1) % menuEntries.size();
            redraw();
        } else if (SensorUtils.isKeyPressedAndReleased(Button.ENTER)) {
            requestNextState(menuEntries.get(selectedState).y);
        } else if (SensorUtils.isKeyPressedAndReleased(Button.ESCAPE)) {
            System.exit(0);
        }
    }

    private void redraw() {
        LCD.clear();
        LCD.drawString("Hauptmenue (" + Config.USER + ")", 0, 0);

        for (int i = 0; i < menuEntries.size(); i++) {
            if (selectedState == i) {
                LCD.drawString(">" + menuEntries.get(i).x, 0, i + 2);
            } else {
                LCD.drawString(" " + menuEntries.get(i).x, 0, i + 2);
            }
        }
    }
}
