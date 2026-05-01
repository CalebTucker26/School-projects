import java.util.Random;

public class LootTable {
	
	private static Random rand = new Random();

    public static LootResult rollLoot(Enemy enemy)
    {
        String type = enemy.getType();
        int roll = rand.nextInt(5);

        if (type.equals("Goblin"))
        {
        	if (roll == 0)
        	{	
                return new LootResult(null, 5, "Goblin dropped gold");
        	}
            if (roll == 1)
            {
                return new LootResult(GoblinLoot.weapon(), 0, "Goblin dropped weapon");
            }
            if (roll == 2)
            {
                return new LootResult(GoblinLoot.head(), 0, "Goblin dropped armor");
            }
            else if (roll==3)
            {
                return new LootResult(GoblinLoot.chest(), 0, "Goblin dropped armor");
            }
            else if(roll==4)
            {
            	return new LootResult(GoblinLoot.shield(), 0, "Goblin dropped shield");
            }
            else
            {
            	 return new LootResult(GoblinLoot.legs(), 0, "Goblin dropped armor");
            }
        }
        else if (type.equals("Skeleton"))
        {
            if (roll == 0)
            {
                return new LootResult(null, 10, "Skeleton dropped gold");
            }
            else if (roll == 1)
            {
            	return new LootResult(SkeletonLoot.weapon(),0,"Skeleton dropped armor");
            }
            else if (roll == 2)
            {
            	return new LootResult(SkeletonLoot.head(),0,"Skeleton dropped armor");
            }
            else if(roll ==3)
            {
            	return new LootResult(SkeletonLoot.chest(),0,"Skeleton dropped armor");
            }
            else
            {
            	return new LootResult(SkeletonLoot.legs(),0,"Skeleton dropped armor");
            }
        }
        else if (type.equals("Orc"))
        {
            if (roll == 0)
            {
            	return new LootResult(null, 20, "Orc dropped gold");
            }
            else if (roll == 1)
            {
            	return new LootResult(OrcLoot.weapon(),0,"Orc dropped armor");
            }
            else if (roll == 2)
            {
            	return new LootResult(OrcLoot.head(),0,"Orc dropped armor");
            }
            else if (roll==3)
            {
            	return new LootResult(OrcLoot.chest(),0,"Orc dropped armor");
            }
            else
            {
            	return new LootResult(OrcLoot.legs(),0,"Orc dropped armor");
            }
        }

        return null;
    }
}
