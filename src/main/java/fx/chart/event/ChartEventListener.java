package fx.chart.event;

import java.util.EventListener;

/**
 * Event Listener.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:47 AM
 */
@FunctionalInterface
public interface ChartEventListener<T extends FxEvent> extends EventListener {

    /**
     * handle event
     *
     * @param event {@link FxEvent}
     */
    void handle(T event);
}
