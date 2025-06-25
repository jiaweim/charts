package fx.chart.event.type;

import fx.chart.event.EvtPriority;
import fx.chart.event.EvtType;
import fx.chart.toolboxfx.geom.Bounds;

import java.util.Objects;


public class BoundsEvt extends ChangeEvt {

    public static final EvtType<BoundsEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<BoundsEvt> BOUNDS = new EvtType<>(BoundsEvt.ANY, "BOUNDS");

    private final Bounds bounds;

    public BoundsEvt(final EvtType<? extends BoundsEvt> evtType, final Bounds bounds) {
        super(evtType);
        this.bounds = bounds;
    }

    public BoundsEvt(final Object src, final EvtType<? extends BoundsEvt> evtType, final Bounds bounds) {
        super(src, evtType);
        this.bounds = bounds;
    }

    public BoundsEvt(final Object src, final EvtType<? extends BoundsEvt> evtType, final EvtPriority priority, final Bounds bounds) {
        super(src, evtType, priority);
        this.bounds = bounds;
    }

    @Override
    public EvtType<? extends BoundsEvt> getEvtType() {return (EvtType<? extends BoundsEvt>) super.getEvtType();}

    public Bounds getBounds() {return bounds;}

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        BoundsEvt boundsEvt = (BoundsEvt) o;
        return Objects.equals(bounds, boundsEvt.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bounds);
    }
}
