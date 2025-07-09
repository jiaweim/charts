package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.XYChartItem;
import fx.chart.data.XYItem;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pdk.util.IBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * a series
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 04 Jul 2025, 9:51 AM
 */
public class XYSeries<T extends XYItem> extends Series<T> {

    /**
     * class to build {@link XYSeries}
     */
    public static class Builder<T extends XYItem> implements IBuilder<XYSeries<T>> {

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
         * @param ITEMS list of data
         * @return this
         */
        public Builder<T> items(T... ITEMS) {
            Collections.addAll(itemList_, ITEMS);
            return this;
        }

        /**
         * set the series data
         *
         * @param ITEMS list of data
         * @return this
         */
        public Builder<T> items(List<T> ITEMS) {
            itemList_.addAll(ITEMS);
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

    public static <T extends XYChartItem> Builder<T> builder() {
        return new Builder<>();
    }

    public XYSeries() {
        this(null, ChartType.SCATTER, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final boolean SHOW_POINTS) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, SHOW_POINTS);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final Paint STROKE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, STROKE, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final Paint FILL, final Paint STROKE) {
        this(ITEMS, TYPE, "", FILL, STROKE, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        this(ITEMS, TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final boolean SHOW_POINTS) {
        this(ITEMS, TYPE, NAME, FILL, STROKE, Symbol.CIRCLE, SHOW_POINTS);
    }

    public XYSeries(final List<T> items, final ChartType type, final String name, final Paint fill, final Paint stroke, final Symbol symbol, final boolean symbolsVisible) {
        super(items, type, name, fill, stroke, symbol);
        setSymbolsVisible(symbolsVisible);
    }

    @Override
    public ObservableList<T> getItems() {return items_;}

    /**
     * @return true if this series contains no data
     */
    public boolean isEmpty() {
        return items_.isEmpty();
    }

    /**
     * Return the minimum x value of the series
     *
     * @return min x
     */
    public double getMinX() {return getItems().stream().min(Comparator.comparingDouble(T::getX)).get().getX();}

    /**
     * Return the maximum x value of this series
     *
     * @return max x
     */
    public double getMaxX() {return getItems().stream().max(Comparator.comparingDouble(T::getX)).get().getX();}

    /**
     * Return the minimum y value of the series
     *
     * @return min y
     */
    public double getMinY() {return getItems().stream().min(Comparator.comparingDouble(T::getY)).get().getY();}

    /**
     * Retuen the maximum y value of this series
     *
     * @return max y
     */
    public double getMaxY() {return getItems().stream().max(Comparator.comparingDouble(T::getY)).get().getY();}

    public double getRangeX() {return getMaxX() - getMinX();}

    public double getRangeY() {return getMaxY() - getMinY();}

    public double getSumOfXValues() {return getItems().stream().mapToDouble(T::getX).sum();}

    public double getSumOfYValues() {return getItems().stream().mapToDouble(T::getY).sum();}
}
