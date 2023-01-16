import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.Scanner;

/**
 * This class is responsible for controlling
 * the events on the Tic Tac Toe GUI.
 * 
 * @author Dr T W Chim.
 * @author Daisy Oira.
 *
 * @version 15.0.2
 *
 */
public class Controller {
	GameLayout game;
	private Player player;
	private Player opponent;
	private boolean gameOver;
	
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	/**
	 * Initialises the variables game and gameOver.
	 * 
	 * @param game
	 */
	public Controller(GameLayout game) {
		this.game = game;
		this.gameOver = false;
	}
	
	/**
	 * Creates a socket to connect to the server and
	 * adds action listeners to the GUI.
	 */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 58808);
			this.in = new Scanner(socket.getInputStream());
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 9; i++) {
			game.getGridButton(i).addActionListener(new gridButtonListener());
		}

		game.getSubmitButton().addActionListener(new submitButtonListener());
		game.getInstructions().addActionListener(new MenuItemListener());
		game.getExit().addActionListener(new MenuItemListener());
		disableGrid();
		
		// Creates a new thread for reading server messages
		Thread handler = new ClientHandler(socket);
		handler.start();	
	}
	
	/**
	 * Initialises the player and opponent variables.
	 * 
	 * @param playerNumber	for player 1 or player 2
	 */
	public void setPlayer(String playerNumber){
		if (playerNumber.equals("Player 1")) {
			player = new Player(1);
			opponent = new Player(2);
		}
		
		else {
			player = new Player(2);
			opponent = new Player(1);
		}
	}
	
	/**
	 * Disables the buttons of the Tic Tac Toe 
	 * grid that have not been clicked.
	 */
	public void disableGrid() {
		for (int i = 0; i < 9; i++) {
			JButton button = game.getGridButton(i);
			
			if(button.getText().equals("")) {
				button.setEnabled(false);
			}
			
		}
	}
	
	/**
	 * Enables the buttons of the Tic Tac Toe 
	 * grid that have not been clicked.
	 */
	public void enableGrid() {
		for (int i = 0; i < 9; i++) {
			JButton button = game.getGridButton(i);
			
			if(button.getText().equals("")) {
				button.setEnabled(true);
			}
			
		}	
	}
	
	/**
	 * Checks if all the buttons in the Tic Tac Toe 
	 * grid have been clicked.
	 * 
	 * @return true 	if all the button in the grid have
	 * 					been clicked; return false otherwise.
	 */
	public boolean gridFull() {
		
		for (int i = 0; i < 9; i++) {
			if (game.getGridButton(i).getText() == "") {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if the button that has been clicked
	 * by a player was clicked before.
	 * 
	 * @param button	button clicked
	 * @return true		if button has not been clicked;
	 * 					return false otherwise.
	 */
	public boolean validMove(JButton button) {
		if (button.getText().equals("")) {
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	/**
	 * Displays that it is the current player's
	 * turn on the GUI.
	 */
	public void playerTurn() {
		game.getLabel().setText("Your opponent has moved, now it's is your turn");
	}
	
	/**
	 * Displays that it is the opponent's
	 * turn on the GUI.
	 */
	public void opponentTurn(){
		game.getLabel().setText("Valid move, wait for your opponent");
	}
	
	/**
	 * Check if the player has won the game.
	 * 
	 * @return true		if a winning condition has been met;
	 * 					return false otherwise.
	 */
	public boolean playerWins() {
		
		if( game.getGridButton(0).getText().equals(player.getMove()) && game.getGridButton(1).getText().equals(player.getMove())
				&& game.getGridButton(2).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(3).getText().equals(player.getMove()) && game.getGridButton(4).getText().equals(player.getMove()) 
				&& game.getGridButton(5).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(6).getText().equals(player.getMove()) && game.getGridButton(7).getText().equals(player.getMove()) 
				&& game.getGridButton(8).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(0).getText().equals(player.getMove()) && game.getGridButton(3).getText().equals(player.getMove()) 
				&& game.getGridButton(6).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(1).getText().equals(player.getMove()) && game.getGridButton(4).getText().equals(player.getMove()) 
				&& game.getGridButton(7).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(2).getText().equals(player.getMove()) && game.getGridButton(5).getText().equals(player.getMove()) 
				&& game.getGridButton(8).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(0).getText().equals(player.getMove()) && game.getGridButton(4).getText().equals(player.getMove()) 
				&& game.getGridButton(8).getText().equals(player.getMove())) {
			return true;
		}
		
		else if (game.getGridButton(2).getText().equals(player.getMove()) && game.getGridButton(4).getText().equals(player.getMove()) 
				&& game.getGridButton(6).getText().equals(player.getMove())) {
			return true;
		}
		
		else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * This class listens for clicks on the buttons of
	 * the Tic Tac Toe grid of the GUI.
	 * 
	 */
	class gridButtonListener implements ActionListener{
			
			/**
			 * Determines whether a click on a button 
			 * is a valid move and the outcome of the 
			 * click e.g a winning move or a draw
			 * 
			 * @param e		event caught by listener
			 */
			public void actionPerformed(ActionEvent actionEvent) {
				
				JButton button = (JButton) actionEvent.getSource();
				if (validMove(button)) {
					button.setForeground(player.getColour());
					button.setText(player.getMove());
					out.println(player.getMove() + " " + button.getName());
					
					if (playerWins()) {
						out.println("Game over");
						out.println("Winner " + player.getMove());
						
					}
					
					else if (gridFull()) {
						out.println("Game over");
						out.println("Draw");
						
					}
					
					
					opponentTurn();
					disableGrid();
					out.flush();
				}
				
			}
	}
	
	/**
	 * This class listens for clicks on the 
	 * submit button of the GUI.
	 */
	class submitButtonListener implements ActionListener{
		
		/**
		 * Gets the name submitted in the text field
		 * and displays a welcome message on the GUI.
		 * Disables the input text field and submit button 
		 * afterwards.
		 * 
		 * @param e		event caught by listener.
		 */
		public void actionPerformed(ActionEvent e) {
				String name = Controller.this.game.getInputField().getText();
				Controller.this.game.setName(name);
				Controller.this.game.getSubmitButton().setEnabled(false);
				Controller.this.game.getInputField().setText("");
				Controller.this.game.getInputField().setEnabled(false);
				Controller.this.game.setTitle("Tic Tac Toe-Player: " + name);
				
				out.println("Submission");
				out.flush();
		}
		
	}
	
	/**
	 * This class listens for clicks on the menu
	 * bar of the GUI.
	 *
	 */
	class MenuItemListener implements ActionListener {
		
		/**
		 * Either displays the instructions of the game
		 * or exits the game depending the on the source
		 * of the event.
		 * 
		 * @param e		event caught by listener.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Instructions")) {
				String instructions = 	"Some information about the game\n"
										+ "Criteria for a valid move:\n"
										+ "- The move is not occupied by any mark.\n"
										+ "- The move is made in the player’s turn.\n"
										+ "- The move is made within the 3 x 3 board\n"
										+ "The game would continue and switch among the opposite player until it reaches either "
										+ "one of the following conditions:\n"
										+"- Player 1 wins.\n"
										+"- Player 2 wins.\n"
										+"- Draw."
;
				JOptionPane.showMessageDialog(game, instructions);
			}
			
			else {
				System.exit(0);
			}
			
		}
		
	}
	
	/**
	 * This class handles messages received from the 
	 * server.
	 *
	 */
	class ClientHandler extends Thread {
		private Socket socket;
		
		/**
		 * Initialises the variable socket.
		 * 
		 * @param socket
		 */
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		/**
		 * Runs the method readFromServer() and
		 * catches any exception thrown by the 
		 * method.
		 */
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * Updates the GUI according to the messages received 
		 * from the server.
		 * 
		 * @throws Exception
		 */

		public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {
					var command = in.nextLine();
					String incoming = (String) command;
						
						if (opponent != null && incoming.startsWith(opponent.getMove())) {
							System.out.println(command);
							enableGrid();
							JButton button = Controller.this.game.getGridButton(Character.getNumericValue(incoming.charAt(2)));
							button.setForeground(opponent.getColour());
							button.setText(opponent.getMove());	
							
							if (gameOver == false) {
								playerTurn();
							}
							
							else {
								disableGrid();
							}
							
						}
						
						else if (incoming.equals("Player 1")) {
							setPlayer(incoming);
						}
						
						else if (incoming.equals("Player 2")) {
							setPlayer(incoming);
						}
						
						else if (player != null && (incoming.equals("Start") && player.getMove().equals("X"))) {
							enableGrid();
						}
						
						else if (incoming.equals("Draw")) {
							JOptionPane.showMessageDialog(game, "Draw");
						}
						
						else if (incoming.startsWith("Winner") && incoming.endsWith(player.getMove())) {
							JOptionPane.showMessageDialog(game, "Congratulations. You Win.");
						}
						
						else if (incoming.startsWith("Winner") && incoming.endsWith(opponent.getMove())) {
							disableGrid();
							JOptionPane.showMessageDialog(game, "You lose.");
						}
						
						else if (incoming.equals("Game over")) {
							gameOver = true;
						}
						
						else if (incoming.equals("Player Left")) {
							disableGrid();
							JOptionPane.showMessageDialog(game, "Game Ends. One of the players left.");
						}
						
						//System.out.println("Client Received: " + command);
						out.flush();
					}
							
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
}
	
	
	
	

