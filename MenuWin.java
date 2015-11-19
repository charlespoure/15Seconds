package FifteenSecondsGame;

import java.io.FileReader;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuWin extends BasicGameState 
{
	public int getID() {return 6;}
	
	private Image i;
	private Medaille medaille;
	private Image[] tabretry, tabquit;
	private int retry, quit;
	private Sound clic;
	
	private boolean DEBUT;
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException 
	{
		i = new Image("./images/15seconds/0/background/5.png");
		medaille = new Medaille();
		
		clic = new Sound("./images/15seconds/0/game/sons/4.wav");
		retry = 0; quit = 0;
		
		tabretry = new Image[3];
		tabquit = new Image[3];
		
		for(int i=0;i<3;i++)
		{
			tabretry[i] = new Image("./images/15seconds/0/boutons/2-"+i+".png");
			tabquit[i] = new Image("./images/15seconds/0/boutons/1-"+i+".png");
		}
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException 
	{
		/*if(!DEBUT)
			init2();*/
		i.draw(0,0);
		medaille.drawMenu(arg0,arg1,arg2);
		tabretry[retry].draw(1186,581);
		tabquit[quit].draw(1186,729);
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException 
	{
		Input c = arg0.getInput();
		if(retry == 2) retry = 0;
		
		if(c.getMouseX() > 1186 && c.getMouseX() < 1186+383)
		{
			if(c.getMouseY()>581 && c.getMouseY()<581+131)
			{
				retry=1;
				if(c.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					clic.play();
				if(c.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
					retry = 2;
			}
			else
			{
				retry = 0;
			}
			
			if(c.getMouseY()>729 && c.getMouseY()<729+131)
			{
				quit=1;
				if(c.isMousePressed(Input.MOUSE_LEFT_BUTTON))
					clic.play();
				if(c.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
				{
					quit = 2;
				}
			}
			else
			{
				quit = 0;
			}
		}
		
		if(retry == 2)
		{
			if(!c.isMousePressed(Input.MOUSE_LEFT_BUTTON))
				arg1.enterState(1);
		}
	}	
	
	/*public void init2() throws SlickException
	{
		DEBUT = true;
		medaille = new Medaille();
	}*/
}
