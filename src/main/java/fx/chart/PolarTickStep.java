package fx.chart;

/**
 *
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 05 Sep 2025, 10:27 AM
 */
public enum PolarTickStep {

    FIVE(5, 72),
    TEN(10, 36),
    FIFTEEN(15, 24),
    TWENTY(20, 18),
    THIRTY(30, 12),
    FOURTY_FIVE(45, 8),
    SIXTY(60, 6),
    SEVENTY_TWO(72, 5),
    NINETY(90, 4),
    HUNDRED_TWENTY(120, 3);

    private final double value;
    private final double noOfSectors;
    private final double angleStep;

    PolarTickStep(final double value, final double noOfSectors) {
        this.value = value;
        this.noOfSectors = noOfSectors;
        this.angleStep = 360.0 / noOfSectors;
    }

    public double get() {return value;}

    public double getNoOfSectors() {return noOfSectors;}

    public double getAngleStep() {return angleStep;}
}
