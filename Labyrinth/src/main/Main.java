package main;

import run.Task;

public class Main {

	public static void main(String [] args)
	{
		
		LabyrinthController robCon = new LabyrinthController();	
		Task.runSimulation(robCon);
	}
}
