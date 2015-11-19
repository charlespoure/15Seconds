package FifteenSecondsGame;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Medaille 
{
	private double auteur, or, argent, bronze, score, lastscore;
	private Image[] medailles;
	private int med;
	
	private Animation anim;
	private Image[] nim;
	
	private boolean fin;
	
	public Medaille() throws SlickException
	{
		init();
		
		fin = false;
		this.med = 0;
		
		medailles = new Image[5];
		nim = new Image[3];
		
		for(int i=8;i<11;i++)
		{
			nim[i-8] = new Image("./images/15seconds/0/medailles/"+i+".png");
		}
		
		for(int i=0;i<5;i++)
		{
			medailles[i] = new Image("./images/15seconds/0/medailles/"+i+".png");
		}
		
		anim = new Animation(nim,100);
	}
	
	public void draw(GameContainer container, StateBasedGame game, Graphics g, double temps) throws SlickException
	{			
		if(temps-8>bronze)
			med=4;
		else if(temps-8>argent)
			med=3;
		else if(temps-8>or)
			med=2;
		else if(temps-8>auteur)
			med=1;
		
		//if(temps>4)medailles[med].draw(78,862);
		if(temps>4 && med !=4)
		{
			anim.draw(880,180);
			medailles[med].draw(880,180);
		}
	}
	
	public void drawMenu(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
	{	
		if(score<auteur)
			med=0;
		else if(score<or)
			med=1;
		else if(score<argent)
			med=2;
		else if(score<bronze)
			med=3;
		else
			med=4;
		
		medailles[med].draw(1280,210);
		
		String temp = String.format("%.3f",(score-lastscore));
		String gauche = (temp.substring(0, temp.indexOf(",")));
		String droite = (temp.substring(temp.indexOf(",")+1));
		Double d = Double.parseDouble(gauche+"."+droite);
	     
		Color c;
		
		if(lastscore>0)
	    	 c = Color.green;
	     else
	    	 c = Color.red;
		
		//g.setColor(Color.gray);
		//g.fillRect(1025, 390, 710, 170);
		//g.setLineWidth(3);
		//g.setColor(c);
		//g.drawRect(1025, 390, 710, 170);
		//g.setLineWidth(1);

     	drawS(410,"Latest"	,d			,Color.white);
    	drawS(460,"Progress"	,lastscore	,c			);
     	drawS(510,"Best"		,score		,Color.white);		
		
	}
	
	public void drawS(int y, String s, Double d, Color c)
	{
		Font awtFont = new Font("Arial", Font.BOLD, 35);
		TrueTypeFont font = new TrueTypeFont(awtFont, false);
		
		font.drawString(1190,y,s	,					c		);
		font.drawString(1365,y,":"	,					c		);
		font.drawString(1385,y,String.format("%.3f",d),	c		);
		font.drawString(1505,y,"sec",					c		);
	}
	
	public void init()
	{
		try
		 {
			 FileReader fr = new FileReader( "./images/15seconds/0/scores.txt");
		   Scanner sc = new Scanner ( fr );

		   String[] temp = sc.nextLine().split(":");
		   
		   this.auteur = Double.parseDouble(temp[0]);
		   this.or = Double.parseDouble(temp[1]);
		   this.argent = Double.parseDouble(temp[2]);
		   this.bronze = Double.parseDouble(temp[3]);
		   this.score = Double.parseDouble(temp[4]);
		   this.lastscore = Double.parseDouble(temp[5]);
		   fr.close();
		   sc.close();
		 }
		 catch (Exception e){e.printStackTrace();}
	}
	
	public void end(double temps)
	{
		fin = true;
		temps = temps-8;		
		
		String temp = String.format("%.3f",temps);
		String gauche = (temp.substring(0, temp.indexOf(",")));
		String droite = (temp.substring(temp.indexOf(",")+1));
		
		String temp2 = String.format("%.3f",(score-temps));
		String gauche2 = (temp2.substring(0, temp2.indexOf(",")));
		String droite2 = (temp2.substring(temp2.indexOf(",")+1));
		
		if(temps<score)
		{
			try
			{
				FileWriter fr = new FileWriter ("./images/15seconds/0/scores.txt");
			  PrintWriter pw = new PrintWriter ( fr );

			  pw.write ( auteur+":"+or+":"+argent+":"+bronze+":"+gauche+"."+droite+":"+gauche2+"."+droite2+"\r\n");
			  
			  fr.close();
			  pw.close();
			}
			catch (Exception e){ e.printStackTrace();} 
		}
		else
		{
			try
			{
				FileWriter fr = new FileWriter ("./images/15seconds/0/scores.txt");
			  PrintWriter pw = new PrintWriter ( fr );

			  pw.write ( auteur+":"+or+":"+argent+":"+bronze+":"+score+":"+gauche2+"."+droite2+"\r\n");
			  
			  fr.close();
			  pw.close();
			}
			catch (Exception e){ e.printStackTrace();} 
		}
	}
	
	public boolean getFin(){return this.fin;}
}
