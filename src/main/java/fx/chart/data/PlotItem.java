package fx.chart.data;

import fx.chart.Cluster;
import fx.chart.Position;
import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import fx.chart.font.Fonts;
import fx.chart.property.BooleanLProperty;
import fx.chart.property.DoubleLProperty;
import fx.chart.property.ObjectLProperty;
import fx.chart.property.StringLProperty;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.*;

/**
 * Used to represent special rendering elements.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 22 Jul 2025, 10:23 AM
 */
public class PlotItem extends DefaultEventSource implements Item, Comparable<PlotItem> {

    private final ChartEvent ITEM_EVENT = new ChartEvent(PlotItem.this, ChartEvent.ITEM_UPDATE);

    private final StringLProperty name;
    private final DoubleLProperty value;
    private final StringLProperty description_;
    private final ObjectLProperty<Color> fill_;
    private final ObjectLProperty<Color> stroke_;
    private final ObjectLProperty<Color> connectionFill_;
    private final ObjectLProperty<Color> textColor_;
    private final ObjectLProperty<Font> font_;
    private final ObjectLProperty<Symbol> symbol_;
    private final BooleanLProperty isEmpty_;
    private final ObjectLProperty<Position> verticalTextPosition_;

    private Map<PlotItem, Double> outgoing;
    private Map<PlotItem, Double> incoming;
    private int level;
    private Cluster cluster;

    public PlotItem() {
        this("", 0, "", Color.RED, -1, false);
    }

    public PlotItem(final boolean IS_EMPTY) {
        this("", 0, "", Color.RED, -1, IS_EMPTY);
    }

    public PlotItem(final String NAME, final double VALUE) {
        this(NAME, VALUE, "", Color.RED, -1, false);
    }

    public PlotItem(final String NAME, final double VALUE, final boolean IS_EMPTY) {
        this(NAME, VALUE, "", Color.RED, -1, IS_EMPTY);
    }

    public PlotItem(final String NAME, final Color COLOR) {
        this(NAME, 0, "", COLOR, -1, false);
    }

    public PlotItem(final String NAME, final Color COLOR, final boolean IS_EMPTY) {
        this(NAME, 0, "", COLOR, -1, IS_EMPTY);
    }

    public PlotItem(final String NAME, final Color COLOR, final int LEVEL) {
        this(NAME, 0, NAME, COLOR, LEVEL, false);
    }

    public PlotItem(final String NAME, final Color COLOR, final int LEVEL, final boolean IS_EMPTY) {
        this(NAME, 0, NAME, COLOR, LEVEL, IS_EMPTY);
    }

    public PlotItem(final String NAME, final double VALUE, final Color COLOR) {
        this(NAME, VALUE, "", COLOR, -1, false);
    }

    public PlotItem(final String NAME, final double VALUE, final Color COLOR, final boolean IS_EMPTY) {
        this(NAME, VALUE, "", COLOR, -1, IS_EMPTY);
    }

    public PlotItem(final String NAME, final double VALUE, final Color COLOR, final int LEVEL) {
        this(NAME, VALUE, "", COLOR, LEVEL, false);
    }

    public PlotItem(final String NAME, final double VALUE, final Color COLOR, final int LEVEL, final boolean IS_EMPTY) {
        this(NAME, VALUE, "", COLOR, LEVEL, IS_EMPTY);
    }

    public PlotItem(final String NAME, final double VALUE, final String DESCRIPTION, final Color FILL) {
        this(NAME, VALUE, DESCRIPTION, FILL, -1, false);
    }

    public PlotItem(final String NAME, final double VALUE, final String DESCRIPTION, final Color FILL, final boolean IS_EMPTY) {
        this(NAME, VALUE, DESCRIPTION, FILL, -1, IS_EMPTY);
    }

    public PlotItem(final String NAME, final double VALUE, final String DESCRIPTION, final Color FILL, final int LEVEL) {
        this(NAME, VALUE, DESCRIPTION, FILL, LEVEL, false);
    }

    public PlotItem(final String NAME, final double VALUE, final String DESCRIPTION, final Color FILL, final int LEVEL, final boolean IS_EMPTY) {
        name = new StringLProperty(this, "name", NAME, () -> fireChartEvent(ITEM_EVENT));
        value = new DoubleLProperty(this, "value", VALUE, () -> fireChartEvent(ITEM_EVENT));
        description_ = new StringLProperty(this, "description", DESCRIPTION, () -> fireChartEvent(ITEM_EVENT));
        fill_ = new ObjectLProperty<>(this, "fill", FILL, () -> fireChartEvent(ITEM_EVENT));
        stroke_ = new ObjectLProperty<>(this, "stroke", Color.TRANSPARENT, () -> fireChartEvent(ITEM_EVENT));
        connectionFill_ = new ObjectLProperty<>(this, "connectionFill", Color.TRANSPARENT, () -> fireChartEvent(ITEM_EVENT));
        textColor_ = new ObjectLProperty<>(this, "textColor", Color.TRANSPARENT, () -> fireChartEvent(ITEM_EVENT));
        font_ = new ObjectLProperty<>(this, "font", Fonts.opensansRegular(10), () -> fireChartEvent(ITEM_EVENT));
        symbol_ = new ObjectLProperty<>(this, "symbol", Symbol.NONE, () -> fireChartEvent(ITEM_EVENT));
        isEmpty_ = new BooleanLProperty(this, "isEmpty", IS_EMPTY, () -> fireChartEvent(ITEM_EVENT));
        verticalTextPosition_ = new ObjectLProperty<>(this, "verticalTextPosition", Position.CENTER);

        level = LEVEL;
        cluster = null;
        outgoing = new LinkedHashMap<>();
        incoming = new LinkedHashMap<>();
    }

