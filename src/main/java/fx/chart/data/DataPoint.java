package fx.chart.data;


import fx.chart.toolboxfx.geom.Point;


public class DataPoint extends Point {
    private double value;

    public DataPoint() {
        this(0, 0, 0);
    }

    public DataPoint(final double X, final double Y) {
        this(X, Y, 0);
    }

    public DataPoint(final double X, final double Y, final double VALUE) {
        super(X, Y);
        value = VALUE;
    }


    // ******************** Methods *******************************************
    public double getValue() {return value;}

    public void setValue(final double VALUE) {value = VALUE;}

    @Override
    public String toString() {
        return new StringBuilder().append("x: ").append(getX()).append(", y: ").append(getY()).append(", value: ").append(value).toString();
    }
}
