package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.Item;
import fx.chart.data.XYChartItem;
import fx.chart.event.ChartEvt;
import fx.chart.event.EvtObserver;
import fx.chart.event.SeriesEvent;
import fx.chart.event.SeriesEventListener;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * data series
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 04 Jul 2025, 10:27 AM
 */
public abstract class Series<T extends Item> {

    public final SeriesEvent UPDATE_EVENT = new SeriesEvent(Series.this);

    protected String name_;
    protected StringProperty nameProperty;

    protected Paint fill_;
    protected ObjectProperty<Paint> fillProperty;

    protected Paint _stroke;
    protected ObjectProperty<Paint> strokeProperty;

    protected double _strokeWidth;
    protected DoubleProperty strokeWidthProperty;

    protected Color textFill_;
    protected ObjectProperty<Color> textFillProperty;

    protected Color _symbolFill;
    protected ObjectProperty<Color> symbolFillProperty;

    protected Color _symbolStroke;
    protected ObjectProperty<Color> symbolStrokeProperty;

    protected Symbol _symbol;
    protected ObjectProperty<Symbol> symbolProperty;

    protected boolean symbolsVisible_;
    protected BooleanProperty symbolsVisibleProperty;

    protected double _symbolSize;
    protected DoubleProperty symbolSizeProperty;

    protected boolean visible_;
    protected BooleanProperty visibleProperty;

    protected boolean _animated;
    protected BooleanProperty animatedProperty;

    protected long _animationDuration;
    protected LongProperty animationDurationProperty;

    //ADDED property to see if wrapping should be used or not by default false. Keeps the previous functionality the same.
    protected boolean _withWrapping;
    protected BooleanProperty withWrappingProperty;

    protected ChartType chartType_;
    protected ObservableList<T> items_;
    private final CopyOnWriteArrayList<SeriesEventListener> listeners_;
    private final ListChangeListener<T> itemListener_;
    private final EvtObserver<ChartEvt> itemObserver_;

