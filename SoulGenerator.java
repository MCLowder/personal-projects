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
				// Display block for showing off the model
				JFrame frame = new JFrame("Line Drawer");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
				BufferedImage canvas = new BufferedImage(300, 800, BufferedImage.TYPE_INT_RGB);
				BresenhamLineDrawer artist = new BresenhamLineDrawer();
				SimpleGraphicsPackage graphics = new SimpleGraphicsPackage();
				String[] blegh = new String[]{null};
				// Now we have to loop through the Model's tables to draw everything correctly.
				for(int line = 0;line < soulSample.model.edgeTable.size();line++){
					int[] newLine = new int[5];
					newLine[0] = soulSample.model.vertexTable.get(soulSample.model.edgeTable.get(line).get(0)).get(0);
					newLine[1] = soulSample.model.vertexTable.get(soulSample.model.edgeTable.get(line).get(0)).get(1);
					newLine[2] = soulSample.model.vertexTable.get(soulSample.model.edgeTable.get(line).get(1)).get(0);
					newLine[3] = soulSample.model.vertexTable.get(soulSample.model.edgeTable.get(line).get(1)).get(1);
					newLine[4] = soulSample.model.edgeTable.get(line).get(2);
					artist.BresenDraw(canvas,blegh,graphics.LineClipper(canvas,newLine));
				}
				for(int circle = 0;circle < soulSample.model.circleTable.size();circle++){
					int[] newCircle = new int[4];
					newCircle[0] = soulSample.model.circleTable.get(circle).get(0);
					newCircle[1] = soulSample.model.circleTable.get(circle).get(1);
					newCircle[2] = soulSample.model.circleTable.get(circle).get(2);
					newCircle[3] = soulSample.model.circleTable.get(circle).get(3);
					artist.BresenCircle(canvas,newCircle,true);
				}
				frame.getContentPane().add(new JLabel(new ImageIcon(canvas)));
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