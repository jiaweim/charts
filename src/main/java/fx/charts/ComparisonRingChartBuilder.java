package fx.charts;

import fx.charts.data.ChartItem;
import fx.charts.series.ChartItemSeries;
import fx.charts.tools.NumberFormat;
import fx.charts.tools.Order;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.HashMap;


public class ComparisonRingChartBuilder<B extends ComparisonRingChartBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();
    private ChartItemSeries<ChartItem> series1;
    private ChartItemSeries<ChartItem> series2;

    protected ComparisonRingChartBuilder(final ChartItemSeries<ChartItem> SERIES_1, final ChartItemSeries<ChartItem> SERIES_2) {
        series1 = SERIES_1;
        series2 = SERIES_2;
    }

    public static final ComparisonRingChartBuilder create(final ChartItemSeries<ChartItem> SERIES_1, final ChartItemSeries<ChartItem> SERIES_2) {
        return new ComparisonRingChartBuilder(SERIES_1, SERIES_2);
    }

    public final B barBackgroundColor(final Color COLOR) {
        properties.put("barBackgroundColor", new SimpleObjectProperty<>(COLOR));
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


    public final ComparisonRingChart build() {
        final ComparisonRingChart control = new ComparisonRingChart(series1, series2);

        properties.forEach((key, property) -> {
            if ("prefSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setPrefSize(dim.getWidth(), dim.getHeight());
            } else if ("minSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setMinSize(dim.getWidth(), dim.getHeight());
            } else if ("maxSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setMaxSize(dim.getWidth(), dim.getHeight());
            } else if ("prefWidth".equals(key)) {
                control.setPrefWidth(((DoubleProperty) property).get());
            } else if ("prefHeight".equals(key)) {
                control.setPrefHeight(((DoubleProperty) property).get());
            } else if ("minWidth".equals(key)) {
                control.setMinWidth(((DoubleProperty) property).get());
            } else if ("minHeight".equals(key)) {
                control.setMinHeight(((DoubleProperty) property).get());
            } else if ("maxWidth".equals(key)) {
                control.setMaxWidth(((DoubleProperty) property).get());
            } else if ("maxHeight".equals(key)) {
                control.setMaxHeight(((DoubleProperty) property).get());
            } else if ("scaleX".equals(key)) {
                control.setScaleX(((DoubleProperty) property).get());
            } else if ("scaleY".equals(key)) {
                control.setScaleY(((DoubleProperty) property).get());
            } else if ("layoutX".equals(key)) {
                control.setLayoutX(((DoubleProperty) property).get());
            } else if ("layoutY".equals(key)) {
                control.setLayoutY(((DoubleProperty) property).get());
            } else if ("translateX".equals(key)) {
                control.setTranslateX(((DoubleProperty) property).get());
            } else if ("translateY".equals(key)) {
                control.setTranslateY(((DoubleProperty) property).get());
            } else if ("padding".equals(key)) {
                control.setPadding(((ObjectProperty<Insets>) property).get());
            } // Control specific properties
            else if ("barBackgroundColor".equals(key)) {
                control.setBarBackgroundFill(((ObjectProperty<Color>) property).get());
            } else if ("sorted".equals(key)) {
                control.setSorted(((BooleanProperty) property).get());
            } else if ("order".equals(key)) {
                control.setOrder(((ObjectProperty<Order>) property).get());
            } else if ("numberFormat".equals(key)) {
                control.setNumberFormat(((ObjectProperty<NumberFormat>) property).get());
            }
        });
        return control;
    }
}
