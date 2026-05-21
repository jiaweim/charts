package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import fx.chart.property.*;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

/**
 * This class represents a cell block in the heat map.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 11:21 AM
 */
public class MatrixChartItem extends DefaultEventSource implements MatrixItem {

    private final ChartEvent ITEM_EVENT = new ChartEvent(MatrixChartItem.this, ChartEvent.ITEM_UPDATE);

    private final IntegerLProperty x_;
    private final IntegerLProperty y_;
    private final DoubleLProperty z_;
    private final StringLProperty name_;
    private final ObjectLProperty<Color> fill_;
    private final ObjectLProperty<Color> stroke_;
    private final ObjectLProperty<Symbol> symbol_;
    private final BooleanLProperty isEmpty_;

    public MatrixChartItem() {
        this(0, 0, 0, "", Color.RED, false);
    }

    public MatrixChartItem(final boolean IS_EMPTY) {
        this(0, 0, 0, "", Color.RED, IS_EMPTY);
    }

    public MatrixChartItem(final int X, final int Y, final double Z) {
        this(X, Y, Z, "", Color.RED, false);
    }

    public MatrixChartItem(final int X, final int Y, final double Z, final boolean IS_EMPTY) {
        this(X, Y, Z, "", Color.RED, IS_EMPTY);
    }

    public MatrixChartItem(final int X, final int Y, final double Z, final String NAME) {
        this(X, Y, Z, NAME, Color.RED, false);
    }

    public MatrixChartItem(final int X, final int Y, final double Z, final String NAME, final boolean IS_EMPTY) {
        this(X, Y, Z, NAME, Color.RED, IS_EMPTY);
    }

    public MatrixChartItem(final int X, final int Y, final double Z, final String NAME, final Color FILL) {
        this(X, Y, Z, NAME, FILL, false);
    }

    public MatrixChartItem(final int x, final int y, final double z, final String name, final Color fill, final boolean isEmpty) {
        x_ = new IntegerLProperty(this, "x", x, () -> fireChartEvent(ITEM_EVENT));
        y_ = new IntegerLProperty(this, "y", y, () -> fireChartEvent(ITEM_EVENT));
        z_ = new DoubleLProperty(this, "z", z, () -> fireChartEvent(ITEM_EVENT));
        name_ = new StringLProperty(this, "name", name, () -> fireChartEvent(ITEM_EVENT));
        fill_ = new ObjectLProperty<>(this, "fill", fill, () -> fireChartEvent(ITEM_EVENT));
        stroke_ = new ObjectLProperty<>(this, "stroke", Color.TRANSPARENT, () -> fireChartEvent(ITEM_EVENT));
        symbol_ = new ObjectLProperty<>(this, "symbol", Symbol.NONE, () -> fireChartEvent(ITEM_EVENT));
        isEmpty_ = new BooleanLProperty(this, "isEmpty", isEmpty, () -> fireChartEvent(ITEM_EVENT));
    }

    @Override
    public int getX() {
        return x_.getAsInt();
    }

    @Override
    public void setX(final int X) {
        x_.set(X);
    }

    public IntegerProperty xProperty() {
        return x_.getProperty();
    }

    @Override
    public int getY() {
        return y_.getAsInt();
    }

    @Override
    public void setY(final int Y) {
        y_.set(Y);
    }

    @Override
    public IntegerProperty yProperty() {
        return y_.getProperty();
    }

    @Override
    public double getZ() {
        return z_.getAsDouble();
    }

    @Override
    public void setZ(final double Z) {
        z_.set(Z);
    }

    @Override
    public DoubleProperty zProperty() {
        return z_.getProperty();
    }

    @Override
    public String getName() {
        return name_.get();
    }

    public void setName(final String NAME) {
        name_.set(NAME);
    }

    public StringProperty nameProperty() {
        return name_.getProperty();
    }

    @Override
    public Color getFillColor() {
        return fill_.get();
    }

    public void setFill(final Color fill) {
        fill_.set(fill);
    }

    public ObjectProperty<Color> fillProperty() {
        return fill_.getProperty();
    }

    @Override
    public Color getStrokeColor() {
        return stroke_.get();
    }

    public void setStroke(final Color stroke) {
        stroke_.set(stroke);
    }

    public ObjectProperty<Color> strokeProperty() {
        return stroke_.getProperty();
    }

    @Override
    public Symbol getSymbol() {
        return symbol_.get();
    }

    public void setSymbol(final Symbol symbol) {
        symbol_.set(symbol);
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
