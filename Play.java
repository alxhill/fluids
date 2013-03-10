import javax.swing.*;

import java.awt.event.*;

class Play implements Runnable
{
    private View view = new View();
    
    Play()
    {
        InputHandler inputHandler = new InputHandler();
        view.addMouseListener(inputHandler);
        view.addMouseMotionListener(inputHandler);
        
        JButton button = new JButton("Toggle ball/water/line");
        button.addActionListener(inputHandler);
        view.add(button);
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
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add button for control
        

        Line lin = new Line();
        lin.push();
        lin.setLine(10, 260, 380, 380);
        lin.physPush();

        Line lin2 = new Line();
        lin2.push();
        lin2.setLine(280, 380, 600, 260);
        lin2.physPush();

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

        w.add(view);
        w.pack();
        w.setLocationByPlatform(true);
        w.setVisible(true);
    }

    void animate()
    {
		while(true)
        {
            try { Thread.sleep(20); }
            catch (InterruptedException interruption) { }
            view.tick();
            PhysEl.physTick();
        }
    }

    /*** Mouse Input Functions ***/

    private Boolean drawing = false;
    Line beingDrawn;

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}
    
    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e)
    {
        if (drawing)
        {
            beingDrawn.setLine(beingDrawn.lx[0], beingDrawn.ly[0], e.getX(), e.getY());
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
