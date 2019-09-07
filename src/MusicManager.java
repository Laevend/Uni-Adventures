import org.jsfml.audio.Music;

public class MusicManager extends Game
{
	private static CtrlStates.MusicArea musicArea = CtrlStates.MusicArea.MENU;
	private static Music musicStream;
	
	public MusicManager()
	{		
		musicStream =  new Music();
		musicStream = getMusicFromFile("assets/music/menu.ogg");
		musicStream.setLoop(true);
		musicStream.play();
	}
	
	public void changeMusic()
	{
		switch(gameState)
		{
			case MENU:
			{
				if(!musicArea.equals(CtrlStates.MusicArea.MENU))
				{
					musicStream.stop();
					musicStream = getMusicFromFile("assets/music/menu.ogg");
					musicStream.setLoop(true);
					musicStream.play();
					
					musicArea = CtrlStates.MusicArea.MENU;
				}
				break;
			}
			case MAP:
			{
				switch(menuState)
				{
					case BATTLESYSTEM:
					{
						battleMusic();
						break;
					}
					default:
					{
						switch(roomState)
						{
							case NULL:
							{
								if(!musicArea.equals(CtrlStates.MusicArea.NULL))
								{
									musicStream.stop();
									musicStream = getMusicFromFile("assets/music/null.ogg");
									musicStream.setLoop(true);
									musicStream.play();
									
									musicArea = CtrlStates.MusicArea.NULL;
								}
								break;
							}
							case FLATROOM1:
							{
							}
							case FLATROOM2:
							{
							}
							case FLATROOM3:
							{
							}
							case FLATROOM4:
							{
							}
							case FLATHALL:
							{
							}
							case FLATROOMKITCHEN:
							{
							}
							case FLATSMALLHALL:
							{
							}
							case FLATSTAIRS:
							{
							}
							case FLATENTRANCE:
							{
								flatRoomMusic();
								break;
							}
							case OUTSIDE:
							{
							}
							case LABTOLEC:
							{
							}
							case PATHTOLAB:
							{
								outsideMusic();
								break;
							}
							case B74:
							{
							}
							case B76:
							{
							}
							case LABHALL:
							{
							}
							case LECHALL:
							{
							}
							case LECROOM:
							{
							}
							case LECROOMNORTH:
							{
								labAndLecMusic();
								break;
							}
							case LIBRARYF0:
							{
								
								break;
							}
						}
						break;
					}
				}
				
				
				break;
			}
		}
	}
	
	private void flatRoomMusic()
	{
		if(!musicArea.equals(CtrlStates.MusicArea.FLAT))
		{
			musicStream.stop();
			musicStream = getMusicFromFile("assets/music/flat.ogg");
			musicStream.setLoop(true);
			musicStream.play();
			
			musicArea = CtrlStates.MusicArea.FLAT;
		}
	}
	
	private void outsideMusic()
	{
		if(!musicArea.equals(CtrlStates.MusicArea.OUTSIDE))
		{
			musicStream.stop();
			musicStream = getMusicFromFile("assets/music/outside.ogg");
			musicStream.setLoop(true);
			musicStream.play();
			
			musicArea = CtrlStates.MusicArea.OUTSIDE;
		}
	}
	
	private void labAndLecMusic()
	{
		if(!musicArea.equals(CtrlStates.MusicArea.LABSANDLECS))
		{
			musicStream.stop();
			musicStream = getMusicFromFile("assets/music/labs.ogg");
			musicStream.setLoop(true);
			musicStream.play();
			
			musicArea = CtrlStates.MusicArea.LABSANDLECS;
		}
	}
	
	private void battleMusic()
	{
		if(!musicArea.equals(CtrlStates.MusicArea.BATTLE))
		{
			musicStream.stop();
			int soundVar = (int) ((Math.random() * 2) + 1);
			
			if(soundVar == 2)
			{
				musicStream = getMusicFromFile("assets/music/battle2.ogg");
			}
			else
			{
				musicStream = getMusicFromFile("assets/music/battle.ogg");
			}
			
			musicStream.setLoop(true);
			musicStream.play();
			
			musicArea = CtrlStates.MusicArea.BATTLE;
		}
	}
}
