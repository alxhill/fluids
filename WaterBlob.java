import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

class WaterBlob extends PhysEl
{

    private double vx, vy, fx, fy;

    private static double lineFriction = 0.4;

    public static double radius=2.5;

    WaterBlob()
    {
        x=0; y=0; rot=0;
    }

    public int side(int l1x, int l1y, int l2x, int l2y, int px, int py)
    {
        int side=(l2x - l1x) * (py - l1y) - (l2y - l1y) * (px - l1x);
        return side;
    }

    public void applyForce(double x, double y)
    {
        fx+=x;
        fy+=y;
    }

    public double[] forceComponents(Line l, double vy)
    {
        double downtheplane=Math.sin(l.angle)*vy;

        double xv=Math.sin(l.angle)*downtheplane;
        double yv=Math.cos(l.angle)*downtheplane;

        if(l.angle > 0)
        {
            xv=-xv;
        }

        double[] ret = new double[2];
        ret[0] = xv;
        ret[1] = yv;
        return ret;
    }

    public Boolean changesSide(Line l, double p1x, double p1y, double p2x, double p2y)
    {
        int s1=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p1x, (int)p1y);
        int s2=side(l.lx[0], l.ly[0], l.lx[1], l.ly[1], (int)p2x, (int)p2y);

        ///not quite a valid assumption, but should only generate very edge case minor errors
        int minx=Math.min(l.lx[0], l.lx[1]);
        int maxx=Math.max(l.lx[0], l.lx[1]);
        int miny=Math.min(l.ly[0], l.ly[1]);
        int maxy=Math.max(l.ly[0], l.ly[1]);

        //if(!(p2x > minx && p2x < maxx && p2y > miny && p2y < maxy))
        if(!(p2x > minx && p2x < maxx))
        {
            return false;
        }

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

    /*public int[] maxParticleMoveToLine(double p1x, double p1y, double p2x, double p2y, double vx, double vy, Line l, Boolean slide)
    {
        ///if iswithin line
        int symod=0;
        int[] rp = new int[2];

        rp[0]=(int)p2x;
        rp[1]=(int)p2y;

        if(changesSide(l, p1x, p1y, p2x, p2y))
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
            double ymov = l.gradient * (xmov) + l.con -1; //-1?

            //nx=x + xmov;
            //ny=ymov;
            rp[0]=(int)xmov;
            rp[1]=(int)ymov;
        }


        return rp;
    }*/

    ///returns a force
    public double[] moveToLine(Line l, double px, double py, double vx, double vy)
    {
        double nx = px + vx;
        double ny = py + vy;

        double ret[] = new double[2];
        ret[0]=0;
        ret[1]=0;
        //x, y, vx, vy

        if(changesSide(l, px, py, nx, ny))
        {
            ///apply force equal to vy
            //System.out.println("hi");
            ///if x collides into line, need to convert to y + some x

            ///work out perpendicular force, then apply that + horizontal. Cancel out all other forces after

            /*double fx, fy;
            fy = -vy;

            double perp = Math.cos(l.angle)*fy;

            //double yv=Math.cos(l.angle)*perp;

            double fc[] = forceComponents(l, fy);

            fx = fc[0];
            //fy+= fc[1];
            ///work out perpendicular to plane force, that is y;]
            //fy+=Math.cos(l.angle)*vx;

            ret[0]=fx;
            ret[1]=fy - 1;*/

            ///get out of jail free
            double friction = 0.8;
            double fx=-vx;
            double fy=-vy;


            double h = Math.sqrt(vx*vx + vy*vy);

            //double angle = 2.0*3.141592 - l.angle;
            //double angle = 2.0*3.141592 - l.angle;

            //double angle = -l.angle  + 3.141592*3.0/2.0;
            double angle = l.angle;

            double along = h * Math.cos(angle);
            double perp  = h * Math.sin(angle);

            //perp=-perp;

            double vpx = perp * Math.sin(angle);
            if(angle<0)
            {
                vpx=-vpx;
            }
            double vpy = perp * Math.cos(angle);
            if(angle<0)
            {
                vpy=-(vpy);
            }

            double ax = along * Math.sin(angle);
            if(angle < 0)
            {
                //ax=-ax;
            }




            double ay = -along * Math.cos(angle);

            if(angle < 0)
            {
                //System.out.println(ay);
            }

            //System.out.println(ax);

            ///get out of jail free
            /*if((vy + gravity)*friction < 2)
            {
                //friction=1;
                friction = 2.0/(vy + gravity);
            }*/



            fx+=(ax + vpx)*friction;
            fy+=(ay + vpy)*friction - 0.5;




            ret[0]=fx;
            ret[1]=fy;


        }

        return ret;
    }


