package fx.chart.event;

import fx.chart.data.ChartItem;
import fx.chart.series.ChartItemSeries;


public class SelectionEvt<T extends ChartItem> extends ChartEvt {

    private final ChartItemSeries<T> SERIES;
    private final T ITEM;
    private final EvtType<? extends ChartEvt> TYPE;

    public SelectionEvt(final T ITEM) {
        this(null, ITEM, ChartEvt.ITEM_SELECTED);
    }

    public SelectionEvt(final ChartItemSeries<T> SERIES) {
        this(SERIES, null, ChartEvt.SERIES_SELECTED);
    }

    public SelectionEvt(final ChartItemSeries<T> SERIES, final T ITEM) {this(SERIES, ITEM, ChartEvt.ITEM_AND_SERIES_SELECTED);}

    public SelectionEvt(final ChartItemSeries<T> SERIES, final T ITEM, final EvtType<? extends ChartEvt> TYPE) {
        super(null == ITEM ? SERIES : ITEM, TYPE);
        this.SERIES = SERIES;
        this.ITEM = ITEM;
        this.TYPE = TYPE;
    }

    public ChartItemSeries<T> getSeries() {return SERIES;}

    public T getItem() {return ITEM;}

    public EvtType<? extends ChartEvt> getEventType() {return TYPE;}

    @Override
    public String toString() {
        String ret;
        if (null == SERIES) {
            if (null == ITEM) {
                ret = "{}";
            }
            // Only Item
            ret = new StringBuilder().append("{\n")
                    .append("  \"item\"  :\"").append(ITEM.getName()).append("\",\n")
                    .append("  \"value\" :").append(ITEM.getValue()).append("\n")
                    .append("}").toString();
        } else {
            if (null == ITEM) {
                // Only Series
                ret = new StringBuilder().append("{\n")
                        .append("  \"series\":\"").append(SERIES.getName()).append("\",\n")
                        .append("  \"sum\"   :").append(SERIES.getSumOfAllItems()).append("\n")
                        .append("}").toString();
            } else {
                // Series and Item
                ret = new StringBuilder().append("{\n")
                        .append("  \"series\":\"").append(SERIES.getName()).append("\",\n")
                        .append("  \"sum\"   :").append(SERIES.getSumOfAllItems()).append(",\n")
                        .append("  \"item\"  :\"").append(ITEM.getName()).append("\",\n")
                        .append("  \"value\" :").append(ITEM.getValue()).append("\n")
                        .append("}").toString();
            }
        }
        return ret;
    }
}
