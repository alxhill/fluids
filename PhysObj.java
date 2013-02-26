import java.util.ArrayList;
abstract class PhysObj extends Element
{
	static public ArrayList<PhysObj> physElements = new ArrayList<PhysObj>();
	
	public String type;

	abstract public void tick();
	
	static public float gravity=9.8f;
	
	public String queryType()
	{
		return type;
	}
	
	public void physPush()
	{
		physElements.add(this);
	}



}