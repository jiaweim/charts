package fx.chart.util;

public class CornerRadii {

    private double topLeft;
    private double topRight;
    private double bottomRight;
    private double bottomLeft;

    public CornerRadii() {
        this(0, 0, 0, 0);
    }

    public CornerRadii(final double radius) {
        this(radius, radius, radius, radius);
    }

    public CornerRadii(final double topLeft, final double topRight,
            final double bottomRight, final double bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;
    }

    public double getTopLeft() {return topLeft;}

    public void setTopLeft(final double value) {topLeft = Math.clamp(value, 0, Double.MAX_VALUE);}

    public double getTopRight() {return topRight;}

    public void setTopRight(final double value) {topRight = Math.clamp(value, 0, Double.MAX_VALUE);}

    public double getBottomRight() {return bottomRight;}

    public void setBottomRight(final double value) {bottomRight = Math.clamp(value, 0, Double.MAX_VALUE);}

    public double getBottomLeft() {return bottomLeft;}

    public void setBottomLeft(final double value) {bottomLeft = Math.clamp(value, 0, Double.MAX_VALUE);}

    @Override
    public String toString() {
        return "{\"topLeft\":" + getTopLeft()
                + ",\"topRight\":" + getTopRight()
                + ",\"bottomRight\":" + getBottomRight()
                + ",\"bottomLeft\":" + getBottomLeft() + "}";
    }
}
