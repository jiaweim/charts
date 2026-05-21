package fx.chart;

import fx.chart.color.ColorUtils;
import fx.chart.event.ChartEvent;
import fx.chart.property.BooleanLProperty;
import fx.chart.property.DoubleLProperty;
import fx.chart.property.ObjectLProperty;
import fx.chart.tools.Helper;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Create grid-line in the XYPane
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 3:24 PM
 */
@DefaultProperty("children")
public class Grid extends ChartElement {

    private static final double PREFERRED_WIDTH = 250;
    private static final double PREFERRED_HEIGHT = 250;

    private static final double MIN_MAJOR_LINE_WIDTH = 1;
    private static final double MIN_MEDIUM_LINE_WIDTH = 0.75;
    private static final double MIN_MINOR_LINE_WIDTH = 0.5;

    private double width_;
    private double height_;

    private final Axis xAxis;
    private final Axis yAxis;

    private final DoubleLProperty gridOpacity_;

    private final ObjectLProperty<Paint> majorHGridLinePaint_;
    private final ObjectLProperty<Paint> mediumHGridLinePaint_;
    private final ObjectLProperty<Paint> minorHGridLinePaint_;

    private final BooleanLProperty majorHGridLinesVisible_;
    private final BooleanLProperty mediumHGridLinesVisible_;
    private final BooleanLProperty minorHGridLinesVisible_;

    private final ObjectLProperty<Paint> majorVGridLinePaint_;
    private final ObjectLProperty<Paint> mediumVGridLinePaint_;
    private final ObjectLProperty<Paint> minorVGridLinePaint_;

    private final BooleanLProperty majorVGridLinesVisible_;
    private final BooleanLProperty mediumVGridLinesVisible_;
    private final BooleanLProperty minorVGridLinesVisible_;

    private double[] dashes_;
    private Canvas canvas;
    private GraphicsContext gc_;
    private Pane pane;

    public Grid(final Axis xAxis, final Axis yAxis) {
        if (null == xAxis || null == yAxis) {
            throw new IllegalArgumentException("Axis cannot be null");
        }
        this.xAxis = xAxis;
        this.yAxis = yAxis;

        gridOpacity_ = new DoubleLProperty(this, "gridOpacity", 0.25, this::drawGrid, opacity -> Math.clamp(opacity, 0, 1));
        majorHGridLinePaint_ = new ObjectLProperty<>(this, "majorHGridLinePaint", null, this::drawGrid);
        mediumHGridLinePaint_ = new ObjectLProperty<>(this, "mediumHGridLinePaint", null, this::drawGrid);
        minorHGridLinePaint_ = new ObjectLProperty<>(this, "minorHGridLinePaint", null, this::drawGrid);

        majorHGridLinesVisible_ = new BooleanLProperty(this, "majorHGridLinesVisible", true, this::drawGrid);
        mediumHGridLinesVisible_ = new BooleanLProperty(this, "mediumHGridLinesVisible", true, this::drawGrid);
        minorHGridLinesVisible_ = new BooleanLProperty(this, "minorHGridLinesVisible", true, this::drawGrid);

        majorVGridLinePaint_ = new ObjectLProperty<>(this, "majorVGridLinePaint", null, this::drawGrid);
        mediumVGridLinePaint_ = new ObjectLProperty<>(this, "mediumVGridLinePaint", null, this::drawGrid);
        minorVGridLinePaint_ = new ObjectLProperty<>(this, "minorVGridLinePaint", null, this::drawGrid);

        majorVGridLinesVisible_ = new BooleanLProperty(this, "majorVGridLinesVisible", true, this::drawGrid);
        mediumVGridLinesVisible_ = new BooleanLProperty(this, "mediumVGridLinesVisible", true, this::drawGrid);
        minorVGridLinesVisible_ = new BooleanLProperty(this, "minorVGridLinesVisible", true, this::drawGrid);

        dashes_ = new double[]{1}; // Solid line, which can be changed to dashed line by calling setGridLineDashes
        setMouseTransparent(true); // Ensure grid lines do not intercept mouse events.
        initGraphics();
        registerListeners();
    }

    private void initGraphics() {
        // If the size is not valid, use the default size (250×250).
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0
                || Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        canvas = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        gc_ = canvas.getGraphicsContext2D();

        pane = new Pane(canvas);
        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        xAxis.addEventListener(ChartEvent.AXIS_RANGE_CHANGED, e -> drawGrid());
        yAxis.addEventListener(ChartEvent.AXIS_RANGE_CHANGED, e -> drawGrid());
    }

