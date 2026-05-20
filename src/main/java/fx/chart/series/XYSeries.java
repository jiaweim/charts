package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.XYChartItem;
import fx.chart.data.XYItem;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pdk.util.IBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A series
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 04 Jul 2025, 9:51 AM
 */
public class XYSeries<T extends XYItem> extends Series<T> {

    /**
     * class to build {@link XYSeries}
     */
    public static class Builder<T extends XYChartItem> implements IBuilder<XYSeries<T>> {

        private final List<T> itemList_ = new ArrayList<>();
        private String name_;
        private Paint fill_;
        private Paint stroke_;
        private Color textFill_;
        private Color symbolFill_;
        private Color symbolStroke_;
        private Symbol symbol_;
        private ChartType chartType_;
        private boolean symbolsVisible_ = true;
        private double symbolSize_ = -1;
        private double strokeWidth_ = -1;
        private boolean visible_ = true;
        private boolean animated_ = false;
        private long animationDuration_ = 800;

        public Builder() {}

        /**
         * set the series data
         *
         * @param items list of data
         * @return this
         */
        public Builder<T> items(T... items) {
            Collections.addAll(itemList_, items);
            return this;
        }

        /**
         * set the series data
         *
         * @param items list of data
         * @return this
         */
        public Builder<T> items(List<? extends T> items) {
            itemList_.addAll(items);
            return this;
        }

        /**
         * set the series name
         *
         * @param name name
         * @return this
         */
        public Builder<T> name(String name) {
            this.name_ = name;
            return this;
        }

        /**
         * @param paint
         * @return
         */
        public Builder<T> fill(Paint paint) {
            this.fill_ = paint;
            return this;
        }

        public Builder<T> stroke(Paint paint) {
            this.stroke_ = paint;
            return this;
        }

        public Builder<T> textFill(Color color) {
            this.textFill_ = color;
            return this;
        }

        public Builder<T> symbolFill(Color color) {
            this.symbolFill_ = color;
            return this;
        }

        public Builder<T> symbolStroke(Color color) {
            this.symbolStroke_ = color;
            return this;
        }

        public Builder<T> symbol(Symbol symbol) {
            this.symbol_ = symbol;
            return this;
        }

        public Builder<T> chartType(ChartType type) {
            this.chartType_ = type;
            return this;
        }

        public Builder<T> symbolsVisible(boolean symbolsVisible) {
            this.symbolsVisible_ = symbolsVisible;
            return this;
        }

        public Builder<T> symbolSize(double symbolSize) {
            this.symbolSize_ = symbolSize;
            return this;
        }

        public Builder<T> strokeWidth(double strokeWidth) {
            this.strokeWidth_ = strokeWidth;
            return this;
        }

        public Builder<T> visible(boolean visible) {
            this.visible_ = visible;
            return this;
        }

        public Builder<T> animated(boolean animated) {
            this.animated_ = animated;
            return this;
        }

        public Builder<T> animationDuration(long animationDuration) {
            this.animationDuration_ = animationDuration;
            return this;
        }

        @Override
        public XYSeries<T> build() {
            XYSeries<T> series = new XYSeries<>();
            if (!itemList_.isEmpty()) {
                series.setItems(itemList_);
            }
            if (name_ != null) {
                series.setName(name_);
            }
            if (fill_ != null) {
                series.setFill(fill_);
            }
            if (stroke_ != null) {
                series.setStroke(stroke_);
            }
            if (textFill_ != null) {
                series.setTextFill(textFill_);
            }
            if (symbolFill_ != null) {
                series.setSymbolFill(symbolFill_);
            }
            if (symbolStroke_ != null) {
                series.setSymbolStroke(symbolStroke_);
            }
            if (symbol_ != null) {
                series.setSymbol(symbol_);
            }
            if (chartType_ != null) {
                series.setChartType(chartType_);
            }

            series.setSymbolsVisible(symbolsVisible_);
            series.setSymbolSize(symbolSize_);
            series.setStrokeWidth(strokeWidth_);
            series.setVisible(visible_);
            series.setAnimated(animated_);
            series.setAnimationDuration(animationDuration_);
            return series;
        }
    }

    public static Builder<XYChartItem> builder() {
        return new Builder<>();
    }

    public XYSeries() {
        this(null, ChartType.SCATTER, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> items, final ChartType type) {
        this(items, type, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> items, final ChartType type, final boolean showPoints) {
        this(items, type, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, showPoints);
    }

    public XYSeries(final List<T> items, final ChartType type, final Paint stroke) {
        this(items, type, "", Color.TRANSPARENT, stroke, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> items, final ChartType type, final Paint fill, final Paint stroke) {
        this(items, type, "", fill, stroke, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> items, final ChartType type, final String name) {
        this(items, type, name, Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> items, final ChartType type, final String name, final Paint fill, final Paint stroke, final boolean showPoints) {
        this(items, type, name, fill, stroke, Symbol.CIRCLE, showPoints);
    }

    public XYSeries(final List<T> items, final ChartType type, final String name, final Paint fill, final Paint stroke, final Symbol symbol, final boolean symbolsVisible) {
        super(items, type, name, fill, stroke, symbol);
        setSymbolsVisible(symbolsVisible);
    }

    /**
     * Return the minimum x value of the series
     *
     * @return min x, or {@link Double#NaN} if this series is empty.
     */
    public double getMinX() {
        return getItems().stream()
                .mapToDouble(XYItem::getX).min().orElse(Double.NaN);
    }

    /**
     * Return the maximum x value of this series
     *
     * @return max x, or {@link Double#NaN} if this series is empty.
     */
    public double getMaxX() {
        return getItems().stream()
                .mapToDouble(XYItem::getX).max().orElse(Double.NaN);
    }

    /**
     * Return the minimum y value of the series
     *
     * @return min y
     */
    public double getMinY() {
        return getItems().stream().mapToDouble(XYItem::getY).min().orElse(Double.NaN);
    }

    /**
     * Return the maximum y value of this series
     *
     * @return max y
     */
    public double getMaxY() {
        return getItems().stream().mapToDouble(XYItem::getY).max().orElse(Double.NaN);
    }

    public double getRangeX() {return getMaxX() - getMinX();}

    public double getRangeY() {return getMaxY() - getMinY();}

    public double getSumOfXValues() {return getItems().stream().mapToDouble(T::getX).sum();}

    public double getSumOfYValues() {return getItems().stream().mapToDouble(T::getY).sum();}
}
