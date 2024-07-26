public class LineMetro {
    public String numberLine;
    public String nameLine;

    @Override
    public String toString() {
        return "Line{" +
                "numberLine=" + numberLine +
                ", nameLine='" + nameLine + '\'' +
                '}';
    }

    public void setNumberLine(String  numberLine) {
        this.numberLine = numberLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    public LineMetro(String numberLine, String nameLine) {
        this.numberLine = numberLine;
        this.nameLine = nameLine;
    }

}
