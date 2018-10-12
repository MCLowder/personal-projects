//import block
import java.util.Arrays;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;

//package import block

public class SoulGenerator{
	public static int[] devs = new int[7];
	public static int[] means = new int[7];
	public static int maxTest = 1;
	public static boolean debugMode = false;
	
	public static void main(String[] args){
		//ArrayList<Soul> population = new ArrayList<Soul>();
		try{
			if(Arrays.asList(args).contains("devs"))
				Arrays.fill(devs,Integer.parseInt(args[Arrays.asList(args).indexOf("devs")+1])); // what is val?
			else
				Arrays.fill(devs,10);
			if(Arrays.asList(args).contains("means"))
				Arrays.fill(means,Integer.parseInt(args[Arrays.asList(args).indexOf("means")+1])); // what is val?
			else
				Arrays.fill(means,0);
			if(Arrays.asList(args).contains("tests"))
				maxTest=Integer.parseInt(args[Arrays.asList(args).indexOf("tests")+1]); // what is val?
			if(Arrays.asList(args).contains("debug"))
				debugMode = true;
			System.out.println("");
			for(int test=0;test<maxTest;test++){
				Soul soulSample = new Soul(means, devs);
				System.out.println(soulSample.Bio);
				if(debugMode)
					System.out.println(Arrays.toString(soulSample.ethicScores));
				// Display block for showing off the model
				if(Arrays.asList(args).contains("display"))
					soulSample.model.displayModel();
		
				System.out.println("");
			}
		}
		catch(java.lang.NumberFormatException e){
			System.out.println("Could not interpret either one of the arguments. Remember, arguments are optional");
		}
	}
}