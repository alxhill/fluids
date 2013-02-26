import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

class WaterBlob extends PhysObj
{

    WaterBlob()
    {
        type="WB";
        x=0; y=0; rot=0;
    }

    public int side(int l1x, int l1y, int l2x, int l2y, int px, int py)
    {
        int side=(l2x - l1x) * (py - l1y) - (l2y - l1y) * (px - l1x);
        return side;
    }

    public void tick()
    {
        int nx, ny;
        ny=y+(int)gravity;
        nx=x;
        
        for (PhysObj e : PhysObj.physElements)
        {
            if(e.queryType()=="L")
            {
                Line l = (Line) e;
                                
                int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], x, y);
                int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], nx, ny);
                
                int sign1=Math.abs(s1)/s1;
                int sign2=Math.abs(s2)/s2;
                
                if(sign1!=sign2)
                {
                    nx=x;
                    ny=y;
                }                
            }        
        }
        
        
        x=nx;
        y=ny;
    }

    public void render(Graphics2D g)
    {
        g.fillOval(0, 0, 5, 5);
    }

}
