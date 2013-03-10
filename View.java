import javax.swing.*;
import java.awt.*;

class View extends JPanel
{

    View()
    {
        setPreferredSize(new Dimension(800, 600));
    }

    void tick()
    {
        repaint();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        for (Element e : Element.elements)
        {
            int rx, ry;

            rx = (int) Math.round(e.x);
            ry = (int) Math.round(e.y);
            g.translate(rx, ry);
            e.render(g2);
            g.translate(-1 * rx, -1 * ry);
        }
    }

}
