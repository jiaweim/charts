package fx.chart.color;

import javafx.scene.paint.Color;

/**
 * Color utilities
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 3:48 PM
 */
public final class ColorUtils {

    /**
     * Get the color with given opacity
     *
     * @param color   the original color
     * @param opacity the new opacity
     * @return new {@link Color}
     */
    public static Color getColorWithOpacity(final Color color, final double opacity) {
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();
        double op = Math.clamp(opacity, 0, 1);
        return Color.color(red, green, blue, op);
    }

}
