package fx.geometry;

import fx.geometry.Path.WindingRule;


public interface PathIterator {

    int MOVE_TO = 0;
    int LINE_TO = 1;
    int QUAD_TO = 2;
    int BEZIER_TO = 3;
    int CLOSE = 4;

    WindingRule getWindingRule();

    boolean isDone();

    void next();

    int currentSegment(double[] coords);
}