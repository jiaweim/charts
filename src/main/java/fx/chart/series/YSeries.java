package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.ValueItem;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Comparator;
import java.util.List;


public class YSeries<T extends ValueItem> extends Series {

    public YSeries() {
        this(null, ChartType.DONUT, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE);
    }

    public YSeries(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE);
    }

    public YSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        this(ITEMS, TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE);
    }

    public YSeries(final List<T> ITEMS, final ChartType TYPE, final Paint FILL, final Paint STROKE) {
        this(ITEMS, TYPE, "", FILL, STROKE, Symbol.CIRCLE);
    }

    public YSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE) {
        super(ITEMS, TYPE, NAME, FILL, STROKE, Symbol.CIRCLE);
    }

    public YSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final Symbol SYMBOL) {
        super(ITEMS, TYPE, NAME, FILL, STROKE, SYMBOL);
    }

    @Override
    public ObservableList<T> getItems() {return items;}

    public double getMinY() {return getItems().stream().min(Comparator.comparingDouble(T::getValue)).get().getValue();}

    public double getMaxY() {return getItems().stream().max(Comparator.comparingDouble(T::getValue)).get().getValue();}

    public double getSumOfYValues() {return getItems().stream().mapToDouble(T::getValue).sum();}
}
