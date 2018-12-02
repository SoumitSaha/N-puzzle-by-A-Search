package n_puzzle;

public class Board_State_by_hamming implements Comparable<Board_State_by_hamming>{
    public int state[][];
    public int goal[][];
    public int row, col;
    public int f;
    public int g;
    public int h;
    public int blank;
    public Board_State_by_hamming parent_state;

    public Board_State_by_hamming(int[][] board, int row, int col, int [][] goal, int blanksign) {
        this.col = col;
        this.row = row;
        this.state = new int[row][col];
        this.goal = new int[row][col];
        for (int i = 0; i < row; i++) {
            System.arraycopy(board[i], 0, state[i], 0, col);
        }
        for (int i = 0; i < row; i++) {
            System.arraycopy(goal[i], 0, this.goal[i], 0, col);
        }
        this.blank = blanksign;
        this.parent_state = null;
    }
    
    public void set_f(int g){
        this.g = g;
        this.h = ham_dist();
        this.f = g + h; 
    }
    
    public int ham_dist(){
        int distance = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(state[i][j] != goal[i][j]){
                    if(state[i][j] != blank) distance++;
                }
            }
        }
        return distance;
    }
    
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                str += state[i][j] + " ";
            }
            str += "\n";
        }
        //str += "\n";
        return String.format(str); 
    }

    public boolean equals(Board_State_by_hamming o){
        return this.f == o.f;
    }
    
    @Override
    public int compareTo(Board_State_by_hamming o) {
        if(this.equals(o)) return 0;
        if(f > o.f) return 1;
        return -1;
    }
}
