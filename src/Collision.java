import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Collision extends Game
{
	/**
	 * @param colFile - The collision file loaded with it's associated map file
	 * @param fileLine - A line read from the collision file
	 * 
	 * @param snapToGrid - Decides if the collision box being created should snap to the tile grid or be absolute
	 * @param numOfBoxes - The number of collision boxes in the room
	 * @param boxType - The type of collision, Type 1 is regular collision, Type 2,3,4,5 are room warp boxes -> TODO this needs changing, it's a terrible system for warp boxes
	 * @param X - The X cord of the collision box
	 * @param Y - The Y cord of the collision box
	 * @param width - The width of the collision box
	 * @param height - The height of the collision box
	 * @param clickTurn - Counts a click turn (2 clicks need to be made for a collision box to be produced, Click 1 = Pos1, Click 2 = Pos2)
	 * 
	 * @param playerHitBox - Displays the players wall collision box
	 * @param playerHB - The players wall collision box
	 * 
	 * @param cBoxs - A list of collision box data from the collision file
	 * @param cRects - A list of collision boxes
	 * @param cRectsDisplay - A list of collision boxes that can be shown in debug mode
	 *
	 * @param north - The northern collision wall of a collision box displayed
	 * @param east - The eastern collision wall of a collision box displayed
	 * @param south - The southern collision wall of a collision box displayed
	 * @param west - The western collision wall of a collision box displayed
	 * 
	 * @param northR - The northern collision wall of a collision box
	 * @param eastR - The eastern collision wall of a collision box
	 * @param southR - The southern collision wall of a collision box
	 * @param westR - The western collision wall of a collision box
	 */
	Scanner colFile;
	String shellOutput;
	String fileLine;
	
	boolean transition;
	boolean snapToGrid;
	int numOfBoxes;
	int boxType;
	int X;
	int Y;
	int width;
	int height;
	int clickTurn;
	
	RectangleShape playerHitbox;
	Rectangle playerHB;
	
	ArrayList<String> cBoxs = new ArrayList<String>();
	ArrayList<Rectangle> cRects = new ArrayList<Rectangle>();
	ArrayList<RectangleShape> cRectsDisplay = new ArrayList<RectangleShape>();
	
	RectangleShape north;
	RectangleShape east;
	RectangleShape south;
	RectangleShape west;
	
	Rectangle northR;
	Rectangle eastR;
	Rectangle southR;
	Rectangle westR;
	
	public Collision()
	{
		shellOutput = "";
		transition = false;
		snapToGrid = true;
		boxType = 1;
		X = 0;
		Y = 0;
		width = 0;
		height = 0;
		numOfBoxes = 0;
		clickTurn = 0;
		
		playerHitbox = new RectangleShape();
		playerHitbox.setSize(new Vector2f(33,6));
		playerHitbox.setFillColor(new Color(255,255,255,0));
		playerHitbox.setOutlineColor(new Color(0,0,0,255));
		playerHitbox.setOutlineThickness(1);
		
		playerHB = new Rectangle(player.getPlayerX() + 21,player.getPlayerY() + 57,33,6);
		
		north = new RectangleShape();
		east = new RectangleShape();
		south = new RectangleShape();
		west = new RectangleShape();
		
		northR = new Rectangle();
		eastR = new Rectangle();
		southR = new Rectangle();
		westR = new Rectangle();
	}
	
	/**
	 * @method loadCollision - Loads in collision data from assets/collision/ into a list
	 * @param path - the path of the collision file
	 */
	public void loadCollision(String path)
	{
		boolean collisionFound = false;
		int boxNumber = 0;
		
		cBoxs.clear();
		cRects.clear();
		cRectsDisplay.clear();
		
		try
		{
			colFile = new Scanner(new File("assets/collision/" + path + ".col"));
			collisionFound = true;
			shellOutput = "Collision successfully loaded";
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[Collision] Error! File does not exist!");
			shellOutput = "Collision failed to loaded";
			collisionFound = false;
			e.printStackTrace();
		}
		
		if(collisionFound)
		{					
			fileLine = colFile.nextLine();
			numOfBoxes = Integer.parseInt(fileLine);
			fileLine = colFile.nextLine();
			
			if(fileLine.equals("[collision]"))
			{				
				while(colFile.hasNextLine())
				{
					fileLine = colFile.nextLine();
					
					String[] param = fileLine.split(",");
					
					int cBoxX = Integer.parseInt(param[0]); 		// Collision box X position
					int cBoxY = Integer.parseInt(param[1]); 		// Collision box Y position
					int cBoxWidth = Integer.parseInt(param[2]); 	// Collision box width
					int cBoxHeight = Integer.parseInt(param[3]); 	// Collision box height
					int cBoxType = Integer.parseInt(param[4]); 		// Type of collision
					
					String cBoxRoomName = param[5]; 				// Room to warp to (Only applies to collision boxs of type 2)
					int cBoxSpawnX = Integer.parseInt(param[6]); 	// X position to spawn player at (Only applies to collision boxs of type 2)
					int cBoxSpawnY = Integer.parseInt(param[7]); 	// Y position to spawn player at (Only applies to collision boxs of type 2)
					String cBoxRoomStateName = param[8];			// Game state for the room (Only applies to collision boxs of type 2)
					
					cBoxs.add(cBoxX + "," + cBoxY + "," + cBoxWidth + "," + cBoxHeight + "," + cBoxType + "," + cBoxRoomName + "," + cBoxSpawnX + "," + cBoxSpawnY + "," + cBoxRoomStateName);
					
					cRects.add(new Rectangle(cBoxX,cBoxY,cBoxWidth,cBoxHeight));
					cRectsDisplay.add(new RectangleShape());
					cRectsDisplay.get(boxNumber).setPosition(cBoxX,cBoxY);
					cRectsDisplay.get(boxNumber).setSize(new Vector2f(cBoxWidth,cBoxHeight));
					cRectsDisplay.get(boxNumber).setOutlineColor(Color.BLACK);
					cRectsDisplay.get(boxNumber).setOutlineThickness(1);
					
					switch(cBoxType)
					{
						case 1:
						{
							cRectsDisplay.get(boxNumber).setFillColor(new Color(255,0,0,127));
							break;
						}
						case 2:
						{
							cRectsDisplay.get(boxNumber).setFillColor(new Color(0,0,255,127));
							break;
						}
						case 3:
						{
							cRectsDisplay.get(boxNumber).setFillColor(new Color(0,255,0,127));
							break;
						}
						case 4:
						{
							cRectsDisplay.get(boxNumber).setFillColor(new Color(0,255,255,127));
							break;
						}
						case 5:
						{
							cRectsDisplay.get(boxNumber).setFillColor(new Color(255,255,0,127));
							break;
						}
					}
					
					boxNumber++;
					System.out.println("[Collision] Placing Box " + boxNumber + " from " + cBoxX + " " + cBoxY + " to " + cBoxWidth + " " + cBoxHeight);
				}
			}
			else
			{
				System.out.println("[Collision] No boxes found!");
			}
		}
		else
		{
			System.out.println("[Collision] File could not be found!");
		}
		
		boxNumber = 0;
	}
	
	public void getPosition()
	{
		clickTurn++;
		
		if(snapToGrid)
		{
			getPosLoop:
			for(int i = 0; i < mapLoader.getRoomSizeY(); i++)
			{			
				if((getMouseY() + getViewY()) >= (i * 32) && (getMouseY() + getViewY()) <= ((i * 32) + 32))
				{
					for(int j = 0; j < mapLoader.getRoomSizeX(); j++)
					{					
						if((getMouseX() + getViewX()) >= (j * 32) && (getMouseX() + getViewX()) <= ((j * 32) + 32))
						{
							if(clickTurn == 1)
							{
								X = j * 32;
								Y = i * 32;
								
								System.out.println("[Collision] Position 1 selected");
							}
							else if(clickTurn == 2)
							{
								clickTurn = 0;
								int temp;
								
								if(X < (j * 32) + 32)
								{
									width = ((j * 32) + 32) - X;
								}
								else
								{
									temp = X;
									X = j * 32;
									width = (temp + 32) - X;
								}
								
								if(Y < (i * 32) + 32)
								{
									height = ((i * 32) + 32) - Y;
								}
								else
								{
									temp = Y;
									Y = i * 32;
									height = (temp + 32) - Y;
								}
								
								cBoxs.add(X + "," + Y + "," + width + "," + height + "," + boxType + ",null,0,0,NULL");
								cRects.add(new Rectangle(X,Y,width,height));
								cRectsDisplay.add(new RectangleShape());
								cRectsDisplay.get(cRectsDisplay.size() - 1).setPosition(X,Y);
								cRectsDisplay.get(cRectsDisplay.size() - 1).setSize(new Vector2f(width,height));
								cRectsDisplay.get(cRectsDisplay.size() - 1).setOutlineColor(Color.BLACK);
								cRectsDisplay.get(cRectsDisplay.size() - 1).setOutlineThickness(1);
								
								switch(boxType)
								{
									case 1:
									{
										cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(255,0,0,127));
										break;
									}
									case 2:
									{
										cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,0,255,127));
										break;
									}
									case 3:
									{
										cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,255,0,127));
										break;
									}
									case 4:
									{
										cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,255,255,127));
										break;
									}
									case 5:
									{
										cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(255,255,0,127));
										break;
									}
								}
								
								System.out.println("[Collision] Position 2 selected");
								System.out.println("[Collision] Collision box created");
							}
							
							break getPosLoop;
						}
					}
				}
			}
		
		}
		else
		{
			if(clickTurn == 1)
			{
				X = (int) (getMouseX() + getViewX());
				Y = (int) (getMouseY() + getViewY());
				
				System.out.println("[Collision] Position 1 selected");
			}
			else if(clickTurn == 2)
			{
				clickTurn = 0;
				
				int temp;
				
				if(X < getMouseX() + getViewX())
				{
					width = (int) ((getMouseX() + getViewX()) - X);
				}
				else
				{
					temp = X;
					X = (int) (getMouseX() + getViewX());
					width = temp - X;
				}
				
				if(Y < getMouseY() + getViewY())
				{
					height = (int) ((getMouseY() + getViewY()) - Y);
				}
				else
				{
					temp = Y;
					Y = (int) (getMouseY() + getViewY());
					height = temp - Y;
				}
				
				cBoxs.add(X + "," + Y + "," + width + "," + height + "," + boxType + ",null,0,0,NULL");
				cRects.add(new Rectangle(X,Y,width,height));
				cRectsDisplay.add(new RectangleShape());
				cRectsDisplay.get(cRectsDisplay.size() - 1).setPosition(X,Y);
				cRectsDisplay.get(cRectsDisplay.size() - 1).setSize(new Vector2f(width,height));
				cRectsDisplay.get(cRectsDisplay.size() - 1).setOutlineColor(Color.BLACK);
				cRectsDisplay.get(cRectsDisplay.size() - 1).setOutlineThickness(1);
				
				switch(boxType)
				{
					case 1:
					{
						cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(255,0,0,127));
						break;
					}
					case 2:
					{
						cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,0,255,127));
						break;
					}
					case 3:
					{
						cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,255,0,127));
						break;
					}
					case 4:
					{
						cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(0,255,255,127));
						break;
					}
					case 5:
					{
						cRectsDisplay.get(cRectsDisplay.size() - 1).setFillColor(new Color(255,255,0,127));
						break;
					}
				}
				
				System.out.println("[Collision] Position 2 selected");
				System.out.println("[Collision] Collision box created");
			}
		}
	}
	
	public void removeBox()
	{		
		for(int i = 0; i < cBoxs.size(); i++)
		{
			if(cRects.get(i).contains(getMouseX() + getViewX(),getMouseY() + getViewY()))
			{
				cBoxs.remove(i);
				cRects.remove(i);
				cRectsDisplay.remove(i);
			}
		}
	}
	
	public void collide()
	{
		if(!transition)
		{
			playerHB.setBounds(player.getPlayerX() + 21,player.getPlayerY() + 57,33,6);
			playerHitbox.setPosition(player.getPlayerX() + 21,player.getPlayerY() + 57);
			
			for(int i = 0; i < cBoxs.size(); i++)
			{
				if(cRects.get(i).intersects(playerHB))
				{
					String[] parts = cBoxs.get(i).split(",");
					
					if(parts[4].equals("1")) // Type 1 Collision - General purpose collision
					{				
						// North
						north.setPosition(Integer.parseInt(parts[0]) + 1,Integer.parseInt(parts[1])); // X,Y
						north.setSize(new Vector2f(Integer.parseInt(parts[2]) - 2,1)); // width,5
						north.setFillColor(Color.BLUE);
						northR.setBounds(Integer.parseInt(parts[0]) + 1,Integer.parseInt(parts[1]),Integer.parseInt(parts[2]) - 2,1);
						
						// East
						east.setPosition((Integer.parseInt(parts[0]) + Integer.parseInt(parts[2])) - 1,Integer.parseInt(parts[1]) + 1); // (X + width) - 5,Y
						east.setSize(new Vector2f(1,Integer.parseInt(parts[3]) - 2)); // 5,height
						east.setFillColor(Color.YELLOW);
						eastR.setBounds((Integer.parseInt(parts[0]) + Integer.parseInt(parts[2])) - 1,Integer.parseInt(parts[1]) + 1,1,Integer.parseInt(parts[3]) - 2);
						
						// South
						south.setPosition(Integer.parseInt(parts[0]) + 1,(Integer.parseInt(parts[1]) + Integer.parseInt(parts[3])) - 1); // X,(Y + height) - 5
						south.setSize(new Vector2f(Integer.parseInt(parts[2]) - 2,1)); // width,5
						south.setFillColor(Color.GREEN);
						southR.setBounds(Integer.parseInt(parts[0]) + 1,(Integer.parseInt(parts[1]) + Integer.parseInt(parts[3])) - 1,Integer.parseInt(parts[2]) - 2,1);
						
						// West
						west.setPosition(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]) + 1); // X,Y
						west.setSize(new Vector2f(1,Integer.parseInt(parts[3]) - 2)); // 5,height
						west.setFillColor(Color.MAGENTA);
						westR.setBounds(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]) + 1,1,Integer.parseInt(parts[3]) - 2);
						
						if(northR.intersects(playerHB))
						{
							player.setPlayerY((northR.y - 6) - 57);
						}
						else if(southR.intersects(playerHB))
						{
							player.setPlayerY((southR.y + 2) - 57);
						}
						else if(eastR.intersects(playerHB))
						{
							player.setPlayerX((eastR.x + 2) - 21);
						}
						else if(westR.intersects(playerHB))
						{
							player.setPlayerX((westR.x - 33) - 21);
						}
					}
					else if(parts[4].equals("2")) // Type 2 Collision - Room warp collision
					{
						System.out.println("Attempting to switch rooms");
						transition = true;
						transition(parts[5],Integer.parseInt(parts[6]),Integer.parseInt(parts[7]),CtrlStates.Room.valueOf(parts[8]));
					}
				}
			}
		}
	}
	
	public void saveCollision(String fileName)
	{					
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter("assets/collision/" + fileName + ".col", "UTF-8");
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
		
		writer.println(cBoxs.size());
		writer.println("[collision]");
		
		for(int i = 0; i < cBoxs.size(); i++)
		{
			writer.println(cBoxs.get(i));
		}
		
		writer.close();
	}
	
	public boolean checkDest(int destX,int destY)
	{
		boolean colFound = false;
		
		for(int i = 0; i < cBoxs.size(); i++)
		{
			
			if(cRects.get(i).contains(destX,destY) || cRects.get(i).contains(destX + 33,destY + 6))
			{
				colFound = true;
				break;
			}
		}
		
		return colFound;
	}
	
	public void setBoxType(int type)
	{
		boxType = type;
	}
	
	public boolean getSnapToGrid()
	{
		return snapToGrid;
	}
	
	public void setSnapToGrid(boolean snap)
	{
		snapToGrid = snap;
	}
	
	public void setTransition(boolean trans)
	{
		transition = trans;
	}
	
	public boolean getTransition()
	{
		return transition;
	}
}
