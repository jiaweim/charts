package fx.chart.event;

import fx.chart.event.type.ChangeEvent;
import javafx.scene.input.MouseEvent;


public class ChartEvent extends ChangeEvent {

    public static final EventType<ChartEvent> ANY = new EventType<>(ChangeEvent.ANY, "ANY");
    public static final EventType<ChartEvent> UPDATE = new EventType<>(ChartEvent.ANY, "UPDATE");
    public static final EventType<ChartEvent> FINISHED = new EventType<>(ChartEvent.ANY, "FINISHED");
    public static final EventType<ChartEvent> SELECTED = new EventType<>(ChartEvent.ANY, "SELECTED");
    public static final EventType<ChartEvent> CONNECTION_SELECTED_FROM = new EventType<>(ChartEvent.ANY, "CONNECTION_SELECTED_FROM");
    public static final EventType<ChartEvent> CONNECTION_SELECTED_TO = new EventType<>(ChartEvent.ANY, "CONNECTION_SELECTED_TO");
    public static final EventType<ChartEvent> CONNECTION_SELECTED = new EventType<>(ChartEvent.ANY, "CONNECTION_SELECTED");
    public static final EventType<ChartEvent> CONNECTION_UPDATE = new EventType<>(ChartEvent.ANY, "CONNECTION_UPDATE");
    public static final EventType<ChartEvent> ITEM_UPDATE = new EventType<>(ChartEvent.ANY, "ITEM_UPDATE");
    public static final EventType<ChartEvent> ITEM_SELECTED = new EventType<>(ChartEvent.ANY, "ITEM_SELECTED");
    public static final EventType<ChartEvent> SERIES_SELECTED = new EventType<>(ChartEvent.ANY, "SERIES_SELECTED");
    public static final EventType<ChartEvent> ITEM_AND_SERIES_SELECTED = new EventType<>(ChartEvent.ANY, "ITEM_AND_SERIES_SELECTED");
    public static final EventType<ChartEvent> AXIS_RANGE_CHANGED = new EventType<>(ChartEvent.ANY, "AXIS_RANGE_CHANGED");

    private final Object target;
    private final MouseEvent mouseEvent;

    public ChartEvent(final Object src, final EventType<? extends ChartEvent> evtType) {
        super(src, evtType);
        this.target = null;
        this.mouseEvent = null;
    }

    public ChartEvent(final Object src, final EventType<? extends ChartEvent> evtType, final EventPriority priority) {
        super(src, evtType, priority);
        this.target = null;
        this.mouseEvent = null;
    }

    public ChartEvent(final Object src, final EventType<? extends ChartEvent> evtType, final MouseEvent mouseEvent) {
        super(src, evtType);
        this.target = null;
        this.mouseEvent = mouseEvent;
    }

    public ChartEvent(final Object src, final EventType<? extends ChartEvent> evtType, final EventPriority priority, final MouseEvent mouseEvent) {
        super(src, evtType, priority);
        this.target = null;
        this.mouseEvent = mouseEvent;
    }

    public ChartEvent(final Object src, final Object target, final EventType<? extends ChartEvent> evtType) {
        super(src, evtType);
        this.target = target;
        this.mouseEvent = null;
    }

    public ChartEvent(final Object src, final Object target, final EventType<? extends ChartEvent> evtType, final MouseEvent mouseEvent) {
        super(src, evtType);
        this.target = target;
        this.mouseEvent = mouseEvent;
    }

    public Object getTarget() {return target;}

    public MouseEvent getMouseEvent() {return mouseEvent;}
}
