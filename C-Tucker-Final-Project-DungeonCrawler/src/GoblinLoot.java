
public class GoblinLoot {
	
	public static Item weapon()
    {
        return new Item("Rusty Dagger", "WEAPON", "Weapon", 3, 1, true);
    }

    public static Item head()
    {
        return new Item("Goblin Hood", "ARMOR", "Head", 1, 1, true);
    }

    public static Item chest()
    {
        return new Item("Torn Leather Vest", "ARMOR", "Chest", 1, 1, true);
    }

    public static Item legs()
    {
        return new Item("Cloth Pants", "ARMOR", "Legs", 1, 1, true);
    }
    public static Item shield()
    {
    	return new Item("Wood Shield", "SHIELD","Shield",1,1,true);
    }
}
