import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Driver {
	private final static int WIDTH = 1800;
	private final static int HEIGHT = 1200;
	private final static double INITIAL_PROB = -0.9;
	private final static double DUSTING_PROB = -0.7;
	
	public static void main(String[] args) {
		
		// setup GUI environment
		// take the menu bar off the jframe
		System.setProperty("apple.laf.useScreenMenuBar", "true");

		// set the name of the application menu item
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Star Generator");

		// set the look and feel
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) 
		{
			System.out.println("Exception with GUI setup");
			e.printStackTrace();
		}
		
		MainView view = new MainView(WIDTH, HEIGHT);
		StarGenerator starGen = new StarGenerator(WIDTH, HEIGHT);
		
		// initial generation
		starGen.genStars(INITIAL_PROB);	
		
     	// second pass generation
		starGen.genStars4(15);
		starGen.genStars4(10);
		//starGen.genStars4(10);
		starGen.genStars4(25);
		starGen.genStars4(6);
		starGen.genStars4(60);
		
		// final dusting
		starGen.genStars1(DUSTING_PROB);
		
		Stars stars = new Stars(starGen.getStars());
		
		view.add(stars);
		view.setVisible(true);
		stars.paintStars();
	}	
}
