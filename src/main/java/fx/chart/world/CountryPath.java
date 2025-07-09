package fx.chart.world;

import javafx.scene.control.Tooltip;
import javafx.scene.shape.SVGPath;

import java.util.Locale;


/**
 * Created by hansolo on 20.09.16.
 */
public class CountryPath extends SVGPath {
    private String name;
    private Locale locale;
    private Tooltip tooltip;


    public CountryPath() {
        this("", null);
    }

    public CountryPath(final String NAME) {
        this(NAME, null);
    }

    public CountryPath(final String NAME, final String CONTENT) {
        super();
        name = NAME;
        locale = new Locale("", NAME);
        tooltip = new Tooltip(locale.getDisplayCountry());
        Tooltip.install(CountryPath.this, tooltip);
        if (null == CONTENT) return;
        setContent(CONTENT);
    }


    public String getName() {return name;}

    public void setName(final String NAME) {this.name = NAME;}

    public Locale getLocale() {return locale;}

    public void setLocale(final Locale LOCALE) {locale = LOCALE;}

    public Tooltip getTooltip() {return tooltip;}

    public void setTooltip(final Tooltip TOOLTIP) {
        tooltip = TOOLTIP;
        Tooltip.install(CountryPath.this, tooltip);
    }

    @Override
    public String toString() {
        return "{\n" + "  name   :\"" + name + "\"\n" +
                "  locale :\"" + locale + "\"\n" +
                "  tooltip:\"" + tooltip.getText() + "\"\n" +
                "  content:\"" + getContent() + "\"\n" +
                "}\n";
    }
}