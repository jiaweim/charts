package fx.chart.toolbox.evtbus;

import fx.chart.toolbox.evt.Evt;
import fx.chart.toolbox.evt.EvtType;


public interface Subscriber {

    EvtType<Evt> getEvtType();

    void handle(Evt evt);
}
