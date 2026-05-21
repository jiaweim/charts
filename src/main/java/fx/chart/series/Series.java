package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.Item;
import fx.chart.data.XYChartItem;
import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.event.SeriesEvent;
import fx.chart.event.SeriesEventListener;
import fx.chart.property.*;
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
 * @version 1.1.0
 * @since 04 Jul 2025, 10:27 AM
 */
public abstract class Series<T extends Item> {

    private static final Runnable EMPTY = () -> {
    };

    public final SeriesEvent UPDATE_EVENT;

    protected StringLProperty name_;
    protected ObjectLProperty<Paint> fill_;
    protected ObjectLProperty<Paint> stroke_;
    protected DoubleLProperty strokeWidth_;
    protected ObjectLProperty<double[]> lineDashes_;
    protected ObjectLProperty<Color> textFill_;
    protected ObjectLProperty<Color> symbolFill_;
    protected ObjectLProperty<Color> symbolStroke_;
    protected ObjectLProperty<Symbol> symbol_;
    protected BooleanLProperty symbolsVisible_;
    protected DoubleLProperty symbolSize_;
    protected BooleanLProperty visible_;
    protected BooleanLProperty animated_;
    protected LongLProperty animationDuration_;
    protected BooleanLProperty withWrapping_;

    protected ChartType chartType_;
    protected ObservableList<T> items_;
    private final CopyOnWriteArrayList<SeriesEventListener> listeners_;
    private final ListChangeListener<T> itemListener_;
    /**
     * Listener for item change to trigger update.
     */
    private final ChartEventListener<ChartEvent> itemObserver_;
    private final ListChangeListener<T> listChangeListener_;

    /**
     * Create an empty Series.
     */
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

    public Series(final List<T> items, final ChartType type) {
        this(items, type, "", Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final ChartType type, final String name, final T... items) {
        this(Arrays.asList(items), type, name, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final List<T> items, final ChartType type, final String name) {
        this(items, type, name, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, Symbol.CIRCLE);
    }

    public Series(final List<T> items, final ChartType type, final String name, final Symbol symbol) {
        this(items, type, name, Color.TRANSPARENT, Color.BLACK, Color.BLACK, Color.BLACK, symbol);
    }

    public Series(final ChartType type, final String name, final Paint fill, final Paint stroke, final Symbol symbol, final T... items) {
        this(Arrays.asList(items), type, name, fill, stroke, Color.BLACK, Color.BLACK, symbol);
    }

    public Series(final List<T> items, final ChartType type, final String name, final Paint fill, final Paint stroke, final Symbol symbol) {
        this(items, type, name, fill, stroke, Color.BLACK, Color.BLACK, symbol);
    }

    /**
     * Create a series.
     *
     * @param items        The data contained in this series.
     * @param type         {@link ChartType}
     * @param name         name of the series
     * @param fill
     * @param stroke
     * @param symbolFill
     * @param symbolStroke
     * @param symbol
     */
    public Series(final List<T> items, final ChartType type, final String name,
            final Paint fill, final Paint stroke, final Color symbolFill, final Color symbolStroke, final Symbol symbol) {
        UPDATE_EVENT = new SeriesEvent(this);

        this.name_ = new StringLProperty(this, "name", name, () -> fireSeriesEvent(UPDATE_EVENT));
        this.fill_ = new ObjectLProperty<>(this, "fill", fill, this::refresh);
        this.stroke_ = new ObjectLProperty<>(this, "stroke", stroke, this::refresh);
        this.strokeWidth_ = new DoubleLProperty(this, "strokeWidth", -1,
                () -> fireSeriesEvent(UPDATE_EVENT),
                width -> width == -1 ? -1 : Math.clamp(width, 1, 24));
        this.lineDashes_ = new ObjectLProperty<>(this, "dashes", null, this::refresh);
        this.textFill_ = new ObjectLProperty<>(this, "textFill", Color.BLACK, this::refresh);
        this.symbolFill_ = new ObjectLProperty<>(this, "symbolFill", symbolFill, this::refresh);
        this.symbolStroke_ = new ObjectLProperty<>(this, "symbolStroke", symbolStroke, this::refresh);
        this.symbol_ = new ObjectLProperty<>(this, "symbol", symbol, () -> fireSeriesEvent(UPDATE_EVENT));
        this.symbolSize_ = new DoubleLProperty(this, "symbolSize", -1, () -> fireSeriesEvent(UPDATE_EVENT),
                v -> v == -1 ? -1 : Math.clamp(v, 1, 24));
        this.symbolsVisible_ = new BooleanLProperty(this, "symbolsVisible", true, () -> fireSeriesEvent(UPDATE_EVENT));
        this.visible_ = new BooleanLProperty(this, "visible", true, () -> fireSeriesEvent(UPDATE_EVENT));
        this.animated_ = new BooleanLProperty(this, "animated", false, EMPTY);
        this.animationDuration_ = new LongLProperty(this, "animationDuration", 800L, EMPTY, time -> Math.clamp(time, 10, 10000));
        this.withWrapping_ = new BooleanLProperty(this, "withWrapping", false, () -> fireSeriesEvent(UPDATE_EVENT));

        chartType_ = type;
        items_ = FXCollections.observableArrayList();

        itemListener_ = change -> fireSeriesEvent(UPDATE_EVENT);
        itemObserver_ = e -> fireSeriesEvent(UPDATE_EVENT);
        listeners_ = new CopyOnWriteArrayList<>();

        if (items != null) {
            items_.setAll(items);
        }

        listChangeListener_ = c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(item -> {
                        if (item instanceof XYChartItem xyChartItem) {
                            xyChartItem.addEventListener(ChartEvent.ANY, itemObserver_);
                        }
                    });
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(item -> {
                        if (item instanceof XYChartItem xyChartItem) {
                            xyChartItem.removeEventListener(ChartEvent.ANY, itemObserver_);
                        }
                    });
                }
            }
        };

