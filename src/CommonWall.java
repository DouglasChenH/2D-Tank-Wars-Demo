import java.awt.*;

public class CommonWall {
	public static final int width = 50; // wall properties 20 20
	public static final int length = 50;
	int x, y;

	TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { // store picture of commonWall 
		tk.getImage(CommonWall.class.getResource("Images/wall_1.png")), };
	}

	public CommonWall(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc; 
	}

	public void draw(Graphics g) {// draw the commonWall out
		g.drawImage(wallImags[0], x, y, null);
	}

	public Rectangle getRect() {  
		return new Rectangle(x, y, width, length);
	}
}
