package fx.chart.data;

import fx.chart.Symbol;
import javafx.scene.paint.Color;

/**
 * A data point to show
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 2025-06-21, 15:34
 */
public interface Item {

    /**
     * @return item name
     */
    String getName();

    /**
     * @return item color
     */
    Color getFill();

    /**
     * @return stroke color
     */
    Color getStroke();

    /**
     * @return symbol
     */
    Symbol getSymbol();

    void setSymbol(Symbol symbol);

    boolean isEmptyItem();
}
