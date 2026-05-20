package fx.chart.event;

import fx.chart.data.ChartItem;
import fx.chart.series.Series;

/**
 * This class is used to represent events that occur on a {@link Series}.
 *
 * @author Jiawei Mao
 * @author hansolo
 * @version 1.0.0
 * @since 20 May 2026, 11:45 AM
 */
public class SeriesEvent<T extends ChartItem> {

    private final Series<T> series;

    public SeriesEvent(final Series<T> series) {
        this.series = series;
    }

    /**
     * Return the series that triggers the event.
     *
     * @return {@link Series}
     */
    public Series<T> getSeries() {return series;}
}
