import java.awt.Rectangle;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

public class NPC extends Game
{	
	private String name = "npc_name";
	private int stressLvl = 0;
	private int agilityLvl = 0;
	private int depressionLvl = 0;
	private int exhaustLvl = 0;
	private int sanityLvl = 0;
	private int intelligenceLvl = 0;
	
	private int maxStressLvl = 100;
	private int maxAgilityLvl = 100;
	private int maxDepressionLvl = 100;
	private int maxExhaustLvl = 100;
	private int maxSanityLvl = 100;
	private int maxIntelligenceLvl = 100;
	
	private int npcX = 0;
	private int npcY = 0;
	
	private int frame = 0;
	private int npcID = 0;
	private boolean willFight = false;
	private int attackingID = 0;
	
	IntRect spriteReadPosition;
	Sprite npcSprite;
	Texture npcSpriteTexture;
	Image npcSpriteSheet;
	RectangleShape npcHitbox;
	Rectangle npcHB;
	
	Text npcPos;
	
	private boolean[] walkSeq = new boolean[3];
	private int posToWalkX = (int) (Math.random() * 64) + 32;
	private int posToWalkY = (int) (Math.random() * 64) + 32;
	
	RectangleShape point;
	
	Rectangle npcIHB;
	RectangleShape npcInteractHitbox;
	
	private int lastMovedDirection;
	private boolean npcTalking = false;
	private int npcFrameClock;
	
	public NPC(String npcName,int stress,int agility,int depression,int exhaust,int sanity,int intelligence,int x,int y,int ID,boolean fight,int attID)
	{
		this.name = npcName;
		this.stressLvl = stress;
		this.agilityLvl = agility; 
		this.depressionLvl = depression;
		this.exhaustLvl = exhaust;
		this.sanityLvl = sanity;
		this.intelligenceLvl = intelligence;
		
		this.npcX = x;
		this.npcY = y;
		
		this.npcID = ID;
		this.willFight = fight;
		this.attackingID = attID;
		this.npcFrameClock = 0;
		
		this.spriteReadPosition = new IntRect(0,0,75,72);
		this.npcSpriteSheet = getImageFromFile("assets/npc/NPC.png");
		this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
		this.npcSprite = new Sprite(npcSpriteTexture);
		this.npcSprite.setPosition(x,y);
		
		this.walkSeq[0] = true;
		
		this.npcHitbox = new RectangleShape();
		this.npcHitbox.setSize(new Vector2f(33,6));
		this.npcHitbox.setFillColor(new Color(255,255,255,0));
		this.npcHitbox.setOutlineColor(new Color(0,0,0,255));
		this.npcHitbox.setOutlineThickness(1);
		
		this.npcHB = new Rectangle(x + 4,y - 4,33,6);
		
		this.point = new RectangleShape();
		this.point.setSize(new Vector2f(33,6));
		this.point.setOutlineColor(Color.WHITE);
		this.point.setFillColor(Color.BLACK);
		
		this.npcIHB = new Rectangle(npcX,npcY,75,72);
		this.npcInteractHitbox = new RectangleShape();
		this.npcInteractHitbox.setSize(new Vector2f(this.npcIHB.height,this.npcIHB.width));
		this.npcInteractHitbox.setPosition(npcIHB.x,npcIHB.y);
		this.npcInteractHitbox.setOutlineColor(Color.RED);
		this.npcInteractHitbox.setFillColor(new Color(255,255,255,0));
		this.npcInteractHitbox.setOutlineThickness(1);
		
		this.lastMovedDirection = 0;
		showPosition();
	}
	
