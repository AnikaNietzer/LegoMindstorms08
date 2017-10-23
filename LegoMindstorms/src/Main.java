import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Main {
    
    public static void main(String[] args){
        LCD.clear();
        LCD.drawString("Hello World", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

}