    public String getName() {
        return name.get();
    }

    public void setName(final String NAME) {
        name.set(NAME);
    }

    public StringProperty nameProperty() {
        return name.getProperty();
    }

    public double getValue() {
        return value.getAsDouble();
    }

    public void setValue(final double VALUE) {
        value.set(VALUE);
    }

    public DoubleProperty valueProperty() {
        return value.getProperty();
    }

    public String getDescription() {
        return description_.get();
    }

    public void setDescription(final String DESCRIPTION) {
        description_.set(DESCRIPTION);
    }

    public StringProperty descriptionProperty() {
        return description_.getProperty();
    }

    @Override
    public Color getFillColor() {
        return fill_.get();
    }

    public void setFill(final Color FILL) {
        fill_.set(FILL);
    }

    public ObjectProperty<Color> fillProperty() {
        return fill_.getProperty();
    }

    @Override
    public Color getStrokeColor() {
        return stroke_.get();
    }

    public void setStroke(final Color STROKE) {
        stroke_.set(STROKE);
    }

    public ObjectProperty<Color> strokeProperty() {
        return stroke_.getProperty();
    }

    public Color getConnectionFill() {
        return connectionFill_.get();
    }

    private void setConnectionFill(final Color FILL) {
        connectionFill_.set(FILL);
    }

    public ReadOnlyObjectProperty<Color> connectionFillProperty() {
        return connectionFill_.getProperty();
    }

    public Color getTextColor() {
        return textColor_.get();
    }

    public void setTextColor(final Color textColor) {
        textColor_.set(textColor);
    }

    public ObjectProperty<Color> textColorProperty() {
        return textColor_.getProperty();
    }

    public Font getFont() {
        return font_.get();
    }

    public void setFont(final Font FONT) {
        font_.set(FONT);
    }

    public ObjectProperty<Font> fontProperty() {
        return font_.getProperty();
    }

    @Override
    public Symbol getSymbol() {
        return symbol_.get();
    }

    @Override
    public void setSymbol(final Symbol SYMBOL) {
        symbol_.set(SYMBOL);
    }

    public ObjectProperty<Symbol> symbolProperty() {
        return symbol_.getProperty();
    }

    @Override
    public boolean isEmptyItem() {
        return isEmpty_.getAsBoolean();
    }

    public void setIsEmpty(final boolean isEmpty) {
        isEmpty_.set(isEmpty);
    }

    public BooleanProperty isEmptyProperty() {
        return isEmpty_.getProperty();
    }

    public Position getVerticalTextPosition() {
        return verticalTextPosition_.get();
    }

    public void setVerticalTextPosition(final Position verticalTextPosition) {
        Position vt = (verticalTextPosition == Position.TOP || verticalTextPosition == Position.BOTTOM) ?
                verticalTextPosition : Position.CENTER;
        verticalTextPosition_.set(vt);
    }

    public ObjectProperty<Position> verticalTextPositionProperty() {
        return verticalTextPosition_.getProperty();
    }

    public double getSumOfIncoming() {return incoming.values().stream().mapToDouble(Double::doubleValue).sum();}

    public double getSumOfOutgoing() {return outgoing.values().stream().mapToDouble(Double::doubleValue).sum();}

    public double getMaxSum() {return Math.max(getSumOfIncoming(), getSumOfOutgoing());}

    public Map<PlotItem, Double> getOutgoing() {return outgoing;}

    public void setOutgoing(final Map<PlotItem, Double> OUTGOING) {
        outgoing.forEach((item, value) -> item.removeFromIncoming(PlotItem.this));
        outgoing.clear();
        outgoing.putAll(OUTGOING);
        establishConnections();
        fireChartEvent(ITEM_EVENT);
    }

    public void addToOutgoing(final PlotItem ITEM, final double VALUE) {
        if (!outgoing.containsKey(ITEM)) {
            outgoing.put(ITEM, Math.clamp(VALUE, 0, Double.MAX_VALUE));
            establishConnections();
            fireChartEvent(ITEM_EVENT);
        }
    }

    public void removeFromOutgoing(final PlotItem ITEM) {
        if (outgoing.containsKey(ITEM)) {
            ITEM.removeFromIncoming(PlotItem.this);
            outgoing.remove(ITEM);
            fireChartEvent(ITEM_EVENT);
        }
    }

