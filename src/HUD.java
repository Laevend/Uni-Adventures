import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;

public class HUD extends Game
{	
	//==================================< Escape Menu Stuff >==================================
	
	Sprite escMenuBG = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/escMenu.png")));
	Sprite escButton = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/blank.png")));
	Sprite[] escButtons = 
			{
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/options.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/save.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/saveDark.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/stats.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bestiary.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/quit.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/hud/blank.png")))
			};
	
	//==================================< Text Box Stuff >==================================
	
	Sprite textbox = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/textbox.png")));
	private static Sound sfxNpcTalkSound = new Sound();
	
	Text[] textboxLine = 
		{
				new Text("",halfBit,32),
				new Text("",halfBit,32),
				new Text("",halfBit,32),
				new Text("",halfBit,32),
				new Text("",halfBit,32)
		};
	
	ArrayList<String> textLines = new ArrayList<String>();
	int textboxDisplayLineNum;
	int textboxVirtualLineNum;
	int textboxCharPos;
	boolean displayText = false;
	
	SoundBuffer sfxText = new SoundBuffer();
	
	Sprite textboxEntitySprite;
	Text textboxEntitySpriteName = new Text("",halfBit,32);
	
	private int npcNumericalIdentifier = 0;
	
	//==================================< Stats Menu Stuff >==================================
	
	Sprite statsMenu = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/statsMenu.png")));
	
	RectangleShape stressRect = new RectangleShape();
	RectangleShape agilityRect = new RectangleShape();
	RectangleShape depressionRect = new RectangleShape();
	RectangleShape exhaustRect = new RectangleShape();
	RectangleShape sanityRect = new RectangleShape();
	RectangleShape intelligenceRect = new RectangleShape();
	
	RectangleShape levelRect = new RectangleShape();
	
	Sprite statsSprite;
	
	Text playerLevel;
	Text playerPounds;
	
	//==================================< Battle System Stuff >==================================
	
	Sprite battleUI = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/BSInterface.png")));
	
	Sprite battlePlayerStats1 = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/BSPlayerStats1.png")));
	Sprite battlePlayerStats2 = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/BSPlayerStats2.png")));
	
	Sprite battleNpcStats1 = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/BSNpcStats1.png")));
	Sprite battleNpcStats2 = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/BSNpcStats2.png")));
	
	Sprite battleScene = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/battleGroundIndoor.png")));
	
	RectangleShape npcStressRect = new RectangleShape();
	RectangleShape npcAgilityRect = new RectangleShape();
	RectangleShape npcDepressionRect = new RectangleShape();
	RectangleShape npcExhaustRect = new RectangleShape();
	RectangleShape npcSanityRect = new RectangleShape();
	RectangleShape npcIntelligenceRect = new RectangleShape();
	
	NPC npcInBattle;
	
	RectangleShape battleBG = new RectangleShape();
	
	Rectangle attackButton = new Rectangle(420,606,109,34);
	Rectangle abilitiesButton = new Rectangle(531,606,109,34);
	Rectangle itemsButton = new Rectangle(420,642,109,34);
	Rectangle summonsButton = new Rectangle(531,642,109,34);
	Rectangle talkButton = new Rectangle(420,678,109,34);
	Rectangle fleeButton = new Rectangle(531,678,109,34);
	
	Sprite attackButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/attack.png")));
	Sprite abilitiesButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/abilities.png")));
	Sprite itemsButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/items.png")));
	Sprite summonsButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/summons.png")));
	Sprite talkButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/talk.png")));
	Sprite fleeButtonSprite = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/bs/flee.png")));
	
	private boolean drawAttackButton = false;
	private boolean drawAbilitiesButton = false;
	private boolean drawItemsButton = false;
	private boolean drawSummonsButton = false;
	private boolean drawTalkButton = false;
	private boolean drawFleeButton = false;
	
	private boolean attackNpc = false;
	private boolean attackPlayer = false;
	
	Sprite battlePlayer;
	Sprite battleNpc;
	
	private boolean[] attackSeq = {true,false,false,false};
	private int battlePlayerFrameClock = 0;
	
	private Scanner attackFile;
	private ArrayList<String> listOfAttacks = new ArrayList<String>();
	
	Text hitsplat;
	
	//==================================< Battle System Sounds >==================================
	
	public static Sound sfxBattleSound = new Sound();
	
	// Harry's Sounds
	public static SoundBuffer sfxHarryHurt1 = new SoundBuffer();
	public static SoundBuffer sfxHarryHurt2 = new SoundBuffer();
	public static SoundBuffer sfxHarryHurt3 = new SoundBuffer();
	public static SoundBuffer sfxHarryHurt4 = new SoundBuffer();
	public static SoundBuffer sfxHarryDeath1 = new SoundBuffer();
	public static SoundBuffer sfxHarryDeath2 = new SoundBuffer();
	public static SoundBuffer sfxHarryTalk1 = new SoundBuffer();
	public static SoundBuffer sfxHarryTalk2 = new SoundBuffer();
	public static SoundBuffer sfxHarryTalk3 = new SoundBuffer();
	public static SoundBuffer sfxHarryTalk4 = new SoundBuffer();
	
