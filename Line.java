import javax.swing.*;
import java.awt.*;

class Line extends PhysObj
{

    int[] lx; int[] ly;

    Line()
    {
        type="L";
        x=0; y=0; rot=0;
        lx = new int[2];
        ly = new int[2];
    }

    public void tick()
    {
        ///lines do not need to do anything in a tick

    }

    public void render(Graphics2D g)
    {
        //g.fillOval(0, 0, 50, 50);
    }



}