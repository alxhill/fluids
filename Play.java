import javax.swing.*;

class Play implements Runnable
{
    private View view = new View();
    private Seesaw seesaw1 = new Seesaw(50, 100, -0.2);
    private Seesaw seesaw2 = new Seesaw(100,200, 0.2);
	private double time;

	Play()
	{
		time=System.nanoTime();
	}

    public static void main(String[] args)
    {
        Play program = new Play();
        SwingUtilities.invokeLater(program);
		//time=System.nanoTime();

        program.animate();
    }

    // Create the main window
    public void run()
    {
        JFrame w = new JFrame();
        w.setDefaultCloseOperation(w.EXIT_ON_CLOSE);
        //view.addElement(seesaw1);
        //view.addElement(seesaw2);
		seesaw1.push();
		seesaw2.push();

		WaterBlob wa = new WaterBlob();
		wa.push();
		wa.physPush();

        w.add(view);
        w.pack();
        w.setLocationByPlatform(true);
        w.setVisible(true);
    }

	void physHelper()
	{
		double ctime=System.nanoTime(); ///current time, does not work absolute apparently
		double emtime=0;

		double etime=ctime-time; ///elapsed time
		emtime=etime*0.000001; ///convert to ms //hmm
		time=ctime;


		PhysObj.simulationTime+=emtime;

		PhysObj.physTick();

		while(PhysObj.simulationTime>PhysObj.tickTime)
		{
			PhysObj.simulationTime-=PhysObj.tickTime;
		}

	}

    // Create the seesaw1 and animate it every second
    void animate()
    {
        for (int i=0; i<100; i++)
        {
            try { Thread.sleep(20); }
            catch (InterruptedException interruption) { }
            if (i%8 < 4)
            {
                seesaw1.update(0.1);
                seesaw2.update(-0.1);
            }
            else
            {
                seesaw1.update(-0.1);
                seesaw2.update(0.1);
            }
            view.tick();
			physHelper();
        }
    }
}
