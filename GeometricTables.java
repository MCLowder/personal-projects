// import block
import java.util.Arrays;
import java.util.ArrayList;

// package import block

public class GeometricTables{
	
	ArrayList<ArrayList<Integer>> vertexTable = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> edgeTable = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> polygonTable = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> surfaceTable = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<Integer>> circleTable = new ArrayList<ArrayList<Integer>>();
	
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
		
		// worth adding a check that makes sure the scape is properly connected
		// ie does edge 1 connect to edge 2 and so on and so forth.
		
		//I think step one is actually finding any convex vertices, drawing an edge from it, and recursing on the resulting sub polygons
		//boolean valid=true;
		// Build the correctly ordered EdgeArray
		ArrayList<ArrayList<Integer>> edgePoints = new ArrayList<ArrayList<Integer>>();
		edgePoints.add(new ArrayList<Integer>());
		if(edgeTable.get(edgeArray[1]).contains(edgeTable.get(edgeArray[0]).get(1))){
			// This is the expected setup
			edgePoints.get(0).add(edgeTable.get(edgeArray[0]).get(0));
			edgePoints.get(0).add(edgeTable.get(edgeArray[0]).get(1));
		}
		else if(edgeTable.get(edgeArray[1]).contains(edgeTable.get(edgeArray[0]).get(0))){
			// This is the backwards setup
			edgePoints.get(0).add(edgeTable.get(edgeArray[0]).get(1));
			edgePoints.get(0).add(edgeTable.get(edgeArray[0]).get(0));
		}
		else{
			// Already found a disconnected edge
			System.out.println("Edges are not connected. Each edge must connect to the next adjacent edge");
			return;
		}
		for(int edge=1;edge<edgeArray.length;edge++){
			if(edge==2){
				System.out.println(edgePoints);
				System.out.println(edgeTable.get(edgeArray[edge]));
				System.out.print("Looking for: ");
				System.out.println(edgePoints.get(edge-1).get(1));
			}
			if(!(edgeTable.get(edgeArray[edge]).contains(edgePoints.get(edge-1).get(1)))){
				System.out.println("Edges are not connected. Each edge must connect to the next adjacent edge");
				return;
			}
			else{
				edgePoints.add(new ArrayList<Integer>());
				if(edgeTable.get(edgeArray[edge]).get(0)==edgePoints.get(edge-1).get(1)){
					edgePoints.get(edge).add(edgeTable.get(edgeArray[edge]).get(0));
					edgePoints.get(edge).add(edgeTable.get(edgeArray[edge]).get(1));
				}
				else{
					edgePoints.get(edge).add(edgeTable.get(edgeArray[edge]).get(1));
					edgePoints.get(edge).add(edgeTable.get(edgeArray[edge]).get(0));
				}
			}
		}
		if(edgePoints.get(0).get(0)!=edgePoints.get(edgeArray.length-1).get(1)){
			System.out.println("Edges are not connected. Final edge must connect back to first edge");
			return;
		}
		
		System.out.println(edgePoints);
		int convex = -1;
		// Handle case where first point might be convex
		double sum = 0;
		sum += vertexTable.get(edgePoints.get(edgePoints.size()-1).get(0)).get(0) * (vertexTable.get(edgePoints.get(0).get(1)).get(1) - vertexTable.get(edgePoints.get(0).get(0)).get(1));
		sum += vertexTable.get(edgePoints.get(0).get(0)).get(0) * (vertexTable.get(edgePoints.get(edgePoints.size()-1).get(1)).get(1) - vertexTable.get(edgePoints.get(0).get(1)).get(1));
		sum += vertexTable.get(edgePoints.get(0).get(1)).get(0) * (vertexTable.get(edgePoints.get(0).get(0)).get(1) - vertexTable.get(edgePoints.get(edgePoints.size()-1).get(0)).get(1));
		if(sum<0)
			convex=0;
		System.out.println(sum);
		for(int point=1;(point<edgePoints.size()-1 && convex==-1);point++){
			/*if((edgeTable.get(edgeArray[point]).get(1)!=edgeTable.get(edgeArray[point+1]).get(0)) && (edgeTable.get(edgeArray[point]).get(1)!=edgeTable.get(edgeArray[point+1]).get(1)))
				valid=false;*/
			// how do we determine if an edge is convex?
			// https://stackoverflow.com/questions/17173633/determine-if-vertex-is-convex-help-understanding to the rescue!
			double areaSum = 0;
			areaSum += vertexTable.get(edgePoints.get(point-1).get(0)).get(0) * (vertexTable.get(edgePoints.get(point).get(1)).get(1) - vertexTable.get(edgePoints.get(point).get(0)).get(1));
			areaSum += vertexTable.get(edgePoints.get(point).get(0)).get(0) * (vertexTable.get(edgePoints.get(point-1).get(0)).get(1) - vertexTable.get(edgePoints.get(point).get(1)).get(1));
			areaSum += vertexTable.get(edgePoints.get(point).get(1)).get(0) * (vertexTable.get(edgePoints.get(point).get(0)).get(1) - vertexTable.get(edgePoints.get(point-1).get(0)).get(1));
			if(areaSum>0)
				convex=point;
		}
		
