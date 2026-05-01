import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	
	private int hp;
	private int attack;
	private int maxHP;
	private int dotDamage = 0;
	private int dotTurns = 0;
	private String type;
	private Image image;
	
	public Enemy()
	{
		hp=0;
		attack=0;
		maxHP=0;
		type="";
		image=null;
	}
	
	
	public Enemy(String type,int hp,int maxHP, int attack)
	{
		this.type=type;
		this.hp= hp;
		this.maxHP=maxHP;
		this.attack =attack;
		
	}
	public Image getImage()
	{
		return image;
	}
	public void setImage(String path)
	{
		this.image = new ImageIcon(path).getImage();
		
	}
	public void setAttack(int attack)
	{
		this.attack=attack;
	}
	public int getAttack()
	{
		return attack;
	}
	
	public void takeDamage(int dmg)
	{
		hp-=dmg;
	}
	public int getHP()
	{
		return hp;
	}
	public int getMaxHP()
	{
		return maxHP;
	}
	public void setHP(int hp)
	{
		this.hp=hp;
	}
	public String getType()
	{
		return type;
	}
	public boolean isAlive()
	{
		return hp>0;
	}

	public void applyDot(int turns, int damage)
	{
	    this.dotTurns = turns;
	    this.dotDamage = damage;
	}
	public void processDot()
	{
	    if (dotTurns > 0)
	    {
	        takeDamage(dotDamage);
	        dotTurns--;
	    }
	}
}
