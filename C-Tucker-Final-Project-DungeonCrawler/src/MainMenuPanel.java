import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel implements ActionListener {

	private JButton newGameBtn;
	private JButton gameInfoBtn;
	private JButton	exitBtn;
	private JButton continueButton;
	
	private JFrame parentFrame;
	private Image background;
	public MainMenuPanel(JFrame frame)
	{
		this.parentFrame =frame;
		background = new ImageIcon("Resources/desertBackgroung.png").getImage();
		setLayout(null);
		newGameBtn = new JButton("New Game");
		gameInfoBtn = new JButton("Game Info");
		exitBtn =new JButton("Exit Game");
		continueButton = new JButton("Continue");
		
		newGameBtn.setBounds(850, 300, 200, 50);
        gameInfoBtn.setBounds(850, 440, 200, 50);
        exitBtn.setBounds(850, 510, 200, 50);
        continueButton.setBounds(850,370,200,50);
        
        add(newGameBtn);
        add(gameInfoBtn);
        add(exitBtn);
        add(continueButton);
        

        newGameBtn.addActionListener(this);
        gameInfoBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        continueButton.addActionListener(this);
        
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

        if (e.getSource() == newGameBtn)
        {
            startNewGame();
        }
        else if (e.getSource() == gameInfoBtn)
        {
            showGameInfo();
        }
        else if (e.getSource() == exitBtn)
        {
            exitGame();
        }
        else if(e.getSource()==continueButton)
        {
        	loadGame();
        }
	}
	
	private void startNewGame()
	{
		ClassSelectionPanel game = new ClassSelectionPanel(parentFrame);
		parentFrame.setContentPane(game);
		parentFrame.revalidate();
		parentFrame.repaint();
	}
	
	private void showGameInfo()
	{
		JOptionPane.showMessageDialog(null,"Use 'w,a,s,d' keys to move. 'I' opens your inventory. To equip or use an item in your inventory click on its name.");
		
	}
	private void exitGame()
	{
		int confirm = JOptionPane.showConfirmDialog(this, "Save before exiting?","Exit Game",JOptionPane.YES_NO_CANCEL_OPTION);
		
		if(confirm==JOptionPane.YES_OPTION)
		{
			if (parentFrame.getContentPane() instanceof DungeonPanel dp)
	        {
	            SaveManager.saveGame(dp.getPlayer(), dp.getFloor());
	        }
	        System.exit(0);
	    }
	    else if (confirm == JOptionPane.NO_OPTION)
	    {
	        System.exit(0);
	    }
		}
	
	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent(g);

	    // draw background image
	    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

	    // draw title on top
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Times New Roman", Font.BOLD, 60));
	    g.drawString("DUNGEON GAME", 700, 200);
	}
	
	private void loadGame()
	{
		SaveData data = SaveManager.loadGame();

	    if (data == null)
	    {
	        JOptionPane.showMessageDialog(this, "No save file found!");
	        return;
	    }

	    DungeonPanel game = new DungeonPanel(parentFrame, data.player.getPlayerClass());

	    game.setPlayer(data.player);
	    game.setFloor(data.floor);

	    parentFrame.setContentPane(game);
	    parentFrame.revalidate();
	    parentFrame.repaint();
}}
