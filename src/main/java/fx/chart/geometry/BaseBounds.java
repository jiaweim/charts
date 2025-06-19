package fx.chart.geometry;


import fx.chart.toolboxfx.geom.Point;


public abstract class BaseBounds {

    public abstract BaseBounds copy();

    public abstract double getWidth();

    public abstract double getHeight();

    public abstract double getMinX();

    public abstract double getMinY();

    public abstract double getMaxX();

    public abstract double getMaxY();

    public abstract void translate(double x, double y, double z);

    public abstract Point getMin(Point min);

    public abstract Point getMax(Point max);

    public abstract BaseBounds deriveWithUnion(BaseBounds other);

    public abstract BaseBounds deriveWithNewBounds(Rectangle other);

    public abstract BaseBounds deriveWithNewBounds(BaseBounds other);

    public abstract BaseBounds deriveWithNewBounds(double minX, double minY, double maxX, double maxY);

    public abstract BaseBounds deriveWithNewBoundsAndSort(double minX, double minY, double maxX, double maxY);

    public abstract BaseBounds deriveWithPadding(double horizontal, double vertical);

    public abstract void intersectWith(Rectangle other);

    public abstract void intersectWith(BaseBounds other);

    public abstract void intersectWith(double minX, double minY, double maxX, double maxY);

    public abstract void setBoundsAndSort(Point p1, Point p2);

    public abstract void setBoundsAndSort(double minX, double minY, double maxX, double maxY);

    public abstract void add(Point p);

    public abstract void add(double x, double y);

    public abstract boolean contains(Point p);

    public abstract boolean contains(double x, double y);

    public abstract boolean intersects(double x, double y, double width, double height);

    public abstract boolean isEmpty();

    public abstract void roundOut();

    public abstract BaseBounds makeEmpty();

    public abstract boolean disjoint(double x, double y, double width, double height);

    protected abstract void sortMinMax();

    public static BaseBounds getInstance(double minX, double minY, double maxX, double maxY) {
        return new RectBounds(minX, minY, maxX, maxY);
    }
}