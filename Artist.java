// import block
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

// package import block

// Let's just make all of our lives simpler. This will be where are scan-conversion happens.
// Set it up to properly use ArrayLists, handle lines, circles, and filling.
public class Artist{
	// Class Variable block
	private int CANVAS_HEIGHT = 800;
	private int CANVAS_WIDTH = 800;
	private BufferedImage canvas;
	private GeometricTables tables;
	private JFrame frame = new JFrame("Line Drawer");
	
	// Draws all of the lines in the surface
	public void DrawAllLines(){
		// How do we want our control setup to look? 
		// This is where the actual methods for accessing Table data get particularly relevant.
		// Let's propose a re-write to our current "Get" functions.
		// Rather than returning the table, return the relevant details of the table - all the ends and color;
		// Control loop works fine with the current methods
		for(int line = 0; line < tables.GetEdgeSize(); line++){
			DrawLine(line);
		}
	}
	
	public void DrawLine(int line){
		ArrayList<Integer> lineToDraw = tables.GetEdge(line);
		lineToDraw = ClipLine(lineToDraw);
		// Single Pixel Lines
		if((lineToDraw.get(0)==lineToDraw.get(2))&&(lineToDraw.get(1)==lineToDraw.get(3)))
			canvas.setRGB(lineToDraw.get(0),lineToDraw.get(1),lineToDraw.get(4));
		//Vertical Lines
		else if(lineToDraw.get(0)==lineToDraw.get(2)){
			if(lineToDraw.get(3)>lineToDraw.get(1)){
				for(int pix = lineToDraw.get(1); pix < lineToDraw.get(3); pix++)
					canvas.setRGB(lineToDraw.get(0),pix,lineToDraw.get(4));
			}
			else{
				for(int pix = lineToDraw.get(1); pix > lineToDraw.get(3); pix--)
					canvas.setRGB(lineToDraw.get(0),pix,lineToDraw.get(4));
			}
		}
		// Horizontal Lines
		else if(lineToDraw.get(1)==lineToDraw.get(3)){
			if(lineToDraw.get(2)>lineToDraw.get(0)){
				for(int pix = lineToDraw.get(0); pix < lineToDraw.get(2); pix++)
					canvas.setRGB(pix,lineToDraw.get(1),lineToDraw.get(4));
			}
			else{
				for(int pix = lineToDraw.get(0); pix > lineToDraw.get(2); pix--)
					canvas.setRGB(pix,lineToDraw.get(1),lineToDraw.get(4));
			}
		}
		// Diagonal Lines
		else if(Math.abs(lineToDraw.get(3)-lineToDraw.get(1))==Math.abs(lineToDraw.get(2)-lineToDraw.get(0))){
			// +dY
			if(lineToDraw.get(3)>lineToDraw.get(1)){
				// +dX
				if(lineToDraw.get(2)>lineToDraw.get(0)){
					for(int pix=0;pix<(lineToDraw.get(3)-lineToDraw.get(1));pix++)
						canvas.setRGB(lineToDraw.get(0)+pix,lineToDraw.get(1)+pix,lineToDraw.get(4));
				}
				else{
					for(int pix=0;pix<(lineToDraw.get(3)-lineToDraw.get(1));pix++)
						canvas.setRGB(lineToDraw.get(0)-pix,lineToDraw.get(1)+pix,lineToDraw.get(4));
				}
			}
			else{
				if(lineToDraw.get(2)>lineToDraw.get(0)){
					for(int pix=0;pix>(lineToDraw.get(3)-lineToDraw.get(1));pix--)
						canvas.setRGB(lineToDraw.get(0)-pix,lineToDraw.get(1)+pix,lineToDraw.get(4));
				}
				else{
					for(int pix=0;pix>(lineToDraw.get(3)-lineToDraw.get(1));pix--)
						canvas.setRGB(lineToDraw.get(0)+pix,lineToDraw.get(1)+pix,lineToDraw.get(4));
				}
			}
		}
		// Major X
		else if(Math.abs(lineToDraw.get(3)-lineToDraw.get(1))<Math.abs(lineToDraw.get(2)-lineToDraw.get(0))){
			int error = (2*Math.abs(lineToDraw.get(3)-lineToDraw.get(1)))-Math.abs(lineToDraw.get(2)-lineToDraw.get(0));
			int inc1 = 2*Math.abs(lineToDraw.get(3)-lineToDraw.get(1));
			int inc2 = 2*(Math.abs(lineToDraw.get(3)-lineToDraw.get(1))-Math.abs(lineToDraw.get(2)-lineToDraw.get(0)));
			int x = lineToDraw.get(0);
			int y = lineToDraw.get(1);
			canvas.setRGB(x,y,lineToDraw.get(4));
			if(lineToDraw.get(2)>lineToDraw.get(0)){
				if(lineToDraw.get(3)>lineToDraw.get(1)){
					while(x<lineToDraw.get(2)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							y++;
						}
						x++;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
				else{
					while(x<lineToDraw.get(2)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							y--;
						}
						x++;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
			}
			else{
				if(lineToDraw.get(3)>lineToDraw.get(1)){
					while(x>lineToDraw.get(2)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							y++;
						}
						x--;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
				else{
					while(x>lineToDraw.get(2)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							y--;
						}
						x--;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
			}
		}
		// Major Y
		else if(Math.abs(lineToDraw.get(3)-lineToDraw.get(1))>Math.abs(lineToDraw.get(2)-lineToDraw.get(0))){
			int error = (2*Math.abs(lineToDraw.get(2)-lineToDraw.get(0)))-Math.abs(lineToDraw.get(3)-lineToDraw.get(1));
			int inc1 = 2*Math.abs(lineToDraw.get(2)-lineToDraw.get(0));
			int inc2 = 2*(Math.abs(lineToDraw.get(2)-lineToDraw.get(0))-Math.abs(lineToDraw.get(3)-lineToDraw.get(1)));
			int x = lineToDraw.get(0);
			int y = lineToDraw.get(1);
			canvas.setRGB(x,y,lineToDraw.get(4));
			if(lineToDraw.get(3)>lineToDraw.get(1)){
				if(lineToDraw.get(2)>lineToDraw.get(0)){
					while(y<lineToDraw.get(3)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							x++;
						}
						y++;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
				else{
					while(y<lineToDraw.get(3)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							x--;
						}
						y++;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
			}
			else{
				if(lineToDraw.get(2)>lineToDraw.get(0)){
					while(y>lineToDraw.get(3)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							x++;
						}
						y--;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
				else{
					while(y>lineToDraw.get(3)){
						if(error<0)
							error += inc1;
						else{
							error += inc2;
							x--;
						}
						y--;
						canvas.setRGB(x,y,lineToDraw.get(4));
					}
				}
			}
		}
		else
			System.out.println("BROKEN!");
	}
	// Draws all of the Circles
	public void DrawAllCircles(boolean fill){
		for(int circle=0;circle<tables.GetCircleSize();circle++){
			DrawCircle(circle, fill);
		}
	}
	
	public void DrawCircle(int circle, boolean fill){
		ArrayList<Integer> circleToDraw = tables.GetCircle(circle);
		int x = 0;
		int y = circleToDraw.get(2);
		int p = 3 - (2*circleToDraw.get(2));
		while(x<y){
			DrawCirclePoints(x,circleToDraw.get(0),y,circleToDraw.get(1),circleToDraw.get(3));
			if(p<0){
				p += ((4*x)+6);
			}
			else{
				p += ((4*(x-y))+10);
				y--;
			}
			x++;
		}
		DrawCirclePoints(x,circleToDraw.get(0),y,circleToDraw.get(1),circleToDraw.get(3));
		if(fill)
			FloodFill(circleToDraw.get(0),circleToDraw.get(1),circleToDraw.get(3),canvas.getRGB(circleToDraw.get(0),circleToDraw.get(1)));
	}
	
	// Helper function for activating all of the circle points
	public void DrawCirclePoints(int x, int xc, int y, int yc, int color){
		canvas.setRGB(xc-x,yc-y,color);
		canvas.setRGB(xc+x,yc-y,color);
		canvas.setRGB(xc-x,yc+y,color);
		canvas.setRGB(xc+x,yc+y,color);
		canvas.setRGB(xc-y,yc-x,color);
		canvas.setRGB(xc+y,yc-x,color);
		canvas.setRGB(xc-y,yc+x,color);
		canvas.setRGB(xc+y,yc+x,color);
	}
	
	// Floodfill - mandatory for filling circles
	public void FloodFill(int x, int y, int fillColor, int colorToFill){
		FloodLine(x, y, fillColor, colorToFill);
		int yint = y;
		while(y+1<canvas.getHeight() && canvas.getRGB(x,y+1)==colorToFill){
			FloodLine(x,y+1,fillColor,colorToFill);
			y++;
		}
		y=yint;
		while(y>0 && canvas.getRGB(x,y-1)==colorToFill){
			FloodLine(x,y-1,fillColor,colorToFill);
			y--;
		}
	}
	
	// Helper for the floodfill
	private void FloodLine(int x, int y, int fillColor, int colorToFill){
		int targetPixel = canvas.getRGB(x,y);
		int xint = x;
		if(targetPixel==colorToFill){
			canvas.setRGB(x,y,fillColor);
			while(canvas.getWidth()>x+1 && canvas.getRGB(x+1,y)==colorToFill){
				canvas.setRGB(x+1,y,fillColor);
				x++;
			}
			x=xint;
			while(x>0 && canvas.getRGB(x-1,y)==colorToFill){
				canvas.setRGB(x-1,y,fillColor);
				x--;
			}
		}
		else
			return;
	}
	
	// Standard YXFill
	
	public void DrawAllTheThings(){
		DrawAllLines();
		DrawAllCircles(true);
	}
	
	// Line Clipper
	public ArrayList<Integer> ClipLine(ArrayList<Integer> lineToClip){
		// Draw the regions
		int top = 0; //reminder, above is less than this, 4th from right, ergo decimal value 8
		int left = 0; // Decimal value 1
		int bottom = canvas.getHeight()-1; //reminder, below is greater than this decimal value 4
		int right = canvas.getWidth()-1; // decimal value 2
		// Assign the codes
		int code1 = 0;
		if(lineToClip.get(0) < left)
			code1 += 1;
		else if(lineToClip.get(0) > right)
			code1 += 2;
		if(lineToClip.get(1) < top)
			code1 += 8;
		else if(lineToClip.get(1) > bottom)
			code1 += 4;
		int code2 = 0;
		if(lineToClip.get(2) < left)
			code2 += 1;
		else if(lineToClip.get(2) > right)
			code2 += 2;
		if(lineToClip.get(3) < top)
			code2 += 8;
		else if(lineToClip.get(3) > bottom)
			code2 += 4;
		// Now the logic tests
		if((code1|code2)==0)
			return lineToClip;
		else if((code1&code2)!=0)
			return null;
		else{
			if((((code1|code2) - (code1&code2))%2)==1){
				double slope = (double)(lineToClip.get(3)-lineToClip.get(1))/(double)(lineToClip.get(2)-lineToClip.get(0)); // Check the datatypes here - do we need doubles, or will ints work?
				int yCept = (int)(lineToClip.get(3) - (slope*lineToClip.get(2)));
				int intersect = (int)((slope*left) + yCept);
				Integer[] subline1 = new Integer[]{lineToClip.get(0),lineToClip.get(1),left,intersect,lineToClip.get(4)};
				Integer[] subline2 = new Integer[]{left,intersect,lineToClip.get(2),lineToClip.get(3),lineToClip.get(4)};
				if(lineToClip.get(2)>lineToClip.get(0))
					subline1[2] = subline1[2]-1;
				else
					subline2[0] = subline2[0]-1;
				ArrayList<Integer> output1 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline1)));
				ArrayList<Integer> output2 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline2)));
				if(output1!=null)
					return output1;
				else
					return output2;
			}
			else if(((((code1|code2) - (code1&code2))/2)%4)==1){ //issue; line that crosses the top will catch this guard
				double slope = (double)(lineToClip.get(3)-lineToClip.get(1))/(double)(lineToClip.get(2)-lineToClip.get(0)); // Check the datatypes here - do we need doubles, or will ints work?
				int yCept = (int)(lineToClip.get(3) - (slope*lineToClip.get(2)));
				int intersect = (int)((slope*right) + yCept);
				Integer[] subline1 = new Integer[]{lineToClip.get(0),lineToClip.get(1),right,intersect,lineToClip.get(4)};
				Integer[] subline2 = new Integer[]{right,intersect,lineToClip.get(2),lineToClip.get(3),lineToClip.get(4)};
				if(lineToClip.get(2)<lineToClip.get(0))
					subline1[2] = subline1[2]+1;
				else
					subline2[0] = subline2[0]+1;
				ArrayList<Integer> output1 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline1)));
				ArrayList<Integer> output2 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline2)));
				if(output1!=null)
					return output1;
				else
					return output2;
			}
			else if((((code1|code2) - (code1&code2))/8)==1){
				int intersect;
				if((lineToClip.get(2)-lineToClip.get(0))!=0){
					double slope = (double)(lineToClip.get(3)-lineToClip.get(1))/(double)(lineToClip.get(2)-lineToClip.get(0)); // Check the datatypes here - do we need doubles, or will ints work?
					int yCept = (int)(lineToClip.get(3) - (slope*lineToClip.get(2)));
					intersect = (int)((top-yCept)/slope);
				}
				else
					intersect=lineToClip.get(0);
				Integer[] subline1 = new Integer[]{lineToClip.get(0),lineToClip.get(1),intersect,top,lineToClip.get(4)};
				Integer[] subline2 = new Integer[]{intersect,top,lineToClip.get(2),lineToClip.get(3),lineToClip.get(4)};
				if(lineToClip.get(3)>lineToClip.get(1))
					subline1[3] = subline1[3]-1;
				else
					subline2[1] = subline2[1]-1;
				ArrayList<Integer> output1 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline1)));
				ArrayList<Integer> output2 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline2)));
				if(output1!=null)
					return output1;
				else
					return output2;
			}
			else if(((((code1|code2) - (code1&code2))/4)%8)==1){
				int intersect;
				if((lineToClip.get(2)-lineToClip.get(0))!=0){
					double slope = (double)(lineToClip.get(3)-lineToClip.get(1))/(double)(lineToClip.get(2)-lineToClip.get(0)); // Check the datatypes here - do we need doubles, or will ints work?
					int yCept = (int)(lineToClip.get(3) - (slope*lineToClip.get(2)));
					intersect = (int)((bottom-yCept)/slope);
				}
				else
					intersect=lineToClip.get(0);
				Integer[] subline1 = new Integer[]{lineToClip.get(0),lineToClip.get(1),intersect,bottom,lineToClip.get(4)};
				Integer[] subline2 = new Integer[]{intersect,bottom,lineToClip.get(2),lineToClip.get(3),lineToClip.get(4)};
				if(lineToClip.get(3)<lineToClip.get(1))
					subline1[3] = subline1[3]+1;
				else
					subline2[1] = subline2[1]+1;
				ArrayList<Integer> output1 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline1)));
				ArrayList<Integer> output2 = ClipLine(new ArrayList<Integer>(Arrays.asList(subline2)));
				if(output1!=null)
					return output1;
				else
					return output2;
			}
			return null;
		}
	}
	
	// Displays the current Buffered Image, immediately generates a new one
	public void DisplayCanvas(){
		frame.getContentPane().add(new JLabel(new ImageIcon(canvas))); // Look up more regarding how ImageIcon works - this is sufficient for now, but best to know more.
		frame.pack();
		frame.setVisible(true);
		canvas = new BufferedImage(CANVAS_HEIGHT, CANVAS_WIDTH, BufferedImage.TYPE_INT_RGB);
	}
	
	//Simple Constructor - Do we want it to take in all of the Buffered Canvas stuff, or handle it here?
	// Let's handle it all here.
	Artist(GeometricTables inputTables){
		//JFrame construction here?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Not sure if this is what we should be doing on close
		canvas = new BufferedImage(CANVAS_HEIGHT, CANVAS_WIDTH, BufferedImage.TYPE_INT_RGB);
		tables = inputTables; // Should be passing memory address, so not too taxing to do.
	}
}