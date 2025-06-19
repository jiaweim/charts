package fx.charts.series;

import fx.charts.ChartType;
import fx.charts.Symbol;
import fx.charts.data.ChartItem;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;


public class ChartItemSeriesBuilder<B extends ChartItemSeriesBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();


    protected ChartItemSeriesBuilder() {}


    // ******************** Methods *******************************************
    public static final ChartItemSeriesBuilder create() {
        return new ChartItemSeriesBuilder();
    }

    public final B items(final ChartItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B) this;
    }

    public final B items(final List<ChartItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return (B) this;
    }

    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return (B) this;
    }

    public final B fill(final Paint FILL) {
        properties.put("fill", new SimpleObjectProperty<>(FILL));
        return (B) this;
    }

    public final B stroke(final Paint STROKE) {
        properties.put("stroke", new SimpleObjectProperty<>(STROKE));
        return (B) this;
    }

    public final B textFill(final Color FILL) {
        properties.put("textFill", new SimpleObjectProperty<>(FILL));
        return (B) this;
    }

    public final B symbolFill(final Color FILL) {
        properties.put("symbolFill", new SimpleObjectProperty<>(FILL));
        return (B) this;
    }

    public final B symbolStroke(final Color STROKE) {
        properties.put("symbolStroke", new SimpleObjectProperty<>(STROKE));
        return (B) this;
    }

    public final B symbol(final Symbol SYMBOL) {
        properties.put("symbol", new SimpleObjectProperty<>(SYMBOL));
        return (B) this;
    }

    public final B chartType(final ChartType TYPE) {
        properties.put("chartType", new SimpleObjectProperty<>(TYPE));
        return (B) this;
    }

    public final B symbolsVisible(final boolean VISIBLE) {
        properties.put("symbolsVisible", new SimpleBooleanProperty(VISIBLE));
        return (B) this;
    }

    public final B symbolSize(final double SIZE) {
        properties.put("symbolSize", new SimpleDoubleProperty(SIZE));
        return (B) this;
    }

    public final B strokeWidth(final double WIDTH) {
        properties.put("strokeWidth", new SimpleDoubleProperty(WIDTH));
        return (B) this;
    }

    public final B animated(final boolean AUTO) {
        properties.put("animated", new SimpleBooleanProperty(AUTO));
        return (B) this;
    }

    public final B animationDuration(final long DURATION) {
        properties.put("animationDuration", new SimpleLongProperty(DURATION));
        return (B) this;
    }


    public final ChartItemSeries build() {
        final ChartItemSeries control = new ChartItemSeries();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<ChartItem[]>) properties.get("itemsArray")).get());
        }
        if (properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<ChartItem>>) properties.get("itemsList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "name" -> control.setName(((StringProperty) property).get());
                case "fill" -> control.setFill(((ObjectProperty<Paint>) property).get());
                case "stroke" -> control.setStroke(((ObjectProperty<Paint>) property).get());
                case "textFill" -> control.setTextFill(((ObjectProperty<Color>) property).get());
                case "symbolFill" -> control.setSymbolFill(((ObjectProperty<Color>) property).get());
                case "symbolStroke" -> control.setSymbolStroke(((ObjectProperty<Color>) property).get());
                case "symbol" -> control.setSymbol(((ObjectProperty<Symbol>) property).get());
                case "chartType" -> control.setChartType(((ObjectProperty<ChartType>) property).get());
                case "symbolSize" -> control.setSymbolSize(((DoubleProperty) property).get());
                case "strokeWidth" -> control.setStrokeWidth(((DoubleProperty) property).get());
                case "animated" -> control.setAnimated(((BooleanProperty) property).get());
                case "animationDuration" -> control.setAnimationDuration(((LongProperty) property).get());
            }
        });
        return control;
    }
}
