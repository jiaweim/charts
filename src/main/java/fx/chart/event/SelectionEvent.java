package fx.chart.event;

import fx.chart.data.ChartItem;
import fx.chart.series.ChartItemSeries;


public class SelectionEvent<T extends ChartItem> extends ChartEvent {

    private final ChartItemSeries<T> series_;
    private final T item_;

    public SelectionEvent(final T ITEM) {
        this(null, ITEM, ChartEvent.ITEM_SELECTED);
    }

    public SelectionEvent(final ChartItemSeries<T> SERIES) {
        this(SERIES, null, ChartEvent.SERIES_SELECTED);
    }

    public SelectionEvent(final ChartItemSeries<T> SERIES, final T ITEM) {this(SERIES, ITEM, ChartEvent.ITEM_AND_SERIES_SELECTED);}

    public SelectionEvent(final ChartItemSeries<T> SERIES, final T ITEM, final EventType<? extends ChartEvent> TYPE) {
        super(null == ITEM ? SERIES : ITEM, TYPE);
        this.series_ = SERIES;
        this.item_ = ITEM;
    }

    public ChartItemSeries<T> getSeries() {return series_;}

    public T getItem() {return item_;}

    @Override
    public String toString() {
        String ret;
        if (series_ == null) {
            if (item_ == null) {
                ret = "{}";
            }
            // Only Item
            ret = "{\n" +
                    "  \"item\"  :\"" + item_.getName() + "\",\n" +
                    "  \"value\" :" + item_.getValue() + "\n" +
                    "}";
        } else {
            if (item_ == null) {
                // Only Series
                ret = "{\n" +
                        "  \"series\":\"" + series_.getName() + "\",\n" +
                        "  \"sum\"   :" + series_.getSumOfAllItems() + "\n" +
                        "}";
            } else {
                // Series and Item
                ret = "{\n" +
                        "  \"series\":\"" + series_.getName() + "\",\n" +
                        "  \"sum\"   :" + series_.getSumOfAllItems() + ",\n" +
                        "  \"item\"  :\"" + item_.getName() + "\",\n" +
                        "  \"value\" :" + item_.getValue() + "\n" +
                        "}";
            }
        }
        return ret;
    }
}
