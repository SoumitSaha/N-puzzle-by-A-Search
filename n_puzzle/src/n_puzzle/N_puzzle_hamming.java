package n_puzzle;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class N_puzzle_hamming {

    public static int hamming_node_expanded;
    public static int hamming_node_explored;
    public static int blanksign;

    public static String value_to_key(Board_State_by_hamming st) {
        String str = "";
        for (int i = 0; i < st.row; i++) {
            for (int j = 0; j < st.col; j++) {
                str += st.state[i][j];
            }
        }
        return str;
    }

    public static Num_pos Hamming_getBlankpos(Board_State_by_hamming board) {
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

    public static Board_State_by_hamming hamming_blank_slide_up(Board_State_by_hamming board, Board_State_by_hamming goal) {
        Num_pos blank = Hamming_getBlankpos(board);
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
            Board_State_by_hamming tempboard = new Board_State_by_hamming(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_hamming Hamming_blank_slide_down(Board_State_by_hamming board, Board_State_by_hamming goal) {
        Num_pos blank = Hamming_getBlankpos(board);
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
            Board_State_by_hamming tempboard = new Board_State_by_hamming(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_hamming Hamming_blank_slide_left(Board_State_by_hamming board, Board_State_by_hamming goal) {
        Num_pos blank = Hamming_getBlankpos(board);
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
            Board_State_by_hamming tempboard = new Board_State_by_hamming(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_hamming Hamming_blank_slide_right(Board_State_by_hamming board, Board_State_by_hamming goal) {
        Num_pos blank = Hamming_getBlankpos(board);
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
            Board_State_by_hamming tempboard = new Board_State_by_hamming(temp, board.row, board.col, goal.state, blanksign);
            return tempboard;
        }
    }

    public static Board_State_by_hamming a_star_serach_by_hamming(Board_State_by_hamming initial, Board_State_by_hamming goal) {
        HashMap<String, Board_State_by_hamming> map = new HashMap<>();
        PriorityQueue<Board_State_by_hamming> q = new PriorityQueue<>();
        int cost = 0;
        initial.set_f(cost);
        q.add(initial);

        while (!q.isEmpty()) {

            Board_State_by_hamming cur_state = q.remove();
            hamming_node_expanded++;
            cost++;
            String cur_state_key = value_to_key(cur_state);
            map.put(cur_state_key, cur_state);
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

            Board_State_by_hamming up, down, left, right;
            up = hamming_blank_slide_up(cur_state, goal);
            down = Hamming_blank_slide_down(cur_state, goal);
            left = Hamming_blank_slide_left(cur_state, goal);
            right = Hamming_blank_slide_right(cur_state, goal);

            if (up != null) {
                String key = value_to_key(up);
                if (!map.containsKey(key)) {
                hamming_node_explored++;
                up.parent_state = cur_state;
                up.set_f(cur_state.g + 1);
                
                
                    q.add(up);
                    map.put(key, up);
                }
            }
            if (down != null) {
                String key = value_to_key(down);
                if (!map.containsKey(key)) {
                hamming_node_explored++;
                down.parent_state = cur_state;
                down.set_f(cur_state.g + 1);
                
                
                    q.add(down);
                    map.put(key, down);
                }
            }
            if (left != null) {
                String key = value_to_key(left);
                if (!map.containsKey(key)) {
                hamming_node_explored++;
                left.parent_state = cur_state;
                left.set_f(cur_state.g + 1);
                
                
                    q.add(left);
                    map.put(key, left);
                }
            }
            if (right != null) {
                String key = value_to_key(right);
                if (!map.containsKey(key)) {
                hamming_node_explored++;
                right.parent_state = cur_state;
                right.set_f(cur_state.g + 1);
                
                
                    q.add(right);
                    map.put(key, right);
                }
            }
        }
        return null;
    }

    public static void Hamming_path(Board_State_by_hamming start, Board_State_by_hamming end) {
        if (end == start) {
            System.out.println(start);
        } else if (end.parent_state == null) {
            System.out.println("No path.");
        } else {
            Hamming_path(start, end.parent_state);
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
        int init_nums[];
        init_nums = new int[(m * n)];
        int pos = 0;
        int zeropos_i = 0;
        System.out.println("Blank sign : ");
        blanksign = in.nextInt();
        System.out.println("Start board : ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                init_board[i][j] = in.nextInt();
                init_nums[pos] = init_board[i][j];
                pos++;
                if (init_board[i][j] == blanksign) {
                    zeropos_i = i;
                }
            }
        }
        pos = 0;
        HashMap<Integer, Integer> goalmap;
        goalmap = new HashMap<>();
        //System.out.println("");
        System.out.println("Goal board : ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                goal_board[i][j] = in.nextInt();
                goalmap.put(goal_board[i][j], pos);
                pos++;
            }
        }

        System.out.println("");
        System.out.println("---------- BY HAMMING DISTANCE ---------");

        int inversions = 0;
        for (int i = 0; i < (m * n); i++) {
            int num1 = init_nums[i];
            int num1_pos_ingoal = goalmap.get(num1);
            for (int j = i + 1; j < (m * n); j++) {
                int num2 = init_nums[j];
                int num2_pos_ingoal = goalmap.get(num2);
                if ((num1 != 0) && (num2 != 0) && (num2_pos_ingoal < num1_pos_ingoal)) {
                    inversions++;
                }
            }
        }

        Boolean canbesolved;
        if (m % 2 == 1) {
            canbesolved = (inversions % 2 == 0);
        }

        else{
            canbesolved = ((inversions + zeropos_i) % 2 == 1);
        }

        if (canbesolved) {

            Board_State_by_hamming initstate = new Board_State_by_hamming(init_board, m, n, goal_board, blanksign);
            Board_State_by_hamming goalstate = new Board_State_by_hamming(goal_board, m, n, goal_board, blanksign);
            Board_State_by_hamming ham = a_star_serach_by_hamming(initstate, goalstate);
            System.out.println("Total Move(s) : " + ham.f);
            System.out.println("Node Expanded : " + hamming_node_expanded);
            System.out.println("Node Explored : " + hamming_node_explored + "\n");
            Hamming_path(initstate, ham);
        }
        else{
            System.out.println("Can't be solved.");
        }
    }
}
