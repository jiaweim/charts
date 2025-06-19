package fx.chart.toolboxfx.geom;

public class CatmullRom<T extends Point> {

    private CatmullRomSpline splineXValues;
    private CatmullRomSpline splineYValues;

    public CatmullRom(final T P0, final T P1, final T P2, final T P3) {
        if (P0 == null) {
            throw new AssertionError("p0 cannot be null");
        }
        if (P1 == null) {
            throw new AssertionError("p1 cannot be null");
        }
        if (P2 == null) {
            throw new AssertionError("p2 cannot be null");
        }
        if (P3 == null) {
            throw new AssertionError("p3 cannot be null");
        }

        splineXValues = new CatmullRomSpline(P0.getX(), P1.getX(), P2.getX(), P3.getX());
        splineYValues = new CatmullRomSpline(P0.getY(), P1.getY(), P2.getY(), P3.getY());
    }


    public T q(final double T) {return (T) new Point(splineXValues.q(T), splineYValues.q(T));}


    class CatmullRomSpline {
        private double p0;
        private double p1;
        private double p2;
        private double p3;


        protected CatmullRomSpline(final double P0, final double P1, final double P2, final double P3) {
            p0 = P0;
            p1 = P1;
            p2 = P2;
            p3 = P3;
        }

        protected double q(final double T) {
            return 0.5 * ((2 * p1) + (p2 - p0) * T + (2 * p0 - 5 * p1 + 4 * p2 - p3) * T * T + (3 * p1 - p0 - 3 * p2 + p3) * T * T * T);
        }
    }
}
