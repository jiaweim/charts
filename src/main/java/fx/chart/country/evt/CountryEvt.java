package fx.chart.country.evt;

import fx.chart.event.EvtPriority;
import fx.chart.event.EvtType;
import fx.chart.event.type.ChangeEvt;

public class CountryEvt<T> extends ChangeEvt {
    public static final EvtType<CountryEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<CountryEvt> UPDATE = new EvtType<>(CountryEvt.ANY, "UPDATE");
    public static final EvtType<CountryEvt> FINISHED = new EvtType<>(CountryEvt.ANY, "FINISHED");
    public static final EvtType<CountryEvt> SELECTED = new EvtType<>(CountryEvt.ANY, "SELECTED");
    public static final EvtType<CountryEvt> LOCATION = new EvtType<>(CountryEvt.ANY, "LOCATION");
    public static final EvtType<CountryEvt> CONNECTION_SELECTED_FROM = new EvtType<>(CountryEvt.ANY, "CONNECTION_SELECTED_FROM");
    public static final EvtType<CountryEvt> CONNECTION_SELECTED_TO = new EvtType<>(CountryEvt.ANY, "CONNECTION_SELECTED_TO");
    public static final EvtType<CountryEvt> CONNECTION_SELECTED = new EvtType<>(CountryEvt.ANY, "CONNECTION_SELECTED");

    private final T data;


    public CountryEvt(final Object src, final EvtType<? extends CountryEvt> evtType, final T data) {
        super(src, evtType);
        this.data = data;
    }

    public CountryEvt(final Object src, final EvtType<? extends CountryEvt> evtType, final EvtPriority priority, final T data) {
        super(src, evtType, priority);
        this.data = data;
    }

    public T getData() {return data;}
}
