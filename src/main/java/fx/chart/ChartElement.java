package fx.chart;

import fx.chart.event.ChartEventListener;
import fx.chart.event.EventSource;
import fx.chart.event.EventType;
import fx.chart.event.type.ChangeEvent;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class for all Chart.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 1:07 PM
 */
@DefaultProperty("children")
public class ChartElement extends Region implements EventSource {

    private static final double MINIMUM_WIDTH = 50;
    private static final double MINIMUM_HEIGHT = 50;
    private static final double MAXIMUM_WIDTH = 4096;
    private static final double MAXIMUM_HEIGHT = 4096;

    /**
     * To support add listeners.
     */
    private final ConcurrentHashMap<EventType, Set<ChartEventListener<? extends ChangeEvent>>> listenerMap_ = new ConcurrentHashMap<>();

    @Override
    public Map<EventType, Set<ChartEventListener<? extends ChangeEvent>>> getListenerMap() {
        return listenerMap_;
    }

    /**
     * Re-render the entire chart.
     */
    protected void redraw() {

    }

    @Override
    protected double computeMinWidth(final double height) {return MINIMUM_WIDTH;}

    @Override
    protected double computeMinHeight(final double width) {return MINIMUM_HEIGHT;}

    @Override
    protected double computeMaxWidth(final double height) {return MAXIMUM_WIDTH;}

    @Override
    protected double computeMaxHeight(final double width) {return MAXIMUM_HEIGHT;}

    @Override
    public void layoutChildren() {
        super.layoutChildren();
    }

    @Override
    protected double computePrefWidth(final double height) {return super.computePrefWidth(height);}

    @Override
    protected double computePrefHeight(final double width) {return super.computePrefHeight(width);}

    @Override
    public ObservableList<Node> getChildren() {return super.getChildren();}
}
