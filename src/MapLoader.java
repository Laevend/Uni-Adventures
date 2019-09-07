import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Vertex;
import org.jsfml.system.Vector2f;

public class MapLoader extends Game
{
	/**
	 * @param roomSizeX - The width of the room (in pixels) (in multiples of 32)
	 * @param roomSizeY - The height of the room (in pixels) (in multiples of 32)
	 * @param numOfLayers - The total number of layers a map has
	 * @param numOfTiles - The total number of tiles a map has
	 * @param currentLayer - The layer that the user is placing the tile on
	 * @param brushTile - The tile ID currently in use by the brush (brush meaning mouse (when you click))
	 * 
	 * @param fileLine - A string of the next line in the file
	 * @param shellOutput - A string the shell reads to display an output
	 * @param mapParamInfo[] - A small array containing 4 parameters of the map: It's Width, It's Height, The number of layers it has, and The number of tiles it has
	 * 
	 * @param tileSheet - An image which holds the tileSheet, used to draw tiles and objects to the room
	 * @param layer[] - An array of images which holds each drawn layer of the map starting at 0
	 * 
	 * @param layerSprite[] - An array of Sprites converted from layer images which are drawn by the window
	 * 
	 * @param mapFile - Grabs the map file from the assets folder and opens it
	 * @param map - Opens map.cfg which holds all the map names
	 * 
	 * @param mapGrid[][] - A 2D array used to draw grid lines to help with tile placement
	 * 
	 * @param tilePosInSheet - A rectangle representing a position on the tileSheet to copy a texture from
	 * 
	 * @param tileList - An array list which holds every tile from the currently loaded map
	 * @param listOfMaps An array list which holds the names of every map available to load
	 * @param currentLayerNum - Text that displays what layer the user is currently editing
	 */
	int roomSizeX;
	int roomSizeY;
	int numOfLayers;
	int numOfTiles;
	int currentLayer;
	int brushTile;
	
	String fileLine;
	String shellOutput;
	String[] mapParamInfo;
	
	Image tileSheet;
	Image[] layer;
	
	Sprite[] layerSprite;
	
	Scanner mapFile;
	Scanner maps;
	
	Vertex[][] mapGrid;
	
	IntRect tilePosInSheet;
	
	ArrayList<String> tileList = new ArrayList<String>();
	ArrayList<String> listOfMaps = new ArrayList<String>();
	Text currentLayerNum;
	
	public MapLoader()
	{
		roomSizeX = 10;
		roomSizeY = 10;
		numOfLayers = 1;
		brushTile = 0;
		currentLayer = 0;
		
		currentLayerNum = new Text("Editing Layer: " + String.valueOf(currentLayer),halfBit,32);
		currentLayerNum.setPosition((getWindowX() - currentLayerNum.getLocalBounds().width) - 15,0);
		
		tilePosInSheet = new IntRect(0,0,32,32);
		
		tileSheet = getImageFromFile("assets/tilesheet/tilemap32.png");
		
		loadMap("emptyMap");
	}
	
	public void loadMap(String path)
	{
		boolean mapFound = false;
		int tileNumber = 0;
		
		tileList.clear();
		
		try
		{
			mapFile = new Scanner(new File("assets/maps/" + path + ".map"));
			mapFound = true;
			shellOutput = "Map successfully loaded";
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[Map Loader] Error! File does not exist!");
			shellOutput = "Map failed to loaded";
			mapFound = false;
			e.printStackTrace();
		}
		
		if(mapFound)
		{					
			fileLine = mapFile.nextLine();
			mapParamInfo = fileLine.split(",");
			
			roomSizeX = Integer.parseInt(mapParamInfo[0]);
			roomSizeY = Integer.parseInt(mapParamInfo[1]);
			numOfLayers = Integer.parseInt(mapParamInfo[2]);
			numOfTiles = Integer.parseInt(mapParamInfo[3]);
			
			fileLine = mapFile.nextLine();
			
			if(fileLine.equals("[tiles]"))
			{				
				layer = new Image[numOfLayers];
				
				for(int i = 0; i < numOfLayers; i++)
				{
					layer[i] = new Image();
					layer[i].create((roomSizeX * 32),(roomSizeY * 32),Color.BLACK);
				}
				
				while(mapFile.hasNextLine())
				{
					fileLine = mapFile.nextLine();
					
					String[] param = fileLine.split(",");
					
					int tileID = Integer.parseInt(param[0]);
					int x32 = Integer.parseInt(param[1]);
					int y32 = Integer.parseInt(param[2]);
					int layerNum = Integer.parseInt(param[3]);
					
					int col;
					int row;
					
					if(tileID > 64)
					{
						col = tileID % 64;
						row = tileID / 64;
					}
					else
					{
						col = tileID;
						row = 0;
					}
					
					tileNumber++;
					System.out.println("[Map Loader] Placing tile " + tileNumber + " from " + col + " " + row + " at " + x32 + " " + y32);
					
					layer[layerNum].copy(tileSheet,x32 * 32,y32 * 32,new IntRect(col * 32,row * 32,32,32),false);
					
					tileList.add(tileID + "," + x32 + "," + y32 + "," + layerNum);
				}
			}
			else
			{
				System.out.println("[Map Loader] No tiles found!");
			}
			
			layerSprite = new Sprite[numOfLayers];
			
			for(int i = 0; i < numOfLayers; i++)
			{
				layerSprite[i] = new Sprite(getTextureFromImage(layer[i]));
				layerSprite[i].setPosition(new Vector2f(0,0));
			}
		}
		else
		{
			System.out.println("[Map Loader] File could not be found!");
		}
		
		tileNumber = 0;
		createGrid();
	}
	
