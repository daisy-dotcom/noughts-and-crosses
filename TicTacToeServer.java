import java.net.ServerSocket;
/**
 * This class is used to run the Tic Tac Toe
 * server.
 *  
 * @author Dr T W Chim.
 * @author Daisy Oira.
 *
 */
public class TicTacToeServer {
	/**
	 * Creates an instance of the class Server
	 * to set up the Tic Tac Toe game server.
	 * 
	 * @param args	Unused.
	 */
	public static void main(String[] args) {
		System.out.println("Tic Tac Toe Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Tic Tac Toe Server Stopped.");
			}
		}));

		try (var listener = new ServerSocket(58808)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
