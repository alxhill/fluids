import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class View extends JPanel
{

    private ArrayList<Element> elements = new ArrayList<Element>();

    View()
    {
        setPreferredSize(new Dimension(800, 600));
    }

    void addElement(Element e)
    {
        elements.add(e);
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

        for (Element e : elements)
        {
            g.translate(e.x, e.y);
            e.render(g2);
            g.translate(-1*e.x, -1*e.y);
        }
    }


}