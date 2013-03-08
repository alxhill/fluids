import javax.swing.*;
import java.awt.*;

class Ball extends PhysEl
{
    private int diammeter;
    private double[] velocity = new double[2];
    private double[] force = new double[2];

    Ball()
    {
        this(0, 0, 25);
    }

    Ball(int x, int y, int r)
    {
        this.x = x;
        this.y = y;
        diammeter = 2 * r;
    }

    public void tick(int id)
    {
        double c = (double)diammeter/2.0;

        for (PhysEl el : PhysEl.physElements) {

            if (el instanceof Line)
            {
                Line l = (Line) el;
                
                double x1 = l.lx[0] - x;
                double y1 = l.ly[0] - y;

                double x2 = l.lx[1] - x;
                double y2 = l.ly[1] - y;

                double dx = x2 - x1;
                double dy = y2 - y1;
                double dr = Math.sqrt(dx*dx + dy*dy);
                double d = x1*y2 - x2*y1;

                double delta = Math.pow(c*0.9, 2)*dr*dr - d*d;

                if (delta >= 0)
                {
                    // BOOOOOOMMMMM
                    System.out.println("COLLISION!!");
                    force[0] = -1*velocity[0];
                    force[1] = -1*velocity[1];
                }

            }
            else
            {
                force[1] = gravity;
            }

        }

        //updateAndMove();
    }

    public void render(Graphics2D g)
    {
        g.fillOval(0, 0, diammeter, diammeter);

        // temporary dot in the centre
        double c = (double)diammeter / 2.0;
        g.setColor(Color.WHITE);
        g.fillOval((int)c-3, (int)c-3, 6, 6);
        g.setColor(Color.BLACK);

    }

    public void update()
    {
        velocity[0] += force[0];
        velocity[1] += force[1];
        x += velocity[0];
        y += velocity[1];
    }

}
