package fx.geometry;

import fx.geometry.Path.WindingRule;
import fx.geometry.transform.BaseTransform;

import java.util.NoSuchElementException;


class LineIterator implements PathIterator {

    Line line;
    BaseTransform transform;
    int index;


    LineIterator(final Line LINE, final BaseTransform TRANSFORM) {
        line = LINE;
        transform = TRANSFORM;
    }


    public boolean isDone() {return (index > 1);}

    public void next() {++index;}

    public WindingRule getWindingRule() {return WindingRule.WIND_NON_ZERO;}

    public int currentSegment(final double[] COORDS) {
        if (isDone()) {
            throw new NoSuchElementException("line iterator out of bounds");
        }
        int type;
        if (index == 0) {
            COORDS[0] = line.x1;
            COORDS[1] = line.y1;
            type = MOVE_TO;
        } else {
            COORDS[0] = line.x2;
            COORDS[1] = line.y2;
            type = LINE_TO;
        }
        if (transform != null) {
            transform.transform(COORDS, 0, COORDS, 0, 1);
        }
        return type;
    }
}