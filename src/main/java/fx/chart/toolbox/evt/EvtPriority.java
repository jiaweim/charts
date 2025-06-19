package fx.chart.toolbox.evt;

import static fx.chart.toolbox.Constants.*;


public enum EvtPriority {
    LOW(0), NORMAL(1), HIGH(2);

    private int value;

    EvtPriority(final int value) {
        this.value = value;
    }

    public int getValue() {return value;}

    @Override
    public String toString() {
        return new StringBuilder().append(CURLY_BRACKET_OPEN)
                .append(QUOTES).append("class").append(QUOTES).append(COLON).append(QUOTES).append(getClass().getName()).append(QUOTES).append(COMMA)
                .append(QUOTES).append("value").append(QUOTES).append(COLON).append(QUOTES).append(getValue())
                .append(CURLY_BRACKET_CLOSE)
                .toString();
    }
}
