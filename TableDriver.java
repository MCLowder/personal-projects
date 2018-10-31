// import block
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// package import block

public class TableDriver{
	public static void main(String[] args){
		GeometricTables tables = new GeometricTables();
		/*boolean same = true;
		boolean outer=true;
		
		if(same){
			//Outer
		tables.AddPoint(50,50);
		tables.AddPoint(250,50);
		tables.AddPoint(250,250);
		tables.AddPoint(50,250);
		tables.AddLine(0,1,(256*256*256)-1);
		tables.AddLine(1,2,(256*256*256)-1);
		tables.AddLine(2,3,(256*256*256)-1);
		tables.AddLine(3,0,(256*256*256)-1);
		
		//Inner
		tables.AddPoint(100,100);
		tables.AddPoint(200,100);
		tables.AddPoint(200,200);
		tables.AddPoint(100,200);
		tables.AddLine(4,5,(256*256*256)-1);
		tables.AddLine(5,6,(256*256*256)-1);
		tables.AddLine(6,7,(256*256*256)-1);
		tables.AddLine(7,4,(256*256*256)-1);
		
		tables.AddHoledSurface(new int[]{0,1,2,3},new int[]{4,5,6,7},(256*256*256)-1);
		}
		else{
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
		}*/
		int hornColor = (85<<16)+(85<<8)+(85);
		int hornPointIndex = tables.vertexTable.size();
		int hornLineIndex = tables.edgeTable.size();
		int scale = 2;
		// Left Horn
		tables.AddPoint(100*scale,65*scale); // 0 - For now, these are clearly the wrong points. but it's a test for building the shape, adjusting it, then moving to fit
		tables.AddPoint(95*scale,55*scale); // 1
		tables.AddPoint(100*scale,45*scale); // 2
		tables.AddPoint(120*scale,25*scale); // 3
		tables.AddPoint(110*scale,40*scale); // 4
		tables.AddPoint(110*scale,50*scale); // 5
		tables.AddPoint(115*scale,60*scale); // 6
		tables.AddLine(hornPointIndex+0,hornPointIndex+1,hornColor); // 0
		tables.AddLine(hornPointIndex+1,hornPointIndex+2,hornColor); // 1
		tables.AddLine(hornPointIndex+2,hornPointIndex+3,hornColor); // 2
		tables.AddLine(hornPointIndex+3,hornPointIndex+4,hornColor); // 3
		tables.AddLine(hornPointIndex+4,hornPointIndex+5,hornColor); // 4
		tables.AddLine(hornPointIndex+5,hornPointIndex+6,hornColor); // 5
		tables.AddLine(hornPointIndex+6,hornPointIndex+0,hornColor); // 6
		tables.AddFilledSurface(new int[]{hornLineIndex+0,hornLineIndex+1,hornLineIndex+2,hornLineIndex+3,hornLineIndex+4,hornLineIndex+5,hornLineIndex+6}, hornColor);
		
		Artist artist = new Artist(tables);
		artist.DrawAllTheThings();
		artist.DisplayCanvas();
	}
}