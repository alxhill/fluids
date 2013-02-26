import javax.swing.*;

class Play implements Runnable
{
    private Seesaw seesaw = new Seesaw();

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
        w.add(seesaw);
        w.pack();
        w.setLocationByPlatform(true);
        w.setVisible(true);
    }

    // Create the seesaw and animate it every second
    void animate()
    {
        for (int i=0; i<100; i++)
        {
            try { Thread.sleep(100); }
            catch (InterruptedException interruption) { }
            if (i%8 < 4) seesaw.update(0.1);
            else seesaw.update(-0.1);
        }
    }
}
