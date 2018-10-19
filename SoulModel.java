// import block
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// package import block

public class SoulModel{
	
	GeometricTables tables = new GeometricTables();
	
	// Updates the core elements of the body to be the new color.
	// NOTE: ATM, this loops through everything so as to make it easy. However, shouldn't we know which lines these are?
	// They should always be the first, so we should be able to fix this without bloating too much later.
	public void UpdateBaseColor(int color){
		for(int line=0; line < 3/*tables.GetEdgeSize()*/; line++)
			tables.SetEdgeColor(line,color);
		//for(int surface=0; surface < tables.GetSurfaceSize(); surface++)
			tables.SetSurfaceColor(0,color);
		//for(int circle=0; circle < tables.GetCircleSize(); circle++)
			tables.SetCircleColor(0,color);
	}
	
	public void AddHalo(){
		/*Hardest part of tyhis is just defining the halo conceptually.
		For now, thet's define the width to be from 100 to 200.
		The vertical range should be smaller - maybe 60 or so pixels total?
		Tolerable range for the halo is 0-100, so declare the midpoint to be 50
		For simplicities sake, let it start out being represented by a simple line*/
		
		int col1 = (255<<16)+(255<<8)+(255);
		int col2 = (255<<16)+(255<<8)+(190);
		int hStretch = 20;
		int HaloPointIndex = tables.GetVertexSize();
		int HaloLineIndex = tables.GetEdgeSize();
		
		// Handle the outer Ring First
		tables.AddPoint(100-hStretch,50); // 0
		tables.AddPoint(125-hStretch,35); // 1
		tables.AddPoint(175+hStretch,35); // 2
		tables.AddPoint(200+hStretch,50); // 3
		tables.AddPoint(175+hStretch,65); // 4
		tables.AddPoint(125-hStretch,65); // 5
		tables.AddLine(HaloPointIndex+0,HaloPointIndex+1,col1); // 0
		tables.AddLine(HaloPointIndex+1,HaloPointIndex+2,col1); // 1
		tables.AddLine(HaloPointIndex+2,HaloPointIndex+3,col1); // 2
		tables.AddLine(HaloPointIndex+3,HaloPointIndex+4,col1); // 3
		tables.AddLine(HaloPointIndex+4,HaloPointIndex+5,col1); // 4
		tables.AddLine(HaloPointIndex+5,HaloPointIndex+0,col1); // 5
		
		// Now the inner Ring
		tables.AddPoint(110-hStretch,50); // 6
		tables.AddPoint(127-hStretch,40); // 7
		tables.AddPoint(173+hStretch,40); // 8
		tables.AddPoint(190+hStretch,50); // 9
		tables.AddPoint(173+hStretch,60); // 10
		tables.AddPoint(127-hStretch,60); // 11
		tables.AddLine(HaloPointIndex+6,HaloPointIndex+7,col1); // 6
		tables.AddLine(HaloPointIndex+7,HaloPointIndex+8,col1); // 7
		tables.AddLine(HaloPointIndex+8,HaloPointIndex+9,col1); // 8
		tables.AddLine(HaloPointIndex+9,HaloPointIndex+10,col1); // 9
		tables.AddLine(HaloPointIndex+10,HaloPointIndex+11,col1); // 10
		tables.AddLine(HaloPointIndex+11,HaloPointIndex+6,col1); // 11 
		
		tables.AddHoledSurface(new int[]{HaloLineIndex,HaloLineIndex+1,HaloLineIndex+2,HaloLineIndex+3,HaloLineIndex+4,HaloLineIndex+5},new int[]{HaloLineIndex+6,HaloLineIndex+7,HaloLineIndex+8,HaloLineIndex+9,HaloLineIndex+10,HaloLineIndex+11},col2);
		// tables.AddPolygon(HaloLineIndex+0,HaloLineIndex+1,HaloLineIndex+6); // 0
		// tables.AddFilledSurface(new int[]{HaloLineIndex,HaloLineIndex+1,HaloLineIndex+2,HaloLineIndex+3,HaloLineIndex+4,HaloLineIndex+5},col);
	}
	
	public void AddHorns(){
		//Define some of the stuff we're working with
		int hornColor = (85<<16)+(85<<8)+(85);
		int hornPointIndex = tables.GetVertexSize();
		int hornLineIndex = tables.GetEdgeSize();
		// Left Horn
		tables.AddPoint(100,65); // 0 - For now, these are clearly the wrong points. but it's a test for building the shape, adjusting it, then moving to fit
		tables.AddPoint(95,45); // 1
		tables.AddPoint(115,25); // 2
		tables.AddPoint(110,40); // 3
		tables.AddPoint(120,60); // 4
		tables.AddLine(hornPointIndex+0,hornPointIndex+1,hornColor); // 0
		tables.AddLine(hornPointIndex+1,hornPointIndex+2,hornColor); // 1
		tables.AddLine(hornPointIndex+2,hornPointIndex+3,hornColor); // 2
		tables.AddLine(hornPointIndex+3,hornPointIndex+4,hornColor); // 3
		tables.AddLine(hornPointIndex+4,hornPointIndex+0,hornColor); // 4
		tables.AddFilledSurface(new int[]{hornLineIndex+0,hornLineIndex+1,hornLineIndex+2,hornLineIndex+3,hornLineIndex+4}, hornColor);
		// Right Horn
	}
	
	/*public void AddGoodWings(){
	}
	
	public void AddBadWings(){
	}*/
	
	// This needs to be updated to the more proper 'encapsulated class' setup
	public void displayModel(int[] lightsource){
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(300, 800, BufferedImage.TYPE_INT_RGB);
		BresenhamLineDrawer artist = new BresenhamLineDrawer();
		SimpleGraphicsPackage graphics = new SimpleGraphicsPackage();
		String[] blegh = new String[]{null};
		// Now we have to loop through the Model's tables to draw everything correctly.
		/*for(int line = 0;line < tables.edgeTable.size();line++){
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
		}*/
		//The following two lines are cheater - eventually we'll make this part of proper filling
		// artist.FloodFill(canvas,150,500,tables.edgeTable.get(0).get(2),canvas.getRGB(0,0));
		// artist.FloodFill(canvas,150,37,((190<<16)+(190<<8)+190),canvas.getRGB(0,0));
		// artist.YXFill(canvas,this.tables,lightsource);
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
		tables.AddLine(2,0,null);
		/*tables.AddPolygon(0,1,2);
		tables.surfaceTable.add(new ArrayList<Integer>());
		tables.surfaceTable.get(0).add(0);
		tables.surfaceTable.get(0).add(null);*/
		tables.AddFilledSurface(new int[]{0,1,2},null);
		tables.AddCircle(150,200,100,null);
	}
}