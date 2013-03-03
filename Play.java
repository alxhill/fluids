import javax.swing.*;

class Play implements Runnable
{
    private View view = new View();
    private Seesaw seesaw1 = new Seesaw(50, 100, -0.2);
    private Seesaw seesaw2 = new Seesaw(100,200, 0.2);

    Play()
    {

    }

    public static void main(String[] args)
    { 
        Play program = new Play();
        SwingUtilities.invokeLater(program);

        program.animate();
    }

    // Create the main window
    public void run()
    {
        JFrame w = new JFrame();
        w.setDefaultCloseOperation(w.EXIT_ON_CLOSE);
        //seesaw1.push();
        //seesaw2.push();

        Line lin = new Line();
        lin.push();
        lin.setLine(10, 260, 380, 380);
        lin.physPush();
        
        //need to do additional checks to check it isnt screwed up when it moves under waterblob
        Line lin2 = new Line();
        lin2.push();
        //lin2.setLine(180, 480, 500, 100);
        lin2.setLine(280, 380, 600, 260);
        lin2.physPush();
        

        ///currently falls through this
        //Line lin3 = new Line();
        //lin3.push();
        //lin3.setLine(20, 220, 500, 230);
        //lin3.physPush();

        for(int i=0; i<1000; i++)
        {
            WaterBlob wa = new WaterBlob();
            wa.setXY((int)(i*0.55), 0);
            wa.push();
            wa.physPush();
        }
        
        WaterBlob wa = new WaterBlob();
        wa.setXY(495, 0);
        wa.push();
        wa.physPush();

        w.add(view);
        w.pack();
        w.setLocationByPlatform(true);
        w.setVisible(true);
    }

    // Create the seesaw1 and animate it every second
    void animate()
    {
        for (int i=0; i<10000; i++)
        {
            try { Thread.sleep(40); }
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
            PhysEl.physTick();
        }
    }
}
