package n_puzzle;

import static java.lang.Math.abs;

public class Board_State_by_linear_conflict implements Comparable<Board_State_by_linear_conflict> {

    public int state[][];
    public int goal[][];
    public int row, col;
    public int f;
    public int g;
    public int h;
    public int blank;
    public Board_State_by_linear_conflict parent_state;

    public Board_State_by_linear_conflict(int[][] board, int row, int col, int[][] goal, int blanksign) {
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

    public void set_f(int g) {
        this.g = g;
        this.h = linear_conflict_distance();
        this.f = g + h;
    }

    public Num_pos getnumberposinstate(int number) {
        Num_pos num = new Num_pos();
        Boolean numset = false;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.state[i][j] == number) {
                    num.i = i;
                    num.j = j;
                    numset = true;
                    break;
                }
                if (numset) {
                    break;
                }
            }
        }
        return num;
    }
    
    public Num_pos getnumberposingoal(int number) {
        Num_pos num = new Num_pos();
        Boolean numset = false;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.goal[i][j] == number) {
                    num.i = i;
                    num.j = j;
                    numset = true;
                    break;
                }
                if (numset) {
                    break;
                }
            }
        }
        return num;
    }

    public int man_dist() {
        int distance = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (goal[i][j] != blank) {
                    Num_pos num = getnumberposinstate(goal[i][j]);
                    distance += abs(i - num.i) + abs(j - num.j);
                }
            }
        }
        return distance;
    }
    
    public int linear_conflict_distance(){
        int distance = man_dist();
        int conflict = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = j + 1; k < col; k++) {
                    Num_pos num1 = getnumberposingoal(state[i][j]);
                    Num_pos num2 = getnumberposingoal(state[i][k]);
                    if((state[i][j] != 0) && (state[i][k] != 0) && (num1.i == i) &&(num2.i == i) && (num2.j < num1.j)){
                        conflict++;
                    }
                }
            }
        }
        
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                for (int k = j + 1; k < row; k++) {
                    Num_pos num1 = getnumberposingoal(state[j][i]);
                    Num_pos num2 = getnumberposingoal(state[k][i]);
                    if((state[j][i] != 0) && (state[k][i] != 0) && (num1.j == j) &&(num2.j == j) && (num2.i < num1.i)){
                        conflict++;
                    }
                }
            }
        }
        
        return (distance + (2 * conflict));
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
        //str += "g : " + g + " h : " + h + " f : " + f + "\n";
        return String.format(str);
    }

    public boolean equals(Board_State_by_linear_conflict o) {
        return this.f == o.f;
    }

    @Override
    public int compareTo(Board_State_by_linear_conflict o) {
        if (this.equals(o)) {
            return 0;
        }
        if (f > o.f) {
            return 1;
        }
        return -1;
    }
}