    public double getGridOpacity() {
        return gridOpacity_.getAsDouble();
    }

    public void setGridOpacity(final double OPACITY) {
        gridOpacity_.set(OPACITY);
    }

    public DoubleProperty gridOpacityProperty() {
        return gridOpacity_.getProperty();
    }

    public Paint getMajorHGridLinePaint() {
        return majorHGridLinePaint_.get();
    }

    public void setMajorHGridLinePaint(final Paint paint) {
        majorHGridLinePaint_.set(paint);
    }

    public ObjectProperty<Paint> majorHGridLinePaintProperty() {
        return majorHGridLinePaint_.getProperty();
    }

    public Paint getMediumHGridLinePaint() {
        return mediumHGridLinePaint_.get();
    }

    public void setMediumHGridLinePaint(final Paint PAINT) {
        mediumHGridLinePaint_.set(PAINT);
    }

    public ObjectProperty<Paint> mediumHGridLinePaintProperty() {
        return mediumHGridLinePaint_.getProperty();
    }

    public Paint getMinorHGridLinePaint() {
        return minorHGridLinePaint_.get();
    }

    public void setMinorHGridLinePaint(final Paint PAINT) {
        minorHGridLinePaint_.set(PAINT);
    }

    public ObjectProperty<Paint> minorHGridLinePaintProperty() {
        return minorHGridLinePaint_.getProperty();
    }

    public void setMajorGridLineVisible(boolean visible) {
        setMajorVGridLinesVisible(visible);
        setMajorHGridLinesVisible(visible);
    }

    public void setMediumGridLineVisible(boolean visible) {
        setMediumVGridLinesVisible(visible);
        setMediumHGridLinesVisible(visible);
    }

    public void setMinorGridLinesVisible(boolean visible) {
        setMinorVGridLinesVisible(visible);
        setMinorHGridLinesVisible(visible);
    }

    public boolean getMajorHGridLinesVisible() {
        return majorHGridLinesVisible_.getAsBoolean();
    }

