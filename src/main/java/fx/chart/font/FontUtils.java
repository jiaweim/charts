package fx.chart.font;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 07 Jul 2025, 11:26 AM
 */
public class FontUtils {

    /**
     * Return the font with different size
     *
     * @param font {@link Font}
     * @param size new size
     * @return {@link Font} with given size
     */
    public static Font derive(Font font, int size) {
        String family = font.getFamily();
        String style = font.getStyle();

        FontWeight weight;
        FontPosture posture;
        int index = style.indexOf("Italic");
        if (index == -1) {
            weight = FontWeight.findByName(style);
            posture = FontPosture.REGULAR;
        } else if (index == 0) {
            weight = FontWeight.NORMAL;
            posture = FontPosture.ITALIC;
        } else {
            posture = FontPosture.ITALIC;
            weight = FontWeight.findByName(style.substring(0, index - 1));
        }

        return Font.font(family, weight, posture, size);
    }

}
