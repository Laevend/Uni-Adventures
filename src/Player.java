import java.awt.Rectangle;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class Player extends Game
{	
	private String name;
	private int stressLvl;
	private int agilityLvl;
	private int depressionLvl;
	private int exhaustLvl;
	private int sanityLvl;
	private int intelligenceLvl;
	
	private int maxStressLvl;
	private int maxAgilityLvl;
	private int maxDepressionLvl;
	private int maxExhaustLvl;
	private int maxSanityLvl;
	private int maxIntelligenceLvl;
	
	private int level;
	private int xpToNextLevel;
	private int currentXp;
	
	private int pounds;
	
	private int playerX;
	private int playerY;
	int walkSpeedMultiplier;
	
	int frame;
	
	IntRect spriteReadPosition;
	Sprite playerSprite;
	Texture playerSpriteTexture;
	Image playerSpriteSheet;
	
	Text playerPos;
	
	Rectangle playerIHB;
	RectangleShape playerInteractHitbox;
	
	public Player()
	{
		name = "Kobi";
		stressLvl = 5;
		agilityLvl = 80;
		depressionLvl = 0;
		exhaustLvl = 15;
		sanityLvl = 100;
		intelligenceLvl = 67;
		
		maxStressLvl = 100;
		maxAgilityLvl = 100;
		maxDepressionLvl = 100;
		maxExhaustLvl = 100;
		maxSanityLvl = 100;
		maxIntelligenceLvl = 100;
		
		level = 0;
		xpToNextLevel = xpLevel[0];
		currentXp = 0;
		
		pounds = 10;
		
		playerX = 0;
		playerY = 0;
		walkSpeedMultiplier = 1;
		
		frame = 0;
		
		spriteReadPosition = new IntRect(0,0,75,72);
		playerSpriteSheet = getImageFromFile("assets/sprite/player.png");
		playerSpriteTexture = getTextureFromImageSheet(playerSpriteSheet,spriteReadPosition);
		playerSprite = new Sprite(playerSpriteTexture);
		
		playerIHB = new Rectangle(playerX,playerY,75,72);
		playerInteractHitbox = new RectangleShape();
		playerInteractHitbox.setSize(new Vector2f(75,72));
		playerInteractHitbox.setPosition(playerX,playerY);
		playerInteractHitbox.setOutlineColor(Color.RED);
		playerInteractHitbox.setFillColor(new Color(255,255,255,0));
		playerInteractHitbox.setOutlineThickness(1);
		showPosition();
	}
	
	public void walkDown()
	{
		// 4 frames
		if(frameClock == 0)
		{
			frame ++;
			
			if(frame > 3)
			{
				frame = 0;
			}
			
			spriteReadPosition = getRectangle(frame * 75,0,75,72);
			playerSpriteTexture = getTextureFromImageSheet(playerSpriteSheet,spriteReadPosition);
			playerSprite = getSpriteFromTexture(playerSpriteTexture);
			frameClock = 5;
		}
		playerSprite.setPosition(playerX,playerY);
		playerIHB.setLocation(playerX,playerY);
		playerInteractHitbox.setPosition(playerIHB.x,playerIHB.y);
		showPosition();
	}
	
	public void walkUp()
	{
		// 4 frames
		if(frameClock == 0)
		{
			frame ++;
			
			if(frame > 3)
			{
				frame = 0;
			}
			
			spriteReadPosition = getRectangle(frame * 75,147,75,72);
			playerSpriteTexture = getTextureFromImageSheet(playerSpriteSheet,spriteReadPosition);
			playerSprite = getSpriteFromTexture(playerSpriteTexture);
			
			frameClock = 5;
		}
		playerSprite.setPosition(playerX,playerY);
		playerIHB.setLocation(playerX,playerY);
		playerInteractHitbox.setPosition(playerIHB.x,playerIHB.y);
		showPosition();
	}
	
	public void walkLeft()
	{
		// 4 frames
		if(frameClock == 0)
		{
			frame ++;
			
			if(frame > 3)
			{
				frame = 0;
			}
			
			spriteReadPosition = getRectangle(frame * 75,219,75,72);
			playerSpriteTexture = getTextureFromImageSheet(playerSpriteSheet,spriteReadPosition);
			playerSprite = getSpriteFromTexture(playerSpriteTexture);
			
			frameClock = 5;
		}
		playerSprite.setPosition(playerX,playerY);
		playerIHB.setLocation(playerX,playerY);
		playerInteractHitbox.setPosition(playerIHB.x,playerIHB.y);
		showPosition();
	}
	
	public void walkRight()
	{
		// 4 frames
		if(frameClock == 0)
		{
			frame ++;
			
			if(frame > 3)
			{
				frame = 0;
			}
			
			spriteReadPosition = getRectangle(frame * 75,75,75,72);
			playerSpriteTexture = getTextureFromImageSheet(playerSpriteSheet,spriteReadPosition);
			playerSprite = getSpriteFromTexture(playerSpriteTexture);
			
			frameClock = 5;
		}
		playerSprite.setPosition(playerX,playerY);
		playerIHB.setLocation(playerX,playerY);
		playerInteractHitbox.setPosition(playerIHB.x,playerIHB.y);
		showPosition();
	}
	
	public void setPosition(int x,int y)
	{
		playerX = x;
		playerY = y;
		playerSprite.setPosition(playerX,playerY);
		playerIHB.setLocation(playerX,playerY);
		playerInteractHitbox.setPosition(playerIHB.x,playerIHB.y);
		showPosition();
	}
	
	public void showPosition()
	{
		playerPos = new Text("X: " + playerX + ", Y: " + playerY,simpleBit,16);
		playerPos.setPosition(new Vector2f(playerX - 20,playerY - 20));
	}
	
	public int getIntelligenceLvl()
	{
		return intelligenceLvl;
	}
	
	public void setIntelligenceLvl(int lvl)
	{
		intelligenceLvl = lvl;
	}
	
	public int getSanityLvl()
	{
		return sanityLvl;
	}
	
	public void setSanityLvl(int lvl)
	{
		sanityLvl = lvl;
	}
	
	public int getExhaustLvl()
	{
		return exhaustLvl;
	}
	
	public void setExhaustLvl(int lvl)
	{
		exhaustLvl = lvl;
	}
	
	public int getDepressionLvl()
	{
		return depressionLvl;
	}
	
	public void setDepressionLvl(int lvl)
	{
		depressionLvl = lvl;
	}
	
	public int getAgilityLvl()
	{
		return agilityLvl;
	}
	
	public void setAgilityLvl(int lvl)
	{
		agilityLvl = lvl;
	}
	
	public int getStressLvl()
	{
		return stressLvl;
	}
	
	public void setStressLvl(int lvl)
	{
		stressLvl = lvl;
	}
	
	public int getMaxIntelligenceLvl()
	{
		return maxIntelligenceLvl;
	}
	
	public void setMaxIntelligenceLvl(int lvl)
	{
		maxIntelligenceLvl = lvl;
	}
	
	public int getMaxSanityLvl()
	{
		return maxSanityLvl;
	}
	
	public void setMaxSanityLvl(int lvl)
	{
		maxSanityLvl = lvl;
	}
	
	public int getMaxExhaustLvl()
	{
		return maxExhaustLvl;
	}
	
	public void setMaxExhaustLvl(int lvl)
	{
		maxExhaustLvl = lvl;
	}
	
	public int getMaxDepressionLvl()
	{
		return maxDepressionLvl;
	}
	
	public void setMaxDepressionLvl(int lvl)
	{
		maxDepressionLvl = lvl;
	}
	
	public int getMaxAgilityLvl()
	{
		return maxAgilityLvl;
	}
	
	public void setMaxAgilityLvl(int lvl)
	{
		maxAgilityLvl = lvl;
	}
	
	public int getMaxStressLvl()
	{
		return maxStressLvl;
	}
	
	public void setMaxStressLvl(int lvl)
	{
		maxStressLvl = lvl;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String nam)
	{
		name = nam;
	}
	
	public int getPlayerX()
	{
		return playerX;
	}
	
	public int getPlayerY()
	{
		return playerY;
	}
	
	public void setPlayerX(int x)
	{
		playerX = x;
	}
	
	public void setPlayerY(int y)
	{
		playerY = y;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel(int lvl)
	{
		level = lvl;
	}
	
	public int getCurrentXp()
	{
		return currentXp;
	}
	
	public void setCurrentXp(int xp)
	{
		currentXp = xp;
	}
	
	public int getXpToNextLevel()
	{
		return xpToNextLevel;
	}
	
	public void setXpToNextLevel(int xp)
	{
		xpToNextLevel = xp;
	}
	
	public int getPounds()
	{
		return pounds;
	}
	
	public void setPounds(int pound)
	{
		pounds = pound;
	}
}