        registerListeners();
    }

    private void registerListeners() {
        items_.addListener(listChangeListener_);
        items_.addListener(itemListener_);
    }

    /**
     * Get data items in this series
     *
     * @return data list
     */
    public ObservableList<T> getItems() {return items_;}

    /**
     * Set the data elements contained in this series.
     *
     * @param items data items.
     */
    public void setItems(final Collection<? extends T> items) {items_.setAll(items);}

    /**
     * Set the data elements contained in this series.
     *
     * @param items data items.
     */
    @SafeVarargs
    public final void setItems(final T... items) {setItems(Arrays.asList(items));}

    /**
     * Set the data elements contained in this series.
     *
     * @param items data items.
     */
    public void setItems(final List<T> items) {items_.setAll(items);}

    /**
     * @return name of this series
     */
    public String getName() {
        return name_.get();
    }

    /**
     * Set the name of this series
     *
     * @param name new name
     */
    public void setName(final String name) {
        this.name_.set(name);
    }

    /**
     * Return the series name as Property type.
     *
     * @return {@link StringProperty} of series name.
     */
    public StringProperty nameProperty() {
        return this.name_.getProperty();
    }

    /**
     *
     * @return the fill {@link Paint}
     */
    public Paint getFill() {
        return this.fill_.get();
    }

    /**
     * Set the fill {@link Paint}.
     *
     * @param paint {@link Paint}.
     */
    public void setFill(final Paint paint) {
        this.fill_.set(paint);
    }

    /**
     * Return the fill Paint as Property type.
     *
     * @return {@link ObjectProperty} of fill Paint.
     */
    public ObjectProperty<Paint> fillProperty() {
        return fill_.getProperty();
    }

    /**
     * @return {@link Paint} for stroke
     */
    public Paint getStroke() {
        return stroke_.get();
    }

    /**
     * set the stroke.
     *
     * @param paint {@link Paint}
     */
    public void setStroke(final Paint paint) {
        this.stroke_.set(paint);
    }

    public ObjectProperty<Paint> strokeProperty() {
        return this.stroke_.getProperty();
    }

    /**
     * @return dashes for stoke
     */
    public double[] getLineDashes() {
        return lineDashes_.get();
    }

    /**
     * set dashes property
     *
     * @param dashes dashes value
     */
    public void setLineDashes(double... dashes) {
        this.lineDashes_.set(dashes);
    }

    public ObjectProperty<double[]> lineDashesProperty() {
        return this.lineDashes_.getProperty();
    }

    public Color getTextFill() {
        return this.textFill_.get();
    }

    public void setTextFill(final Color color) {
        this.textFill_.set(color);
    }

    public ObjectProperty<Color> textFillProperty() {
        return this.textFill_.getProperty();
    }

    public Color getSymbolFill() {
        return this.symbolFill_.get();
    }

    public void setSymbolFill(final Color color) {
        this.symbolFill_.set(color);
    }

    public ObjectProperty<Color> symbolFillProperty() {
        return this.symbolFill_.getProperty();
    }

    public Color getSymbolStroke() {
        return this.symbolStroke_.get();
    }

    public void setSymbolStroke(final Color color) {
        this.symbolStroke_.set(color);
    }

    public ObjectProperty<Color> symbolStrokeProperty() {
        return this.symbolStroke_.getProperty();
    }

    public Symbol getSymbol() {
        return this.symbol_.get();
    }

    public void setSymbol(final Symbol symbol) {
        this.symbol_.set(symbol);
    }

    public ObjectProperty<Symbol> symbolProperty() {
        return this.symbol_.getProperty();
    }

    public boolean getSymbolsVisible() {
        return this.symbolsVisible_.get();
    }

    public void setSymbolsVisible(final boolean visible) {
        this.symbolsVisible_.set(visible);
    }

    public BooleanProperty symbolsVisibleProperty() {
        return symbolsVisible_.getProperty();
    }

    public ChartType getChartType() {return chartType_;}

    public void setChartType(final ChartType chartType) {
        chartType_ = chartType;
        refresh();
    }

    /**
     * Return the symbol size.
     *
     * @return symbol size.
     */
    public double getSymbolSize() {
        return this.symbolSize_.getAsDouble();
    }

    /**
     * Set the size of the symbol.
     *
     * @param size symbol size.
     */
    public void setSymbolSize(final double size) {
        this.symbolSize_.set(size);
    }

    public DoubleProperty symbolSizeProperty() {
        return this.symbolSize_.getProperty();
    }

    /**
     * Return the strokeWidth, -1 means automatics
     *
     * @return stroke width
     */
    public double getStrokeWidth() {
        return this.strokeWidth_.getAsDouble();
    }

    /**
     * set the stroke width
     *
     * @param width stroke width
     */
    public void setStrokeWidth(final double width) {
        this.strokeWidth_.set(width);
    }

    public DoubleProperty strokeWidthProperty() {
        return strokeWidth_.getProperty();
    }

    public boolean isVisible() {
        return this.visible_.get();
    }

    public void setVisible(final boolean visible) {
        this.visible_.set(visible);
    }

    public BooleanProperty visibleProperty() {
        return visible_.getProperty();
    }

    public boolean isAnimated() {
        return this.animated_.get();
    }

    public void setAnimated(final boolean animated) {
        this.animated_.set(animated);
    }

    public BooleanProperty animatedProperty() {
        return this.animated_.getProperty();
    }

    public long getAnimationDuration() {
        return this.animationDuration_.getAsLong();
    }

    public void setAnimationDuration(final long duration) {
        this.animationDuration_.set(duration);
    }

    public LongProperty animationDurationProperty() {
        return this.animationDuration_.getProperty();
    }

    public boolean isWithWrapping() {
        return withWrapping_.get();
    }

    public void setWithWrapping(final boolean withWrapping) {
        this.withWrapping_.set(withWrapping);
    }

    public BooleanProperty withWrappingProperty() {
        return withWrapping_.getProperty();
    }

    /**
     * Return the number of data elements contained in this series.
     *
     * @return number of elements.
     */
    public int size() {return items_.size();}

    /**
     * Return true if this series contains no data.
     *
     * @return true if this series contains no data.
     */
    public boolean isEmpty() {
        return items_.isEmpty();
    }

    /**
     * Removes all the elements from this series. The list will be empty after this call returns.
     */
    public void clear() {
        items_.clear();
    }

    public void dispose() {
        items_.clear();
        items_.removeListener(listChangeListener_);
        items_.removeListener(itemListener_);
        listeners_.clear();
    }

    public void refresh() {fireSeriesEvent(UPDATE_EVENT);}

    /**
     * Add a new {@link SeriesEventListener}
     *
     * @param listener {@link SeriesEventListener}
     */
    public void addSeriesEventListener(final SeriesEventListener listener) {
        if (!listeners_.contains(listener)) {
            listeners_.add(listener);
        }
    }

    /**
     * Remove the {@link SeriesEventListener} from this series.
     *
     * @param listener a {@link SeriesEventListener} instance.
     */
    public void removeSeriesEventListener(final SeriesEventListener listener) {
        listeners_.remove(listener);
    }

    /**
     * Send the specified event to all listeners.
     *
     * @param seriesEvent {@link SeriesEvent}
     */
    public void fireSeriesEvent(final SeriesEvent seriesEvent) {
        for (SeriesEventListener listener : listeners_) {
            listener.onModelEvent(seriesEvent);
        }
    }
}
