package fx.charts.data;

import eu.hansolo.toolbox.evt.EvtObserver;
import eu.hansolo.toolbox.evt.EvtType;
import fx.charts.event.ChartEvt;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Connection {

    //    private final ConnectionEvent         SELECTED_EVENT = new ConnectionEvent(Connection.this, EventType.SELECTED);
    private Map<EvtType, List<EvtObserver<ChartEvt>>> observers;
    private PlotItem _incomingItem;
    private ObjectProperty<PlotItem> incomingItem;
    private PlotItem _outgoingItem;
    private ObjectProperty<PlotItem> outgoingItem;
    private Color _fill;
    private ObjectProperty<Color> fill;
    private double _value;
    private DoubleProperty value;
    private String _tooltipText;
    private StringProperty tooltipText;


    public Connection(final PlotItem INCOMING_ITEM, final PlotItem OUTGOING_ITEM, final double VALUE, final Color FILL) {
        this(INCOMING_ITEM, OUTGOING_ITEM, VALUE, FILL, "");
    }

    public Connection(final PlotItem INCOMING_ITEM, final PlotItem OUTGOING_ITEM, final double VALUE, final Color FILL, final String TOOLTIP_TEXT) {
        observers = new ConcurrentHashMap<>();
        _incomingItem = INCOMING_ITEM;
        _outgoingItem = OUTGOING_ITEM;
        _value = VALUE;
        _fill = FILL;
        _tooltipText = TOOLTIP_TEXT;
    }


    public PlotItem getIncomingItem() {return null == incomingItem ? _incomingItem : incomingItem.get();}

    public void setIncomingItem(final PlotItem ITEM1) {
        if (null == incomingItem) {
            _incomingItem = ITEM1;
        } else {
            incomingItem.set(ITEM1);
        }
    }

    public ObjectProperty<PlotItem> incomingItemProperty() {
        if (null == incomingItem) {
            incomingItem = new ObjectPropertyBase<PlotItem>(_incomingItem) {
                @Override
                public Object getBean() {return Connection.this;}

                @Override
                public String getName() {return "item1";}
            };
            _incomingItem = null;
        }
        return incomingItem;
    }

    public PlotItem getOutgoingItem() {return null == outgoingItem ? _outgoingItem : outgoingItem.get();}

    public void setOutgoingItem(final PlotItem ITEM2) {
        if (null == outgoingItem) {
            _outgoingItem = ITEM2;
        } else {
            outgoingItem.set(ITEM2);
        }

    }

    public ObjectProperty<PlotItem> outgoingItemProperty() {
        if (null == outgoingItem) {
            outgoingItem = new ObjectPropertyBase<PlotItem>(_outgoingItem) {
                @Override
                public Object getBean() {return Connection.this;}

                @Override
                public String getName() {return "item2";}
            };
            _outgoingItem = null;
        }
        return outgoingItem;
    }

    public double getValue() {return null == value ? _value : value.get();}

    public ReadOnlyDoubleProperty valueProperty() {
        if (null == value) {
            value = new DoublePropertyBase(_value) {
                @Override
                public Object getBean() {return Connection.this;}

                @Override
                public String getName() {return "value";}
            };
        }
        return value;
    }

    public Color getFill() {return null == fill ? _fill : fill.get();}

    public void setFill(final Color FILL) {
        if (null == fill) {
            _fill = FILL;
        } else {
            fill.set(FILL);
        }
    }

    public ObjectProperty<Color> fillProperty() {
        if (null == fill) {
            fill = new ObjectPropertyBase<Color>(_fill) {
                @Override
                public Object getBean() {return Connection.this;}

                @Override
                public String getName() {return "fill";}
            };
            _fill = null;
        }
        return fill;
    }

    public String getTooltipText() {return null == tooltipText ? _tooltipText : tooltipText.get();}

    public void setTooltipText(final String TOOLTIP_TEXT) {
        if (null == tooltipText) {
            _tooltipText = TOOLTIP_TEXT;
        } else {
            tooltipText.set(TOOLTIP_TEXT);
        }
    }

    public StringProperty tooltipTextProperty() {
        if (null == tooltipText) {
            tooltipText = new StringPropertyBase(_tooltipText) {
                @Override
                public Object getBean() {return Connection.this;}

                @Override
                public String getName() {return "tooltipText";}
            };
        }
        return tooltipText;
    }


    // ******************** Event Handling ************************************
    public void addChartEvtObserver(final EvtType type, final EvtObserver<ChartEvt> observer) {
        if (!observers.containsKey(type)) {
            observers.put(type, new CopyOnWriteArrayList<>());
        }
        if (observers.get(type).contains(observer)) {
            return;
        }
        observers.get(type).add(observer);
    }

    public void removeChartEvtObserver(final EvtType type, final EvtObserver<ChartEvt> observer) {
        if (observers.containsKey(type)) {
            if (observers.get(type).contains(observer)) {
                observers.get(type).remove(observer);
            }
        }
    }

    public void removeAllChartEvtObservers() {observers.clear();}

    public void fireChartEvt(final ChartEvt evt) {
        final EvtType type = evt.getEvtType();
        observers.entrySet().stream().filter(entry -> entry.getKey().equals(ChartEvt.ANY)).forEach(entry -> entry.getValue().forEach(observer -> observer.handle(evt)));
        if (observers.containsKey(type) && !type.equals(ChartEvt.ANY)) {
            observers.get(type).forEach(observer -> observer.handle(evt));
        }
    }
}
