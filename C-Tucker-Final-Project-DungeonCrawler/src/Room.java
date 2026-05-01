
public class Room {

	

	private Enemy enemy;
	private boolean hasChest;
	private boolean chestOpened;
	private boolean hasShop;
	
	public void setEnemy(Enemy enemy)
	{
		this.enemy=enemy;
	}
	
	public Enemy getEnemy()
	{
		return enemy;
	}
	
	public boolean hasEnemy()
	{
		return enemy !=null;
	}
	
	public void setChest(boolean value)
	{
		this.hasChest=value;
	}
	
	public boolean hasChest()
	{
		return hasChest;
	}
	public boolean isChestOpened()
	{
		return chestOpened;
	}
	public void openChest()
	{
		chestOpened=true;
	}

	public boolean hasShop()
	{
		return hasShop;
	}
	public void setShop(boolean value)
	{
		this.hasShop=value;
	}
}
