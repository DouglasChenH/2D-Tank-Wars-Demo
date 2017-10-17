import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class River {
	public static final int riverWidth = 55; //40;
	public static final int riverLength = 155; //100;
	private int x, y;
	TankClient tc ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	
	static {   //store river image
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.jpg")),
		};
	}
	
	
	public River(int x, int y, TankClient tc) {    
		this.x = x;
		this.y = y;
		this.tc = tc;             
	}
	
	public void draw(Graphics g) {
		g.drawImage(riverImags[0],x, y, null);            //draw river at location x,y
	}
	public static int getRiverWidth() {
		return riverWidth;
	}

	public static int getRiverLength() {
		return riverLength;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
