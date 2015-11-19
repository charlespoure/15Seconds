package FifteenSecondsGame;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Affichage 
{
	private ArrayList<String> donnees;
	private ArrayList<Integer> pos;
	
	public Affichage()
	{
		donnees = new ArrayList<String>();
		pos = new ArrayList<Integer>();
	}
	
	public void add(String s)
	{
		this.donnees.add(s);
		this.pos.add(new Integer(100));
	}
	
	public void draw(GameContainer container, StateBasedGame game, Graphics g, int x, int y) throws SlickException
	{		
		g.setColor(Color.red);
		for(int i=0;i<pos.size();i++)
		{
			g.drawString(donnees.get(i),x-15,y+pos.get(i)-50);
			pos.set(i,pos.get(i)-1);
			if(pos.get(i)==0)
			{
				donnees.remove(i);
				pos.remove(i);
			}
		}
		g.setColor(Color.black);
	}
}