		if(convex!=-1){
			// Recursive case - add a line to make a triangle using the problem vertex,
			// recurse on triangle (trivial), then the remainder
			System.out.println("This surface has a convex edge!");
			System.out.println(convex);
			if(convex>1){
				AddLine(edgePoints.get(convex).get(0),edgePoints.get(convex-2).get(0),color);
				AddFilledSurface(new int[]{edgeArray[convex-2],edgeArray[convex-1],GetEdgeSize()-1}, color);
			}
			else if(convex==1){
				AddLine(edgeTable.get(edgeArray[convex]).get(1),edgeTable.get(edgeArray.length-1).get(0),color);
				AddFilledSurface(new int[]{edgeArray[edgeArray.length-1],edgeArray[convex],GetEdgeSize()-1},color);
			}
			else{
			}
			// Now how do we build the remainder of the surface's edges?
			int[] subEdges = new int[edgeArray.length-1];
			for(int edge=0;edge<subEdges.length;edge++){
				if(edge<convex-1)
					subEdges[edge]=edgeArray[edge];
				else if(edge==(convex-1))
					subEdges[edge]=GetEdgeSize()-1;
				else
					subEdges[edge] = edgeArray[edge+1];
			}
			//System.out.println(Arrays.toString(subEdges));
			AddFilledSurface(subEdges, color);
		}
		else{
			// Let's change this up slightly - polygon is now 2d, so we can order edges the way we need to
			if(edgeArray.length>0)
				return;
			ArrayList<Integer> polygon = new ArrayList<Integer>();
			for(int edge=0;edge<edgeArray.length;edge++){
				//polygon.add(new ArrayList<Integer>());
				polygon.add(edgeArray[edge]);
			}
			surfaceTable.add(new ArrayList<Integer>());
			while(polygon.size()>3){
				// Made this simple. While there are more than 3 edges, add a line to make a triangle with 2 of the edges, delete the 2 you used.
				AddLine(edgeTable.get(polygon.get(0)).get(0),edgeTable.get(polygon.get(1)).get(1),color);
				AddPolygon(polygon.get(0),polygon.get(1),edgeTable.size()-1);
				surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
				polygon.remove(0);
				polygon.remove(0);
				polygon.add(edgeTable.size()-1); // can make this 0,edge if we need it to be added to the beginning
			}
			//Add last triangle	
			AddPolygon(polygon.get(0),polygon.get(1),polygon.get(2));
			surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
			surfaceTable.get(surfaceTable.size()-1).add(color);
			//Assign color to the circle
		}
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
			int addedLine = edgeTable.size();
			AddLine(edgeTable.get(outer.get(0)).get(0),edgeTable.get(inner.get(0)).get(0),color);
			for(int ad=0;ad<(outEdgeArray.length+inEdgeArray.length-1);ad++){ // refer to the arrays, as the size of those are static, while we will be decrementing inner and outer
				if(outer.size()>=inner.size()){
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(outer.get(0)).get(1),color);
					AddPolygon(outer.get(0),edgeTable.size()-2,edgeTable.size()-1);
					surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
					outer.remove(0);
				}
				else{
					AddLine(edgeTable.get(edgeTable.size()-1).get(1),edgeTable.get(inner.get(0)).get(1),color);
					AddPolygon(inner.get(0),edgeTable.size()-2,edgeTable.size()-1);
					surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
					inner.remove(0);
				}
			}
			AddPolygon(addedLine,edgeTable.size()-1,inEdgeArray[inEdgeArray.length-1]);
			surfaceTable.get(surfaceTable.size()-1).add(polygonTable.size()-1);
			surfaceTable.get(surfaceTable.size()-1).add(color);
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
	
	// Series of methods for accessing the full table data, for making stuff private.
	public ArrayList<ArrayList<Integer>> GetVertexTable(){
		return vertexTable;
	}
	
	public ArrayList<ArrayList<Integer>> GetEdgeTable(){
		return edgeTable;
	}
	
	public ArrayList<ArrayList<Integer>> GetPolygonTable(){
		return polygonTable;
	}
	
	public ArrayList<ArrayList<Integer>> GetSurfaceTable(){
		return surfaceTable;
	}
	
	public ArrayList<ArrayList<Integer>> GetCircleTable(){
		return circleTable;
	}
	
	public int GetVertexSize(){
		return vertexTable.size();
	}
	
	public int GetEdgeSize(){
		return edgeTable.size();
	}
	
	public int GetPolygonSize(){
		return polygonTable.size();
	}
	
	public int GetSurfaceSize(){
		return surfaceTable.size();
	}
	
	public int GetCircleSize(){
		return circleTable.size();
	}
	
	// Sets the specified edge to the specified color
	public void SetEdgeColor(int edge, int color){
		edgeTable.get(edge).set(2,color);
	}
	
	// Sets the specified surface to the specified color
	public void SetSurfaceColor(int surface, int color){
		surfaceTable.get(surface).set(surfaceTable.get(surface).size()-1,color);
	}
	
	// Sets the specified circle to the specified color
	public void SetCircleColor(int circle, int color){
		circleTable.get(circle).set(3,color);
	}
	
	public ArrayList<Integer> GetEdge(int edge){
		ArrayList<Integer> grabbedEdge = new ArrayList<Integer>();
		grabbedEdge.add(vertexTable.get(edgeTable.get(edge).get(0)).get(0));
		grabbedEdge.add(vertexTable.get(edgeTable.get(edge).get(0)).get(1));
		grabbedEdge.add(vertexTable.get(edgeTable.get(edge).get(1)).get(0));
		grabbedEdge.add(vertexTable.get(edgeTable.get(edge).get(1)).get(1));
		grabbedEdge.add(edgeTable.get(edge).get(2));
		return grabbedEdge;
	}
	
	public ArrayList<Integer> GetCircle(int circle){
		return circleTable.get(circle);
	}
	
	GeometricTables(){
	}
}