	//==================================< Inventory Stuff >==================================
	
	Sprite inventory = new Sprite(getTextureFromImage(getImageFromFile("assets/hud/inv.png")));
	private ArrayList<Rectangle> invSlotsRect = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> armSlotsRect = new ArrayList<Rectangle>();
	private int[] invSlot = new int[40];
	
	public HUD()
	{
		//==========< Text box Stuff >==========
		
		for(int i = 0; i < textboxLine.length; i++)
		{
			textboxLine[i].setPosition(333,547 + (i * 25));
		}
		
		textboxDisplayLineNum = 0;
		textboxVirtualLineNum = 0;
		textboxCharPos = 1;
		
		sfxText = getSoundFromFile("assets/sfx/text.ogg");
		sfx.setBuffer(sfxText);
		
		textboxEntitySpriteName.setColor(new Color(0,255,255,255));
		
		//==========< Stats menu Stuff >==========
		
		stressRect.setFillColor(new Color(255,87,87,255));
		agilityRect.setFillColor(new Color(255,255,0,255));
		depressionRect.setFillColor(new Color(255,174,0,255));
		exhaustRect.setFillColor(new Color(0,255,186,255));
		sanityRect.setFillColor(new Color(0,0,255,255));
		intelligenceRect.setFillColor(new Color(0,255,0,255));
		
		levelRect.setFillColor(new Color(0,255,0,255));
		
		playerLevel = new Text("Lvl " + String.valueOf(player.getLevel()),halfBit,32);
		playerLevel.setColor(Color.WHITE);
		playerLevel.setPosition(545,180);
		
		playerPounds = new Text("Wallet: £" + String.valueOf(player.getPounds()),halfBit,32);
		playerPounds.setColor(Color.WHITE);
		playerPounds.setPosition(820 - playerPounds.getLocalBounds().width,180);
		
		//==========< Battle System Stuff >==========
		
		npcStressRect.setFillColor(new Color(255,87,87,255));
		npcAgilityRect.setFillColor(new Color(255,255,0,255));
		npcDepressionRect.setFillColor(new Color(255,174,0,255));
		npcExhaustRect.setFillColor(new Color(0,255,186,255));
		npcSanityRect.setFillColor(new Color(0,0,255,255));
		npcIntelligenceRect.setFillColor(new Color(0,255,0,255));
		
		battlePlayerStats1.setPosition(1,600);
		battlePlayerStats2.setPosition(1,600);
		
		battleNpcStats1.setPosition(904,600);
		battleNpcStats2.setPosition(904,600);
		
		battleBG.setFillColor(Color.BLACK);
		battleBG.setSize(new Vector2f(getWindowX(),getWindowY()));
		
		attackButtonSprite.setPosition(420,606);
		abilitiesButtonSprite.setPosition(531,606);
		itemsButtonSprite.setPosition(420,642);
		summonsButtonSprite.setPosition(531,642);
		talkButtonSprite.setPosition(420,678);
		fleeButtonSprite.setPosition(531,678);
		
		sfx.setBuffer(sfxText);
		
		hitsplat = new Text("",halfBit,64);
		hitsplat.setColor(Color.RED);
		
		loadAttack();
		
		//==========< Battle System Sounds >==========
		
		sfxHarryHurt1 = getSoundFromFile("assets/sfx/harryHurt1.ogg");
		sfxHarryHurt2 = getSoundFromFile("assets/sfx/harryHurt2.ogg");
		sfxHarryHurt3 = getSoundFromFile("assets/sfx/harryHurt3.ogg");
		sfxHarryHurt4 = getSoundFromFile("assets/sfx/harryHurt4.ogg");
		sfxHarryDeath1 = getSoundFromFile("assets/sfx/harryDeath1.ogg");
		sfxHarryDeath2 = getSoundFromFile("assets/sfx/harryDeath2.ogg");
		sfxHarryTalk1 = getSoundFromFile("assets/sfx/harryTalk1.ogg");
		sfxHarryTalk2 = getSoundFromFile("assets/sfx/harryTalk2.ogg");
		sfxHarryTalk3 = getSoundFromFile("assets/sfx/harryTalk3.ogg");
		sfxHarryTalk4 = getSoundFromFile("assets/sfx/harryTalk4.ogg");
		
		//==========< Inventory System >==========
		
		int invSlotOffsetX = 191;
		int invSlotOffsetY = 159;  
		
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				invSlotsRect.add(new Rectangle((j * 83) + invSlotOffsetX,(i * 85) + invSlotOffsetY,64,66));
			}
		}
		
		armSlotsRect.add(new Rectangle(947,159,64,66)); // Helm
		armSlotsRect.add(new Rectangle(947,243,64,66)); // Necklace
		armSlotsRect.add(new Rectangle(865,327,64,66)); // Main Hand
		armSlotsRect.add(new Rectangle(947,327,64,66)); // Torso
		armSlotsRect.add(new Rectangle(1029,327,64,66)); // Off Hand
		armSlotsRect.add(new Rectangle(947,411,64,66)); // Trousers
		armSlotsRect.add(new Rectangle(865,495,64,66)); // Gloves
		armSlotsRect.add(new Rectangle(947,495,64,66)); // Boots
		armSlotsRect.add(new Rectangle(1029,495,64,66)); // Ring
		
		for(int i = 0; i < 40; i++)
		{
			invSlot[i] = 0;
		}
	}
	
	public void escButtonClick(boolean hover)
	{
		Rectangle buttonOptions = new Rectangle(576,296,128,24);
		Rectangle buttonSave = new Rectangle(576,322,128,24);
		Rectangle buttonStats = new Rectangle(576,348,128,24);
		Rectangle buttonBestiary = new Rectangle(576,374,128,24);
		Rectangle buttonQuit = new Rectangle(576,400,128,24);
		
		if(buttonOptions.contains(getMouseX(),getMouseY()))
		{
			if(hover)
			{
				escButton = escButtons[0];
				escButton.setPosition(576,296);
			}
			else
			{
				// TODO Open options
			}
		}
		else if(buttonSave.contains(getMouseX(),getMouseY()))
		{
			if(hover)
			{
				escButton = escButtons[1];
				escButton.setPosition(576,322);
			}
			else
			{
				sfxClickSound.play();
				savePlayerSave();
			}
		}
		else if(buttonStats.contains(getMouseX(),getMouseY()))
		{
			if(hover)
			{
				escButton = escButtons[3];
				escButton.setPosition(576,348);
			}
			else
			{
				sfxClickSound.play();
				displayStats();
			}
		}
		else if(buttonBestiary.contains(getMouseX(),getMouseY()))
		{
			if(hover)
			{
				escButton = escButtons[4];
				escButton.setPosition(576,374);
			}
			else
			{
				// TODO Open bestiary
			}
		}
		else if(buttonQuit.contains(getMouseX(),getMouseY()))
		{
			if(hover)
			{
				escButton = escButtons[5];
				escButton.setPosition(576,400);
			}
			else
			{
				sfxClickSound.play();
				player.setPosition(50,50);
				setCameraCenter(640,360);	
				gameState = CtrlStates.State.MENU;
				menuState = CtrlStates.Menu.NONE;
				story.npcs.clear();
				musicManager.changeMusic();
			}
		}
		else
		{
			escButton = escButtons[6];
		}
	}
	
	public void loadText(int npcNum)
	{
		boolean npcDiaFound = false;
		boolean npcFileFound = false;
		String[] npcTextParams = null;
		String npcText = "";
		Scanner npcDia = null;
		
		try
		{
			npcDia = new Scanner(new File("assets/npc/npc.dat"));
			npcFileFound = true;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[NPC Dialogue] Error! File does not exist!");
			npcFileFound = false;
			e.printStackTrace();
		}
		
		if(npcFileFound)
		{
			while(!npcDiaFound && npcDia.hasNextLine())
			{
				npcTextParams = npcDia.nextLine().split(",");
				
				if(npcTextParams[0].matches("^[0-9]+$"))
				{
					if(story.npcs.get(npcNum).getID() == Integer.parseInt(npcTextParams[0]))
					{
						npcDiaFound = true;
					}
				}
			}
			
			for(int i = 0; i < Integer.parseInt(npcTextParams[1]); i++)
			{
				npcText += npcDia.nextLine() + " ";
			}
		}
		
		textboxEntitySprite = new Sprite(getTextureFromImageSheet(story.npcs.get(npcNum).npcSpriteSheet,new IntRect(25,10,27,24)));
		textboxEntitySprite.setScale(3,3);
		textboxEntitySprite.setPosition(215,592);
		textboxEntitySpriteName.setString(story.npcs.get(npcNum).getName());
		textboxEntitySpriteName.setPosition(((141 / 2) - (textboxEntitySpriteName.getLocalBounds().width / 2)) + 184,540); //141 + 184 (184 being distance from far left screen to box border, 141 being width of player skull box)
		
		prepText(npcText);
		story.npcs.get(npcNum).setNPCTalking(true);
		npcNumericalIdentifier = npcNum;
	}
	
	public void prepText(String textInput)
	{
		textLines.clear();
		
		int spaceLeft = 760;
		int lineNum = 0;
		String[] words;
		Text word = new Text("",halfBit,16);
		
		words = textInput.split(" ");
		
		for(int i = 0; i < words.length; i++)
		{
			word.setString(words[i]);
			
			if(((int) word.getLocalBounds().width + 30) <= spaceLeft)
			{
				if(textLines.isEmpty())
				{
					textLines.add(lineNum,words[i] + " ");
				}
				else
				{
					textLines.set(lineNum,textLines.get(lineNum) + words[i] + " ");
				}
				
				spaceLeft -= ((int) word.getLocalBounds().width + 30);
			}
			else
			{
				lineNum++;
				textLines.add(lineNum,words[i] + " ");
				spaceLeft = 760 - ((int) word.getLocalBounds().width + 30);
				
			}
		}
		
		for(int i = 0; i < textboxLine.length; i++)
		{
			textboxLine[i].setString("");
		}
		
		displayText = true;
		textboxVirtualLineNum = 0;
		textboxDisplayLineNum = 0;
		textboxCharPos = 0;
		sfxTextSound.setBuffer(sfxText);
	}
	
	public void displayText()
	{
		if(textboxSpeed == 0 && displayText)
		{			
			if(textboxCharPos >= textLines.get(textboxVirtualLineNum).length())
			{
				textboxCharPos = 1;
				
				if(textboxVirtualLineNum < textLines.size() - 1)
				{
					textboxVirtualLineNum ++;
				}
				else
				{
					displayText = false;
				}
				
				if(textboxDisplayLineNum < textboxLine.length - 1)
				{
					textboxDisplayLineNum ++;
				}
				else
				{
					textboxDisplayLineNum = 0;
					displayText = false;
				}
			}
			
			if(displayText)
			{
				textboxLine[textboxDisplayLineNum].setString(textLines.get(textboxVirtualLineNum).substring(0,textboxCharPos));
				
				if(textboxCharPos > 0)
				{
					switch(textLines.get(textboxVirtualLineNum).charAt(textboxCharPos - 1))
					{
						case '.':
						{
							textboxSpeed = 40;
							sfxTextSound.play();
							break;
						}
						case '!':
						{
							textboxSpeed = 40;
							sfxTextSound.play();
							break;
						}
						case '?':
						{
							textboxSpeed = 40;
							sfxTextSound.play();
							break;
						}
						case ' ':
						{
							textboxSpeed = 4;
							break;
						}
						default:
						{
							textboxSpeed = 4;
							sfxTextSound.play();
							break;
						}
					}	
				}
			}
			textboxCharPos ++;
		}
	}
	
	public void manageText()
	{
		boolean finishBox = false;
		
		if(displayText)
		{
			displayText = false;
			
			textboxLine[textboxDisplayLineNum].setString(textLines.get(textboxVirtualLineNum));
			
			while(!finishBox)
			{
				if(textboxVirtualLineNum < textLines.size() - 1)
				{
					textboxVirtualLineNum ++;
				}
				else
				{
					finishBox = true;
				}
				
				if(textboxDisplayLineNum < textboxLine.length - 1)
				{
					textboxDisplayLineNum ++;
				}
				else
				{
					finishBox = true;
				}
				
				if(!finishBox)
				{
					textboxLine[textboxDisplayLineNum].setString(textLines.get(textboxVirtualLineNum));
				}	
			}
			
			textboxDisplayLineNum = 0;
			textboxCharPos = 1;
		}
		else if(!displayText)
		{
			if(textboxVirtualLineNum < textLines.size() - 1)
			{
				for(int i = 0; i < textboxLine.length; i++)
				{
					textboxLine[i].setString("");
				}
				
				displayText = true;
			}
			else
			{
				if(story.npcs.get(npcNumericalIdentifier).getWillFight() && menuState != CtrlStates.Menu.BATTLESYSTEM)
				{
					menuState = CtrlStates.Menu.BATTLESYSTEM;
					startBattle(story.npcs.get(npcNumericalIdentifier));
					musicManager.changeMusic();
				}
				else
				{
					menuState = CtrlStates.Menu.NONE;
					battleTextState = CtrlStates.BattleSystemText.DISABLED;
					story.npcs.get(npcNumericalIdentifier).setNPCTalking(false);
					musicManager.changeMusic();
				}
			}
		}
	}
	
	public void displayStats()
	{
		menuState = CtrlStates.Menu.STATS;
		
		stressRect.setPosition(554,291);
		agilityRect.setPosition(554,333);
		depressionRect.setPosition(554,375);
		exhaustRect.setPosition(554,417);
		sanityRect.setPosition(554,459);
		intelligenceRect.setPosition(554,501);
		
		levelRect.setPosition(554,223);
		
		stressRect.setSize(new Vector2f((254 / (float) player.getMaxStressLvl()) * player.getStressLvl(),14));
		agilityRect.setSize(new Vector2f((254 / (float) player.getMaxAgilityLvl()) * player.getAgilityLvl(),14));
		depressionRect.setSize(new Vector2f((254 / (float) player.getMaxDepressionLvl()) * player.getDepressionLvl(),14));
		exhaustRect.setSize(new Vector2f((254 / (float) player.getMaxExhaustLvl()) * player.getExhaustLvl(),14));
		sanityRect.setSize(new Vector2f((254 / (float) player.getMaxSanityLvl()) * player.getSanityLvl(),14));
		intelligenceRect.setSize(new Vector2f((254 / (float) player.getMaxIntelligenceLvl()) * player.getIntelligenceLvl(),14));
		
		levelRect.setSize(new Vector2f((254 / (float) player.getXpToNextLevel()) * player.getCurrentXp(),14));
		
		playerLevel.setString("Lvl " + String.valueOf(player.getLevel()));
		playerPounds.setString("Wallet: £" + String.valueOf(player.getPounds()));
		
		statsSprite = new Sprite(getTextureFromImageSheet(player.playerSpriteSheet,new IntRect(25,10,27,24)));
		statsSprite.setScale(2,2);
		statsSprite.setPosition(478,208);
		
	}
	
	public void battleSystemPlayerUISwitch()
	{
		if((getMouseX() > 0 && getMouseX() < 377) && (getMouseY() > 599 && getMouseY() < 721))
		{
			if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC1)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER2ANDNPC1;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC2)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER2ANDNPC2;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC1)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER1ANDNPC1;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC2)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER1ANDNPC2;
			}
			else
			{
				System.out.println("[Hud] Invalid UI State <player>");
			}
			sfxClickSound.play();
		}
	}
	
	public void battleSystemNpcUISwitch() // 376 600
	{
		if((getMouseX() > 904 && getMouseX() < 1281) && (getMouseY() > 599 && getMouseY() < 721))
		{
			if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC1)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER1ANDNPC2;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER1ANDNPC2)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER1ANDNPC1;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC1)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER2ANDNPC2;
			}
			else if(battleUIState == CtrlStates.BattleSystemUI.PLAYER2ANDNPC2)
			{
				battleUIState = CtrlStates.BattleSystemUI.PLAYER2ANDNPC1;
			}
			else
			{
				System.out.println("[Hud] Invalid UI State <npc>");
			}
			sfxClickSound.play();
		}
	}
	
	public void battleSystemButtonHover()
	{
		if(battleTextState.equals(CtrlStates.BattleSystemText.DISABLED))
		{
			if(attackButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = true;
				drawAbilitiesButton = false;
				drawItemsButton = false;
				drawSummonsButton = false;
				drawTalkButton = false;
				drawFleeButton = false;
			}
			else if(abilitiesButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = false;
				drawAbilitiesButton = true;
				drawItemsButton = false;
				drawSummonsButton = false;
				drawTalkButton = false;
				drawFleeButton = false;
			}
			else if(itemsButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = false;
				drawAbilitiesButton = false;
				drawItemsButton = true;
				drawSummonsButton = false;
				drawTalkButton = false;
				drawFleeButton = false;
			}
			else if(summonsButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = false;
				drawAbilitiesButton = false;
				drawItemsButton = false;
				drawSummonsButton = true;
				drawTalkButton = false;
				drawFleeButton = false;
			}
			else if(talkButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = false;
				drawAbilitiesButton = false;
				drawItemsButton = false;
				drawSummonsButton = false;
				drawTalkButton = true;
				drawFleeButton = false;
			}
			else if(fleeButton.contains(getMouseX(),getMouseY()))
			{
				drawAttackButton = false;
				drawAbilitiesButton = false;
				drawItemsButton = false;
				drawSummonsButton = false;
				drawTalkButton = false;
				drawFleeButton = true;
			}
			else
			{
				drawAttackButton = false;
				drawAbilitiesButton = false;
				drawItemsButton = false;
				drawSummonsButton = false;
				drawTalkButton = false;
				drawFleeButton = false;
			}
		}
	}
	
	public void battleSystemButtonClick()
	{
		if(battleTextState.equals(CtrlStates.BattleSystemText.DISABLED))
		{
			if(attackButton.contains(getMouseX(),getMouseY()))
			{
				if(!attackNpc && !attackPlayer)
				{
					sfxClickSound.play();
					attackNpc = true;
				}
			}
			else if(abilitiesButton.contains(getMouseX(),getMouseY()))
			{
				sfxClickSound.play();
			}
			else if(itemsButton.contains(getMouseX(),getMouseY()))
			{
				sfxClickSound.play();
			}
			else if(summonsButton.contains(getMouseX(),getMouseY()))
			{
				sfxClickSound.play();
			}
			else if(talkButton.contains(getMouseX(),getMouseY()))
			{
				sfxClickSound.play();
			}
			else if(fleeButton.contains(getMouseX(),getMouseY()))
			{
				sfxClickSound.play();
			}
		}
	}
	
	public void startBattle(NPC npcBattle)
	{
		npcInBattle = npcBattle;
		
		stressRect.setPosition(103,618);
		agilityRect.setPosition(103,652);
		depressionRect.setPosition(103,686);
		exhaustRect.setPosition(103,618);
		sanityRect.setPosition(103,652);
		intelligenceRect.setPosition(103,686);
		
		updatePlayerStats();
		
		npcStressRect.setPosition(924,618);
		npcAgilityRect.setPosition(924,652);
		npcDepressionRect.setPosition(924,686);
		npcExhaustRect.setPosition(924,618);
		npcSanityRect.setPosition(924,652);
		npcIntelligenceRect.setPosition(924,686);
		
		updateNpcStats();
		
		battlePlayer = getSpriteFromTexture(getTextureFromImageSheet(player.playerSpriteSheet,new IntRect(0,75,75,72)));
		battleNpc = getSpriteFromTexture(getTextureFromImageSheet(npcInBattle.npcSpriteSheet,new IntRect(0,219,75,72)));
		
		battlePlayer.setPosition(32,415);
		battleNpc.setPosition(1180,415);
		
		attackSeq[0] = true;
		attackSeq[1] = false;
		attackSeq[2] = false;
		
		hitsplat.setString("");
	}
	
	public void updatePlayerStats()
	{
		stressRect.setSize(new Vector2f((254 / (float) player.getMaxStressLvl()) * player.getStressLvl(),14));
		agilityRect.setSize(new Vector2f((254 / (float) player.getMaxAgilityLvl()) * player.getAgilityLvl(),14));
		depressionRect.setSize(new Vector2f((254 / (float) player.getMaxDepressionLvl()) * player.getDepressionLvl(),14));
		exhaustRect.setSize(new Vector2f((254 / (float) player.getMaxExhaustLvl()) * player.getExhaustLvl(),14));
		sanityRect.setSize(new Vector2f((254 / (float) player.getMaxSanityLvl()) * player.getSanityLvl(),14));
		intelligenceRect.setSize(new Vector2f((254 / (float) player.getMaxIntelligenceLvl()) * player.getIntelligenceLvl(),14));
	}
	
	public void updateNpcStats()
	{
		npcStressRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxStressLvl()) * npcInBattle.getStressLvl(),14));
		npcAgilityRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxAgilityLvl()) * npcInBattle.getAgilityLvl(),14));
		npcDepressionRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxDepressionLvl()) * npcInBattle.getDepressionLvl(),14));
		npcExhaustRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxExhaustLvl()) * npcInBattle.getExhaustLvl(),14));
		npcSanityRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxSanityLvl()) * npcInBattle.getSanityLvl(),14));
		npcIntelligenceRect.setSize(new Vector2f((254 / (float) npcInBattle.getMaxIntelligenceLvl()) * npcInBattle.getIntelligenceLvl(),14));
	}
	
	public void loadAttack()
	{
		boolean attackFound = false;
		
		try
		{
			attackFile = new Scanner(new File("assets/moves/attack.dat"));
			attackFound = true;
		}
		catch (FileNotFoundException e)
		{
			System.out.println("[HUD Attack Loader] Error! File does not exist!");
			attackFound = false;
			e.printStackTrace();
		}
		
		attackFile.nextLine();
		
		if(attackFound)
		{
			while(attackFile.hasNextLine())
			{
				listOfAttacks.add(attackFile.nextLine());
			}
		}
	}
	
	public void attackNpc(int attackID)
	{
		String[] attackParam = null;
		String[] tempParam = null;
		
		for(int i = 0; i < listOfAttacks.size(); i++)
		{
			tempParam = listOfAttacks.get(i).split(",");
			
			if(Integer.parseInt(tempParam[0]) == attackID)
			{
				attackParam = listOfAttacks.get(i).split(",");
			}
		}
		
		if(attackSeq[0])
		{
			hitsplat.setString("");
			battlePlayer.setTexture(getTextureFromImageSheet(player.playerSpriteSheet,new IntRect(battlePlayerFrameClock * 75,Integer.parseInt(attackParam[1]) * 72,75,72)));
			battlePlayer.move(Integer.parseInt(attackParam[3]),0);
			
			if(battlePlayerFrameClock < Integer.parseInt(attackParam[2]) - 1)
			{
				battlePlayerFrameClock++;
			}
			else
			{
				battlePlayerFrameClock = 0;
			}
			
			if(battlePlayer.getPosition().x > 1170 && battlePlayer.getPosition().x < 1190)
			{
				attackSeq[0] = false;
				attackSeq[1] = true;
			}
		}
		else if(attackSeq[1])
		{						
			selectSound(attackParam[4]);
			
			shakeTime = 30;
			
			damageNpc(attackParam[4]);
			updateNpcStats();
			updatePlayerStats();
			
			attackSeq[1] = false;
			attackSeq[2] = true;
		}
		else if(attackSeq[2])
		{			
			battlePlayer.setTexture(getTextureFromImageSheet(player.playerSpriteSheet,new IntRect(battlePlayerFrameClock * 75,Integer.parseInt(attackParam[1]) * 72,75,72)));
			battlePlayer.move(-Integer.parseInt(attackParam[3]),0);
			
			if(battlePlayerFrameClock < Integer.parseInt(attackParam[2]) - 1)
			{
				battlePlayerFrameClock++;
			}
			else
			{
				battlePlayerFrameClock = 0;
			}
			
			if(battlePlayer.getPosition().x > 20 && battlePlayer.getPosition().x < 40)
			{
				battlePlayer.setPosition(32,415);
				attackSeq[2] = false;
				attackSeq[0] = true;
				attackNpc = false;
				
				if(battleTextState == CtrlStates.BattleSystemText.DISABLED)
				{
					attackPlayer = true;
				}
			}
		}
	}
	
	public void attackPlayer(int attackID)
	{
		String[] attackParam = null;
		String[] tempParam = null;
		
		for(int i = 0; i < listOfAttacks.size(); i++)
		{
			tempParam = listOfAttacks.get(i).split(",");
			
			if(Integer.parseInt(tempParam[0]) == attackID)
			{
				attackParam = listOfAttacks.get(i).split(",");
			}
		}
		
		if(attackSeq[0])
		{
			hitsplat.setString("");
			battleNpc.setTexture(getTextureFromImageSheet(npcInBattle.npcSpriteSheet,new IntRect(battlePlayerFrameClock * 75,Integer.parseInt(attackParam[1]) * 72,75,72)));			
			battleNpc.move(-Integer.parseInt(attackParam[3]),0);
			
			if(battlePlayerFrameClock < 3)
			{
				battlePlayerFrameClock++;
			}
			else
			{
				battlePlayerFrameClock = 0;
			}
			
			if(battleNpc.getPosition().x > 20 && battleNpc.getPosition().x < 40)
			{
				attackSeq[0] = false;
				attackSeq[1] = true;
			}
		}
		else if(attackSeq[1])
		{						
			selectSound(attackParam[4]);
			
			shakeTime = 30;
			
			damagePlayer();
			updateNpcStats();
			updatePlayerStats();
			
			attackSeq[1] = false;
			attackSeq[2] = true;
		}
		else if(attackSeq[2])
		{			
			battleNpc.setTexture(getTextureFromImageSheet(npcInBattle.npcSpriteSheet,new IntRect(battlePlayerFrameClock * 75,Integer.parseInt(attackParam[1]) * 72,75,72)));
			battleNpc.move(Integer.parseInt(attackParam[3]),0);
			
			if(battlePlayerFrameClock < Integer.parseInt(attackParam[2]) - 1)
			{
				battlePlayerFrameClock++;
			}
			else
			{
				battlePlayerFrameClock = 0;
			}
			
			if(battleNpc.getPosition().x > 1170 && battleNpc.getPosition().x < 1190)
			{
				battleNpc.setPosition(1180,415);
				attackSeq[2] = false;
				attackSeq[0] = true;
				attackPlayer = false;
			}
		}
	}
	
	public void selectSound(String attackName)
	{
		switch(attackName)
		{
			case "hit":
			{
				int soundVar = (int) ((Math.random() * 3) + 1);
				switch(soundVar)
				{
					case 1:
					{
						sfxBattleSound.setBuffer(sfxHit1);
						break;
					}
					case 2:
					{
						sfxBattleSound.setBuffer(sfxHit2);
						break;
					}
					case 3:
					{
						sfxBattleSound.setBuffer(sfxHit3);
						break;
					}
					case 4:
					{
						sfxBattleSound.setBuffer(sfxHit4);
						break;
					}
				}
				
				sfxBattleSound.play();
				break;
			}
			case "harry":
			{
				int soundVar = (int) ((Math.random() * 3) + 1);
				switch(soundVar)
				{
					case 1:
					{
						sfxBattleSound.setBuffer(sfxHarryHurt1);
						break;
					}
					case 2:
					{
						sfxBattleSound.setBuffer(sfxHarryHurt2);
						break;
					}
					case 3:
					{
						sfxBattleSound.setBuffer(sfxHarryHurt3);
						break;
					}
					case 4:
					{
						sfxBattleSound.setBuffer(sfxHarryHurt4);
						break;
					}
				}
				
				sfxBattleSound.play();
				break;
			}
		}
		
		if(!attackName.equals("hit"))
		{
			int soundVar = (int) ((Math.random() * 3) + 1);
			switch(soundVar)
			{
				case 1:
				{
					sfx.setBuffer(sfxHit1);
					break;
				}
				case 2:
				{
					sfx.setBuffer(sfxHit2);
					break;
				}
				case 3:
				{
					sfx.setBuffer(sfxHit3);
					break;
				}
				case 4:
				{
					sfx.setBuffer(sfxHit4);
					break;
				}
			}
			
			sfx.play();
		}
	}
	
	public void damageNpc(String attackName)
	{
		// Initial exhaustion taken from attacking
		player.setExhaustLvl(player.getExhaustLvl() + 2);
		
		// Incoming damage the Npc takes from the player
		int damageTaken = (int) ((Math.random() * 60) + 1);
		npcInBattle.setStressLvl(npcInBattle.getStressLvl() + damageTaken);
		hitsplat.setString(String.valueOf(damageTaken));
		hitsplat.setPosition(battleNpc.getPosition().x + 20,battleNpc.getPosition().y - 70);
		
		// If the damage taken exceeds their maximum stress lvl, set it to it's maximum
		if(npcInBattle.getStressLvl() > npcInBattle.getMaxStressLvl())
		{
			npcInBattle.setStressLvl(npcInBattle.getMaxStressLvl());
			
			// Since the npc's stress bar is now maxed, their sanity will start to drain from the damage taken.
			// Incoming damage the Npc takes from the player
			npcInBattle.setSanityLvl(npcInBattle.getSanityLvl() - damageTaken);
			
			// If the damage taken exceeds their minimum stress lvl, set it to it's minimum
			if(npcInBattle.getSanityLvl() < 0)
			{
				npcInBattle.setSanityLvl(0);
				
				// Since the sanity bar is now drained and the stress bar is maxed, the npc's exhaustion bar will increase much more with each strike
				// Incoming damage the Npc takes from the player
				npcInBattle.setExhaustLvl(npcInBattle.getExhaustLvl() + damageTaken);
				
				// If the damage taken exceeds their minimum exhaust lvl, set it to it's minimum
				if(npcInBattle.getExhaustLvl() > npcInBattle.getMaxExhaustLvl())
				{
					npcInBattle.setExhaustLvl(npcInBattle.getMaxExhaustLvl());
					
					battleTextState = CtrlStates.BattleSystemText.ENABLED;
					npcDeathEffect(attackName);
					prepText("You have won!");
				}
			} // credit : harry :) grab some popcorn
		}
	}
	
	public void npcDeathEffect(String attackName)
	{
		if(attackName.equals("harry"))
		{
			int soundVar = (int) ((Math.random() * 2) + 1);
			switch(soundVar)
			{
				case 1:
				{
					sfxBattleSound.setBuffer(sfxHarryDeath1);
					break;
				}
				case 2:
				{
					sfxBattleSound.setBuffer(sfxHarryDeath2);
					break;
				}
			}
			
			sfxBattleSound.play();
		}
	}
	
	public void damagePlayer()
	{
		// Initial exhaustion taken from attacking
		npcInBattle.setExhaustLvl(npcInBattle.getExhaustLvl() + 2);
		
		// Incoming damage the player takes from the npc
		int damageTaken = (int) ((Math.random() * 60) + 1);
		player.setStressLvl(player.getStressLvl() + damageTaken);
		hitsplat.setString(String.valueOf(damageTaken));
		hitsplat.setPosition(battlePlayer.getPosition().x + 20,battlePlayer.getPosition().y - 70);
		
		// If the damage taken exceeds their maximum stress lvl, set it to it's maximum
		if(player.getStressLvl() > player.getMaxStressLvl())
		{
			player.setStressLvl(player.getMaxStressLvl());
			
			// Since the players stress bar is now maxed, their sanity will start to drain from the damage taken.
			// Incoming damage the player takes from the npc
			player.setSanityLvl(player.getSanityLvl() - damageTaken);
			
			// If the damage taken exceeds their minimum stress lvl, set it to it's minimum
			if(player.getSanityLvl() < 0)
			{
				player.setSanityLvl(0);
				
				// Since the sanity bar is now drained and the stress bar is maxed, the players exhaustion bar will increase much more with each strike
				// Incoming damage the player takes from the npc
				player.setExhaustLvl(player.getExhaustLvl() + damageTaken);
				
				// If the damage taken exceeds their minimum exhaust lvl, set it to it's minimum
				if(player.getExhaustLvl() > player.getMaxExhaustLvl())
				{
					player.setExhaustLvl(player.getMaxExhaustLvl());
					
					battleTextState = CtrlStates.BattleSystemText.ENABLED;
					prepText("You have lost...");
					
					player.setExhaustLvl(5);
					player.setSanityLvl(80);
					player.setStressLvl(2);
				}
			}
		}
	}
	
	public boolean getAttackDraw()
	{
		return drawAttackButton;
	}
	
	public boolean getAbilitiesDraw()
	{
		return drawAbilitiesButton;
	}
	
	public boolean getItemsDraw()
	{
		return drawItemsButton;
	}
	
	public boolean getSummonsDraw()
	{
		return drawSummonsButton;
	}
	
	public boolean getTalkDraw()
	{
		return drawTalkButton;
	}
	
	public boolean getFleeDraw()
	{
		return drawFleeButton;
	}
	
	public boolean getAttackNpc()
	{
		return attackNpc;
	}
	
	public boolean getAttackPlayer()
	{
		return attackPlayer;
	}
	
	public void setInvSlot(int slotNum,int itemID)
	{
		invSlot[slotNum] = itemID;
	}
	
	public int getInvSlot(int slotNum)
	{
		return invSlot[slotNum];
	}
	
	public void setNpcTextSound(int snd)
	{
		switch(snd)
		{
			case 1:
			{
				sfxNpcTalkSound.setBuffer(sfxHarryTalk1);
				break;
			}
			case 2:
			{
				sfxNpcTalkSound.setBuffer(sfxHarryTalk2);
				break;
			}
			case 3:
			{
				sfxNpcTalkSound.setBuffer(sfxHarryTalk3);
				break;
			}
			case 4:
			{
				sfxNpcTalkSound.setBuffer(sfxHarryTalk4);
				break;
			}
		}
	}
	
	public void playNpcTextSound()
	{
		sfxNpcTalkSound.play();
	}
}