    public void clearOutgoing() {
        outgoing.forEach((item, value) -> item.removeFromIncoming(PlotItem.this));
        outgoing.clear();
        fireChartEvent(ITEM_EVENT);
    }

    public boolean hasOutgoing() {return outgoing.size() > 0;}

    public Map<PlotItem, Double> getIncoming() {return incoming;}

    protected void setIncoming(final Map<PlotItem, Double> INCOMING) {
        incoming.clear();
        incoming.putAll(INCOMING);
        fireChartEvent(ITEM_EVENT);
    }

    protected void addToIncoming(final PlotItem ITEM, final double VALUE) {
        if (!incoming.containsKey(ITEM)) {
            incoming.put(ITEM, Math.clamp(VALUE, 0, Double.MAX_VALUE));
            fireChartEvent(ITEM_EVENT);
        }
    }

    protected void removeFromIncoming(final PlotItem ITEM) {
        if (incoming.containsKey(ITEM)) {
            incoming.remove(ITEM);
            fireChartEvent(ITEM_EVENT);
        }
    }

    protected void clearIncoming() {
        incoming.clear();
        fireChartEvent(ITEM_EVENT);
    }

    public boolean hasIncoming() {return incoming.size() > 0;}

    public double getIncomingValueFrom(final PlotItem INCOMING_ITEM) {
        if (getIncoming().containsKey(INCOMING_ITEM)) {
            return getIncoming().get(INCOMING_ITEM);
        } else {
            return 0;
        }
    }

    public double getOutgoingValueTo(final PlotItem OUTGOING_ITEM) {
        if (getOutgoing().containsKey(OUTGOING_ITEM)) {
            return getOutgoing().get(OUTGOING_ITEM);
        } else {
            return 0;
        }
    }

    public boolean isRoot() {return hasOutgoing() && !hasIncoming();}

    public boolean isLeaf() {return hasIncoming() && !hasOutgoing();}

    public int getLevel() {return level;}

    public void setLevel(final int LEVEL) {
        if (LEVEL < 0) {
            throw new IllegalArgumentException("Level cannot be smaller than 0");
        }
        level = LEVEL;
    }

    public Cluster getCluster() {return cluster;}

    public void setCluster(final Cluster CLUSTER) {cluster = CLUSTER;}

    public void sortOutgoingByGivenList(final List<PlotItem> LIST_WITH_SORTED_ITEMS) {
        List<PlotItem> outgoingKeys = new ArrayList(getOutgoing().keySet());

        sortAndReverse(outgoingKeys, LIST_WITH_SORTED_ITEMS);

        Map<PlotItem, Double> sortedOutgoingItems = new LinkedHashMap<>(outgoingKeys.size());
        for (PlotItem plotItem : outgoingKeys) {
            sortedOutgoingItems.put(plotItem, getOutgoing().get(plotItem));
        }
        outgoing.clear();
        outgoing.putAll(sortedOutgoingItems);
    }

    public void sortIncomingByGivenList(final List<PlotItem> LIST_WITH_SORTED_ITEMS) {
        List<PlotItem> incomingKeys = new ArrayList(getIncoming().keySet());
        Collections.reverse(incomingKeys);

        sortAndReverse(incomingKeys, LIST_WITH_SORTED_ITEMS);

        Map<PlotItem, Double> sortedIncomingItems = new LinkedHashMap<>(incomingKeys.size());
        for (PlotItem plotItem : incomingKeys) {
            sortedIncomingItems.put(plotItem, getIncoming().get(plotItem));
        }
        incoming.clear();
        incoming.putAll(sortedIncomingItems);
    }

    private void sortAndReverse(final List<PlotItem> LIST_TO_SORT, final List<PlotItem> SORTED_LIST) {
        Collections.sort(LIST_TO_SORT, Comparator.comparing(item -> SORTED_LIST.indexOf(item)));
        Collections.reverse(LIST_TO_SORT);
    }

    private void establishConnections() {
        outgoing.forEach((item, value) -> item.addToIncoming(PlotItem.this, value));
    }

    @Override
    public int compareTo(final PlotItem ITEM) {return Double.compare(getValue(), ITEM.getValue());}

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlotItem item = (PlotItem) o;

        if (Double.compare(item.getValue(), getValue()) != 0) return false;
        if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null) return false;
        return (getName() != null ? !getName().equals(item.getName()) : item.getName() != null);
//        if (getFill() != null ? !getFill().equals(item.getFill()) : item.getFill() != null) return false;
//        if (getStroke() != null ? !getStroke().equals(item.getStroke()) : item.getStroke() != null) return false;
//        return getConnectionFill() != null ? getConnectionFill().equals(item.getConnectionFill()) : item.getConnectionFill() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName() != null ? getName().hashCode() : 0;
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
//        result = 31 * result + (getFill() != null ? getFill().hashCode() : 0);
//        result = 31 * result + (getStroke() != null ? getStroke().hashCode() : 0);
//        result = 31 * result + (getConnectionFill() != null ? getConnectionFill().hashCode() : 0);
        return result;
    }
}
