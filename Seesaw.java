import javax.swing.*;
import java.awt.*;

class Seesaw implements Element
{
    private double angle = -0.2;

    void update(double change)
    {
        angle += change;
    }

    public void render(Graphics2D g)
    {
        g.rotate(angle, 200, 150);
        g.fillRect(50, 100, 300, 25);
        g.fillOval(175, 125, 50, 50);
    }
}
