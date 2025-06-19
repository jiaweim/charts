package fx.chart.event;

import fx.chart.data.ChartItem;
import fx.chart.series.Series;


/**
 * Created by hansolo on 16.07.17.
 */
public class SeriesEvent<T extends ChartItem> {

    private final Series<T> SERIES;

    public SeriesEvent(final Series<T> SERIES) {
        this.SERIES = SERIES;
    }

    public Series<T> getSeries() {return SERIES;}
}
