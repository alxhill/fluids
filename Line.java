import javax.swing.*;
import java.awt.*;

class Line extends PhysEl
{

    int[] lx; int[] ly;

    public double angle;

    public double gradient;

    public double con;

    public double length;

    Line()
    {
        x=0; y=0; rot=0;
        lx = new int[2];
        ly = new int[2];
    }

    Line(int x1, int y1, int x2, int y2)
    {
        this();
        setLine(x1, y1, x2, y2);
    }

    public void setLine(int x1, int y1, int x2, int y2)
    {
        int dy = y2 - y1;
        int dx = x2 - x1;
        gradient=(double)dy/dx;
        angle=Math.atan2(dy, dx);
        con=y1 - gradient*x1;
        length = Math.sqrt(dy*dy + dx*dx);
        lx[0]=x1;
        ly[0]=y1;
        lx[1]=x2;
        ly[1]=y2;
    }

    public void tick(int id)
    {

    }

    public void render(Graphics2D g)
    {
        g.drawLine(lx[0], ly[0], lx[1], ly[1]);
    }
}
