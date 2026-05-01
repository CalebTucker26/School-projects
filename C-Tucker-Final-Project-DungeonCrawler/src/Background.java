import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel {

	private Image background;
	
	public Background()
	{
		background=new ImageIcon("Resources/desertBackgroung.png").getImage();
		setLayout(new BorderLayout());
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(background,0,0,getWidth(),getHeight(),null);
	}
}

