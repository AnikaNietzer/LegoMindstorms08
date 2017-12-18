package edu.kit.lego08.states;

import edu.kit.lego08.Config;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.bridge.BridgeLeftState;
import edu.kit.lego08.states.bridge.BridgeRightState;
import edu.kit.lego08.states.bridge.BridgeStartState;
import edu.kit.lego08.states.linefollow.LineFollowState;
import edu.kit.lego08.states.maze.MazeFindState;
import edu.kit.lego08.utils.LedPattern;
import edu.kit.lego08.utils.Tuple;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

import java.util.ArrayList;

public class MainMenuState extends State {
    private static MainMenuState instance = null;
    private ArrayList<Tuple<String, State>> menuEntries = new ArrayList<>();
    private int selectedState = 0;
    private int autoModeState = 0;

    private MainMenuState() {
        // States shall be used as singleton
        menuEntries.add(new Tuple<String, State>("Linienfolgen", LineFollowState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Gerade", ForwardState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Labyrinth", MazeFindState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Bruecke", BridgeStartState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Farbenfinden", ColorSearchState.getInstance()));
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
        Button.LEDPattern(LedPattern.OFF);
    }

    @Override
    public void onExit() {
        LCD.clear();
        LCD.drawString(menuEntries.get(selectedState).x, 2, 2);
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

        if (Config.AUTO_MODE && autoModeState < menuEntries.size()) {
            Sound.playTone(500, 500);
            Sound.playTone(700, 500);
            Sound.playTone(900, 500);
            requestNextState(menuEntries.get(autoModeState).y);
            selectedState = autoModeState;
            autoModeState++;
        } else if (Config.AUTO_MODE) {
            Sound.playTone(800, 1000);
            Sound.playTone(500, 3000);
            System.exit(0);
        }
    }

    private void redraw() {
        LCD.clear();
        LCD.drawString("Hauptmenue", 0, 0);
        LCD.drawString("  (" + Config.USER+")", 0, 1);

        for (int i = 0; i < menuEntries.size(); i++) {
            if (selectedState == i) {
                LCD.drawString(">" + menuEntries.get(i).x, 0, i + 3);
            } else {
                LCD.drawString(" " + menuEntries.get(i).x, 0, i + 3);
            }
        }
    }
}
