//import block
import java.util.Arrays;
//import java.util.Stream;

import java.util.Scanner;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

//import the LineDrawer - let's see how that goes.
// Question - do we want to call ApplyTransformation as part of each of the transformation subfunctions? Or as a step immediately afterwards in the main control system.
// More importantly, are we going to implement clipping?

public class SimpleGraphicsPackage{
	
	public static int[][] lineList = new int[0][5];
	public static int canvasWidth = 600;
	public static int canvasHeight = 600;
	public static boolean debugMode = false;
	public static boolean lineDisplay = false;
	
	public static double[][] BasicTranslate(Scanner sc, int Tx, int Ty){
		if(Tx==0 && Ty==0){
			boolean fixed = false;
			System.out.println("Please input the values the image should be translated by in the following format: \"Tx Ty\"");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					String[] splitCoords = coords.split(" ");
					Tx = Integer.parseInt(splitCoords[0]);
					Ty = Integer.parseInt(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Tx Ty\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Tx Ty\"");
				}
			}
		}
		double[][] matrix = new double[][]{{1,0,0},{0,1,0},{Tx,Ty,1}};
		if(debugMode){
			System.out.println("Transformation matrix for Basic Translate is:");
			System.out.println(Arrays.deepToString(matrix));
		}
		return matrix;
	}
	
	public static double[][] BasicScale(Scanner sc, double Sx, double Sy){
		if(Sx==1 && Sy==1){
			boolean fixed = false;
			System.out.println("Please input the values the image should be scaled by in the following format: \"Sx Sy\"");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					String[] splitCoords = coords.split(" ");
					Sx = Double.parseDouble(splitCoords[0]);
					Sy = Double.parseDouble(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
			}
		}
		double[][] matrix = new double[][]{{Sx,0,0},{0,Sy,0},{0,0,1}};
		if(debugMode){
			System.out.println("Transformation matrix for Basic Scale is:");
			System.out.println(Arrays.deepToString(matrix));
		}
		return matrix;
	}
	
	public static double[][] BasicRotate(Scanner sc, int theta){ //Double check how java innately expects inputs for cos, sin, etc...
		if(theta==0){
			boolean fixed = false;
			System.out.println("Please input the value the image should be rotated by in degrees (clockwise)");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					//String[] splitCoords = coords.split(" ");
					theta = Integer.parseInt(coords);
					//Sy = Double.parseDouble(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
			}
		}
		double[][] matrix = new double[][]{{Math.cos(Math.toRadians(theta)),-Math.sin(Math.toRadians(theta)),0},{Math.sin(Math.toRadians(theta)),Math.cos(Math.toRadians(theta)),0},{0,0,1}};
		if(debugMode){
			System.out.println("Transformation matrix for Basic Rotate is:");
			System.out.println(Arrays.deepToString(matrix));
		}
		return matrix;
	}
	
	public static double[][] CenteredScale(Scanner sc, double Sx, double Sy, int Cx, int Cy){
		if(Cx==0 && Cy==0){
			boolean fixed = false;
			System.out.println("Please input the point the image should be scaled around in the following format: \"Cx Cy\"");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					String[] splitCoords = coords.split(" ");
					Cx = Integer.parseInt(splitCoords[0]);
					Cy = Integer.parseInt(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Cx Cy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Cx Cy\"");
				}
			}
		}
		if(Sx==1 && Sy==1){
			boolean fixed = false;
			System.out.println("Please input the values the image should be scaled by in the following format: \"Sx Sy\"");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					String[] splitCoords = coords.split(" ");
					Sx = Double.parseDouble(splitCoords[0]);
					Sy = Double.parseDouble(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
			}
		}
		double[][] m1 = BasicTranslate(sc, -Cx,-Cy);
		double[][] m2 = BasicScale(sc, Sx,Sy);
		double[][] m3 = BasicTranslate(sc, Cx,Cy);
		double[][] m4 = MatrixMultiplier(m1,m2); // Possibly execute this by means of calling an auxiliary function that takes a list on n matrices, multiplies them sequentially
		double[][] matrix = MatrixMultiplier(m4,m3);
		if(debugMode){
			System.out.println("Transformation matrix for Centered Scale is:");
			System.out.println(Arrays.deepToString(matrix));
		}
		return matrix;
	}
	
	public static double[][] CenteredRotate(Scanner sc, int theta, int Cx, int Cy){
		if(Cx==0 && Cy==0){
			boolean fixed = false;
			System.out.println("Please input the point the image should be rotated about the following format: \"Cx Cy\"");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					String[] splitCoords = coords.split(" ");
					Cx = Integer.parseInt(splitCoords[0]);
					Cy = Integer.parseInt(splitCoords[1]);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Cx Cy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Cx Cy\"");
				}
			}
		}
		if(theta==0){
			boolean fixed = false;
			System.out.println("Please input the value the image should be rotated by in degrees (clockwise)");
			while(!fixed){
				try{
					String coords = sc.nextLine();
					theta = Integer.parseInt(coords);
					fixed = true;
				}
				catch(java.lang.NumberFormatException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
				catch(java.lang.ArrayIndexOutOfBoundsException e){
					System.out.println("Cannot interpret input. Please try again and int the following format: \"Sx Sy\"");
				}
			}
		}
		double[][] m1 = BasicTranslate(sc, -Cx,-Cy);
		double[][] m2 = BasicRotate(sc, theta);
		double[][] m3 = BasicTranslate(sc, Cx,Cy);
		double[][] m4 = MatrixMultiplier(m1,m2); // Possibly execute this by means of calling an auxiliary function that takes a list on n matrices, multiplies them sequentially
		double[][] matrix = MatrixMultiplier(m4,m3);
		if(debugMode){
			System.out.println("Transformation matrix for Centered Rotate is:");
			System.out.println(Arrays.deepToString(matrix));
		}
		return matrix;
	}
	
	public static void ApplyTransformation(double[][] transform){ // Take in the points, apply the transformation.
		for(int line=0;line<lineList.length;line++){
			double[][] end1 = new double[][]{{lineList[line][0],lineList[line][1],1}};
			double[][] end2 = new double[][]{{lineList[line][2],lineList[line][3],1}};
			end1 = MatrixMultiplier(end1, transform);
			end2 = MatrixMultiplier(end2, transform);
			lineList[line][0] = (int)end1[0][0];
			lineList[line][1] = (int)end1[0][1];
			lineList[line][2] = (int)end2[0][0];
			lineList[line][3] = (int)end2[0][1];
		}
	}
	
	public static void DisplayImage(){ // This line will be where an import of an existing java function is used.
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);
		BresenhamLineDrawer artist = new BresenhamLineDrawer();
		String[] args = new String[]{null};
		for(int line=0;line<lineList.length;line++){
			int[] clippedLine = LineClipper(canvas, lineList[line]);
			if(debugMode){
				System.out.println("");
				System.out.print("Drawing Line: ");
				System.out.println(Arrays.toString(clippedLine));
				System.out.println("");
			}
			if(clippedLine.length!=0){
				artist.BresenDraw(canvas,args,clippedLine);
			}
		}
		frame.getContentPane().add(new JLabel(new ImageIcon(canvas))); // Look up more regarding how ImageIcon works - this is sufficient for now, but best to know more.
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void /*int[][]*/ InputLines(Scanner sc, String lineFile){
		boolean success = false;
		if(lineFile==null){
			System.out.println("Please enter the path to the file listing the input lines");
			lineFile = sc.nextLine();
		}
		while(!success){
			try{
				String data = new String(Files.readAllBytes(Paths.get(lineFile)));
				String[] eachLine = data.split("\n");
				int[][] newLines = new int[eachLine.length][5];
				for(int line=0;line<eachLine.length;line++){
					String[] lineCoords = eachLine[line].split(" ");
					if(debugMode){
						System.out.println("Handling Line " + line);
					}
					for(int element=0;element<lineCoords.length;element++){ 
						if(debugMode){
							System.out.println("Handling coord " + element);
						}
						newLines[line][element] = (element!=lineCoords.length-1)?Integer.parseInt(lineCoords[element]):Integer.parseInt(lineCoords[element].substring(0,lineCoords[element].length()-1));
					}
				}
				if(lineList.length!=0){
					int[][] dummy = new int[lineList.length][5];
					dummy = lineList;
					lineList = new int[dummy.length+newLines.length][5];
					System.arraycopy(dummy,0,lineList,0,dummy.length);
					System.arraycopy(newLines,0,lineList,dummy.length,newLines.length);
				}
				else{
					lineList = new int[newLines.length][5]; //this is where we would need to adjust the concatenation code
					lineList=newLines;
				}
				if(debugMode)
					System.out.println(Arrays.deepToString(lineList));
				success = true;
			}
			catch(java.io.IOException ex){
				System.out.println("Could not find listed file. Please re-enter and try again");
				lineFile = sc.nextLine();
			}
		}
	}
	
	public static void AddLine(Scanner sc){
		boolean added = false;
		int[] newLine = new int[5];
		while(!added){
			System.out.println("Input the coordinates of the new line in the following format: ");
			System.out.println("\"X1 Y1 X2 Y2 Color\"");
			System.out.println("(If unsure of value for color, leave blank)");
			try{
				String coords = sc.nextLine();
				String[] splitCoords = coords.split(" ");
				for(int input=0;input<splitCoords.length;input++){
					if(debugMode)
						System.out.println("Handling Coord " + input + ": " + splitCoords[input]);
					newLine[input] = Integer.parseInt(splitCoords[input]);
				}
				if(splitCoords.length==4){
					boolean colored = false;
					System.out.println("Would you like to add a color to this line?");
					System.out.println("Yes		No");
					while(!colored){
						String userInput = sc.nextLine();
						if(userInput.equalsIgnoreCase("yes")){
							boolean validColor = false;
							System.out.println("Input the color values for the line in the following format:");
							System.out.println("R G B");
							while(!validColor){
								try{
									String[] colorChannels = sc.nextLine().split(" ");
									int r = Integer.parseInt(colorChannels[0]);
									int g = Integer.parseInt(colorChannels[1]);
									int b = Integer.parseInt(colorChannels[2]);
									if(r>255||g>255||b>255){
										System.out.println("Color channel value cannot exceed 255. Please reinput");
										continue;
									}
									int color =0;
									color += (r<<16);
									color += (g<<8);
									color += b;
									newLine[4] = color;
									validColor = true;
								}
								catch(java.lang.NumberFormatException e){
									System.out.println("Could not interpret input. Please use the following format:");
									System.out.println("R G B");
								}
							}
							colored = true;
						}
						else if(userInput.equalsIgnoreCase("no"))
							colored = true;
						else{
							System.out.println("Decision unrecognized. Please enter \"yes\" or \"no\"");
							System.out.println("");
						}
					}
				}
				else if(Integer.parseInt(splitCoords[3])>=(256*256*256)){
					System.out.println("Color index out of bounds. Input an appropriate index or leave blank");
					System.out.println("");
					continue;
				}
				added = true;
			}
			catch(java.lang.NumberFormatException e){
				System.out.println("Cannot interpret input. Please try again and input in the following format: \"X1 Y1 X2 Y2 Color\"");
			}
			catch(java.lang.ArrayIndexOutOfBoundsException e){
				System.out.println("Cannot interpret input. Please try again and input in the following format: \"X1 Y1 X2 Y2 Color\"");
			}
		}
		if(lineList.length!=0){
			int[][] dummy = new int[lineList.length][5];
			dummy = lineList;
			lineList = new int[dummy.length+1][5];
			int[][] newLines = new int[1][5];
			newLines[0] = newLine;
			if(debugMode){
				System.out.println(Arrays.deepToString(dummy));
				System.out.println(Arrays.deepToString(newLines));
			}
			System.arraycopy(dummy,0,lineList,0,dummy.length);
			System.arraycopy(newLines,0,lineList,dummy.length,1);
		}
		else{
			lineList = new int[1][5]; //this is where we would need to adjust the concatenation code
			lineList[0] = newLine;
		}
	}
	
	public static void OutputLines(Scanner sc, String outputName){
		if(outputName==null){
			boolean valid = false;
			File output = null;
			while(!valid){
				System.out.println("Please enter the filename you wish to write the lines to");
				outputName = sc.nextLine();
				// check if file exists
				output = new File(outputName);
				if(output.exists() && !output.isDirectory()){
					System.out.println("Filename already in use. Overwrite?:");
					System.out.println("Yes		No");
					String check = sc.nextLine();
					if(check.equalsIgnoreCase("yes")){
						valid = true;
					}
					else if(check.equalsIgnoreCase("no")){
						continue;
					}
					else{
						System.out.println("Could not recognize decision");
					}
				}
				else{
					valid = true;
				}
			}
			String outputString = "";
			for(int line = 0; line < lineList.length; line++){
				for(int coord = 0; coord < lineList[0].length; coord++){
					outputString += lineList[line][coord];
					if(coord < lineList[line].length-1)
						outputString += " ";
				}
				outputString += "\n";
			}
			//output is now a new file, or an existing one that you are comfortable overwriting
			String[] outputLines = outputString.split("\n");
			if(debugMode)
				System.out.println(outputString); // have validated that this is the correct string setup, although would need it in a text document to verify
			//System.out.println(output);
			BufferedWriter br = null;
			FileWriter fr = null;
			
			try{
				fr = new FileWriter(output);
				br = new BufferedWriter(fr);
				//fr.write(outputString);
				for(int line=0;line<outputLines.length;line++){
					br.write(outputLines[line]+System.getProperty("line.separator"));
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
			finally{
				try{
					br.close();
					fr.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
			//Path path = Paths.get(output);
			//Files.write(Paths.get(outputName),outputString);
		}
	}
	
	public static double[][] MatrixMultiplier(double[][] m1, double[][]m2){ //optional, but might be useful to speed up some of the operations.
		double[][] outputMatrix = new double[m1.length][m2[0].length];
		for(int row=0;row<m1.length;row++){
			for(int col=0;col<m2[0].length;col++){
				double sum = 0;
				for(int entry=0;entry<m2.length;entry++){
					sum += m1[row][entry] * m2[entry][col];
				}
				outputMatrix[row][col] = sum;
			}
		}
		return outputMatrix;
	}
	
	//This piece of shit is frustratingly important. How are we feeding inputs in, and what outputs are we looking for?
	public static int[] LineClipper(BufferedImage canvas, int[] fullLine){
		//most logical inputs will be a single line, returning a single line.
		if(debugMode){
			System.out.print("Handling line: ");
			System.out.println(Arrays.toString(fullLine));
			System.out.println("");
		}
		int[] end1 = new int[]{fullLine[0],fullLine[1]};
		int[] end2 = new int[]{fullLine[2],fullLine[3]};
		int top = 0; //reminder, above is less than this, 4th from right, ergo decimal value 8
		int left = 0; // Decimal value 1
		int bottom = canvas.getHeight()-1; //reminder, below is greater than this decimal value 4
		int right = canvas.getWidth()-1; // decimal value 2
		// how do we assign and initialize codes
		int code1 = 0;
		if(end1[0] < left)
			code1 += 1;
		else if(end1[0] > right)
			code1 += 2;
		if(end1[1] < top)
			code1 += 8;
		else if(end1[1] > bottom)
			code1 += 4;
		int code2 = 0;
		if(end2[0] < left)
			code2 += 1;
		else if(end2[0] > right)
			code2 += 2;
		if(end2[1] < top)
			code2 += 8;
		else if(end2[1] > bottom)
			code2 += 4;
		if(debugMode){
			System.out.println("");
			System.out.println("The two codes are " + code1 + " and " + code2);
			System.out.println("The OR'd code is: " + (code1|code2));
			System.out.println("The AND'd code is: " + (code1&code2));
			System.out.println("");
		}
		if((code1|code2)==0){
			if(debugMode){
				System.out.println("Guard 1: Entirely within Viewport. Return line");
				System.out.println("");
			}
			return fullLine;
		}
		else if((code1&code2)!=0){
			if(debugMode){
				System.out.println("Guard 2: Entirely outside Viewport. Return empty");
				System.out.println("");
			}
			return new int[0];
		}
		else{
			/*this is the ugly part where we're going to recurse.
			How are we going to find the intersections?
			Let's think - there are 12 total edges that can be intersected.
			We can try looping across all of those, or we can try and
			identify which edge is a viable candidate by looking at the codes
			
			Good news, we can indeed mathemetically identify which edge we are hoping to check the intersection with.
			'Horizontal' lines output either 1,2, or 3 depending on how far it stretches.
			1 = Left to mid, 2 = mid to right, 3=left to right
			4 = mid to bottom, 8 = top to mid, 12=top to bottom
			then there are the diagonals.*/
			if(debugMode)
				System.out.println("Recursive Case");
			if((((code1|code2) - (code1&code2))%2)==1){
				if(debugMode)
					System.out.println("Line crosses the Left edge");
				double slope = (double)(end2[1]-end1[1])/(double)(end2[0]-end1[0]); // Check the datatypes here - do we need doubles, or will ints work?
				int yCept = (int)(end2[1] - (slope*end2[0]));
				int intersect = (int)((slope*left) + yCept);
				if(debugMode)
					System.out.println("Crosses the intersection at " + left + ", " + intersect);//one subline is interior - the other is exterior. How do we quick calc which endpoint corresponds to which?
				int[] subline1 = new int[]{end1[0],end1[1],left,intersect,fullLine[4]};
				int[] subline2 = new int[]{left,intersect,end2[0],end2[1],fullLine[4]};
				if(end2[0]>end1[0]){
					subline1[2] = subline1[2]-1;
				}
				else{
					subline2[0] = subline2[0]-1;
				}
				int[] output1 = LineClipper(canvas, subline1);
				int[] output2 = LineClipper(canvas, subline2);
				if(output1.length!=0){
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output1));
					}
					return output1;
				}
				else{
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output2));
					}
					return output2;
				}
			}
			else if(((((code1|code2) - (code1&code2))/2)%4)==1){ //issue; line that crosses the top will catch this guard
				if(debugMode)
					System.out.println("Line crosses the Right edge");
				double slope = (double)(end2[1]-end1[1])/(double)(end2[0]-end1[0]); // Check the datatypes here - do we need doubles, or will ints work?
				int yCept = (int)(end2[1] - (slope*end2[0]));
				int intersect = (int)((slope*right) + yCept);
				if(debugMode)
					System.out.println("Crosses the intersection at " + right + ", " + intersect);//one subline is interior - the other is exterior. How do we quick calc which endpoint corresponds to which?
				int[] subline1 = new int[]{end1[0],end1[1],right,intersect,fullLine[4]};
				int[] subline2 = new int[]{right,intersect,end2[0],end2[1],fullLine[4]};
				if(end2[0]<end1[0]){
					subline1[2] = subline1[2]+1;
				}
				else{
					subline2[0] = subline2[0]+1;
				}
				int[] output1 = LineClipper(canvas, subline1);
				int[] output2 = LineClipper(canvas, subline2);
				if(output1.length!=0){
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output1));
					}
					return output1;
				}
				else{
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output2));
					}
					return output2;
				}
			}
			else if((((code1|code2) - (code1&code2))/8)==1){
				if(debugMode)
					System.out.println("Line crosses the Top edge");
				int intersect;
				if((end2[0]-end1[0])!=0){
					double slope = (double)(end2[1]-end1[1])/(double)(end2[0]-end1[0]); // Check the datatypes here - do we need doubles, or will ints work?
					int yCept = (int)(end2[1] - (slope*end2[0]));
					intersect = (int)((top-yCept)/slope);
				}
				else{
					intersect=end1[0];
				}
				if(debugMode)
					System.out.println("Crosses the intersection at " + intersect + ", " + top);//one subline is interior - the other is exterior. How do we quick calc which endpoint corresponds to which?
				int[] subline1 = new int[]{end1[0],end1[1],intersect,top,fullLine[4]};
				int[] subline2 = new int[]{intersect,top,end2[0],end2[1],fullLine[4]};
				if(end2[1]>end1[1]){
					subline1[3] = subline1[3]-1;
				}
				else{
					subline2[1] = subline2[1]-1;
				}
				int[] output1 = LineClipper(canvas, subline1);
				int[] output2 = LineClipper(canvas, subline2);
				if(output1.length!=0){
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output1));
					}
					return output1;
				}
				else{
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output2));
					}
					return output2;
				}
			}
			else if(((((code1|code2) - (code1&code2))/4)%8)==1){
				if(debugMode)
					System.out.println("Line crosses the Bottom edge");
				int intersect;
				if((end2[0]-end1[0])!=0){
					double slope = (double)(end2[1]-end1[1])/(double)(end2[0]-end1[0]); // Check the datatypes here - do we need doubles, or will ints work?
					int yCept = (int)(end2[1] - (slope*end2[0]));
					intersect = (int)((bottom-yCept)/slope);
				}
				else{
					intersect=end1[0];
				}
				if(debugMode)
					System.out.println("Crosses the intersection at " + intersect + ", " + bottom);//one subline is interior - the other is exterior. How do we quick calc which endpoint corresponds to which?
				int[] subline1 = new int[]{end1[0],end1[1],intersect,bottom,fullLine[4]};
				int[] subline2 = new int[]{intersect,bottom,end2[0],end2[1],fullLine[4]};
				if(end2[1]<end1[1]){
					subline1[3] = subline1[3]+1;
				}
				else{
					subline2[1] = subline2[1]+1;
				}
				int[] output1 = LineClipper(canvas, subline1);
				int[] output2 = LineClipper(canvas, subline2);
				if(output1.length!=0){
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output1));
					}
					return output1;
				}
				else{
					if(debugMode){
						System.out.print("Returning line for drawing: ");
						System.out.println(Arrays.toString(output2));
					}
					return output2;
				}
			}
			return new int[0];
		}
	}
	
	/*public static ToGeometricTable{ //possibly more trouble than it's worth, but worth considering implementing some means of splitting up the input table into vertex table and edge table
	}*/
	
	public static void HelpMessage(){
		
		System.out.println("");	
		System.out.println("");
		System.out.println("List of commands:");
		System.out.println("");
		System.out.println("0: Input Lines");
		System.out.println("1: Add Line");
		System.out.println("2: Display Image");
		System.out.println("3: Translate");
		System.out.println("4: Basic Scale");
		System.out.println("5: Basic Rotate");
		System.out.println("6: Centered Scale");
		System.out.println("7: Centered Rotate");
		System.out.println("8: Output Lines");
		System.out.println("9: Clear Lines");
		System.out.println("10: Quit");
		System.out.println("11: Options");
		System.out.println("");
		System.out.println("Input either the number or the command name to call a function (omit spaces between words).");
		System.out.println("To get an explanation of a function, add \"-help\" after the command");
		System.out.println("To redisplay this list, type \"help\" with no command attached");
		System.out.println("");
		System.out.println("");
	}
	
	public static void OptionsMenu(Scanner sc){
		System.out.println("");
		System.out.println("");
		System.out.println("0: Toggle Debug Mode");
		System.out.println("1: Set Canvas size");
		System.out.println("2: Toggle Line display");
		//System.out.println("3: Toggle Geometric tables");
		System.out.println("4: Exit options");
		System.out.println("");
		boolean keepGoing = true;
		while(keepGoing){
			System.out.println("Select option: ");
			String userInput = sc.nextLine();
			String[] split = userInput.toLowerCase().split(" ");
			if(Arrays.asList(split).contains("0") || Arrays.asList(split).contains("debug")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Activates or deactivates debug mode, printing a lot of behind the scenes information to the screen.");
					System.out.println("");
					System.out.println("");
				}
				else if(debugMode){
					debugMode = false;
					System.out.println("Turning off debug mode");
					System.out.println("You can still selectively activate debug mode for individual commands");
					System.out.println("by adding \"debug\" to the list of arguments");
					System.out.println("");
				}
				else{
					debugMode = true;
					System.out.println("Turning on debug mode");
					System.out.println("");
				}
			}
			else if(Arrays.asList(split).contains("1") || Arrays.asList(split).contains("canvas")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Allows you to adjust the size of the viewport that displays the lines");
					System.out.println("");
					System.out.println("");
				}
				else{
					boolean valid = false;
					System.out.println("Input the new size for the canvas in the format \"Width Height\"");
					System.out.println("");
					while(!valid){
						try{
							String dimensions = sc.nextLine();
							String[] splitDimensions = dimensions.split(" ");
							if(splitDimensions.length!=2){
								System.out.println("Invalid input. Input must consist of exactly 2 entries, separated by a space");
								System.out.println("");
								continue;
							}
							if(Integer.parseInt(splitDimensions[0])>0 && Integer.parseInt(splitDimensions[1])>0){
								canvasWidth = Integer.parseInt(splitDimensions[0]);
								canvasHeight = Integer.parseInt(splitDimensions[1]);
								valid = true;
								System.out.println("New Canvas dimensions have been set");
								System.out.println("");
							}
							else{
								System.out.println("Invalid input. Both Width and Height must be a positive number");
								System.out.println("");
								continue;
							}
						}
						catch(java.lang.NumberFormatException e){
							System.out.println("Cannot interpret input. Please try again and int the following format: \"Width Height\"");
						}
					}
					System.out.println("");
				}
			}
			else if(Arrays.asList(split).contains("2") || Arrays.asList(split).contains("display")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Activates or deactivates line display, outputting the coordinates of every line after every command.");
					System.out.println("");
					System.out.println("");
				}
				else if(lineDisplay){
					debugMode = false;
					System.out.println("Turning off line display");
					System.out.println("Note that line information will still be displayed if");
					System.out.println("debug mode is turned on, either for a function or globally");
					System.out.println("");
				}
				else{
					lineDisplay = true;
					System.out.println("Turning on line display");
					System.out.println("");
				}
			}
			/*else if(Arrays.asList(split).contains("3") || Arrays.asList(split).contains("geometric")){
			}*/
			else if(Arrays.asList(split).contains("4") || Arrays.asList(split).contains("exit") || Arrays.asList(split).contains("quit")){
				if(Arrays.asList(split).contains("help")){
				}
				else{
					System.out.println("Exiting options menu");
					keepGoing = false;
				}
			}
			else{
				System.out.println("Command not recognized");
				System.out.println("");
			}
		}
	}
	
	public static void main(String args[]){
		/*
		How are we implementing the main control setup? 
		Let's start by generating a print statement that lists the commands available - link each command input to either the typed command or a number.
		If an incorrect input is made, prompt a reinput.
		*/
		Scanner sc = new Scanner(System.in);
		
		HelpMessage();
		
		boolean keepGoing = true;
		while(keepGoing){
			System.out.println("Awaiting Command:");
			String userInput = sc.nextLine();
			userInput = userInput.toLowerCase();
			String[] split = userInput.split(" ");
			if(Arrays.asList(split).contains("0") || Arrays.asList(split).contains("inputlines")){ // Conclusion - split is useful for parsing additional arguments, but might be bad for commands that are multiple lines
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to load a file of lines.");
					System.out.println("You can either include the path to the file as part of the command input,");
					System.out.println("or be prompted for it inside of the command");
					System.out.println("");
					System.out.println("");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					// need a splitter here for identifying if a file was listed.
					InputLines(sc, null);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("1") || Arrays.asList(split).contains("addline")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Adds a line to the existing lineList.");
					System.out.println("");
					System.out.println("");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					AddLine(sc);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("2") || Arrays.asList(split).contains("displayimage")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to display the image.");
					System.out.println("Lines are drawn using the Bresnham Line Drawing Algorithm,");
					System.out.println("defaulting to white lines on a black background when the color");
					System.out.println("is not provided in the input file");
					System.out.println("");
					System.out.println("");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					DisplayImage();
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("3") || Arrays.asList(split).contains("basictranslate")){
				//Dummy spot for testing the behavior using the split list
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to move the endpoints of all lines in both the X and Y direction by a specified amount");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					// need a splitter here for whether the user provided translate values
					//System.out.println("We understand this is a call to Translate");
					double[][] transform = new double[3][3];
					transform = BasicTranslate(sc,0,0);
					ApplyTransformation(transform);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("4") || Arrays.asList(split).contains("basicscale")){
				//Dummy spot for testing the behavior using the split list
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to scale the endpoints of all lines in both the X and Y direction by a specified amount, relative to the origin");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					// need a splitter here for whether the user provided translate values
					//System.out.println("We understand this is a call to Translate");
					double[][] transform = new double[3][3];
					transform = BasicScale(sc,1,1);
					ApplyTransformation(transform);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("5") || Arrays.asList(split).contains("basicrotate")){
				//Dummy spot for testing the behavior using the split list
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to rotate the endpoints of all lines in both the X and Y direction by a specified amount, relative to the origin");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					// need a splitter here for whether the user provided translate values
					//System.out.println("We understand this is a call to Translate");
					double[][] transform = new double[3][3];
					transform = BasicRotate(sc,0);
					ApplyTransformation(transform);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("6") || Arrays.asList(split).contains("centeredscale")){
				//Dummy spot for testing the behavior using the split list
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to scale the endpoints of all lines in both the X and Y direction by a specified amount, relative to the a specified point");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					// need a splitter here for whether the user provided translate values
					//System.out.println("We understand this is a call to Translate");
					double[][] transform = new double[3][3];
					transform = CenteredScale(sc,1,1,0,0);
					ApplyTransformation(transform);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("7") || Arrays.asList(split).contains("centeredrotate")){
				//Dummy spot for testing the behavior using the split list
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to rotate the endpoints of all lines in both the X and Y direction by a specified amount, relative to the a specified point");
				}
				else{
					// need a splitter here for whether the user provided translate values
					//System.out.println("We understand this is a call to Translate");
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					double[][] transform = new double[3][3];
					transform = CenteredRotate(sc,0,0,0);
					ApplyTransformation(transform);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("8") || Arrays.asList(split).contains("outputlines")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to write the list of lines");
					System.out.println("to a specified file. For now, can only identify");
					System.out.println("from a later prompt");
					System.out.println("");
					System.out.println("");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					OutputLines(sc,null);
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("9") || Arrays.asList(split).contains("clearlines")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Clears all existing lines");
					System.out.println("");
					System.out.println("");
				}
				else{
					if(Arrays.asList(split).contains("debug"))
						debugMode = true;
					lineList = new int[0][5];
					System.out.println("");
					System.out.println("");
					if(Arrays.asList(split).contains("debug"))
						debugMode=false;
				}
			}
			else if(Arrays.asList(split).contains("10") || Arrays.asList(split).contains("quit") || Arrays.asList(split).contains("exit")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Instructs the program to close out");
					System.out.println("");
					System.out.println("");
				}
				else{
					keepGoing = false;
					System.out.println("");
					System.out.println("");
				}
			}
			else if(Arrays.asList(split).contains("11") || Arrays.asList(split).contains("options")){
				if(Arrays.asList(split).contains("help")){
					System.out.println("");
					System.out.println("Pulls up an options list for adjusting details");
					System.out.println("");
					System.out.println("");
				}
				else{
					OptionsMenu(sc);
					System.out.println("");
					System.out.println("");
					HelpMessage();
				}
			}
			else if(userInput.equals("help")){
				HelpMessage();
				System.out.println("");
				System.out.println("");
			}
			else{
				System.out.println("Command not recognized. Input \"help\" to get a list of commands");
				System.out.println("");
				System.out.println("");
			}
			if(lineDisplay||debugMode){
				System.out.println(Arrays.deepToString(lineList));
			}
		}
		System.out.println("Thank you for using the program. Have a nice day!");
	}
	
}

/* relevant citations:
https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space - For helping to manage arguments and optional arguments more efficiently
https://www.tutorialspoint.com/java/java_files_io.htm - How to do Java IO, for reading in a line file / outputting said file.
http://www.java67.com/2016/08/how-to-read-text-file-as-string-in-java.html - Other, more usable IO source
https://stackoverflow.com/questions/80476/how-can-i-concatenate-two-arrays-in-java - Guide for clean array concatenation, to cleanly grow linelist instead of replacing with each new read
https://www.programiz.com/java-programming/bitwise-operators - relevant for both the Clipping codes and possibly defining colors? Look into that 
https://www.journaldev.com/878/java-write-to-file - source on how to actually write a text string to file. Generating string from array not included
*/