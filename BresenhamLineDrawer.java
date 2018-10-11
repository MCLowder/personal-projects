import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.swing.*; // https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
import java.util.Arrays;

public class BresenhamLineDrawer{
	static int caseCount1, caseCount2, caseCount3, caseCount4, caseCount5;
	static long caseTotal1, caseTotal2, caseTotal3, caseTotal4, caseTotal5;
	
	public static void BresenDraw(BufferedImage canvas, String[] args, int[] line){ // x1, int y1, int x2, int y2){ //needs to be static, or else the main can't call it
	
		int x1 = line[0];
		int y1 = line[1];
		int x2 = line[2];
		int y2 = line[3];
		int col;
		if(line.length==5)
			col = line[4];
		else
			col = 0;
		int dX = x2 - x1;
		int dY = y2 - y1;
		int r,g,b;
		r=g=b=0;
		if(col==0){
			col = new Color(255,255,255).getRGB();
			r=g=b=255;
		}
		//5 possible cases here - handle the special ones first
		if(dX==0&&dY==0){
			canvas.setRGB(x1,y1,col);
			if(Arrays.asList(args).contains("-debug")){
				System.out.println("Case 0: Line only a single pixel long");
			}
		}
		else if(dX==0){
			if(dY>0){
				long t0 = System.nanoTime();
				for(int pix = 0;pix<=dY;pix++){
					canvas.setRGB(x1, y1+pix, col);
				}
				long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
				if(Arrays.asList(args).contains("-debug")){
					System.out.println("Case 1.1: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
				}
				caseCount1 ++;
				caseTotal1 += pixTime;
			}
			else{
				long t0 = System.nanoTime();
				for(int pix = 0;pix>=dY;pix--){
					canvas.setRGB(x1,y1+pix, col);
				}				
				long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
				if(Arrays.asList(args).contains("-debug")){
					System.out.println("Case 1.2: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
				}
				caseCount1 ++;
				caseTotal1 += pixTime;
			}
		}
		else if(dY==0){
			if(dX>0){
				long t0 = System.nanoTime();
				for(int pix=0;pix<=dX;pix++){
					canvas.setRGB(x1+pix, y1, col);
				}
				long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
				if(Arrays.asList(args).contains("-debug")){
					System.out.println("Case 2.1: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
				
				}
				caseCount2 ++;
				caseTotal2 += pixTime;
			}
			else{
				long t0 = System.nanoTime();
				for(int pix=0; pix>=dX;pix--){
					canvas.setRGB(x1+pix, y1, col);
				}
				long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
				if(Arrays.asList(args).contains("-debug")){
					System.out.println("Case 2.2: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
				}
				caseCount2 ++;
				caseTotal2 += pixTime;
			}
		}
		else if(Math.abs(dX)==Math.abs(dY)){
			if(dY>0){
				if(dX>0){
					long t0 = System.nanoTime();
					for(int pix = 0;pix<=dY;pix++){
						canvas.setRGB(x1+pix, y1+pix, col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 3.1: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount3 ++;
					caseTotal3 += pixTime;
				}					
				else{
					long t0 = System.nanoTime();
					for(int pix = 0;pix<=dY;pix++){
						canvas.setRGB(x1-pix, y1+pix, col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 3.2: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount3 ++;
					caseTotal3 += pixTime;
				}
			}
			else{
				if(dX>0){
					long t0 = System.nanoTime();
					for(int pix = 0;pix>=dY;pix--){
						canvas.setRGB(x1-pix, y1+pix, col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 3.3: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount3 ++;
					caseTotal3 += pixTime;
				}
				else{
					long t0 = System.nanoTime();
					for(int pix = 0;pix>=dY;pix--){
						canvas.setRGB(x1+pix, y1+pix, col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 3.4: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount3 ++;
					caseTotal3 += pixTime;
				}
			}
		}
		else if(Math.abs(dX)>Math.abs(dY)){
			int error = (2*Math.abs(dY))-Math.abs(dX);
			int inc1 = 2*Math.abs(dY);
			int inc2 = 2*(Math.abs(dY)-Math.abs(dX));
			int y = y1;
			int x = x1;
			if(dX>0){
				if(dY>0){
					long t0 = System.nanoTime();
					while(x < x2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							y ++;
						}
						x ++;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 4.1: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
					}
					caseCount4 ++;
					caseTotal4 += pixTime;
				}
				else{
					long t0 = System.nanoTime();
					while(x < x2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							y--;
						}
						x ++;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 4.2: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
					}
					caseCount4 ++;
					caseTotal4 += pixTime;
				}
			}
			else{
				if(dY>0){
					long t0 = System.nanoTime();
					while(x > x2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							y ++;
						}
						x --;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 4.3: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
					}
					caseCount4 ++;
					caseTotal4 += pixTime;
				}
				else{
					long t0 = System.nanoTime();
					while(x > x2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							y --;
						}
						x --;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dX);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 4.4: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dX));
					}
					caseCount4 ++;
					caseTotal4 += pixTime;
				}
			}
		}
		else if(Math.abs(dX)<Math.abs(dY)){
			int error = (2*Math.abs(dX))-Math.abs(dY);
			int inc1 = 2*Math.abs(dX);
			int inc2 = 2*(Math.abs(dX)-Math.abs(dY));
			int y = y1;
			int x = x1;
			if(dY>0){
				if(dX>0){
					long t0 = System.nanoTime();
					while(y < y2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							x ++;
						}
						y ++;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 5.1: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount5 ++;
					caseTotal5 += pixTime;
				}
				else{
					long t0 = System.nanoTime();
					while(y < y2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							x--;
						}
						y ++;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 5.2: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount5 ++;
					caseTotal5 += pixTime;
				}
			}
			else{
				if(dX>0){
					long t0 = System.nanoTime();
					while(y > y2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							x ++;
						}
						y --;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 5.3: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount5 ++;
					caseTotal5 += pixTime;
				}
				else{
					long t0 = System.nanoTime();
					while(y > y2){
						if(error<0){
							error += inc1;
						}
						else{
							error += inc2;
							x --;
						}
						y --;
						canvas.setRGB(x,y,col);
					}
					long pixTime = (System.nanoTime()-t0) / Math.abs(dY);
					if(Arrays.asList(args).contains("-debug")){
						System.out.println("Case 5.4: (" + x1+","+y1+") to (" + x2 + "," + y2 +"), Color: (" + r + "," + g + "," + b + "), Average time per pixel: " + pixTime + " nanoseconds. Total Pixels: " + Math.abs(dY));
					}
					caseCount5 ++;
					caseTotal5 += pixTime;
				}
			}
		}
		else{
			System.out.print("Case uncertain: "); // Included as part of the debugging - if this every shows up, it's a sign that we forgot to account for a case above
			System.out.println(x1 + " " + y1 + " to " + x2 + " " + y2);
			
		}
	}
	
	public static void BresenCircle(BufferedImage canvas, int[] circle, boolean filled){
		// Basic preemptive variable declaration
		int xc = circle[0];
		int yc = circle[1];
		int r = circle[2];
		int col;
		if(circle.length==4)
			col = circle[3];
		else
			col = 0;
		int red,g,b;
		red=g=b=0;
		if(col==0){
			col = new Color(255,255,255).getRGB();
			red=g=b=255;
		}
		//On to the algorithm;
		int x=0;
		int y=r;
		int p = 3 - (2*r);
		CirclePoints(canvas,x,xc,y,yc,col);
		while(x<y){
			CirclePoints(canvas,x,xc,y,yc,col);
			if(p<0){
				p += ((4*x)+6);
			}
			else{
				p += ((4*(x-y))+10);
				y--;
			}
			x++;
		}
		CirclePoints(canvas,x,xc,y,yc,col);
		if(filled)
			FloodFill(canvas,xc,yc,col,canvas.getRGB(xc,yc));
	}
	
	public static void CirclePoints(BufferedImage canvas, int x, int xc, int y, int yc, int color){
		canvas.setRGB(xc-x,yc-y,color);
		canvas.setRGB(xc+x,yc-y,color);
		canvas.setRGB(xc-x,yc+y,color);
		canvas.setRGB(xc+x,yc+y,color);
		canvas.setRGB(xc-y,yc-x,color);
		canvas.setRGB(xc+y,yc-x,color);
		canvas.setRGB(xc-y,yc+x,color);
		canvas.setRGB(xc+y,yc+x,color);
	}
	
	//Consider adding a floodfill and YX fill? I know floodfill is 'toylike', but it's the only way to fill circles
	
	public static void FloodFill(BufferedImage canvas, int x, int y, int color, int colToFill){ // renamed to Circlefill, since this depends on it being a nice circle
		int targetPixel = canvas.getRGB(x,y);
		if(targetPixel==colToFill){
			canvas.setRGB(x,y,color);
			FloodFill(canvas,x+1,y,color,colToFill);
			FloodFill(canvas,x-1,y,color,colToFill);
			/*FloodFill(canvas,x,y-1,color,colToFill);
			FloodFill(canvas,x,y+1,color,colToFill);*/
		}
		/*while(canvas.getRGB(x+1,y)==colToFill){
			FloodFill(canvas,x,y+1,color,colToFill);
			x++;
		}*/
	}
	
	public static void main(String args[]){
		//This all sets up the space we will be drawing on
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		BufferedImage canvas = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB); //This is the actual canvas for us to write on. Once we figure out how to paint it to the JFRame, we'll have the IT portion of the HW done.
		Scanner sc = new Scanner(System.in);
		System.out.println("Input how many lines for the system to draw: ");
		int test;
		try{
			test = sc.nextInt();
			int[][] endPoints = new int[test][4];
			int x1, x2, y1, y2;
			//This will be the codeblock that actually involves drawing the lines on the buffered canvas
			// Generate the random number of lines to draw, then call a seperate number that many times (refer to Drawer for RandInt format, then multiply by maximum and typecast as int to get the proper loop number
			// Can we use the static declaration if there is going to be a random number of function calls? probably. We'll find out
			for(int i=0;i<test;i++){
				// call the line draw method here.
				endPoints[i][0] = x1 = (int)(Math.random() * canvas.getWidth());
				endPoints[i][1] = y1 = (int)(Math.random() * canvas.getHeight());
				endPoints[i][2] = x2 = (int)(Math.random() * canvas.getWidth());
				endPoints[i][3] = y2 = (int)(Math.random() * canvas.getHeight());
				BresenDraw(canvas, args, endPoints[i]);
			}
			
			//This paints the resulting canvas to a visible window
			frame.getContentPane().add(new JLabel(new ImageIcon(canvas))); // Look up more regarding how ImageIcon works - this is sufficient for now, but best to know more.
			frame.pack();
			frame.setVisible(true);
			
			if(Arrays.asList(args).contains("-debug")){
				System.out.println("");
				System.out.println("Average Runtime for case 1 lines: " + ((double)caseTotal1/caseCount1) + " nanoseconds (Total lines involved: " + caseCount1 + ")");
				System.out.println("Average Runtime for case 2 lines: " + ((double)caseTotal2/caseCount2) + " nanoseconds (Total lines involved: " + caseCount2 + ")");
				System.out.println("Average Runtime for case 3 lines: " + ((double)caseTotal3/caseCount3) + " nanoseconds (Total lines involved: " + caseCount3 + ")");
				System.out.println("Average Runtime for case 4 lines: " + ((double)caseTotal4/caseCount4) + " nanoseconds (Total lines involved: " + caseCount4 + ")");
				System.out.println("Average Runtime for case 5 lines: " + ((double)caseTotal5/caseCount5) + " nanoseconds (Total lines involved: " + caseCount5 + ")");
			}
			
		} catch (java.util.InputMismatchException e) {
			System.out.println("Cannot interpret input. Please try again and enter a number");
		}
	}
}

/* TODO: Nothing. It's good! :D
Might need to add some citations for the basic IT stuff from the web - canvas, display, color, mostly.
Also see about talking with the TA to make sure that you aren't using anything too high level - not likely, but still*/