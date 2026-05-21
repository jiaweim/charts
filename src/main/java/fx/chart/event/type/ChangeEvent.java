package fx.chart.event.type;

import fx.chart.event.EventPriority;
import fx.chart.event.EventType;
import fx.chart.event.FxEvent;

/**
 * Change event
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:55 AM
 */
public class ChangeEvent extends FxEvent {

    /**
     * root type of the {@link ChangeEvent}
     */
    public static final EventType<ChangeEvent> ANY = new EventType<>(FxEvent.ANY, "CHANGE_EVENT");

    public ChangeEvent(final Object src, final EventType<? extends ChangeEvent> evtType) {
        super(src, evtType);
    }

    public ChangeEvent(final Object src, final EventType<? extends ChangeEvent> evtType, final EventPriority priority) {
        super(src, evtType, priority);
    }

    @Override
    public EventType<? extends ChangeEvent> getEventType() {
        return (EventType<? extends ChangeEvent>) super.getEventType();
    }
}
