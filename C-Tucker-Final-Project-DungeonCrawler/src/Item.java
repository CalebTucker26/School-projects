import java.io.Serializable;
public class Item implements Serializable {

	private String name;
	private String type;
	private String slot;
	private int value;
	private int price;
	private boolean equipable;
	public Item()
	{
		name="";
		type="";
		value=0;
		equipable=false;
	}
	
	public Item(String name, String type, String slot, int value, int price, boolean equipable)
	{
	    this.name = name;
	    this.type = type;
	    this.slot = slot;
	    this.value = value;
	    this.price=price;
	    this.equipable = equipable;
	}
	
	public boolean isEquipable()
	{
		return equipable;
	}
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getValue()
	{
		return value;
	}
	public int getPrice()
	{
		return price;
	}
	public String getSlot()
	{
		return slot;
	}
	
	
	
	
	
}
