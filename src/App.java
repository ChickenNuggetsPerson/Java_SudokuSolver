import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        ArgsParser parser = new ArgsParser();
        parser.parse(args);

        // Check for the file input parameter
        if (parser.getArgument("file") == null) { printUsage(); System.exit(0); }

        System.out.println("");
        System.out.println("");

        Solver solver = new Solver();
        
        solver.loadBoard(parser.getArgument("file"));

        if (parser.getArgument("solveAll") == null) {
            Board b = new Board();
            if (parser.getArgument("show") == null) {
                b = solver.solve();
            } else {
                b = solver.solve(true, Integer.parseInt(parser.getArgument("show")));
            }

            if (parser.getArgument("save") != null) {
                b.saveBoardToFile(parser.getArgument("save"));
            }
        } else {

            List<Board> boards = new ArrayList<>();
            if (parser.getArgument("show") == null) {
                boards = solver.solveForAll();
            } else {
                boards = solver.solveForAll(true, Integer.parseInt(parser.getArgument("show")));
            }

            System.out.println("");
            System.out.println("");
            System.out.println("-----------------------");
            System.out.println("Found " + boards.size() + " possilbe boards");
            System.out.println("");
            for (Board b: boards) {
                b.printBoard();
            }
            System.out.println("Found " + boards.size() + " possilbe boards");
        }

        
    }

    private static void printUsage() {
        String newLine = "\n";

        String printString = "";
        printString += newLine;
        printString += "Sudoku Solver" + newLine;
        printString += "-------------" + newLine;
        printString += "Usage: " + newLine;
        printString += newLine;

        printString += "- Required -" + newLine;
        printString += "-file [fileName]" + newLine;
        printString += "The path to the board CSV file" + newLine;
        printString += newLine;

        printString += "-solveAll true" + newLine;
        printString += "Solve for all possilbe solutions" + newLine;
        printString += newLine;

        printString += "- Optional -" + newLine;
        printString += "-save [fileName]" + newLine;
        printString += "The path to save the Board file to" + newLine;
        printString += newLine;

        printString += "-show [updateTime]" + newLine;
        printString += "Show the solving process with the update time interval in ms" + newLine;
        printString += newLine;

        System.out.println(printString);
    }
}
