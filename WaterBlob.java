import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

class WaterBlob extends PhysEl
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
        double nx, ny;
        
        double px=x, py=y;
        ny=y+gravity;
        nx=x;


        for (PhysEl e : PhysEl.physElements)
        {
            if(e.type()=="L")
            {
                Line l = (Line) e;
                int symod=0;
                int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)x, (int)y);
                int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)nx, (int)ny);
                
                
                int sign1=0, sign2=1;
                
                if(s1!=0 && s2!=0)
                {
                    sign1=Math.abs(s1)/s1;
                    sign2=Math.abs(s2)/s2;
                }

                
                
                
                ///particle should cross line boundary
                if(sign1!=sign2)
                {                    
                
                    double angle=l.angle;
                    ///y component to move down = cos

                    double downtheplane=Math.cos(angle)*gravity;
                    
                    double xmov=Math.cos(angle)*downtheplane;
                    //double ymov=Math.sin(angle)*downtheplane;
                    double ymov = l.gradient * (xmov + x) + l.con - 1;
                    //y=mx+c;
                    //System.out.println(l.gradient);
                    
                    if(angle < 0)
                    {
                        xmov=-xmov;
                    }
                    
                    //System.out.println(angle);
                    
                    ///calculate byslope of line
                    ///itll work fine if we do more checks to check this hasnt screwed up 
                    nx=x + xmov;
                    ny=ymov;
                    
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