    public void setMajorHGridLinesVisible(final boolean VISIBLE) {
        majorHGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty majorHGridLinesVisibleProperty() {
        return majorHGridLinesVisible_.getProperty();
    }

    public boolean getMediumHGridLinesVisible() {
        return mediumHGridLinesVisible_.getAsBoolean();
    }

    public void setMediumHGridLinesVisible(final boolean VISIBLE) {
        mediumHGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty mediumHGridLinesVisibleProperty() {
        return mediumHGridLinesVisible_.getProperty();
    }

    public boolean getMinorHGridLinesVisible() {
        return minorHGridLinesVisible_.getAsBoolean();
    }

    public void setMinorHGridLinesVisible(final boolean VISIBLE) {
        minorHGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty minorHGridLinesVisibleProperty() {
        return minorHGridLinesVisible_.getProperty();
    }

    public Paint getMajorVGridLinePaint() {
        return majorVGridLinePaint_.get();
    }

    public void setMajorVGridLinePaint(final Paint PAINT) {
        majorVGridLinePaint_.set(PAINT);
    }

    public ObjectProperty<Paint> majorVGridLinePaintProperty() {
        return majorVGridLinePaint_.getProperty();
    }

    public Paint getMediumVGridLinePaint() {
        return mediumVGridLinePaint_.get();
    }

    public void setMediumVGridLinePaint(final Paint PAINT) {
        mediumVGridLinePaint_.set(PAINT);
    }

    public ObjectProperty<Paint> mediumVGridLinePaintProperty() {
        return mediumVGridLinePaint_.getProperty();
    }

    public Paint getMinorVGridLinePaint() {
        return minorVGridLinePaint_.get();
    }

    public void setMinorVGridLinePaint(final Paint PAINT) {
        minorVGridLinePaint_.set(PAINT);
    }

    public ObjectProperty<Paint> minorVGridLinePaintProperty() {
        return minorVGridLinePaint_.getProperty();
    }

    public boolean getMajorVGridLinesVisible() {
        return majorVGridLinesVisible_.getAsBoolean();
    }

    public void setMajorVGridLinesVisible(final boolean VISIBLE) {
        majorVGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty majorVGridLinesVisibleProperty() {
        return majorVGridLinesVisible_.getProperty();
    }

    public boolean getMediumVGridLinesVisible() {
        return mediumVGridLinesVisible_.getAsBoolean();
    }

    public void setMediumVGridLinesVisible(final boolean VISIBLE) {
        mediumVGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty mediumVGridLinesVisibleProperty() {
        return mediumVGridLinesVisible_.getProperty();
    }

    public boolean getMinorVGridLinesVisible() {
        return minorVGridLinesVisible_.getAsBoolean();
    }

    public void setMinorVGridLinesVisible(final boolean VISIBLE) {
        minorVGridLinesVisible_.set(VISIBLE);
    }

    public BooleanProperty minorVGridLinesVisibleProperty() {
        return minorVGridLinesVisible_.getProperty();
    }

    public void setGridLinePaint(final Paint PAINT) {
        setMajorHGridLinePaint(PAINT);
        setMediumHGridLinePaint(PAINT);
        setMinorHGridLinePaint(PAINT);

        setMajorVGridLinePaint(PAINT);
        setMediumVGridLinePaint(PAINT);
        setMinorVGridLinePaint(PAINT);
    }

    public void adjustGridLineVisibilityToAxis() {
        setMajorVGridLinesVisible(xAxis.getMajorTickMarksVisible());
        setMediumVGridLinesVisible(xAxis.getMediumTickMarksVisible());
        setMinorVGridLinesVisible(xAxis.getMinorTickMarksVisible());

        setMajorHGridLinesVisible(yAxis.getMajorTickMarksVisible());
        setMediumHGridLinesVisible(yAxis.getMediumTickMarksVisible());
        setMinorHGridLinesVisible(yAxis.getMinorTickMarksVisible());
    }

    public void setGridLineDashes(final double... dashes) {
        this.dashes_ = dashes;
        drawGrid();
    }

    private void drawGrid() {
        gc_.clearRect(0, 0, width_, height_);
        gc_.setLineDashes(dashes_);

        // Determine grid line width.
        double majorLineWidth = 25 * 0.007 < MIN_MAJOR_LINE_WIDTH ? MIN_MAJOR_LINE_WIDTH : 25 * 0.007;
        double mediumLineWidth = 25 * 0.006 < MIN_MEDIUM_LINE_WIDTH ? MIN_MEDIUM_LINE_WIDTH : 25 * 0.005;
        double minorLineWidth = 25 * 0.005 < MIN_MINOR_LINE_WIDTH ? MIN_MINOR_LINE_WIDTH : 25 * 0.003;

        // set grid line paint
        Paint minorHGridColor = getMinorHGridLinePaint() == null ? ColorUtils.getColorWithOpacity(yAxis.getMinorTickMarkColor(), getGridOpacity()) : getMinorHGridLinePaint();
        Paint mediumHGridColor = getMediumHGridLinePaint() == null ? ColorUtils.getColorWithOpacity(yAxis.getMediumTickMarkColor(), getGridOpacity()) : getMediumHGridLinePaint();
        Paint majorHGridColor = getMajorHGridLinePaint() == null ? ColorUtils.getColorWithOpacity(yAxis.getMajorTickMarkColor(), getGridOpacity()) : getMajorHGridLinePaint();

        Paint minorVGridColor = getMinorVGridLinePaint() == null ? ColorUtils.getColorWithOpacity(xAxis.getMinorTickMarkColor(), getGridOpacity()) : getMinorVGridLinePaint();
        Paint mediumVGridColor = getMediumVGridLinePaint() == null ? ColorUtils.getColorWithOpacity(xAxis.getMediumTickMarkColor(), getGridOpacity()) : getMediumVGridLinePaint();
        Paint majorVGridColor = getMajorVGridLinePaint() == null ? ColorUtils.getColorWithOpacity(xAxis.getMajorTickMarkColor(), getGridOpacity()) : getMajorVGridLinePaint();

        // Read data such as value range, tick interval and zero line position from the axis to calculate the canvas coordinates of each line.
        AxisType xAxisType = xAxis.getType();
        double minX = xAxis.getMinValue();
        double maxX = xAxis.getMaxValue();
        boolean fullRangeX = (minX < 0 && maxX > 0);
        double minorTickSpaceX = xAxis.getMinorTickSpace();
        double majorTickSpaceX = xAxis.getMajorTickSpace();
        double rangeX = xAxis.getRange();
        double stepSizeX = Math.abs(width_ / rangeX);
        double zeroPositionX = xAxis.getZeroPosition();

        AxisType yAxisType = yAxis.getType();
        double minY = yAxis.getMinValue();
        double maxY = yAxis.getMaxValue();
        boolean fullRangeY = (minY < 0 && maxY > 0);
        double minorTickSpaceY = yAxis.getMinorTickSpace();
        double majorTickSpaceY = yAxis.getMajorTickSpace();
        double rangeY = yAxis.getRange();
        double stepSizeY = Math.abs(height_ / rangeY);
        double zeroPositionY = yAxis.getZeroPosition();

        BigDecimal minorTickSpaceBD = BigDecimal.valueOf(minorTickSpaceX);
        BigDecimal majorTickSpaceBD = BigDecimal.valueOf(majorTickSpaceX);
        BigDecimal mediumCheck2 = BigDecimal.valueOf(2 * minorTickSpaceX);
        BigDecimal mediumCheck5 = BigDecimal.valueOf(5 * minorTickSpaceX);

        // Main Loop for grid lines
        if (AxisType.LINEAR == xAxisType || AxisType.TEXT == xAxisType) {
            // ******************** Linear ************************************
            boolean isZero;
            double tmpStepSize = minorTickSpaceX;
            BigDecimal counterBD = BigDecimal.valueOf(minX);
            double counter = minX;
            BigDecimal tmpStepBD = new BigDecimal(tmpStepSize);
            tmpStepBD = tmpStepBD.setScale(3, RoundingMode.HALF_UP);
            double tmpStep = tmpStepBD.doubleValue();
            for (double i = 0; Double.compare(-rangeX - tmpStep, i) <= 0; i -= tmpStep) {
                double startPointX = width_ + i * stepSizeX;
                double startPointY = 0;
                double endPointX = startPointX;
                double endPointY = height_;

                if (Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(majorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw major tick grid line
                    isZero = Double.compare(0.0, maxX - counter + minX) == 0;

                    if (getMajorVGridLinesVisible()) {
                        gc_.setStroke((fullRangeX && isZero) ? xAxis.getZeroColor() : majorVGridColor);
                        gc_.setLineWidth(majorLineWidth);
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else if (getMinorVGridLinesVisible()) {
                        gc_.setStroke((fullRangeX && isZero) ? xAxis.getZeroColor() : minorVGridColor);
                        gc_.setLineWidth(minorLineWidth);
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    }
                } else if (getMediumVGridLinesVisible() &&
                        Double.compare(minorTickSpaceBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck2).doubleValue(), 0.0) != 0.0 &&
                        Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck5).doubleValue(), 0.0) == 0.0) {
                    // Draw medium tick grid line
                    gc_.setStroke(mediumVGridColor);
                    gc_.setLineWidth(mediumLineWidth);
                    gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                } else if (getMinorVGridLinesVisible() && Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(minorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw minor tick grid line
                    gc_.setStroke(minorVGridColor);
                    gc_.setLineWidth(minorLineWidth);
                    gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                }

                counterBD = counterBD.add(minorTickSpaceBD);
                counter = counterBD.doubleValue();
                if (counter > maxX) break;
            }
        } else if (AxisType.LOGARITHMIC == xAxisType) {
            // ******************** Logarithmic *******************************
            double logUpperBound = Math.log10(xAxis.getMaxValue());
            double section = width_ / logUpperBound;
            boolean majorTickMarksVisible = xAxis.getMajorTickMarksVisible();
            boolean minorTickMarksVisible = xAxis.getMinorTickMarksVisible();

            for (double i = 0; i <= logUpperBound; i += 1) {
                for (double j = 1; j <= 9; j++) {
                    BigDecimal value = new BigDecimal(j * Math.pow(10, i));
                    double stepSize = i > 0 ? (Math.log10(value.doubleValue()) % i) : Math.log10(value.doubleValue());
                    double startPointX = i * section + (stepSize * section);
                    double startPointY = 0;
                    double endPointX = startPointX;
                    double endPointY = height_;

                    if (Helper.isPowerOf10(value.intValue())) {
                        if (majorTickMarksVisible) {
                            gc_.setStroke(majorVGridColor);
                            gc_.setLineWidth(majorLineWidth);
                        } else if (minorTickMarksVisible) {
                            gc_.setStroke(minorVGridColor);
                            gc_.setLineWidth(minorLineWidth);
                        }
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else {
                        if (minorTickMarksVisible) {
                            gc_.setStroke(minorVGridColor);
                            gc_.setLineWidth(minorLineWidth);
                            gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                        }
                    }
                }
            }
        }

        minorTickSpaceBD = BigDecimal.valueOf(minorTickSpaceY);
        majorTickSpaceBD = BigDecimal.valueOf(majorTickSpaceY);
        mediumCheck2 = BigDecimal.valueOf(2 * minorTickSpaceY);
        mediumCheck5 = BigDecimal.valueOf(5 * minorTickSpaceY);

        if (AxisType.LINEAR == yAxisType || AxisType.TEXT == yAxisType) {
            // ******************** Linear ************************************
            boolean isZero;
            double tmpStepSize = minorTickSpaceY;
            BigDecimal counterBD = BigDecimal.valueOf(minY);
            double counter = minY;
            BigDecimal tmpStepBD = new BigDecimal(tmpStepSize);
            tmpStepBD = tmpStepBD.setScale(3, RoundingMode.HALF_UP);
            double tmpStep = tmpStepBD.doubleValue();
            for (double i = 0; Double.compare(-rangeY - tmpStep, i) <= 0; i -= tmpStep) {
                double startPointX = 0;
                double startPointY = height_ + i * stepSizeY;
                double endPointX = width_;
                double endPointY = startPointY;

                if (Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(majorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw major tick grid line
                    isZero = Double.compare(0.0, counter) == 0;

                    if (getMajorHGridLinesVisible()) {
                        gc_.setStroke((fullRangeY && isZero) ? yAxis.getZeroColor() : majorHGridColor);
                        gc_.setLineWidth(majorLineWidth);
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else if (getMinorHGridLinesVisible()) {
                        gc_.setStroke((fullRangeY && isZero) ? yAxis.getZeroColor() : minorHGridColor);
                        gc_.setLineWidth(minorLineWidth);
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    }
                } else if (getMediumHGridLinesVisible() &&
                        Double.compare(minorTickSpaceBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck2).doubleValue(), 0.0) != 0.0 &&
                        Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck5).doubleValue(), 0.0) == 0.0) {
                    // Draw medium tick grid line
                    gc_.setStroke(mediumHGridColor);
                    gc_.setLineWidth(mediumLineWidth);
                    gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                } else if (getMinorHGridLinesVisible() && Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(minorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw minor tick grid line
                    gc_.setStroke(minorHGridColor);
                    gc_.setLineWidth(minorLineWidth);
                    gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                }

                counterBD = counterBD.add(minorTickSpaceBD);
                counter = counterBD.doubleValue();
                if (counter > maxY) break;
            }
        } else if (AxisType.LOGARITHMIC == yAxisType) {
            // ******************** Logarithmic *******************************
            double logUpperBound = Math.log10(yAxis.getMaxValue());
            double section = height_ / logUpperBound;
            boolean majorTickMarksVisible = yAxis.getMajorTickMarksVisible();
            boolean minorTickMarksVisible = yAxis.getMinorTickMarksVisible();
            double maxPosition = height_;

            for (double i = 0; i <= logUpperBound; i += 1) {
                for (double j = 1; j <= 9; j++) {
                    BigDecimal value = new BigDecimal(j * Math.pow(10, i));
                    double stepSize = i > 0 ? (Math.log10(value.doubleValue()) % i) : Math.log10(value.doubleValue());
                    double startPointX = 0;
                    double startPointY = maxPosition - i * section - (stepSize * section);
                    double endPointX = width_;
                    double endPointY = startPointY;

                    if (Helper.isPowerOf10(value.intValue())) {
                        if (majorTickMarksVisible) {
                            gc_.setStroke(majorHGridColor);
                            gc_.setLineWidth(majorLineWidth);
                        } else if (minorTickMarksVisible) {
                            gc_.setStroke(minorHGridColor);
                            gc_.setLineWidth(minorLineWidth);
                        }
                        gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else {
                        if (minorTickMarksVisible) {
                            gc_.setStroke(minorHGridColor);
                            gc_.setLineWidth(minorLineWidth);
                            gc_.strokeLine(startPointX, startPointY, endPointX, endPointY);
                        }
                    }
                }
            }
        }
    }

    private void resize() {
        width_ = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height_ = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (width_ > 0 && height_ > 0) {
            pane.setMaxSize(width_, height_);
            pane.setPrefSize(width_, height_);
            pane.relocate((getWidth() - width_) * 0.5, (getHeight() - height_) * 0.5);

            canvas.setWidth(width_);
            canvas.setHeight(height_);

            drawGrid();
        }
    }
}
