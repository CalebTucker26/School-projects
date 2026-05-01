import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class ClassSelectionPanel extends JPanel implements ActionListener {
	
	private JFrame parentFrame;
	private JButton knightBtn;
	private JButton rangerBtn;
	private JButton	wizardBtn;
	private JButton backBtn;
	
	private Image background;

	public ClassSelectionPanel(JFrame frame)
    {
        this.parentFrame = frame;
        background= new ImageIcon("Resources/desertBackgroung.png").getImage();

        setLayout(null);
        setBackground(Color.BLACK);

        knightBtn = new JButton("Knight");
        rangerBtn = new JButton("Ranger");
        wizardBtn = new JButton("Wizard");
        backBtn = new JButton("Back");

        knightBtn.setBounds(50, 300, 250, 60);
        rangerBtn.setBounds(50, 380, 250, 60);
        wizardBtn.setBounds(50, 460, 250, 60);
        backBtn.setBounds(50, 600, 250, 50);

        add(knightBtn);
        add(rangerBtn);
        add(wizardBtn);
        add(backBtn);

        knightBtn.addActionListener(this);
        rangerBtn.addActionListener(this);
        wizardBtn.addActionListener(this);
        backBtn.addActionListener(this);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == knightBtn)
        {
            startGame("Knight");
        }
        else if (e.getSource() == rangerBtn)
        {
            startGame("Ranger");
        }
        else if (e.getSource() == wizardBtn)
        {
            startGame("Wizard");
        }
        else if (e.getSource() == backBtn)
        {
            parentFrame.setContentPane(new MainMenuPanel(parentFrame));
            parentFrame.revalidate();
        }
	}
	
	@Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        g.drawString("Select Your Class Ability", 700, 200);

        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g.drawString("Knight: Smite: executes enemies under 30% health.", 300, 350);
        g.drawString("Ranger: Arrow Rain: deals damage over 3 turns.", 300, 430);
        g.drawString("Wizard: Fire Ball: deals high damage.", 300, 510);
    }
	
	private void startGame(String playerClass)
    {
        DungeonPanel game = new DungeonPanel(parentFrame, playerClass);

        parentFrame.setContentPane(game);
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
