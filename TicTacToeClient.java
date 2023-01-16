import javax.swing.SwingUtilities;

/**
 * This class creates a Tic Tac Toe client
 * to connect to the Tic Tac Toe server.
 *  
 * @author Dr T W Chim.
 * @author Daisy Oira.
 *
 * @version 15.0.2
 */
public class TicTacToeClient {
	/**
	 * This method creates an instance of the
	 * GUI and its controller.
	 * 
	 * @param args	Unused.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameLayout game = new GameLayout();
				Controller controller = new Controller(game);
				controller.start();
			}
		});
	}

}
