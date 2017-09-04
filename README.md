# TicTacToeAI
TicTacToe AI that will never lose a game.


This is an interactive Tic-Tac-Toe game complete with an AI opponent that will
algorithmically respond to the user’s moves so that the user will never win (games will only result in a tie or
loss.)

### Making a Move – JButton actionPerformed()

When a user clicks a JButton (indicating their move) the following sequence of events is executed:

1. An ‘X’ is placed in the spot to indicate the user’s move and the corresponding JButton is disabled.
2. checkHumanWin() is called to check if the user has won (N X’s in a row).
3. If the user has not won, the AI moves with a call to aiMove()
4. checkAIWin() is called to check for an AI win (N O’s in a row).
5. If neither player has won and this is the last move, the game is tied.

The moves of the user (X’s) and AI (O’s) are tracked by adding their integer position representation to the
ArrayList<Integer> human and computer following a user or AI’s move, respectively. Turns are tracked with
the global int variable turn, and calls to updateTurn() after each move. An array of JButtons buttons is used for
easy referencing to the individual JButtons within the grid layout.

### The AI’s Move – aiMove()

The user (human) player always moves first. Despite this advantage, it is possible for the second player (AI) to
guarantee at least a tie if they respond to the user’s moves appropriately. This is where the main algorithm of
the program comes into play. There are three basic opening moves the human user can make in a 3x3 game.

1. Choosing a corner (AI must choose the middle)
2. Choosing middle (AI must choose a corner, in this case top left)
3. Choosing a side (AI should go in center to restrict user possible wins)
When aiMove() is called after the user clicks a JButton
and makes their move, turn is checked to see if this is
the second turn (opening move), and the ArrayList<int>
human is checked to see where the user made their
opening move. If the user chose a corner, the AI
changes the JButton corresponding to the middle of
the grid to an O, and stores its move in the
ArrayList<int> computer. If the user chose the middle,
the AI chooses the top left corner, and if the user chose
a side, the AI chooses the center.
From then on, it is impossible for the user to “trap” the
AI, and to force a tie the AI must simply block any “twoin-
a-rows” by the user. In subsequent calls to
aiMove() (when turn>2), the AI implements this by
making a call to checkTwoInRowHuman().

### Checking for Two X’s in a Row – checkTwoInRowHuman()
With subsequent calls to aiMove() in the case of a 3x3 game, when turn>2, checkTwoInRowHuman() is called
to decide where the AI should move. checkTwoinRowHuman() is passed all the moves in the game so far as
represented by the int’s stored in the ArrayList<int> human and computer. The method returns an int
corresponding to the position the AI should move and the JButton that should be disabled.
The method itself is simply a set of conditional statements covering all possible “two-in-a-row” situations that
could happen after the opening moves. If the user has two X’s in a row (human contains two int’s
corresponding to those positions), and the AI does not have an O in the same row/line from a previous move
(computer does not contain the corresponding int), then the int value corresponding to that position in the
grid is returned back to the calling method aiMove(), and the AI finishes there move there.

### NxN Game
In an NxN game, it becomes increasingly difficult for either side to win, and a tie becomes increasingly likely.
The number of possible solutions grows linearly with N (2N + 2), but the number of possible moves grows by
N2. For any N>3, the AI begings the game by blocking the diagonals in its first 2 turns, simultaneously also
blocking the corresponding row and column intersecting that position.
The int arrays colWins and rowWins are used to keep track of the possible rows/columns the human
can still win. After each move by the AI, the intersecting row/column number of that position are set to ‘0’,
indicating that the row/column is no longer “dangerous” and has a minimum priority for the AI to move in the
future. For the rest of the game after the opening 2 turns by the AI, the algorithm for choosing a spot to move
iterates over the list of rows and columns that can still be won by the human, and counts the number of X’s in
that row/column. The complexity for this algorithm grows proportionately to the area of the grid being used,
or O(N2). Speed was not an issue in calculating moves to make, so the algorithm could be improved in terms of
complexity, at the cost of requiring a less generalized algorithm for all NxN sizes.

### End of Game
After each move by the user and AI, the methods checkHumanWin() and checkAIWin() are called, respectively.
Both methods simply check the ArrayList<int> human and computer for ints that would represent a “three-ina-
row” and return a Boolean indicating if it was found. If either method returns “true” during the length of the
game, the status bar JLabel status is updated accordingly, and all JButtons are disabled, significying the end of
game.
If neither method has returned a true during the length of the game so far, and all possible moves have been
made (turn=N*N), then this game is a tie, and the status bar is updated accordingly.

### Additional Work

The game of Tic-Tac-Toe can be expanded to higher dimensions and/or a greater, but only with adding
exponentially increasing number of permutations that the course of the game can take. At certain dimensions
the game is not only unwinnable, but un tie-able for the player going second (the AI). Take for example the
trivial case of a a 1 square game, the player going first will always win.At higher dimensions the dimensions
the game also becomes trivial to win for whomever goes first. Take the 3D variant of Tic-Tac-Toe for example.
With a starting move in the middle of the “cube”, the first player can always guarantee a win within the first 7
turns.
While it would be possible to compute a “perfect game” using a MinMax algorithm and computing the
marginal “best next move” in every situation, it is not always the case that this “perfect” (no mistakes made)
strategy would always result in a tie.
Adding additional dimensions would have vastly increased the complexity of the algorithm for the AI,
requiring a completely different algorithm used in this program. Additional, it would not even be guaranteed
that for all dimensions a tie could be forced by the second player.
