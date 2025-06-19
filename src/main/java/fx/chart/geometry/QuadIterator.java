package fx.chart.geometry;

import fx.chart.geometry.Path.WindingRule;
import fx.chart.geometry.transform.BaseTransform;

import java.util.NoSuchElementException;

class QuadIterator implements PathIterator {

    QuadCurve quadCurve;
    BaseTransform transform;
    int index;


    QuadIterator(final QuadCurve QUAD_CURVE, final BaseTransform TRANSFORM) {
        quadCurve = QUAD_CURVE;
        transform = TRANSFORM;
    }


    public WindingRule getWindingRule() {return WindingRule.WIND_NON_ZERO;}

    public boolean isDone() {return (index > 1);}

    public void next() {++index;}

    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("quadCurve iterator iterator out of bounds");
        }
        int type;
        if (index == 0) {
            coords[0] = quadCurve.x1;
            coords[1] = quadCurve.y1;
            type = MOVE_TO;
        } else {
            coords[0] = quadCurve.ctrlx;
            coords[1] = quadCurve.ctrly;
            coords[2] = quadCurve.x2;
            coords[3] = quadCurve.y2;
            type = QUAD_TO;
        }
        if (transform != null) {
            transform.transform(coords, 0, coords, 0, index == 0 ? 1 : 2);
        }
        return type;
    }
}