import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

// inheriting Frame class from AWT
// frame has title bar and menu
public class TankClient extends Frame implements ActionListener {


	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; // static frame size 
	public static final int Fram_length = 600;
	public static boolean printable = true;
	MenuBar jmb = null;
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	Image screenImage = null;

	Tank homeTank = new Tank(250, 550, true, false, Direction.STOP, this);// instantiate tank 
	GetBlood blood = new GetBlood(); // instantiate blood 
	Home home = new Home(373, 545, this);// instantiate home 

	List<River> theRiver = new ArrayList<River>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<ExplodedTank> explodedTanks = new ArrayList<ExplodedTank>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>(); 
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);

		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.GRAY);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void framPaint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.green); // set font color to green 

		Font f1 = g.getFont();
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Enemies: ", 200, 80);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 25));
		g.drawString("" + tanks.size(), 300, 80);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("Health: ", 500, 80);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 25));
		g.drawString("" + homeTank.getLife(), 580, 80);
		g.setFont(f1);

		if (tanks.size() == 0 && home.isLive() && homeTank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 60)); // judge winner
			this.otherWall.clear();
			g.drawString("You Win£¡ ", 310, 300);
			g.setFont(f);
		}

		if (homeTank.isLive() == false) {
			/*
			 *  tc.tanks.clear();// clear all items
				tc.metalWall.clear();
				tc.otherWall.clear();
				tc.bombTanks.clear();
				tc.theRiver.clear();
				tc.trees.clear();
				tc.bullets.clear();
				tc.homeTank.setLive(false);
				Color c = g.getColor(); 
				g.setColor(Color.green);
				Font f = g.getFont();
				g.setFont(new Font(" ", Font.PLAIN, 40));
				g.drawString("You Lose£¡", 220, 250);
				g.drawString("  Game Over£¡ ", 220, 300);
				g.setFont(f);
				g.setColor(c);
			 */
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			//tanks.clear();
			//bullets.clear();
			g.drawString("You Lose£¡", 220, 250);
			g.drawString("  Game Over£¡ ", 220, 300);
			g.setFont(f);
			printable = false;
		}
		g.setColor(c);

		for (int i = 0; i < theRiver.size(); i++) { // draw rivers 
			River r = theRiver.get(i);
			r.draw(g);
		}

		for (int i = 0; i < theRiver.size(); i++) {
			River r = theRiver.get(i);
			homeTank.collideRiver(r);

			r.draw(g);
		}

		home.draw(g); // draw home 
		homeTank.draw(g);// draw own tank 
		homeTank.eat(blood);// refill blood 

		for (int i = 0; i < bullets.size(); i++) { 
			Bullets m = bullets.get(i);
			m.hitTanks(tanks); 
			m.hitTank(homeTank); 
			m.hitHome(); 

			for (int j = 0; j < metalWall.size(); j++) { 
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); 
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i); 

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				t.collideWithWall(cw); 
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) { 
				CommonWall cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < metalWall.size(); j++) { 
				MetalWall mw = metalWall.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			for (int j = 0; j < theRiver.size(); j++) {
				River r = theRiver.get(j); 
				t.collideRiver(r);
				r.draw(g);
				// t.draw(g);
			}

			t.collideWithTanks(tanks); 
			t.collideHome(home);

			t.draw(g);
		}

		blood.draw(g);

		for (int i = 0; i < trees.size(); i++) { // draw trees
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < explodedTanks.size(); i++) { // draw bomb effect
			ExplodedTank bt = explodedTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { // draw otherWall
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) { // draw metalWall
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		homeTank.collideWithTanks(tanks);
		homeTank.collideHome(home);

		for (int i = 0; i < metalWall.size(); i++) {// collide with metal wall
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { //collide with common wall
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) { // own tank collide with own home
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

	}

	public TankClient() {
		// printable = false;
		// create menu
		jmb = new MenuBar();
		jm1 = new Menu("Game");
		jm2 = new Menu("Stop/Continue");
		jm3 = new Menu("Help");
		jm4 = new Menu("Difficulty");
		
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));// set font for menu
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));
        
		//create menu items
		jmi1 = new MenuItem("New Game");
		jmi2 = new MenuItem("Exit");
		jmi3 = new MenuItem("Stop");
		jmi4 = new MenuItem("Continue");
		jmi5 = new MenuItem("Instruction");
		jmi6 = new MenuItem("Easy");
		jmi7 = new MenuItem("Normal");
		jmi8 = new MenuItem("Hard");
		jmi9 = new MenuItem("Lengendary");
		
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));
        
		//add menu items 1 & 2 to menu 1
		jm1.add(jmi1);
		jm1.add(jmi2);
		//add menu items 3 & 4 to menu 2
		jm2.add(jmi3);
		jm2.add(jmi4);
		//add menu items 5 to menu 3
		jm3.add(jmi5);
		//add menu items 6-9 to menu 4
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);

		// add menus 1-4 to menu bar 
		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm4);
		jmb.add(jm3);

		jmi1.addActionListener(this); //passing current instance  
		jmi1.setActionCommand("NewGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1");
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");

		this.setMenuBar(jmb);// push Bar to JFrame
		this.setVisible(true);
        
		for (int i = 0; i < 5; i++) { // home location
			if (i < 2)
				homeWall.add(new CommonWall(320, 550 - 50 * i, this));
			else if (i < 3)
				homeWall.add(new CommonWall(370, 500, this));
			else
				homeWall.add(new CommonWall(416, 350 + i* 50, this));

		}
		


		for (int i = 0; i < 5; i++) {			
			otherWall.add(new CommonWall(150 + 50 * i, 220, this)); // common wall layout
			//otherWall.add(new CommonWall(500 + 50 * i, 180, this));
			otherWall.add(new CommonWall(100, 400 + 50 * i, this));
			//otherWall.add(new CommonWall(500, 400 + 50 * i, this));
		
		}

		for (int i = 0; i < 10; i++) { // metal wall layout
			if (i < 5) {
			
				metalWall.add(new MetalWall(220 + 50 * i, 300, this));
				metalWall.add(new MetalWall(600, 400 + 50 * (i), this));
			} 
			else
				metalWall.add(new MetalWall(750 + 50 * (i - 10), 160, this));
		}

		for (int i = 0; i < 4; i++) { // tree layout
			if (i < 4) {
				trees.add(new Tree(0 + 30 * i, 360, this));
				trees.add(new Tree(220 + 30 * i, 360, this));
				trees.add(new Tree(440 + 30 * i, 360, this));
				trees.add(new Tree(660 + 30 * i, 360, this));
			}

		}

		theRiver.add(new River(85, 100, this));

		for (int i = 0; i < 12; i++) { // initialize  enemy tanks
			if (i < 9) // initialize tank start locations
				tanks.add(new Tank(150 + 70 * i, 40, false, true, Direction.D, this));
			else if (i < 15)
				tanks.add(new Tank(700, 140 + 50 * (i - 6), false, true, Direction.D, this));
			else
				tanks.add(new Tank(10, 50 * (i - 12), false, true, Direction.D, this));
		}

		this.setSize(Fram_width, Fram_length); // set screen size
		this.setLocation(280, 50); //set screen location
		this.setTitle("Tank Wars¡ª¡ª(Restart£ºR Fire£ºF)                 Name£ºDouglas Chen");

		this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start(); 
	}

	

	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) { 
			homeTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { 
			homeTank.keyPressed(e);
		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "Yes", "No" };
			int response = JOptionPane.showOptionDialog(this, "New Game?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new TankClient();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
			// try {
			// Thread.sleep(10000);
			//
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); 
			}

		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "Yes", "No" };
			int response = JOptionPane.showOptionDialog(this, "Exit?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("exit");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); 

			}

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "Use ¡ú ¡û ¡ü ¡ý to move£¬F to fire and R to restart£¡",
					"Tutorial£¡", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); 
		} else if (e.getActionCommand().equals("level1")) {
			Tank.count = 12;
			Tank.speedX = 6;
			Tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.count = 12;
			Tank.speedX = 10;
			Tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.count = 20;
			Tank.speedX = 14;
			Tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.count = 20;
			Tank.speedX = 16;
			Tank.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		}
	}
	
	
	public static void main(String[] args) {
		new TankClient(); 
	}
}