	public void createGrid()
	{
		mapGrid = new Vertex[getMax(roomSizeX,roomSizeY)][4];
		for(int i = 0; i < getMax(roomSizeX,roomSizeY); i++)
		{
			mapGrid[i][0] = new Vertex(new Vector2f(32 * i,0));
			mapGrid[i][1] = new Vertex(new Vector2f(32 * i,((32 * i) * 30)));
			mapGrid[i][2] = new Vertex(new Vector2f(0,32 * i));
			mapGrid[i][3] = new Vertex(new Vector2f(((32 * i) * 50),32 * i));
			System.out.println("[Map Grid] CrossLine: " + i);
		}
	}
	
	public void findTile()
	{
		int tileNum = 0;
		int tileCordX = 0;
		int tileCordY = 0;
			
		tileFindLoop:
		for(int i = 0; i < roomSizeY; i++)
		{			
			if((getMouseY() + getViewY()) >= (i * 32) && (getMouseY() + getViewY()) <= ((i * 32) + 32))
			{
				for(int j = 0; j < roomSizeX; j++)
				{					
					if((getMouseX() + getViewX()) >= (j * 32) && (getMouseX() + getViewX()) <= ((j * 32) + 32))
					{
						System.out.println("[Map Loader] Tile " + tileNum + " Clicked at - X: " + tileCordX + " Y: " + tileCordY);
						
						layer[currentLayer].copy(tileSheet,j * 32,i * 32,tilePosInSheet,false);
						
						tileList.add(brushTile + "," + j + "," + i + "," + currentLayer);
						
						break tileFindLoop;
					}
					tileCordX ++;
					tileNum ++;
				}
			}
			tileCordY ++;
			tileNum += roomSizeX;
		}
		layerSprite[currentLayer].setTexture(getTextureFromImage(layer[currentLayer]));
	}
	
	public void setBrush(int id)
	{
		int col;
		int row;
		
		brushTile = id;
		
		if(id >= 64)
		{
			col = id % 64;
			row = id / 64;
		}
		else
		{
			col = id;
			row = 0;
		}
		
		tilePosInSheet = new IntRect(col * 32,row * 32,32,32);
		System.out.println("[Map Editor] Tile selected at - col: " + col + " row: " + row);
	}
	
	public void createMap(String fileName,int roomX,int roomY,int numOfLay)
	{		
		int roomSX = roomX;
		int roomSY = roomY;
		int numOfLays = numOfLay;
		boolean mapFoundInList = false;
		
		for(int i = 0; i < listOfMaps.size(); i++)
		{
			if(listOfMaps.get(i) == fileName)
			{
				mapFoundInList = true;
				break;
			}
		}
		
		// If duplicate
		if(!mapFoundInList)
		{
			listOfMaps.add(fileName);
		}
			
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter("assets/maps/" + fileName + ".map", "UTF-8");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println(roomSX + "," + roomSY + "," + numOfLays + ",0");
		writer.println("[tiles]");
		
		writer.close();
		updateMapCFG();
	}
	
