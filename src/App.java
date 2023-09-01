import java.awt.*;
import java.awt.event.*;

public class App extends Frame {
    public static void main(String[] args) throws Exception {

        System.out.println("");
        System.out.println("");

        Solver solver = new Solver();

        Board b = new Board();
        b.setNum(new BoardPos(5, 5), 1);
        b.setNum(new BoardPos(0, 2), 5);
        //solver.loadBoard(b);

        solver.loadBoard("./test2.csv");

        solver.solve(true, 25);
        //solver.solve();

        /*
        App gameView = new App();
        gameView.setVisible(true);
        gameView.setTitle("Sudoku Solver");
        gameView.setSize(400, 400);
        gameView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                gameView.dispose(); // use dispose method 
                System.exit(1);
            }
        });

        GridLayout experimentLayout = new GridLayout(9,9);
        Container grid = new Container();
        grid.setMaximumSize(new Dimension(400, 200));
        grid.setLayout(experimentLayout);
        for (int i = 0; i < 81; i++) {
            grid.add(new TextField(i));
        }
        gameView.add(grid);

        
        App controlView = new App();
        controlView.setVisible(true);
        controlView.setTitle("Sudoku Solver");
        controlView.setSize(400, 400);
        controlView.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                controlView.dispose(); // use dispose method 
                System.exit(1);
            }
        });
        Dimension windowSize = controlView.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();

        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        controlView.setLocation(dx, dy);
         */

    }

    void App() {}
}
