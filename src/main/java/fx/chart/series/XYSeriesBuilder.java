package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.XYItem;
import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;

/**
 * Class used to build {@link XYSeries}
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 2025-06-21, 16:27
 */
public class XYSeriesBuilder {

    private HashMap<String, Property> properties = new HashMap<>();

    protected XYSeriesBuilder() {}

    public static XYSeriesBuilder create() {
        return new XYSeriesBuilder();
    }

    public final XYSeriesBuilder items(final XYItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return this;
    }

    public final XYSeriesBuilder items(final List<? extends XYItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return this;
    }

    public final XYSeriesBuilder name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return this;
    }

    public final XYSeriesBuilder fill(final Paint FILL) {
        properties.put("fill", new SimpleObjectProperty<>(FILL));
        return this;
    }

    public final XYSeriesBuilder stroke(final Paint STROKE) {
        properties.put("stroke", new SimpleObjectProperty<>(STROKE));
        return this;
    }

    public final XYSeriesBuilder textFill(final Color FILL) {
        properties.put("textFill", new SimpleObjectProperty<>(FILL));
        return this;
    }

    public final XYSeriesBuilder symbolFill(final Color FILL) {
        properties.put("symbolFill", new SimpleObjectProperty<>(FILL));
        return this;
    }

    public final XYSeriesBuilder symbolStroke(final Color STROKE) {
        properties.put("symbolStroke", new SimpleObjectProperty<>(STROKE));
        return this;
    }

    public final XYSeriesBuilder symbol(final Symbol SYMBOL) {
        properties.put("symbol", new SimpleObjectProperty<>(SYMBOL));
        return this;
    }

    public final XYSeriesBuilder chartType(final ChartType TYPE) {
        properties.put("chartType", new SimpleObjectProperty<>(TYPE));
        return this;
    }

    public final XYSeriesBuilder symbolsVisible(final boolean VISIBLE) {
        properties.put("symbolsVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final XYSeriesBuilder symbolSize(final double SIZE) {
        properties.put("symbolSize", new SimpleDoubleProperty(SIZE));
        return this;
    }

    public final XYSeriesBuilder strokeWidth(final double WIDTH) {
        properties.put("strokeWidth", new SimpleDoubleProperty(WIDTH));
        return this;
    }

    public final XYSeriesBuilder visible(final boolean VISIBLE) {
        properties.put("visible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final XYSeriesBuilder animated(final boolean AUTO) {
        properties.put("animated", new SimpleBooleanProperty(AUTO));
        return this;
    }

    public final XYSeriesBuilder animationDuration(final long DURATION) {
        properties.put("animationDuration", new SimpleLongProperty(DURATION));
        return this;
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
