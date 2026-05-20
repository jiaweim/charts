package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.event.EventType;
import fx.chart.property.BooleanLazyProperty;
import fx.chart.property.DoubleLazyProperty;
import fx.chart.property.ObjectLazyProperty;
import fx.chart.property.StringLazyProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A 2D chart data point
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 2:16 PM
 */
public class XYChartItem implements XYItem, Comparable<XYChartItem> {

    private final ChartEvent ITEM_EVENT = new ChartEvent(XYChartItem.this, ChartEvent.ITEM_UPDATE);
    private final Map<EventType, List<ChartEventListener<ChartEvent>>> observers_;

    private final DoubleLazyProperty x_;
    private final DoubleLazyProperty y_;
    private final StringLazyProperty name_;
    private final ObjectLazyProperty<Color> fill_;
    private final ObjectLazyProperty<Color> stroke_;
    private final ObjectLazyProperty<Symbol> symbol_;
    private final BooleanLazyProperty isEmpty;
    private final StringLazyProperty tooltipText_;

    public XYChartItem(final double x, final double y) {
        this(x, y, "", Color.RED, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double x, final double y, final String name) {
        this(x, y, name, Color.RED, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double x, final double y, final String name, final String tooltip) {
        this(x, y, name, Color.RED, Color.TRANSPARENT, Symbol.NONE, tooltip, false);
    }

    public XYChartItem(final double x, final double y, final String name, final Color fill) {
        this(x, y, name, fill, Color.TRANSPARENT, Symbol.NONE, "", false);
    }

    public XYChartItem(final double x, final double y, final String name, final Color fill, final String tooltip) {
        this(x, y, name, fill, Color.TRANSPARENT, Symbol.NONE, tooltip, false);
    }

    public XYChartItem(final double x, final double y, final String name, final Color fill, final String tooltip, final boolean isEmpty) {
        this(x, y, name, fill, Color.TRANSPARENT, Symbol.NONE, tooltip, isEmpty);
    }

    public XYChartItem(final double x, final double y, final String name, final Color fill, final Color stroke, final Symbol symbol) {
        this(x, y, name, fill, stroke, symbol, "", false);
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
        this.x_ = new DoubleLazyProperty(this, "x", x, () -> fireChartEvent(ITEM_EVENT));
        this.y_ = new DoubleLazyProperty(this, "y", y, () -> fireChartEvent(ITEM_EVENT));
        this.name_ = new StringLazyProperty(this, "name", name, () -> fireChartEvent(ITEM_EVENT));
        this.fill_ = new ObjectLazyProperty<>(this, "fill", fill, () -> fireChartEvent(ITEM_EVENT));
        this.stroke_ = new ObjectLazyProperty<>(this, "stroke", stroke, () -> fireChartEvent(ITEM_EVENT));
        this.symbol_ = new ObjectLazyProperty<>(this, "symbol", symbol, () -> fireChartEvent(ITEM_EVENT));
        this.isEmpty = new BooleanLazyProperty(this, "isEmpty", isEmpty, () -> fireChartEvent(ITEM_EVENT));
        this.tooltipText_ = new StringLazyProperty(this, "tooltip", tooltip, () -> fireChartEvent(ITEM_EVENT));

        observers_ = new ConcurrentHashMap<>();
    }

    @Override
    public double getX() {
        return this.x_.getAsDouble();
    }

    @Override
    public void setX(final double X) {
        this.x_.set(X);
    }

    @Override
    public DoubleProperty xProperty() {
        return this.x_.getProperty();
    }

    @Override
    public double getY() {
        return this.y_.getAsDouble();
    }

    @Override
    public void setY(final double Y) {
        this.y_.set(Y);
    }

    @Override
    public DoubleProperty yProperty() {
        return this.y_.getProperty();
    }

    @Override
    public String getName() {
        return this.name_.get();
    }

    public void setName(final String NAME) {
        this.name_.set(NAME);
    }

    public StringProperty nameProperty() {
        return this.name_.getProperty();
    }

    @Override
    public Color getFill() {
        return this.fill_.get();
    }

    public void setFill(final Color FILL) {
        this.fill_.set(FILL);
    }

    public ObjectProperty<Color> fillProperty() {
        return this.fill_.getProperty();
    }

    @Override
    public Color getStroke() {
        return this.stroke_.get();
    }

    public void setStroke(final Color STROKE) {
        this.stroke_.set(STROKE);
    }

    public ObjectProperty<Color> strokeProperty() {
        return this.stroke_.getProperty();
    }

    @Override
    public Symbol getSymbol() {
        return this.symbol_.get();
    }

    @Override
    public void setSymbol(final Symbol symbol) {
        this.symbol_.set(symbol);
    }

    public ObjectProperty<Symbol> symbolProperty() {
        return this.symbol_.getProperty();
    }

    @Override
    public String getTooltipText() {
        return this.tooltipText_.get();
    }

    @Override
    public void setTooltipText(final String tooltip) {
        this.tooltipText_.set(tooltip);
    }

    @Override
    public StringProperty tooltipTextProperty() {
        return tooltipText_.getProperty();
    }

    @Override
    public boolean isEmptyItem() {
        return isEmpty.getAsBoolean();
    }

    public void setIsEmpty(final boolean isEmpty) {
        this.isEmpty.set(isEmpty);
    }

    public BooleanProperty isEmptyProperty() {
        return this.isEmpty.getProperty();
    }

    public void addChartEventObserver(final EventType type, final ChartEventListener<ChartEvent> observer) {
        if (!observers_.containsKey(type)) {
            observers_.put(type, new CopyOnWriteArrayList<>());
        }
        if (observers_.get(type).contains(observer)) {
            return;
        }
        observers_.get(type).add(observer);
    }

    public void removeChartEventObserver(final EventType type, final ChartEventListener<ChartEvent> observer) {
        if (observers_.containsKey(type)) {
            if (observers_.get(type).contains(observer)) {
                observers_.get(type).remove(observer);
            }
        }
    }

    public void removeAllChartEvtObservers() {observers_.clear();}

    public void fireChartEvent(final ChartEvent evt) {
        final EventType type = evt.getEventType();
        observers_.entrySet().stream().filter(entry -> entry.getKey().equals(ChartEvent.ANY)).forEach(entry -> entry.getValue().forEach(observer -> observer.handle(evt)));
        if (observers_.containsKey(type) && !type.equals(ChartEvent.ANY)) {
            observers_.get(type).forEach(observer -> observer.handle(evt));
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
    public int compareTo(final XYChartItem item) {return Double.compare(getX(), item.getX());}
}
