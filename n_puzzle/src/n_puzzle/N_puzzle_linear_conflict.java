package n_puzzle;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class N_puzzle_linear_conflict {

    public static int linear_conflict_node_expanded;
    public static int linear_conflict_node_explored;
    public static int blanksign;

    public static String value_to_key(Board_State_by_linear_conflict st) {
        String str = "";
        for (int i = 0; i < st.row; i++) {
            for (int j = 0; j < st.col; j++) {
                str += st.state[i][j];
            }
        }
        return str;
    }

    public static Num_pos linear_conflict_getBlankpos(Board_State_by_linear_conflict board) {
        Num_pos blank = new Num_pos();
        Boolean blankset = false;
        for (int i = 0; i < board.row; i++) {
            for (int j = 0; j < board.col; j++) {
                if (board.state[i][j] == blanksign) {
                    blank.i = i;
                    blank.j = j;
                    blankset = true;
                    break;
                }
                if (blankset) {
                    break;
                }
            }
        }
        return blank;
    }

    public static Board_State_by_linear_conflict linear_conflict_blank_slide_up(Board_State_by_linear_conflict board, Board_State_by_linear_conflict goal) {
        Num_pos blank = linear_conflict_getBlankpos(board);
        if (blank.i == 0) {
            return null;
        } else {
            int temp[][] = new int[board.row][board.col];
            for (int i = 0; i < board.row; i++) {
                System.arraycopy(board.state[i], 0, temp[i], 0, board.col);
            }
            int x = board.state[blank.i - 1][blank.j];
            temp[blank.i][blank.j] = x;
            temp[blank.i - 1][blank.j] = blanksign;
            Board_State_by_linear_conflict tempboard = new Board_State_by_linear_conflict(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_linear_conflict linear_conflict_blank_slide_down(Board_State_by_linear_conflict board, Board_State_by_linear_conflict goal) {
        Num_pos blank = linear_conflict_getBlankpos(board);
        if (blank.i == (board.row - 1)) {
            return null;
        } else {
            int temp[][] = new int[board.row][board.col];
            for (int i = 0; i < board.row; i++) {
                System.arraycopy(board.state[i], 0, temp[i], 0, board.col);
            }
            int x = board.state[blank.i + 1][blank.j];
            temp[blank.i][blank.j] = x;
            temp[blank.i + 1][blank.j] = blanksign;
            Board_State_by_linear_conflict tempboard = new Board_State_by_linear_conflict(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_linear_conflict linear_conflict_blank_slide_left(Board_State_by_linear_conflict board, Board_State_by_linear_conflict goal) {
        Num_pos blank = linear_conflict_getBlankpos(board);
        if (blank.j == 0) {
            return null;
        } else {
            int temp[][] = new int[board.row][board.col];
            for (int i = 0; i < board.row; i++) {
                System.arraycopy(board.state[i], 0, temp[i], 0, board.col);
            }
            int x = board.state[blank.i][blank.j - 1];
            temp[blank.i][blank.j] = x;
            temp[blank.i][blank.j - 1] = blanksign;
            Board_State_by_linear_conflict tempboard = new Board_State_by_linear_conflict(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_linear_conflict linear_conflict_blank_slide_right(Board_State_by_linear_conflict board, Board_State_by_linear_conflict goal) {
        Num_pos blank = linear_conflict_getBlankpos(board);
        if (blank.j == (board.col - 1)) {
            return null;
        } else {
            int temp[][] = new int[board.row][board.col];
            for (int i = 0; i < board.row; i++) {
                System.arraycopy(board.state[i], 0, temp[i], 0, board.col);
            }
            int x = board.state[blank.i][blank.j + 1];
            temp[blank.i][blank.j] = x;
            temp[blank.i][blank.j + 1] = blanksign;
            Board_State_by_linear_conflict tempboard = new Board_State_by_linear_conflict(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_linear_conflict a_star_serach_by_linear_conflict(Board_State_by_linear_conflict initial, Board_State_by_linear_conflict goal) {
        HashMap<String, Board_State_by_linear_conflict> map = new HashMap<>();
        PriorityQueue<Board_State_by_linear_conflict> q = new PriorityQueue<>();
        int cost = 0;
        initial.set_f(cost);
        q.add(initial);
        String cur_state_key = value_to_key(initial);
        map.put(cur_state_key, initial);

        while (!q.isEmpty()) {

            Board_State_by_linear_conflict cur_state = q.remove();
            linear_conflict_node_expanded++;
            cost++;

            Boolean isSolvable = true;
            for (int i = 0; i < cur_state.row; i++) {
                for (int j = 0; j < cur_state.col; j++) {
                    if (cur_state.state[i][j] != goal.state[i][j]) {
                        isSolvable = false;
                        break;
                    }
                }
                if (!isSolvable) {
                    break;
                }
            }
            if (isSolvable) {
                return cur_state;
            }

            Board_State_by_linear_conflict up, down, left, right;
            up = linear_conflict_blank_slide_up(cur_state, goal);
            down = linear_conflict_blank_slide_down(cur_state, goal);
            left = linear_conflict_blank_slide_left(cur_state, goal);
            right = linear_conflict_blank_slide_right(cur_state, goal);

            if (up != null) {
                linear_conflict_node_explored++;
                up.parent_state = cur_state;
                up.set_f(cost);
                String key = value_to_key(initial);
                if (!map.containsKey(key)) {
                    q.add(up);
                    map.put(key, up);
                }
            }
            if (down != null) {
                linear_conflict_node_explored++;
                down.parent_state = cur_state;
                down.set_f(cost);
                String key = value_to_key(initial);
                if (!map.containsKey(key)) {
                    q.add(down);
                    map.put(key, down);
                }
            }
            if (left != null) {
                linear_conflict_node_explored++;
                left.parent_state = cur_state;
                left.set_f(cost);
                String key = value_to_key(initial);
                if (!map.containsKey(key)) {
                    q.add(left);
                    map.put(key, left);
                }
            }
            if (right != null) {
                linear_conflict_node_explored++;
                right.parent_state = cur_state;
                right.set_f(cost);
                String key = value_to_key(initial);
                if (!map.containsKey(key)) {
                    q.add(right);
                    map.put(key, right);
                }
            }
        }
        return null;
    }

    public static void linear_conflict_path(Board_State_by_linear_conflict start, Board_State_by_linear_conflict end) {
        if (end == start) {
            System.out.println(start);
        } else if (end.parent_state == null) {
            System.out.println("No path.");
        } else {
            linear_conflict_path(start, end.parent_state);
            System.out.println(end);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int init_board[][];
        int goal_board[][];
        int m, n;
        System.out.print("Row : ");
        m = in.nextInt();
        System.out.print("Col : ");
        n = in.nextInt();
        init_board = new int[m][n];
        goal_board = new int[m][n];
        System.out.println("Start board : ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                init_board[i][j] = in.nextInt();
            }
        }
        //System.out.println("");
        System.out.println("Goal board : ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                goal_board[i][j] = in.nextInt();
            }
        }

        System.out.println("Blank sign : ");
        blanksign = in.nextInt();
        System.out.println("");
        System.out.println("---------- BY LINEAR_CONFLICT DISTANCE ---------");
        Board_State_by_linear_conflict initstate = new Board_State_by_linear_conflict(init_board, m, n, goal_board, blanksign);
        Board_State_by_linear_conflict goalstate = new Board_State_by_linear_conflict(goal_board, m, n, goal_board, blanksign);
        Board_State_by_linear_conflict lin = a_star_serach_by_linear_conflict(initstate, goalstate);
        System.out.println("Total Move(s) : " + lin.f);
        System.out.println("Node Expanded : " + linear_conflict_node_expanded);
        System.out.println("Node Explored : " + linear_conflict_node_explored + "\n");
        linear_conflict_path(initstate, lin);
    }

}
