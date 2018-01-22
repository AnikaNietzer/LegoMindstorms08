package edu.kit.lego08.states;

import java.util.ArrayList;

import edu.kit.lego08.Config;
import edu.kit.lego08.sensors.SensorUtils;
import edu.kit.lego08.states.bridge.BridgeStartState;
import edu.kit.lego08.states.linefollow.LineFollowState;
import edu.kit.lego08.states.maze.MazeFindState;
import edu.kit.lego08.utils.LedPattern;
import edu.kit.lego08.utils.Tuple;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class MainMenuState extends State {
    private static MainMenuState instance = null;
    private ArrayList<Tuple<String, State>> menuEntries = new ArrayList<>();
    private int selectedState = 0;
    private int autoModeState = -1;
    private boolean wasExitedManually = false;

    private MainMenuState() {
        // States shall be used as singleton
        menuEntries.add(new Tuple<String, State>("Linienfolgen", LineFollowState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Gerade", ForwardState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Labyrinth", MazeFindState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Bruecke", BridgeStartState.getInstance()));
        menuEntries.add(new Tuple<String, State>("Farbenfinden", FindMiddleState.getInstance()));
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
        setExitedManually(false);
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
            autoModeState = selectedState;
            if (!Config.AUTO_MODE) {
                requestNextState(menuEntries.get(selectedState).y);
            }
        } else if (SensorUtils.isKeyPressedAndReleased(Button.ESCAPE)) {
            System.exit(0);
        }

        if (Config.AUTO_MODE && autoModeState != -1) {
            if (autoModeState >= menuEntries.size() || wasExitedManually) {
                Sound.playTone(800, 100);
                Sound.playTone(500, 300);
                autoModeState = -1;
            } else {
                Sound.playTone(500, 50);
                Sound.playTone(700, 50);
                Sound.playTone(500, 50);
                Sound.playTone(700, 50);
                requestNextState(menuEntries.get(autoModeState).y);
                selectedState = autoModeState;
                autoModeState++;
            }
        }
    }

    private void redraw() {
        LCD.clear();
        if (Config.AUTO_MODE) {
            LCD.drawString("Hauptmenue AUTO", 0, 0);
        } else {
            LCD.drawString("Hauptmenue", 0, 0);
        }
        LCD.drawString("  (" + Config.USER+")", 0, 1);

        for (int i = 0; i < menuEntries.size(); i++) {
            if (selectedState == i) {
                LCD.drawString(">" + menuEntries.get(i).x, 0, i + 3);
            } else {
                LCD.drawString(" " + menuEntries.get(i).x, 0, i + 3);
            }
        }
    }

    public void setExitedManually(boolean wasExitedManually) {
        this.wasExitedManually = wasExitedManually;
    }
}
