import java.io.Serializable;
public class SaveData implements Serializable{

	private static final long serialVersionUID=1L;
	
	public Player player;
	public int floor;
	
	public SaveData(Player player, int floor)
	{
		this.player=player;
		this.floor=floor;
	}
}
