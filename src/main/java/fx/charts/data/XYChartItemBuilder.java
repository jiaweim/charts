package fx.charts.data;

import fx.charts.Symbol;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class XYChartItemBuilder<B extends XYChartItemBuilder<B>> {
    private HashMap<String, Property> properties = new LinkedHashMap<>();

    protected XYChartItemBuilder() {}

    public static final XYChartItemBuilder create() {
        return new XYChartItemBuilder();
    }

    public final B x(final double x) {
        properties.put("x", new SimpleDoubleProperty(x));
        return (B) this;
    }

    public final B y(final double y) {
        properties.put("y", new SimpleDoubleProperty(y));
        return (B) this;
    }

    public final B name(final String name) {
        properties.put("name", new SimpleStringProperty(name));
        return (B) this;
    }

    public final B fill(final Color fill) {
        properties.put("fill", new SimpleObjectProperty<>(fill));
        return (B) this;
    }

    public final B stroke(final Color fill) {
        properties.put("stroke", new SimpleObjectProperty<>(fill));
        return (B) this;
    }

    public final B symbol(final Symbol symbol) {
        properties.put("symbol", new SimpleObjectProperty<>(symbol));
        return (B) this;
    }

    public final B isEmpty(final boolean isEmpty) {
        properties.put("isEmpty", new SimpleBooleanProperty(isEmpty));
        return (B) this;
    }

    public final B tooltipText(final String tooltipText) {
        properties.put("tooltipText", new SimpleStringProperty(tooltipText));
        return (B) this;
    }


    public final XYChartItem build() {
        final XYChartItem control = new XYChartItem();
        properties.forEach((key, property) -> {
            switch (key) {
                case "x" -> control.setX(((DoubleProperty) property).get());
                case "y" -> control.setY(((DoubleProperty) property).get());
                case "name" -> control.setName(((StringProperty) property).get());
                case "symbol" -> control.setSymbol(((ObjectProperty<Symbol>) property).get());
                case "fill" -> control.setFill(((ObjectProperty<Color>) property).get());
                case "stroke" -> control.setStroke(((ObjectProperty<Color>) property).get());
                case "isEmpty" -> control.setIsEmpty(((BooleanProperty) property).get());
                case "tooltipText" -> control.setTooltipText(((StringProperty) property).get());
            }
        });
        return control;
    }
}
