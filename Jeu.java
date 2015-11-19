package FifteenSecondsGame;

import java.awt.Dimension;
import java.io.*;
import java.util.*;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

 public class Jeu extends BasicGameState 
 { 
	 public int getID() {return 1;}
	 
	 private Case[][] map;
	 private Objet[][] objets;
	 
	 private Medaille medailles;
	 private Image imageFin;
	 private int level, indxfin, indyfin;
	 private double temps, start, temps2, start2;
	 private Background background;
	 private Musiques musiques;
	 private Joueur joueur;
	 private boolean end;
	 
	 private ArrayList<Objet> pieces, boutons;
	 
	 public void init(GameContainer container, StateBasedGame game) throws SlickException 
	 {
		 pieces = new ArrayList<Objet>();
		 boutons = new ArrayList<Objet>();
		 
		 imageFin = new Image("./images/15seconds/0/game/1/1.png");
		 medailles = new Medaille();
		 
		 end = false;
		 level = 0;
		 temps=0;start=0;temps2=0;start2=0;

		 map = new Case[20][40];
		 objets = new Objet[20][40];
		 
		 initMap();
		 
		 musiques = new Musiques();
		 background = new Background(map, objets);
		 joueur = new Joueur(map, objets);
		
		 for(int i=0;i<6;i++){musiques.addSound(new Sound("./images/15seconds/"+level+"/musique/"+i+".wav"));}
		 for(int i=0;i<2;i++){background.add(new Image("./images/15seconds/"+level+"/background/"+i+".png"));}
	 } 
	 
	 public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException 
	 {
		 if(!joueur.getFin())
		 {
			 if(joueur.getVie()<=0||(int)(temps)==24)
			 {
				 if(!end)
				 {
					 joueur.saut();
					 stop();		
					 start = java.lang.System.currentTimeMillis() ;
				 }
				
				end = true;
			 }
			 else
			 {
				 if(start==0)	start = java.lang.System.currentTimeMillis() ;
				 if(!joueur.getFin())
				 {
					 temps = (java.lang.System.currentTimeMillis()-start)/1000;
					 musiques.check(temps); 
				 }
			 }
			 
			 background.render(container, game, g, temps);
			 medailles.draw(container,game,g,temps);
			 joueur.draw(container, game, g, temps);
			 
			 for(int i=0;i<pieces.size();i++)
			 {
				 if(pieces.get(i).getN()!=0)
					 pieces.remove(i);
			 }
			 if(pieces.size()==0)
			 {
				 for(int i=0;i<20;i++)
				 {
					 for(int j=0;j<40;j++)
					 {
						 if(objets[i][j].getN()==1)
						 {
							 objets[i][j].etat();
							 indxfin = j*40;
							 indyfin = i*40;
						 }
					 }
				 }
			 }
			 boolean test = true;
			 for(int i=0;i<boutons.size();i++)
			 {
				 if(boutons.get(i).getEtat()==0)test = false;
			 }
			 if(test)
			 {
				 if(boutons.size()==3)
				 {
					 map[5][25].addsol(1);
					 map[5][26].addsol(1);
					 objets[16][1] = new Objet(4);
					 boutons.add(objets[16][1]);
				 }
				 else if (boutons.size()==4)
				 {
					 for(int i=2;i<17;i++)
					 {
						 objets[i][33] = new Objet(5);
						 map[i][33].addcontenu(5);
					 }
				 }			 
			 }
			 
			 if(end)
			 {
				 temps = (java.lang.System.currentTimeMillis()-start)/1000;
				 background.stop(container, game, g);
				 medailles.draw(container,game,g,temps);
				 joueur.draw(container, game, g, temps);
				 musiques.stop();
				 if((int)(temps)==2)
				{
					 init(container, game);
					 game.enterState(5);
				}
			 }
		 }
		 else
		 {
			if(!medailles.getFin())
			{
				start2 = java.lang.System.currentTimeMillis() ;
				medailles.end(temps);
			}
			temps2 = (java.lang.System.currentTimeMillis()-start2)/1000;
			musiques.finish();
			background.render(container, game, g, temps);
			joueur.draw(container, game, g, temps);
			imageFin.draw(indxfin+160,indyfin+140);
			joueur.fin(indxfin+170);
			if((temps2)>1.3)
			{
				 init(container, game);
				 game.getState(6).init(container,game);
				 game.enterState(6);
			}
		 }
	 } 
	 
	 public void initMap() throws SlickException
	 {
		 try
		 {
			 FileReader fr = new FileReader( "./images/15seconds/"+level+"/0.txt");
		   Scanner sc = new Scanner ( fr );

		   int i = 0, j = 0;
		   String[] temp;
		   int[] cases = new int[100];
		   int[] contenus = new int[100];
		   
		   while ( sc.hasNext() )
		   {
			   temp = sc.nextLine().split(":");
			   for(int x=0;x<temp.length;x++) 
			   {
				  cases[x] 		= Integer.parseInt(temp[x].substring(0, temp[x].indexOf(",")));
				  contenus[x] 	= Integer.parseInt(temp[x].substring(temp[x].indexOf(",")+1));
			   }
			   while(j<temp.length)
			   {
				   map[i][j] = new Case();
				   if(i>0) map[i][j].setHaut(map[i-1][j]);
				   if(j>0) map[i][j].setGauche(map[i][j-1]);
				   map[i][j].addsol(cases[j]);
				   objets[i][j] = new Objet(contenus[j]);
				   if(contenus[j]==0) pieces.add(objets[i][j]);
				   else if(contenus[j]==4) boutons.add(objets[i][j]);
				   j++;
			   }
			   i++;
			   j=0;			   
		   }
		   fr.close();
		   sc.close();
		 }
		 catch (Exception e){e.printStackTrace();} 	 
		 
		 for(int i=0;i<20;i++)
		 {
			 for(int j=0;j<40;j++)
			 {
				 if(objets[i][j].getN()!=-1)
					 map[i][j].addcontenu(objets[i][j].getN());
			 }
		 }
	 }
	 
	 public void stop() throws SlickException
	 {
		 for(int i=0;i<20;i++)
			{
				 for(int j=0;j<40;j++)
				 {
					 map[i][j].addsol(0);
					 objets[i][j] = new Objet(-1);
				 }
			}
	 }
	 
	 public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException 
	 { 
		 Input c = container.getInput();
		 
		 joueur.setAction(0);
		 
		 if(temps>8 && joueur.getVie()>0 && !joueur.getFin())
		 {
			 if((c.isKeyDown(Input.KEY_Q)  || Mouse.getX() < 20 	) 	&& joueur.getG()>0					)  	{joueur.gauche();/*j1.marcher();*/joueur.setAction(1);joueur.setCote(0);}
			 if((c.isKeyDown(Input.KEY_D)  || Mouse.getX() > 1900 	)	&& joueur.getD()<map[0].length-1	)	{joueur.droite();/*j1.marcher();*/joueur.setAction(1);joueur.setCote(1);}
			 if((c.isKeyDown(Input.KEY_S)  || Mouse.getY() < 20 	) 	/*&& y<largeur*50-780*/		) 	{joueur.descendre();}
			 if((c.isKeyDown(Input.KEY_SPACE)  || Mouse.getY() > 1060 	)	/*&& y>-295		*/		) 		{joueur.saut();}
		 }
	 }
 }
