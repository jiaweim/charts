package fx.chart.util;

import fx.chart.event.DefaultEventSource;
import fx.chart.event.type.BoundsEvent;

import java.util.Objects;

/**
 * This class define bounds
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 Jul 2025, 5:56 PM
 */
public class Bounds extends DefaultEventSource {

    private double x;
    private double y;
    private double width;
    private double height;

    public Bounds() {
        this(0, 0, 0, 0);
    }

    public Bounds(final double width, final double height) {
        this(0, 0, width, height);
    }

    public Bounds(final double x, final double y, final double width, final double height) {
        set(x, y, width, height);
    }


    public double getX() {return x;}

    public void setX(final double x) {
        this.x = x;
        fireChartEvent(new BoundsEvent(Bounds.this, BoundsEvent.BOUNDS, Bounds.this));
    }

    public double getY() {return y;}

    public void setY(final double y) {
        this.y = y;
        fireChartEvent(new BoundsEvent(Bounds.this, BoundsEvent.BOUNDS, Bounds.this));
    }

    public double getMinX() {return x;}

    public double getMaxX() {return x + width;}

    public double getMinY() {return y;}

    public double getMaxY() {return y + height;}

    public double getWidth() {return width;}

    public void setWidth(final double width) {
        this.width = Math.clamp(width, 0, Double.MAX_VALUE);
        fireChartEvent(new BoundsEvent(Bounds.this, BoundsEvent.BOUNDS, Bounds.this));
    }

    public double getHeight() {return height;}

    public void setHeight(final double height) {
        this.height = Math.clamp(height, 0, Double.MAX_VALUE);
        fireChartEvent(new BoundsEvent(Bounds.this, BoundsEvent.BOUNDS, Bounds.this));
    }

    public double getCenterX() {return x + width * 0.5;}

    public double getCenterY() {return y + height * 0.5;}

    public void set(final Bounds bounds) {
        set(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    public void set(final double x, final double y, final double width, final double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        fireChartEvent(new BoundsEvent(Bounds.this, BoundsEvent.BOUNDS, Bounds.this));
    }

    public boolean contains(final double x, final double y) {
        return (Double.compare(x, getMinX()) >= 0 && Double.compare(x, getMaxX()) <= 0 &&
                Double.compare(y, getMinY()) >= 0 && Double.compare(y, getMaxY()) <= 0);
    }

    public boolean intersects(final Bounds other) {
        return (other.getMaxX() >= getMinX() && other.getMaxY() >= getMinY() &&
                other.getMinX() <= getMaxX() && other.getMinY() <= getMaxY());
    }

    public boolean intersects(final double x, final double y, final double width, final double height) {
        return (x + width >= getMinX() && y + height >= getMinY() &&
                x <= getMaxX() && y <= getMaxY());
    }

    public Bounds copy() {return new Bounds(x, y, width, height);}

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj instanceof Bounds) {
            Bounds other = (Bounds) obj;
            return getX() == other.getX() &&
                    getY() == other.getY() &&
                    getWidth() == other.getWidth() &&
                    getHeight() == other.getHeight();
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }

    @Override
    public String toString() {
        return "{\"x\":" + getX()
                + ",\"y\":" + getY()
                + ",\"w\":" + getWidth()
                + ",\"h\":" + getHeight() + "}";
    }
}
