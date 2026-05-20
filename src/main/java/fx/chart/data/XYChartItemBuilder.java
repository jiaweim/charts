package fx.chart.data;

import fx.chart.Symbol;
import javafx.scene.paint.Color;
import pdk.util.IBuilder;

/**
 * Builder class for {@link XYChartItem}.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 5:04 PM
 */
public class XYChartItemBuilder implements IBuilder<XYChartItem> {

    public static XYChartItemBuilder create() {
        return new XYChartItemBuilder();
    }

    private double x = 0;
    private double y = 0;
    private String name = "";
    private Color fill = Color.RED;
    private Color stroke = Color.TRANSPARENT;
    private Symbol symbol = Symbol.NONE;
    private boolean isEmpty = false;
    private String tooltipText = "";

    protected XYChartItemBuilder() {}

    public final XYChartItemBuilder x(final double x) {
        this.x = x;
        return this;
    }

    public final XYChartItemBuilder y(final double y) {
        this.y = y;
        return this;
    }

    public final XYChartItemBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public final XYChartItemBuilder fill(final Color fill) {
        this.fill = fill;
        return this;
    }

    public final XYChartItemBuilder stroke(final Color color) {
        this.stroke = color;
        return this;
    }

    public final XYChartItemBuilder symbol(final Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public final XYChartItemBuilder isEmpty(final boolean isEmpty) {
        this.isEmpty = isEmpty;
        return this;
    }

    public final XYChartItemBuilder tooltipText(final String tooltipText) {
        this.tooltipText = tooltipText;
        return this;
    }

    public final XYChartItem build() {
        return new XYChartItem(x, y, name, fill, stroke, symbol, tooltipText, isEmpty);
    }
}
