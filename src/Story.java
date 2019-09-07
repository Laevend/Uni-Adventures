import java.util.ArrayList;

public class Story extends Game
{
	public ArrayList<NPC> npcs = new ArrayList<NPC>();
	
	public void loadRoomAssets(CtrlStates.Room room)
	{
		//npcName, stress, agility, depression, exhaust, sanity, intelligence, x, y, ID, fight, attack id
		switch(room)
		{
			case NULL:
			{
				npcs.clear();
				npcs.add(npc = new NPC("Laeven",0,100,0,0,100,100,600,300,0,false,1));
				break;
			}	
			case FLATROOM1:
			{
				npcs.clear();
				break;
			}
			case FLATROOM2:
			{
				npcs.clear();
				npcs.add(npc = new NPC("Vlad",12,5,0,0,100,70,600,300,1,false,1));
				break;
			}
			case FLATROOM3:
			{
				npcs.clear();
				npcs.add(npc = new NPC("James",12,5,40,40,100,70,500,300,2,false,1));
				break;
			}
			case FLATROOM4:
			{
				npcs.clear();
				npcs.add(npc = new NPC("Jon",12,5,0,0,100,70,700,300,3,false,1));
				break;
			}
			case FLATHALL:
			{
				npcs.clear();
				npcs.add(npc = new NPC("Jack",12,5,40,40,100,70,520,740,4,true,1));
				npcs.add(npc = new NPC("Harry",20,30,40,5,30,90,555,307,5,true,3));
				break;
			}
			case FLATROOMKITCHEN:
			{
				npcs.clear();
				break;
			}
			case FLATSMALLHALL:
			{
				npcs.clear();
				break;
			}
			case FLATSTAIRS:
			{
				npcs.clear();
				break;
			}
			case FLATENTRANCE:
			{
				npcs.clear();
				break;
			}
			case OUTSIDE:
			{
				npcs.clear();
				break;
			}
			case B74:
			{
				npcs.clear();
				break;
			}
			case B76:
			{
				npcs.clear();
				break;
			}
			case LABHALL:
			{
				npcs.clear();
				break;
			}
			case LABTOLEC:
			{
				npcs.clear();
				break;
			}
			case LECHALL:
			{
				npcs.clear();
				break;
			}
			case LECROOM:
			{
				npcs.clear();
				break;
			}
			case LECROOMNORTH:
			{
				npcs.clear();
				break;
			}
			case LIBRARYF0:
			{
				npcs.clear();
				break;
			}
			default:
			{
				System.out.println("Invalid Room Asset State");
				break;
			}
		}
	}
	
	// Check the instance of the NPC still exists
	public boolean checkNPC(int ID)
	{
		if(npc != null)
		{
			for(int i = 0; i < npcs.size(); i++)
			{
				if(npcs.get(i).getID() == ID)
				{
					return true;
				}
			}
			return false;
		}
		else
		{
			return false;
		}
	}
}
