import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private static final String SET_PLAIN_TEXT = "\033[0;0m";
    private static final String SET_BOLD_TEXT = "\033[0;1m";
    
    private int[][] storage = new int[9][9];
    
    public Board() {};
    public Board(String filePath) {
        loadCSVData(filePath);
    }

    public void loadCSVData(String filePath) {
        System.out.println("Reading: " + filePath);
        try {
            File file = new File(filePath); // Replace with your file path
            Scanner scanner = new Scanner(file);
            
            int y = 0;
            while (scanner.hasNextLine()) {
                int x = 0;
                String line = scanner.nextLine();
                String[] nums = line.split(",");
                for (int i = 0; i < nums.length; i++) {
                    int num = Integer.parseInt(nums[i].trim());
                    storage[x][y] = num;
                    x++;
                }
                y++;
            }
            // Close the scanner
            scanner.close();
            System.out.println("Loaded Without Errors");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    public void loadFromOtherBoard(Board b) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                setNum(new BoardPos(x, y), b.getNum(new BoardPos(x, y)));
            }
        }
    }

    public int getNum(BoardPos pos) {
        return storage[pos.x][pos.y];
    }
    public void setNum(BoardPos pos, int newNumber) {
        storage[pos.x][pos.y] = newNumber;
    }

    public void applyChange(BoardChange change) {
        setNum(change.pos, change.newVal);
    }
    public void undoChange(BoardChange change) {
        setNum(change.pos, 0);
    }

    public List<BoardPos> getEmptySpots() {
        List<BoardPos> positions = new ArrayList<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (storage[x][y] == 0) {
                    positions.add(new BoardPos(x, y));
                }
            }
        }
        return positions;
    }

    public void printBoard() {
        System.out.println("");
        for (int y = 0; y < 9; y++) {
            String currentLine = "";
            for (int x = 0; x < 9; x++) {
                if (this.getNum(new BoardPos(x, y)) == 0) {
                    currentLine += (" -");
                } else {
                    currentLine += (" " + this.getNum(new BoardPos(x, y)));
                }
                
            }
            System.out.println(currentLine);
        }
    }
    public void printBoard(Board b) {
        System.out.println("");
        for (int y = 0; y < 9; y++) {
            String currentLine = "";
            for (int x = 0; x < 9; x++) {
                if (this.getNum(new BoardPos(x, y)) == b.getNum(new BoardPos(x, y))) {
                    if (this.getNum(new BoardPos(x, y)) == 0) {
                        currentLine += (" -");
                    } else {
                        currentLine += (" " + this.getNum(new BoardPos(x, y)));
                    }

                } else if (this.getNum(new BoardPos(x, y)) == 0) {
                    currentLine += (" " + SET_BOLD_TEXT + "-" + SET_PLAIN_TEXT);
                } else {
                        currentLine += (" " + SET_BOLD_TEXT + this.getNum(new BoardPos(x, y)) + SET_PLAIN_TEXT);
                }
            }
            System.out.println(currentLine);
        }
    }
}
