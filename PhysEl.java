import java.util.ArrayList;

abstract class PhysEl extends Element
{
    static public ArrayList<PhysEl> physElements = new ArrayList<PhysEl>();


    abstract public void tick(int id);

    static public double gravity=0.098 * 4;

    static public double simulationTime=0;
    static public double tickTime=40.0;

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

        for (PhysEl e : physElements)

        //for (PhysEl e : physElements)
        for(int n=0; n<physElements.size(); n++)
        {
            for(int i = 0; i<ticknum; i++)
                physElements.get(n).tick(i);
            ///if out of bounds, remove element n
        }

        while(simulationTime>tickTime)
        {
            simulationTime-=tickTime;
        }
    }


    ///would like to keep these two separated because rendering water will not be a straight per element render

    public void physPush()
    {
        physElements.add(this);
    }

    public void push()
    {
        super.push();
    }
}
