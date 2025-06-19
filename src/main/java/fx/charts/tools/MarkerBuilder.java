package fx.charts.tools;

import fx.charts.Axis;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class MarkerBuilder<B extends MarkerBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();
    private Axis axis;
    private double value;


    protected MarkerBuilder(final Axis axis, final double value) {
        this.axis = axis;
        this.value = value;
    }


    public static final MarkerBuilder create(final Axis axis, final double value) {
        return new MarkerBuilder(axis, value);
    }

    public final B stroke(final Color stroke) {
        properties.put("stroke", new SimpleObjectProperty<>(stroke));
        return (B) this;
    }

    public final B lineWidth(final double lineWidth) {
        properties.put("lineWidth", new SimpleDoubleProperty(lineWidth));
        return (B) this;
    }

    public final B text(final String text) {
        properties.put("text", new SimpleStringProperty(text));
        return (B) this;
    }

    public final B textFill(final Color textFill) {
        properties.put("textFill", new SimpleObjectProperty<>(textFill));
        return (B) this;
    }

    public final B fromatString(final String formatString) {
        properties.put("formatString", new SimpleStringProperty(formatString));
        return (B) this;
    }

    public final B lineStyle(final LineStyle lineStyle) {
        properties.put("lineStyle", new SimpleObjectProperty<>(lineStyle));
        return (B) this;
    }

    public final Marker build() {
        final Marker control = new Marker(axis, value);
        properties.forEach((key, property) -> {
            switch (key) {
                case "stroke" -> control.setStroke(((ObjectProperty<Color>) property).get());
                case "lineWidth" -> control.setLineWidth(((DoubleProperty) property).get());
                case "text" -> control.setText(((StringProperty) property).get());
                case "textFill" -> control.setTextFill(((ObjectProperty<Color>) property).get());
                case "formatString" -> control.setFormatString(((StringProperty) property).get());
                case "lineStyle" -> control.setLineStyle(((ObjectProperty<LineStyle>) property).get());
            }
        });
        return control;
    }
}
