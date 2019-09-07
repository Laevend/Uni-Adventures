import java.io.File;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

public class MainMenu extends Game
{
	/**
	 * @param menuBackground - The background image of the main game menu (what you see when the game first starts)
	 * @param musMenu - The music to be played at the main game menu
	 * @param simpleBit - The font of the text used
	 */
	
	Sprite menuBackground = new Sprite(getTextureFromImage(getImageFromFile("assets/menu/menu.png")));
	Sprite menuTitle = new Sprite(getTextureFromImage(getImageFromFile("assets/menu/title.png")));
	Sprite menuButton = new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons.png")));
	Sprite[] menuButtons = 
			{
					new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons1.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons2.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons3.png"))),
					new Sprite(getTextureFromImage(getImageFromFile("assets/menu/buttons4.png")))
			};
	
    Text pressEnter;
    
    CircleShape[] circle = 
    	{
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0),
    			new CircleShape(0)
    	};
    
    int[] circleSpeed = {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5};
	
    int textSize = 1;
    boolean up = false;
    
    boolean[] seq = {true,false,false,false};
    
	public MainMenu()
	{				        
        pressEnter = new Text("Press Enter",simpleBit,32);
        
        pressEnter.setPosition(positionText(0,100,pressEnter,true,true));
        
        menuTitle.setPosition(new Vector2f(0,-380));
        menuButton.setPosition(new Vector2f(0,200));
        
        for(int i = 0; i < 20; i++)
        {
        	circle[0].setPosition(new Vector2f(0,getWindowY() + 100));
        }
	}
	
	public void intro()
	{
		float speed = 0;
		
		if(seq[0])
		{			
			if(menuTitle.getPosition().y > 0)
			{
				speed = 15;
			}
			else
			{
				speed = -15;
			}
			
			menuTitle.move(new Vector2f(0,menuTitle.getPosition().y / speed));
			
			if(menuTitle.getPosition().y >= -1)
			{
				seq[0] = false;
				seq[1] = true;
				menuTitle.setPosition(new Vector2f(0,0));
			}
		}
		
		if(seq[1])
		{
			if(menuButton.getPosition().y > 0)
			{
				speed = -15;
			}
			else
			{
				speed = 15;
			}
			
			menuButton.move(new Vector2f(0,menuButton.getPosition().y / speed));
			
			if(menuButton.getPosition().y <= 1)
			{
				seq[1] = false;
				seq[2] = true;
				menuButton.setPosition(new Vector2f(0,0));
			}
		}
		
		if(seq[2])
		{
			for(int i = 0; i < 20; i++)
			{
				if(circle[i].getPosition().y < -30)
				{
					float randNum = (float) ((Math.random() * 20) + 5);
					circle[i] = new CircleShape(randNum);
					
					float randPos = (float) ((Math.random() * getWindowX()) + 1);
					circle[i].setPosition(new Vector2f(randPos,getWindowY() + 20));
					
					int randSpeed = (int) ((Math.random() * 10) + 2);
					circleSpeed[i] = randSpeed;
					
					switch((int) (Math.random() * 3) + 1)
					{
						case 1:
						{
							circle[i].setFillColor(new Color(255,169,118,255));
							break;
						}
						case 2:
						{
							circle[i].setFillColor(new Color(255,203,146,255));
							break;
						}
						case 3:
						{
							circle[i].setFillColor(new Color(255,219,174,255));
							break;
						}
					}
					
				}
				circle[i].move(new Vector2f(0,-circleSpeed[i]));
			}
		}
	}
	
	public void buttonClick(boolean hover)
	{
		IntRect buttonStart = new IntRect(608,551,64,23);
		IntRect buttonContinue = new IntRect(585,575,110,23);
		IntRect buttonOptions = new IntRect(562,599,156,23);
		IntRect buttonExit = new IntRect(539,623,202,23);
		Vector2i mousePosition = new Vector2i(getMouseX(),getMouseY());
		
		if(buttonStart.contains(mousePosition))
		{
			if(hover)
			{
				menuButton = menuButtons[1];
			}
			else
			{
				sfxClickSound.play();
				gameState = CtrlStates.State.MAP;
				mapLoader.loadMap("flatRoom1");
				collision.loadCollision("flatRoom1");
				player.setPosition(650,300);
				player.setName("Kobi");
				player.setStressLvl(5);
				player.setAgilityLvl(80);
				player.setDepressionLvl(0);
				player.setExhaustLvl(15);
				player.setSanityLvl(100);
				player.setIntelligenceLvl(67);
				player.setLevel(0);
				player.setCurrentXp(0);
				player.setPounds(10);
				roomState = CtrlStates.Room.FLATROOM1;
				musicManager.changeMusic();
			}
		}
		else if(buttonContinue.contains(mousePosition))
		{
			if(hover)
			{
				menuButton = menuButtons[2];
			}
			else
			{
				File save = new File("assets/player.sav");
				
				if(save.exists())
				{
					sfxClickSound.play();
					loadPlayerSave();
					musicManager.changeMusic();
				}				
			}
		}
		else if(buttonOptions.contains(mousePosition))
		{
			if(hover)
			{
				menuButton = menuButtons[3];
			}
			else
			{
				// Open options menu
				sfxClickSound.play();
			}
		}
		else if(buttonExit.contains(mousePosition))
		{
			if(hover)
			{
				menuButton = menuButtons[4];
			}
			else
			{
				sfxClickSound.play();
				getWindow().close();
			}
		}
		else
		{
			menuButton = menuButtons[0];
		}
	}
}
