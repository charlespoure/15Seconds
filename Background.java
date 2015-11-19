package FifteenSecondsGame;

import java.awt.Dimension;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Background 
{
	private Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	private int largeur 	= (int)dimension.getHeight();
	private int longueur 	= (int)dimension.getWidth();
	 
	private ArrayList<Image> images;
	private Case[][] map;
	private Objet[][] objets;
	private Image[] timer;
	private Image rope;
	private Image[] flame, fin;
	private Animation aflame, afin;
	
	public Background(Case[][] map, Objet[][] objets) throws SlickException
	{
		this.images = new ArrayList<Image>();
		this.map = map;
		this.objets = objets;
		this.rope = new Image("./images/15seconds/0/rope.png");
		timer = new Image[20];
		fin = new Image[2];
		fin[0] = new Image("./images/15seconds/0/fin/0.png");
		fin[1] = new Image("./images/15seconds/0/fin/1.png");
		flame = new Image[2];
		flame[0] = new Image("./images/15seconds/0/flame/0.png");
		flame[1] = new Image("./images/15seconds/0/flame/1.png");
		aflame = new Animation(flame,90);
		afin = new Animation(fin,120);
		
		for(int i=0;i<timer.length;i++){timer[i] = new Image("./images/15seconds/0/feu/"+i+".png");}
	}
	
	public void add(Image i)	{this.images.add(i);			}
	public Image get(int i)		{return this.images.get(i);		}
		
	public void render(GameContainer container, StateBasedGame game, Graphics g, double temps) throws SlickException 
	{
		if(temps<4)
		{
			images.get(0).draw(0,0);
		}
		else if(temps>3)
		{			
			images.get(1).draw(0,0);
			if(temps<8)timer[(int)(temps-4)].draw(82,65);
			else if(temps<24) timer[(int)(temps-4)].draw(107,100);
			else timer[19].draw(107,100);
			
			for(int i=0;i<20;i++)
			{
				 for(int j=0;j<40;j++)
				 {
					 map[i][j].draw(container, game, g, ((longueur-1600)/2)+40*j, ((largeur-800)/2)+40*i);
					 objets[i][j].draw(container, game, g, ((longueur-1600)/2)+40*j, ((largeur-800)/2)+40*i);
				 }
			}
			
			if(temps>8)
			{
				int cpt = 0;
				for(int i=0;i<84/15*(24-temps);i++)
				{
					cpt=16*i;
					rope.draw(319+cpt,129);
				}
				aflame.draw(319+cpt,99);
			}
			else
			{
				int cpt=0;
				for(int i=0;i<84;i++)
				{
					cpt=16*i;
					rope.draw(319+cpt,129);
				}
				aflame.draw(319+cpt,99);
			}
		}	
		
		/*if(temps<16) timer[temps%16].draw(longueur/2-67,129,Color.black	);
		 if(temps<16) timer[temps%16].draw(longueur/2-70,120				);*/
	}
	
	public void stop(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{
		afin.draw(0,0);
	}
}
