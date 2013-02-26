import javax.swing.*;
import java.awt.*;

abstract class Element
{
    public int x, y;

    abstract public void render(Graphics2D g);
}
