package fx.charts.event;

import fx.charts.data.ChartItem;
import fx.charts.series.Series;


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
