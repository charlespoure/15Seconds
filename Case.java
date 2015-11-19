package FifteenSecondsGame;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Case 
{
	private Image imgt;
	private int type;
	private int passage;
	
	public static int nb = 0;
	private int ind;
	
	private Case haut, droite, gauche, bas;
	
	private int x;
	
	public Case() throws SlickException
	{
		this.type = 0;
		
		this.gauche = null;
		this.droite = null;
		this.bas = null;
		this.haut = null;
		
		this.imgt = new Image("./images/15seconds/0/carte/0/0.png");
		
		this.passage = 1; x=1;
	
		ind = nb++;
	}

	public void addcontenu(int i) throws SlickException
	{
		if(i==1)
		{
			this.passage = 0;
			this.droite.setPassage(0);
			this.droite.getBas().setPassage(0);
			this.bas.setPassage(0);
		}
		else if(i==3)
		{
			this.passage = 0;
		}
		else if(i==5)
		{
			this.passage = 2;
		}
	}
	
	public Case getBas(){return this.bas;}
	public void setPassage(int i){this.passage = i;}
	
	public int getPassage(){return this.passage;}
	
	public void addsol(int i) throws SlickException
	{
		/*File r = new File("./images/sols/"+i);
		int rdm = (int)(Math.random()*r.listFiles().length-1);*/
		this.type = i;
		//this.numero = rdm;
		if(this.haut!=null)
		{
			if(this.haut.getType()!=0)this.imgt = new Image("./images/15seconds/0/carte/"+i+"/"+1/*rdm*/+".png");
			else this.imgt = new Image("./images/15seconds/0/carte/"+i+"/"+0/*rdm*/+".png");
		}
		else
		{
			this.imgt = new Image("./images/15seconds/0/carte/"+i+"/"+0/*rdm*/+".png");
		}
		
		this.passage = 0;
		
		if(i==0) delete();
	}
	
	public void delete() throws SlickException
	{
		this.type = 0;

		this.imgt = new Image("./images/15seconds/0/carte/0/0.png");
		
		this.passage = 1;
	}
	
	public void draw(GameContainer container, StateBasedGame game, Graphics g, int x, int y) throws SlickException
	{
		this.imgt.draw(x,y);
	}
	
	public int getType(){return this.type;}
	
	public void setHaut(Case c)		{this.haut = c;c.setBas(this);}
	public void setBas(Case c)		{this.bas = c;}
	public void setDroite(Case c)	{this.droite = c;}
	public void setGauche(Case c)	{this.gauche = c;c.setDroite(this);}
}
