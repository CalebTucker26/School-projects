
public class SkeletonLoot {

	public static Item weapon()
    {
        return new Item("Bone Sword", "WEAPON", "Weapon", 5,7, true);
    }

    public static Item head()
    {
        return new Item("Skull Helm", "ARMOR", "Head", 2, 7,true);
    }

    public static Item chest()
    {
        return new Item("Bone Rib Armor", "ARMOR", "Chest", 2,7, true);
    }

    public static Item legs()
    {
        return new Item("Bone Greaves", "ARMOR", "Legs", 2,7, true);
    }
    public static Item shield()
    {
    	return new Item("Bone Shield", "SHIELD","Shield",2,7,true);
    }
}
