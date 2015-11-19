package FifteenSecondsGame;

import java.io.File;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Objet 
{
	private int nb;
	private int type;
	private int etat;
	
	private Sound son;
	private Image image1, image2;
	private Animation animation;
	
	public Objet(int nb) throws SlickException
	{
		this.nb = nb;
		this.etat = 0;
		init();
	}
	
	public void init() throws SlickException
	{
		File r = new File("./images/15seconds/0/game/"+nb);
		int nbFile = r.listFiles().length;
		if(nbFile>2)
		{
			Image[] temp = new Image[nbFile];
			for(int i=0;i<nbFile;i++)
				temp[i] = new Image("./images/15seconds/0/game/"+nb+"/"+i+".png");
			animation = new Animation(temp,100);
			type=1;
		}
		else if(nbFile == 2)
		{
			image1 = new Image("./images/15seconds/0/game/"+nb+"/0.png");
			image2 = new Image("./images/15seconds/0/game/"+nb+"/1.png");
			type = 2;
		}
		else
		{
			image1 = new Image("./images/15seconds/0/game/"+nb+"/0.png");
			type = 3;
		}

		if(nb>-1&&nb<5)son = new Sound("./images/15seconds/0/game/sons/"+nb+".wav");
	}
	
	public int getN(){return this.nb;}
	
	public void draw(GameContainer container, StateBasedGame game, Graphics g, int x, int y) throws SlickException
	{
		if(type==1)
			animation.draw(x,y);
		else if(etat==0)
			image1.draw(x,y);
		else if(etat==1)
			image2.draw(x,y);
	}
	
	public void action(Joueur j) throws SlickException
	{
		if(nb==0&&!son.playing())
		{
			son.play();
			this.nb=-1;this.init();
		}
		else if(nb==2 && j.getVie()>0 &&!son.playing()) 
		{
			son.play();
			j.degats(25);
		}	
		else if(nb==4)
		{
			if(!son.playing() && etat==0)son.play();
			etat();
		}
	}
	
	public void action2(Joueur j) throws SlickException
	{
		if(nb==1&&j.getDescendre()&&this.etat==1)
		{
			j.fin(j.getX());
			son.play();
		}
		else if(nb==3 )
		{
			if(!son.playing())son.play();
			j.saut2();
		}
		
	}
	
	public int getEtat(){return this.etat;}
	public void etat(){this.etat = 1;}
}
