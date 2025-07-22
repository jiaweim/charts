package fx.chart.event;

import fx.chart.data.Item;
import fx.chart.data.TreeNode;
import fx.chart.event.type.ChangeEvent;


public class TreeNodeEvent<T extends Item> extends ChangeEvent {

    public static final EventType<TreeNodeEvent> ANY = new EventType<>(ChangeEvent.ANY, "ANY");
    public static final EventType<TreeNodeEvent> PARENT_SET = new EventType<>(TreeNodeEvent.ANY, "PARENT_SET");
    public static final EventType<TreeNodeEvent> PARENT_REMOVED = new EventType<>(TreeNodeEvent.ANY, "PARENT_REMOVED");
    public static final EventType<TreeNodeEvent> CHILDREN_CHANGED = new EventType<>(TreeNodeEvent.ANY, "CHILDREN_CHANGED");
    public static final EventType<TreeNodeEvent> NODE_SELECTED = new EventType<>(TreeNodeEvent.ANY, "NODE_SELECTED");

    private final T item;
    private final EventType<? extends TreeNodeEvent> type;

    public TreeNodeEvent(final TreeNode<T> src, final T item) {
        this(src, TreeNodeEvent.NODE_SELECTED, item);
    }

    public TreeNodeEvent(final TreeNode<T> src, final EventType<? extends TreeNodeEvent> type, final T item) {
        super(src, type);
        this.item = item;
        this.type = type;
    }

    public T getItem() {return item;}

    @Override
    public EventType<? extends TreeNodeEvent> getEventType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"item\":\"" + item.getName() + "\"}";
    }
}