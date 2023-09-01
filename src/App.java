
public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("");
        System.out.println("");

        Solver solver = new Solver();

        Board b = new Board();
        b.setNum(new BoardPos(5, 5), 1);
        b.setNum(new BoardPos(0, 2), 5);
        //solver.loadBoard(b);

        solver.loadBoard("./test3.csv");

        solver.solve(true, 10);
        //solver.solve();
    }
}
