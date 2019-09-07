import java.util.ArrayList;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Vertex;
import org.jsfml.system.Vector2f;

public class ShellInterface extends Game
{
	/**
	 * @param playr - The player instance
	 * 
	 * @param tempCmd - The initial command typed by the user
	 * @param cmd - The formatted version of [tempCmd] (Removed extra spaces)
	 * @param cmdParts - A split up version of [cmd], where each phrase sits in it's own array slot
	 * 
	 * @param halfBit - The font used in displaying the text
	 * @param command - The input text typed by the user rendered in the shell
	 * @param line - Space that holds the history of command outputs, up to 10 lines
	 * @param displayLine - Displays the contents of [line] on the screen
	 * 
	 * @param listOfCommands - The list of available commands to be executed, Used for auto-completing via tab key
	 * 
	 * @param prevCommands - A list of the history of every command entered during that game session
	 * @param prevCmd - A counter used to cycle through the history of commands used
	 * @param numOfPrevCmds - The number of commands used in the current game session
	 * @param lastCommand - The last command the user entered
	 * 
	 * @param shellBg - The background black rectangle of the shell rendered on screen
	 * @param shellBorder - Vertex's to render the empty white box of the shell
	 */	
	String tempCmd = "";
	String cmd = "";
	String[] cmdParts;
	
	Text command;
	String[] line;
	Text[] displayLine;
	
	String[] listOfCommands =
		{
			"help",
			"tp",
			"editor",
			"saveMap",
			"loadMap",
			"setBrush",
			"grid",
			"listMaps",
			"createMap",
			"setLayer",
			"toggleDebugMode",
			"collision",
			"toggleSnap",
			"saveCol",
			"loadCol",
			"toggleAutoSave"
		};
	
	ArrayList<String> prevCommands = new ArrayList<String>();
	int prevCmd;
	int numOfPrevCmds;
	String lastCommand;
	
	RectangleShape shellBg = new RectangleShape(new Vector2f(getWindowX(),30));
	
	Vertex[] shellBorder =
		{
			new Vertex(new Vector2f(1,getWindowY() - 30)),
			new Vertex(new Vector2f(getWindowX(),getWindowY() - 30)),
			new Vertex(new Vector2f(getWindowX(),getWindowY() - 1)),
			new Vertex(new Vector2f(1,getWindowY() - 1)),
			new Vertex(new Vector2f(1,getWindowY() - 30))
		};
	
	/**
	 * @method ShellInterface - Sets up and assigns variables.
	 * @param plyr - the players instance
	 */
	public ShellInterface()
	{		
		// Define variables
		line = new String[10];
		displayLine = new Text[10];
		prevCmd = 0;
		numOfPrevCmds = 0;
		lastCommand = "";
		
		// Create black rectangle background
		shellBg.setFillColor(Color.BLACK);
		shellBg.setPosition(0,getWindowY() - 30);
        
		// Prepare the command/text that the user inputs to be rendered
        command = new Text("> " + tempCmd,halfBit,32);
        command.setPosition(new Vector2f(10,getWindowY() - 42));
        
        /*
         * Set the positions for the 10 lines to display command output history
         * and prepare the output lines
         */
        for(int i = 0; i < 10; i++)
        {
        	displayLine[i] = new Text("",halfBit,32);
        	displayLine[i].setPosition(new Vector2f(10,(getWindowY() - 60) - ((i + 1) * 16)));
        	line[i] = "";
        }
	}
	
