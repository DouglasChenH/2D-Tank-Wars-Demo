import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//设置界面树和丛林
public class Tree {
	public static final int width = 100; //30;
	public static final int length = 100; //30;
	int x, y;
	TankClient tc ;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	static {
		treeImags = new Image[]{
				tk.getImage(ConcreteWall.class.getResource("Images/tree.gif")),
		};
	}
	
	
	public Tree(int x, int y, TankClient tc) {  
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {           //draw tree
		g.drawImage(treeImags[0],x, y, null);
	}
	
}
