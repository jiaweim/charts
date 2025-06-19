package fx.chart.toolbox.evtbus;

import fx.chart.toolbox.evt.Evt;


public interface EvtBus {

    <T extends Evt> void publish(fx.chart.toolbox.evtbus.Topic topic, T evt);

    void subscribe(fx.chart.toolbox.evtbus.Topic topic, Subscriber subscriber);

    void unsubscribe(Topic topic, Subscriber subscriber);
}
