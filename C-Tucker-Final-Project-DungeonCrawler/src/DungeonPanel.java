import javax.swing.*;
import java.awt.image.*;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
public class DungeonPanel extends JPanel implements ActionListener{
	
	final int MAX_FLOOR=50;
	private static final int START_ROW = 4;
	private static final int START_COL = 2;
	JButton attackButton;
	JButton runButton;
	JButton lootChest;
	JButton leave;
	JButton closeShop;
	JButton abilityButton;
	JButton returnToMainMenu;
	int rows=5;
	int cols=5;
	int floor =1;
	int currentFloor;
	int enemiesPlaced=0;
	int chestCount=3;
	boolean playerTurn=true;
	ArrayList<String> combatLog=new ArrayList();
	Player player;
	Enemy currentEnemy;
	Room[][] dungeon;
	ShopSystem shop; 
	Room currentRoom;
	Random rand =new Random();
	Image playerImage;
	Image playerCombatImage;
	Image combatBackground;
	Image roomImage;
	Image background;
	Image chestImage;
	private JFrame parentFrame;
	GameState previousState;
	
	enum GameState
	{
		EXPLORE,
		COMBAT,
		CHEST,
		INVENTORY,
		SHOP
	}
	GameState state= GameState.EXPLORE;
	public DungeonPanel(JFrame frame, String playerClass)
	{
		this.parentFrame=frame;
		if (playerClass == null)
		{
		    throw new IllegalArgumentException("playerClass is null during load");
		}
		
		if (playerClass.equals("Knight"))
		{
		    player = Player.createKnight(4,2);
		}
		else if (playerClass.equals("Ranger"))
		{
		    player = Player.createRanger(4,2);
		}
		else if (playerClass.equals("Wizard"))
		{
		    player = Player.createWizard(4,2);
		}
		else
		{
		    throw new IllegalArgumentException("Unknown class: " + playerClass);
		}
		player.setPlayerClass(playerClass);
		currentFloor=1;
		floor=1;
		rows=5;
		cols=5;
		setupKeyBindings();
		player.addItem(new Item("Minor Healing Potion", "Heal", null, 20, 5, false));
		generateDungeon();
		shop= new ShopSystem();
		
		playerImage =new ImageIcon("Resources/barbute.png").getImage();
		playerCombatImage = new ImageIcon("Resources/player.png").getImage();
		combatBackground = new ImageIcon("Resources/OldDungeon.png").getImage();
		roomImage=new ImageIcon("Resources/wall_tiles.png").getImage();
		background= new ImageIcon("Resources/desertBackgroung.png").getImage();
		chestImage= new ImageIcon("Resources/chest.png").getImage();
		//<a href="https://www.freepik.com/free-vector/realistic-old-style-pirate-treasure-chest_3949277.htm">Image by macrovector on Freepik</a>
		setOpaque(false);
		
		attackButton= new JButton("Attack");
		runButton= new JButton("Run");
		leave= new JButton("Leave");
		lootChest= new JButton("Open Chest");
		closeShop = new JButton("Leave Shop");
		abilityButton = new JButton("Use Ability");
		returnToMainMenu= new JButton("Return to main menu.");
		returnToMainMenu.setVisible(true);
		abilityButton.setVisible(false);
		attackButton.setVisible(false);
		runButton.setVisible(false);
		leave.setVisible(false);
		lootChest.setVisible(false);
		closeShop.setVisible(false);
		this.add(attackButton);
		this.add(runButton);
		this.add(leave);
		this.add(lootChest);
		this.add(closeShop);
		this.add(abilityButton);
		this.add(returnToMainMenu);
		setLayout(null);
		attackButton.setBounds(900,950,100,40);
		runButton.setBounds(1800,25,100,40);
		leave.setBounds(1800,25,100,40);
		lootChest.setBounds(900,950,100,40);
		closeShop.setBounds(900,950,100,40);
		abilityButton.setBounds(750,950,100,40);
		returnToMainMenu.setBounds(50,1000,300,40);
		attackButton.addActionListener(this);
		lootChest.addActionListener(this);
		leave.addActionListener(this);
		runButton.addActionListener(this);
		closeShop.addActionListener(this);
		abilityButton.addActionListener(this);
		returnToMainMenu.addActionListener(this);
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
		    {
		        int mouseY = e.getY();

		        // INVENTORY CLICK HANDLING
		        if (state == GameState.INVENTORY)
		        {
		            int y = 100;

		            for (int i = 0; i < player.getInventory().size(); i++)
		            {
		                int itemTop = y - 20;
		                int itemBottom = y + 5;

		                if (mouseY >= itemTop && mouseY <= itemBottom)
		                {
		                    Item item = player.getInventory().get(i);
		                    useItem(item, i);
		                    state=GameState.INVENTORY;
		                }

		                y += 30;
		            }
		            return;
		        }

		        // SHOP CLICK HANDLING
		        if (state == GameState.SHOP)
		        {
		        	int y = 100;
		        	 for (int i = 0; i < shop.getItems().size(); i++)
		        	    {
		        	        int itemTop = y - 20;
		        	        int itemBottom = y + 5;

		        	        if (mouseY >= itemTop && mouseY <= itemBottom)
		        	        {
		        	            Item item = shop.getItems().get(i);

		        	            if (player.spendGold(item.getPrice()))
		        	            {
		        	                player.addItem(item);
		        	                addLog("Bought " + item.getName());
		        	            }
		        	            else
		        	            {
		        	                addLog("Not enough gold");
		        	            }

		        	            repaint();
		        	            return;
		        	        }

		        	        y += 30;
		        	    }
		        	    return;
		        }
		    }
		});
	}
	
	

	
	
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		if(state== GameState.EXPLORE)
		{
			attackButton.setVisible(false);
		    runButton.setVisible(false);
		    leave.setVisible(false);
		    lootChest.setVisible(false);
			drawDungeon(g);
		}
		else if(state== GameState.COMBAT)
		{
			
			drawCombatScreen(g);
			System.out.println("Current State: " + state);
			lootChest.setVisible(false);
			leave.setVisible(false);
		}
		else if(state==GameState.CHEST)
		{
			attackButton.setVisible(false);
		    runButton.setVisible(false);
			drawChestScreen(g);
		}
		else if(state==GameState.INVENTORY)
		{
			drawInventory(g);
		}
		else if(state==GameState.SHOP)
		{
			drawShop(g);
		}
		
	}
		
	private void drawDungeon(Graphics g)
		{
			
			int tileSize = Math.min(getWidth() / cols, getHeight() / rows);
			int xOffset = (getWidth() - (tileSize * cols)) / 2;
			int yOffset = (getHeight() - (tileSize * rows)) / 2;
			int imgSize =tileSize/2;
			g.drawString("Floor: " + currentFloor, 50, 50);
			
			g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
			for (int r =0; r< rows; r++)
				{
					for (int c=0;c<cols;c++)
							{
								int screenX= xOffset + c*tileSize;
								int screenY= yOffset + r*tileSize;
								Image tile = roomImage;
								g.drawImage(tile, screenX, screenY, tileSize, tileSize, null);
							}
					int y=140;
					g.setFont(new Font("Times New Roman", Font.BOLD, 18));
					for (String msg :combatLog)
					{
						g.drawString(msg, 1550, y);
						y+=25;
					}
					g.drawImage(playerImage, xOffset + player.getCol() * tileSize + (tileSize - imgSize) / 2, yOffset + player.getRow() * tileSize + (tileSize - imgSize) / 2, imgSize, imgSize, null);
						closeShop.setVisible(false);
						abilityButton.setVisible(false);
				}
			
		}
	
	private void drawCombatScreen(Graphics g)
		{
			g.drawImage(combatBackground, 0, 0, getWidth(), getHeight(), null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", Font.BOLD, 24));
			g.drawString("COMBAT ENCOUNTER!!!",825,50);
			g.setFont(new Font("Times New Roman", Font.BOLD, 24));
			g.drawString("Player HP: " + player.getHP(), 1400, 140);
			g.drawString("Enemy: "+currentEnemy.getType(), 380, 110 );
			g.drawString("Enemy HP: " + currentEnemy.getHP(), 380, 140);
			
			if(playerCombatImage!=null)
			{
				g.drawImage(playerCombatImage, 1400, 750, 200, 200, null);
				
			}
			
			if (currentEnemy != null && currentEnemy.getImage() != null)
		    {
		        g.drawImage(currentEnemy.getImage(), 380, 750, 200, 200, null);
		        
		    }
			int y=140;
			for (String msg :combatLog)
			{
				g.drawString(msg, 1650, y);
				y+=25;
			}
			g.drawString(playerTurn ? "Your Turn" : "Enemy Turn", 900, 140);
			attackButton.setVisible(true);
			runButton.setVisible(true);
			abilityButton.setVisible(true);
		}
	
	private void drawChestScreen(Graphics g)
		{
			g.drawImage(combatBackground, 0, 0, getWidth(), getHeight(), null);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", Font.BOLD, 24));
			g.drawString("You Found A Chest!!!", 900, 50);
			if(chestImage!=null &&playerCombatImage!=null)
			{
				g.drawImage(chestImage, 380, 700, 250, 250, null);
				g.drawImage(playerCombatImage,1400,750,200,200,null);
			}
			int y=140;
			for (String msg :combatLog)
			{
				g.drawString(msg, 1550, y);
				y+=25;
			}
			g.drawString("Inventory:", 50, 50);

			int y2 = 80;
			for (Item item : player.getInventory()) {
			    g.drawString("- " + item.getName(), 50, y2);
			    y2 += 20;
			}
			leave.setVisible(true);
			lootChest.setVisible(true);
			closeShop.setVisible(false);
			abilityButton.setVisible(false);
		}
	private void drawInventory(Graphics g)
	{
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0,  getWidth(), getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("Times New Roman", Font.BOLD,28));
		g.drawString("INVENTORY", 50, 50);
		g.setFont(new Font("Times New Roman", Font.PLAIN,20));
		int y3 =100;
		for (Item item: player.getInventory())
		{
			g.drawString(item.getName() +" (" + item.getType()+" )", 50,y3);
			y3+=30;
		}
		g.setFont(new Font("Times New Roman", Font.BOLD, 28));
		g.drawString("Equipped", 950, 50);
		
		g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		g.drawString("Head: " + getItemName(player.getHead()), 950, 90);
		g.drawString("Chest: " + getItemName(player.getChest()), 950, 120);
		g.drawString("Legs: " + getItemName(player.getLegs()), 950, 150);
		g.drawString("Weapon: " + getItemName(player.getWeapon()), 950, 180);
		g.drawString("Shield: " + getItemName(player.getSheild()),950,210);
		System.out.println("Drawing inventory: "+player.getInventory().size());
		attackButton.setVisible(false);
		runButton.setVisible(false);
		closeShop.setVisible(false);
		abilityButton.setVisible(false);
		
		
	}
	
	private void drawShop(Graphics g)
	{
		g.setFont(new Font("Times New Roman", Font.BOLD, 28));
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, getWidth(), getHeight());

	    g.setColor(Color.WHITE);
	    g.drawString("SHOP", 950, 50);

	    int y = 100;

	    for (Item item : shop.getItems())
	    {
	        g.drawString(item.getName() + " - " + item.getPrice() + " gold", 50, y);
	        y += 30;
	    }

	    g.drawString("Gold: " + player.getGold(), 1650,50);

		int y2=140;
		for (String msg :combatLog)
		{
			g.drawString(msg, 1550, y2);
			y2+=25;
		}
		g.drawString(playerTurn ? "Your Turn" : "Enemy Turn", 900, 140);
		closeShop.setVisible(true);
	}
	
	
	
	private void setupKeyBindings()
		{
			//Up (W)
			getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"),"up");
			getActionMap().put("up", new AbstractAction()
				{
					public void actionPerformed(ActionEvent e)
					{
							movePlayer(-1,0);
					}
				});
		
		//Down (S)
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"),"down");
		getActionMap().put("down", new AbstractAction()
				{
					public void actionPerformed(ActionEvent e)
					{
							movePlayer(1,0);
					}
				});
		//Left (A)
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"),"left");
		getActionMap().put("left", new AbstractAction()
				{
					public void actionPerformed(ActionEvent e)
					{
						movePlayer(0,-1);
					}
				});
		//Right (D)
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"),"right");
		getActionMap().put("right", new AbstractAction()
				{
					public void actionPerformed(ActionEvent e)
					{
						movePlayer(0,1);
					}
				});
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("I"),"inventory");
		getActionMap().put("inventory", new AbstractAction()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				if(state==GameState.INVENTORY)
				{
					state=previousState;
				}
				else
				{
					previousState=state;
					state=GameState.INVENTORY;
				}
				repaint();
			}
		});
	}
	
	private void enterRoom()
	{
		player.setAbilityUsed(false);
		System.out.println("Entered room: " + player.getRow() + "," + player.getCol());
		currentRoom= dungeon[player.getRow()][player.getCol()];
		currentEnemy=currentRoom.getEnemy();
		
			
			if (currentEnemy!= null)
			{
				startCombat();
				System.out.println("Enemy Encounter at: " +player.getRow()+", "+player.getCol());
			}
			else if(currentRoom.hasChest() && !currentRoom.isChestOpened())
			{
				startChest();
			}
			else if(currentRoom.hasShop())
			{
				startShop();
			}
		}
	
	
	private void movePlayer(int dRow,int dCol)
	{
		if(state != GameState.EXPLORE)
		{
			return;
		}
		int newRow=player.getRow()+dRow;
		int newCol=player.getCol()+dCol;
		
		if(newRow >= 0 &&newRow < rows && newCol >= 0 && newCol < cols)
		{
			player.move(dRow, dCol);
			enterRoom();
			repaint();
		}
	}
	
	private void startCombat()
	{
		previousState=state;
		state=GameState.COMBAT;
		addLog("An enemy appears!");
		state=GameState.COMBAT;
		addLog("Combat Started!");
	}
	private void startExplore()
	{
		state=GameState.EXPLORE;
		addLog("Back to Dungeon Map!");
	}
	private void startChest()
	{
		state=GameState.CHEST;
		addLog("Loot Found!");
	}
	private void startShop()
	{
		state=GameState.SHOP;
		addLog("Entered Shop");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==attackButton)
		{
			if(state != GameState.COMBAT|| !playerTurn|| currentEnemy ==null)
			{
				return;
			}
			int playerDamage=player.getAttack() +rand.nextInt(6);
			currentEnemy.takeDamage(playerDamage);
			addLog("Player hits " + currentEnemy.getType()+ " for "+playerDamage);
			
			if(!currentEnemy.isAlive())
			{
				addLog("Enemy Felled!");
				LootResult result = LootTable.rollLoot(currentEnemy);
				if (result.getGold() > 0)
				{
				    player.addGold(result.getGold());
				}

				if (result.getItem() != null)
				{
				    player.addItem(result.getItem());
				}
				
				addLog(result.getMessage());
				currentRoom.setEnemy(null);
				checkFloorClear();
				startExplore();
				repaint();
				return;
			}
			playerTurn=false;
			
			Timer timer=new Timer(500, a -> {enemyTurn();});
			timer.setRepeats(false);
			timer.start();
			repaint();
		}
		else if(e.getSource()==runButton)
		{
			
			
			System.out.println("You Ran Away"); 
			state=GameState.EXPLORE;
			repaint();
			
		}
		else if( e.getSource()==lootChest)
		{
			openChest();
		}
		else if(e.getSource()==leave)
		{
			startExplore();
			repaint();
		}
		else if(e.getSource()==closeShop)
		{
			state=GameState.EXPLORE;
			addLog("You left the shop");
			repaint();
		}
		else if(e.getSource()==abilityButton)
		{
			if (state != GameState.COMBAT || !playerTurn || currentEnemy == null)
		        return;

			if(player.hasUsedAbility())
			{
				addLog("Ability already used in this room!");
				repaint();
				return;
			}
		    int dmg = player.useAbility(currentEnemy);
		    player.setAbilityUsed(true);

		    if (player.getPlayerClass().equalsIgnoreCase("Knight"))
		    {
		        if (dmg > 0)
		            addLog("SMITE executed the enemy!");
		        else
		            addLog("Smite failed!");
		    }
		    else if (player.getPlayerClass().equalsIgnoreCase("Ranger"))
		    {
		        addLog("Arrow Rain applied!");
		    }
		    else if (player.getPlayerClass().equalsIgnoreCase("Wizard"))
		    {
		        addLog("Fireball hits for " + dmg);
		    }

		    if (!currentEnemy.isAlive())
		    {
		        addLog("Enemy Felled!");
		        currentRoom.setEnemy(null);
		        startExplore();
		        repaint();
		        return;
		    }

		    playerTurn = false;

		    Timer timer = new Timer(500, a -> enemyTurn());
		    timer.setRepeats(false);
		    timer.start();
		}
		else if (e.getSource()==returnToMainMenu)
		{
			System.out.println("return to main menu button clicked.");
			parentFrame.toFront();
			parentFrame.requestFocus();
			int confirm = JOptionPane.showConfirmDialog(
			        this,
			        "Return to main menu?\nSave your game before leaving.",
			        "Leave Dungeon",
			        JOptionPane.YES_NO_OPTION
			    );

			    if (confirm == JOptionPane.YES_OPTION)
			    {
			        SaveManager.saveGame(player, currentFloor);
			        goToMainMenu();
			    }

			    
		}
		}
			
	
	
	private void enemyTurn()
	{
		if(currentEnemy==null)
		{
			return;
		}
		currentEnemy.processDot();
	    int rawDamage = currentEnemy.getAttack();
	    int damageTaken=player.takeDamage(rawDamage);

	    System.out.println(
	        "[COMBAT DEBUG] Enemy Attack Breakdown -> " +
	        "Raw: " + rawDamage +
	        "Defense: "+player.getDefense()+
	        " | Final: " + damageTaken);
		 addLog(currentEnemy.getType()+" hits you for " + damageTaken);
		    if (player.getHP() <= 0) 
		    {
		        addLog("You died!");
		        Timer t = new Timer(800, e ->
		        {
		        	JOptionPane.showMessageDialog(
		                    parentFrame,
		                    "Game Over! You have been defeated.",
		                    "Game Over",
		                    JOptionPane.INFORMATION_MESSAGE);
		        	goToMainMenu();
		        });

		        t.setRepeats(false);
		        t.start();

		        return;
		    }

		    // BACK TO PLAYER TURN
		    playerTurn = true;
		    repaint();
	}
	private void addLog(String message)
	{
		combatLog.add(message);
		if(combatLog.size()>5)
		{
			combatLog.remove(0);
		}
	}
	
	public void openChest()
	{
		LootResult result=ChestLootTable.rollLoot();
		if (result.getGold()>0)
		{
			player.addGold(result.getGold());
		}
		if (result.getItem()!=null)
		{
			player.addItem(result.getItem());
		}
		
		currentRoom.openChest();
		
		addLog(result.getMessage());
		currentRoom.openChest();
		startExplore();
		repaint();
	}
	public void useItem(Item item, int index)
	{
		if(previousState==GameState.COMBAT &&!playerTurn)
		{
			return;
		}
		boolean isCombat=(previousState==GameState.COMBAT);
		
		if(item.getType().equals("Heal"))
		{
			int oldHP=player.getHP();
			System.out.println("Old HP"+ oldHP);
			int healed= player.healed(item.getValue());
			addLog("Used "+item.getName()+ " + "+healed+"HP");
			
			player.getInventory().remove(index);
			System.out.println("HP after heal: "+ player.getHP());
			repaint();
			if(isCombat)
			{
				state=GameState.COMBAT;
				playerTurn=false;
				
				Timer timer = new Timer(500, e -> enemyTurn());
	            timer.setRepeats(false);
	            timer.start();
			}
			else
			{
				state=GameState.EXPLORE;
			}
		}
		else if(item.isEquipable())
		{
			player.equip(item);
			addLog("Equipped " + item.getName());
			state=GameState.INVENTORY;
		}
		
		
		repaint();
	}
	public String getItemName(Item item)
	{
		return (item==null)?"None": item.getName(); 
	}
	
	
	public Player getPlayer()
	{
		return player;
	}
	
	public int getFloor()
	{
		return floor;
	}
	
	public void setPlayer(Player player)
	{
		this.player =player;
	}
	public void setFloor(int floor)
	{
		this.floor= floor;
	}
	private void generateDungeon()
	{
	    dungeon = new Room[rows][cols];

	    enemiesPlaced = 0;
	    chestCount = 3;

	    for (int r = 0; r < rows; r++)
	    {
	        for (int c = 0; c < cols; c++)
	        {
	            dungeon[r][c] = new Room();
	        }
	    }
	    int centerRow = rows / 2;
 	    int centerCol = cols / 2;
 	    dungeon[centerRow][centerCol].setShop(true);
	    // optional: increase difficulty per floor
	    int enemyCount = 3 + (currentFloor / 5);
	   
	    while (enemiesPlaced < enemyCount)
	    {

	    	 int r = rand.nextInt(rows);
	         int c = rand.nextInt(cols);
	        if (!dungeon[r][c].hasEnemy() &&!dungeon[r][c].hasChest()&&!dungeon[r][c].hasShop()&& !(r==START_ROW &&c==START_COL))
	        {
	            int roll = rand.nextInt(3);

	            Enemy enemy;
	            if (roll == 0) enemy = EnemyFactory.createGoblin();
	            else if (roll == 1) enemy = EnemyFactory.createSkeleton();
	            else enemy = EnemyFactory.createOrc();

	            // scale enemy with floor
	            enemy.setHP(enemy.getHP() + currentFloor * 2);
	            enemy.setAttack(enemy.getAttack() + currentFloor / 2);

	            dungeon[r][c].setEnemy(enemy);
	            enemiesPlaced++;
	        }
	    }

	    while (chestCount > 0)
	    {
	    	 int r = rand.nextInt(rows);
	         int c = rand.nextInt(cols);

	        if (!dungeon[r][c].hasEnemy() && !dungeon[r][c].hasChest())
	        {
	            dungeon[r][c].setChest(true);
	            chestCount--;
	        }
	    }


	    // FLOOR BOSS SPAWN
	    if (currentFloor % 5 == 0)
	    {
	    	 int r = rand.nextInt(rows);
	         int c = rand.nextInt(cols);
	         if(!dungeon[r][c].hasEnemy() &&!dungeon[r][c].hasChest()&&!dungeon[r][c].hasShop()) {
	        Enemy boss = EnemyFactory.createMinotaur();
	        boss.setHP(50 + currentFloor * 5);
	        boss.setAttack(8 + currentFloor);

	        dungeon[r][c].setEnemy(boss);
	    }
	    }
	}
	private void checkFloorClear()
	{
	    for (int r = 0; r < rows; r++)
	    {
	        for (int c = 0; c < cols; c++)
	        {
	            if (dungeon[r][c].hasEnemy())
	            {
	                return; // still enemies left
	            }
	        }
	    }

	    // FLOOR COMPLETE
	    currentFloor++;
	    player.scaleMaxHP(currentFloor);
	    player.setHP(player.getMaxHP());
	    addLog("Floor " + (currentFloor - 1) + " cleared!");

	    if (currentFloor > MAX_FLOOR)
	    {
	        addLog("YOU WIN! You cleared the dungeon.");
	        state = GameState.EXPLORE;
	        return;
	    }

	    generateDungeon();
	    resetPlayerPosistion();

	    addLog("Descending to floor " + currentFloor);
	}
	private void resetPlayerPosistion()
	{
		player.setPosition(START_ROW, START_COL);
	}
	private void goToMainMenu()
	{
		MainMenuPanel menu= new MainMenuPanel(parentFrame);
		parentFrame.setContentPane(menu);
		parentFrame.revalidate();
		parentFrame.repaint();
	}
}
	

