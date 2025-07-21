package fx.chart.forcedirectedgraph;

import fx.chart.data.ChartItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Node in a Force Directed Graph, containing double and string values
 *
 * @author Jiawei Mao
 * @author Michael L\u00E4uchli
 * @author MLaeuchli
 * @author Stefan Mettler
 * @author orizion
 * @version 1.0.0
 * @since 21 Jul 2025, 8:59 AM
 */
public class GraphNode extends ChartItem {

    private final ObjectProperty<Point2D> disp;
    private final ObjectProperty<Point2D> positionProperty;

    private HashMap<String, Double> numericAttributes;
    private HashMap<String, String> stringAttributes;

    private ArrayList<GraphNode> connectedNodes;
    /**
     * defines which attribute will be assigned to the value field
     */
    private String SIZE_KEY;
    private boolean selected_;
    private BooleanProperty selectedProperty;

    /*
     * Default Key (addDefault), to assure that a VALUE is set.
     */
    public GraphNode(final String NAME, final Color COLOR, final Map<String, Double> NUMERIC_ATTRIBUTES, final Map<String, String> STRING_ATTRIBUTES) {
        this(Point2D.ZERO, NAME, COLOR, NUMERIC_ATTRIBUTES, STRING_ATTRIBUTES);
    }

    public GraphNode(final String NAME, final Map<String, Double> NUMERIC_ATTRIBUTES, final Map<String, String> STRING_ATTRIBUTES) {
        this(NAME, Color.BLACK, NUMERIC_ATTRIBUTES, STRING_ATTRIBUTES);
    }

    public GraphNode(final Point2D POSITION, final String NAME, final Color COLOR, final Map<String, Double> NUMERIC_ATTRIBUTES, final Map<String, String> STRING_ATTRIBUTES) {
        super(NAME, 1, COLOR);
        positionProperty = new SimpleObjectProperty<>(POSITION);
        disp = new SimpleObjectProperty<>(new Point2D(0, 0));
        numericAttributes = new HashMap<>(NUMERIC_ATTRIBUTES);
        stringAttributes = new HashMap<>(STRING_ATTRIBUTES);
        connectedNodes = new ArrayList<>();
        selected_ = false;
        setFill(COLOR);
        setStroke(Color.WHITE);
        addDefault();
    }

    /**
     * Create a {@link GraphNode}
     *
     * @param numericAttributes attributes
     * @param stringAttributes  attributes
     */
    public GraphNode(Map<String, Double> numericAttributes, Map<String, String> stringAttributes) {
        this(Point2D.ZERO, "", Color.BLUE, numericAttributes, stringAttributes);
    }

    public GraphNode(final String NAME) {
        this(NAME, new HashMap<>(), new HashMap<>());
    }


    public boolean containedIn(double x, double y, double nodeScaleFactor, double generalScaleFactor, double minRadius) {
        double x0 = x - positionProperty.get().getX();
        double y0 = y - positionProperty.get().getY();
        return (Math.sqrt(Math.pow(x0, 2) + Math.pow(y0, 2)) < (getRadius() * nodeScaleFactor + minRadius) * generalScaleFactor);
    }

    /**
     * Calculates radius from value
     *
     * @return
     */
    public double getRadius() {
        return getValue();
    }

    private void addDefault() {
        numericAttributes.putIfAbsent(NodeEdgeModel.DEFAULT, Math.sqrt(1 / Math.PI));
        stringAttributes.putIfAbsent(NodeEdgeModel.DEFAULT, "Group1");
    }

    public String getSizeKey() {
        return SIZE_KEY;
    }

    /**
     * Sets the key for the value which contributes towards the size of the node
     * and sets the value of the attribute as the value of the node
     *
     * @param key
     */
    public void setSizeKey(String key) {
        this.SIZE_KEY = key;
        setValue(Math.sqrt(getNumericAttribute(key) / Math.PI));
    }

    public void addConnection(GraphNode node) {
        connectedNodes.add(node);
    }

    public double getNumericAttribute(String key) {
        Double value = numericAttributes.get(key);
        return value == null ? 0 : value;
    }

    public void setNumericAttribute(String key, double value) {
        numericAttributes.put(key, value);
    }

    /**
     * Returns the given string attribute, or the empty string if the attribute wasnt found
     *
     * @param key
     * @return
     */
    public String getStringAttribute(String key) {
        String val = stringAttributes.get(key);
        return val == null ? "" : val;
    }

    public boolean containsNumericAttribute(String key) {
        return numericAttributes.containsKey(key);
    }


    public void setDisp(Point2D disp) {
        this.disp.setValue(disp);
    }

    public Point2D getDisp() {return this.disp.get();}

    public ObjectProperty<Point2D> dispProperty() {
        return disp;
    }

    public Point2D getPosition() {
        return positionProperty.get();
    }

    public void setPosition(Point2D position) {
        this.positionProperty.set(position);
    }

    public ObjectProperty<Point2D> positionProperty() {
        return positionProperty;
    }

    public boolean isSelected() {return null == selectedProperty ? selected_ : selectedProperty.get();}

    public void setSelected(final boolean SELECTED) {
        if (null == selectedProperty) {
            selected_ = SELECTED;
        } else {
            selectedProperty.set(SELECTED);
        }
    }

    public BooleanProperty selectedProperty() {
        if (null == selectedProperty) {
            selectedProperty = new BooleanPropertyBase(selected_) {
                @Override
                protected void invalidated() {}

                @Override
                public Object getBean() {return GraphNode.this;}

                @Override
                public String getName() {return "selected";}
            };
        }
        return selectedProperty;
    }

    public ArrayList<GraphNode> getConnectedNodes() {
        return connectedNodes;
    }

    public ArrayList<String> getNumericAttributeKeys() {
        return new ArrayList<>(numericAttributes.keySet());
    }

    public ArrayList<String> getStringAttributeKeys() {
        return new ArrayList<>(stringAttributes.keySet());
    }
}
