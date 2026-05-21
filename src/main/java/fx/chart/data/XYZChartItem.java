package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import javafx.beans.property.*;
import javafx.scene.paint.Color;


public class XYZChartItem extends DefaultEventSource implements XYZItem {

    private final ChartEvent ITEM_EVENT = new ChartEvent(XYZChartItem.this, ChartEvent.ITEM_UPDATE);
    private double _x;
    private DoubleProperty x;
    private double _y;
    private DoubleProperty y;
    private double _z;
    private DoubleProperty z;
    private String _name;
    private StringProperty name;
    private Color _fill;
    private ObjectProperty<Color> fill;
    private Color _stroke;
    private ObjectProperty<Color> stroke;
    private Symbol _symbol;
    private ObjectProperty<Symbol> symbol;
    private boolean _isEmpty;
    private BooleanProperty isEmpty;


    public XYZChartItem() {
        this(0, 0, 0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public XYZChartItem(final boolean IS_EMPTY) {
        this(0, 0, 0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public XYZChartItem(final double X, final double Y, final double Z) {
        this(X, Y, Z, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final boolean IS_EMPTY) {
        this(X, Y, Z, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME) {
        this(X, Y, Z, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final boolean IS_EMPTY) {
        this(X, Y, Z, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL) {
        this(X, Y, Z, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL, final boolean IS_EMPTY) {
        this(X, Y, Z, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL, final Color STROKE) {
        this(X, Y, Z, NAME, FILL, STROKE, Symbol.NONE, false);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL, final Color STROKE, final boolean IS_EMPTY) {
        this(X, Y, Z, NAME, FILL, STROKE, Symbol.NONE, IS_EMPTY);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL, final Color STROKE, final Symbol SYMBOL) {
        this(X, Y, Z, NAME, FILL, STROKE, SYMBOL, false);
    }

    public XYZChartItem(final double X, final double Y, final double Z, final String NAME, final Color FILL, final Color STROKE, final Symbol SYMBOL, final boolean IS_EMPTY) {
        _x = X;
        _y = Y;
        _z = Z;
        _name = NAME;
        _fill = FILL;
        _stroke = STROKE;
        _symbol = SYMBOL;
        _isEmpty = IS_EMPTY;
    }


    // ******************** Methods ***************************************
    @Override
    public double getX() {return null == x ? _x : x.get();}

    @Override
    public void setX(final double X) {
        if (null == x) {
            _x = X;
            fireChartEvent(ITEM_EVENT);
        } else {
            x.set(X);
        }
    }

    @Override
    public DoubleProperty xProperty() {
        if (null == x) {
            x = new DoublePropertyBase(_x) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "x";}
            };
        }
        return x;
    }

    @Override
    public double getY() {return null == y ? _y : y.get();}

    @Override
    public void setY(final double Y) {
        if (null == y) {
            _y = Y;
            fireChartEvent(ITEM_EVENT);
        } else {
            y.set(Y);
        }
    }

    @Override
    public DoubleProperty yProperty() {
        if (null == y) {
            y = new DoublePropertyBase(_y) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "y";}
            };
        }
        return y;
    }

    @Override
    public double getZ() {return null == z ? _z : z.get();}

    @Override
    public void setZ(final double Z) {
        if (null == z) {
            _z = Z;
            fireChartEvent(ITEM_EVENT);
        } else {
            z.set(Z);
        }
    }

    @Override
    public DoubleProperty zProperty() {
        if (null == z) {
            z = new DoublePropertyBase(_z) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "z";}
            };
        }
        return z;
    }

    @Override
    public String getName() {return null == name ? _name : name.get();}

    public void setName(final String NAME) {
        if (null == name) {
            _name = NAME;
            fireChartEvent(ITEM_EVENT);
        } else {
            name.set(NAME);
        }
    }

    public StringProperty nameProperty() {
        if (null == name) {
            name = new StringPropertyBase(_name) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "name";}
            };
            _name = null;
        }
        return name;
    }

    @Override
    public Color getFillColor() {return null == fill ? _fill : fill.get();}

    public void setFill(final Color FILL) {
        if (null == fill) {
            _fill = FILL;
            fireChartEvent(ITEM_EVENT);
        } else {
            fill.set(FILL);
        }
    }

    public ObjectProperty<Color> fillProperty() {
        if (null == fill) {
            fill = new ObjectPropertyBase<Color>(_fill) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "fill";}
            };
            _fill = null;
        }
        return fill;
    }

    @Override
    public Color getStrokeColor() {return null == stroke ? _stroke : stroke.get();}

    public void setStroke(final Color STROKE) {
        if (null == stroke) {
            _stroke = STROKE;
            fireChartEvent(ITEM_EVENT);
        } else {
            stroke.set(STROKE);
        }
    }

    public ObjectProperty<Color> strokeProperty() {
        if (null == stroke) {
            stroke = new ObjectPropertyBase<Color>(_stroke) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "stroke";}
            };
            _stroke = null;
        }
        return stroke;
    }

    @Override
    public Symbol getSymbol() {return null == symbol ? _symbol : symbol.get();}

    public void setSymbol(final Symbol SYMBOL) {
        if (null == symbol) {
            _symbol = SYMBOL;
            fireChartEvent(ITEM_EVENT);
        } else {
            symbol.set(SYMBOL);
        }
    }

    public ObjectProperty<Symbol> symbolProperty() {
        if (null == symbol) {
            symbol = new ObjectPropertyBase<Symbol>(_symbol) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "symbol";}
            };
            _symbol = null;
        }
        return symbol;
    }

    @Override
    public boolean isEmptyItem() {return null == isEmpty ? _isEmpty : isEmpty.get();}

    public void setIsEmpty(final boolean isEmpty) {
        if (null == this.isEmpty) {
            _isEmpty = isEmpty;
            fireChartEvent(ITEM_EVENT);
        } else {
            this.isEmpty.set(isEmpty);
        }
    }

    public BooleanProperty isEmptyProperty() {
        if (null == isEmpty) {
            isEmpty = new BooleanPropertyBase(_isEmpty) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYZChartItem.this;}

                @Override
                public String getName() {return "isEmpty";}
            };
        }
        return isEmpty;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{\n")
                .append("  \"name\":\"").append(getName()).append("\",\n")
                .append("  \"x\":").append(getX()).append(",\n")
                .append("  \"y\":").append(getY()).append(",\n")
                .append("  \"z\":").append(getZ()).append(",\n")
                .append("  \"symbol\":\"").append(getSymbol().name()).append("\"\n")
                .append("}")
                .toString();
    }
}
