import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Tank {
	public static  int speedX = 6, speedY =6; //tank speed, the bigger the harder
	public static int count = 0;
	public static final int width = 50, length = 50; // tank size, unchangeable
	private Direction direction = Direction.STOP; // initial tank moving status is "stop"
	private Direction Kdirection = Direction.U; // initial direction is "upward"
	TankClient tc;

	private boolean good;
	private boolean enemy;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; // initial status is "alive"
	private int life = 200; // initial health is 200 points

	private static Random r = new Random(); 
	private int step = r.nextInt(10)+5 ; // random number from 0 to 9 and then add 5

	private boolean bL = false, bU = false, bR = false, bD = false;
	

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null; 
	static {
		tankImags = new Image[] {
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_enemy_down_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_enemy_up_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_enemy_left_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_enemy_right_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_self_down_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_self_up_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_self_left_3.png")),
				tk.getImage(ExplodedTank.class.getResource("Images/TANK1_self_right_3.png"))};

	}

	public Tank(int x, int y, boolean good, boolean enemy) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
		this.enemy = enemy;
	}

	public Tank(int x, int y, boolean good, boolean enemy, Direction dir, TankClient tc) {
		this(x, y, good, enemy);
		this.direction = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); // remove dead tanks
			}
			return;
		}

		if (good)
			new DrawBloodbBar().draw(g); // create a first aid kit 

		switch (Kdirection) {
							//choose tank image according to direction
		case D:
			g.drawImage(enemy?tankImags[0]:tankImags[4], x, y, null);
			break;

		case U:
			g.drawImage(enemy?tankImags[1]:tankImags[5], x, y, null);
			break;
		case L:
			g.drawImage(enemy?tankImags[2]:tankImags[6], x, y, null);
			break;

		case R:
			g.drawImage(enemy?tankImags[3]:tankImags[7], x, y, null);
			break;
		
		case STOP:
			break;

		}

		move();   
	}

	void move() {

		this.oldX = x;
		this.oldY = y;

		switch (direction) {  
		case L:
			x -= speedX;
			break;
		case U:
			y -= speedY;
			break;
		case R:
			x += speedX;
			break;
		case D:
			y += speedY;
			break;
		case STOP:
			break;
		}

		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}

		if (x < 0)
			x = 0;
		if (y < 50)      //in case tank move out of the screen
			y = 50;
		if (x + Tank.width > TankClient.Fram_width)  //relocate tank to the border if it moves out
			x = TankClient.Fram_width - Tank.width;
		if (y + Tank.length > TankClient.Fram_length)
			y = TankClient.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  //random step
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      //random direction
			}
			step--;

			if (r.nextInt(40) > 38)//random fire
				this.fire();
		}
	}

	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_R:  //restart
			tc.tanks.clear();  
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			if (tc.tanks.size() == 0) {   //reinitialize tanks
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              //initialize tank locations
						tc.tanks.add(new Tank(150 + 70 * i, 40, false, true, Direction.R, tc));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i -6), false, true, Direction.D, tc));
					else
						tc.tanks.add(new Tank(10,  50 * (i - 12), false, true, Direction.L, tc));
				}
			}
			
			tc.homeTank = new Tank(300, 560, true, false, Direction.STOP, tc);//initialize own tank location
			
			if (!tc.home.isLive())  //reset home 
				tc.home.setLive(true);
			new TankClient(); //repaint panel
			break;
		case KeyEvent.VK_RIGHT: 
			bR = true;
			break;
			
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		
		case KeyEvent.VK_UP:  
			bU = true;
			break;
		
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		decideDirection();
	}

	void decideDirection() {
		if (!bL && !bU && bR && !bD)  
			direction = Direction.R;

		else if (bL && !bU && !bR && !bD)   
			direction = Direction.L;

		else if (!bL && bU && !bR && !bD)  
			direction = Direction.U;

		else if (!bL && !bU && !bR && bD) 
			direction = Direction.D;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  
	}

	public void keyReleased(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_F:
			fire();
			break;
			
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		
		case KeyEvent.VK_UP:
			bU = false;
			break;
		
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
			

		}
		decideDirection();  //decide moving direction after releasing 
	}

	public Bullets fire() {  
		if (!live)
			return null;
		int x = this.x + Tank.width / 2 - Bullets.width / 2;  //fire location
		int y = this.y + Tank.length / 2 - Bullets.length / 2;
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc);  //fire along original direction
		tc.bullets.add(m);                                                
		return m;
	}


	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(ConcreteWall w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    //return to original direction
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {  
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) {    
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) {  
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}


	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			g.fillRect(375, 585, w > 0? w: 10, 10);
			g.setColor(c);
		}
	}

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      //add 100 health point after eating one heart
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
