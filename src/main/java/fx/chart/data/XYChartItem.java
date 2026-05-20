package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.event.EventType;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A 2D chart data point
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 03 Jul 2025, 2:16 PM
 */
public class XYChartItem implements XYItem, Comparable<XYChartItem> {

    private final ChartEvent ITEM_EVENT = new ChartEvent(XYChartItem.this, ChartEvent.ITEM_UPDATE);
    private final Map<EventType, List<ChartEventListener<ChartEvent>>> observers;

    private double x_;
    private DoubleProperty xProperty;
    private double y_;
    private DoubleProperty yProperty;
    private String name_;
    private StringProperty nameProperty;
    private Color fill_;
    private ObjectProperty<Color> fillProperty;
    private Color stroke_;
    private ObjectProperty<Color> strokeProperty;
    private Symbol symbol_;
    private ObjectProperty<Symbol> symbolProperty;
    private boolean isEmpty_;
    private BooleanProperty isEmptyProperty;
    private String tooltipText_;
    private StringProperty tooltipTextProperty;

    public XYChartItem() {
        this(0, 0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final boolean isEmpty) {
        this(0, 0, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, "", isEmpty);
    }

    public XYChartItem(final double X, final double Y) {
        this(X, Y, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double X, final double Y, final boolean IS_EMPTY) {
        this(X, Y, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, "", IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final Color FILL) {
        this(X, Y, "", FILL, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double X, final double Y, final Color FILL, final String TOOLTIP) {
        this(X, Y, "", FILL, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, false);
    }

    public XYChartItem(final double X, final double Y, final Color FILL, final boolean IS_EMPTY) {
        this(X, Y, "", FILL, Color.TRANSPARENT, Symbol.NONE, "", IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final Color FILL, final String TOOLTIP, final boolean IS_EMPTY) {
        this(X, Y, "", FILL, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final String NAME) {
        this(X, Y, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final String TOOLTIP) {
        this(X, Y, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, false);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final boolean IS_EMPTY) {
        this(X, Y, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, "", IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final String TOOLTIP, final boolean IS_EMPTY) {
        this(X, Y, NAME, Color.RED, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final Color FILL) {
        this(X, Y, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final Color FILL, final String TOOLTIP) {
        this(X, Y, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, false);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final Color FILL, final boolean IS_EMPTY) {
        this(X, Y, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, "", IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final Color FILL, final String TOOLTIP, final boolean IS_EMPTY) {
        this(X, Y, NAME, FILL, Color.TRANSPARENT, Symbol.NONE, TOOLTIP, IS_EMPTY);
    }

    public XYChartItem(final double X, final double Y, final String NAME, final Color FILL, final Color STROKE, final Symbol SYMBOL) {
        this(X, Y, NAME, FILL, STROKE, SYMBOL, false);
    }

    public XYChartItem(final double x, final double y, final String name, final Color fill, final Color stroke, final Symbol symbol, final boolean isEmpty) {
        this(x, y, name, fill, stroke, symbol, "", isEmpty);
    }

    /**
     * Create a data item
     *
     * @param x       x value
     * @param y       y value
     * @param name    data name
     * @param fill    fill color
     * @param stroke  stroke
     * @param symbol  {@link Symbol}
     * @param tooltip tooltip text
     * @param isEmpty true if it is an empty data
     */
    public XYChartItem(final double x, final double y, final String name,
            final Color fill, final Color stroke, final Symbol symbol, final String tooltip, final boolean isEmpty) {
        x_ = x;
        y_ = y;
        name_ = name;
        fill_ = fill;
        stroke_ = stroke;
        symbol_ = symbol;
        isEmpty_ = isEmpty;
        tooltipText_ = tooltip;
        observers = new ConcurrentHashMap<>();
    }

    @Override
    public double getX() {return null == xProperty ? x_ : xProperty.get();}

    @Override
    public void setX(final double X) {
        if (null == xProperty) {
            x_ = X;
            fireChartEvt(ITEM_EVENT);
        } else {
            xProperty.set(X);
        }
    }

    @Override
    public DoubleProperty xProperty() {
        if (null == xProperty) {
            xProperty = new DoublePropertyBase(x_) {
                @Override
                protected void invalidated() {
                    fireChartEvt(ITEM_EVENT);
                }

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "x";}
            };
        }
        return xProperty;
    }

    @Override
    public double getY() {return null == yProperty ? y_ : yProperty.get();}

    @Override
    public void setY(final double Y) {
        if (null == yProperty) {
            y_ = Y;
            fireChartEvt(ITEM_EVENT);
        } else {
            yProperty.set(Y);
        }
    }

    @Override
    public DoubleProperty yProperty() {
        if (null == yProperty) {
            yProperty = new DoublePropertyBase(y_) {
                @Override
                protected void invalidated() {
                    fireChartEvt(ITEM_EVENT);
                }

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "y";}
            };
        }
        return yProperty;
    }

    @Override
    public String getName() {return null == nameProperty ? name_ : nameProperty.get();}

    public void setName(final String NAME) {
        if (null == nameProperty) {
            name_ = NAME;
            fireChartEvt(ITEM_EVENT);
        } else {
            nameProperty.set(NAME);
        }
    }

    public StringProperty nameProperty() {
        if (null == nameProperty) {
            nameProperty = new StringPropertyBase(name_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "name";}
            };
            name_ = null;
        }
        return nameProperty;
    }

    @Override
    public Color getFill() {return null == fillProperty ? fill_ : fillProperty.get();}

    public void setFill(final Color FILL) {
        if (null == fillProperty) {
            fill_ = FILL;
            fireChartEvt(ITEM_EVENT);
        } else {
            fillProperty.set(FILL);
        }
    }

    public ObjectProperty<Color> fillProperty() {
        if (null == fillProperty) {
            fillProperty = new ObjectPropertyBase<Color>(fill_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "fill";}
            };
            fill_ = null;
        }
        return fillProperty;
    }

    @Override
    public Color getStroke() {return null == strokeProperty ? stroke_ : strokeProperty.get();}

    public void setStroke(final Color STROKE) {
        if (null == strokeProperty) {
            stroke_ = STROKE;
            fireChartEvt(ITEM_EVENT);
        } else {
            strokeProperty.set(STROKE);
        }
    }

    public ObjectProperty<Color> strokeProperty() {
        if (null == strokeProperty) {
            strokeProperty = new ObjectPropertyBase<Color>(stroke_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "stroke";}
            };
            stroke_ = null;
        }
        return strokeProperty;
    }

    @Override
    public Symbol getSymbol() {return null == symbolProperty ? symbol_ : symbolProperty.get();}

    public void setSymbol(final Symbol SYMBOL) {
        if (null == symbolProperty) {
            symbol_ = SYMBOL;
            fireChartEvt(ITEM_EVENT);
        } else {
            symbolProperty.set(SYMBOL);
        }
    }

    public ObjectProperty<Symbol> symbolProperty() {
        if (null == symbolProperty) {
            symbolProperty = new ObjectPropertyBase<Symbol>(symbol_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "symbol";}
            };
            symbol_ = null;
        }
        return symbolProperty;
    }

    @Override
    public String getTooltipText() {return null == tooltipTextProperty ? tooltipText_ : tooltipTextProperty.get();}

    @Override
    public void setTooltipText(final String TOOLTIP) {
        if (null == tooltipTextProperty) {
            tooltipText_ = TOOLTIP;
            fireChartEvt(ITEM_EVENT);
        } else {
            tooltipTextProperty.set(TOOLTIP);
        }
    }

    @Override
    public StringProperty tooltipTextProperty() {
        if (null == tooltipTextProperty) {
            tooltipTextProperty = new StringPropertyBase(tooltipText_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "tooltip";}
            };
            tooltipText_ = null;
        }
        return tooltipTextProperty;
    }

    @Override
    public boolean isEmptyItem() {return null == isEmptyProperty ? isEmpty_ : isEmptyProperty.get();}

    public void setIsEmpty(final boolean isEmpty) {
        if (null == this.isEmptyProperty) {
            isEmpty_ = isEmpty;
            fireChartEvt(ITEM_EVENT);
        } else {
            this.isEmptyProperty.set(isEmpty);
        }
    }

    public BooleanProperty isEmptyProperty() {
        if (null == isEmptyProperty) {
            isEmptyProperty = new BooleanPropertyBase(isEmpty_) {
                @Override
                protected void invalidated() {fireChartEvt(ITEM_EVENT);}

                @Override
                public Object getBean() {return XYChartItem.this;}

                @Override
                public String getName() {return "isEmpty";}
            };
        }
        return isEmptyProperty;
    }

    public void addChartEventObserver(final EventType type, final ChartEventListener<ChartEvent> observer) {
        if (!observers.containsKey(type)) {
            observers.put(type, new CopyOnWriteArrayList<>());
        }
        if (observers.get(type).contains(observer)) {
            return;
        }
        observers.get(type).add(observer);
    }

    public void removeChartEventObserver(final EventType type, final ChartEventListener<ChartEvent> observer) {
        if (observers.containsKey(type)) {
            if (observers.get(type).contains(observer)) {
                observers.get(type).remove(observer);
            }
        }
    }

    public void removeAllChartEvtObservers() {observers.clear();}

    public void fireChartEvt(final ChartEvent evt) {
        final EventType type = evt.getEventType();
        observers.entrySet().stream().filter(entry -> entry.getKey().equals(ChartEvent.ANY)).forEach(entry -> entry.getValue().forEach(observer -> observer.handle(evt)));
        if (observers.containsKey(type) && !type.equals(ChartEvent.ANY)) {
            observers.get(type).forEach(observer -> observer.handle(evt));
        }
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"name\":\"" + getName() + "\",\n" +
                "  \"x\":" + getX() + ",\n" +
                "  \"y\":" + getY() + ",\n" +
                "  \"symbol\":\"" + getSymbol().name() + "\"\n" +
                "}";
    }

    @Override
    public int compareTo(final XYChartItem ITEM) {return Double.compare(getX(), ITEM.getX());}
}
