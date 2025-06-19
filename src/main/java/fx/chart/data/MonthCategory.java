package fx.chart.data;

import fx.chart.Category;
import javafx.scene.paint.Color;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public class MonthCategory extends Category {

    private Month month;

    public MonthCategory(final Month month, final Color color) {
        super(month.getDisplayName(TextStyle.FULL, Locale.getDefault()), color);
        this.month = month;
    }

    @Override
    public String getName() {return getName(TextStyle.FULL, Locale.getDefault());}

    public String getName(final TextStyle textStyle) {return getName(textStyle, Locale.getDefault());}

    public String getName(final TextStyle textStyle, final Locale locale) {return month.getDisplayName(textStyle, locale);}

    public Month getMonth() {return month;}
}
