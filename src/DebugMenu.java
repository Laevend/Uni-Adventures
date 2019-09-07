import org.jsfml.graphics.Text;

public class DebugMenu extends Game
{
	/**
	 * @param simpleBit - The font of the text used
	 * @param debugTitle - The version of the game displayed in the debug menu
	 * @param fpsText - The number of frames per second (FPS) the game is running at displayed in the debug menu
	 * @param updatesText - The number of of updates the game has done in the current running session
	 */
	Text debugTitle;
    Text fpsText;
    Text updatesText;
    
    Text gameStateText;
    Text debugStateText;
    Text shellStateText;
    
    Text mousePos;
    Text cameraPos;
	
	public DebugMenu()
	{		
		debugTitle = new Text("Uni Adventures 1.0",simpleBit,16);
		fpsText = new Text("FPS: 60",simpleBit,16);
		updatesText = new Text("Total Updates: 0",simpleBit,16);
		
		gameStateText = new Text("Game State: " + gameState,simpleBit,16);
		debugStateText = new Text("Debug State: " + debugState,simpleBit,16);
		shellStateText = new Text("Shell State: " + shellState,simpleBit,16);
		
		mousePos = new Text("Mouse - X: " + getMouseX() + " Y: " + getMouseY(),simpleBit,16);
		cameraPos = new Text("Camera - X: " + 0 + " Y: " + 0,simpleBit,16);
		
		//debugTitle.setPosition(camera.getViewport().top + 5,camera.getViewport().top);
		debugTitle.setPosition(5,0);
        fpsText.setPosition(5,16);
        updatesText.setPosition(5,32);
        
        gameStateText.setPosition(5,48);
        debugStateText.setPosition(5,64);
        shellStateText.setPosition(5,80);
        
        mousePos.setPosition(5,96);
        cameraPos.setPosition(5,112);
	}
	
	public void setFPSText(int fps)
	{
		fpsText.setString("FPS: " + Integer.toString(fps));
	}
	
	public void setUpdateText(long update)
	{
		updatesText.setString("Total Updates: " + Long.toString(update));
		gameStateText.setString("Game State: " + gameState);
		debugStateText.setString("Debug State: " + debugState);
		shellStateText.setString("Shell State: " + shellState);
		mousePos.setString("Mouse - X: " + getMouseX() + " Y: " + getMouseY());
		cameraPos.setString("Camera - X: " + (int) getCameraX() + " Y: " + (int) getCameraY());
	}
}
