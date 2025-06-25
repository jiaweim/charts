package fx.chart.event;

import java.util.EventListener;

/**
 * Event Listener.
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 25 Jun 2025, 10:47 AM
 */
@FunctionalInterface
public interface EvtObserver<T extends Evt> extends EventListener {
    void handle(T event);
}
