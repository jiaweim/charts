package fx.chart.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by hansolo on 17.07.17.
 */
public interface XYItem extends Item {

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
