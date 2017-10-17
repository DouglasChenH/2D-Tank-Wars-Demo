import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
// import java AWT API to design GUI

public class ExplodedTank {
	private int x, y;
	private boolean live = true; // damaged tank was live originally
	private TankClient tc;
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] imgs = { // stored bomb effect images from small to big 
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/1.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/2.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/3.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/4.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/5.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/6.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/7.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/8.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/9.gif")),
			tk.getImage(ExplodedTank.class.getClassLoader().getResource(
					"images/10.gif")), };
	int step = 0;

	public ExplodedTank(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) { // draw bomb image

		if (!live) { // remove bomb image afterwards
			tc.explodedTanks.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}

		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
