package FifteenSecondsGame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Lanceur extends StateBasedGame 
{ 
	private GameState jeu;
	private AppGameContainer container;
	
	public Lanceur() {super("Eragon Tsang");}
	 
	public void initStatesList(GameContainer container) throws SlickException 
	{ 
		if (container instanceof AppGameContainer) {this.container = (AppGameContainer) container;} 
		
		container.setShowFPS(false);
		addState(new Jeu());
		addState(new MenuFin());
		addState(new MenuWin());
	} 
	
	public static void main(String[] args) 
	{ 
		try
		{ 
			AppGameContainer container = new AppGameContainer(new Lanceur()); 
			container.setDisplayMode(1920, 1080, false);
			container.setTargetFrameRate(60);
			container.start(); 
		} 
		catch (SlickException e) {e.printStackTrace();}
	} 
}
