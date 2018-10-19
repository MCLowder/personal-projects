// import block
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.ArrayList;

// package import block

// Let's just make all of our lives simpler. This will be where are scan-conversion happens.
// Set it up to properly use ArrayLists, handle lines, circles, and filling.
public class Artist{
	// Generates the new BufferedImage

	// Displays the current Buffered Image
	
	//Simple Constructor - Do we want it to take in all of the Buffered Canvas stuff, or handle it here?
	// Let's handle it all here.
	Artist(){
		//JFrame construction here?
		JFrame frame = new JFrame("Line Drawer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Not sure if this is what we should be doing on close
	}
}