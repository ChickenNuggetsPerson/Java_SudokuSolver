public class PercentageDisplay {

    private boolean initialized = false;

    private double min;
    private double max;
    private double val;

    private int charLength;

    private int count;

    public PercentageDisplay(String DisplayText, double minNumber, double maxNumber, double startValue, int width) {
        this.min = minNumber;
        this.max = maxNumber;
        this.val = startValue;
        this.charLength = width;

        initialized = true;

        System.out.println("");
        System.out.println(DisplayText);
        this.print();
    }

    public void print() {
        String pString = "";
        pString += "[";

        double currentPercent = (val - min) / (max - min);
        for (int i = 0; i < this.charLength - 2; i++) {
            double displayPercent = (double)i / (double)(this.charLength - 2);

            if (displayPercent <= currentPercent) {
                pString += "=";
            } else {
                pString += " ";
            }
        }
        pString += "] " + Math.floor(currentPercent * 100) / 100 + "% " + count;

        System.out.println(pString);
    }
    
    public void setNewVal(double newVal) {
        if (!initialized) { return; }
        count++;
        this.val = newVal;
        this.print();
    }
    
}