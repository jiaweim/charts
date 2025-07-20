package fx.chart.event;


import fx.chart.util.Point;

public class PixelMatrixEvt {

    private final int X;
    private final int Y;
    private final double MOUSE_SCREEN_X;
    private final double MOUSE_SCREEN_Y;


    public PixelMatrixEvt(final int X, final int Y, final double MOUSE_X, final double MOUSE_Y) {
        this.X = X;
        this.Y = Y;
        this.MOUSE_SCREEN_X = MOUSE_X;
        this.MOUSE_SCREEN_Y = MOUSE_Y;
    }

    public int getX() {return X;}

    public int getY() {return Y;}

    public double getMouseScreenX() {return MOUSE_SCREEN_X;}

    public double getMouseScreenY() {return MOUSE_SCREEN_Y;}

    public Point getMouseScreenPos() {return new Point(MOUSE_SCREEN_X, MOUSE_SCREEN_Y);}
}