import javax.swing.*;
import java.awt.*;

class Seesaw extends Element
{
    private double angle;

    Seesaw(int x, int y, double start)
    {
        this.x = x;
        this.y = y;
        angle = start;
    }

    void update(double change)
    {
        angle += change;
    }

    public void render(Graphics2D g)
    {
        g.rotate(angle, 150, 50);
        g.fillRect(0, 0, 300, 25);
        g.fillOval(125, 25, 50, 50);
        g.rotate(-1 * angle, 150, 50);
    }
}
