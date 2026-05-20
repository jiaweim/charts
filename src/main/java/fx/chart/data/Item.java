package fx.chart.data;

import fx.chart.Symbol;
import javafx.scene.paint.Color;

/**
 * A data point to show
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 2025-06-21, 15:34
 */
public interface Item {

    /**
     * @return item name
     */
    String getName();

    /**
     * @return item fill color
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

    /**
     * set the {@link Symbol} for this item
     *
     * @param symbol {@link Symbol}
     */
    void setSymbol(Symbol symbol);

    /**
     * Whether this item is empty, empty items are not rendered, and therefore not displayed
     *
     * @return true if it is empty
     */
    boolean isEmptyItem();
}
