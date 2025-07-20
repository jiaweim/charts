package fx.chart.event;

import fx.chart.util.Point;

public class CursorEvent {

    private final double x;
    private final double y;

    public CursorEvent(final double X, final double Y) {
        this.x = X;
        this.y = Y;
    }

    public double getX() {return x;}

    public double getY() {return y;}

    public Point getXY() {return new Point(x, y);}
}
