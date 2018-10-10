//import block
import java.util.Arrays;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

//package import block

public class SoulGenerator{
	public static int[] devs = new int[7];
	public static int[] means = new int[7];
	public static int maxTest = 1;
	public static boolean debugMode = false;
	
	public static void main(String[] args){
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
				
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(601, 601, BufferedImage.TYPE_INT_RGB);
		BresenhamLineDrawer artist = new BresenhamLineDrawer();
		String[] blegh = new String[]{null};
		int[] line1 = new int[]{0,0,600,600,soulSample.color};
		int[] line2 = new int[]{0,600,600,0,soulSample.color};
		artist.BresenDraw(canvas,blegh,line1);
		artist.BresenDraw(canvas,blegh,line2);
		frame.getContentPane().add(new JLabel(new ImageIcon(canvas))); // Look up more regarding how ImageIcon works - this is sufficient for now, but best to know more.
		frame.pack();
		frame.setVisible(true);
		
				System.out.println("");
			}
		}
		catch(java.lang.NumberFormatException e){
			System.out.println("Could not interpret either one of the arguments. Remember, arguments are optional");
		}
	}
}