	public void npcWalk()
	{
		if(!this.npcTalking && menuState != CtrlStates.Menu.BATTLESYSTEM)
		{
			if(this.walkSeq[0])
			{
				int chanceToMove = (int) (Math.random() * 101);
				
				if(chanceToMove > 99)
				{				
					int max = 64;
					int min = -64;
					int range = (max - min) + 1;     
					this.posToWalkX = npcX + (int)(Math.random() * range) + min;
					this.posToWalkY = npcY + (int)(Math.random() * range) + min;
					
					this.point.setPosition(posToWalkX + 21,posToWalkY + 57);
					
					if(!collision.checkDest(posToWalkX + 21,posToWalkY + 57) && (posToWalkX + 21 > 0 && posToWalkY + 57 > 0))
					{
						this.walkSeq[0] = false;
						this.walkSeq[1] = true;
					}
				}
			}
			if(this.walkSeq[1])
			{
				if(((posToWalkX + 21) - 2 <= (npcX + 21) && (posToWalkX + 21) + 2 >= (npcX + 21)) && ((posToWalkY + 57) - 2 <= (npcY + 57) && (posToWalkY + 57) + 2 >= (npcY + 57)))
				{
					this.walkSeq[1] = false;
					this.walkSeq[0] = true;
					
					switch(lastMovedDirection)
					{
						case 1:
						{
							this.spriteReadPosition = getRectangle(0,0,75,72);
							this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
							this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
							break;
						}
						case 2:
						{
							this.spriteReadPosition = getRectangle(0,147,75,72);
							this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
							this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
							break;
						}
						case 3:
						{
							this.spriteReadPosition = getRectangle(0,219,75,72);
							this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
							this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
							break;
						}
						case 4:
						{
							this.spriteReadPosition = getRectangle(0,75,75,72);
							this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
							this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
							break;
						}
					}
					this.npcSprite.setPosition(npcX,npcY);
				}
				else
				{
					if(this.posToWalkY + 57 > this.npcY + 57)
					{
						walkDown();
						lastMovedDirection = 1;
					}
					else if(this.posToWalkY + 57 < this.npcY + 57)
					{
						walkUp();
						lastMovedDirection = 2;
					}
					
					if(this.posToWalkX + 21 < this.npcX + 21)
					{
						walkLeft();
						lastMovedDirection = 3;
					}
					else if(this.posToWalkX + 21 > this.npcX + 21)
					{
						walkRight();
						lastMovedDirection = 4;
					}
				}
			}
		}
	}
	
	public void walkDown()
	{
		// 4 frames
		if(npcFrameClock == 0)
		{
			this.frame ++;
			
			if(frame > 3)
			{
				this.frame = 0;
			}
			
			this.spriteReadPosition = getRectangle(frame * 75,0,75,72);
			this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
			this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
			
			npcFrameClock = 4;
		}
		this.npcY += 1;
		this.npcSprite.setPosition(npcX,npcY);
		this.npcHB.x = this.npcX - 4;
		this.npcHB.y = this.npcY - 4;
		this.npcHitbox.setPosition(npcX + 21,npcY + 57);
		
		this.npcIHB.setLocation(this.npcX,this.npcY);
		this.npcInteractHitbox.setPosition(this.npcIHB.x,this.npcIHB.y);
		showPosition();
	}
	
	public void walkUp()
	{
		// 4 frames
		if(npcFrameClock == 0)
		{
			this.frame ++;
			
			if(frame > 3)
			{
				this.frame = 0;
			}
			
			this.spriteReadPosition = getRectangle(frame * 75,147,75,72);
			this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
			this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
			
			npcFrameClock = 4;
		}
		this.npcY -= 1;
		this.npcSprite.setPosition(npcX,npcY);
		this.npcHB.x = this.npcX - 4;
		this.npcHB.y = this.npcY - 4;
		this.npcHitbox.setPosition(npcX + 21,npcY + 57);
		
		this.npcIHB.setLocation(this.npcX,this.npcY);
		this.npcInteractHitbox.setPosition(this.npcIHB.x,this.npcIHB.y);
		showPosition();
	}
	
	public void walkLeft()
	{
		// 4 frames
		if(npcFrameClock == 0)
		{
			this.frame ++;
			
			if(frame > 3)
			{
				this.frame = 0;
			}
			
			this.spriteReadPosition = getRectangle(frame * 75,219,75,72);
			this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
			this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
			
			npcFrameClock = 5;
		}
		this.npcX -= 1;
		this.npcSprite.setPosition(npcX,npcY);
		this.npcHB.x = this.npcX - 4;
		this.npcHB.y = this.npcY - 4;
		this.npcHitbox.setPosition(npcX + 21,npcY + 57);
		
		this.npcIHB.setLocation(this.npcX,this.npcY);
		this.npcInteractHitbox.setPosition(this.npcIHB.x,this.npcIHB.y);
		showPosition();
	}
	
