import javax.swing.*;

class Play implements Runnable
{
    private View view = new View();
    private Seesaw seesaw1 = new Seesaw(50, 100, -0.2);
    private Seesaw seesaw2 = new Seesaw(100,200, 0.2);

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
        //view.addElement(seesaw1);
        //view.addElement(seesaw2);
		seesaw1.push();
		seesaw2.push();
        w.add(view);
        w.pack();
        w.setLocationByPlatform(true);
        w.setVisible(true);
    }

    // Create the seesaw1 and animate it every second
    void animate()
    {
        for (int i=0; i<100; i++)
        {
            try { Thread.sleep(100); }
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
        }
    }
}
