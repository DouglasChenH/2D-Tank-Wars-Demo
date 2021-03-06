//import java.awt.Color;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Home {
	private int x, y;
	private TankClient tc;
	public static final int width = 30, length = 30; 
	private boolean live = true;

	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	static {
		homeImags = new Image[] { 
				tk.getImage(ConcreteWall.class.getResource("Images/home_1.png")),
				tk.getImage(ConcreteWall.class.getResource("Images/home_2.png"))};
	}

	public Home(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}

	public void gameOver(Graphics g) {

		//tc.tanks.clear();// clear all items
		//tc.metalWall.clear();
		//tc.otherWall.clear();
		//tc.bombTanks.clear();
		//tc.theRiver.clear();
		//tc.trees.clear();
		//tc.bullets.clear();
		tc.homeTank.setLive(false);
		/*
		Color c = g.getColor(); 
		g.setColor(Color.green);
		Font f = g.getFont();
		g.setFont(new Font(" ", Font.PLAIN, 40));
		g.drawString("You Lose��", 220, 250);
		g.drawString("  Game Over�� ", 220, 300);
		g.setFont(f);
		g.setColor(c);
		*/

	}

	public void draw(Graphics g) {

		if (live) { // if live then draw home
			g.drawImage(homeImags[0], x, y, null);

			for (int i = 0; i < tc.homeWall.size(); i++) {
				ConcreteWall w = tc.homeWall.get(i);
				w.draw(g);
			}
		} else {
			g.drawImage(homeImags[1], x, y, null);
			gameOver(g); 

		}
	}

	public boolean isLive() { 
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}

}
