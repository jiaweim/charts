package fx.chart.event;


import fx.chart.data.ChartItem;

/**
 *
 * This class is used to represent listeners for {@link SeriesEvent}.
 *
 * @author Jiawei Mao
 * @author hansolo
 * @version 1.0.0
 * @since 20 May 2026, 11:44 AM
 */
@FunctionalInterface
public interface SeriesEventListener<T extends ChartItem> {

    void onModelEvent(final SeriesEvent<T> event);

}
