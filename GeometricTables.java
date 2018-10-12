// import block
import java.util.Arrays;
import java.util.ArrayList;

// package import block

public class GeometricTables{
	
	public ArrayList<ArrayList<Integer>> vertexTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> polygonTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> surfaceTable = new ArrayList<ArrayList<Integer>>();
	public ArrayList<ArrayList<Integer>> circleTable = new ArrayList<ArrayList<Integer>>();
	
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
	
	public void AddPolygon(Integer line1, Integer line2, Integer line3){//, Integer color){
		polygonTable.add(new ArrayList<Integer>());
		polygonTable.get(polygonTable.size()-1).add(line1);
		polygonTable.get(polygonTable.size()-1).add(line2);
		polygonTable.get(polygonTable.size()-1).add(line3);
		//polygonTable.get(polygonTable.size()-1).add(color);
	}
	
	public void AddCircle(Integer xCenter, Integer yCenter, Integer radius, Integer color){
		circleTable.add(new ArrayList<Integer>());
		circleTable.get(circleTable.size()-1).add(xCenter);
		circleTable.get(circleTable.size()-1).add(yCenter);
		circleTable.get(circleTable.size()-1).add(radius);
		circleTable.get(circleTable.size()-1).add(color);
	}
	
	public void AddFilledSurface(int[] edgeArray, Integer color){ // edge array is an array of indices in the edge table. For now, we assume the edgeArray is valid (IE clockwise, connected, etc...)
		// Takes in a set of edges describing the outline of a polygon, adds lines until it is composed of triangles,
		// add's those triangles to the polygon list, then adds all those triangles to a new surface
		// we can mathematically define the number of triangles within a given polyon as N-2
		ArrayList<Integer> polygon = new ArrayList<Integer>();
		for(int edge=0;edge<edgeArray.length;edge++)
			polygon.add(edgeArray[edge]);
		surfaceTable.add(new ArrayList<Integer>());
		while(polygon.size()>3){
			// Made this simple. While there are more than 3 edges, add a line to make a triangle with 2 of the edges, delete the 2 you used.
			AddLine(edgeTable.get(polygon.get(0)).get(0),edgeTable.get(polygon.get(1)).get(1),color);
			AddPolygon(polygon.get(0),polygon.get(1),edgeTable.size()-1);
			surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
			polygon.remove(0);
			polygon.remove(0);
			polygon.add(0,edgeTable.size()-1); // can make this 0,edge if we need it to be added to the beginning
		}
		//Add last triangle	
		AddPolygon(polygon.get(0),polygon.get(1),polygon.get(2));
		surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
		surfaceTable.get(surfaceTable.size()-1).add(color);
		//Assign color to the circle
	}
	
	public void AddHoledSurface(int[] outEdgeArray, int[] inEdgeArray, int color){
		// As above, but this is going to get really ugly. Takes in a set of edges representing outer polygon, and a set representing an inner polygon.
		ArrayList<Integer> outer = new ArrayList<Integer>();
		for(int edge=0;edge<outEdgeArray.length;edge++)
			outer.add(outEdgeArray[edge]);
		ArrayList<Integer> inner = new ArrayList<Integer>();
		for(int edge=0;edge<inEdgeArray.length;edge++)
			inner.add(inEdgeArray[edge]);
		// Let's get into some math theory bullshit. A surface defined by an N-gon with an N-gon hole inside has n (edges for internal, outer, points for internal, and outer).
		// The additional triangles needed to to reduce surface to a finite number of shapes is 2N, at least when N=6. Math test for N=3 (base case) - validated
		// it stands to reason then that a loop is feasible, since the known number of edges. We also need 2N Triangles.
		// How do we formulate the loop? let's Say instead we loop through 2N-1. The reason for this is that 2N-2 triangles involve adding 1 edge.
		// 1 requires 2 lines, and the final already has lines present. If we add 1 line before the loop, this becomes 2N-1 1-Edge Triangles. 
		// Can then do the last triangle easily.
		
		// Could we functionalize this?  Seems feasible for N-gon in N-gon. Can we generalize to N-gon in M-gon? If not, could always make case.
		// If internal has = or fewer edges, same code needed. If not, additional lines / triangles are needed.
		// End of the day, always equal number triangles and additional lines. Equal to N+M. Loop behavior might differ, but this is fundamental.
		// Could we run a loop on 2N-1 (or M, whichever is smaller), then have a finalize block that compensates?
		
		//Case 1: N = N; Might conjoin with others if this works
		// Turns out, either >= or > will work for the choice in the loop.
		// This can be joined with Outer being larger, as long as > is used.
		// Checking now about outer being smaller, but I expect that we'll need a case splitter.
		if(outer.size()==inner.size()){
			surfaceTable.add(new ArrayList<Integer>());
			AddLine(edgeTable.get(outer.get(0)).get(0),edgeTable.get(inner.get(0)).get(0),color);
			for(int ad=0;ad<(outEdgeArray.length+inEdgeArray.length-1);ad++){ // refer to the arrays, as the size of those are static, while we will be decrementing inner and outer
				if(outer.size()>=inner.size()){
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(outer.get(0)).get(1),color);
					outer.remove(0);
				}
				else{
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(inner.get(0)).get(1),color);
					inner.remove(0);
				}
			}
			System.out.println(outer);
		}
		// Case 2: Outer is more than inner
		else if(outer.size()>=inner.size()){
			surfaceTable.add(new ArrayList<Integer>());
			AddLine(edgeTable.get(outer.get(0)).get(0),edgeTable.get(inner.get(0)).get(0),color);
			for(int ad=0;ad<(outEdgeArray.length+inEdgeArray.length-1);ad++){ // refer to the arrays, as the size of those are static, while we will be decrementing inner and outer
				if(outer.size()>inner.size()){
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(outer.get(0)).get(1),color);
					outer.remove(0);
				}
				else{
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(inner.get(0)).get(1),color);
					inner.remove(0);
				}
			}
			System.out.println(outer);
		}
		// Case 3: Inner is more than outer
		else{
			surfaceTable.add(new ArrayList<Integer>());
			AddLine(edgeTable.get(inner.get(0)).get(0),edgeTable.get(outer.get(0)).get(0),color);
			for(int ad=0;ad<(outEdgeArray.length+inEdgeArray.length-1);ad++){ // refer to the arrays, as the size of those are static, while we will be decrementing inner and outer
				if(outer.size()<inner.size()){
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(inner.get(0)).get(1),color);
					inner.remove(0);
				}
				else{
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(outer.get(0)).get(1),color);
					outer.remove(0);
				}
			}
		}
	}
	
	// Could list an "Add Surface" Function, but a bit tricky with the number of surfaces
	
	GeometricTables(){
	}
}