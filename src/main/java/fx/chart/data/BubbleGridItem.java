package fx.chart.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;

/**
 *
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 20 May 2026, 12:51 PM
 */
public interface BubbleGridItem extends Item {

    ChartItem getCategoryX();

    void setCategoryX(final ChartItem categoryX);

    ObjectProperty<ChartItem> categoryXProperty();

    ChartItem getCategoryY();

    void setCategoryY(final ChartItem categoryY);

    ObjectProperty<ChartItem> categoryYProperty();

    double getValue();

    void setValue(final double value);

    DoubleProperty valueProperty();
}
