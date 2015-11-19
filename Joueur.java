package FifteenSecondsGame;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Joueur 
{
	private int x, y, nAction, cote, chute, saut, bordd, bordg, debx, deby;
	private ArrayList<Image[]> droite, gauche;
	private Animation[][] action;
	private Case[][] map;
	private Objet[][] objets;
	private Image tampon;
	private Affichage affichage;
	private int vie;
	private Sound aie, course, sau;
	private boolean descendre, fin;
	
	public Joueur(Case[][] map, Objet[][] objets) throws SlickException
	{		
		aie = new Sound("./images/15seconds/0/personnage/0.wav");
		course = new Sound("./images/15seconds/0/personnage/1.wav");
		sau = new Sound("./images/15seconds/0/personnage/2.wav");
		affichage = new Affichage();
		vie = 100;
		initPerso();
		this.map = map;
		this.objets = objets;
		int nbAnim = 2;
		descendre = false;fin = false;
		droite = new ArrayList<Image[]>();
		gauche = new ArrayList<Image[]>();
		tampon = new Image("./images/15seconds/0/Personnage/Droite/0/0.png");
		this.action = new Animation[2][nbAnim];

		for(int i=0;i<nbAnim;i++)
		{
			File r = new File("./images/15seconds/0/Personnage/Droite/"+i);
			int tmp = r.listFiles().length;
			Image[] temp = new Image[tmp];	Image[] temp2 = new Image[tmp];
			
			for(int j=0;j<temp.length;j++){
				temp[j] 	= new Image("./images/15seconds/0/personnage/droite/"+i+"/"+j+".png");
				temp2[j] 	= new Image("./images/15seconds/0/personnage/gauche/"+i+"/"+j+".png");}
			
			droite.add(temp);	gauche.add(temp2);
		}
		action[0][0] = new Animation(gauche.get(0),150);	action[1][0] = new Animation(droite.get(0),150);
		action[0][1] = new Animation(gauche.get(1),50);		action[1][1] = new Animation(droite.get(1),50);
		
		this.x = 220;this.y = 720;this.saut=0;
		this.debx = 160; this.deby=140;
		this.nAction = 0;this.cote = 1;
	}
	
	public boolean getDescendre(){return this.descendre;}
	
	public void descendre() throws SlickException
	{
		descendre=true;
		if(conditions())
		{
			if (objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordg)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordg)/40].action2(this);
			}
			else if (objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordd)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordd)/40].action2(this);
			}
		}
		descendre = false;
	}
	
	public int getX(){return this.x;}
	
	public void fin(int x)
	{
		if(!fin)
		{
			fin = true;
		}
		this.x=x;
		y+=1;
	}
	
	public int getY(){return this.y;}
	
	public boolean getFin(){return this.fin;}
	
	public void draw(GameContainer container, StateBasedGame game, Graphics g, double temps) throws SlickException
	{		
		checkbas2();
		if(saut==1)
			haut(19);
		else if(saut==2)
			haut(29);
		checkbas();
		check();
		bas();
		
		
		if(temps>4)
		{
			g.setColor(Color.red);
			g.fillRect(x,y-20,60,5);
			g.setColor(Color.green);
			g.fillRect(x,y-20,(vie*60/100),5);
			g.setColor(Color.black);
			g.drawRect(x,y-20,60,5);
			g.drawAnimation(action[cote][nAction],x,y);
		}
		
		affichage.draw(container, game, g, x, y);
	}
	
	public void checkbas2() throws SlickException
	{
		if(conditions())
		{
			if(map[(y+79+1-deby)/40][(x+bordg-debx)/40].getPassage()==0 || map[(y+79+1-deby)/40][(x+bordd-debx)/40].getPassage()==0)
			{
			check2();
			}
		}
	}
	
	public void checkbas() throws SlickException
	{
		if(conditions())
		{
			if(map[(y+79+1-deby)/40][(x+bordg-debx)/40].getPassage()==0 || map[(y+79+1-deby)/40][(x+bordd-debx)/40].getPassage()==0)
			{
				chute = 0; saut = 0; check2();
			}
			else if(map[(y+79+1-deby)/40][(x+bordg-debx)/40].getPassage()==2 || map[(y+79+1-deby)/40][(x+bordd-debx)/40].getPassage()==2)
			{
				chute = 0; saut = 0;
			}
			else
			{
				course.stop();
			}
		}
			
	}
	
	public void degats(int i)		{this.vie-=i;affichage.add(""+i);aie.play();}
	public void setCote(int i)		{this.cote = i;		}
	public void setAction(int i)	{this.nAction = i;	}
	
	public void check() throws SlickException
	{
		if(conditions())
		{
			if(objets[(y+5-deby)/40][(x-debx+bordg)/40].getN()!=-1)			
			{
				objets[(y+5-deby)/40][(x-debx+bordg)/40].action(this);
			}
			else if (objets[(y+tampon.getHeight()-5-deby)/40][(x-debx+bordg)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40][(x-debx+bordg)/40].action(this);
			}
			else if (objets[(y+tampon.getHeight()-5-deby)/40][(x-debx+bordd)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40][(x-debx+bordd)/40].action(this);
			}
			else if(objets[(y+5-deby)/40][(x-debx+bordd)/40].getN()!=-1)			
			{
				objets[(y+5-deby)/40][(x-debx+bordd)/40].action(this);
			}
		}
	}
	
	public void check2() throws SlickException
	{
		if(conditions())
		{
			if(objets[(y+5-deby)/40+1][(x-debx+bordg)/40].getN()!=-1)			
			{
				objets[(y+5-deby)/40+1][(x-debx+bordg)/40].action2(this);
			}
			else if (objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordg)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordg)/40].action2(this);
			}
			else if (objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordd)/40].getN()!=-1)
			{
				objets[(y+tampon.getHeight()-5-deby)/40+1][(x-debx+bordd)/40].action2(this);
			}
			else if(objets[(y+5-deby)/40+1][(x-debx+bordd)/40].getN()!=-1)			
			{
				objets[(y+5-deby)/40+1][(x-debx+bordd)/40].action2(this);
			}
		}
	}
	
	public void droite()	
	{
		if(conditions())
		{
			if(map[(y+6-deby)/40][(x+bordd+10-debx)/40].getPassage()>0 && map[(y+35-deby)/40][(x+bordd+10-debx)/40].getPassage()>0
					&& map[(y+65-deby)/40][(x+bordd+10-debx)/40].getPassage()>0 && map[(y+78-deby)/40][(x+bordd+10-debx)/40].getPassage()>0)
				x+=10;
			if(!course.playing())course.play();
		}
		else
			x+=10;
	}
	
	public void gauche()
	{
		if(conditions())
		{
			if(map[(y+6-deby)/40][(x+bordg-10-debx)/40].getPassage()>0 && map[(y+35-deby)/40][(x+bordg-10-debx)/40].getPassage()>0
					&& map[(y+65-deby)/40][(x+bordg-10-debx)/40].getPassage()>0 && map[(y+78-deby)/40][(x+bordg-10-debx)/40].getPassage()>0)
				x-=10;
			if(!course.playing())course.play();
		}
		else
			x-=10;
	}
	
	public void haut(int i) throws SlickException		
	{	
		if(conditions())
		{
			if(map[(y+5-deby-i+chute)/40][(x+bordg-debx)/40].getPassage()==1 && map[(y+5-deby-i+chute)/40][(x+bordd-debx)/40].getPassage()==1) 
				y -= i;
			else if(map[(y+5-deby)/40][(x+bordg-debx)/40].getPassage()==2 || map[(y+5-deby)/40][(x+bordd-debx)/40].getPassage()==2) 
				y -= 10;
			else
				chute+=6;
		}
		else y-=i;	
	}
	
	public void bas() throws SlickException
	{
		if(conditions())
		{
			if(map[(y+79-deby)/40][(x+bordg-debx)/40].getPassage()==1 && map[(y+79-deby)/40][(x+bordd-debx)/40].getPassage()==1) y += chute++;
			for(int i=0;i<chute;i++)
			{
				if(map[(y+79+i-deby)/40][(x+bordg-debx)/40].getPassage()==0 || map[(y+79+i-deby)/40][(x+bordd-debx)/40].getPassage()==0)
				{
					y = y+i; chute = 0; saut = 0;
				}
			}
		}
		else
			y += chute++;
	}
	
	public void saut(){if(saut==0&&chute==0){saut=1;if(map[(y-deby-5)/40][(x+bordg-debx)/40].getPassage()==1 && map[(y-deby-5)/40][(x+bordd-debx)/40].getPassage()==1) sau.play();}}
	public void saut2(){saut=2;}
	
	public int getVie(){return (this.vie);}
	public int getG(){return (this.x+bordg-debx)/40;}
	public int getD(){return (this.x+bordd-debx)/40;}
	
	public boolean conditions()
	{
		return (y-deby-30)/40>-1 && (x-debx)/40>-1 && (y-deby+chute+80)/40<map.length-2 && (x-debx)/40+2 <map[0].length;
	}
	
	public void initPerso()
	{
		bordg = 20;
		bordd = 50;
	}
}
