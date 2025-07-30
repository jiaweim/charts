package fx.chart.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 09 Jul 2025, 3:17 PM
 */
public final class ImageUtils {

    public static WritableImage getRedChannel(final Image sourceImage) {
        return getColorChannel(sourceImage, Color.RED);
    }

    public static WritableImage getGreenChannel(final Image sourceImage) {
        return getColorChannel(sourceImage, Color.LIME);
    }

    public static WritableImage getBlueChannel(final Image sourceImage) {
        return getColorChannel(sourceImage, Color.BLUE);
    }

    private static WritableImage getColorChannel(final Image sourceImage, final Color color) {
        final Node imageView = new ImageView(sourceImage);
        final Blend blend = createColorBlend(sourceImage, color);
        imageView.setEffect(blend);

        final SnapshotParameters params = new SnapshotParameters();
        return imageView.snapshot(params, null);
    }

    public static Blend createColorBlend(final Image sourceImage, final Color color) {
        final ColorInput mask = createColorMask(sourceImage, color);
        final Blend blend = new Blend(BlendMode.MULTIPLY);
        blend.setTopInput(mask);
        return blend;
    }

    public static ColorInput createColorMask(final Image sourceImage, final Color color) {
        return new ColorInput(0, 0, sourceImage.getWidth(), sourceImage.getHeight(), color);
    }

    /**
     * Save a {@link Node} to a png file
     *
     * @param node     {@link Node}
     * @param fileName file name
     */
    public static void saveAsPNG(final Node node, final String fileName) {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        final WritableImage snapshot = node.snapshot(parameters, null);
        final String name = fileName.replace("\\.[a-zA-Z]{3,4}", "");
        final File file = new File(name + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException exception) {
            // handle exception here
        }
    }
}
