import javax.swing.*;
import java.awt.*;
/**
 * This class contains the elements 
 * of the Tic Tac Toe GUI.
 * 
 * @author Daisy Oira
 * 
 * @version 15.0.2
 */
public class GameLayout extends JFrame{
	private JButton [] grid;
	private JButton submit;
	
	private JLabel message;
	private JTextField inputName;
	
	
	private JMenuBar menuBar;
	
	private JMenu control;
	private JMenu help;
	
	private JMenuItem exit;
	private JMenuItem instructions;
	
	private JPanel board;
	private JPanel inputPanel;
	private JPanel mainPanel;
	
	/**
	 * Adds all the elements of the GUI 
	 * to the JFrame and enables its visibility.
	 */
	public GameLayout() {
		setMenuBar();
		setGrid();
		setLayout();
		setJMenuBar(menuBar);
		
		setTitle("Tic Tac Toe");
		getContentPane().add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300); 
		setVisible(true);
		
	}
	
	/**
	 * Adds all the menu items to the menu bar.
	 */
	public void setMenuBar() {
		control = new JMenu("Control");
		help = new JMenu("Help");
		
		exit = new JMenuItem("Exit");
		instructions = new JMenuItem("Instructions");
		
		control.add(exit);
		help.add(instructions);
		
		menuBar = new JMenuBar();
		menuBar.add(control);
		menuBar.add(help);
	}
	
	/**
	 * Adds JButtons to the Tic Tac Toe grid.
	 */
	public void setGrid() {
		grid = new JButton[9];
		board = new JPanel();
		board.setLayout(new GridLayout(3,3));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				grid[(3*i) + j] = new JButton("");
				grid[(3*i) + j].setFont(new Font("Arial", Font.PLAIN, 40));
				grid[(3*i) + j].setBackground(Color.WHITE);
				grid[(3*i) + j].setName(Integer.toString((3*i) + j));
				grid[(3*i) + j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				
				board.add(grid[(3*i) + j]);
			}
		}
	}
	
	/**
	 * Places the different JPanels in their
	 * respective borders in the BorderLayout of 
	 * the GUI.
	 */
	public void setLayout() {
		message = new JLabel("Enter your player name...");
		
		inputName = new JTextField(15);
		submit = new JButton("Submit");
		inputPanel = new JPanel();
		inputPanel.add(inputName);
		inputPanel.add(submit);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(message, BorderLayout.NORTH);
		mainPanel.add(board, BorderLayout.CENTER);
		mainPanel.add(inputPanel, BorderLayout.SOUTH);
				
	}
	
	/**
	 * Returns a JButton of the Tic Tac Toe grid
	 * given by i.
	 * 
	 * @param i			number of JButton
	 * @return grid[i]	JButton i
	 */
	public JButton getGridButton(int i) {
		return grid[i];
	}
	
	/**
	 * Returns the input text field of the GUI.
	 * 
	 * @return inputName	
	 */
	
	public JTextField getInputField(){
		return inputName;
	}
	
	/**
	 * Returns the submit JButton of the GUI.
	 * 
	 * @return submit
	 */
	public JButton getSubmitButton() {
		return submit;
	}
	
	/**
	 * Sets the welcome message once a player
	 * submits their name.
	 * 
	 * @param name		name to include in welcome
	 * 					message.
	 */
	public void setName(String name) {
		message.setText("Welcome " + name);
	}
	
	/**
	 * Returns the information holding
	 * JLabel of the GUI.
	 * 
	 * @return message 
	 */
	public JLabel getLabel() {
		return message;
	}
	
	/**
	 * Return the instruction JMenuItem of the GUI.
	 * 
	 * @return instruction	
	 */
	public JMenuItem getInstructions() {
		return instructions;
	}
	
	/**
	 * Returns the exit JMenuItem of the GUI.
	 * 
	 * @return exit
	 */
	public JMenuItem getExit() {
		return exit;
	}
}
