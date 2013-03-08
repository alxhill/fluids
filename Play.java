import javax.swing.*;
import java.awt.event.*;

class Play implements Runnable, MouseListener, MouseMotionListener
{
    private View view = new View();
    
    Play()
    {
        view.addMouseListener(this);
        view.addMouseMotionListener(this);
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

        for(int i=0; i<120; i++)
        {
            WaterBlob wa = new WaterBlob();
            wa.setXY((int)(i*5.5), 0);
            wa.push();
            wa.physPush();
        }

        WaterBlob wa = new WaterBlob();
        wa.setXY(495, 0);
        wa.push();
        wa.physPush();

        Ball b = new Ball();
        b.push();
        b.physPush();

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
            view.tick();
            PhysEl.physTick();
        }
    }

    /*** Mouse Input Functions ***/

    private Boolean drawing = false;
    Line beingDrawn;

    // screw you java and your shitty requirements
    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e)
    {
        if (drawing)
        {
            beingDrawn.lx[0] = e.getX();
            beingDrawn.ly[0] = e.getY();
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        if (drawing)
        {
            beingDrawn.physPush();
            drawing = false;
        }
        else
        {
            int x = e.getX(), y = e.getY();
            beingDrawn = new Line(x, y, x, y);
            beingDrawn.push();
            drawing = true;
        }
    }


}
