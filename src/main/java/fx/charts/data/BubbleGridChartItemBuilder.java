package fx.charts.data;

import fx.charts.Symbol;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.HashMap;


public class BubbleGridChartItemBuilder<B extends BubbleGridChartItemBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();

    protected BubbleGridChartItemBuilder() {}


    public static final BubbleGridChartItemBuilder create() {
        return new BubbleGridChartItemBuilder();
    }

    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
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

    public final B symbol(final Symbol SYMBOL) {
        properties.put("symbol", new SimpleObjectProperty<>(SYMBOL));
        return (B) this;
    }

    public final B categoryXItem(final ChartItem categoryX) {
        properties.put("categoryX", new SimpleObjectProperty<>(categoryX));
        return (B) this;
    }

    public final B categoryYItem(final ChartItem categoryY) {
        properties.put("categoryY", new SimpleObjectProperty<>(categoryY));
        return (B) this;
    }

    public final B isEmpty() {
        properties.put("isEmpty", new SimpleBooleanProperty(true));
        return (B) this;
    }


    public final BubbleGridChartItem build() {
        final BubbleGridChartItem control = new BubbleGridChartItem();
        properties.forEach((key, property) -> {
            switch (key) {
                case "name" -> control.setName(((StringProperty) property).get());
                case "value" -> control.setValue(((DoubleProperty) property).get());
                case "fill" -> control.setFill(((ObjectProperty<Color>) property).get());
                case "stroke" -> control.setStroke(((ObjectProperty<Color>) property).get());
                case "symbol" -> control.setSymbol(((ObjectProperty<Symbol>) property).get());
                case "categoryX" -> control.setCategoryX(((ObjectProperty<ChartItem>) property).get());
                case "categoryY" -> control.setCategoryY(((ObjectProperty<ChartItem>) property).get());
                case "isEmpty" -> control.setIsEmpty(((BooleanProperty) property).get());
            }
        });
        return control;
    }
}
