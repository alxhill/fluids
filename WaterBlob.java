import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

class WaterBlob extends PhysObj
{
	
	WaterBlob()
	{
		type="WB";
		x=0; y=0; rot=0;
	}
	
	public void tick()
	{
		
		y+=gravity;
	}
	
	public void render(Graphics2D g)
	{
		g.fillOval(0, 0, 50, 50);
	}



}