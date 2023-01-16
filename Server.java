import java.util.*;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class creates a server to facilitate
 * communication between clients.
 * 
 * @author Dr T W Chim.
 * @author Daisy Oira.
 */
public class Server {
	private ServerSocket serverSocket;
	private int namesSubmitted;
	private boolean start;

	// The set of all the print writers for all the clients, used for broadcast.
	private HashSet<PrintWriter> writers = new LinkedHashSet<>();
	
	/**
	 * Initialises the variables serverSocket,
	 * start and namesSubmitted.
	 * 
	 * @param serverSocket
	 */
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.start = false;
		this.namesSubmitted = 0;
	}
	
	/**
	 * Waits for a client to connect to
	 * the server and adds it to the execution pool.
	 */
	public void start() {
		var pool = Executors.newFixedThreadPool(2);
		int clientCount = 0;
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				pool.execute(new Handler(socket));
				System.out.println("Connected to client " + clientCount++);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * Set the state of the start variable to
	 * true or false.
	 * 
	 * @param state		true if the game has started;
	 * 					return false otherwise.
	 */
	public synchronized void setStart(boolean state) {
		this.start = state;
	}
	
	/**
	 * Returns state of start variable.
	 * 
	 * @return start	true if the game has started;
	 * 					return false otherwise.
	 */
	public synchronized boolean getStart() {
		return start;
	}
	
	/**
	 * 
	 * This class receives and sends messages 
	 * to and from the clients.
	 *
	 */
	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;
		
		/**
		 * Initialises the variable socket.
		 * 
		 * @param socket
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}
		
		/**
		 * Adds a PrintWriter output to the LinkedHashSet 
		 * writers and sends out information on the number 
		 * of the player.
		 * 
		 * @param output
		 */
		public synchronized void addWriter(PrintWriter output) {
			writers.add(output);
			String outgoing_msg = new String("");
			int i; 
			
			
			if (writers.size() == 1) {
				i = 0;
				outgoing_msg = new String("Player 1");
		
			}
			else {
				i = 1;
				outgoing_msg = new String("Player 2");
				
			}
			
			
			int j = 0;
			for(PrintWriter writer : writers) {
				if (i == j) {
					writer.println(outgoing_msg);
				}
				j++;
			}
		}
		
		/**
		 * Returns size of the LinkedHashSet writers.
		 * 
		 * @return writers.size();
		 */
		public synchronized Integer getWriterSize() {
			return writers.size();
		}
		
		/**
		 * Removes the PrintWriter output from the
		 * LinkedHashSet writers.
		 * 
		 */
		public synchronized void removeWriter(PrintWriter output) {
			writers.remove(output);
		}
		
		/**
		 * Increments the number of names submitted by 
		 * by the clients by one.
		 */
		public synchronized void increaseNames() {
			namesSubmitted++;
		}
		
		/**
		 * Decrements the number of names submitted by 
		 * by the clients by one.
		 */
		public synchronized void decreaseNames() {
			namesSubmitted--;
		}
		
		/**
		 * Returns the number of names submitted by 
		 * by the clients.
		 */
		public synchronized Integer getNames() {
			return namesSubmitted;
		}
		
		/**
		 * Updates variables according to messages received 
		 * from the clients and broadcasts messages to all 
		 * the clients.
		 * Also checks if a client has disconnected from
		 * the server. 
		 */
		public void run() {
			try {
				
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);

				// add this client to the broadcast list
				addWriter(output);
				
				while (input.hasNextLine()) {
					var command = input.nextLine();

					//System.out.println("Server Received: " + command);
					
					if (getStart() == false && command.equals("Submission")) {
						increaseNames();
						//System.out.println(getNames());
						if (getNames().equals(2)) {
							command = "Start";
							setStart(true);
						}
					}
					
					if (command.equals("Game over")) {
						setStart(false);
						decreaseNames();
						decreaseNames();
					}
					
					// Broadcast to all clients
					for (PrintWriter writer : writers) {
						writer.println(command);
					}

					//System.out.println("Server Broadcasted: " + command);

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				// client disconnected
				if (output != null) {
					removeWriter(output);
					
					
					if(getStart()) {
						for (PrintWriter writer : writers) {
							writer.println("Player Left");
						}
						decreaseNames();
					}
					
					
					setStart(false);
					if (getWriterSize() > 0) {
						writers.iterator().next().println("Player 1");
					}
				}
			}
		}
	}
}
