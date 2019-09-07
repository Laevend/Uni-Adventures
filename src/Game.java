import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Scanner;

import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;

import org.jsfml.window.Mouse;



public abstract class Game
{
	/**
	 * @param window - The game window where everything takes place
	 * @param mainMenu - The main menu of the game
	 * @param debugMenu - The debug menu
	 * @param keyPress - A Key press listener, it listens every game tick for key pressing
	 * @param coolDownTimer - A timer used to prevent key press events happening too quickly
	 * @param fps - The number of frames the game runs at
	 * @param updates - The number of updates the game has done
	 * @param timer - The current time (in milliseconds) that the game has been running for
	 */
	protected static RenderWindow window;
	protected static MainMenu  mainMenu;
	protected static DebugMenu debugMenu;
	protected static KeyPress keyPress;
	protected static Player player;
	protected static ShellInterface shell;
	protected static MapLoader mapLoader;
	protected static Collision collision;
	protected static HUD hud;
	protected static Story story;
	public static MusicManager musicManager;
	protected NPC npc;
	protected static Font halfBit  = new Font();
	protected static Font simpleBit = new Font();
	
	private static int coolDownTimer;
	protected static int textboxSpeed;
	private static int fps;
	private static long updates;
	private static long timer;
	private static final int windowX = 1280;
	private static final int windowY = 720;
	private static int mouseX;
	private static int mouseY;
	
	//Game states, tells the game what logic to run, and what to render depending on the state
	public static CtrlStates.State gameState = CtrlStates.State.MENU;
	public static CtrlStates.Menu menuState = CtrlStates.Menu.NONE;
	public static CtrlStates.Debug debugState = CtrlStates.Debug.DISABLED;
	public static CtrlStates.Shell shellState = CtrlStates.Shell.DISABLED;
	public static CtrlStates.Room roomState = CtrlStates.Room.FLATROOM1;
	public static CtrlStates.MapEditor editorState = CtrlStates.MapEditor.DISABLED;
	public static CtrlStates.CollideMap collideState = CtrlStates.CollideMap.DISABLED;
	public static CtrlStates.BattleSystemUI battleUIState = CtrlStates.BattleSystemUI.PLAYER1ANDNPC1;
	public static CtrlStates.BattleSystemText battleTextState = CtrlStates.BattleSystemText.DISABLED;
	
	public static PrimitiveType LinesStrip = PrimitiveType.LINE_STRIP;
	public static PrimitiveType Lines = PrimitiveType.LINES;
	
	public static View camera;
	public static float cameraX;
	public static float cameraY;
	public static View cameraGUI;
	public static int frameClock;
	public static int mouseDelta;
	
	private static boolean[] fadeSeq = {false,false,false};
	private static RectangleShape fadeRect;
	private static Color fadeCol = new Color(255,255,255,0);
	private static int fadeAlpha = 0;
	
	public static Sound sfx = new Sound();
	public static Sound sfxClickSound = new Sound();
	public static Sound sfxTextSound = new Sound();
	
	public static SoundBuffer sfxClick = new SoundBuffer();
	public static SoundBuffer sfxHit1 = new SoundBuffer();
	public static SoundBuffer sfxHit2 = new SoundBuffer();
	public static SoundBuffer sfxHit3 = new SoundBuffer();
	public static SoundBuffer sfxHit4 = new SoundBuffer();
	
	private static boolean directShake = false;
	private static int cameraShake = 0;
	protected static int shakeTime = 0;
	public static int[] xpLevel = new int[120];
	public static boolean isFocused;
	public static String roomName = "";
	
	private static String transMapName = "";
	private static int transEntrancePosX = 0;
	private static int transEntrancePosY = 0;
	private static CtrlStates.Room transRoom = CtrlStates.Room.NULL;
	
