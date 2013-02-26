import javax.swing.*;
import java.awt.*;

class Seesaw implements Element
{
    private double angle;

    private int x;
    private int y;

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
        g.rotate(angle, x + 150, y + 50);
        g.fillRect(x, y, 300, 25);
        g.fillOval(x + 125, y + 25, 50, 50);
        g.rotate(-1 * angle, x + 150, y + 50);
    }
}
