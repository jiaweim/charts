package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import javafx.beans.property.*;
import javafx.scene.paint.Color;


public class ValueChartItem extends DefaultEventSource implements ValueItem, Comparable<ValueChartItem> {

    private final ChartEvent ITEM_EVENT = new ChartEvent(ValueChartItem.this, ChartEvent.ITEM_UPDATE);

    private double _value;
    private DoubleProperty value;
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

    public ValueChartItem() {
        this(0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public ValueChartItem(final boolean IS_EMPTY) {
        this(0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public ValueChartItem(final double VALUE, final String NAME) {
        this(VALUE, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public ValueChartItem(final double VALUE, final String NAME, final boolean IS_EMPTY) {
        this(VALUE, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public ValueChartItem(final double VALUE, final String NAME, final Color FILL) {
        this(VALUE, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, false);
    }

    public ValueChartItem(final double VALUE, final String NAME, final Color FILL, final boolean IS_EMPTY) {
        this(VALUE, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, IS_EMPTY);
    }

    public ValueChartItem(final double VALUE, final String NAME, final Color FILL, final Color STROKE, final Symbol SYMBOL) {
        this(VALUE, NAME, FILL, STROKE, SYMBOL, false);
    }

    public ValueChartItem(final double VALUE, final String NAME, final Color FILL, final Color STROKE, final Symbol SYMBOL, final boolean IS_EMPTY) {
        _value = VALUE;
        _name = NAME;
        _fill = FILL;
        _stroke = STROKE;
        _symbol = SYMBOL;
        _isEmpty = IS_EMPTY;
    }


    // ******************** Methods ***************************************
    @Override
    public double getValue() {return null == value ? _value : value.get();}

    @Override
    public void setValue(final double VALUE) {
        if (null == value) {
            _value = VALUE;
            fireChartEvent(ITEM_EVENT);
        } else {
            value.set(VALUE);
        }
    }

    @Override
    public DoubleProperty valueProperty() {
        if (null == value) {
            value = new DoublePropertyBase(_value) {
                @Override
                protected void invalidated() {fireChartEvent(ITEM_EVENT);}

                @Override
                public Object getBean() {return ValueChartItem.this;}

                @Override
                public String getName() {return "value";}
            };
        }
        return value;
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
                public Object getBean() {return ValueChartItem.this;}

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
                public Object getBean() {return ValueChartItem.this;}

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
                public Object getBean() {return ValueChartItem.this;}

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
                public Object getBean() {return ValueChartItem.this;}

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
                public Object getBean() {return ValueChartItem.this;}

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
                .append("  \"value\":").append(getValue()).append(",\n")
                .append("  \"symbol\":\"").append(getSymbol().name()).append("\"\n")
                .append("}")
                .toString();
    }

    @Override
    public int compareTo(final ValueChartItem ITEM) {return Double.compare(getValue(), ITEM.getValue());}
}
