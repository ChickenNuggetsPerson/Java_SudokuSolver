import java.util.ArrayList;
import java.util.List;

public class Generator {

    private Board startBoard = new Board();
    private Board workingBoard = new Board();
    
    private Solver internalSolver = new Solver();

    private int difficulty;
    private String outFile;

    public Generator() {}
    public void setConfig(int difficulty) {
        this.difficulty = difficulty;
        this.outFile = null;
        if (difficulty <= 0 || difficulty > 10) {
            throw new Error("Difficulty Out of Range: 1-10. Inputed number: " + difficulty);
        }
    }
    public void setConfig(int difficulty, String fileName) {
        this.difficulty = difficulty;
        this.outFile = fileName;
        if (difficulty <= 0 || difficulty > 10) {
            throw new Error("Difficulty Out of Range: 1-10. Inputed number: " + difficulty);
        }
    }

    private int randomIntFromRange(int min, int max) {
        return (int)Math.round(Math.random() * (double)(max - min)) + min;
    }
    public boolean recursiveGenerate(int desiredClues) {
        workingBoard.printBoard();
        Board beforeState = new Board();
        beforeState.loadFromOtherBoard(workingBoard);

        internalSolver.loadBoard(workingBoard);
        Board b = internalSolver.solve(false, 0, false, 10);
        if (!internalSolver.validateBoard(b, true)) { return false; }

        workingBoard.loadFromOtherBoard(beforeState);;
        System.out.println(workingBoard.getZerosLeft() + " " + desiredClues);
        while (workingBoard.getZerosLeft() != 0) {

            if ((81 - workingBoard.getZerosLeft() <= desiredClues)) { return true; }

            BoardPos pos = new BoardPos(randomIntFromRange(0, 8), randomIntFromRange(0, 8));
            BoardChange change = new BoardChange(pos, workingBoard.getNum(pos));

            workingBoard.undoChange(change); // Set the number to 0

            if (recursiveGenerate(desiredClues)) {
                return true;
            }
            workingBoard.applyChange(change);

            internalSolver.loadBoard(workingBoard);
        }

        return false;
    }

    public void generateBoard() {

        System.out.println("Generating Random Board... ");
        System.out.println("Tries: 0 ");
        int tries = 0;

        Board tmpBaord = new Board();

        while (!internalSolver.validateBoard(tmpBaord, true)) {

            startBoard.randomize(20);
            while (!internalSolver.validateBoard(startBoard, false)) {
                startBoard.randomize(20);
            }
            workingBoard.loadFromOtherBoard(startBoard); // Load the valid board into the working board

            internalSolver.loadBoard(workingBoard);
            tmpBaord = internalSolver.solve(false, 0, false, 1); // Solve the board

            tries++;
            System.out.println("Tries: " + tries);
        }
        tmpBaord.printBoard();
        workingBoard.loadFromOtherBoard(tmpBaord);

        int totalClues = 19 + (int)Math.round((11 - difficulty) * 1.7);

        // Remove random number to start proccess
        BoardPos pos = new BoardPos(randomIntFromRange(0, 8), randomIntFromRange(0, 8));
        BoardChange change = new BoardChange(pos, workingBoard.getNum(pos));
        workingBoard.undoChange(change); // Set the number to 0
        
        recursiveGenerate(totalClues); // Start recursion loop


        System.out.println("Finished Generating");
        System.out.println("-------------------");
        System.out.println("");
        workingBoard.printBoard();
        if (this.outFile != null) {
            workingBoard.saveBoardToFile(this.outFile);
        }
    }


}
