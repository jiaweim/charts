package fx.charts.data;

import fx.charts.Category;
import fx.charts.Symbol;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;


public class ChartItemBuilder<B extends ChartItemBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();

    protected ChartItemBuilder() {}

    public static final ChartItemBuilder create() {
        return new ChartItemBuilder();
    }

    public final B index(final int INDEX) {
        properties.put("index", new SimpleIntegerProperty(INDEX));
        return (B) this;
    }

    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return (B) this;
    }

    public final B unit(final String UNIT) {
        properties.put("unit", new SimpleStringProperty(UNIT));
        return (B) this;
    }

    public final B description(final String DESCRIPTION) {
        properties.put("description", new SimpleStringProperty(DESCRIPTION));
        return (B) this;
    }

    public final B category(final Category CATEGORY) {
        properties.put("category", new SimpleObjectProperty(CATEGORY));
        return (B) this;
    }

    public final B value(final double VALUE) {
        properties.put("value", new SimpleDoubleProperty(VALUE));
        return (B) this;
    }

    public final B fill(final Color COLOR) {
        properties.put("fill", new SimpleObjectProperty(COLOR));
        return (B) this;
    }

    public final B stroke(final Color COLOR) {
        properties.put("stroke", new SimpleObjectProperty(COLOR));
        return (B) this;
    }

    public final B textFill(final Color COLOR) {
        properties.put("textFill", new SimpleObjectProperty(COLOR));
        return (B) this;
    }

    public final B timestamp(final ZonedDateTime DATE_TIME) {
        properties.put("timestampDateTime", new SimpleObjectProperty<>(DATE_TIME));
        return (B) this;
    }

    public final B timestamp(final Instant TIMESTAMP) {
        properties.put("timestamp", new SimpleObjectProperty<>(TIMESTAMP));
        return (B) this;
    }

    public final B timestampEpochSecond(final long TIMESTAMP_EPOCH_SECOND) {
        properties.put("timestampEpochSecond", new SimpleLongProperty(TIMESTAMP_EPOCH_SECOND));
        return (B) this;
    }

    public final B symbol(final Symbol SYMBOL) {
        properties.put("symbol", new SimpleObjectProperty<>(SYMBOL));
        return (B) this;
    }

    public final B animated(final boolean AUTO) {
        properties.put("animated", new SimpleBooleanProperty(AUTO));
        return (B) this;
    }

    public final B x(final double X) {
        properties.put("x", new SimpleDoubleProperty(X));
        return (B) this;
    }

    public final B y(final double Y) {
        properties.put("y", new SimpleDoubleProperty(Y));
        return (B) this;
    }

    public final B isEmpty() {
        properties.put("isEmpty", new SimpleBooleanProperty(true));
        return (B) this;
    }

    public final B metadata(final Metadata METADATA) {
        properties.put("metadata", new SimpleObjectProperty<>(METADATA));
        return (B) this;
    }

    public final B animationDuration(final long DURATION) {
        properties.put("animationDuration", new SimpleLongProperty(DURATION));
        return (B) this;
    }

    public final ChartItem build() {
        final ChartItem control = new ChartItem();
        properties.forEach((key, property) -> {
            switch (key) {
                case "index" -> control.setIndex(((IntegerProperty) property).get());
                case "name" -> control.setName(((StringProperty) property).get());
                case "value" -> control.setValue(((DoubleProperty) property).get());
                case "unit" -> control.setUnit(((StringProperty) property).get());
                case "description" -> control.setDescription(((StringProperty) property).get());
                case "category" -> control.setCategory(((ObjectProperty<Category>) property).get());
                case "fill" -> control.setFill(((ObjectProperty<Color>) property).get());
                case "stroke" -> control.setStroke(((ObjectProperty<Color>) property).get());
                case "textFill" -> control.setTextFill(((ObjectProperty<Color>) property).get());
                case "timestamp" -> control.setTimestamp(((ObjectProperty<Instant>) property).get());
                case "timestampDateTime" -> control.setTimestamp(((ObjectProperty<ZonedDateTime>) property).get());
                case "timestampEpochSecond" -> control.setTimestamp(((LongProperty) property).get());
                case "symbol" -> control.setSymbol(((ObjectProperty<Symbol>) property).get());
                case "animated" -> control.setAnimated(((BooleanProperty) property).get());
                case "animationDuration" -> control.setAnimationDuration(((LongProperty) property).get());
                case "x" -> control.setX(((DoubleProperty) property).get());
                case "y" -> control.setY(((DoubleProperty) property).get());
                case "isEmpty" -> control.setIsEmpty(((BooleanProperty) property).get());
                case "metadata" -> control.setMetadata(((ObjectProperty<Metadata>) property).get());
            }
        });
        return control;
    }
}