    public Series() {
        this(null, ChartType.SCATTER, "", Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    @SafeVarargs
    public Series(final T... items) {
        this(Arrays.asList(items), ChartType.SCATTER, "", Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    @SafeVarargs
    public Series(final ChartType type, final T... items) {
        this(Arrays.asList(items), type, "", Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final ChartType TYPE, final String NAME, final T... ITEMS) {
        this(Arrays.asList(ITEMS), TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        this(ITEMS, TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Symbol SYMBOL) {
        this(ITEMS, TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, SYMBOL);
    }

    public Series(final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final Symbol SYMBOL, final T... ITEMS) {
        this(Arrays.asList(ITEMS), TYPE, NAME, FILL, STROKE, Color.BLACK, Color.BLACK, SYMBOL);
    }

    public Series(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final Symbol SYMBOL) {
        this(ITEMS, TYPE, NAME, FILL, STROKE, Color.BLACK, Color.BLACK, SYMBOL);
    }

    public Series(final List<T> items, final ChartType type, final String name, final Paint FILL, final Paint STROKE, final Color SYMBOL_FILL, final Color SYMBOL_STROKE, final Symbol SYMBOL) {
        name_ = name;
        fill_ = FILL;
        _stroke = STROKE;
        textFill_ = Color.BLACK;
        _symbolFill = SYMBOL_FILL;
        _symbolStroke = SYMBOL_STROKE;
        _symbol = SYMBOL;
        symbolsVisible_ = true;
        _symbolSize = -1;
        _strokeWidth = -1;
        visible_ = true;
        _animated = false;
        _animationDuration = 800;
        _withWrapping = false;
        chartType_ = type;
        items_ = FXCollections.observableArrayList();
        itemListener_ = change -> fireSeriesEvent(UPDATE_EVENT);
        itemObserver_ = e -> fireSeriesEvent(UPDATE_EVENT);
        listeners_ = new CopyOnWriteArrayList<>();

        if (null != items) {
            items_.setAll(items);
        }

        registerListeners();
    }


    // ******************** Initialization ************************************
    private void registerListeners() {
        items_.addListener((ListChangeListener<T>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(item -> {
                        if (item instanceof XYChartItem) {
                            XYChartItem xyChartItem = (XYChartItem) item;
                            xyChartItem.addChartEvtObserver(ChartEvt.ANY, itemObserver_);
                        }
                    });
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(item -> {
                        if (item instanceof XYChartItem) {
                            XYChartItem xyChartItem = (XYChartItem) item;
                            xyChartItem.removeChartEvtObserver(ChartEvt.ANY, itemObserver_);
                        }
                    });
                }
            }
        });
        items_.addListener(itemListener_);
    }

    /**
     * Get data items in this series
     *
     * @return data list
     */
    public ObservableList<T> getItems() {return items_;}

    public void setItems(final Collection<T> ITEMS) {items_.setAll(ITEMS);}

    public void setItems(final T... items) {setItems(Arrays.asList(items));}

    public void setItems(final List<T> ITEMS) {items_.setAll(ITEMS);}

    /**
     * @return name of this series
     */
    public String getName() {return null == nameProperty ? name_ : nameProperty.get();}

    public void setName(final String NAME) {
        if (null == nameProperty) {
            name_ = NAME;
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            nameProperty.set(NAME);
        }
    }

    public StringProperty nameProperty() {
        if (null == nameProperty) {
            nameProperty = new StringPropertyBase(name_) {
                @Override
                protected void invalidated() {fireSeriesEvent(UPDATE_EVENT);}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "name";}
            };
            name_ = null;
        }
        return nameProperty;
    }

    public Paint getFill() {return fillProperty == null ? fill_ : fillProperty.get();}

    public void setFill(final Paint PAINT) {
        if (null == fillProperty) {
            fill_ = PAINT;
            refresh();
        } else {
            fillProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> fillProperty() {
        if (null == fillProperty) {
            fillProperty = new ObjectPropertyBase<Paint>(fill_) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "fill";}
            };
            fill_ = null;
        }
        return fillProperty;
    }

    public Paint getStroke() {return null == strokeProperty ? _stroke : strokeProperty.get();}

    public void setStroke(final Paint PAINT) {
        if (null == strokeProperty) {
            _stroke = PAINT;
            refresh();
        } else {
            strokeProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> strokeProperty() {
        if (null == strokeProperty) {
            strokeProperty = new ObjectPropertyBase<Paint>(_stroke) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "stroke";}
            };
            _stroke = null;
        }
        return strokeProperty;
    }

    public Color getTextFill() {return null == textFillProperty ? textFill_ : textFillProperty.get();}

    public void setTextFill(final Color COLOR) {
        if (null == textFillProperty) {
            textFill_ = COLOR;
            refresh();
        } else {
            textFillProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> textFillProperty() {
        if (null == textFillProperty) {
            textFillProperty = new ObjectPropertyBase<>(textFill_) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "textFill";}
            };
            textFill_ = null;
        }
        return textFillProperty;
    }

    public Color getSymbolFill() {return null == symbolFillProperty ? _symbolFill : symbolFillProperty.get();}

    public void setSymbolFill(final Color COLOR) {
        if (null == symbolFillProperty) {
            _symbolFill = COLOR;
            refresh();
        } else {
            symbolFillProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> symbolFillProperty() {
        if (null == symbolFillProperty) {
            symbolFillProperty = new ObjectPropertyBase<>(_symbolFill) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "symbolFill";}
            };
            _symbolFill = null;
        }
        return symbolFillProperty;
    }

    public Color getSymbolStroke() {return null == symbolStrokeProperty ? _symbolStroke : symbolStrokeProperty.get();}

    public void setSymbolStroke(final Color COLOR) {
        if (null == symbolStrokeProperty) {
            _symbolStroke = COLOR;
            refresh();
        } else {
            symbolStrokeProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> symbolStrokeProperty() {
        if (null == symbolStrokeProperty) {
            symbolStrokeProperty = new ObjectPropertyBase<>(_symbolStroke) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "symbolStroke";}
            };
            _symbolStroke = null;
        }
        return symbolStrokeProperty;
    }

    public Symbol getSymbol() {return null == symbolProperty ? _symbol : symbolProperty.get();}

    public void setSymbol(final Symbol aSymbol) {
        if (null == symbolProperty) {
            _symbol = aSymbol;
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            symbolProperty.set(aSymbol);
        }
    }

    public ObjectProperty<Symbol> symbolProperty() {
        if (null == symbolProperty) {
            symbolProperty = new ObjectPropertyBase<>(_symbol) {
                @Override
                protected void invalidated() {fireSeriesEvent(UPDATE_EVENT);}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "symbol";}
            };
            _symbol = null;
        }
        return symbolProperty;
    }

    public boolean getSymbolsVisible() {return symbolsVisibleProperty == null ? symbolsVisible_ : symbolsVisibleProperty.get();}

    public void setSymbolsVisible(final boolean visible) {
        if (symbolsVisibleProperty == null) {
            symbolsVisible_ = visible;
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            symbolsVisibleProperty.set(visible);
        }
    }

    public BooleanProperty symbolsVisibleProperty() {
        if (null == symbolsVisibleProperty) {
            symbolsVisibleProperty = new BooleanPropertyBase(symbolsVisible_) {
                @Override
                protected void invalidated() {fireSeriesEvent(UPDATE_EVENT);}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "symbolsVisible";}
            };
        }
        return symbolsVisibleProperty;
    }

    public ChartType getChartType() {return chartType_;}

    public void setChartType(final ChartType TYPE) {
        chartType_ = TYPE;
        refresh();
    }

    public double getSymbolSize() {return null == symbolSizeProperty ? _symbolSize : symbolSizeProperty.get();}

    public void setSymbolSize(final double SIZE) {
        if (null == symbolSizeProperty) {
            _symbolSize = Math.clamp(SIZE, 1, 24);
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            symbolSizeProperty.set(SIZE);
        }
    }

    public DoubleProperty symbolSizeProperty() {
        if (null == symbolSizeProperty) {
            symbolSizeProperty = new DoublePropertyBase(_symbolSize) {
                @Override
                protected void invalidated() {
                    set(Math.clamp(get(), 1, 24));
                    fireSeriesEvent(UPDATE_EVENT);
                }

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "symbolSize";}
            };
        }
        return symbolSizeProperty;
    }

    public double getStrokeWidth() {return null == strokeWidthProperty ? _strokeWidth : strokeWidthProperty.get();}

    public void setStrokeWidth(final double WIDTH) {
        if (null == strokeWidthProperty) {
            _strokeWidth = Math.clamp(WIDTH, 1, 24);
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            strokeWidthProperty.set(WIDTH);
        }
    }

    public DoubleProperty strokeWidthProperty() {
        if (null == strokeWidthProperty) {
            strokeWidthProperty = new DoublePropertyBase(_strokeWidth) {
                @Override
                protected void invalidated() {
                    set(Math.clamp(get(), 1, 24));
                    fireSeriesEvent(UPDATE_EVENT);
                }

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "strokeWidth";}
            };
        }
        return strokeWidthProperty;
    }

