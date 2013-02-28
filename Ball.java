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

    public void tick()
    {
        double c = (double)diammeter/2.0;

        for (PhysEl el : PhysEl.physElements) {

            if (el instanceof Line)
            {
                // check if the distance from the centre of the circle to the
                // point perpendicular to the line is greater or less than
                // the radius of the circle.

                Line l = (Line) el;
                // because who wants to find the gradient/angle of a line again?
                Line ac = new Line(l.lx[0], l.lx[1], (int)(x+c), (int)(y+c));

                double theta = Math.abs(l.angle - ac.angle);
                double dc = ac.length * Math.sin(theta);

                if (dc < c)
                {
                    // BOOOOOOMMMMM
                    System.out.println("COLLISION!!");
                    force[0] = -velocity[0];
                    force[1] = -velocity[1];
                }

            }
            else
            {
                force[1] = gravity;
            }

        }

        updateAndMove();
    }

    public void render(Graphics2D g)
    {
        g.fillOval(0, 0, diammeter, diammeter);
    }

    private void updateAndMove()
    {
        velocity[0] += force[0];
        velocity[1] += force[1];
        x += velocity[0];
        y += velocity[1];
    }

}
