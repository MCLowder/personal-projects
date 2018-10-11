// import block
import java.util.ArrayList;

// package import block

public class SoulModel{
	
	public ArrayList<ArrayList<Integer>> vertexTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
	public int[][] polygonTable = new int[0][4]; //Traditionally, this would be a 3 wide table, referenced in the below table calling color.
	// public int[][] surfaceTable = new int[][]; // Since this more or less requires both dimensions to be variable, bit of a pain in java. If we get the ArrayList working, might be included later.
	public ArrayList<ArrayList<Integer>> circleTable = new ArrayList<ArrayList<Integer>>();
	// When 3d becomes a thing, this is where we start adding to it
	
	/*public void AddHalo(){
	}
	
	public void AddHorns(){
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
		AddCircle(150,200,100,null);
	}
}