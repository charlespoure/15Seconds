package FifteenSecondsGame;

import java.util.ArrayList;
import org.newdawn.slick.*;

public class Musiques 
{
	private ArrayList<Sound> sons;
	
	//0=fond	1=debut		2=phase1	3=phase2	4=phase3	5=fin	6=meche
	
	public Musiques() throws SlickException
	{
		this.sons = new ArrayList<Sound>();
		this.sons.add(new Sound("./images/15seconds/fond.wav"));
	}
	
	public void addSound(Sound s){this.sons.add(s);}
	
	public boolean playing(int i){return this.sons.get(i).playing();}
	
	public void check(double temps)
	{
		temps = (int)(temps);
		if(temps==0&&!sons.get(0).playing())
		{
			sons.get(0).play();
		}
		else if(temps==4 && !sons.get(1).playing()) 
		{
			sons.get(1).play();
		}
		else if(temps==8 && !sons.get(2).playing()) 
		{
			sons.get(2).loop();sons.get(6).loop();
		}
		else if(temps==14 && !sons.get(3).playing()) 
		{
			sons.get(2).stop();sons.get(3).loop();
		}
		else if(temps==20 && !sons.get(4).playing()) 
		{
			sons.get(3).stop();sons.get(4).loop();
		}
	}
	
	public void stop()
	{
		for(int i=0;i<sons.size();i++)
		{
			if(i!=5)sons.get(i).stop();
		}
		if(!sons.get(5).playing())sons.get(5).play();
	}
	public void finish()
	{
		for(int i=0;i<sons.size();i++)
		{
			sons.get(i).stop();
		}
	}
}
