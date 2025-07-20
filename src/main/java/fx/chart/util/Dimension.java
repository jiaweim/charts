package fx.chart.util;

public class Dimension {
    private int hash;
    private double width;
    private double height;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(final double width, final double height) {
        this.hash = 0;
        this.width = width;
        this.height = height;
    }

    public double getWidth() {return width;}

    public void setWidth(final double width) {this.width = width;}

    public double getHeight() {return height;}

    public void setHeight(final double height) {this.height = height;}

    public double getCenterX() {return width * 0.5;}

    public double getCenterY() {return height * 0.5;}

    public Point getCenter() {return new Point(width * 0.5, height * 0.5);}

    public void set(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    public void set(final Dimension dimension) {
        this.width = dimension.getWidth();
        this.height = dimension.getHeight();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Dimension other) {
            return getWidth() == other.getWidth() && getHeight() == other.getHeight();
        } else return false;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getWidth());
            bits = 31L * bits + Double.doubleToLongBits(getHeight());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    @Override
    public String toString() {
        return "{\"w\":" + getWidth()
                + ",\"h\":" + getHeight() + "}";
    }
}
