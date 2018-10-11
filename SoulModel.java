// import block
import java.util.ArrayList;

// package import block

public class SoulModel{
	
	public ArrayList<ArrayList<Integer>> vertexTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> polygonTable = new ArrayList<ArrayList<Integer>>();
	// public int[][] polygonTable = new int[0][4]; // Traditionally, this would be a 3 wide table, referenced in the below table calling color.
	// public int[][] surfaceTable = new int[][]; // Since this more or less requires both dimensions to be variable, bit of a pain in java. If we get the ArrayList working, might be included later.
	public ArrayList<ArrayList<Integer>> circleTable = new ArrayList<ArrayList<Integer>>();
	// When 3d becomes a thing, this is where we start adding to it
	
	public void AddHalo(){
		/*Hardest part of tyhis is just defining the halo conceptually.
		For now, thet's define the width to be from 100 to 200.
		The vertical range should be smaller - maybe 60 or so pixels total?
		Tolerable range for the halo is 0-100, so declare the midpoint to be 50
		For simplicities sake, let it start out being represented by a simple line*/
		
		int col = (255<<16)+(255<<8)+(255);
		int HaloPointIndex = vertexTable.size();
		int HaloLineIndex = edgeTable.size();
		int HaloPolygonIndex = polygonTable.size();
		
		// Handle the outer Ring First
		AddPoint(100,50);
		AddPoint(125,35);
		AddPoint(175,35);
		AddPoint(200,50);
		AddPoint(175,65);
		AddPoint(125,65);
		AddLine(HaloPointIndex+0,HaloPointIndex+1,col);
		AddLine(HaloPointIndex+1,HaloPointIndex+2,col);
		AddLine(HaloPointIndex+2,HaloPointIndex+3,col);
		AddLine(HaloPointIndex+3,HaloPointIndex+4,col);
		AddLine(HaloPointIndex+4,HaloPointIndex+5,col);
		AddLine(HaloPointIndex+5,HaloPointIndex+0,col);
		
		// Now the inner Ring
		AddPoint(110,50);
		AddPoint(127,40);
		AddPoint(173,40);
		AddPoint(190,50);
		AddPoint(173,60);
		AddPoint(127,60);
		AddLine(HaloPointIndex+6,HaloPointIndex+7,col);
		AddLine(HaloPointIndex+7,HaloPointIndex+8,col);
		AddLine(HaloPointIndex+8,HaloPointIndex+9,col);
		AddLine(HaloPointIndex+9,HaloPointIndex+10,col);
		AddLine(HaloPointIndex+10,HaloPointIndex+11,col);
		AddLine(HaloPointIndex+11,HaloPointIndex+6,col);
		
		// Now Triangulate and declare the surfaces
	}
	
	/*public void AddHorns(){
	}
	
	public void AddGoodWings(){
	}
	
	public void AddBadWings(){
	}*/
	
	public void AddPoint(Integer x, Integer y){
		vertexTable.add(new ArrayList<Integer>());
		vertexTable.get(vertexTable.size()-1).add(x);
		vertexTable.get(vertexTable.size()-1).add(y);
	}
	
	public void AddLine(Integer endIndex1, Integer endIndex2, Integer color){
		edgeTable.add(new ArrayList<Integer>());
		edgeTable.get(edgeTable.size()-1).add(endIndex1);
		edgeTable.get(edgeTable.size()-1).add(endIndex2);
		edgeTable.get(edgeTable.size()-1).add(color);
	}
	
	public void AddPolygon(Integer line1, Integer line2, Integer line3, Integer color){
		polygonTable.add(new ArrayList<Integer>());
		polygonTable.get(polygonTable.size()-1).add(line1);
		polygonTable.get(polygonTable.size()-1).add(line2);
		polygonTable.get(polygonTable.size()-1).add(line3);
		polygonTable.get(polygonTable.size()-1).add(color);
	}
	
	public void AddCircle(Integer xCenter, Integer yCenter, Integer radius, Integer color){
		circleTable.add(new ArrayList<Integer>());
		circleTable.get(circleTable.size()-1).add(xCenter);
		circleTable.get(circleTable.size()-1).add(yCenter);
		circleTable.get(circleTable.size()-1).add(radius);
		circleTable.get(circleTable.size()-1).add(color);
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
		AddPoint(50,350);
		AddPoint(250,350);
		AddPoint(150,750);
		AddLine(0,1,null);
		AddLine(1,2,null);
		AddLine(0,2,null);
		AddPolygon(0,1,2,null);
		AddCircle(150,200,100,null);
	}
}