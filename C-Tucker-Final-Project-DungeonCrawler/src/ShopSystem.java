import java.util.ArrayList;

public class ShopSystem {

	ArrayList<Item> items= new ArrayList<>();
	
	public ShopSystem()
	{
		
	       
	        items.add(new Item("Weak Healing Potion", null, null, 10, 5, false));
	        items.add(new Item("Healing Potion", "Heal", null, 25, 12, false));
	        items.add(new Item("Greater Healing Potion", null, null, 50, 25, false));

	       
	        items.add(new Item("Revive Potion", "REVIVE", null, 1, 75, false));


	        items.add(new Item("Reinforced Hood", "ARMOR", "Head", 2, 12, true));
	        items.add(new Item("Reinforced Leather Vest", "ARMOR", "Chest", 2, 15, true));
	        items.add(new Item("Reinforced Leather Legs", "ARMOR", "Legs", 2, 15, true));

	        items.add(new Item("Hunter Blade", "WEAPON", "Weapon", 4, 18, true));

	      

	        items.add(new Item("Iron Helmet", "ARMOR", "Head", 4, 20, true));
	        items.add(new Item("Iron Chestplate", "ARMOR", "Chest", 4, 25, true));
	        items.add(new Item("Iron Greaves", "ARMOR", "Legs", 4, 25, true));

	        items.add(new Item("Steel Sword", "WEAPON", "Weapon", 6, 30, true));

	   
	}
	 public ArrayList<Item> getItems()
	    {
	        return items;
	    }
}
