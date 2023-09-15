import javax.swing.*;
import java.awt.*;

public class SudokuVisualizer {

    private static final int WINDOW_SIZE = 540;
    private static final int CELL_SIZE = WINDOW_SIZE / 9;

public static void drawGrid(Graphics g) {
    g.setColor(Color.WHITE); // Set the color to white for the grid lines
    
    for (int x = 0; x <= WINDOW_SIZE; x += CELL_SIZE) {
        if (x % (3 * CELL_SIZE) == 0) {
            g.fillRect(x, 0, 2, WINDOW_SIZE); // Thicker line for 3x3 borders
        } else {
            g.drawLine(x, 0, x, WINDOW_SIZE);
        }
    }
    
    for (int y = 0; y <= WINDOW_SIZE; y += CELL_SIZE) {
        if (y % (3 * CELL_SIZE) == 0) {
            g.fillRect(0, y, WINDOW_SIZE, 2); // Thicker line for 3x3 borders
        } else {
            g.drawLine(0, y, WINDOW_SIZE, y);
        }
    }
}

public static void displayBoard(Graphics g, int[][] board) {
    g.setColor(Color.WHITE); // Set the color to white for the text
    g.setFont(new Font("Arial", Font.PLAIN, 36));
    
    for (int y = 0; y < board.length; y++) {
        for (int x = 0; x < board[y].length; x++) {
            if (board[y][x] != 0) {
                String number = String.valueOf(board[y][x]);
                g.drawString(number, x * CELL_SIZE + CELL_SIZE / 3, y * CELL_SIZE + 2 * CELL_SIZE / 3);
            }
        }
    }
}


    public static boolean is_valid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = 3 * (row / 3);
        int startCol = 3 * (col / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean solve_sudoku(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (is_valid(board, i, j, num)) {
                            board[i][j] = num;
                            if (solve_sudoku(board)) {
                                return true;
                            } else {
                                board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        if (solve_sudoku(board)) {
            System.out.println("Solved");
        } else {
            System.out.println("No solution exists");
        }

        JFrame frame = new JFrame("Sudoku Visualization");
        frame.setSize(WINDOW_SIZE, WINDOW_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.BLACK); // Set the background color to black
                g.fillRect(0, 0, WINDOW_SIZE, WINDOW_SIZE); // Fill the background
                drawGrid(g);
                displayBoard(g, board);
            }
        };
    
        frame.add(panel);
    }
}
