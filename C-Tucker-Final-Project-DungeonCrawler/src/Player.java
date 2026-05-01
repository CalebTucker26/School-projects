import java.util.ArrayList;
import java.util.Random;
import java.io.Serializable;

public class Player implements Serializable{
	
	private static final long serialVersionUID=1L;
	private ArrayList<Item> inventory= new ArrayList<>();
	private int row;
	private int col;
	private int hp=100;
	private int maxHP=100;
	private int defense=0;
	private int attack=10;
	private int gold=0;
	private boolean abilityUsed=false;
	private String playerClass;
	private Item helmArmor;
	private Item chestArmor;
	private Item legArmor;
	private Item weapon;
	private Item shield;
	
	
	public Player()
	{
		row=0;
		col=0;
		hp=100;
		maxHP=100;
		defense=0;
		attack=10;
		gold=0;
		helmArmor=null;
		chestArmor=null;
		legArmor=null;
		weapon=null;
		shield=null;
	}
	public Player(int row, int col,int maxHP, int hp, int attack, int defense, int gold, Item helmArmor, Item chestArmor, Item legArmor, Item weapon, Item shield)
	{
		this.row=row;
		this.col=col;
		this.maxHP=maxHP;
		this.hp=hp;
		this.defense=defense;
		this.attack=attack;
		this.gold=gold;
		this.helmArmor=helmArmor;
		this.chestArmor=chestArmor;
		this.legArmor=legArmor;
		this.weapon=weapon;
		this.shield=shield;
	}
	
	public static Player createKnight(int row,int col)
	{
		return new Player(row,col,100,100, 10,0,20,null,null,null,null,null);
		
	}
	
	public static Player createRanger(int row, int col)
	{
		return new Player(4,2,100,100, 10,0,20,null,null,null,null,null);
	}
	public static Player createWizard(int row, int col)
	{
		return new Player(4,2,100,100, 10,0,20,null,null,null,null,null);
	}
	
	public void move(int dr, int dc)
	{
		row+=dr;
		col+=dc;
	}
	
	public int getRow() {return row;}
	public int getCol()	{return col;}
	public void setRow(int row)
	{
		this.row=row;
	}
	
	public void setCol(int col)
	{
		this.col=col;
	}
	public Item getHead()
	{
		return helmArmor;
	}
	public Item getChest()
	
	{
		return chestArmor;
	}
	public Item getLegs()
	{
		return legArmor;
	}
	public Item getWeapon()
	{
		return weapon;
	}
	public Item getSheild()
	{
		return shield;
	}
	public int getDefense()
	{
		return defense;
	}
	
	public int takeDamage(int dmg)
	{
		double reduction =defense*.05;
		int finalDamage= (int)(dmg*(1.0-reduction));
		if(finalDamage<1)
		{
			finalDamage=0;
		}
		hp-=finalDamage;
		return finalDamage;
	}
	public int getHP()
	{
		return hp;
	}
	public int getAttack()
	{
		return attack;
	}
	
	public void addItem(Item item)
	{
		inventory.add(item);
		
	}
	
	public ArrayList<Item> getInventory()
	{
		return inventory;
	}
	
	public int healed(int amount)
	{
		int oldHP=hp;
		hp=Math.min(maxHP, hp+amount);
		return hp-oldHP;
	}
	
	public void equip(Item item)
	{
		if(!item.isEquipable())
		{
			return;
		}
		
		switch (item.getSlot())
		{
		case"Head":
			helmArmor=item;
			break;
		case "Chest":
			chestArmor=item;
			break;
		case "Legs":
			legArmor=item;
			break;
		case"Weapon":
			weapon=item;
			break;
		case"Shield":
			shield=item;
			break;
		}
		recalculateStats();
	}
	
	private void recalculateStats()
	{
		defense=getDefense();
		attack=getAttack();
		
		if(helmArmor !=null)
		{
			defense= helmArmor.getValue();
		}
		if(chestArmor !=null)
		{
			defense=chestArmor.getValue();
		}
		if(legArmor!=null)
		{
			defense=legArmor.getValue();
		}
		if(weapon !=null)
		{
			attack=weapon.getValue();
		}
		if(shield !=null)
		{
			attack=shield.getValue();
		}
	}
	public int getGold()
	{
	    return gold;
	}

	public void addGold(int amount)
	{
	    gold += amount;
	}

	public boolean spendGold(int amount)
	{
	    if (gold >= amount)
	    {
	        gold -= amount;
	        return true;
	    }
	    return false;
	}
	
	public String getPlayerClass()
	{
		return playerClass;
	}
	public void setPlayerClass(String playerClass)
	{
		this.playerClass=playerClass;
	}
	
	public int useAbility(Enemy enemy)
	{
	    if (playerClass.equalsIgnoreCase("Knight"))
	    {
	        // SMITE: execute if enemy < 30% HP
	        if (enemy.getHP() <= enemy.getMaxHP() * 0.3)
	        {
	            int dmg = enemy.getHP(); // kill
	            enemy.takeDamage(dmg);
	            return dmg;
	        }
	        return 0; // failed
	    }
	    else if (playerClass.equalsIgnoreCase("Ranger"))
	    {
	        // ARROW RAIN: apply damage over time
	        enemy.applyDot(3, 3); // 3 turns, 3 dmg each
	        return 0;
	    }
	    else if (playerClass.equalsIgnoreCase("Wizard"))
	    {
	        // FIREBALL: big burst damage
	        int dmg = 15 + new Random().nextInt(6);
	        enemy.takeDamage(dmg);
	        return dmg;
	    }

	    return 0;
	}
	public boolean hasUsedAbility()
	{
	    return abilityUsed;
	}

	public void setAbilityUsed(boolean used)
	{
	    this.abilityUsed = used;
	}
	public void setPosition(int row, int col)
	{
	    this.row = row;
	    this.col = col;
	}
	public int getMaxHP()
	{
		return maxHP;
	}
	public void scaleMaxHP(int floor)
	{
		int baseHP=100;
		maxHP=baseHP+(floor*2);
		if(hp>maxHP)
		{
			hp=maxHP;
		}
	}
	public void setHP(int hp)
	{
		this.hp=hp;
	}
}
