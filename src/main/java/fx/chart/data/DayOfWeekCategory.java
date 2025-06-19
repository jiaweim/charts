package fx.chart.data;

import fx.chart.Category;
import javafx.scene.paint.Color;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;


public class DayOfWeekCategory extends Category {

    private DayOfWeek dayOfWeek;

    public DayOfWeekCategory(final DayOfWeek dayOfWeek, final Color color) {
        super(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()), color);
        this.dayOfWeek = dayOfWeek;
    }


    @Override
    public String getName() {return getName(TextStyle.FULL, Locale.getDefault());}

    public String getName(final TextStyle textStyle) {return getName(textStyle, Locale.getDefault());}

    public String getName(final TextStyle textStyle, final Locale locale) {return dayOfWeek.getDisplayName(textStyle, locale);}

    public DayOfWeek getDayOfWeek() {return dayOfWeek;}
}
