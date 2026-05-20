package fx.chart.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

/**
 * A data point in Cartesian coordinate system
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 2:13 PM
 */
public interface XYItem extends Item {

    /**
     * Return the x-value
     *
     * @return x-value
     */
    double getX();

    /**
     * Set the x-value
     *
     * @param x new value
     */
    void setX(double x);

    /**
     * Return the x value in the Property type.
     *
     * @return the x value.
     */
    DoubleProperty xProperty();

    /**
     * Return the y-value.
     *
     * @return y-value.
     */
    double getY();

    /**
     * Set the y-value.
     *
     * @param y new y.
     */
    void setY(double y);

    /**
     * Return the y value in the Property type.
     *
     * @return the y value.
     */
    DoubleProperty yProperty();

    /**
     * Return the tooltip text fo this data item.
     *
     * @return tooltip text.
     */
    String getTooltipText();

    /**
     * Set the tooltip text fo this data item.
     *
     * @param text tooltip text
     */
    void setTooltipText(String text);

    /**
     * Return the tooltip text in the property type.
     *
     * @return tooltip text.
     */
    StringProperty tooltipTextProperty();
}