	public void walkRight()
	{
		// 4 frames
		if(npcFrameClock == 0)
		{
			this.frame ++;
			
			if(frame > 3)
			{
				this.frame = 0;
			}
			
			this.spriteReadPosition = getRectangle(frame * 75,75,75,72);
			this.npcSpriteTexture = getTextureFromImageSheet(npcSpriteSheet,spriteReadPosition);
			this.npcSprite = getSpriteFromTexture(npcSpriteTexture);
			
			npcFrameClock = 5;
		}
		this.npcX += 1;
		this.npcSprite.setPosition(npcX,npcY);
		this.npcHB.x = this.npcX - 4;
		this.npcHB.y = this.npcY - 4;
		this.npcHitbox.setPosition(npcX + 21,npcY + 57);
		
		this.npcIHB.setLocation(this.npcX,this.npcY);
		this.npcInteractHitbox.setPosition(this.npcIHB.x,this.npcIHB.y);
		showPosition();
	}
	
	public void showPosition()
	{		
		this.npcPos = new Text("X: " + npcX + ", Y: " + npcY,simpleBit,16);
		this.npcPos.setPosition(new Vector2f(npcX - 20,npcY - 20));
	}
	
	public int getID()
	{
		return this.npcID;
	}
	
	public int getNPCX()
	{
		return this.npcX;
	}
	
	public int getNPCY()
	{
		return this.npcY;
	}
	
	public boolean getNPCTalking()
	{
		return this.npcTalking;
	}
	
	public void setNPCTalking(boolean talk)
	{
		this.npcTalking = talk;
	}
	
	public int getNPCFrameClock()
	{
		return this.npcFrameClock;
	}
	
	public void setNPCFrameClock(int frame)
	{
		this.npcFrameClock = frame;
	}
	
	public boolean getWillFight()
	{
		return this.willFight;
	}
	
	public int getIntelligenceLvl()
	{
		return this.intelligenceLvl;
	}
	
	public void setIntelligenceLvl(int lvl)
	{
		this.intelligenceLvl = lvl;
	}
	
	public int getSanityLvl()
	{
		return this.sanityLvl;
	}
	
	public void setSanityLvl(int lvl)
	{
		this.sanityLvl = lvl;
	}
	
	public int getExhaustLvl()
	{
		return this.exhaustLvl;
	}
	
	public void setExhaustLvl(int lvl)
	{
		this.exhaustLvl = lvl;
	}
	
	public int getDepressionLvl()
	{
		return this.depressionLvl;
	}
	
	public void setDepressionLvl(int lvl)
	{
		this.depressionLvl = lvl;
	}
	
	public int getAgilityLvl()
	{
		return this.agilityLvl;
	}
	
	public void setAgilityLvl(int lvl)
	{
		this.agilityLvl = lvl;
	}
	
	public int getStressLvl()
	{
		return this.stressLvl;
	}
	
	public void setStressLvl(int lvl)
	{
		this.stressLvl = lvl;
	}
	
	public int getMaxIntelligenceLvl()
	{
		return this.maxIntelligenceLvl;
	}
	
	public void setMaxIntelligenceLvl(int lvl)
	{
		this.maxIntelligenceLvl = lvl;
	}
	
	public int getMaxSanityLvl()
	{
		return this.maxSanityLvl;
	}
	
	public void setMaxSanityLvl(int lvl)
	{
		this.maxSanityLvl = lvl;
	}
	
	public int getMaxExhaustLvl()
	{
		return this.maxExhaustLvl;
	}
	
	public void setMaxExhaustLvl(int lvl)
	{
		this.maxExhaustLvl = lvl;
	}
	
	public int getMaxDepressionLvl()
	{
		return this.maxDepressionLvl;
	}
	
	public void setMaxDepressionLvl(int lvl)
	{
		this.maxDepressionLvl = lvl;
	}
	
	public int getMaxAgilityLvl()
	{
		return this.maxAgilityLvl;
	}
	
	public void setMaxAgilityLvl(int lvl)
	{
		this.maxAgilityLvl = lvl;
	}
	
	public int getMaxStressLvl()
	{
		return this.maxStressLvl;
	}
	
	public void setMaxStressLvl(int lvl)
	{
		this.maxStressLvl = lvl;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String nam)
	{
		this.name = nam;
	}
	
	public int getAttackID()
	{
		return this.attackingID;
	}
	
	public void setAttackID(int id)
	{
		this.attackingID = id;
	}
}