	public static void main(String args[])
	{				
		//Initialise variables
		halfBit = getFontFromFile("assets/font/Halfbit.ttf");
		simpleBit = getFontFromFile("assets/font/Simplebit.ttf");
		
		generateXpReq();
		
		window = new RenderWindow();
		mainMenu = new MainMenu();
		debugMenu = new DebugMenu();
		keyPress = new KeyPress();
		player = new Player();
		shell = new ShellInterface();
		mapLoader = new MapLoader();
		collision = new Collision();
		hud = new HUD();
		story = new Story();
		musicManager = new MusicManager();
		
		camera = new View(new FloatRect(player.getPlayerX(),player.getPlayerY(),1280,720));
		cameraGUI = new View(new FloatRect(0,0,1280,720));
		cameraX = 0;
		cameraY = 0;
		
		coolDownTimer = 0;
		textboxSpeed = 0;
		fps = 0;
		updates = 0;
		timer = System.currentTimeMillis();	
		frameClock = 0;
		mouseDelta = 0;
		isFocused = true;
		
		fadeRect = new RectangleShape();
		fadeRect.setPosition(0,0);
		fadeRect.setSize(new Vector2f(windowX,windowY));
		fadeRect.setFillColor(fadeCol);
		
		sfxClick = getSoundFromFile("assets/sfx/click.ogg");
		sfxHit1 = getSoundFromFile("assets/sfx/hit1.ogg");
		sfxHit2 = getSoundFromFile("assets/sfx/hit2.ogg");
		sfxHit3 = getSoundFromFile("assets/sfx/hit3.ogg");
		sfxHit4 = getSoundFromFile("assets/sfx/hit4.ogg");
		
		sfxClickSound.setBuffer(sfxClick);
		
		//Set up basic game window
		window.create(new VideoMode(windowX,windowY),"Uni Adventures",WindowStyle.CLOSE);
        window.setFramerateLimit(60);
        window.setIcon(getImageFromFile("assets/icon.png"));
        
        mapLoader.listMaps();
        
        window.setVerticalSyncEnabled(true);
        window.setKeyRepeatEnabled(false);
        gameLoop();
	}
	
	/**
	 * @method gameLoop - The main game loop where logic, rendering and AI is executed every game tick
	 */
	public static void gameLoop()
	{
        /*
         * ========================================
         *  Main Game Loop
         * ========================================
         */
		while(window.isOpen())
		{
			// Update the FPS and Updates counters
			fps++;
			updates++;
			
			pollEvents();
			
			// Used to calculate the FPS (Frames per second) at which the game is running at
			if(System.currentTimeMillis() - timer >= 1000)
			{
				timer = System.currentTimeMillis();
				debugMenu.setFPSText(fps);
				fps = 0;
			}
			
			debugMenu.setUpdateText(updates);
			
			// Cool down for key pressing
			if(coolDownTimer > 0)
			{
				coolDownTimer --;
			}
			
			// Cool down for frames
			if(frameClock > 0)
			{
				frameClock --;
			}
			
			// Text box text output cool down
			if(textboxSpeed > 0)
			{
				textboxSpeed --;
			}
			
			if(shakeTime > 0)
			{
				shakeTime --;
				shakeGUI();
			}
			
			if(fadeSeq[0] || fadeSeq[1] || fadeSeq[2])
			{
				screenFade();
			}
			
			for(int i = 0; i < story.npcs.size(); i++)
			{
				if(story.npcs.get(i).getNPCFrameClock() > 0)
				{
					story.npcs.get(i).setNPCFrameClock(story.npcs.get(i).getNPCFrameClock() - 1);
				}
			}
			
			if(isFocused && !collision.getTransition())
			{
				// Listens for key presses
				keyPress.keyListen();
			}
			
			// Clear screen
			window.clear(Color.BLACK);
			renderList();			
			window.display();
		}
		/*
		 * ========================================
		 * End Game Loop
		 * ========================================
		 */
	}
	
