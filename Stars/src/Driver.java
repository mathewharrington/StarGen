import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		starGen.generateCluster(15);
		starGen.generateCluster(10);
		starGen.generateCluster(25);
		starGen.generateCluster(6);
		starGen.generateCluster(60);
		
		// final dusting
		starGen.generateDusting();
		
		Stars stars = new Stars(starGen.getStars());
		
		view.add(stars);
		view.setVisible(true);
		stars.paintStars();
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
