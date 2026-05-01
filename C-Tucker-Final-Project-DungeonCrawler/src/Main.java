import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		JFrame frame= new JFrame("Dungeon Crawler");
		MainMenuPanel menu= new MainMenuPanel(frame);
		frame.setContentPane(menu);
		frame.setSize(1920,1080);
		frame.requestFocusInWindow();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}

	
}
