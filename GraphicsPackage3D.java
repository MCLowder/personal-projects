// Import Block
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

//Package Import

public class GraphicsPackage3D{
	GeometricTables tables = new GeometricTables();
	
	// Prints a series of statements listing what commands are available - atm is a copy of the 2D version
	public static void HelpMessage(){
		System.out.println("");	
		System.out.println("");
		System.out.println("List of commands:");
		System.out.println("");
		System.out.println("0: Quit");
		System.out.println("1: Options");
		System.out.println("");
		System.out.println("Input either the number or the command name to call a function (omit spaces between words).");
		System.out.println("To get an explanation of a function, add \"-help\" after the command");
		System.out.println("To redisplay this list, type \"help\" with no command attached");
		System.out.println("");
		System.out.println("");
	}
	
	
	// Same as the non-3d Package, but Ideally this one includes some fancier tools - camera control, specific object rotation, etc...
	// Same control setup here. Help menu, scanner read in, while loop for control.
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		HelpMessage();
		
		boolean keepGoing = true;
		while(keepGoing){
			System.out.println("Awaiting Command:");
			String userInput = sc.nextLine();
			userInput = userInput.toLowerCase();
			String[] split = userInput.split(" ");
			if(Arrays.asList(split).contains("help"))
				HelpMessage();
			else if((Arrays.asList(split).contains("exit")) || (Arrays.asList(split).contains("quit"))){
				keepGoing=false;
				System.out.println("");
				System.out.println("");
			}
			else{
				System.out.println("Command not recognized");
				System.out.println("");
				System.out.println("");
			}
		}
		System.out.println("Thank you for using the program. Have a nice day!");
	}
}

// This is now the HW in Computer Graphics. Follow similar general format, although possibly with some cheats
// (include a toggle in options for demo/performance - demo is the cheater clipping and viewing transformation,
// while performance is full 3d clipping and the fancier matrix generation, ideally for testing the speed
// 