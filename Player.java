import java.awt.Color;
/**
 * This class holds information
 * about the players.
 * 
 * @author Daisy Oira. 
 *
 */
public class Player {
	private Integer number;
	private String move;
	private Color colour;
	
	/**
	 * Initialises the number, move 
	 * and colour of a player.
	 * 
	 * @param number	for player 1 or player 2.
	 */
	public Player(Integer number) {
		this.number = number;
		
		if (number.equals(1)) {
			move = "X";
			colour = Color.green;
		}
		
		else {
			move = "O";
			colour = Color.red;

		}
	}
	
	/**
	 * Returns the move of the player.
	 * Either X or O.
	 * 
	 * @return move
	 */
	public String getMove() {
		return this.move;
	}
	/**
	 * Returns the number of the player.
	 * Either 1 or 2.
	 * 
	 * @return number
	 */
	public int getNumber() {
		return this.number;
	}
	/**
	 * Returns the colour of a player's
	 * symbol.
	 * 
	 * @return colour
	 */
	public Color getColour() {
		return this.colour;
	}
}
