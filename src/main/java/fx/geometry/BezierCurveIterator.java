package fx.geometry;

import fx.geometry.Path.WindingRule;
import fx.geometry.transform.BaseTransform;

import java.util.NoSuchElementException;


class BezierCurveIterator implements PathIterator {

    BezierCurve bezierCurve;
    BaseTransform transform;
    int index;


    BezierCurveIterator(BezierCurve bezierCurve, BaseTransform transform) {
        this.bezierCurve = bezierCurve;
        this.transform = transform;
    }


    public boolean isDone() {
        return (index > 1);
    }

    public void next() {
        ++index;
    }

    public WindingRule getWindingRule() {return WindingRule.WIND_NON_ZERO;}

    public int currentSegment(final double[] COORDS) {
        if (isDone()) {
            throw new NoSuchElementException("bezierCurve iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            COORDS[0] = bezierCurve.x1;
            COORDS[1] = bezierCurve.y1;
            type = MOVE_TO;
        } else {
            COORDS[0] = bezierCurve.ctrlx1;
            COORDS[1] = bezierCurve.ctrly1;
            COORDS[2] = bezierCurve.ctrlx2;
            COORDS[3] = bezierCurve.ctrly2;
            COORDS[4] = bezierCurve.x2;
            COORDS[5] = bezierCurve.y2;
            type = BEZIER_TO;
        }
        if (transform != null) {
            transform.transform(COORDS, 0, COORDS, 0, index == 0 ? 1 : 3);
        }
        return type;
    }
}