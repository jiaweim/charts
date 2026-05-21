package fx.chart.data;

import fx.chart.Category;
import fx.chart.Symbol;
import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import fx.chart.property.*;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A general class representing data to be rendered.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 10:56 AM
 */
public class ChartItem extends DefaultEventSource implements Item, Comparable<ChartItem> {

    protected final ChartEvent UPDATE_EVENT = new ChartEvent(ChartItem.this, ChartEvent.ITEM_UPDATE);
    protected final ChartEvent FINISHED_EVENT = new ChartEvent(ChartItem.this, ChartEvent.FINISHED);
    protected final ChartEvent SELECTED_EVENT = new ChartEvent(ChartItem.this, ChartEvent.SELECTED);

    private final IntegerLProperty index_;
    private final StringLProperty name_;
    private final StringLProperty unit_;
    private final StringLProperty description_;
    private final ObjectLProperty<Category> category_;

    private double _value;
    private DoubleProperty value;
    private double oldValue;

    private final ObjectLProperty<Color> fill_;
    private final ObjectLProperty<Color> stroke_;
    private final ObjectLProperty<Color> textFill_;
    private final ObjectLProperty<Instant> timestamp_;
    private final ObjectLProperty<Symbol> symbol_;
    private final BooleanLProperty animated_;
    private final DoubleLProperty x_;
    private final DoubleLProperty y_;
    private final BooleanLProperty isEmpty_;
    private final BooleanLProperty selected_;
    private final ObjectLProperty<Metadata> metadata_;

    private long animationDuration_;
    private final DoubleProperty currentValue_;
    private final Timeline timeline_;

