package main;

import robotControl.RobotController;

public class LabyrinthController extends RobotController {
	
	/**
	 * Constructor for Controller in Labyrinth Parcour
	 */
	public LabyrinthController() {

	}

	
	/**
	 * Implements the logic for the robot controls.
	 * @param lightSensorRedValue Value of the red channel from the lightsensor
	 * @param lightSensorBlueValue Value of the blue channel from the lightsensor
	 * @param lightSensorGreenValue Value of the green channel from the lightsensor
	 * All light values are given in rgb pixel values (0-255)
	 */
	public double[] getControlAction(int lightSensorRedValue, int lightSensorGreenValue, int lightSensorBlueValue)
	{
		
		//speed in pixel per cycle
		double motorSpeedLeft = 0;
		double motorSpeedRight = 0;
		
		//robot kinematics is as follows:
		//center of rotation is one third in, seen from the front
		//the light sensor is mounted on the center of the front 
		
		// Insert Code here
		
		 
		double[] motorSpeeds = {motorSpeedLeft, motorSpeedRight};
		return motorSpeeds;
	}
}
