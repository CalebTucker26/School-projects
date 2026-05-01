public class LootResult {

	 private Item item;
	    private int gold;
	    private String message;

	    public LootResult(Item item, int gold, String message)
	    {
	        this.item = item;
	        this.gold = gold;
	        this.message = message;
	    }

	    public Item getItem() { return item; }
	    public int getGold() { return gold; }
	    public String getMessage() { return message; }
}