    public ChartItem() {
        this("", 0, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final boolean IS_EMPTY) {
        this("", 0, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME) {
        this(NAME, 0, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final String NAME, final boolean IS_EMPTY) {
        this(NAME, 0, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(double VALUE) {
        this("", VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(double VALUE, final boolean IS_EMPTY) {
        this("", VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final double VALUE, final Instant TIMESTAMP) {
        this("", VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, TIMESTAMP, false, 800, false, null);
    }

    public ChartItem(final double VALUE, final Instant TIMESTAMP, final boolean IS_EMPTY) {
        this("", VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, TIMESTAMP, false, 800, IS_EMPTY, null);
    }

    public ChartItem(final double VALUE, final Color FILL_COLOR) {
        this("", VALUE, FILL_COLOR, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final double VALUE, final Color FILL_COLOR, final boolean IS_EMPTY) {
        this("", VALUE, FILL_COLOR, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final Color FILL_COLOR) {
        this(NAME, 0, FILL_COLOR, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final String NAME, final Color FILL_COLOR, final boolean IS_EMPTY) {
        this(NAME, 0, FILL_COLOR, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE) {
        this(NAME, VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final boolean IS_EMPTY) {
        this(NAME, VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Instant TIMESTAMP) {
        this(NAME, VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, TIMESTAMP, true, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Instant TIMESTAMP, final boolean IS_EMPTY) {
        this(NAME, VALUE, Color.rgb(233, 30, 99), Color.TRANSPARENT, Color.BLACK, TIMESTAMP, true, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, Instant.now(), false, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, Instant.now(), false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Instant TIMESTAMP) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, TIMESTAMP, false, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Instant TIMESTAMP, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, TIMESTAMP, false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL, final Instant TIMESTAMP) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, TIMESTAMP, false, 800, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL, final Instant TIMESTAMP, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, TIMESTAMP, false, 800, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, TIMESTAMP, ANIMATED, ANIMATION_DURATION, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, Color.BLACK, TIMESTAMP, ANIMATED, ANIMATION_DURATION, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, TIMESTAMP, ANIMATED, ANIMATION_DURATION, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color TEXT_FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, Color.TRANSPARENT, TEXT_FILL, TIMESTAMP, ANIMATED, ANIMATION_DURATION, IS_EMPTY, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color STROKE, final Color TEXT_FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION) {
        this(NAME, VALUE, FILL, STROKE, TEXT_FILL, TIMESTAMP, ANIMATED, ANIMATION_DURATION, false, null);
    }

    public ChartItem(final String NAME, final double VALUE, final Color FILL, final Color STROKE, final Color TEXT_FILL, final Instant TIMESTAMP, final boolean ANIMATED, final long ANIMATION_DURATION, final boolean IS_EMPTY) {
        this(NAME, VALUE, FILL, STROKE, TEXT_FILL, TIMESTAMP, ANIMATED, ANIMATION_DURATION, IS_EMPTY, null);
    }

    public ChartItem(final String name, final double value, final Color fill, final Color stroke, final Color textFill,
            final Instant timestamp, final boolean animated, final long animationDuration, final boolean IS_EMPTY, final Metadata metadata) {
        index_ = new IntegerLProperty(this, "index", -1);
        name_ = new StringLProperty(this, "name", name, () -> fireChartEvent(UPDATE_EVENT));
        unit_ = new StringLProperty(this, "unit", "", () -> fireChartEvent(UPDATE_EVENT));
        description_ = new StringLProperty(this, "description", "", () -> fireChartEvent(UPDATE_EVENT));
        category_ = new ObjectLProperty<>(this, "category", null, () -> fireChartEvent(UPDATE_EVENT));

        _value = value;
        oldValue = 0;

        fill_ = new ObjectLProperty<>(this, "fill", fill, () -> fireChartEvent(UPDATE_EVENT));
        stroke_ = new ObjectLProperty<>(this, "stroke", stroke, () -> fireChartEvent(UPDATE_EVENT));
        textFill_ = new ObjectLProperty<>(this, "textFill", textFill, () -> fireChartEvent(UPDATE_EVENT));
        timestamp_ = new ObjectLProperty<>(this, "timestamp", timestamp, () -> fireChartEvent(UPDATE_EVENT));
        symbol_ = new ObjectLProperty<>(this, "symbol", Symbol.NONE, () -> fireChartEvent(UPDATE_EVENT));
        animated_ = new BooleanLProperty(this, "animated", animated);
        x_ = new DoubleLProperty(this, "x", 0.0);
        y_ = new DoubleLProperty(this, "y", 0.0);
        isEmpty_ = new BooleanLProperty(this, "isEmpty", IS_EMPTY, () -> fireChartEvent(UPDATE_EVENT));
        selected_ = new BooleanLProperty(this, "selected", false, () -> fireChartEvent(SELECTED_EVENT));
        metadata_ = new ObjectLProperty<>(this, "metadata", metadata, () -> fireChartEvent(UPDATE_EVENT));

        currentValue_ = new DoublePropertyBase(_value) {
            @Override
            protected void invalidated() {
                oldValue = ChartItem.this.getValue();
                ChartItem.this.setValue(get());
                fireChartEvent(UPDATE_EVENT);
            }

            @Override
            public Object getBean() {return ChartItem.this;}

            @Override
            public String getName() {return "currentValue";}
        };
        timeline_ = new Timeline();
        this.animationDuration_ = animationDuration;

        timeline_.setOnFinished(e -> fireChartEvent(FINISHED_EVENT));
    }


    public int getIndex() {
        return this.index_.getAsInt();
    }

    public void setIndex(final int index) {
        this.index_.set(index);
    }

    public IntegerProperty indexProperty() {
        return this.index_.getProperty();
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

    public String getUnit() {
        return this.unit_.get();
    }

    public void setUnit(final String UNIT) {
        this.unit_.set(UNIT);
    }

    public StringProperty unitProperty() {
        return this.unit_.getProperty();
    }

    public String getDescription() {
        return this.description_.get();
    }

    public void setDescription(final String DESCRIPTION) {
        this.description_.set(DESCRIPTION);
    }

    public StringProperty descriptionProperty() {
        return this.description_.getProperty();
    }

    public Category getCategory() {
        return category_.get();
    }

    public void setCategory(final Category category) {
        category_.set(category);
    }

    public ObjectProperty<Category> categoryProperty() {
        return category_.getProperty();
    }

    public double getValue() {return null == value ? _value : value.get();}

    public void setValue(final double VALUE) {
        if (null == value) {
            if (isAnimated()) {
                if (timeline_.getCurrentRate() > 0) {
                    // Only update values if timeline is already running
                    oldValue = _value;
                    _value = VALUE;
                } else {
                    // Start timeline only if it is NOT already running
                    oldValue = _value;
                    _value = VALUE;
                    timeline_.stop();
                    KeyValue kv1 = new KeyValue(currentValue_, oldValue, Interpolator.EASE_BOTH);
                    KeyValue kv2 = new KeyValue(currentValue_, VALUE, Interpolator.EASE_BOTH);
                    KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);
                    KeyFrame kf2 = new KeyFrame(Duration.millis(animationDuration_), kv2);
                    timeline_.getKeyFrames().setAll(kf1, kf2);
                    timeline_.play();
                }
            } else {
                oldValue = _value;
                _value = VALUE;
                fireChartEvent(FINISHED_EVENT);
            }
        } else {
            value.set(VALUE);
        }
    }

    public DoubleProperty valueProperty() {
        if (null == value) {
            value = new DoublePropertyBase(_value) {
                @Override
                public void set(final double VALUE) {
                    oldValue = get();
                    super.set(VALUE);
                }

                @Override
                protected void invalidated() {
                    if (isAnimated()) {
                        if (Double.compare(timeline_.getCurrentRate(), 0.0) == 0) {
                            // Only start timeline if it is NOT already running
                            timeline_.stop();
                            KeyValue kv1 = new KeyValue(currentValue_, oldValue, Interpolator.EASE_BOTH);
                            KeyValue kv2 = new KeyValue(currentValue_, get(), Interpolator.EASE_BOTH);
                            KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);
                            KeyFrame kf2 = new KeyFrame(Duration.millis(animationDuration_), kv2);
                            timeline_.getKeyFrames().setAll(kf1, kf2);
                            timeline_.play();
                        }
                    } else {
                        fireChartEvent(FINISHED_EVENT);
                    }
                }

                @Override
                public Object getBean() {return ChartItem.this;}

                @Override
                public String getName() {return "value";}
            };
        }
        return value;
    }

    public double getOldValue() {return oldValue;}

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

    public Color getStrokeColor() {
        return stroke_.get();
    }

    public void setStroke(final Color stroke) {
        stroke_.set(stroke);
    }

    public ObjectProperty<Color> strokeProperty() {
        return stroke_.getProperty();
    }

    public Color getTextFill() {
        return textFill_.get();
    }

    public void setTextFill(final Color COLOR) {
        textFill_.set(COLOR);
    }

    public ObjectProperty<Color> textFillProperty() {
        return textFill_.getProperty();
    }

    @Override
    public Symbol getSymbol() {
        return symbol_.get();
    }

    @Override
    public void setSymbol(final Symbol symbol) {
        symbol_.set(symbol);
    }

    public ObjectProperty<Symbol> symbolProperty() {
        return symbol_.getProperty();
    }

    public Instant getTimestamp() {
        return timestamp_.get();
    }

    public void setTimestamp(final ZonedDateTime zonedDateTime) {
        setTimestamp(zonedDateTime.toInstant());
    }

    public void setTimestamp(final long timestampEpochSecond) {
        setTimestamp(Instant.ofEpochSecond(timestampEpochSecond));
    }

    public void setTimestamp(final Instant timestamp) {
        timestamp_.set(timestamp);
    }

    public ObjectProperty<Instant> timestampProperty() {
        return timestamp_.getProperty();
    }

    public ZonedDateTime getTimestampAdDateTime() {return getTimestampAsDateTime(ZoneId.systemDefault());}

    public ZonedDateTime getTimestampAsDateTime(final ZoneId ZONE_ID) {return ZonedDateTime.ofInstant(getTimestamp(), ZONE_ID);}

    public LocalDate getTimestampAsLocalDate() {return getTimestampAsLocalDate(ZoneId.systemDefault());}

    public LocalDate getTimestampAsLocalDate(final ZoneId ZONE_ID) {return getTimestampAsDateTime(ZONE_ID).toLocalDate();}

    public boolean isAnimated() {
        return animated_.getAsBoolean();
    }

    public void setAnimated(final boolean animated) {
        animated_.set(animated);
    }

    public BooleanProperty animatedProperty() {
        return animated_.getProperty();
    }

    public double getX() {
        return x_.getAsDouble();
    }

    public void setX(final double x) {
        x_.set(x);
    }

    public DoubleProperty xProperty() {
        return x_.getProperty();
    }

    public double getY() {
        return y_.getAsDouble();
    }

    public void setY(final double Y) {
        y_.set(Y);
    }

    public DoubleProperty yProperty() {
        return y_.getProperty();
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

    public boolean isSelected() {
        return selected_.getAsBoolean();
    }

    public void setSelected(final boolean selected) {
        selected_.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected_.getProperty();
    }

    public Metadata getMetadata() {
        return metadata_.get();
    }

    public void setMetadata(final Metadata metadata) {
        metadata_.set(metadata);
    }

    public ObjectProperty<Metadata> metadataProperty() {
        return metadata_.getProperty();
    }

    public long getAnimationDuration() {return animationDuration_;}

    public void setAnimationDuration(final long DURATION) {animationDuration_ = Math.clamp(DURATION, 10, 10000);}

    @Override
    public String toString() {
        return new StringBuilder().append("{\n")
                .append("  \"name\":").append(getName()).append(",\n")
                .append("  \"unit\":").append(getUnit()).append(",\n")
                .append("  \"description\":").append(getDescription()).append(",\n")
                .append("  \"category\":").append(getCategory()).append(",\n")
                .append("  \"value\":").append(getValue()).append(",\n")
                .append("  \"timestamp\":").append(getTimestamp().toEpochMilli()).append(",\n")
                .append("  \"metadata\":").append("\"").append(null == getMetadata() ? "" : getMetadata().toString()).append("\"\n")
                .append("}")
                .toString();
    }

    @Override
    public int compareTo(final ChartItem ITEM) {return Double.compare(getValue(), ITEM.getValue());}

    @Override
    public int hashCode() {
        return Objects.hash(index_, name_, unit_, description_, category_, _value, value, oldValue, fill_, stroke_,
                textFill_, timestamp_, symbol_, animated_, x_, y_, isEmpty_, selected_, metadata_,
                animationDuration_, currentValue_);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChartItem)) {
            return false;
        }

        ChartItem item = (ChartItem) o;

        return item.getName().equals(getName()) &&
                item.getUnit().equals(getUnit()) &&
                item.getDescription().equals(getDescription()) &&
                item.getTimestamp().equals(getTimestamp()) &&
                item.isEmptyItem() == isEmptyItem() &&
                Double.compare(item.getValue(), getValue()) == 0;
    }
}
