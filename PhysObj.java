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

    static private double time = System.nanoTime();

    static void physTick()
    {

        double ctime = System.nanoTime(); ///current time, does not work absolute apparently
        double emtime = 0;

        double etime = ctime-time; ///elapsed time
        emtime = etime*0.000001; ///convert to ms //hmm
        time = ctime;


        simulationTime += emtime;

        int ticknum = 0;
        ticknum = (int)(simulationTime / tickTime);
        for (PhysObj e : physElements)
        {
            for(int i = 0; i<ticknum; i++)
                e.tick();
        }

        while(simulationTime>tickTime)
        {
            simulationTime-=tickTime;
        }
    }

    public String queryType()
    {
        return type;
    }

    public void push()
    {
        super.push();
        physElements.add(this);
    }
}
