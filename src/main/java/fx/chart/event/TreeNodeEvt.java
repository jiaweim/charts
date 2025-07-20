package fx.chart.event;

import fx.chart.data.Item;
import fx.chart.data.TreeNode;
import fx.chart.event.type.ChangeEvt;


public class TreeNodeEvt<T extends Item> extends ChangeEvt {

    public static final EvtType<TreeNodeEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<TreeNodeEvt> PARENT_SET = new EvtType<>(TreeNodeEvt.ANY, "PARENT_SET");
    public static final EvtType<TreeNodeEvt> PARENT_REMOVED = new EvtType<>(TreeNodeEvt.ANY, "PARENT_REMOVED");
    public static final EvtType<TreeNodeEvt> CHILDREN_CHANGED = new EvtType<>(TreeNodeEvt.ANY, "CHILDREN_CHANGED");
    public static final EvtType<TreeNodeEvt> NODE_SELECTED = new EvtType<>(TreeNodeEvt.ANY, "NODE_SELECTED");

    private final T item;
    private final EvtType<? extends TreeNodeEvt> type;

    public TreeNodeEvt(final TreeNode<T> src, final T item) {
        this(src, TreeNodeEvt.NODE_SELECTED, item);
    }

    public TreeNodeEvt(final TreeNode<T> src, final EvtType<? extends TreeNodeEvt> type, final T item) {
        super(src, type);
        this.item = item;
        this.type = type;
    }

    public T getItem() {return item;}

    @Override
    public EvtType<? extends TreeNodeEvt> getEvtType() {
        return type;
    }

    @Override
    public String toString() {
        return "{\"item\":\"" + item.getName() + "\"}";
    }
}