    public double[] waterBlobRepel(WaterBlob a, WaterBlob b)
    {
        double p1x, p1y, p2x, p2y;
        p1x = a.x;
        p1y = a.y;
        p2x = b.x;
        p2y = b.y;

        double maxRepel = 2;

        double dx, dy;

        dx = b.x - a.x;
        dy = b.y - a.y;

        //System.out.

        double distance = Math.sqrt(dx*dx + dy*dy);

        double[] ret = new double[2];

        ret[0]=0;
        ret[1]=0;

        if(distance < maxRepel && distance!=0)
        {
            double angle = Math.atan2(dy, dx);
            double force = 1.0/distance;// / maxRepel;
            //force = 1.0 - distance;

            force = force * 4.0;

            //System.out.println(force);

            if(force > 2.0)
            {
                force=2.0;
            }

            double xf = force * Math.sin(angle);
            double yf = force * Math.cos(angle);

            //yf=0;
            //xf=0;

            ret[0] = xf;
            ret[1] = yf;


            //b.fx+=xf;
            //b.fy+=yf;

        }

        return ret;
    }

    public void tick(int id)
    {

        //double nx, ny;
        double px = x, py = y;

        //nx = px + vx;
        //ny = py + vy;



        fx=0;
        fy=0;

        applyForce(0, gravity);
        //applyForce(2, 2);

        double[] sumforce = new double[2];

        for(int i = 0; i<PhysEl.physElements.size(); i++)
        {
            PhysEl el = PhysEl.physElements.get(i);
            if(el instanceof WaterBlob && id != i)
            {
                double[] r = waterBlobRepel(this, (WaterBlob)el);
                sumforce[0]+=r[0];
                sumforce[1]+=r[1];
            }
        }

        double maxforce=1.0;
        if(sumforce[0] > maxforce)
        {
            sumforce[0] = maxforce;
        }

        if(sumforce[1] > maxforce)
        {
            sumforce[1] = maxforce;
        }

        if(sumforce[0] < -maxforce)
        {
            sumforce[0] = -maxforce;
        }

        if(sumforce[1] < -maxforce)
        {
            sumforce[1] = -maxforce;
        }


        fx+=sumforce[0];
        fy+=sumforce[1];

        //System.out.println(fx);
        //System.out.println(fy);



        for(int i = 0; i<PhysEl.physElements.size(); i++)
        {
            PhysEl el = PhysEl.physElements.get(i);
            if(el instanceof Line)
            {
                Line l = (Line) el;
                if(changesSide(l, px, py, px + vx + fx, py + vy + fy))
                {
                    double[] force = moveToLine(l, px, py, vx + fx, vy + fy);

                    fx+=force[0];
                    fy+=force[1];

                    //System.out.println(fy);

                    //break;
                }
            }


        }

        //System.out.println(fx);
        //System.out.println(fy);

        //System.out.println("hi");


        vx+=fx;
        vy+=fy;


        x+=vx;
        y+=vy;







        /*double nx, ny;

        double px = x, py = y;
        applyForce(0, gravity);
        ny = y + vy;
        nx = x + vx;
        vy+=fy;
        vx+=fx;
        fx=0;
        fy=0;
        int[] newPos = new int[2];

        for(int i=0; i<PhysEl.physElements.size(); i++)
        newPos[0] = 0;
        newPos[1] = 0;

        Boolean slide = true;
        Boolean first = true;

        int nskip = -1;
        for(int i = 0; i<PhysEl.physElements.size(); i++)
        {
            PhysEl el = PhysEl.physElements.get(i);
            if(el instanceof Line && i != nskip)
            {

                Line l = (Line) el;

                ///changes side of a line
                if(first && changesSide(l, px, py, nx, ny) && i!=nskip)
                {
                    int[] tPos=new int[2];
                    tPos=maxParticleMoveToLine(px, py, nx, ny, vx, vy, l, slide);
                    newPos=tPos;

                    double[] vc = forceComponents(l, vy);

                    fx+=vc[0]*lineFriction;
                    fy+=vc[1]*lineFriction;

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
                    tPos=maxParticleMoveToLine(px, py, newPos[0], newPos[1], vx, vy, l, slide);
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
