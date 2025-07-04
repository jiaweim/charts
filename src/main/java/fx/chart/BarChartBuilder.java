package fx.chart;

import fx.chart.data.ChartItem;
import fx.chart.tools.NumberFormat;
import fx.chart.tools.Order;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;


public class BarChartBuilder<B extends BarChartBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();

    protected BarChartBuilder() {}

    public static final BarChartBuilder create() {
        return new BarChartBuilder();
    }


    public final B items(final ChartItem... items) {
        properties.put("itemsArray", new SimpleObjectProperty(items));
        return (B) this;
    }

    public final B items(final List<ChartItem> items) {
        properties.put("itemsList", new SimpleObjectProperty<>(items));
        return (B) this;
    }

    public final B orientation(final Orientation orientation) {
        properties.put("orientation", new SimpleObjectProperty<>(orientation));
        return (B) this;
    }

    public final B backgroundFill(final Paint backgroundFill) {
        properties.put("backgroundFill", new SimpleObjectProperty<>(backgroundFill));
        return (B) this;
    }

    public final B namesBackgroundFill(final Paint namesBackgroundFill) {
        properties.put("namesBackgroundFill", new SimpleObjectProperty<>(namesBackgroundFill));
        return (B) this;
    }

    public final B barBackgroundFill(final Color barBackgroundFill) {
        properties.put("barBackgroundFill", new SimpleObjectProperty<>(barBackgroundFill));
        return (B) this;
    }

    public final B seriesFill(final Paint seriesFill) {
        properties.put("seriesFill", new SimpleObjectProperty<>(seriesFill));
        return (B) this;
    }

    public final B textFill(final Color textFill) {
        properties.put("textFill", new SimpleObjectProperty<>(textFill));
        return (B) this;
    }

    public final B namesTextFill(final Color namesTextFill) {
        properties.put("namesTextFill", new SimpleObjectProperty<>(namesTextFill));
        return (B) this;
    }

    public final B barBackgroundVisible(final boolean barBackgroundVisible) {
        properties.put("barBackgroundVisible", new SimpleBooleanProperty(barBackgroundVisible));
        return (B) this;
    }

    public final B shadowsVisible(final boolean shadowsVisible) {
        properties.put("shadowsVisible", new SimpleBooleanProperty(shadowsVisible));
        return (B) this;
    }

    public final B numberFormat(final NumberFormat numberFormat) {
        properties.put("numberFormat", new SimpleObjectProperty(numberFormat));
        return (B) this;
    }

    public final B useItemFill(final boolean useItemFill) {
        properties.put("useItemFill", new SimpleBooleanProperty(useItemFill));
        return (B) this;
    }

    public final B useItemTextFill(final boolean useItemTextFill) {
        properties.put("useItemTextFill", new SimpleBooleanProperty(useItemTextFill));
        return (B) this;
    }

    public final B useNamesTextFill(final boolean useNamesTextFill) {
        properties.put("useNamesTextFill", new SimpleBooleanProperty(useNamesTextFill));
        return (B) this;
    }

    public final B shortenNumbers(final boolean shortenNumbers) {
        properties.put("shortenNumbers", new SimpleBooleanProperty(shortenNumbers));
        return (B) this;
    }

    public final B sorted(final boolean sorted) {
        properties.put("sorted", new SimpleBooleanProperty(sorted));
        return (B) this;
    }

    public final B order(final Order order) {
        properties.put("order", new SimpleObjectProperty<>(order));
        return (B) this;
    }

    public final B animated(final boolean animated) {
        properties.put("animated", new SimpleBooleanProperty(animated));
        return (B) this;
    }

    public final B animationDuration(final long animationDuration) {
        properties.put("animationDuration", new SimpleLongProperty(animationDuration));
        return (B) this;
    }

    public final B minNumberOfBars(final int minNumberOfBars) {
        properties.put("minNumberOfBars", new SimpleIntegerProperty(minNumberOfBars));
        return (B) this;
    }

    public final B useMinNumberOfBars(final boolean useMinNumberOfBars) {
        properties.put("useMinNumberOfBars", new SimpleBooleanProperty(useMinNumberOfBars));
        return (B) this;
    }

    public final B useGivenColors(final boolean useGivenColors) {
        properties.put("useGivenColors", new SimpleBooleanProperty(useGivenColors));
        return (B) this;
    }

    public final B colors(final List<Color> colors) {
        properties.put("colors", new SimpleObjectProperty<>(colors));
        return (B) this;
    }

    public final B barCornerRadius(final double barCornerRadius) {
        properties.put("barCornerRadius", new SimpleDoubleProperty(Math.clamp(barCornerRadius, 0, 20)));
        return (B) this;
    }

    public final B boldValueFont(final boolean boldValueFont) {
        properties.put("boldValueFont", new SimpleBooleanProperty(boldValueFont));
        return (B) this;
    }

    // General properties
    public final B prefSize(final double width, final double height) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }

    public final B minSize(final double width, final double height) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }

    public final B maxSize(final double width, final double height) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }

    public final B prefWidth(final double prefWidth) {
        properties.put("prefWidth", new SimpleDoubleProperty(prefWidth));
        return (B) this;
    }

    public final B prefHeight(final double prefHeight) {
        properties.put("prefHeight", new SimpleDoubleProperty(prefHeight));
        return (B) this;
    }

    public final B minWidth(final double minWidth) {
        properties.put("minWidth", new SimpleDoubleProperty(minWidth));
        return (B) this;
    }

    public final B minHeight(final double minHeight) {
        properties.put("minHeight", new SimpleDoubleProperty(minHeight));
        return (B) this;
    }

    public final B maxWidth(final double maxWidth) {
        properties.put("maxWidth", new SimpleDoubleProperty(maxWidth));
        return (B) this;
    }

    public final B maxHeight(final double maxHeight) {
        properties.put("maxHeight", new SimpleDoubleProperty(maxHeight));
        return (B) this;
    }

    public final B scaleX(final double scaleX) {
        properties.put("scaleX", new SimpleDoubleProperty(scaleX));
        return (B) this;
    }

    public final B scaleY(final double scaleY) {
        properties.put("scaleY", new SimpleDoubleProperty(scaleY));
        return (B) this;
    }

    public final B layoutX(final double layoutX) {
        properties.put("layoutX", new SimpleDoubleProperty(layoutX));
        return (B) this;
    }

    public final B layoutY(final double layoutY) {
        properties.put("layoutY", new SimpleDoubleProperty(layoutY));
        return (B) this;
    }

    public final B translateX(final double translateX) {
        properties.put("translateX", new SimpleDoubleProperty(translateX));
        return (B) this;
    }

    public final B translateY(final double translateY) {
        properties.put("translateY", new SimpleDoubleProperty(translateY));
        return (B) this;
    }

    public final B padding(final Insets insets) {
        properties.put("padding", new SimpleObjectProperty<>(insets));
        return (B) this;
    }


    public final BarChart build() {
        final BarChart barChart = new BarChart();

        if (properties.keySet().contains("itemsArray")) {
            barChart.setItems(((ObjectProperty<? extends ChartItem[]>) properties.get("itemsArray")).get());
        }
        if (properties.keySet().contains("itemsList")) {
            barChart.setItems(((ObjectProperty<List<? extends ChartItem>>) properties.get("itemsList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    barChart.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    barChart.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    barChart.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth" -> barChart.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight" -> barChart.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth" -> barChart.setMinWidth(((DoubleProperty) property).get());
                case "minHeight" -> barChart.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth" -> barChart.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight" -> barChart.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX" -> barChart.setScaleX(((DoubleProperty) property).get());
                case "scaleY" -> barChart.setScaleY(((DoubleProperty) property).get());
                case "layoutX" -> barChart.setLayoutX(((DoubleProperty) property).get());
                case "layoutY" -> barChart.setLayoutY(((DoubleProperty) property).get());
                case "translateX" -> barChart.setTranslateX(((DoubleProperty) property).get());
                case "translateY" -> barChart.setTranslateY(((DoubleProperty) property).get());
                case "padding" -> barChart.setPadding(((ObjectProperty<Insets>) property).get());
                case "orientation" -> barChart.setOrientation(((ObjectProperty<Orientation>) property).get());
                case "backgroundFill" -> barChart.setBackgroundFill(((ObjectProperty<Paint>) property).get());
                case "namesBackgroundFill" -> barChart.setNamesBackgroundFill(((ObjectProperty<Paint>) property).get());
                case "barBackgroundFill" -> barChart.setBarBackgroundFill(((ObjectProperty<Color>) property).get());
                case "seriesFill" -> barChart.setSeriesFill(((ObjectProperty<Paint>) property).get());
                case "textFill" -> barChart.setTextFill(((ObjectProperty<Color>) property).get());
                case "namesTextFill" -> barChart.setNamesTextFill(((ObjectProperty<Color>) property).get());
                case "barBackgroundVisible" -> barChart.setBarBackgroundVisible(((BooleanProperty) property).get());
                case "shadowsVisible" -> barChart.setShadowsVisible(((BooleanProperty) property).get());
                case "numberFormat" -> barChart.setNumberFormat(((ObjectProperty<NumberFormat>) property).get());
                case "useItemFill" -> barChart.setUseItemFill(((BooleanProperty) property).get());
                case "useItemTextFill" -> barChart.setUseItemTextFill(((BooleanProperty) property).get());
                case "useNamesTextFill" -> barChart.setUseNamesTextFill(((BooleanProperty) property).get());
                case "shortenNumbers" -> barChart.setShortenNumbers(((BooleanProperty) property).get());
                case "sorted" -> barChart.setSorted(((BooleanProperty) property).get());
                case "order" -> barChart.setOrder(((ObjectProperty<Order>) property).get());
                case "animated" -> barChart.setAnimated(((BooleanProperty) property).get());
                case "animationDuration" -> barChart.setAnimationDuration(((LongProperty) property).get());
                case "minNumberOfBars" -> barChart.setMinNumberOfBars(((IntegerProperty) property).get());
                case "useMinNumberOfBars" -> barChart.setUseMinNumberOfBars(((BooleanProperty) property).get());
                case "useGivenColors" -> barChart.setUseGivenColors(((BooleanProperty) property).get());
                case "colors" -> barChart.setColors(((ObjectProperty<List<Color>>) property).get());
                case "barCornerRadius" -> barChart.setBarCornerRadius(((DoubleProperty) property).get());
                case "boldValueFont" -> barChart.setBoldValueFont(((BooleanProperty) property).get());
            }
        });
        return barChart;
    }
}
