package fx.chart.event;

/**
 * Define the priority of event
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:49 AM
 */
public enum EventPriority {

    LOW(0), NORMAL(1), HIGH(2);

    private final int value;

    EventPriority(final int value) {
        this.value = value;
    }

    public int getValue() {return value;}

    @Override
    public String toString() {
        return "{\"class\":\"" + getClass().getName() + "\",\"value\":\"" + getValue() + "\"}";
    }
}