	/**
	 * @method runCommand - Takes the string in [tempCmd] and examines it by:
	 *  - Removing all extra white space between values in the command
	 *  - splitting the command up into separate string to be individually examined
	 *  - Tests each part of the command to see if there is a match amongst the available commands
	 *  - In places where the command is expected to have numerical data, those values are checked to be numerical
	 *  
	 *  If the command does match any of the commands below, an error will print.
	 *  Detailed events of the command entered are displayed in the console.
	 */
	public void runCommand()
	{		
		cmd = tempCmd.trim().replaceAll(" +", " "); // Remove multiple whitespace
		cmd.toLowerCase();
		cmdParts = cmd.split(" ");
		
		/**
		 * =======================================================
		 * <<< HELP >>>
		 * =======================================================
		 * This command is used to display command syntax.
		 * It tells the user how to enter commands.
		 */
		if(cmdParts[0].equals("help"))
		{
			printToConsole();
			
			if(cmd.length() > 5) 
			{
				boolean isNumeric = false;
				isNumeric = cmdParts[1].trim().matches("^[0-9]+$");
				
				if(isNumeric)
				{
					switch(Integer.parseInt(cmdParts[1]))
					{
						case 1:
						{
							// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
							cycleLines();
							line[0] = "==============================[ Help ]=================< 1/2 >======";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> help";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> tp";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> editor";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> saveMap";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> loadMap";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> setBrush";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> grid";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> listMaps";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> createMap";
							displayLine[0].setString(line[0]);
							break;
						}
						case 2:
						{
							// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
							cycleLines();
							line[0] = "==============================[ Help ]=================< 2/2 >======";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> setLayer";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> toggleDebugMode";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> collision";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> toggleSnap";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> saveCol";
							displayLine[0].setString(line[0]);
							cycleLines();
							line[0] = "> loadCol";
							displayLine[0].setString(line[0]);
							break;
						}
					}
				}
			}
			else
			{
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "==============================[ Help ]=================< 1/2 >======";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> help";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> tp";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> editor";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> saveMap";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> loadMap";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> setBrush";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> grid";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> listMaps";
				displayLine[0].setString(line[0]);
				cycleLines();
				line[0] = "> createMap";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< TELEPORT >>>
		 * =======================================================
		 * This command teleports the player to a desired location in the room
		 */
		else if(cmdParts[0].equals("tp"))
		{
			printToConsole();
			
			boolean[] isNumeric = new boolean[2];
			
			// Check that not only the command keyword has been entered
			if(cmd.length() <= 3) 
			{
				syntaxError();
			}
			
			// If bogus data was entered this will catch it
			try
			{
				// Check the 2 numbers representing X and Y are numerical
				isNumeric[0] = cmdParts[1].trim().matches("^[0-9]+$");
				isNumeric[1] = cmdParts[2].trim().matches("^[0-9]+$");
				
				if(isNumeric[0] && isNumeric[1])
				{
					int x = Integer.parseInt(cmdParts[1].trim());
					int y = Integer.parseInt(cmdParts[2].trim());
					
					// Set the players new position
					player.setPosition(x,y);
					
					// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
					cycleLines();
					line[0] = "Teleported " + player.getName() + " to " + player.getPlayerX() + "," + player.getPlayerY();
					displayLine[0].setString(line[0]);
				}
			}
			catch(Exception e)
			{
				syntaxError();
				cycleLines();
				line[0] = "Usage: tp <x> <y>";
				displayLine[0].setString(line[0]);
				
				if(debugState == CtrlStates.Debug.ENABLED)
				{
					e.printStackTrace();
				}
			}			
		}
		/**
		 * =======================================================
		 * <<< EDITOR >>>
		 * =======================================================
		 * This command toggles the map editing mode
		 */
		else if(cmdParts[0].equals("editor"))
		{
			printToConsole();
			// If the game is already in editor mode, disable it
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				editorState = CtrlStates.MapEditor.DISABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "Editor mode disabled";
				displayLine[0].setString(line[0]);
			}
			// If the game is not in editor mode, enable it
			else if(editorState.equals(CtrlStates.MapEditor.DISABLED))
			{
				editorState = CtrlStates.MapEditor.ENABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "Editor mode enabled";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< SAVEMAP >>>
		 * =======================================================
		 * This command saves the current map being edited
		 */
		else if(cmdParts[0].equals("saveMap"))
		{			
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 8) 
				{
					syntaxError();
				}
				
				try
				{
					mapLoader.saveNewRoom(cmdParts[1]);
					cycleLines();
					line[0] = "Saved map as " + cmdParts[1];
					displayLine[0].setString(line[0]);
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: saveMap <map name>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< LOADMAP >>>
		 * =======================================================
		 * This command loads a map from the assets/maps folder
		 */
		else if(cmdParts[0].equals("loadMap"))
		{			
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 8) 
				{
					syntaxError();
				}
				
				try
				{
					mapLoader.loadMap(cmdParts[1]);
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: loadMap <map name>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
				
				cycleLines();
				line[0] = mapLoader.shellOutput;
				displayLine[0].setString(line[0]);
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< SETBRUSH >>>
		 * =======================================================
		 * This command sets the brush of the mouse to a tileID in the map editor
		 */
		else if(cmdParts[0].equals("setBrush"))
		{
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				boolean isNumeric = false;
				
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 9) 
				{
					syntaxError();
				}
				
				try
				{
					isNumeric = cmdParts[1].trim().matches("^[0-9]+$");
					
					if(isNumeric)
					{
						mapLoader.setBrush(Integer.parseInt(cmdParts[1]));
					}
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: setBrush <tile id>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< GRID >>>
		 * =======================================================
		 * This command toggles a grid for use in helping with placing tiles
		 */
		else if(cmdParts[0].equals("grid"))
		{
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				editorState = CtrlStates.MapEditor.ENABLED;
				
				cycleLines();
				line[0] = "Grid disabled";
				displayLine[0].setString(line[0]);
			}
			else if(editorState.equals(CtrlStates.MapEditor.ENABLED))
			{
				editorState = CtrlStates.MapEditor.ENABLEDGRID;
				
				cycleLines();
				line[0] = "Grid enabled";
				displayLine[0].setString(line[0]);
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< LISTMAPS >>>
		 * =======================================================
		 * This command lists the maps available to be loaded
		 */
		else if(cmdParts[0].equals("listMaps"))
		{
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				mapLoader.listMaps();
				
				cycleLines();
				line[0] = mapLoader.shellOutput;
				displayLine[0].setString(line[0]);
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< CREATEMAP >>>
		 * =======================================================
		 * This command create a new map and saves it to assets/maps
		 */
		else if(cmdParts[0].equals("createMap"))
		{			
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 10) 
				{
					syntaxError();
				}
				
				try
				{
					int x = Integer.parseInt(cmdParts[2]);
					int y = Integer.parseInt(cmdParts[3]);
					int layerNum = Integer.parseInt(cmdParts[4]);
					
					mapLoader.createMap(cmdParts[1],x,y,layerNum);
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: createMap <map name> <x> <y> <number of layers>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< SETLAYER >>>
		 * =======================================================
		 * This command moves you to the specified layer on the map
		 */
		else if(cmdParts[0].equals("setLayer"))
		{			
			printToConsole();
			if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 9) 
				{
					syntaxError();
				}
				
				try
				{
					int layNum = Integer.parseInt(cmdParts[1]);
					
					if(layNum > 10000)
					{
						syntaxError();
						cycleLines();
						line[0] = "You will never need that many layers...";
						displayLine[0].setString(line[0]);
						
						mapLoader.setLayerNum(0);
					}
					else
					{
						mapLoader.setLayerNum(layNum);
					}
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: setLayer <layer number>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in editor mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< TOGGLEDEBUGSTATE >>>
		 * =======================================================
		 * This command toggles the debug state for the game. Used to debug things...
		 */
		else if(cmdParts[0].equals("toggleDebugMode"))
		{
			printToConsole();
			// If the game is already in debug mode, disable it
			if(debugState.equals(CtrlStates.Debug.ENABLED))
			{
				debugState = CtrlStates.Debug.DISABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "Debug mode disabled";
				displayLine[0].setString(line[0]);
			}
			// If the game is not in debug mode, enable it
			else if(debugState.equals(CtrlStates.Debug.DISABLED))
			{
				debugState = CtrlStates.Debug.ENABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "Debug mode enabled";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< COLLISION >>>
		 * =======================================================
		 * This command toggles the collision editing mode
		 */
		else if(cmdParts[0].equals("collision"))
		{
			printToConsole();
			// If the game is already in collision map mode, disable it
			if(collideState.equals(CtrlStates.CollideMap.ENABLED))
			{
				collideState = CtrlStates.CollideMap.DISABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "CollisionMap mode disabled";
				displayLine[0].setString(line[0]);
			}
			// If the game is not in collision map mode, enable it as long as editor mode is disabled
			else if(collideState.equals(CtrlStates.CollideMap.DISABLED) && editorState.equals(CtrlStates.MapEditor.DISABLED))
			{
				collideState = CtrlStates.CollideMap.ENABLED;
				
				// Cycle the lines to move all command outputs up by 1 line, add a new output and render it
				cycleLines();
				line[0] = "CollisionMap mode enabled";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< TOGGLESNAP >>>
		 * =======================================================
		 * This command toggles creating collision areas based on tile position or absolute
		 */
		else if(cmdParts[0].equals("toggleSnap"))
		{			
			printToConsole();
			if(collideState.equals(CtrlStates.CollideMap.ENABLED))
			{
				// Check which mode the user in
				if(collision.getSnapToGrid())
				{
					collision.setSnapToGrid(false);
				}
				else
				{
					collision.setSnapToGrid(true);
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in collision mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< SAVECOLLISION >>>
		 * =======================================================
		 * This command saves the current collision map being edited
		 */
		else if(cmdParts[0].equals("saveCol"))
		{			
			printToConsole();
			if(collideState.equals(CtrlStates.CollideMap.ENABLED))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 8) 
				{
					syntaxError();
				}
				
				try
				{
					collision.saveCollision(cmdParts[1]);
					cycleLines();
					line[0] = "Saved collision map as " + cmdParts[1];
					displayLine[0].setString(line[0]);
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: saveCol <map name>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				cycleLines();
				line[0] = "You must be in collision mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		/**
		 * =======================================================
		 * <<< LOADCOLLISION >>>
		 * =======================================================
		 * This command loads a collision file from the assets/collision folder
		 */
		else if(cmdParts[0].equals("loadCol"))
		{			
			printToConsole();
			if(collideState.equals(CtrlStates.CollideMap.ENABLED))
			{
				// Check that not only the command keyword has been entered
				if(cmd.length() <= 8) 
				{
					syntaxError();
				}
				
				try
				{
					collision.loadCollision(cmdParts[1]);
				}
				catch(Exception e)
				{
					syntaxError();
					cycleLines();
					line[0] = "Usage: loadCol <map name>";
					displayLine[0].setString(line[0]);
					
					if(debugState == CtrlStates.Debug.ENABLED)
					{
						e.printStackTrace();
					}
				}
				
				cycleLines();
				line[0] = collision.shellOutput;
				displayLine[0].setString(line[0]);
			}
			else
			{
				cycleLines();
				line[0] = "You must be in collision mode to do that";
				displayLine[0].setString(line[0]);
			}
		}
		else
		{
			printToConsole();
			syntaxError();
			cycleLines();
			line[0] = "Unkown command. Enter help for a list of commands";
			displayLine[0].setString(line[0]);
		}
	}
	
	/**
	 * @method syntaxError - Displays an error in the console that the command entered had something wrong with it
	 */
	public void syntaxError()
	{
		System.out.println("[Console] ERROR! Syntax error from issued command: " + tempCmd);
		prevCommandList();
	}
	
	/**
	 * @method printToConsole - Prints information to the console about the command entered
	 */
	public void printToConsole()
	{
		System.out.println("[Console] issued command: " + tempCmd);
		prevCommandList();
	}
	
	/**
	 * @method cycleLines - Shifts all of the command outputs up by 1 level to make space for new outputs
	 * Example: Line 9 = Line 10, Line 8 = Line 9, Line 7 = Line 8, etc.
	 */
	public void cycleLines()
	{
		for(int i = 9; i > 0; i--)
        {
			// Set the lower line to the upper line and prepare it for rendering
			line[i] = line[i - 1];
			displayLine[i].setString(line[i - 1]);
        }
	}
	
	/**
	 * @method autoCompleteCommand - Automatically completes the command you are typing based on
	 * the text you have started typing.
	 * Example: Typing "toggle" and pressing tab will display "toggleDebugMode"
	 */
	public void autoCompleteCommand()
	{
		// Don't do anything if the user typed nothing
		if(tempCmd.length() != 0)
		{
			// Loop until you have reached the end of all possible commands
			for(int i = 0; i < listOfCommands.length; i++)
			{
				boolean commandFound = false;
				
				// Did you find a command match that contained the users input?
				commandFound = listOfCommands[i].contains(tempCmd);
				
				if(commandFound)
				{
					// Set the new command
					tempCmd = listOfCommands[i];
				}
			}
		}
	}
	
	/**
	 * @method prevCommandList - Adds command just entered the list of previously entered commands
	 */
	public void prevCommandList()
	{		
		// If the list is not empty
		if(prevCommands.size() != 0)
		{
			// Check the command entered is not exactly the same as the command typed before it
			if(lastCommand != tempCmd)
			{
				// Add the new command the history, add the command to the last entered one, and increment the new number of previous commands
				prevCommands.add(tempCmd);
				lastCommand = tempCmd;
				numOfPrevCmds ++;
			}
		}
		else
		{
			// Add the new command the history, add the command to the last entered one, and increment the new number of previous commands
			prevCommands.add(tempCmd);
			lastCommand = tempCmd;
			numOfPrevCmds ++;
		}
				
		prevCmd = numOfPrevCmds;
		
		/*
		 * Debug Info about the specific of the Array list holding the history of commands
		 * Shows the entire list of previous commands as a whole and with their respective
		 * numbers to show what place they are in at the 
		 */
		if(debugState == CtrlStates.Debug.ENABLED)
		{
			for(int i = 0; i < prevCommands.size(); i++)
			{
				// Print out array list slot numbers with commands
				System.out.println("[Shell Interface] " + i + " " + prevCommands.get(i));
			}
			
			// Print entire array list
			System.out.println("[Shell Interface] " + prevCommands);
		}
	}
	
	/**
	 * @method getPrevCommandUp - Cycles up through the previous commands via up key
	 */
	public void getPrevCommandUp()
	{
		// Is there at least 1 previous command?
		if(numOfPrevCmds != 0)
		{
			// As long as the iterator is more than 0, increment down
			if(prevCmd > 0)
			{
				prevCmd--;
				
				// Set shell command to the selected previous command
				tempCmd = prevCommands.get(prevCmd);	
			}		
		}
	}
	
	/**
	 * @method getPrevCommandDown - Cycles down through the previous commands via down key
	 */
	public void getPrevCommandDown()
	{
		// Is there at least 1 previous command?
		if(numOfPrevCmds != 0)
		{
			// As long as the iterator is less than the total number of previous commands, increment up
			if(prevCmd < numOfPrevCmds - 1)
			{
				prevCmd++;
				
				// Set shell command to the selected previous command
				tempCmd = prevCommands.get(prevCmd);
			}
		}
	}
}
