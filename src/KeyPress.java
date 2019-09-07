import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class KeyPress extends Game
{	
	boolean shellKeyEnter = true;
	boolean shellKeyTab = true;
	
	boolean directRot = false;
	boolean directShake = false;
	int cameraRot = 0;
	int cameraShake = 0;
	
	public KeyPress()
	{
		
	}
	
	public void keyListen()
	{
		int coolDownTimer = getCoolDown();
		
		// Only able to open up the shell when your are in the game
		if(gameState == CtrlStates.State.MAP)
		{
			if(Keyboard.isKeyPressed(Key.SLASH))
			{
				if(coolDownTimer == 0)
				{
					shellState = CtrlStates.Shell.ENABLED;
					setCoolDown(10); // 1/6 of a second
				}
			}
				
			if(Keyboard.isKeyPressed(Key.ESCAPE))
			{
				if(coolDownTimer == 0)
				{
					shellState = CtrlStates.Shell.DISABLED;
					setCoolDown(10); // 1/6 of a second
				}
			}
		}	
		
		// All shell events
		if(shellState == CtrlStates.Shell.ENABLED)
		{
			if(Keyboard.isKeyPressed(Key.BACKSPACE) && shell.tempCmd.length() != 0 && coolDownTimer == 0)
			{
				shell.tempCmd = shell.tempCmd.substring(0,shell.tempCmd.length() - 1);
				shell.command.setString("> " + shell.tempCmd);
				setCoolDown(4);
			}
			
			if(Keyboard.isKeyPressed(Key.RETURN) && coolDownTimer == 0 && shellKeyEnter)
			{
				shell.runCommand();
				shell.tempCmd = "";
				shell.command.setString("> " + shell.tempCmd);
				shellState = CtrlStates.Shell.DISABLED;
				setCoolDown(4);
				shellKeyEnter = false;
			}
			else
			{
				shellKeyEnter = true;
			}
			
			if(Keyboard.isKeyPressed(Key.TAB) && coolDownTimer == 0 && shellKeyTab)
			{
				shell.autoCompleteCommand();
				shell.command.setString("> " + shell.tempCmd);
				setCoolDown(4);
				shellKeyTab = false;
			}
			else
			{
				shellKeyTab = true;
			}
			
			if(Keyboard.isKeyPressed(Key.UP) && coolDownTimer == 0)
			{
				shell.getPrevCommandUp();
				shell.command.setString("> " + shell.tempCmd);
				setCoolDown(12);
			}
			
			if(Keyboard.isKeyPressed(Key.DOWN) && coolDownTimer == 0)
			{
				shell.getPrevCommandDown();
				shell.command.setString("> " + shell.tempCmd);
				setCoolDown(12);
			}
		}
		
		// When shell is not in use, allow other events to take control
		if(shellState == CtrlStates.Shell.DISABLED)
		{						
			if(gameState == CtrlStates.State.MAP)
			{
				collision.collide();
				
				if(Keyboard.isKeyPressed(Key.I) && coolDownTimer == 0)
				{
					if(menuState == CtrlStates.Menu.NONE)
					{
						menuState = CtrlStates.Menu.INVENTORY;
					}
					else if(menuState == CtrlStates.Menu.INVENTORY)
					{
						menuState = CtrlStates.Menu.NONE;
					}
					setCoolDown(12);
				}
				
				if(Keyboard.isKeyPressed(Key.LSHIFT))
				{
					player.walkSpeedMultiplier = 2;
				}
				else
				{
					player.walkSpeedMultiplier = 1;
				}
				
				if(Keyboard.isKeyPressed(Key.F))
				{
					isFocused = false;
				}
				
				if(collideState == CtrlStates.CollideMap.ENABLED)
				{
					if(Keyboard.isKeyPressed(Key.NUM1))
					{
						collision.setBoxType(1);
					}
					else if(Keyboard.isKeyPressed(Key.NUM2))
					{
						collision.setBoxType(2);
					}
					else if(Keyboard.isKeyPressed(Key.NUM3))
					{
						collision.setBoxType(3);
					}
					else if(Keyboard.isKeyPressed(Key.NUM4))
					{
						collision.setBoxType(4);
					}
					else if(Keyboard.isKeyPressed(Key.NUM5))
					{
						collision.setBoxType(5);
					}
				}
				
				if(menuState != CtrlStates.Menu.BATTLESYSTEM)
				{
				
					if(menuState == CtrlStates.Menu.NONE)
					{
						if(Keyboard.isKeyPressed(Key.W))
						{
							int playY = player.getPlayerY();
							player.setPlayerY(playY -= (player.walkSpeedMultiplier * 2));
							player.playerSprite.setPosition(player.getPlayerX(),player.getPlayerY());
							player.walkUp();
						}
						
						if(Keyboard.isKeyPressed(Key.A))
						{
							int playX = player.getPlayerX();
							player.setPlayerX(playX -= (player.walkSpeedMultiplier * 2));
							player.playerSprite.setPosition(player.getPlayerX(),player.getPlayerY());
							player.walkLeft();
						}
						
						if(Keyboard.isKeyPressed(Key.S))
						{
							int playY = player.getPlayerY();
							player.setPlayerY(playY += (player.walkSpeedMultiplier * 2));
							player.playerSprite.setPosition(player.getPlayerX(),player.getPlayerY());
							player.walkDown();
						}
						
						if(Keyboard.isKeyPressed(Key.D))
						{
							int playX = player.getPlayerX();
							player.setPlayerX(playX += (player.walkSpeedMultiplier * 2));
							player.playerSprite.setPosition(player.getPlayerX(),player.getPlayerY());
							player.walkRight();
						}
					}
					
					if(Keyboard.isKeyPressed(Key.ESCAPE) && coolDownTimer == 0)
					{
						if(menuState == CtrlStates.Menu.ESC)
						{
							menuState = CtrlStates.Menu.NONE;
							setCoolDown(12);
						}
						else if(menuState == CtrlStates.Menu.NONE)
						{
							menuState = CtrlStates.Menu.ESC;
							setCoolDown(12);
						}
						else
						{
							menuState = CtrlStates.Menu.NONE;
							setCoolDown(12);
						}
					}
					
					if(Keyboard.isKeyPressed(Key.E) && coolDownTimer == 0)
					{
						if(menuState == CtrlStates.Menu.NONE)
						{
							for(int i = 0; i < story.npcs.size(); i++)
							{
								if(player.playerIHB.intersects(story.npcs.get(i).npcIHB))
								{
									menuState = CtrlStates.Menu.TEXTBOX;
									story.npcs.get(i).setNPCTalking(true);
									hud.loadText(i);
									setCoolDown(12);
									
									if(story.npcs.get(i).getName() == "Harry")
									{
										int soundVar = (int) ((Math.random() * 5) + 1);
										hud.setNpcTextSound(soundVar);
										hud.playNpcTextSound();	
									}
								}
							}
						}
					}
				}
				
				if(Keyboard.isKeyPressed(Key.X) && coolDownTimer == 0)
				{
					player.setCurrentXp(player.getCurrentXp() + 1000);
					updateLevel();
				}
				
				if(Keyboard.isKeyPressed(Key.SPACE) && coolDownTimer == 0)
				{
					if(menuState == CtrlStates.Menu.TEXTBOX || battleTextState == CtrlStates.BattleSystemText.ENABLED)
					{
						hud.manageText();
						setCoolDown(12);
					}
				}
			}
		}
	}
}