	public void saveNewRoom(String fileName)
	{		
		ArrayList<String> tileListFinal = new ArrayList<String>();
		ArrayList<String> tilePosID = new ArrayList<String>();
		boolean mapFoundInList = false;
		int numOfTileInList = 0;
		
		/**
		 * TLDR: It removed duplicate tile rendering pointers
		 * 
		 * In the list, there may be two or more items which tell the game to render a
		 * tile at the same location. We don't want this because it wastes rendering time.
		 * To prevent this we work back to front, creating an ID from the x, y, and layer
		 * and holding them in a list to check against. We work back to front to save only
		 * the newer changes and discard older changes.
		 */
		for(int i = tileList.size() - 1; i > -1; i--)
		{
			String TLC = tileList.get(i); // Tile List Changes (TLC)
			String[] TLCP = TLC.split(","); // Tile List Changes Parts (TLCP)
			String UTNC = TLCP[1]  + "," + TLCP[2]  + "," + TLCP[3]; // Unique Tile Number Change(UTNC)
			boolean dupe = false;
			
			if(tilePosID.size() > 0)
			{
				for(int j = 0; j < tilePosID.size(); j++)
				{
					if(tilePosID.get(j).equals(UTNC))
					{
						dupe = true;
					}
				}
				if(!dupe)
				{
					tilePosID.add(UTNC);
					tileListFinal.add(tileList.get(i));
				}
			}
			else
			{
				tilePosID.add(UTNC);
				tileListFinal.add(tileList.get(i));
			}
		}
		
		// Remove all tiles which are the ID of 0 because 0 is air, we don't care what is stored there
		for(int i = tileListFinal.size() - 1; i > -1; i--)
		{
			String TLCF = tileListFinal.get(i); // Tile List Changes Final (TLCF)
			String[] TLCP = TLCF.split(","); // Tile List Changes Parts Final (TLCPF)
			
			if(TLCP[0].equals("0"))
			{
				tileListFinal.remove(i);
			}
		}
		
		// Get the total number of tiles
		numOfTileInList = tileListFinal.size();
		
		// Check for duplicate map names
		for(int i = 0; i < listOfMaps.size(); i++)
		{			
			if(listOfMaps.get(i).equals(fileName))
			{
				mapFoundInList = true;
				break;
			}
		}
		
		// If no duplicates where found
		if(!mapFoundInList)
		{
			System.out.println("[Map Loader] No map by that name! Adding new map name...");
			listOfMaps.add(fileName);
		}
		else
		{
			System.out.println("[Map Loader] Map name exists!");
			mapFoundInList = false;
		}
			
		PrintWriter writer = null;
		boolean writerNotNull = false;
		
		try
		{
			writer = new PrintWriter("assets/maps/" + fileName + ".map", "UTF-8");
			writerNotNull = true;
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			writerNotNull = false;
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			writerNotNull = false;
		}
		
		if(writerNotNull)
		{
			writer.println(roomSizeX + "," + roomSizeY + "," + numOfLayers + "," + numOfTileInList);
			writer.println("[tiles]");
			
			for(int i = 0; i < tileListFinal.size(); i++)
			{
				writer.println(tileListFinal.get(i));
				System.out.println("[Map Loader] Saving tile: " + tileListFinal.get(i));
			}
			
			writer.close();
			updateMapCFG();
		}
		
		tileListFinal.clear();
	}
	
	public void listMaps()
	{
		File listMaps = new File("assets/maps.cfg");
		
		if(listMaps.exists() && !listMaps.isDirectory())
		{
			openMapCFG();
			
			for(int i = 0; i < listOfMaps.size(); i++)
			{
				System.out.println("[Map Loader] Map: " + listOfMaps.get(i));
			}
			
			shellOutput = "Check console for maps";
		}
		else
		{
			shellOutput = "No maps found! Creating file...";
			
			PrintWriter writer = null;
			
			try
			{
				writer = new PrintWriter("assets/maps.cfg", "UTF-8");
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			writer.println("emptyMap");
			
			writer.close();
		}
	}
	
	public void openMapCFG()
	{
		listOfMaps.clear();
		try
		{
			maps = new Scanner(new File("assets/maps.cfg"));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[Map Loader] Error! File does not exist!");
			e.printStackTrace();
		}
		
		while(maps.hasNextLine())
		{
			listOfMaps.add(maps.nextLine());
		}
	}
	
	public void updateMapCFG()
	{
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter("assets/maps.cfg", "UTF-8");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i = 0; i < listOfMaps.size(); i++)
		{
			writer.println(listOfMaps.get(i));
		}
		
		writer.close();
	}
	
	public int getRoomSizeX()
	{
		return roomSizeX;
	}
	
	public int getRoomSizeY()
	{
		return roomSizeY;
	}
	
	public int getLargerParam()
	{
		return getMax(roomSizeX,roomSizeY);
	}
	
	public void setLayerNum(int layer)
	{
		currentLayer = layer;
		currentLayerNum = new Text("Editing Layer: " + String.valueOf(currentLayer),halfBit,32);
		currentLayerNum.setPosition((getWindowX() - currentLayerNum.getLocalBounds().width) - 15,0);
	}
}
