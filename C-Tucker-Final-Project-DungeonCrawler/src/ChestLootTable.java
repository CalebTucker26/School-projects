import java.util.Random;

public class ChestLootTable {
	
	private static Random rand = new Random();

    public static LootResult rollLoot()
    {
        int roll = rand.nextInt(8);

        if (roll == 0)
        {
            return new LootResult(null, 10, "Chest Contained gold!");
        }
        else if (roll == 1)
        {
            return new LootResult(new Item("Minor Healing Potion", "Heal", null, 20, 5, false),0, "Found Health Potion");
        }
        else if (roll == 2)
        {
            return new LootResult(new Item("Steel Sword", "WEAPON", "Weapon", 2,6,true),0,"Found Weapon");
        }
        else if (roll == 3)
        {
            return new LootResult(new Item("Iron Helmet", "ARMOR", "Head", 3, 2, true),0,"Found Armor");
        }
        else if (roll==4) 
        {
            return new LootResult(new Item("Iron Chestplate", "ARMOR", "Chest", 3, 2, true),0,"Found Armor");
        }
        else if(roll==5)
        {
        	return new LootResult(new Item("Iron Greaves", "ARMOR", "Legs", 3, 2, true), 0 ,"Found armor!");
        }
        else if(roll==6)
        {
        	return new LootResult(new Item("Basic Shield", "SHIELD","Shield",3,2,true),0,"Found Shield");
        }
        else 
        {
        	return null;
        }
    }
    
}
