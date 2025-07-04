package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.ChartItem;
import fx.chart.tools.Order;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ChartItemSeries<T extends ChartItem> extends Series<T> {

    public ChartItemSeries() {
        super();
    }

    public ChartItemSeries(final ChartType TYPE, final String NAME, final T... ITEMS) {
        super(Arrays.asList(ITEMS), TYPE, NAME);
    }

    public ChartItemSeries(final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final T... ITEMS) {
        super(Arrays.asList(ITEMS), TYPE, NAME, FILL, STROKE, Symbol.NONE);
    }

    public ChartItemSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE) {
        super(ITEMS, TYPE, NAME, FILL, STROKE, Symbol.NONE);
    }

    public ChartItemSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final Symbol SYMBOL) {
        super(ITEMS, TYPE, NAME, FILL, STROKE, SYMBOL);
    }


    // ******************** Methods *******************************************
    @Override
    public ObservableList<T> getItems() {return items_;}

    public double getMinValue() {return items_.stream().mapToDouble(T::getValue).min().orElse(0d);}

    public double getMaxValue() {return items_.stream().mapToDouble(T::getValue).max().orElse(100d);}

    public double getSumOfAllItems() {return items_.stream().mapToDouble(T::getValue).sum();}

    public void sort(final Order order) {
        switch (order) {
            case ASCENDING -> Collections.sort(getItems(), Comparator.comparingDouble(ChartItem::getValue));
            case DESCENDING -> Collections.sort(getItems(), Comparator.comparingDouble(ChartItem::getValue).reversed());
        }
    }
}
