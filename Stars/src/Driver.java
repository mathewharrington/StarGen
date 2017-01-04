
public class Driver {
	private final static int WIDTH = 1800;
	private final static int HEIGHT = 1200;
	private final static double INITIAL_PROB = -0.9;
	private final static double DUSTING_PROB = -0.7;
	
	public static void main(String[] args) {
		
		MainView view = new MainView(WIDTH, HEIGHT);
		StarGenerator starGen = new StarGenerator(WIDTH, HEIGHT);
		
		// initial generation
		starGen.genStars(INITIAL_PROB);	
		
     	// second pass generation
		starGen.genStars4(15);
		starGen.genStars4(10);
		starGen.genStars4(10);
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
