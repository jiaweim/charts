package fx.chart.data;

import fx.chart.property.DoubleLProperty;
import fx.chart.property.ObjectLProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 *
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 10:57 AM
 */
public class CandleChartItem extends ChartItem {

    private final DoubleLProperty high_;
    private final DoubleLProperty low_;
    private final DoubleLProperty open_;
    private final DoubleLProperty close_;
    private ObjectLProperty<Instant> openTimestamp_;
    private ObjectLProperty<Instant> closeTimestamp_;

    public CandleChartItem() {
        this("", "", "", 0, 0, 0, 0, Instant.now());
    }

    public CandleChartItem(final String name, final String unit, final String description,
            final double low, final double open, final double close, final double high, final ZonedDateTime timestamp) {
        super();
        setName(name);
        setUnit(unit);
        setDescription(description);
        setTimestamp(timestamp);
        high_ = new DoubleLProperty(this, "high", high, () -> fireChartEvent(UPDATE_EVENT));
        low_ = new DoubleLProperty(this, "low", low, () -> fireChartEvent(UPDATE_EVENT));
        open_ = new DoubleLProperty(this, "open", open, () -> fireChartEvent(UPDATE_EVENT));
        close_ = new DoubleLProperty(this, "close", close, () -> fireChartEvent(UPDATE_EVENT));
    }

    public CandleChartItem(final String name, final String unit, final String description, final double low, final double open, final double close, final double high, final Instant timestamp) {
        this(name, unit, description, low, open, close, high, timestamp, timestamp, timestamp);
    }

    public CandleChartItem(final String name, final String unit, final String description,
            final double low, final double open, final double close, final double high,
            final Instant timestamp, final Instant openTimestamp, final Instant closeTimestamp) {
        super();
        setName(name);
        setUnit(unit);
        setDescription(description);
        setTimestamp(timestamp);
        high_ = new DoubleLProperty(this, "high", high, () -> fireChartEvent(UPDATE_EVENT));
        low_ = new DoubleLProperty(this, "low", low, () -> fireChartEvent(UPDATE_EVENT));
        open_ = new DoubleLProperty(this, "open", open, () -> fireChartEvent(UPDATE_EVENT));
        close_ = new DoubleLProperty(this, "close", close, () -> fireChartEvent(UPDATE_EVENT));
        openTimestamp_ = new ObjectLProperty<>(this, "openTimestamp", openTimestamp);
        closeTimestamp_ = new ObjectLProperty<>(this, "closeTimestamp", closeTimestamp);
    }


    public double getHigh() {
        return high_.getAsDouble();
    }

    public void setHigh(final double high) {
        high_.set(high);
    }

    public DoubleProperty highProperty() {
        return high_.getProperty();
    }

    public double getLow() {
        return low_.getAsDouble();
    }

    public void setLow(final double low) {
        low_.set(low);
    }

    public DoubleProperty lowProperty() {
        return low_.getProperty();
    }

    public double getOpen() {
        return open_.getAsDouble();
    }

    public void setOpen(final double open) {
        open_.set(open);
    }

    public DoubleProperty openProperty() {
        return open_.getProperty();
    }

    public double getClose() {
        return close_.getAsDouble();
    }

    public void setClose(final double close) {
        close_.set(close);
    }

    public DoubleProperty closeProperty() {
        return close_.getProperty();
    }

    public Instant getOpenTimestamp() {
        return openTimestamp_.get();
    }

    public void setOpenTimestamp(final long openTimestampEpochSecond) {
        setOpenTimestamp(Instant.ofEpochSecond(openTimestampEpochSecond));
    }

    public void setOpenTimestamp(final Instant openTimestamp) {
        openTimestamp_.set(openTimestamp);
    }

    public ObjectProperty<Instant> openTimestampProperty() {
        return openTimestamp_.getProperty();
    }

    public Instant getCloseTimestamp() {
        return closeTimestamp_.get();
    }

    public void setCloseTimestamp(final long closeTimestampEpochSecond) {
        setCloseTimestamp(Instant.ofEpochSecond(closeTimestampEpochSecond));
    }

    public void setCloseTimestamp(final Instant closeTimestamp) {
        closeTimestamp_.set(closeTimestamp);
    }

    public ObjectProperty<Instant> closeTimestampProperty() {
        return closeTimestamp_.getProperty();
    }

    public void validate() {
        if (Double.compare(getHigh(), getLow()) != 0 && getHigh() < getLow()) {
            throw new IllegalArgumentException("High cannot be smaller than low");
        }
    }

    @Override
    public String toString() {
        return new StringBuilder().append("{\n")
                .append("  \"name\":").append(getName()).append(",\n")
                .append("  \"unit\":").append(getUnit()).append(",\n")
                .append("  \"description\":").append(getDescription()).append(",\n")
                .append("  \"high\":").append(getHigh()).append(",\n")
                .append("  \"low\":").append(getLow()).append(",\n")
                .append("  \"open\":").append(getOpen()).append(",\n")
                .append("  \"close\":").append(getClose()).append(",\n")
                .append("  \"open_timestamp\":").append(getOpenTimestamp().getEpochSecond()).append(",\n")
                .append("  \"close_timestamp\":").append(getCloseTimestamp().getEpochSecond()).append(",\n")
                .append("  \"timestamp\":").append(getTimestamp().getEpochSecond()).append("\n")
                .append("}")
                .toString();
    }
}
