public class Station {
    String nameStation;
    LineMetro lineMetro;

    @Override
    public String toString() {
        return "Station{" +
                "nameStation='" + nameStation + '\'' +
                ", lineMetro=" + lineMetro +
                '}';
    }

    public String getNameStation() {
        return nameStation;
    }

    public LineMetro getLineMetro() {
        return lineMetro;
    }

    public Station(String nameStation, LineMetro lineMetro) {
        this.nameStation = nameStation;
        this.lineMetro = lineMetro;
    }
}
