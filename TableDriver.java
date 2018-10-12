// import block
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// package import block

public class TableDriver{
	public static void main(String[] args){
		GeometricTables tables = new GeometricTables();
		boolean outer=true;
		
		if(outer){
		//Outer
		tables.AddPoint(50,50);
		tables.AddPoint(150,25);
		tables.AddPoint(250,50);
		tables.AddPoint(250,250);
		tables.AddPoint(50,250);
		tables.AddLine(0,1,(256*256*256)-1);
		tables.AddLine(1,2,(256*256*256)-1);
		tables.AddLine(2,3,(256*256*256)-1);
		tables.AddLine(3,4,(256*256*256)-1);
		tables.AddLine(4,0,(256*256*256)-1);
		
		//Inner
		tables.AddPoint(150,100);
		tables.AddPoint(200,200);
		tables.AddPoint(100,200);
		tables.AddLine(5,6,(256*256*256)-1);
		tables.AddLine(6,7,(256*256*256)-1);
		tables.AddLine(7,5,(256*256*256)-1);
		
		tables.AddHoledSurface(new int[]{0,1,2,3,4},new int[]{5,6,7},(256*256*256)-1);
		}
		else{
		
		//Outer
		tables.AddPoint(150,50);
		tables.AddPoint(250,250);
		tables.AddPoint(50,250);
		tables.AddLine(0,1,(256*256*256)-1);
		tables.AddLine(1,2,(256*256*256)-1);
		tables.AddLine(2,0,(256*256*256)-1);
		
		//Inner
		tables.AddPoint(125,150);
		tables.AddPoint(175,150);
		tables.AddPoint(175,200);
		tables.AddPoint(125,200);
		tables.AddLine(3,4,(256*256*256)-1);
		tables.AddLine(4,5,(256*256*256)-1);
		tables.AddLine(5,6,(256*256*256)-1);
		tables.AddLine(6,3,(256*256*256)-1);
		
		tables.AddHoledSurface(new int[]{0,1,2},new int[]{3,4,5,6},(256*256*256)-1);}
		
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
		BresenhamLineDrawer artist = new BresenhamLineDrawer();
		SimpleGraphicsPackage graphics = new SimpleGraphicsPackage();
		String[] blegh = new String[]{null};
		// Now we have to loop through the Model's tables to draw everything correctly.
		System.out.println(tables.edgeTable);
		
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
		
		frame.getContentPane().add(new JLabel(new ImageIcon(canvas)));
		frame.pack();
		frame.setVisible(true);
	}
}