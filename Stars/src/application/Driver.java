package application;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import star.StarGenerator;

public class Driver {
	private final static int WINDOW_WIDTH = 1800;
	private final static int WINDOW_HEIGHT = 1200;
	
	public static void main(String[] args) {

		setupUI();
		
		MainView view = new MainView(WINDOW_WIDTH, WINDOW_HEIGHT);
		StarGenerator starGen = new StarGenerator(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// initial generation
		starGen.generateInitial();
		
     	// second pass cluster generation
		starGen.buildCluster(10, 9);
		starGen.buildCluster(16, 15);
		starGen.buildCluster(25, 24);
		starGen.buildCluster(50, 49);
		
		// final dusting
		starGen.generateDusting();
		
		Sky sky = new Sky(starGen.getStars());
		
		view.add(sky);
		view.setVisible(true);
		sky.paintStars();
	}	
	
	private static void setupUI()
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) 
		{
			e.printStackTrace();
		}
	}
}