    public boolean isVisible() {return null == visibleProperty ? visible_ : visibleProperty.get();}

    public void setVisible(final boolean visible) {
        if (null == this.visibleProperty) {
            visible_ = visible;
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            this.visibleProperty.set(visible);
        }
    }

    public BooleanProperty visibleProperty() {
        if (null == visibleProperty) {
            visibleProperty = new BooleanPropertyBase(visible_) {
                @Override
                protected void invalidated() {fireSeriesEvent(UPDATE_EVENT);}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "visible";}
            };
        }
        return visibleProperty;
    }

    public boolean isAnimated() {return null == animatedProperty ? _animated : animatedProperty.get();}

    public void setAnimated(final boolean ANIMATED) {
        if (null == animatedProperty) {
            _animated = ANIMATED;
        } else {
            animatedProperty.set(ANIMATED);
        }
    }

    public BooleanProperty animatedProperty() {
        if (null == animatedProperty) {
            animatedProperty = new BooleanPropertyBase(_animated) {
                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "animated";}
            };
        }
        return animatedProperty;
    }

    public long getAnimationDuration() {return null == animationDurationProperty ? _animationDuration : animationDurationProperty.get();}

    public void setAnimationDuration(final long DURATION) {
        if (null == animationDurationProperty) {
            _animationDuration = Math.clamp(DURATION, 10, 10000);
        } else {
            animationDurationProperty.set(Math.clamp(DURATION, 10, 10000));
        }
    }

    public LongProperty animationDurationProperty() {
        if (null == animationDurationProperty) {
            animationDurationProperty = new LongPropertyBase(_animationDuration) {
                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "animationDuration";}
            };
        }
        return animationDurationProperty;
    }

    // ADDED accessors for the withWrapping boolean value and associated property.

    public boolean isWithWrapping() {return null == withWrappingProperty ? _withWrapping : withWrappingProperty.get();}

    public void setWithWrapping(final boolean WITH_WRAPPING) {
        if (null == withWrappingProperty) {
            _withWrapping = WITH_WRAPPING;
            fireSeriesEvent(UPDATE_EVENT);
        } else {
            withWrappingProperty.set(WITH_WRAPPING);
        }
    }

    public BooleanProperty withWrappingProperty() {
        if (null == withWrappingProperty) {
            withWrappingProperty = new BooleanPropertyBase(_withWrapping) {
                @Override
                protected void invalidated() {fireSeriesEvent(UPDATE_EVENT);}

                @Override
                public Object getBean() {return Series.this;}

                @Override
                public String getName() {return "withWrapping";}
            };
        }
        return symbolsVisibleProperty;
    }

    public int getNoOfItems() {return items_.size();}

    public void dispose() {items_.remove(itemListener_);}

    public void refresh() {fireSeriesEvent(UPDATE_EVENT);}


    // ******************** Event handling ************************************
    public void setOnSeriesEvent(final SeriesEventListener LISTENER) {addSeriesEventListener(LISTENER);}

    public void addSeriesEventListener(final SeriesEventListener LISTENER) {
        if (!listeners_.contains(LISTENER)) listeners_.add(LISTENER);
    }

    public void removeSeriesEventListener(final SeriesEventListener LISTENER) {
        if (listeners_.contains(LISTENER)) listeners_.remove(LISTENER);
    }

    public void fireSeriesEvent(final SeriesEvent EVENT) {
        for (SeriesEventListener listener : listeners_) {
            listener.onModelEvent(EVENT);
        }
    }
}
