import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseHandler implements MouseListener, MouseMotionListener
{
    private enum Mode {
        BALL, LINE, DRAWINGLINE
    };
    
    private Mode mode = Mode.BALL;
    private Boolean drawing = false;
    Line beingDrawn;
    
    @Override
    public void mouseDragged(MouseEvent arg0) {}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (mode == Mode.DRAWINGLINE)
        {
            beingDrawn.setLine(beingDrawn.lx[0], beingDrawn.ly[0], e.getX(), e.getY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (mode == Mode.DRAWINGLINE)
        {
            beingDrawn.physPush();
            mode = Mode.LINE;
        }
        else if (mode == Mode.BALL)
        {
            Ball b = new Ball(e.getX(), e.getY(), 10);
            b.push();
            b.physPush();
        }
        else
        {
            int x = e.getX(), y = e.getY();
            beingDrawn = new Line(x, y, x, y);
            beingDrawn.push();
            mode = Mode.DRAWINGLINE;
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

}