	/**
	 * @method renderList - A list of all items in that game that need drawing depending
	 * on the current state of the game. When a state is activated, it will draw only
	 * what is needed for that state
	 */
	public static void renderList()
	{
		//Main game states
		switch(gameState)
		{
			case MENU:
			{
				window.setView(cameraGUI);
				window.draw(mainMenu.menuBackground);
				
				if(mainMenu.seq[2])
				{
					for(int i = 0; i < 20; i++)
					{
						window.draw(mainMenu.circle[i]);
					}
				}
					
				window.draw(mainMenu.menuTitle);
				window.draw(mainMenu.menuButton);
				
				mainMenu.intro();
				window.setView(camera);
				break;
			}
			case MAP:
			{
				window.setView(camera);
				
				for(int i = 0; i < mapLoader.numOfLayers; i++)
				{
					window.draw(mapLoader.layerSprite[i]);
				}
				
				for(int i = 0; i < story.npcs.size(); i++)
				{
					window.draw(story.npcs.get(i).npcSprite);
				}
				
				for(int i = 0; i < story.npcs.size(); i++)
				{
					story.npcs.get(i).npcWalk();
				}
				
				window.draw(player.playerSprite);
				
				if(editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
				{
					for(int i = 0; i < mapLoader.getLargerParam(); i++)
					{
						window.draw(mapLoader.mapGrid[i],Lines);
					}
				}
				
				if(collideState.equals(CtrlStates.CollideMap.ENABLED))
				{
					for(int i = 0; i < collision.cRectsDisplay.size(); i++)
					{
						window.draw(collision.cRectsDisplay.get(i));
					}
				}
				
				window.setView(cameraGUI);
				window.draw(fadeRect);
				
				if(editorState.equals(CtrlStates.MapEditor.ENABLEDGRID) || editorState.equals(CtrlStates.MapEditor.ENABLED))
				{
					window.draw(mapLoader.currentLayerNum);
				}
				
				menuRender();
				window.setView(camera);
				setCamera();
				break;
			}
			default:
			{
				System.out.println("Invalid game state");
				break;
			}
		}
		
		//Debug Menu states
		switch(debugState)
		{
			case ENABLED:
			{
				window.setView(cameraGUI);
				window.draw(debugMenu.debugTitle);
				window.draw(debugMenu.fpsText);
				window.draw(debugMenu.updatesText);
				window.draw(debugMenu.gameStateText);
				window.draw(debugMenu.debugStateText);
				window.draw(debugMenu.shellStateText);
				window.draw(debugMenu.mousePos);
				window.draw(debugMenu.cameraPos);
				window.setView(camera);
				window.draw(player.playerPos);
				
				for(int i = 0; i < story.npcs.size(); i++)
				{
					window.draw(story.npcs.get(i).npcPos);
				}
				
				window.draw(collision.north);
				window.draw(collision.east);
				window.draw(collision.south);
				window.draw(collision.west);
				window.draw(collision.playerHitbox);
				
				for(int i = 0; i < collision.cRectsDisplay.size(); i++)
				{
					window.draw(collision.cRectsDisplay.get(i));
				}
				
				for(int i = 0; i < story.npcs.size(); i++)
				{
					window.draw(story.npcs.get(i).npcHitbox);
					window.draw(story.npcs.get(i).point);
					window.draw(story.npcs.get(i).npcInteractHitbox);
				}
				
				window.draw(player.playerInteractHitbox);
				break;
			}
			case DISABLED:
			{
				//Debug view is disabled
				break;
			}
			default:
			{
				System.out.println("Invalid debug state");
				break;
			}
		}
		
		//Shell Interface states
		switch(shellState)
		{
			case ENABLED:
			{
				window.setView(cameraGUI);
				window.draw(shell.shellBg);
				window.draw(shell.shellBorder,LinesStrip);
				window.draw(shell.command);
				for(int i = 0; i < 10; i++)
				{
					window.draw(shell.displayLine[i]);
				}
				window.setView(camera);
				break;
			}
			case DISABLED:
			{
				//Debug view is disabled
				break;
			}
			default:
			{
				System.out.println("Invalid shell state");
				break;
			}
		}
	}
	
	public static void menuRender()
	{
		//Menu states
		switch(menuState)
		{
			case NONE:
			{
				// Do Nothing
				break;
			}	
			case INVENTORY:
			{
				window.draw(hud.inventory);
				break;
			}
			case ESC:
			{
				window.draw(hud.escMenuBG);
				window.draw(hud.escButton);
				break;
			}
			case TEXTBOX:
			{
				window.setView(cameraGUI);
				window.draw(hud.textbox);
				
				for(int i = 0; i < hud.textboxLine.length; i++)
				{
					window.draw(hud.textboxLine[i]);
				}
				
				window.draw(hud.textboxEntitySprite);
				window.draw(hud.textboxEntitySpriteName);
				
				hud.displayText();
				
				window.setView(camera);
				break;
			}
			case STATS:
			{
				window.setView(cameraGUI);
				window.draw(hud.statsMenu);
				window.draw(hud.stressRect);
				window.draw(hud.agilityRect);
				window.draw(hud.depressionRect);
				window.draw(hud.exhaustRect);
				window.draw(hud.sanityRect);
				window.draw(hud.intelligenceRect);
				window.draw(hud.levelRect);
				window.draw(hud.statsSprite);
				window.draw(hud.playerLevel);
				window.draw(hud.playerPounds);
				window.setView(camera);
				break;
			}
			case BATTLESYSTEM:
			{
				window.setView(cameraGUI);
				window.draw(hud.battleBG);
				window.draw(hud.battleScene);
				window.draw(hud.battleUI);
				
				window.draw(hud.battlePlayer);
				window.draw(hud.battleNpc);
				
				if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC1)
				{
					window.draw(hud.battlePlayerStats1);
					window.draw(hud.battleNpcStats1);
					
					window.draw(hud.stressRect);
					window.draw(hud.agilityRect);
					window.draw(hud.depressionRect);
					
					window.draw(hud.npcStressRect);
					window.draw(hud.npcAgilityRect);
					window.draw(hud.npcDepressionRect);
				}
				else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC2)
				{
					window.draw(hud.battlePlayerStats1);
					window.draw(hud.battleNpcStats2);
					
					window.draw(hud.stressRect);
					window.draw(hud.agilityRect);
					window.draw(hud.depressionRect);
					
					window.draw(hud.npcExhaustRect);
					window.draw(hud.npcSanityRect);
					window.draw(hud.npcIntelligenceRect);
				}
				else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC1)
				{
					window.draw(hud.battlePlayerStats2);
					window.draw(hud.battleNpcStats1);
					
					window.draw(hud.exhaustRect);
					window.draw(hud.sanityRect);
					window.draw(hud.intelligenceRect);
					
					window.draw(hud.npcStressRect);
					window.draw(hud.npcAgilityRect);
					window.draw(hud.npcDepressionRect);
				}
				else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC2)
				{
					window.draw(hud.battlePlayerStats2);
					window.draw(hud.battleNpcStats2);
					
					window.draw(hud.exhaustRect);
					window.draw(hud.sanityRect);
					window.draw(hud.intelligenceRect);
					
					window.draw(hud.npcExhaustRect);
					window.draw(hud.npcSanityRect);
					window.draw(hud.npcIntelligenceRect);
				}
				
