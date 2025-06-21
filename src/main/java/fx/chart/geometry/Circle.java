package fx.chart.geometry;

public class Circle extends Ellipse {

    public Circle(final double CENTER_X, final double CENTER_Y, final double RADIUS) {
        super(CENTER_X - RADIUS, CENTER_Y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    public double getRadius() {return getRadiusX();}

    public void setRadius(final double radius) {
        setRadiusX(radius);
        setRadiusY(radius);
    }
}
