package fx.chart.toolbox.evt;

import java.util.EventListener;

@FunctionalInterface
public interface EvtObserver<T extends Evt> extends EventListener {
    void handle(T event);
}
