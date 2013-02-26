import javax.swing.*;
import java.awt.*;

abstract class Element
{
    public int x, y;
	public int rot;

    abstract public void render(Graphics2D g);
}
