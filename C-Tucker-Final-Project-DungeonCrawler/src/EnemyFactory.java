
public class EnemyFactory {
	
	public static Enemy createGoblin()
	{
		Enemy e= new Enemy("Goblin",30,30,8);
		e.setImage("Resources/goblin.png");
		return e;
		// <a href="https://www.freepik.com/free-vector/blue-goblin-troll-cartoon-character-sticker_18245844.htm">Image by brgfx on Freepik</a> GOBLIN image
	}
	public static Enemy createSkeleton()
	{
		Enemy e = new Enemy("Skeleton",50,50,10);
		e.setImage("Resources/skeleton.png");
		return e;
	}
	public static Enemy createOrc()
	{
		Enemy e = new Enemy("Orc",80,80,15);
		e.setImage("Resources/orc.png");
		return e;
		// <a href="https://www.freepik.com/free-vector/orc-cartoon-character-isolated_30551651.htm">Image by brgfx on Freepik</a> ORC image
	}
	public static Enemy createMinotaur()
	{
		
		Enemy minotaur = new Enemy("Minotaur",120,120,18);
		minotaur.setImage("Resources/minotaur.png");
		return minotaur;
	}
}