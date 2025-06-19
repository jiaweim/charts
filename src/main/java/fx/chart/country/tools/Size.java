package fx.chart.country.tools;

public enum Size {

    SMALL, MEDIUM, LARGE, UNKNOWN;

    public static Size fromText(final String text) {
        switch (text) {
            case "s" -> {
                return SMALL;
            }
            case "m" -> {
                return MEDIUM;
            }
            case "l" -> {
                return LARGE;
            }
            default -> {
                return UNKNOWN;
            }
        }
    }
}
