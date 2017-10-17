import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall {
	public static final int width = 50; //30 30
	public static final int length = 50;
	private int x, y;
	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/iron_1.png")), };
	}

	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { // draw metal wall
		g.drawImage(wallImags[0], x, y, null);
		
	}

	public Rectangle getRect() { // draw rectangle
		return new Rectangle(x, y, width, length);
	}
}
