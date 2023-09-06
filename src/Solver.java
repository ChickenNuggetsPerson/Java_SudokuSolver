import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

    private class Square {
        public BoardPos pos = new BoardPos();
        public int value = 0;
        public List<Integer> possibleVals = new ArrayList<>();
    };


    private Board storeBoard = new Board();
    private Board tempBoard = new Board();

    public Solver() {}
    void loadBoard(String filePath) {
        storeBoard.loadCSVData(filePath);
        tempBoard = storeBoard;

    }
    void loadBoard(Board b) {
        storeBoard = b;
        tempBoard = b;
    }

    // Box Related Functions

    // Get the box from a position in the box
    private int getBoxFromPos(BoardPos pos) {
        int x = pos.x;
        int y = pos.y;

        if ( x < 3 && y < 3 ) { return 0; }
        if ( x > 2 && x < 6 && y < 3) { return 1; }
        if ( x > 5 && y < 3) { return 2; }

        if ( x < 3 && y > 2 && y < 6 ) { return 3; }
        if ( x > 2 && x < 6 && y > 2 && y < 6 ) { return 4; }
        if ( x > 5 && y > 2 && y < 6) { return 5; }

        if ( x < 3 && y > 5 ) { return 6; }
        if ( x > 2 && x < 6 && y > 5 ) { return 7; }
        if ( x > 5 && y > 5 ) { return 8; }

        return -1;
    }
    // Get a list of positions that are in the box
    private List<BoardPos> getPosesFromBox(Board b, int index) {
        List<BoardPos> positions = new ArrayList<>();
        int xOffset = index % 3;
        int yOffset = index / 3;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                positions.add( new BoardPos((xOffset * 3) + x, (yOffset * 3) + y));
            }
        }
        return positions;
    }

    // Board Validation Functions: returns true if it is valid
    public boolean validateBoard(Board b, boolean finalCheck) {
        for (int i = 0; i < 9; i++) {
            if (!validateRow(b, i)) { return false; }
        }
        for (int i = 0; i < 9; i++) {
            if (!validateCol(b, i)) { return false; }
        }
        for (int i = 0; i < 9; i++) {
            if (!validateBox(b, i)) { return false; }
        }
        if (finalCheck) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    if (b.getNum(new BoardPos(x, y)) == 0) {
                        return false;
                    };
                }
            }    
        }
        
        return true;
    }
    private boolean validateRow(Board b, int row) {
        
        Set<Integer> nums = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            int num = b.getNum(new BoardPos(i, row));
            if (nums.contains(num)) {
                return false;
            }
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return true;
    }
    private boolean validateCol(Board b, int col) {
        Set<Integer> nums = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            int num = b.getNum(new BoardPos(col, i));
            if (nums.contains(num)) {
                return false;
            }
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return true;
    }
    private boolean validateBox(Board b, int index) {
        /*
         *    Box Indexs
         * 
         *    0     1     2
         *    
         *    3     4     5
         * 
         *    6     7     8
         * 
         */

        Set<Integer> nums = new HashSet<>();

        List<BoardPos> positions = getPosesFromBox(b, index);

        for (int i = 0; i < positions.size(); i++) {
            int num = b.getNum(new BoardPos(positions.get(i).x, positions.get(i).y));
            if (nums.contains(num)) {
                return false;
            }
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return true;
    }
    
    // Board Retreival Functions
    private List<Integer> getRow(Board b, int row) {
        
        List<Integer> nums = new ArrayList<>();
    
        for (int i = 0; i < 9; i++) {
            int num = b.getNum(new BoardPos(i, row));
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return nums;
    }
    private List<Integer> getCol(Board b, int col) {
       List<Integer> nums = new ArrayList<>();
    
        for (int i = 0; i < 9; i++) {
            int num = b.getNum(new BoardPos(col, i));
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return nums;
    }
    private List<Integer> getBox(Board b, int index) {
        List<Integer> nums = new ArrayList<>();

        List<BoardPos> positions = getPosesFromBox(b, index);

        for (int i = 0; i < positions.size(); i++) {
            int num = b.getNum(new BoardPos(positions.get(i).x, positions.get(i).y));
            if (num == 0) { continue; }
            nums.add(num);
        }
        
        return nums;
    }
    
    private List<Integer> getPossivleVals(Board b, BoardPos pos) {
        Set<Integer> vals = new HashSet<>();
        List<Integer> possibleVals = new ArrayList<>();
    
        if (b.getNum(pos) != 0) { return possibleVals; }

        List<Integer> rowVals = getRow(b, pos.y);
        List<Integer> colVals = getCol(b, pos.x);
        List<Integer> boxVals = getBox(b, getBoxFromPos(pos));

        for (int val: rowVals) {
            if (vals.contains(val)) { continue; }
            vals.add(val);
        }
        for (int val: colVals) {
            if (vals.contains(val)) { continue; }
            vals.add(val);
        }
        for (int val: boxVals) {
            if (vals.contains(val)) { continue; }
            vals.add(val);
        }

        for (int i = 1; i < 10; i++) {
            if (!vals.contains(i)) {
                possibleVals.add(i);
            }
        }
        return possibleVals;
    }

    private boolean recursiveSolve(boolean showSolving, int delayMS, boolean solveAll, double quitTime) {

        if (System.currentTimeMillis() > quitTime) {
            tempBoard = new Board();
            return false;
        }

        // Get Empty Positions
        List<BoardPos> emptyPoses = tempBoard.getEmptySpots();

        // Create a list of square objects from the empty postions
        List<Square> squares = new ArrayList<>(); 
        for (BoardPos pos: emptyPoses) {
            Square box = new Square();
            box.pos = pos;
            box.value = tempBoard.getNum(pos);
            box.possibleVals = getPossivleVals(tempBoard, pos);
            squares.add(box);
        }

        // Find the index with the smallest value
        int runningLength = 100;
        int runningIndex = -1;
        for (int i = 0; i < squares.size(); i++) {
            if (squares.get(i).possibleVals.isEmpty()) { continue; }
            if (squares.get(i).possibleVals.size() > runningLength) { continue; }
            runningLength = squares.get(i).possibleVals.size();
            runningIndex = i;
        }
        // If there are empty spots but no availabe spaces then return false
        if (runningIndex == -1 && !emptyPoses.isEmpty()) {
            return false;
        }


        // If there are no avaialbe numbers, return true
        if (runningIndex == -1) { 
            if (solveAll) {
                Board b = new Board();
                b.loadFromOtherBoard(tempBoard);
                allAnswers.add(b);
                return false;
            }   
            return true; 
        }

        // Loop through possible numbers, and loop further down the recursion loop
        // If the baord fails later on, it will go back and try other solutions
        for (int i = 0; i < squares.get(runningIndex).possibleVals.size(); i++) {
            // Createa a change object and apply it to the board
            BoardChange change = new BoardChange(squares.get(runningIndex).pos, squares.get(runningIndex).possibleVals.get(i));
            tempBoard.applyChange(change);

            // Show the solving steps if the user wants it
            if (showSolving) {
                System.out.println("\n\n\n\n");
                tempBoard.printBoard();
                try {
                    Thread.sleep(delayMS, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // Loop further down the tree
            boolean result = recursiveSolve(showSolving, delayMS, solveAll, quitTime);
            // If the further down loop finished without errors and the board is valid, return true
            // Otherwise undo the change and try the next possible number
            if (result && validateBoard(tempBoard, true)) {
                if (solveAll) {
                    Board b = new Board();
                    b.loadFromOtherBoard(tempBoard);
                    allAnswers.add(b);
                    return false;
                }   
                return true;
            } else {
                tempBoard.undoChange(change);
            }
        }

        // When in doubt, its an invalid board
        return false;
    }

    public Board solve() {
        return solve(false, 0, true, 100000);
    }
    public Board solve(boolean showSolving, int delayMS, boolean printingLogs, int timeOut) {
        
        // Validate the board
        if (printingLogs) {
             System.out.println("");
            if (!validateBoard(storeBoard, false)) { System.out.println("Board Is Not Valid"); return new Board(); }
            System.out.println("Board Is Valid");
            System.out.println("");
            
            tempBoard.printBoard();
        }


        // Main recursive solving logic
        recursiveSolve(showSolving, delayMS, false, System.currentTimeMillis() + (double)(timeOut * 1000));

        // Double check the board to make sure it is valid
        if (printingLogs) {
            if (!validateBoard(tempBoard, true)) { System.out.println("Could not find answer"); return new Board(); }

            System.out.println("");
            System.out.println("Done Solving");

            tempBoard.printBoard(storeBoard);
        }
    
        return tempBoard;
    }


    List<Board> allAnswers = new ArrayList<>();
    public List<Board> solveForAll() {
        return solveForAll(false, 0, true, 1000000);
    }
    public List<Board> solveForAll(boolean showSolving, int delayMS, boolean printingLogs, int timeOut) {
        
        // Validate the board
        if (printingLogs) {
            System.out.println("");
            if (!validateBoard(storeBoard, false)) { System.out.println("Board Is Not Valid"); return allAnswers; }
            System.out.println("Board Is Valid");
            System.out.println("");
        }
        

        tempBoard.printBoard();

        // Main recursive solving logic
        recursiveSolve(showSolving, delayMS, true, System.currentTimeMillis() + (double)(timeOut * 1000));

        // Double check the board to make sure it is valid
        if (printingLogs) {
             if (!validateBoard(tempBoard, true)) { System.out.println("Could not find answer"); return allAnswers; }

            System.out.println("");
            System.out.println("Done Solving");

            tempBoard.printBoard(storeBoard);
        }
       

        return allAnswers;
    }
}
