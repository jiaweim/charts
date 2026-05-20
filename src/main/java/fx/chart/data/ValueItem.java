package fx.chart.data;

import javafx.beans.property.DoubleProperty;


public interface ValueItem extends Item {

    double getValue();

    void setValue(double value);

    DoubleProperty valueProperty();
}
