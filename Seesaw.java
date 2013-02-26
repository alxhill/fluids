import javax.swing.*;
import java.awt.*;

class Seesaw implements Element
{
    private double angle = -0.2;

    private int x;
    private int y;

    Seesaw(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    void update(double change)
    {
        angle += change;
    }

    public void render(Graphics2D g)
    {
        g.rotate(angle, 200, 150);
        g.fillRect(x, y, 300, 25);
        g.fillOval(x + 125, y + 25, 50, 50);
    }
}
