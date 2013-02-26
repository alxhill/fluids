import javax.swing.*;
import java.awt.*;

class View extends JPanel
{

	private ArrayList<Element> elements = new ArrayList<Element>();

	View()
	{
		setPreferredSize(800, 600);
	}

	void addElement(Element e)
	{
		elements.add(e);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		g2 = (Graphics2D) g;
		for (Element e : elements)
		{
			e.render(g2);
		}

	}


}
