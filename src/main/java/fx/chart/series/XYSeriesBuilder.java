package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.XYItem;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;


public class XYSeriesBuilder<B extends XYSeriesBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();

    protected XYSeriesBuilder() {}


    public static final XYSeriesBuilder create() {
        return new XYSeriesBuilder();
    }

    public final B items(final XYItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B) this;
    }

    public final B items(final List<XYItem> ITEMS) {
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

    public final B visible(final boolean VISIBLE) {
        properties.put("visible", new SimpleBooleanProperty(VISIBLE));
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


    public final XYSeries build() {
        final XYSeries control = new XYSeries();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<XYItem[]>) properties.get("itemsArray")).get());
        }
        if (properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<XYItem>>) properties.get("itemsList")).get());
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
                case "symbolsVisible" -> control.setSymbolsVisible(((BooleanProperty) property).get());
                case "symbolSize" -> control.setSymbolSize(((DoubleProperty) property).get());
                case "strokeWidth" -> control.setStrokeWidth(((DoubleProperty) property).get());
                case "visible" -> control.setVisible(((BooleanProperty) property).get());
                case "animated" -> control.setAnimated(((BooleanProperty) property).get());
                case "animationDuration" -> control.setAnimationDuration(((LongProperty) property).get());
            }
        });
        return control;
    }
}
