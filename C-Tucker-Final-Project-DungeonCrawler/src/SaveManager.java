import java.io.*;
public class SaveManager {
	
	public static final String SAVE_FILE="save.dat";
	
	public static void saveGame(Player player, int floor)
	{
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE)))
		{
			SaveData data= new SaveData(player, floor);
			out.writeObject(data);
			System.out.println("Game saved!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static SaveData loadGame()
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE)))
        {
            return (SaveData) in.readObject();
        }
        catch (Exception e)
        {
            System.out.println("No save file found.");
            return null;
        }
    }

    public static boolean saveExists()
    {
        return new File(SAVE_FILE).exists();
    }
}
