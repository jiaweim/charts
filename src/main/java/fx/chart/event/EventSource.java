package fx.chart.event;

import fx.chart.event.type.ChangeEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Interface for object support adding and removing listeners.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 12:37 PM
 */
public interface EventSource {

    /**
     * Return the Map holding all added listeners.
     *
     * @return {@link Map} of all added listeners.
     */
    Map<EventType, Set<ChartEventListener<? extends ChangeEvent>>> getListenerMap();

    /**
     * Add a {@link ChartEventListener}
     *
     * @param type     {@link EventType}
     * @param listener {@link ChartEventListener}
     */
    default <T extends ChangeEvent> void addEventListener(final EventType type, final ChartEventListener<T> listener) {
        Objects.requireNonNull(listener);
        getListenerMap().computeIfAbsent(type, k -> new CopyOnWriteArraySet<>()).add(listener);
    }

    /**
     * Remove the specified listener of the specified event type.
     *
     * @param type     {@link EventType}
     * @param listener {@link ChartEventListener}
     */
    default <T extends ChangeEvent> void removeEventListener(final EventType type, final ChartEventListener<T> listener) {
        Objects.requireNonNull(listener);
        Set<ChartEventListener<? extends ChangeEvent>> listeners = getListenerMap().get(type);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Remove all listeners attached to this object.
     */
    default void removeAllEventListeners() {
        getListenerMap().clear();
    }

    /**
     * Trigger the specified event and notify all registered listeners for this event.
     *
     * @param event {@link ChartEvent}
     */
    default void fireChartEvent(final ChangeEvent event) {
        fireChartEvent(event, ChartEvent.ANY);
    }

    /**
     * Trigger the specified event and notify all registered listeners for this event.
     *
     * @param event {@link ChartEvent}
     * @param ANY   {@link EventType} of the parent any type.
     */
    default void fireChartEvent(final ChangeEvent event, EventType ANY) {
        Objects.requireNonNull(event);
        final EventType type = event.getEventType();
        // Notify listeners of any type.
        Set<ChartEventListener<? extends ChangeEvent>> anyListeners = getListenerMap().get(ANY);
        if (anyListeners != null) {
            for (var listener : anyListeners) {
                ((ChartEventListener<ChangeEvent>) listener).handle(event);
            }
        }
        // Notify listeners of specific types.
        if (!Objects.equals(type, ANY)) {
            Set<ChartEventListener<? extends ChangeEvent>> listeners = getListenerMap().get(type);
            if (listeners != null) {
                for (ChartEventListener<? extends ChangeEvent> listener : listeners) {
                    ((ChartEventListener<ChangeEvent>) listener).handle(event);
                }
            }
        }
    }
}
