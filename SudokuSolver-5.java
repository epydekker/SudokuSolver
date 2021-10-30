/**
 * Class that solves the Asterisk Sudoku
 * prints the number of solutions of a Sudoku if there are multiple. If there is 
only a single solution, prints this solution instead.
 * by << Mark Shekhtman 1710133 >>
 * and <<Erik Dekker 1665049 >>
 * as group << 155 >>
 */

class SudokuSolver {

    // Size of the gird.
    int SUDOKU_SIZE = 9; 
    // Minimum digit to be filled in.               
    int SUDOKU_MIN_NUMBER = 1;
    // Maximum digit to be filled in.    
    int SUDOKU_MAX_NUMBER = 9;   
    // Dimension of the boxes (sub-girds that should contain all digits).  
    int SUDOKU_BOX_DIMENSION = 3; 
    
    int[][] grid = new int[][] {
    // The puzzle grid; 0 represents empty.
    { 0, 9, 0,  7, 3, 0,  4, 0, 0 }, // One solution.
    { 0, 0, 0,  0, 0, 0,  5, 0, 0 },
    { 3, 0, 0,  0, 0, 6,  0, 0, 0 },

    { 0, 0, 0,   0, 0, 2,  6, 4, 0 },
    { 0, 0, 0,   6, 5, 1,  0, 0, 0 },
    { 0, 0, 6,   9, 0, 7,  0, 0, 0 },

    { 5, 8, 0,   0, 0, 0,  0, 0, 0 },
    { 9, 0, 0,   0, 0, 3,  0, 2, 5 },
    { 6, 0, 3,   0, 0, 0,  8, 0, 0 }, 

    };
// Initialising a second array to store the grid that will 
// be printed if the sudoku has one solution. 
    int[][] foundSolution = new int[SUDOKU_SIZE][SUDOKU_SIZE];

int solutionCounter = 0; // Solution counter 

// Is there a conflict when we fill in d at position (r, c)?
boolean givesConflict(int r, int c, int d) {

    // IF statements thaat check whether the conditions of
    // the four conflict methods has been fulfilled.
    if (!rowConflict(r, d) && !columnConflict(c, d) &&
     !boxConflict(r, c, d) && !asteriskConflict(r, c, d)) {
        return false;
    }
    return true;
}

// Is there a conflict when we fill in d in row r?
boolean rowConflict(int r, int d) {

    // FOR loop passes through each row checking for 
    // duplicates of the number taken as a parameter.
    for (int i = 0; i < SUDOKU_SIZE; i++) {
        if (d == grid[r][i]) {
            return true;
        }
    }
    return false;
}

// Is there a conflict in column c when we fill in d?
boolean columnConflict(int c, int d) {

    // FOR loop passes through each column checking for 
    // duplicates of the number taken as a parameter.
    for (int i = 0; i < SUDOKU_SIZE; i++) {
        if (d == grid[i][c]) {
            return true;
        }
    }
    return false;
}

// Is there a conflict in the box at (r, c) when we fill in d?
boolean boxConflict(int r, int c, int d) {
    int rBox = r - r % 3;
    int cBox = c - c % 3;

    // FOR loop passes through each box (3*3) checking for 
    // duplicates of the number taken as a parameter.
    for (int i = rBox; i < rBox + 3; i++) {
        for (int j = cBox; j < cBox + 3; j++) {
            if (grid[i][j] == d) {
                return true;
            }
        }
    }
    return false;
}

// Is there a conflict in the asterisk when we fill in d?
boolean asteriskConflict(int row, int col, int d) {

    //IF statement identifies and segragate 
    // the postions of the asterisk numbers.
    if ((row == 2 && col == 2) || 
        (row == 1 && col == 4) || 
        (row == 2 && col == 6) ||
        (row == 4 && col == 1) || 
        (row == 4 && col == 4) || 
        (row == 4 && col == 7) ||
        (row == 6 && col == 2) || 
        (row == 6 && col == 6) ||
         (row == 7 && col == 4)) {

        
    // IF statement passes through each asterisk number postion 
    // checking for duplicates of the number taken as a parameter.    
        if (d == grid[2][2]) {
            return true;
        } else if (d == grid[1][4]) {
            return true;
        } else if (d == grid[2][6]) {
            return true;
        } else if (d == grid[4][1]) {
            return true;
        } else if (d == grid[4][4]) {
            return true;
        } else if (d == grid[4][7]) {
            return true;
        } else if (d == grid[6][2]) {
            return true;
        } else if (d == grid[6][6]) {
            return true;
        } else if (d == grid[7][4]) {
            return true;
        }
    }
    return false;
}

// Finds the next empty square (in "reading order").
int[] findEmptySquare() {

    // FOR loop passes through each position on the grid
    // in reading order, and sends back the coordinates of
    // the next empty space as elements in an array.
    for (int row = 0; row < SUDOKU_SIZE; row++) {
        for (int col = 0; col < SUDOKU_SIZE; col++) {
            if (grid[row][col] == 0) {
                return new int[] {
                    row,
                    col
                };
            }
        }
    }
    return new int[] {
        -1, -1
    };
}

// Find all solutions for the grid, and stores the final solution.
void solve() {

    // Initialising the arry that holds the position of the next empty space.
    int[] xy = findEmptySquare();

    // If the initial grid is already filled, it gets automatically printed. 
    if (xy[0] == -1 || xy[1] == -1) {
        this.print();
    }

    // Empty square identified by the previous method is filled
    // in with a number, and then we check for conflicts. 
    for (int i = SUDOKU_MIN_NUMBER; i <= SUDOKU_MAX_NUMBER; i++) {

        // IF the number i fits into the cell without conflicts
        // it is assigned to that position on the grid.
        if (!givesConflict(xy[0], xy[1], i)) {
            grid[xy[0]][xy[1]] = i;

            // Checking once again for an empty square. 
            int[] nextEmptySquare = findEmptySquare();


            // Checking if we have gone through the whole grid.
            if (nextEmptySquare[0] == -1 || nextEmptySquare[1] == -1) {
                solutionCounter++;


                // A copy of the solution that we have is made,
                // in case the sudoku puzzle only has one solution.
                for (int r = 0; r < SUDOKU_SIZE; r++) {
                    for (int c = 0; c < SUDOKU_SIZE; c++) {
                        foundSolution[r][c] = grid[r][c];
                    }
                }
                // We begin backtracking to find more solutions.
                grid[xy[0]][xy[1]] = 0;
                continue;
            }
            solve();
            grid[xy[0]][xy[1]] = 0;
        }
    }
}

// Print the sudoku grid.
void print() {

    // Array holding the '+' and '-' characters. 
    char[] plusMinus = new char[19];

    plusMinus[0] = '+';
    plusMinus[18] = '+';

    // Multiple IF statements formatting the grid for when it
    // is to be printed. 
    for (int i = 1; i < 18; i++) {
        plusMinus[i] = '-';
    }

    for (int i = 0; i < 9; i++) {

        if ((i == 0) || (i == 3) || (i == 6) || (i == 9)) {
            for (int m = 0; m < 19; m++) {
                System.out.print(plusMinus[m]);
            }
            System.out.println();
        }
        System.out.print("|");
        if (grid[i][0] != 0) {
            System.out.print(grid[i][0]);
        } else System.out.print(" ");
        if ((i == 4)) {
            System.out.print(">");
        } else
            System.out.print(" ");
        if (grid[i][1] != 0) {
            System.out.print(grid[i][1]);
        } else System.out.print(" ");
        if ((i == 2) || (i == 6)) {
            System.out.print(">");
        } else if ((i == 4)) {
            System.out.print("<");
        } else
            System.out.print(" ");
        if (grid[i][2] != 0) {
            System.out.print(grid[i][2]);
        } else System.out.print(" ");
        System.out.print("|");
        if (grid[i][3] != 0) {
            System.out.print(grid[i][3]);
        } else System.out.print(" ");
        if ((i == 1) || (i == 4) || (i == 7)) {
            System.out.print(">");
        } else
            System.out.print(" ");
        if (grid[i][4] != 0) {
            System.out.print(grid[i][4]);
        } else System.out.print(" ");
        if ((i == 1) || (i == 4) || (i == 7)) {
            System.out.print("<");
        } else
            System.out.print(" ");
        if (grid[i][5] != 0) {
            System.out.print(grid[i][5]);
        } else System.out.print(" ");
        System.out.print("|");
        if (grid[i][6] != 0) {
            System.out.print(grid[i][6]);
        } else System.out.print(" ");
        if ((i == 2) || (i == 6)) {
            System.out.print("<");
        } else if ((i == 4)) {
            System.out.print(">");
        } else
            System.out.print(" ");
        if (grid[i][7] != 0) {
            System.out.print(grid[i][7]);
        } else System.out.print(" ");
        if ((i == 4)) {
            System.out.print("<");
        } else
            System.out.print(" ");
        if (grid[i][8] != 0) {
            System.out.print(grid[i][8]);
        } else System.out.print(" ");
        System.out.print("|");

        System.out.println();
    }
    for (int m = 0; m < 19; m++) {
        System.out.print(plusMinus[m]);
    }
}

// Run the actual solver.
void solveIt() {

    // We solve the sudoku.
    this.solve();


    // Output the results of the solver method,
    // if there is only one solution.
    if (solutionCounter == 1) {
        for (int r = 0; r < SUDOKU_SIZE; r++) {
            for (int c = 0; c < SUDOKU_SIZE; c++) {
                grid[r][c] = foundSolution[r][c];
            }
        }
        this.print();
        // If there is more than one solution we print 
        // the number of solutions.
    } else if (solutionCounter > 1) {
        System.out.println(solutionCounter);
    }

}

public static void main(String[] args) {
    (new SudokuSolver()).solveIt();
    }
}

