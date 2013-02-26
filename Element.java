import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

abstract class Element
{
	static public ArrayList<Element> elements = new ArrayList<Element>();
		
    public int x, y;
	public int rot;

    abstract public void render(Graphics2D g);
	
	public void push()
	{
		elements.add(this);
	}
}
