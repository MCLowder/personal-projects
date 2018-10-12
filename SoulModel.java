// import block
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// package import block

public class SoulModel{
	
	/*public ArrayList<ArrayList<Integer>> vertexTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> polygonTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> surfaceTable = new ArrayList<ArrayList<Integer>>();
	// public int[][] polygonTable = new int[0][4]; // Traditionally, this would be a 3 wide table, referenced in the below table calling color.
	// public int[][] surfaceTable = new int[][]; // Since this more or less requires both dimensions to be variable, bit of a pain in java. If we get the ArrayList working, might be included later.
	public ArrayList<ArrayList<Integer>> circleTable = new ArrayList<ArrayList<Integer>>();
	// When 3d becomes a thing, this is where we start adding to it*/
	
	public GeometricTables tables = new GeometricTables();
	
	public void AddHalo(){
		/*Hardest part of tyhis is just defining the halo conceptually.
		For now, thet's define the width to be from 100 to 200.
		The vertical range should be smaller - maybe 60 or so pixels total?
		Tolerable range for the halo is 0-100, so declare the midpoint to be 50
		For simplicities sake, let it start out being represented by a simple line*/
		
		int col = (255<<16)+(255<<8)+(255);
		int hStretch = 20;
		int HaloPointIndex = tables.vertexTable.size();
		int HaloLineIndex = tables.edgeTable.size();
		int HaloPolygonIndex = tables.polygonTable.size();
		
		// Handle the outer Ring First
		tables.AddPoint(100-hStretch,50); // 0
		tables.AddPoint(125-hStretch,35); // 1
		tables.AddPoint(175+hStretch,35); // 2
		tables.AddPoint(200+hStretch,50); // 3
		tables.AddPoint(175+hStretch,65); // 4
		tables.AddPoint(125-hStretch,65); // 5
		tables.AddLine(HaloPointIndex+0,HaloPointIndex+1,col); // 0
		tables.AddLine(HaloPointIndex+1,HaloPointIndex+2,col); // 1
		tables.AddLine(HaloPointIndex+2,HaloPointIndex+3,col); // 2
		tables.AddLine(HaloPointIndex+3,HaloPointIndex+4,col); // 3
		tables.AddLine(HaloPointIndex+4,HaloPointIndex+5,col); // 4
		tables.AddLine(HaloPointIndex+5,HaloPointIndex+0,col); // 5
		
		// Now the inner Ring
		tables.AddPoint(110-hStretch,50); // 6
		tables.AddPoint(127-hStretch,40); // 7
		tables.AddPoint(173+hStretch,40); // 8
		tables.AddPoint(190+hStretch,50); // 9
		tables.AddPoint(173+hStretch,60); // 10
		tables.AddPoint(127-hStretch,60); // 11
		tables.AddLine(HaloPointIndex+6,HaloPointIndex+7,col); // 6
		tables.AddLine(HaloPointIndex+7,HaloPointIndex+8,col); // 7
		tables.AddLine(HaloPointIndex+8,HaloPointIndex+9,col); // 8
		tables.AddLine(HaloPointIndex+9,HaloPointIndex+10,col); // 9
		tables.AddLine(HaloPointIndex+10,HaloPointIndex+11,col); // 10
		tables.AddLine(HaloPointIndex+11,HaloPointIndex+6,col); // 11 
		
		// tables.AddPolygon(HaloLineIndex+0,HaloLineIndex+1,HaloLineIndex+6); // 0
		// tables.AddFilledSurface(new int[]{HaloLineIndex,HaloLineIndex+1,HaloLineIndex+2,HaloLineIndex+3,HaloLineIndex+4,HaloLineIndex+5},col);
	}
	
	/*public void AddHorns(){
	}
	
	public void AddGoodWings(){
	}
	
	public void AddBadWings(){
	}*/
	
	public void displayModel(){
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(300, 800, BufferedImage.TYPE_INT_RGB);
		BresenhamLineDrawer artist = new BresenhamLineDrawer();
		SimpleGraphicsPackage graphics = new SimpleGraphicsPackage();
		String[] blegh = new String[]{null};
		// Now we have to loop through the Model's tables to draw everything correctly.
		for(int line = 0;line < tables.edgeTable.size();line++){
			int[] newLine = new int[5];
			newLine[0] = tables.vertexTable.get(tables.edgeTable.get(line).get(0)).get(0);
			newLine[1] = tables.vertexTable.get(tables.edgeTable.get(line).get(0)).get(1);
			newLine[2] = tables.vertexTable.get(tables.edgeTable.get(line).get(1)).get(0);
			newLine[3] = tables.vertexTable.get(tables.edgeTable.get(line).get(1)).get(1);
			newLine[4] = tables.edgeTable.get(line).get(2);
			artist.BresenDraw(canvas,blegh,graphics.LineClipper(canvas,newLine));
		}
		for(int circle = 0;circle < tables.circleTable.size();circle++){
			int[] newCircle = new int[4];
			newCircle[0] = tables.circleTable.get(circle).get(0);
			newCircle[1] = tables.circleTable.get(circle).get(1);
			newCircle[2] = tables.circleTable.get(circle).get(2);
			newCircle[3] = tables.circleTable.get(circle).get(3);
			artist.BresenCircle(canvas,newCircle,true);
		}
		//The following two lines are cheater - eventually we'll make this part of proper filling
		artist.FloodFill(canvas,150,500,tables.edgeTable.get(0).get(2),canvas.getRGB(0,0));
		//artist.FloodFill(canvas,150,37,((190<<16)+(190<<8)+190),canvas.getRGB(0,0));
		frame.getContentPane().add(new JLabel(new ImageIcon(canvas)));
		frame.pack();
		frame.setVisible(true);
	}
	
	SoulModel(){
		/*Here's where we construct the base of the model. 
		For the moment, we're sticking with basics; Triangle, and Circle above it.
		Obviously, this will become a cone and a sphere once we're dealing with 3D,
		but since making the horns and halo is more of a pain in 3d, we'll keep it simple for now
		Scale is... unknown? Let's assume 200 pixels wide for the triangle. Overall model could be wider, but still easy*/
		
		//So how do we add to an ArrayList?
		//Adding a new row is done via NAME.add(new ArrayList<TYPE>());
		//How do we add to a row? possible NAME.get(ROW).add(INTEGER)
		tables.AddPoint(50,350);
		tables.AddPoint(250,350);
		tables.AddPoint(150,750);
		tables.AddLine(0,1,null);
		tables.AddLine(1,2,null);
		tables.AddLine(0,2,null);
		/*tables.AddPolygon(0,1,2);
		tables.surfaceTable.add(new ArrayList<Integer>());
		tables.surfaceTable.get(0).add(0);
		tables.surfaceTable.get(0).add(null);*/
		tables.AddFilledSurface(new int[]{0,1,2},null);
		tables.AddCircle(150,200,100,null);
	}
}