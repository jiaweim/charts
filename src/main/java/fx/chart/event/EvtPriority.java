package fx.chart.event;

import static fx.chart.util.Constants.*;

/**
 * Define the priority of event
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:49 AM
 */
public enum EvtPriority {

    LOW(0), NORMAL(1), HIGH(2);

    private final int value;

    EvtPriority(final int value) {
        this.value = value;
    }

    public int getValue() {return value;}

    @Override
    public String toString() {
        return CURLY_BRACKET_OPEN +
                QUOTES + "class" + QUOTES + COLON + QUOTES + getClass().getName() + QUOTES + COMMA +
                QUOTES + "value" + QUOTES + COLON + QUOTES + getValue() +
                CURLY_BRACKET_CLOSE;
    }
}
