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
    
    public Boolean changesSide(Line l, double p1x, double p1y, double p2x, double p2y)
    {
        int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p1x, (int)p1y);
        int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p2x, (int)p2y);
        
        int sign1=0, sign2=1;
        
        int[] rp = new int[2];
        
        rp[0]=(int)p2x;
        rp[1]=(int)p2y;
        
        if(s1!=0 && s2!=0)
        {
            sign1=(int)Math.signum(s1);
            sign2=(int)Math.signum(s2);
        }

        if(sign1!=sign2)
            return true;
        else
            return false;
    }
        
    public int[] maxParticleMoveToLine(double p1x, double p1y, double p2x, double p2y, Line l, Boolean slide)
    {
        ///if iswithin line
        int symod=0;
        int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p1x, (int)p1y);
        int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p2x, (int)p2y);
        
        int sign1=0, sign2=1;
        
        int[] rp = new int[2];
        
        rp[0]=(int)p2x;
        rp[1]=(int)p2y;
        
        if(s1!=0 && s2!=0)
        {
            sign1=(int)Math.signum(s1);
            sign2=(int)Math.signum(s2);
        }

        if(sign1!=sign2)
        {
            //System.out.println("hi");
            double downtheplane=Math.cos(l.angle)*gravity;
            
            double xmov;
            if(slide)
            {
                xmov=Math.cos(l.angle)*downtheplane;
                xmov = xmov + x;
            }
            else
            {
                xmov=p2x;
            }
            
            if(l.angle < 0 && slide)
            {
                xmov=(-(xmov - x)) + x;
            }
            double ymov = l.gradient * (xmov) + l.con - 1;

            //nx=x + xmov;
            //ny=ymov;
            rp[0]=(int)xmov;
            rp[1]=(int)ymov;
        }
        
    
        return rp;
    }

    public void tick()
    {
        double nx, ny;
        
        double px=x, py=y;
        ny=y+gravity;
        nx=x;
        int[] newPos = new int[2];
        
        newPos[0]=0;
        newPos[1]=1000000;
        
        Boolean slide=true;
        Boolean first=true;
        
        int nskip=-1;
        for(int i=0; i<PhysEl.physElements.size(); i++)
        {
            if(PhysEl.physElements.get(i).type()=="L" && i!=nskip)
            {
                Line l = (Line) PhysEl.physElements.get(i);
                
                ///changes side of a line
                if(changesSide(l, px, py, nx, ny) && first && i!=nskip)
                {   
                
                    int[] tPos=new int[2];
                    tPos=maxParticleMoveToLine(px, py, nx, ny, l, slide);
                    newPos=tPos;

                    first=false;
                    nskip=i;
                    i=-1;
                    newPos=tPos;
                    slide=false;
                    continue;
                    
                }
                else if(!first && changesSide(l, px, py, newPos[0], newPos[1]))
                {
                    int[] tPos=new int[2];
                    tPos=maxParticleMoveToLine(px, py, newPos[0], newPos[1], l, slide);
                    newPos[1]=Math.min(tPos[1], newPos[1]);
                }
            }
        }
        
        if(!first)
        {
            x=newPos[0];
            y=newPos[1];
        }
        else
        {
            x=nx;
            y=ny;
        }
        

        /*for(int i=0; i<PhysEl.physElements.size(); i++)
        //for (PhysEl e : PhysEl.physElements)
        {
            if(PhysEl.physElements.get(i).type()=="L" && i!=nskip)
            {   
                Line l = (Line) PhysEl.physElements.get(i);
                ///if it changes side of the line, update newpos with value from temporary
                
                if(changesSide(l, px, py, nx, ny))
                {
                    if(slide)
                    {
                        nskip=i;
                        i=-1;
                    }
                    int[] tPos=new int[2];
                    tPos=maxParticleMoveToLine(px, py, nx, ny, l, slide);
                    newPos[0]=tPos[0];
                    newPos[1]=Math.min(tPos[1], newPos[1]);
                    slide=false;
                }
                
                /*for (PhysEl n : PhysEl.physElements)
                {
                    if(n.type()=="L")
                    {   
                        Line lr = (Line) n;
                        int[] newPos2 = new int[2];
                        if(changesSide(lr, px, py, (double)newPos[0], (double)newPos[1]))
                        {
                            newPos2=maxParticleMoveToLine(px, py, (double)newPos[0], (double)newPos[1], lr, true);
                            newPos[1] = Math.min(newPos2[1], newPos[1]);
                        }
                        
                    }
                }*/
                
                
                ///if iswithin line
                /*int symod=0;
                int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)x, (int)y);
                int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)nx, (int)ny);
                
                
                int sign1=0, sign2=1;
                
                if(s1!=0 && s2!=0)
                {
                    sign1=(int)Math.signum(s1);
                    sign2=(int)Math.signum(s2);
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
                    
                    ///go through lines, take f(x) of lines and take minimum - assumes connected OR repeat this process above
                    
                }
                
            }
        }*/

        /*if(slide)
        {
            x=nx;
            y=ny;
        }
        else
        {
            x=newPos[0];
            y=newPos[1];
        }*/
    }
    
    public void setXY(int px, int py)
    {
        x=(double)px;
        y=(double)py;
    
    }

    public void render(Graphics2D g)
    {
        g.fillOval(0, 0, 5, 5);
    }

}
