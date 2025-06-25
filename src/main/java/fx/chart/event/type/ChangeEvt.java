package fx.chart.event.type;

import fx.chart.event.Evt;
import fx.chart.event.EvtPriority;
import fx.chart.event.EvtType;

/**
 * Change event
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:55 AM
 */
public class ChangeEvt extends Evt {

    /**
     * root type of the {@link ChangeEvt}
     */
    public static final EvtType<ChangeEvt> ANY = new EvtType<>(Evt.ANY, "CHANGE_EVT");

    public ChangeEvt(final EvtType<? extends ChangeEvt> evtType) {
        super(evtType);
    }

    public ChangeEvt(final Object src, final EvtType<? extends ChangeEvt> evtType) {
        super(src, evtType);
    }

    public ChangeEvt(final Object src, final EvtType<? extends ChangeEvt> evtType, final EvtPriority priority) {
        super(src, evtType, priority);
    }

    @Override
    public EvtType<? extends ChangeEvt> getEvtType() {
        return (EvtType<? extends ChangeEvt>) super.getEvtType();
    }
}
