import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public final class TicTacToeGame extends JFrame {

	public static int n=1;
	// Main class for starting the game
	public static void main(String[] args) {

		JFrame init = new JFrame();
		while(n<3){
        String name = JOptionPane.showInputDialog(init,
                "Enter Size of Your NxN Board (N)", null);
        n = (int) Integer.parseInt(name);
		}
        
		// Set up the game
		TicTacToeGame game = new TicTacToeGame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);
		game.setSize(600, 600);

		//Center JFrame
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - game.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - game.getHeight()) / 2);
		game.setLocation(x, y);



	}

	public int turn = 0; // Keeps track of turn
	JButton buttons[] = new JButton[n*n]; // Keeps track of buttons (spots)
	int colWins[] = new int[n];
	int rowWins[] = new int[n];
	

	// Method called after each move, to advance turn count
	int updateTurn() {
		System.out.println("Turn: "+turn);
		turn+=1;
		return turn;
	}

	public TicTacToeGame() {

		ArrayList<Integer> human = new ArrayList(); // Keeps track of humans' X's
		ArrayList<Integer> computer = new ArrayList(); // Keeps track of AI's O's

		// Set up container for game
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(n, n));

		//Set up Status Bar
		JLabel status = new JLabel("Make your move!", SwingConstants.CENTER); // Status bar for game
		status.setFont(new Font("Calibri", Font.BOLD, 20));
		status.setForeground(Color.RED);
		status.setBackground(Color.WHITE);
		status.setOpaque(true);

		JLabel empty1 = new JLabel("");
		empty1.setBackground(Color.WHITE);
		empty1.setOpaque(true);
		JLabel empty2 = new JLabel("");
		empty2.setBackground(Color.WHITE);
		empty2.setOpaque(true);

		//Initialize possible wins matrices for NxN
		for(int i=0; i<n; i++){
			rowWins[i]=1;
			colWins[i]=1;
		}

		// Set up grid of buttons for game
		for (int i = 0; i < n*n; i++) {
			JButton button = new JButton();
			final int tempint = i;

			// Action to perform when this button is pressed (human moves)
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					// Put an X in this spot
					button.setText("X");
					button.setFont(new Font("Calibri", Font.BOLD, 60));
					button.setEnabled(false);

					// Add this spot to human's moves
					human.add(tempint);

					// Advance turn
					final int turn = updateTurn();

					// Check if human has won
					if (checkHumanWin(human)) {
						status.setText("You win!");
						for(int i=0; i<n*n; i++){
							if(!human.contains(i)&&!computer.contains(i))
								buttons[i].setEnabled(false);
						}
						JOptionPane.showMessageDialog(pane,
							    "You Win!");

					}
					// If human has not won, make AI's move
					aiMove(human, computer, turn);

					// Check if AI has won
					if (checkAIWin(computer)) {
						status.setText("You lose!");
						for(int i=0; i<n*n; i++){
							if(!human.contains(i)&&!computer.contains(i))
								buttons[i].setEnabled(false);
						}
						JOptionPane.showMessageDialog(pane,
							    "You Lose!");
					}

					// Check for stalemate CAT
					if ( ((n%2==0 && turn==(n*n)-1) || (n!=4) && turn == (n*n)) && !checkAIWin(computer) && !checkHumanWin(human)) {
						status.setText("Tie Game - CAT!");
						System.out.println("Tie Game! CAT!");
						JOptionPane.showMessageDialog(pane,
							    "CAT");
					}

					System.out.println("You've pressed: " + human.toString() + ", Turn is:" + turn);
				}
			});

			// Add this button to the grid
			pane.add(button);

			// Add to array for easy referencing
			buttons[i] = button;

		}
	}


	// Method called after human's move (button press) to check for Win
	boolean checkHumanWin(ArrayList<Integer> human) {

		//Check diagonals
		int sum=0;
		for(int i=0; i<n; i++){
			if(human.contains(i*(n+1))){
				sum++;
			}
		}
		if (sum==n)
			return true;

		sum=0;
		for(int i=0; i<n; i++){
			if(human.contains((i+1)*(n-1))){
				sum++;
			}
		}
		if (sum==n)
			return true;

		//Check columns
		for(int c=0; c<n; c++){
			sum=0;
			for(int i=0; i<n; i++){
				if(human.contains((i*n)+c)){
					sum++;
				}
			}
			if(sum==n)
				return true;
		}			

		//Check Rows
		for(int r=0; r<n; r++){
			sum=0;
			for(int i=0; i<n; i++){
				if(human.contains((n*r)+i)){
					sum++;
				}
				if(sum==n)
					return true;
			}			
		}

		return false;
	}


	//Method called after button press (human's move) when there is no Win
	void aiMove(ArrayList<Integer> human, ArrayList<Integer> computer, int turn) {

		//If a 3x3 game
		if(n==3){
			//If this is the first turn
			if (turn == 1) {

				// If human chose a corner, go in center
				if (human.contains(0) || human.contains(2) || human.contains(6) || human.contains(8)) {
					computer.add(4);
					buttons[4].setText("O");
					buttons[4].setFont(new Font("Calibri", Font.BOLD, 60));
					buttons[4].setEnabled(false);
				}

				// If human chose center, go in a corner (top left)
				else if (human.contains(4)) {

					computer.add(0);
					buttons[0].setText("O");
					buttons[0].setFont(new Font("Calibri", Font.BOLD, 60));
					buttons[0].setEnabled(false);

				}

				// If human chooses a side, go in center
				else if (human.contains(1) || human.contains(3) || human.contains(5) || human.contains(7)) {
					computer.add(4);
					buttons[4].setText("O");
					buttons[4].setFont(new Font("Calibri", Font.BOLD, 60));
					buttons[4].setEnabled(false);

				}

			}

			// If not opening turn, play "smart" 
			else {

				//Get a position to move based on human's moves
				int compShouldMove = checkTwoInRowHuman(human, computer);

				//This should never happen until the end of game
				if (compShouldMove == -1) {
					System.out.println("No where left to go.");
				} 

				//If a good move was found, make the move
				else {
					computer.add(compShouldMove);
					buttons[compShouldMove].setText("O");
					buttons[compShouldMove].setFont(new Font("Calibri", Font.BOLD, 60));
					buttons[compShouldMove].setEnabled(false);
				}
			}
		}

		//Else if NxN>3x3
		else{

			//For all spots in the grid (in turns 1 and 3)...
			for(int i=0; i<n*n; i++){	
				//If human and AI have not gone there yet
				if(!human.contains(i) && !computer.contains(i)){

					//If AI's first move, take 1st diagonal
					if(turn==1){
						if(i%(n+1)==0){

							//Make AI's move
							computer.add(i);
							buttons[i].setText("O");
							buttons[i].setFont(new Font("Calibri", Font.BOLD, 60));
							buttons[i].setEnabled(false);

							//Remove intersecting rows/cols from possible wins
							rowWins[i/n]=0;
							colWins[i%n]=0;
							break;

						}
					}

					//If AI's second move, take 2nd diagonal
					if(turn==3){
						if(i%(n-1)==0){
							
							//Make AI's move
							computer.add(i);
							buttons[i].setText("O");
							buttons[i].setFont(new Font("Calibri", Font.BOLD, 60));
							buttons[i].setEnabled(false);

							//Remove intersecting rows/cols from possible wins
							rowWins[i/n]=0;
							colWins[i%n]=0;
							break;
						}
					}
				}//end: If Human and AI have not gone here
			}//end: For all spots in grid

			if(turn>3){

				//Look for possible row/col Win with most X's
				int max = 0; //Count of row/col with most "X's" found
				int move = 0; //Row/Col number to block
				int col_or_row = 0; //Col = 0, Row = 1
				boolean possible = false; //If a "dangerous" row/col is found

				//For each row/col (1-N)
				for(int p=0; p<n; p++){

					//If this column can still be won by human
					if(colWins[p]==1){
						int tempcount=0;
						//Count how many X's in this col
						for(int x=0; x<human.size(); x++){
							if((human.get(x)%n)==p){
								tempcount++;
							}
						}
						//If this is the most X's found so far, update "max"
						if(tempcount>max){
							possible=true;
							max = tempcount;
							move = p;
							col_or_row = 0;
						}	
					}

					//If this row can still be won by human
					if(rowWins[p]==1){
						int tempcount=0;
						
						//Count how many X's in this row
						for(int x=0; x<human.size(); x++){
							if((human.get(x)/n)==p){
								tempcount++;
							}
						}
						//If this is the most X's so far, track
						if(tempcount>max){
							possible=true;
							max=tempcount;
							move = p;
							col_or_row = 1;
						}	
					}

				}

				//If a "dangerous" row/column is found
				if(possible){

					//If a dangerous row is found, block that row
					if(col_or_row==1){
						for(int a=0; a<n*n; a++){
							if(!human.contains(a) && !computer.contains(a)){
								if(a/n==move){
									
									//Make AI's move
									computer.add(a);
									buttons[a].setText("O");
									buttons[a].setFont(new Font("Calibri", Font.BOLD, 60));
									buttons[a].setEnabled(false);

									//Remove intersecting rows/cols from possible wins
									rowWins[a/n]=0;
									colWins[a%n]=0;
									break; 

								}
							}
						}
					}
					
					
					//If a dangerous column is found, block that column
					if(col_or_row==0){
						for(int a=0; a<n*n; a++){
							if(!human.contains(a) && !computer.contains(a)){
								if(a%n==move){
									
									//Make AI's move
									computer.add(a);
									buttons[a].setText("O");
									buttons[a].setFont(new Font("Calibri", Font.BOLD, 60));
									buttons[a].setEnabled(false);

									//Remove intersecting rows/cols from possible wins
									rowWins[a/n]=0;
									colWins[a%n]=0;
									break; 

								}
							}
						}
					}

				}
				
				//If no dangerous row/col, go anywhere
				if(!possible){
					for(int z=0; z<n*n; z++){
						if(!human.contains(z) && !computer.contains(z)){
							
							//Make AI's move
							computer.add(z);
							buttons[z].setText("O");
							buttons[z].setFont(new Font("Calibri", Font.BOLD, 60));
							buttons[z].setEnabled(false);

							//Remove intersecting rows/cols from possible wins
							rowWins[z/n]=0;
							colWins[z%n]=0;
							break; 
						}
					}	
				}
			}//end: If turn >3
		}//end: Else if N>3

		updateTurn();

	}


	// Method called after AI's move (aiMove())
	boolean checkAIWin(ArrayList<Integer> computer) {


		//Check diagonals
		int sum=0;
		for(int i=0; i<n; i++){
			if(computer.contains(i*(n+1))){
				sum++;
			}
		}
		if (sum==n)
			return true;

		sum=0;
		for(int i=0; i<n; i++){
			if(computer.contains((i+1)*(n-1))){
				sum++;
			}
		}
		if (sum==n)
			return true;

		//Check columns
		for(int c=0; c<n; c++){
			sum=0;
			for(int i=0; i<n; i++){
				if(computer.contains((i*n)+c)){
					sum++;
				}
			}
			if(sum==n)
				return true;
		}			

		//Check Rows
		for(int r=0; r<n; r++){
			sum=0;
			for(int i=0; i<n; i++){
				if(computer.contains((n*r)+i)){
					sum++;
				}
				if(sum==n)
					return true;
			}			
		}

		return false;
	}

	//Method called in aiMove(), look for win or block human
	int checkTwoInRowHuman(ArrayList<Integer> human, ArrayList<Integer> computer) {

		// If computer chooses opposite corners
		if (computer.contains(0) && computer.contains(8)) {
			if (!computer.contains(4) && !human.contains(4))
				return 4;
		}

		if (computer.contains(2) && computer.contains(6)) {
			if (!human.contains(4) && !computer.contains(4))
				return 4;
		}

		//If computer chooses two corners on same side
		if (computer.contains(0) && computer.contains(6)) {
			if (!computer.contains(3) && !human.contains(3))
				return 3;
		}

		if (computer.contains(0) && computer.contains(2)) {
			if (!computer.contains(1) && !human.contains(1))
				return 1;
		}

		if (computer.contains(6) && computer.contains(8)) {
			if (!human.contains(7) && !computer.contains(7))
				return 7;
		}

		if (computer.contains(2) && computer.contains(8)) {
			if (!human.contains(5) && !computer.contains(5))
				return 5;
		}

		//If computer chooses two adjacent spots
		if (computer.contains(0) && computer.contains(1)) {
			if (!human.contains(2) && !computer.contains(2))
				return 2;
		}

		if (computer.contains(0) && computer.contains(3)) {
			if (!human.contains(6) && !computer.contains(6))
				return 6;
		}

		if (computer.contains(2) && computer.contains(1)) {
			if (!human.contains(0) && !computer.contains(0))
				return 0;
		}

		if (computer.contains(2) && computer.contains(5)) {
			if (!human.contains(8) && !computer.contains(8))
				return 8;
		}

		if (computer.contains(5) && computer.contains(8)) {
			if (!human.contains(2) && !computer.contains(2))
				return 2;
		}

		if (computer.contains(7) && computer.contains(8)) {
			if (!human.contains(6) && !computer.contains(6))
				return 6;
		}

		if (computer.contains(3) && computer.contains(6)) {
			if (!human.contains(0) && !computer.contains(0))
				return 0;
		}

		if (computer.contains(6) && computer.contains(7)) {
			if (!human.contains(8) && !computer.contains(8))
				return 8;
		}

		//If computer chooses center and a side
		if (computer.contains(1) && computer.contains(4)) {
			if (!human.contains(7) && !computer.contains(7))
				return 7;
		}

		if (computer.contains(5) && computer.contains(4)) {
			if (!human.contains(3) && !computer.contains(3))
				return 3;
		}

		if (computer.contains(7) && computer.contains(4)) {
			if (!human.contains(1) && !computer.contains(1))
				return 1;
		}

		if (computer.contains(3) && computer.contains(4)) {
			if (!human.contains(5) && !computer.contains(5))
				return 5;
		}

		// If computer chooses center and a corner
		if (computer.contains(2) && computer.contains(4)) {
			if (!human.contains(6) && !computer.contains(6))
				return 6;
		}

		if (computer.contains(8) && computer.contains(4)) {
			if (!human.contains(0) && !computer.contains(0))
				return 0;
		}

		if (computer.contains(6) && computer.contains(4)) {
			if (!human.contains(2) && !computer.contains(2))
				return 2;
		}

		if (computer.contains(0) && computer.contains(4)) {
			if (!human.contains(8) && !computer.contains(8))
				return 8;
		}

		// X CORNER (O IN CENTER): If human chooses opposite corners
		if (human.contains(0) && human.contains(8)) {
			if (!computer.contains(1) && !human.contains(1))
				return 1;
		}

		if (human.contains(2) && human.contains(6)) {
			if (!computer.contains(1) && !human.contains(1))
				return 1;
		}

		// X CORNER (O IN CENTER): If human chooses two corners on same side
		if (human.contains(0) && human.contains(6)) {
			if (!computer.contains(3) && !human.contains(3))
				return 3;
		}

		if (human.contains(0) && human.contains(2)) {
			if (!computer.contains(1) && !human.contains(1))
				return 1;
		}

		if (human.contains(6) && human.contains(8)) {
			if (!computer.contains(7) && !human.contains(7))
				return 7;
		}

		if (human.contains(2) && human.contains(8)) {
			if (!computer.contains(5) && !human.contains(5))
				return 5;
		}

		// X CORNER (O IN CENTER): If human chooses two adjacent spots
		if (human.contains(0) && human.contains(1)) {
			if (!computer.contains(2) && !human.contains(2))
				return 2;
		}

		if (human.contains(0) && human.contains(3)) {
			if (!computer.contains(6) && !human.contains(6))
				return 6;
		}

		if (human.contains(2) && human.contains(1)) {
			if (!computer.contains(0) && !human.contains(0))
				return 0;
		}

		if (human.contains(2) && human.contains(5)) {
			if (!computer.contains(8) && !human.contains(8))
				return 8;
		}

		if (human.contains(5) && human.contains(8)) {
			if (!computer.contains(2) && !human.contains(2))
				return 2;
		}

		if (human.contains(7) && human.contains(8)) {
			if (!computer.contains(6) && !human.contains(6))
				return 6;
		}

		if (human.contains(3) && human.contains(6)) {
			if (!computer.contains(0) && !human.contains(0))
				return 0;
		}

		if (human.contains(6) && human.contains(7)) {
			if (!computer.contains(8) && !human.contains(8))
				return 8;
		}

		// X CENTER (O IN TOP LEFT) If human chooses center and a side
		if (human.contains(1) && human.contains(4)) {
			if (!computer.contains(7) && !human.contains(7))
				return 7;
		}

		if (human.contains(5) && human.contains(4)) {
			if (!computer.contains(3) && !human.contains(3))
				return 3;
		}

		if (human.contains(7) && human.contains(4)) {
			if (!computer.contains(1) && !human.contains(1))
				return 1;
		}

		if (human.contains(3) && human.contains(4)) {
			if (!computer.contains(5) && !human.contains(5))
				return 5;
		}

		// X CENTER (O IN TOP LEFT) If human chooses center and a corner
		if (human.contains(2) && human.contains(4)) {
			if (!computer.contains(6) && !human.contains(6))
				return 6;
		}

		if (human.contains(8) && human.contains(4)) {
			if (!computer.contains(0) && !human.contains(0))
				return 0;
		}

		if (human.contains(6) && human.contains(4)) {
			if (!computer.contains(2) && !human.contains(2))
				return 2;
		}

		if (human.contains(0) && human.contains(4)) {
			if (!computer.contains(8) && !human.contains(8))
				return 8;
		}


		// Else if no way to win or block, go anywhere
		else {
			for(int i=0; i<n*n;i++){
				if(!computer.contains(i) && !human.contains(i))
					return i;
			}

		}

		// No where left to move
		return -1;
	}


}
