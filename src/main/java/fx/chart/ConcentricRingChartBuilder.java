package fx.chart;

import fx.chart.data.ChartItem;
import fx.chart.series.Series;
import fx.chart.tools.NumberFormat;
import fx.chart.tools.Order;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;


public class ConcentricRingChartBuilder<B extends ConcentricRingChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();

    protected ConcentricRingChartBuilder() {}

    public static final ConcentricRingChartBuilder create() {
        return new ConcentricRingChartBuilder();
    }

    public final B series(final Series<ChartItem> SERIES) {
        properties.put("series", new SimpleObjectProperty(SERIES));
        return (B) this;
    }

    public final B items(final ChartItem... ITEMS) {
        properties.put("itemArray", new SimpleObjectProperty<>(ITEMS));
        return (B) this;
    }

    public final B items(final List<ChartItem> ITEMS) {
        properties.put("itemList", new SimpleObjectProperty<>(ITEMS));
        return (B) this;
    }

    public final B barBackgroundFill(final Color COLOR) {
        properties.put("barBackgroundFill", new SimpleObjectProperty<>(COLOR));
        return (B) this;
    }

    public final B sorted(final boolean SORTED) {
        properties.put("sorted", new SimpleBooleanProperty(SORTED));
        return (B) this;
    }

    public final B order(final Order ORDER) {
        properties.put("order", new SimpleObjectProperty<>(ORDER));
        return (B) this;
    }

    public final B numberFormat(final NumberFormat FORMAT) {
        properties.put("numberFormat", new SimpleObjectProperty(FORMAT));
        return (B) this;
    }

    public final B itemLabelFill(final Color FILL) {
        properties.put("itemLabelFill", new SimpleObjectProperty<>(FILL));
        return (B) this;
    }

    public final B shortenNumbers(final boolean SHORTEN) {
        properties.put("shortenNumbers", new SimpleBooleanProperty(SHORTEN));
        return (B) this;
    }

    public final B valueVisible(final boolean VISIBLE) {
        properties.put("valueVisible", new SimpleBooleanProperty(VISIBLE));
        return (B) this;
    }

    // General properties
    public final B prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B minSize(final double WIDTH, final double HEIGHT) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B maxSize(final double WIDTH, final double HEIGHT) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B) this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B) this;
    }

    public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B) this;
    }

    public final B minWidth(final double MIN_WIDTH) {
        properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return (B) this;
    }

    public final B minHeight(final double MIN_HEIGHT) {
        properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return (B) this;
    }

    public final B maxWidth(final double MAX_WIDTH) {
        properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return (B) this;
    }

    public final B maxHeight(final double MAX_HEIGHT) {
        properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return (B) this;
    }

    public final B scaleX(final double SCALE_X) {
        properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return (B) this;
    }

    public final B scaleY(final double SCALE_Y) {
        properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return (B) this;
    }

    public final B layoutX(final double LAYOUT_X) {
        properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return (B) this;
    }

    public final B layoutY(final double LAYOUT_Y) {
        properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return (B) this;
    }

    public final B translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B) this;
    }

    public final B translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B) this;
    }

    public final B padding(final Insets INSETS) {
        properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return (B) this;
    }


    public final ConcentricRingChart build() {
        final ConcentricRingChart control = new ConcentricRingChart();

        if (properties.keySet().contains("series")) {
            control.setItems(((ObjectProperty<Series<ChartItem>>) properties.get("series")).get());
        }
        if (properties.keySet().contains("itemArray")) {
            control.setItems(((ObjectProperty<ChartItem[]>) properties.get("itemArray")).get());
        }
        if (properties.keySet().contains("itemList")) {
            control.setItems(((ObjectProperty<List<ChartItem>>) properties.get("itemList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth" -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight" -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth" -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight" -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth" -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight" -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX" -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY" -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX" -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY" -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX" -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY" -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding" -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "barBackgroundFill" -> control.setBarBackgroundFill(((ObjectProperty<Color>) property).get());
                case "sorted" -> control.setSorted(((BooleanProperty) property).get());
                case "order" -> control.setOrder(((ObjectProperty<Order>) property).get());
                case "numberFormat" -> control.setNumberFormat(((ObjectProperty<NumberFormat>) property).get());
                case "itemLabelFill" -> control.setItemLabelFill(((ObjectProperty<Color>) property).get());
                case "shortenNumbers" -> control.setShortenNumbers(((BooleanProperty) property).get());
                case "valueVisible" -> control.setValueVisible(((BooleanProperty) property).get());
            }
        });
        return control;
    }
}