				if(hud.getAttackDraw())
				{
					window.draw(hud.attackButtonSprite);
				}
				else if(hud.getAbilitiesDraw())
				{
					window.draw(hud.abilitiesButtonSprite);
				}
				else if(hud.getItemsDraw())
				{
					window.draw(hud.itemsButtonSprite);
				}
				else if(hud.getSummonsDraw())
				{
					window.draw(hud.summonsButtonSprite);
				}
				else if(hud.getTalkDraw())
				{
					window.draw(hud.talkButtonSprite);
				}
				if(hud.getFleeDraw())
				{
					window.draw(hud.fleeButtonSprite);
				}
				
				if(hud.getAttackNpc())
				{
					hud.attackNpc(hud.npcInBattle.getAttackID());
					window.draw(hud.hitsplat);
				}
				
				if(hud.getAttackPlayer())
				{
					hud.attackPlayer(2);
					window.draw(hud.hitsplat);
				}
				
				if(battleTextState.equals(CtrlStates.BattleSystemText.ENABLED))
				{
					window.setView(cameraGUI);
					window.draw(hud.textbox);
					
					for(int i = 0; i < hud.textboxLine.length; i++)
					{
						window.draw(hud.textboxLine[i]);
					}
					
					hud.displayText();
					
					window.setView(camera);
					break;
				}
				
