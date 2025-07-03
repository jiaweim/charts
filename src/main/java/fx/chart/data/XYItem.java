package fx.chart.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

/**
 * A data point in Cartesian coordinate system
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
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

    void setX(double x);

    DoubleProperty xProperty();

    double getY();

    void setY(double y);

    DoubleProperty yProperty();

    String getTooltipText();

    void setTooltipText(String text);

    StringProperty tooltipTextProperty();
}
