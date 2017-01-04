package star;
import java.awt.Color;

public class Star {

	private boolean visible;
	private int size;
	private Color color;
	
	public Star() 
	{
		this.visible = false;
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public int getSize()
	{
		return this.size;
	}
	
	public boolean isVisible()
	{
		return this.visible;
	}
	
	public Color getColor()
	{
		return this.color;
	}
}
