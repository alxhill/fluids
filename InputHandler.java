import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;


public class InputHandler implements MouseListener, MouseMotionListener, ActionListener
{
    private enum Mode {
        ball, line, drawLine, water
    };
    
    private Mode mode = Mode.ball;
    private Boolean drawing = false;
    Line beingDrawn;
    
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (mode == Mode.drawLine)
        {
            beingDrawn.setLine(beingDrawn.lx[0], beingDrawn.ly[0], e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (mode == Mode.drawLine)
        {
            beingDrawn.physPush();
            mode = Mode.line;
        }
        else if (mode == Mode.ball)
        {
			WaterBlob b = new WaterBlob();
			b.setXY(e.getX(), e.getY());
			b.push();
			b.physPush();
			b.setWater(false);
			b.setMass(1);
        }
		else if (mode == Mode.water)
		{
			WaterBlob b = new WaterBlob();
			b.setXY(e.getX(), e.getY());
			b.push();
			b.physPush();
		}
        else
        {
            int x = e.getX(), y = e.getY();
            beingDrawn = new Line(x, y, x, y);
            beingDrawn.push();
            mode = Mode.drawLine;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton) e.getSource();
        if (button.getText() == "Add balls")
        {
            // thing
        }
        //System.out.println(e);
        if (mode == Mode.water)
            mode = Mode.line;
        else if(mode == Mode.line)
            mode = Mode.ball;        
		else if(mode == Mode.ball)
            mode = Mode.water;
        
    }

}
