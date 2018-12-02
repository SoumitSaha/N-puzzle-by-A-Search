package n_puzzle;

import static java.lang.Math.abs;

public class Board_State_by_manhatton implements Comparable<Board_State_by_manhatton> {

    public int state[][];
    public int goal[][];
    public int row, col;
    public int f;
    public int g;
    public int h;
    public int blank;
    public Board_State_by_manhatton parent_state;

    public Board_State_by_manhatton(int[][] board, int row, int col, int[][] goal, int blanksign) {
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
        this.h = man_dist();
        this.f = g + h;
    }

    public Num_pos getnumberpos(int number) {
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

    public int man_dist() {
        int distance = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (goal[i][j] != blank) {
                    Num_pos num = getnumberpos(goal[i][j]);
                    distance += abs(i - num.i) + abs(j - num.j);
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

    public boolean equals(Board_State_by_manhatton o) {
        return this.f == o.f;
    }

    @Override
    public int compareTo(Board_State_by_manhatton o) {
        if (this.equals(o)) {
            return 0;
        }
        if (f > o.f) {
            return 1;
        }
        return -1;
    }
}
