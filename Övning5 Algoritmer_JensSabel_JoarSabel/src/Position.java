import java.util.LinkedList;
import java.util.List;

public class Position {
    public char[] board;
    public char turn;
    public int dim = 3;

    /**
     * Position Constructors below
     */
    public Position() {
        this.board = "         ".toCharArray();
        this.turn = 'x';
    }

    public Position(char[] board, char turn){
        this.board = board;
        this.turn = turn;
    }

    public Position(String str) {
        this.board = str.toCharArray();
        this.turn = 'x';
    }

    public Position(String str, char turn){
        this.board = str.toCharArray();
        this.turn = turn;
    }

    public String toString() {
        return new String(board);
    }

    public Position move(int idx) {
        char[] newBoard = board.clone();
        newBoard[idx] = turn;
        return new Position(newBoard, turn == 'x' ? 'o' : 'x');
    }

    /**
     * @return Returns the possible moves for the computer
     */
    public Integer[] possibleMoves() {
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' '){
                list.add(i);
            }
        }
        Integer[] array = new Integer[list.size()];
        list.toArray(array);
        return array;
    }

    /**
     * Defines what a winning line is
     */
    public boolean win_line(char turn, int start, int step){
        for (int i = 0; i < 3; i++) {
            if (board[start + step*i] != turn){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for a win using the win_line function
     */
    public boolean win(char turn){
        for (int i = 0; i < dim; i++) {
            if (win_line(turn, i*dim, 1) || win_line(turn, i , dim)){
                return true;
            }
        }

        if (win_line(turn, dim-1, dim-1) || win_line(turn, 0, dim+1)){
            return true;
        }
        return false;
    }

    /**
     * Simple minmax function
     */
    public int minimax() {
        if (win('x')) {return 100; }
        if (win('o')) {return -100;}
        if (possibleMoves().length == 0){return 0;}
        Integer mm = null;
        for (Integer idx: possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == 'x' && mm < value || turn == 'o' && value < mm){
                    mm = value;
                }
            }
        return mm + (turn == 'x' ? -1 : 1) - 1;
    }

    /**
     * Checks the best move for the computer
     */
    public int bestMove() {
        Integer mm = null;
        int best = -1;
        for (Integer idx: possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == 'x' && mm < value || turn == 'o' && value < mm){
                mm = value;
                best = idx;
            }
        }
        return best;
    }

    /**
     * Checks if the game has come to an end
     */
    public boolean gameEnd() {
        return win('x') || win('o') || possibleMoves().length == 0;
    }

}
