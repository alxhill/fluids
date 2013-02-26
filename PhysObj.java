import java.util.ArrayList;
abstract class PhysObj extends Element
{
	static public ArrayList<PhysObj> physElements = new ArrayList<PhysObj>();
	
	public String type;

	abstract public void tick();
	
	static public float gravity=9.8f;
	
	static public double simulationTime=0;
	static public double tickTime=20.0; 
	///1 tick every 20 ms
	
	static void physTick()
	{
		int ticknum=0;
		ticknum = (int)(simulationTime / tickTime);
		System.out.println(ticknum);
		for (PhysObj e : PhysObj.physElements)
		{
			for(int i=0; i<ticknum; i++)
				e.tick();
		}	
	}
	
	public String queryType()
	{
		return type;
	}
	
	public void physPush()
	{
		physElements.add(this);
	}



}