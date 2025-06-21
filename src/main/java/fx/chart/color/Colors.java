package fx.chart.color;

import javafx.scene.paint.Color;

public interface Colors {

    /**
     * Returns the corresponding JavaFX color.
     *
     * @return the corresponding JavaFX color.
     */
    Color get();

    /**
     * Returns a String expression from the color with the format: colorToRGB(12, 121, 15)
     *
     * @return the String expression.
     */
    String rgb();

    /**
     * Returns a String expression from the color and opacity with the format: colorToRGBA(12, 121, 15, 0.5)
     *
     * @return the String expression.
     */
    String rgba(final double OPACITY);

    /**
     * Returns a String expression from the color with the format: #AB12CD
     *
     * @return the String expression.
     */
    String web();
}