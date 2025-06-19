package fx.chart.geometry.tools;

import fx.chart.toolboxfx.geom.Point;
import fx.chart.geometry.Rectangle;

public interface Segment extends Cloneable {

    double minX();

    double maxX();

    double minY();

    double maxY();

    Rectangle getBounds2D();

    Point evalDt(double t);

    Point eval(double t);

    Segment getSegment(double t0, double t1);

    SplitResults split(double y);

    Segment splitBefore(double t);

    Segment splitAfter(double t);

    void subdivide(Segment s0, Segment s1);

    void subdivide(double t, Segment s0, Segment s1);

    double getLength();

    double getLength(double maxErr);


    class SplitResults {
        Segment[] above;
        Segment[] below;

        SplitResults(Segment[] below, Segment[] above) {
            this.below = below;
            this.above = above;
        }

        Segment[] getBelow() {
            return below;
        }

        Segment[] getAbove() {
            return above;
        }
    }
}
