package fx.chart.util;

import javafx.application.Platform;

/**
 *
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 11:34 AM
 */
public final class FXUtils {

    private FXUtils() {}

    /**
     * Initialize the javafx toolkit.
     *
     * @param runnable {@link Runnable}
     */
    public static void startFX(Runnable runnable) {
        Platform.startup(runnable);
    }

}
