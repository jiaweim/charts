package fx.chart.util;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 30 Jul 2025, 9:15 AM
 */
public class ShapeUtils {

    public static double squareDistance(final double x1, final double y1,
            final double x2, final double y2) {

        double deltaX = (x1 - x2);
        double deltaY = (y1 - y2);
        return (deltaX * deltaX) + (deltaY * deltaY);
    }

    public static double euclideanDistance(final double x1, final double y1,
            final double x2, final double y2) {

        double deltaX = (x2 - x1);
        double deltaY = (y2 - y1);
        return (deltaX * deltaX) + (deltaY * deltaY);
    }

    public static double distance(final double p1X, final double p1Y,
            final double p2X, final double p2Y) {
        return Math.sqrt((p2X - p1X) * (p2X - p1X) + (p2Y - p1Y) * (p2Y - p1Y));
    }

    public static Dimension getTextDimension(final String text, final Font font) {
        Text t = new Text(text);
        t.setFont(font);
        double textWidth = t.getBoundsInLocal().getWidth();
        double textHeight = t.getBoundsInLocal().getHeight();
        t = null;
        return new Dimension(textWidth, textHeight);
    }

}
