import java.awt.Color;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class MainView extends JFrame {
	
	public MainView(int width, int height) throws HeadlessException {
		super("Stars");
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
