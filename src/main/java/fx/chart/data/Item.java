package fx.chart.data;

import fx.chart.Symbol;
import javafx.scene.paint.Color;

public interface Item {

    String getName();

    Color getFill();

    Color getStroke();

    Symbol getSymbol();

    void setSymbol(Symbol symbol);

    boolean isEmptyItem();
}