				window.setView(camera);
				break;
			}
			default:
			{
				System.out.println("Invalid menu state");
				break;
			}
		}
	}
	
	public static void pollEvents()
	{
		/**
		 * All special events
		 */
		for(Event evnt : window.pollEvents())
		{
			// If user ever clicks the red "X" button at the top right
			if(evnt.type == Event.Type.CLOSED)
			{
				// Stop the game
				window.close();
			}
			
			// Text entered event
			if(evnt.type == Event.Type.TEXT_ENTERED)
			{
				// Text entered into shell
				if(shellState.equals(CtrlStates.Shell.ENABLED))
				{
					// Check the keys being entered are ASCII and only alphanumeric
					if(evnt.asTextEvent().unicode < 128 && evnt.asTextEvent().unicode > 31)
					{
						shell.tempCmd = shell.tempCmd + (char) evnt.asTextEvent().unicode;
						shell.command.setString("> " + shell.tempCmd);
					}
				}
			}
			
			// Moving mouse event
			if(evnt.type == Event.Type.MOUSE_MOVED)
			{
				mouseX = Mouse.getPosition(window).x;
				mouseY = Mouse.getPosition(window).y;
				
				if(gameState.equals(CtrlStates.State.MENU))
				{
					if(mainMenu.seq[2])
					{
						mainMenu.buttonClick(true);
					}
				}
				
				if(menuState.equals(CtrlStates.Menu.ESC))
				{
					hud.escButtonClick(true);
				}
				
				if(menuState.equals(CtrlStates.Menu.BATTLESYSTEM))
				{
					hud.battleSystemButtonHover();
				}
			}
			
			// Mouse clicking event (left and right)
			if(evnt.type == Event.Type.MOUSE_BUTTON_PRESSED)
			{
				isFocused = true;
				
				// Mouse clicking when in map edit mode
				if(editorState.equals(CtrlStates.MapEditor.ENABLED) || editorState.equals(CtrlStates.MapEditor.ENABLEDGRID))
				{
					mapLoader.findTile();
				}
				
				if(collideState.equals(CtrlStates.CollideMap.ENABLED))
				{
					if(Mouse.isButtonPressed(Button.LEFT))
					{
						collision.getPosition();
					}
					else if(Mouse.isButtonPressed(Button.RIGHT))
					{
						collision.removeBox();
					}
				}
				
				if(gameState.equals(CtrlStates.State.MENU))
				{
					if(mainMenu.seq[2])
					{
						mainMenu.buttonClick(false);
					}
				}
				
				if(menuState.equals(CtrlStates.Menu.ESC))
				{
					hud.escButtonClick(false);
				}
				
				if(menuState.equals(CtrlStates.Menu.BATTLESYSTEM))
				{
					hud.battleSystemPlayerUISwitch();
					hud.battleSystemNpcUISwitch();
					hud.battleSystemButtonClick();
				}
			}
		}
	}
	
	/**
	 * @method getImageFromFile - Gets an image from a path and loads it into memory
	 * @param path - The file path used to grab the image from
	 */
	public static Image getImageFromFile(String path)
	{
		//Load image into memory
		Image img = new Image();
		
		try
		{
			img.loadFromFile(Paths.get(path));
			img.createMaskFromColor(Color.CYAN);
			img.createMaskFromColor(Color.MAGENTA);
		}
		catch (IOException e)
		{
			System.err.println("ERROR loading image from file");
		    e.printStackTrace();
		}
		
		return img;
	}
	
	/**
	 * @method getTextureFromImage - Converts an image in memory into a texture held in VRAM
	 * @param img - The image to be converted
	 */
	public static Texture getTextureFromImage(Image img)
	{
		//Load texture into graphics
		Texture tex = new Texture();
		
		try
		{
			tex.loadFromImage(img);
		}
		catch (TextureCreationException e)
		{
			System.err.println("ERROR loading texture from image");
		    e.printStackTrace();
		}
		
		return tex;
	}
	
	public static Texture getTextureFromImageSheet(Image img,IntRect rect)
	{
		//Load texture into graphics
		Texture tex = new Texture();
		
		try
		{
			tex.loadFromImage(img,rect);
		}
		catch (TextureCreationException e)
		{
			System.err.println("ERROR loading texture from spritesheet image");
		    e.printStackTrace();
		}
		
		return tex;
	}
	
	public static Sprite getSpriteFromTexture(Texture tex)
	{
		//Load texture into sprite
		Sprite spr = new Sprite();
		
		spr.setTexture(tex);
		
		return spr;
	}
	
	/**
	 * @method getMusicFromFile - Gets a music file from a path and loads it into memory
	 * @param path - The file path used to grab the music from
	 */
	public static Music getMusicFromFile(String path)
	{
		//Load a music to play
		Music mus = new Music();
		
        try
		{
			mus.openFromFile(Paths.get(path));
		}
        catch (IOException e)
		{
        	System.err.println("ERROR loading music from file");
		    e.printStackTrace();
		}
		
		return mus;
	}
	
	/**
	 * @method getSoundFromFile - Gets a sound file from a path and loads it into memory
	 * @param path - The file path used to grab the music from
	 */
	public static SoundBuffer getSoundFromFile(String path)
	{
		//Load a sound to play
		SoundBuffer sb = new SoundBuffer();
		
        try
		{
			sb.loadFromFile(Paths.get(path));
		}
        catch (IOException e)
		{
        	System.err.println("ERROR loading sound from file");
		    e.printStackTrace();
		}
		
		return sb;
	}
	
	/**
	 * @method getFontFromFile - Gets a font from a path and loads it into memory
	 * @param path - The file path used to grab the font from
	 */
	public static Font getFontFromFile(String path)
	{
		Font fnt = new Font();
		
        try
		{
			fnt.loadFromFile(Paths.get(path));
		}
        catch (IOException e)
		{
        	System.err.println("ERROR loading font from file");
		    e.printStackTrace();
		}
        
        return fnt;
	}
	
	public static IntRect getRectangle(int left,int top,int width,int height)
	{
		IntRect rec = new IntRect(left,top,width,height);
		
		return rec;
	}
	
	/**
	 * @method positionText - Positions text on the screen starting from the top left.
	 * If centre is false the x and y values passed are exact, If centre is true the
	 * x and y values passed are relative from the centre
	 * @param x - The x position of the text
	 * @param y - The y position of the text
	 * @param txt - The text to get the height and width of
	 * @param centre - Decides if you want the text to be centred first
	 * @param pos - Decides if the text is to be rendered from the centre of it's position as oppose from the top left
	 */
	public Vector2f positionText(int x,int y,Text txt,boolean centre,boolean pos)
	{
		Vector2f textPos;		
		
		if(centre && pos)
		{
			int textWidth = (int) txt.getLocalBounds().width;
			int textHeight = (int) txt.getLocalBounds().height;
			
			int centerX = (windowX / 2) - (textWidth / 2);
			int centerY = (windowY / 2) - (textHeight / 2);
			
			textPos = new Vector2f(centerX + x,centerY + y);
		}
		else if(centre && !pos)
		{			
			int centerX = windowX / 2;
			int centerY = windowY / 2;
			
			textPos = new Vector2f(centerX + x,centerY + y);
		}
		else if(!centre && pos)
		{
			int textWidth = (int) txt.getLocalBounds().width;
			int textHeight = (int) txt.getLocalBounds().height;
			
			textPos = new Vector2f((textWidth / 2) + x,(textHeight / 2) + y);
		}
		else
		{
			textPos = new Vector2f(x,y);
		}
		
		return textPos;
	}
	
	/**
	 * @method setCoolDown - Sets a new cool down timer
	 * @param cooldown - The cool down (in game ticks) to wait before another key press can be executed upon
	 */
	public void setCoolDown(int cooldown)
	{
		coolDownTimer = cooldown;
	}
	
	public static void setCamera()
	{
		cameraX = camera.getCenter().x;
		cameraY = camera.getCenter().y;
		
		cameraX += ((player.getPlayerX() - 12) - cameraX) / 25;
		cameraY += ((player.getPlayerY() - 12) - cameraY) / 25;
				
		camera.setCenter(new Vector2f(cameraX,cameraY));
		
		if((camera.getCenter().x - 640) < 0 || (camera.getCenter().y - 360) < 0)
		{
			if((camera.getCenter().x - 640) < 0)
			{
				cameraX = 640;
			}
			
			if((camera.getCenter().y - 360) < 0)
			{
				cameraY = 360;
			}
			
			camera.setCenter(new Vector2f(cameraX,cameraY));
		}
		
		if((camera.getCenter().x + 640) > (mapLoader.getRoomSizeX() * 32) || (camera.getCenter().y + 360) > (mapLoader.getRoomSizeY() * 32))
		{
			if((camera.getCenter().x + 640) > (mapLoader.getRoomSizeX() * 32))
			{
				cameraX = (mapLoader.getRoomSizeX() * 32) - 640;
			}
			
			if((camera.getCenter().y + 360) > (mapLoader.getRoomSizeY() * 32))
			{
				cameraY = (mapLoader.getRoomSizeY() * 32) - 360;
			}
			
			camera.setCenter(new Vector2f(cameraX,cameraY));
		}
		
		cameraGUI.setCenter(new Vector2f(640,360));
	}
	
	public void transition(String mapName,int entrancePosX,int entrancePosY,CtrlStates.Room room)
	{
		fadeSeq[0] = true;
		
		transMapName = mapName;
		transEntrancePosX = entrancePosX;
		transEntrancePosY = entrancePosY;
		transRoom = room;
	}
	
	private static void screenFade()
	{
		if(fadeSeq[0])
		{
			fadeAlpha += 10;
			fadeRect.setFillColor(new Color(0,0,0,fadeAlpha));
			
			if(fadeAlpha >= 255)
			{
				fadeSeq[0] = false;
				fadeSeq[1] = true;
			}
		}
		else if(fadeSeq[1])
		{
			mapLoader.loadMap(transMapName);
			collision.loadCollision(transMapName);
			player.setPosition(transEntrancePosX,transEntrancePosY);
			setStaticCameraCenter(transEntrancePosX,transEntrancePosY);
			roomState = transRoom;
			
			story.loadRoomAssets(transRoom);
			roomName = transMapName;
			musicManager.changeMusic();
			
			fadeSeq[1] = false;
			fadeSeq[2] = true;
		}
		if(fadeSeq[2])
		{
			fadeAlpha -= 10;
			fadeRect.setFillColor(new Color(0,0,0,fadeAlpha));
			
			if(fadeAlpha <= 0)
			{
				fadeSeq[2] = false;
				collision.setTransition(false);
			}
		}
	}
	
	public void loadPlayerSave()
	{
		Scanner playerSave = null;
		boolean saveFound;
		String fileLine;
		
		try
		{
			playerSave = new Scanner(new File("assets/player.sav"));
			saveFound = true;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[Game] Error! File does not exist!");
			saveFound = false;
			e.printStackTrace();
		}
		
		if(saveFound)
		{
			while(playerSave.hasNextLine())
			{
				fileLine = playerSave.nextLine();
				
				String[] param = fileLine.split("=");
				
				switch(param[0])
				{
					case "name":
					{
						player.setName(param[1]);
						break;
					}
					case "stressLvl":
					{
						player.setStressLvl(Integer.parseInt(param[1]));
						break;
					}
					case "agilityLvl":
					{
						player.setAgilityLvl(Integer.parseInt(param[1]));
						break;
					}
					case "depressionLvl":
					{
						player.setDepressionLvl(Integer.parseInt(param[1]));
						break;
					}
					case "exhaustLvl":
					{
						player.setExhaustLvl(Integer.parseInt(param[1]));
						break;
					}
					case "sanityLvl":
					{
						player.setSanityLvl(Integer.parseInt(param[1]));
						break;
					}
					case "intelligenceLvl":
					{
						player.setIntelligenceLvl(Integer.parseInt(param[1]));
						break;
					}
					case "level":
					{
						player.setLevel(Integer.parseInt(param[1]));
						player.setXpToNextLevel(xpLevel[Integer.parseInt(param[1])]);
						break;
					}
					case "currentXp":
					{
						player.setCurrentXp(Integer.parseInt(param[1]));
						break;
					}
					case "X":
					{
						player.setPlayerX(Integer.parseInt(param[1]));
						break;
					}
					case "Y":
					{
						player.setPlayerY(Integer.parseInt(param[1]));
						break;
					}
					case "room":
					{
						roomState = CtrlStates.Room.valueOf(param[1]);
						break;
					}
					case "roomName":
					{
						roomName = param[1];
						break;
					}
					case "pounds":
					{
						player.setPounds(Integer.parseInt(param[1]));;
						break;
					}
				}
				
				if(param[0].contains("invSlot"))
				{
					for(int i = 0; i < 40; i++)
					{
						hud.setInvSlot(i,Integer.parseInt(param[1]));
					}
				}
			}
			
			mapLoader.loadMap(roomName);
			collision.loadCollision(roomName);			
			player.setPosition(player.getPlayerX(),player.getPlayerY());
			gameState = CtrlStates.State.MAP;	
		}
	}
	
	public void savePlayerSave()
	{					
		PrintWriter writer = null;
		
		try
		{
			writer = new PrintWriter("assets/player.sav", "UTF-8");
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
		
		writer.println("name=" + player.getName());
		writer.println("stressLvl=" + player.getStressLvl());
		writer.println("agilityLvl=" + player.getAgilityLvl());
		writer.println("depressionLvl=" + player.getDepressionLvl());
		writer.println("exhaustLvl=" + player.getExhaustLvl());
		writer.println("sanityLvl=" + player.getSanityLvl());
		writer.println("intelligence=" + player.getIntelligenceLvl());
		writer.println("level=" + player.getLevel());
		writer.println("currentXp=" + player.getCurrentXp());
		writer.println("X=" + player.getPlayerX());
		writer.println("Y=" + player.getPlayerY());
		writer.println("room=" + roomState);
		writer.println("roomName=" + roomName);
		writer.println("pounds=" + player.getPounds());
		
		for(int i = 0; i < 40; i++)
		{
			writer.println("invSlot" + i + "=" + hud.getInvSlot(i));
		}
		
		writer.close();
	}
	
	public int getMax(int a,int b)
	{
		if(a >= b)
		{
			return a;
		}
		
		if(b >= a)
		{
			return b;
		}
		return 0;
	}
	
	public static void shakeGUI()
	{		
		if(directShake)
		{
			if(cameraShake > 1)
			{
				directShake = false;
			}
			else
			{
				cameraShake ++;
			}
			cameraGUI.move(4,0);
		}
		if(!directShake)
		{
			if(cameraShake < -1)
			{
				directShake = true;
			}
			else
			{
				cameraShake --;
			}
			cameraGUI.move(-4,0);
		}
	}
	
	public static void generateXpReq()
	{
		int totalXp = 0;
		
		xpLevel[0] = 83;		
		System.out.format("| %5s | %10s | %10s |\n","Level","Difference","Total");
		System.out.format("| %5s | %10s | %10s |\n","1","77","77");
		
		totalXp += xpLevel[0];
		
		for(int i = 1; i < xpLevel.length; i++)
		{
			xpLevel[i] = (i * 83) + xpLevel[i - 1];
			totalXp += xpLevel[i];
			System.out.format("| %5s | %10s | %10s |\n",i + 1,xpLevel[i],totalXp);
		}
	}
	
	public static void updateLevel()
	{
		int remainXp = 0;
		
		if(player.getCurrentXp() > xpLevel[player.getLevel()])
		{
			remainXp = player.getCurrentXp() - xpLevel[player.getLevel()];
			player.setCurrentXp(remainXp);
			player.setLevel(player.getLevel() + 1);
			player.setXpToNextLevel(xpLevel[player.getLevel()]);
			
			System.out.println("Level up! > " + player.getLevel());
			
			if(player.getCurrentXp() > xpLevel[player.getLevel()])
			{
				updateLevel();
			}
		}
	}
	
	public float getViewX()
	{
		return camera.getCenter().x - 640;
	}
	
	public float getViewY()
	{
		return camera.getCenter().y - 360;
	}
	
	/**
	 * @method getCoolDown - Gets the current cool down timer
	 */
	public int getCoolDown()
	{
		return coolDownTimer;
	}
	
	public int getWindowX()
	{
		return windowX;
	}
	
	public int getWindowY()
	{
		return windowY;
	}
	
	public RenderWindow getWindow()
	{
		return window;
	}
	
	public int getMouseX()
	{
		return mouseX;
	}
	
	public int getMouseY()
	{
		return mouseY;
	}
	
	public float getCameraX()
	{
		return camera.getCenter().x;
	}
	
	public float getCameraY()
	{
		return camera.getCenter().y;
	}
	
	public static void setStaticCameraCenter(int x,int y)
	{
		camera.setCenter(x,y);
	}
	
	public void setCameraCenter(int x,int y)
	{
		camera.setCenter(x,y);
	}
}