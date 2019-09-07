
public class CtrlStates
{
	public enum State
	{
		MENU,
		MAP
	}
	
	public enum Menu
	{
		NONE,
		INVENTORY,
		ESC,
		TEXTBOX,
		STATS,
		BATTLESYSTEM
	}
	
	public enum BattleSystemUI
	{
		PLAYER1ANDNPC1,
		PLAYER2ANDNPC2,
		PLAYER1ANDNPC2,
		PLAYER2ANDNPC1
	}
	
	public enum BattleSystemText
	{
		ENABLED,
		DISABLED
	}
	
	public enum Debug
	{
		ENABLED,
		DISABLED
	}
	
	public enum Shell
	{
		ENABLED,
		DISABLED
	}
	
	public enum Room
	{
		NULL,
		FLATROOM1,
		FLATROOM2,
		FLATROOM3,
		FLATROOM4,
		FLATHALL,
		FLATROOMKITCHEN,
		FLATSMALLHALL,
		FLATSTAIRS,
		FLATENTRANCE,
		OUTSIDE,
		B74,
		B76,
		LABHALL,
		LABTOLEC,
		LECHALL,
		LECROOM,
		LECROOMNORTH,
		LIBRARYF0,
		PATHTOLAB
	}
	
	public enum MapEditor
	{
		ENABLEDGRID,
		ENABLED,
		DISABLED
	}
	
	public enum CollideMap
	{
		ENABLED,
		DISABLED
	}
	
	public enum MusicArea
	{
		MENU,
		FLAT,
		NULL,
		OUTSIDE,
		LABSANDLECS,
		BATTLE,
		LIB
	}
}
