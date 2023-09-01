public class BoardChange {
    public BoardPos pos;
    public int newVal;
    public BoardChange() {};
    public BoardChange(BoardPos position, int newVal) {
        this.pos = position;
        this.newVal = newVal;
    }
};