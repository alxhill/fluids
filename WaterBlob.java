import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

class WaterBlob extends PhysEl
{

    private double vx, vy, fx, fy;

    private static double radius=4;

    private Boolean isWater;
	
	private double mass;

    WaterBlob()
    {
        x=0; y=0; rot=0;
		isWater=true;
		mass=1;
    }
	
	public void setWater(Boolean p)
	{
		isWater=p;
	}
	
	public void setMass(double value)
	{
		mass=value;	
		radius=5*mass;
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

    public Boolean changesSide(Line l, double p1x, double p1y, double p2x, double p2y, double radius)
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
            double friction = 0.9;
            double fx=-vx;
            double fy=-vy;


            double h = Math.sqrt(vx*vx + vy*vy);

            //double angle = 2.0*3.141592 - l.angle;
            //double angle = 2.0*3.141592 - l.angle;

            //double angle = -l.angle  + 3.141592*3.0/2.0;
            double angle = l.angle;
			//double pangle = Math.atan2((vy), (vx));
			//angle+=Math.abs(pangle);

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
            

            double ay = -along * Math.cos(angle);
			
			if(vy < 0)
			{
				ay=-ay;
			}

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
			if(vy < 0)
				fy+=(ay + vpy)*friction  + 0.5;
			else
				fy+=(ay + vpy)*friction  - 0.5;




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

        double maxRepel = radius*8;

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
            //double force = 1.0/distance;// / maxRepel;
            double force = distance/maxRepel;
            force = 1.0 - force;

            force = force * 2;
            
            //force=force*8;

            //System.out.println(force);

            if(force > 2.0)
            {
                force=2.0;
            }

            double xf = -force * Math.cos(angle)/2.0;
            double yf = -force * Math.sin(angle)/2.0;
            
            b.fx+=-xf/2.0;
            b.fy+=-yf/2.0;

            //yf=0;
            //xf=0;

            ret[0] = xf;
            ret[1] = yf;


            //b.fx+=xf;
            //b.fy+=yf;

        }

        return ret;
    }

    public double[] waterBlobCollide(WaterBlob a, WaterBlob b)
    {
        double[] f = new double[2];
        f[0]=0;
        f[1]=0;


        double v1x, v1y, v2x, v2y;

        v1x=a.vx + a.fx;
        v1y=a.vy + a.fy;
        v2x=b.vx + b.fx;
        v2y=b.vy + b.fy;

        double dx=b.x + v2x - a.x - v1x;
        double dy=b.y + v2y - a.y - v1y;

        double tr=radius;
        double distance=Math.sqrt(dx*dx + dy*dy);
        if(distance < tr*2)
        {
            double angle = Math.atan2(dy, dx);
            ///transfer of momentum will do, all the same mass so transfer of velocity
            /*f[0]=-v1x;
            f[1]=-v1y;
            double n1x, n1y, n2x, n2y;
            n1x=v2x;
            n1y=v2y;
            n2x=v1x;
            n2y=v1y;
            f[0]+=n1x;
            f[1]+=n1y;

            /*a.fx=f[0];
            a.fy=f[1];
            b.fx=-v2x;
            b.fy=-v2y;
            b.fx+=n2x;
            b.fy+=n2y;*/
            
            /*double edist = tr*2 - Math.sqrt(dx*dx + dy*dy);
            edist=edist/(tr*2);
            edist=0.3;




            a.fx=v2x;
            a.fy=v2y;
            a.fx+=-Math.cos(angle)*edist;
            a.fy+=-Math.sin(angle)*edist;

            b.fx=v1x;
            b.fy=v1y;
            b.fx+=Math.cos(angle)*edist;
            b.fy+=Math.sin(angle)*edist;

            a.vx=0;
            a.vy=0;
            b.vx=0;
            b.vy=0;*/
            
            double dampen=1.0;
            
            a.fx=(v2x + v1x)*dampen/2.0;
            a.fy=(v2y + v1y)*dampen/2.0;
            b.fx=(v2x + v1x)*dampen/2.0;
            b.fy=(v2y + v1y)*dampen/2.0;
            
            ///plus enough to move it out of tr*2
            
            double xf=Math.cos(angle)*tr*2 - Math.cos(angle)*distance;
            double yf=Math.sin(angle)*tr*2 - Math.sin(angle)*distance;
            
            a.fx -= xf/2.0;
            a.fy -= yf/2.0;
            b.fx += xf/2.0;
            b.fy += yf/2.0;
            
            a.vx=0;
            a.vy=0;
            b.vx=0;
            b.vy=0;
         
            
            


        }

        //f[0]=0;
        //f[1]=0;



        return f;
    }

    public void tick(int id)
    {
        double px = x, py = y;

        //fx=0;
        //fy=0;

        applyForce(0, gravity);

        double[] sumforce = new double[2];

        double[] r2 = new double[2];

        for(int i = 0; i<PhysEl.physElements.size(); i++)
        {
            PhysEl el = PhysEl.physElements.get(i);
            if(el instanceof WaterBlob && id != i)
            {
                double[] r = waterBlobRepel(this, (WaterBlob)el);

                sumforce[0]+=r[0];
                sumforce[1]+=r[1];
                double[] r3=waterBlobCollide(this, (WaterBlob)el);
                /*if(r3[0] != 0 || r3[1] != 0)
                {
                    System.out.println(r3[0]);
                    break;
                }*/
            }
        }



        /*double maxforce=0.7;
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
        }*/

        fx+=sumforce[0];
        fy+=sumforce[1];

        //fx=0.1;
        //fy=1;
        //fx+=this.fx;
        //fy+=this.fy;



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
                }
            }


        }

        vx+=fx;
        vy+=fy;

        x+=vx;
        y+=vy;

        fx=0;
        fy=0;


    }

    public void update()
    {

    }

    public void setXY(int px, int py)
    {
        x=(double)px;
        y=(double)py;
    }

    public void render(Graphics2D g)
    {
        g.fillOval(0, 0, (int)mass*5, (int)mass*5);
    }

}
