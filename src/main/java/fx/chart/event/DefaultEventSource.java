package fx.chart.event;

import fx.chart.event.type.ChangeEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event source, capable of registering listeners.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 9:55 AM
 */
public class DefaultEventSource implements EventSource {

    private final ConcurrentHashMap<EventType, Set<ChartEventListener<? extends ChangeEvent>>> observers = new ConcurrentHashMap<>();

    @Override
    public Map<EventType, Set<ChartEventListener<? extends ChangeEvent>>> getListenerMap() {
        return observers;
    }
}
