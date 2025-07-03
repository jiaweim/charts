package fx.chart;

import fx.chart.color.ColorUtils;
import fx.chart.event.ChartEvt;
import fx.chart.tools.Helper;
import javafx.beans.DefaultProperty;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Create grid-line in the XYPane
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 03 Jul 2025, 3:24 PM
 */
@DefaultProperty("children")
public class Grid extends Region {

    private static final double PREFERRED_WIDTH = 250;
    private static final double PREFERRED_HEIGHT = 250;
    private static final double MINIMUM_WIDTH = 50;
    private static final double MINIMUM_HEIGHT = 50;
    private static final double MAXIMUM_WIDTH = 4096;
    private static final double MAXIMUM_HEIGHT = 4096;
    private static final double MIN_MAJOR_LINE_WIDTH = 1;
    private static final double MIN_MEDIUM_LINE_WIDTH = 0.75;
    private static final double MIN_MINOR_LINE_WIDTH = 0.5;

    private double width;
    private double height;

    private final Axis xAxis;
    private final Axis yAxis;

    private double _gridOpacity;
    private DoubleProperty gridOpacityProperty;

    private Paint _majorHGridLinePaint;
    private ObjectProperty<Paint> majorHGridLinePaintProperty;

    private Paint _mediumHGridLinePaint;
    private ObjectProperty<Paint> mediumHGridLinePaintProperty;

    private Paint _minorHGridLinePaint;
    private ObjectProperty<Paint> minorHGridLinePaint;

    private boolean _majorHGridLinesVisible;
    private BooleanProperty majorHGridLinesVisibleProperty;

    private boolean _mediumHGridLinesVisible;
    private BooleanProperty mediumHGridLinesVisibleProperty;

    private boolean _minorHGridLinesVisible;
    private BooleanProperty minorHGridLinesVisibleProperty;

    private Paint _majorVGridLinePaint;
    private ObjectProperty<Paint> majorVGridLinePaintProperty;

    private Paint _mediumVGridLinePaint;
    private ObjectProperty<Paint> mediumVGridLinePaintProperty;

    private Paint _minorVGridLinePaint;
    private ObjectProperty<Paint> minorVGridLinePaintProperty;

    private boolean _majorVGridLinesVisible;
    private BooleanProperty majorVGridLinesVisibleProperty;

    private boolean _mediumVGridLinesVisible;
    private BooleanProperty mediumVGridLinesVisibleProperty;

    private boolean _minorVGridLinesVisible;
    private BooleanProperty minorVGridLinesVisibleProperty;

    private double[] dashes;
    private Canvas canvas;
    private GraphicsContext ctx;
    private Pane pane;

    public Grid(final Axis X_AXIS, final Axis Y_AXIS) {
        if (null == X_AXIS || null == Y_AXIS) {
            throw new IllegalArgumentException("Axis cannot be null");
        }
        xAxis = X_AXIS;
        yAxis = Y_AXIS;
        _gridOpacity = 0.25;
        _majorHGridLinePaint = null;
        _mediumHGridLinePaint = null;
        _minorHGridLinePaint = null;
        _majorHGridLinesVisible = true;
        _mediumHGridLinesVisible = true;
        _minorHGridLinesVisible = true;
        _majorVGridLinePaint = null;
        _mediumVGridLinePaint = null;
        _minorVGridLinePaint = null;
        _majorVGridLinesVisible = true;
        _mediumVGridLinesVisible = true;
        _minorVGridLinesVisible = true;
        dashes = new double[]{1};
        setMouseTransparent(true);
        initGraphics();
        registerListeners();
    }

    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
                Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        canvas = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        ctx = canvas.getGraphicsContext2D();

        pane = new Pane(canvas);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        xAxis.addChartEvtObserver(ChartEvt.AXIS_RANGE_CHANGED, e -> drawGrid());
        yAxis.addChartEvtObserver(ChartEvt.AXIS_RANGE_CHANGED, e -> drawGrid());
    }


    // ******************** Methods *******************************************
    @Override
    protected double computeMinWidth(final double HEIGHT) {return MINIMUM_WIDTH;}

    @Override
    protected double computeMinHeight(final double WIDTH) {return MINIMUM_HEIGHT;}

    @Override
    protected double computePrefWidth(final double HEIGHT) {return super.computePrefWidth(HEIGHT);}

    @Override
    protected double computePrefHeight(final double WIDTH) {return super.computePrefHeight(WIDTH);}

    @Override
    protected double computeMaxWidth(final double HEIGHT) {return MAXIMUM_WIDTH;}

    @Override
    protected double computeMaxHeight(final double WIDTH) {return MAXIMUM_HEIGHT;}

    @Override
    public ObservableList<Node> getChildren() {return super.getChildren();}

    public double getGridOpacity() {return null == gridOpacityProperty ? _gridOpacity : gridOpacityProperty.get();}

    public void setGridOpacity(final double OPACITY) {
        if (null == gridOpacityProperty) {
            _gridOpacity = Helper.clamp(0, 1, OPACITY);
            drawGrid();
        } else {
            gridOpacityProperty.set(OPACITY);
        }
    }

    public DoubleProperty gridOpacityProperty() {
        if (null == gridOpacityProperty) {
            gridOpacityProperty = new DoublePropertyBase(_gridOpacity) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "gridOpacity";}
            };
        }
        return gridOpacityProperty;
    }

    public Paint getMajorHGridLinePaint() {return null == majorHGridLinePaintProperty ? _majorHGridLinePaint : majorHGridLinePaintProperty.get();}

    public void setMajorHGridLinePaint(final Paint PAINT) {
        if (null == majorHGridLinePaintProperty) {
            _majorHGridLinePaint = PAINT;
            drawGrid();
        } else {
            majorHGridLinePaintProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> majorHGridLinePaintProperty() {
        if (null == majorHGridLinePaintProperty) {
            majorHGridLinePaintProperty = new ObjectPropertyBase<Paint>(_majorHGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "majorHGridLinePaint";}
            };
            _majorHGridLinePaint = null;
        }
        return majorHGridLinePaintProperty;
    }

    public Paint getMediumHGridLinePaint() {return null == mediumHGridLinePaintProperty ? _mediumHGridLinePaint : mediumHGridLinePaintProperty.get();}

    public void setMediumHGridLinePaint(final Paint PAINT) {
        if (null == mediumHGridLinePaintProperty) {
            _mediumHGridLinePaint = PAINT;
            drawGrid();
        } else {
            mediumHGridLinePaintProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> mediumHGridLinePaintProperty() {
        if (null == mediumHGridLinePaintProperty) {
            mediumHGridLinePaintProperty = new ObjectPropertyBase<Paint>(_mediumHGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "mediumHGridLinePaint";}
            };
            _mediumHGridLinePaint = null;
        }
        return mediumHGridLinePaintProperty;
    }

    public Paint getMinorHGridLinePaint() {return null == minorHGridLinePaint ? _minorHGridLinePaint : minorHGridLinePaint.get();}

    public void setMinorHGridLinePaint(final Paint PAINT) {
        if (null == minorHGridLinePaint) {
            _minorHGridLinePaint = PAINT;
            drawGrid();
        } else {
            minorHGridLinePaint.set(PAINT);
        }
    }

    public ObjectProperty<Paint> minorHGridLinePaintProperty() {
        if (null == minorHGridLinePaint) {
            minorHGridLinePaint = new ObjectPropertyBase<Paint>(_minorHGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "minorHGridLinePaint";}
            };
            _minorHGridLinePaint = null;
        }
        return minorHGridLinePaint;
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

    public boolean getMajorHGridLinesVisible() {return null == majorHGridLinesVisibleProperty ? _majorHGridLinesVisible : majorHGridLinesVisibleProperty.get();}

    public void setMajorHGridLinesVisible(final boolean VISIBLE) {
        if (null == majorHGridLinesVisibleProperty) {
            _majorHGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            majorHGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty majorHGridLinesVisibleProperty() {
        if (null == majorHGridLinesVisibleProperty) {
            majorHGridLinesVisibleProperty = new BooleanPropertyBase(_majorHGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "majorHGridLinesVisible";}
            };
        }
        return majorHGridLinesVisibleProperty;
    }

    public boolean getMediumHGridLinesVisible() {return null == mediumHGridLinesVisibleProperty ? _mediumHGridLinesVisible : mediumHGridLinesVisibleProperty.get();}

    public void setMediumHGridLinesVisible(final boolean VISIBLE) {
        if (null == mediumHGridLinesVisibleProperty) {
            _mediumHGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            mediumHGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty mediumHGridLinesVisibleProperty() {
        if (null == mediumHGridLinesVisibleProperty) {
            mediumHGridLinesVisibleProperty = new BooleanPropertyBase(_mediumHGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "mediumHGridLinesVisible";}
            };
        }
        return mediumHGridLinesVisibleProperty;
    }

    public boolean getMinorHGridLinesVisible() {return null == minorHGridLinesVisibleProperty ? _minorHGridLinesVisible : minorHGridLinesVisibleProperty.get();}

    public void setMinorHGridLinesVisible(final boolean VISIBLE) {
        if (null == minorHGridLinesVisibleProperty) {
            _minorHGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            minorHGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty minorHGridLinesVisibleProperty() {
        if (null == minorHGridLinesVisibleProperty) {
            minorHGridLinesVisibleProperty = new BooleanPropertyBase(_minorHGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "minorHGridLinesVisible";}
            };
        }
        return minorHGridLinesVisibleProperty;
    }

    public Paint getMajorVGridLinePaint() {return null == majorVGridLinePaintProperty ? _majorVGridLinePaint : majorVGridLinePaintProperty.get();}

    public void setMajorVGridLinePaint(final Paint PAINT) {
        if (null == majorVGridLinePaintProperty) {
            _majorVGridLinePaint = PAINT;
            drawGrid();
        } else {
            majorVGridLinePaintProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> majorVGridLinePaintProperty() {
        if (null == majorVGridLinePaintProperty) {
            majorVGridLinePaintProperty = new ObjectPropertyBase<Paint>(_majorVGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "majorVGridLinePaint";}
            };
            _majorVGridLinePaint = null;
        }
        return majorVGridLinePaintProperty;
    }

    public Paint getMediumVGridLinePaint() {return null == mediumVGridLinePaintProperty ? _mediumVGridLinePaint : mediumVGridLinePaintProperty.get();}

    public void setMediumVGridLinePaint(final Paint PAINT) {
        if (null == mediumVGridLinePaintProperty) {
            _mediumVGridLinePaint = PAINT;
            drawGrid();
        } else {
            mediumVGridLinePaintProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> mediumVGridLinePaintProperty() {
        if (null == mediumVGridLinePaintProperty) {
            mediumVGridLinePaintProperty = new ObjectPropertyBase<Paint>(_mediumVGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "mediumVGridLinePaint";}
            };
            _mediumVGridLinePaint = null;
        }
        return mediumVGridLinePaintProperty;
    }

    public Paint getMinorVGridLinePaint() {return null == minorVGridLinePaintProperty ? _minorVGridLinePaint : minorVGridLinePaintProperty.get();}

    public void setMinorVGridLinePaint(final Paint PAINT) {
        if (null == minorVGridLinePaintProperty) {
            _minorVGridLinePaint = PAINT;
            drawGrid();
        } else {
            minorVGridLinePaintProperty.set(PAINT);
        }
    }

    public ObjectProperty<Paint> minorVGridLinePaintProperty() {
        if (null == minorVGridLinePaintProperty) {
            minorVGridLinePaintProperty = new ObjectPropertyBase<Paint>(_minorVGridLinePaint) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "minorVGridLinePaint";}
            };
            _minorVGridLinePaint = null;
        }
        return minorVGridLinePaintProperty;
    }

    public boolean getMajorVGridLinesVisible() {return null == majorVGridLinesVisibleProperty ? _majorVGridLinesVisible : majorVGridLinesVisibleProperty.get();}

    public void setMajorVGridLinesVisible(final boolean VISIBLE) {
        if (null == majorVGridLinesVisibleProperty) {
            _majorVGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            majorVGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty majorVGridLinesVisibleProperty() {
        if (null == majorVGridLinesVisibleProperty) {
            majorVGridLinesVisibleProperty = new BooleanPropertyBase(_majorVGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "majorVGridLinesVisible";}
            };
        }
        return majorVGridLinesVisibleProperty;
    }

    public boolean getMediumVGridLinesVisible() {return null == mediumVGridLinesVisibleProperty ? _mediumVGridLinesVisible : mediumVGridLinesVisibleProperty.get();}

    public void setMediumVGridLinesVisible(final boolean VISIBLE) {
        if (null == mediumVGridLinesVisibleProperty) {
            _mediumVGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            mediumVGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty mediumVGridLinesVisibleProperty() {
        if (null == mediumVGridLinesVisibleProperty) {
            mediumVGridLinesVisibleProperty = new BooleanPropertyBase(_mediumVGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "mediumVGridLinesVisible";}
            };
        }
        return mediumVGridLinesVisibleProperty;
    }

    public boolean getMinorVGridLinesVisible() {return null == minorVGridLinesVisibleProperty ? _minorVGridLinesVisible : minorVGridLinesVisibleProperty.get();}

    public void setMinorVGridLinesVisible(final boolean VISIBLE) {
        if (null == minorVGridLinesVisibleProperty) {
            _minorVGridLinesVisible = VISIBLE;
            drawGrid();
        } else {
            minorVGridLinesVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty minorVGridLinesVisibleProperty() {
        if (null == minorVGridLinesVisibleProperty) {
            minorVGridLinesVisibleProperty = new BooleanPropertyBase(_minorVGridLinesVisible) {
                @Override
                protected void invalidated() {drawGrid();}

                @Override
                public Object getBean() {return Grid.this;}

                @Override
                public String getName() {return "minorVGridLinesVisible";}
            };
        }
        return minorVGridLinesVisibleProperty;
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

    public void setGridLineDashes(final double... DASHES) {
        dashes = DASHES;
        drawGrid();
    }

    private void drawGrid() {
        ctx.clearRect(0, 0, width, height);
        ctx.setLineDashes(dashes);

        double majorLineWidth = 25 * 0.007 < MIN_MAJOR_LINE_WIDTH ? MIN_MAJOR_LINE_WIDTH : 25 * 0.007;
        double mediumLineWidth = 25 * 0.006 < MIN_MEDIUM_LINE_WIDTH ? MIN_MEDIUM_LINE_WIDTH : 25 * 0.005;
        double minorLineWidth = 25 * 0.005 < MIN_MINOR_LINE_WIDTH ? MIN_MINOR_LINE_WIDTH : 25 * 0.003;

        Paint minorHGridColor = null == getMinorHGridLinePaint() ? ColorUtils.getColorWithOpacity(yAxis.getMinorTickMarkColor(), getGridOpacity()) : getMinorHGridLinePaint();
        Paint mediumHGridColor = null == getMediumHGridLinePaint() ? ColorUtils.getColorWithOpacity(yAxis.getMediumTickMarkColor(), getGridOpacity()) : getMediumHGridLinePaint();
        Paint majorHGridColor = null == getMinorHGridLinePaint() ? ColorUtils.getColorWithOpacity(yAxis.getMajorTickMarkColor(), getGridOpacity()) : getMinorHGridLinePaint();

        Paint minorVGridColor = null == getMajorVGridLinePaint() ? ColorUtils.getColorWithOpacity(xAxis.getMinorTickMarkColor(), getGridOpacity()) : getMajorVGridLinePaint();
        Paint mediumVGridColor = null == getMediumVGridLinePaint() ? ColorUtils.getColorWithOpacity(xAxis.getMediumTickMarkColor(), getGridOpacity()) : getMediumVGridLinePaint();
        Paint majorVGridColor = null == getMinorVGridLinePaint() ? ColorUtils.getColorWithOpacity(xAxis.getMajorTickMarkColor(), getGridOpacity()) : getMinorVGridLinePaint();

        AxisType xAxisType = xAxis.getType();
        double minX = xAxis.getMinValue();
        double maxX = xAxis.getMaxValue();
        boolean fullRangeX = (minX < 0 && maxX > 0);
        double minorTickSpaceX = xAxis.getMinorTickSpace();
        double majorTickSpaceX = xAxis.getMajorTickSpace();
        double rangeX = xAxis.getRange();
        double stepSizeX = Math.abs(width / rangeX);
        double zeroPositionX = xAxis.getZeroPosition();

        AxisType yAxisType = yAxis.getType();
        double minY = yAxis.getMinValue();
        double maxY = yAxis.getMaxValue();
        boolean fullRangeY = (minY < 0 && maxY > 0);
        double minorTickSpaceY = yAxis.getMinorTickSpace();
        double majorTickSpaceY = yAxis.getMajorTickSpace();
        double rangeY = yAxis.getRange();
        double stepSizeY = Math.abs(height / rangeY);
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
                double startPointX = width + i * stepSizeX;
                double startPointY = 0;
                double endPointX = startPointX;
                double endPointY = height;

                if (Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(majorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw major tick grid line
                    isZero = Double.compare(0.0, maxX - counter + minX) == 0;

                    if (getMajorVGridLinesVisible()) {
                        ctx.setStroke((fullRangeX && isZero) ? xAxis.getZeroColor() : majorVGridColor);
                        ctx.setLineWidth(majorLineWidth);
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else if (getMinorVGridLinesVisible()) {
                        ctx.setStroke((fullRangeX && isZero) ? xAxis.getZeroColor() : minorVGridColor);
                        ctx.setLineWidth(minorLineWidth);
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    }
                } else if (getMediumVGridLinesVisible() &&
                        Double.compare(minorTickSpaceBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck2).doubleValue(), 0.0) != 0.0 &&
                        Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck5).doubleValue(), 0.0) == 0.0) {
                    // Draw medium tick grid line
                    ctx.setStroke(mediumVGridColor);
                    ctx.setLineWidth(mediumLineWidth);
                    ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                } else if (getMinorVGridLinesVisible() && Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(minorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw minor tick grid line
                    ctx.setStroke(minorVGridColor);
                    ctx.setLineWidth(minorLineWidth);
                    ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                }

                counterBD = counterBD.add(minorTickSpaceBD);
                counter = counterBD.doubleValue();
                if (counter > maxX) break;
            }
        } else if (AxisType.LOGARITHMIC == xAxisType) {
            // ******************** Logarithmic *******************************
            double logUpperBound = Math.log10(xAxis.getMaxValue());
            double section = width / logUpperBound;
            boolean majorTickMarksVisible = xAxis.getMajorTickMarksVisible();
            boolean minorTickMarksVisible = xAxis.getMinorTickMarksVisible();

            for (double i = 0; i <= logUpperBound; i += 1) {
                for (double j = 1; j <= 9; j++) {
                    BigDecimal value = new BigDecimal(j * Math.pow(10, i));
                    double stepSize = i > 0 ? (Math.log10(value.doubleValue()) % i) : Math.log10(value.doubleValue());
                    double startPointX = i * section + (stepSize * section);
                    double startPointY = 0;
                    double endPointX = startPointX;
                    double endPointY = height;

                    if (Helper.isPowerOf10(value.intValue())) {
                        if (majorTickMarksVisible) {
                            ctx.setStroke(majorVGridColor);
                            ctx.setLineWidth(majorLineWidth);
                        } else if (minorTickMarksVisible) {
                            ctx.setStroke(minorVGridColor);
                            ctx.setLineWidth(minorLineWidth);
                        }
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else {
                        if (minorTickMarksVisible) {
                            ctx.setStroke(minorVGridColor);
                            ctx.setLineWidth(minorLineWidth);
                            ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
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
                double startPointY = height + i * stepSizeY;
                double endPointX = width;
                double endPointY = startPointY;

                if (Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(majorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw major tick grid line
                    isZero = Double.compare(0.0, counter) == 0;

                    if (getMajorHGridLinesVisible()) {
                        ctx.setStroke((fullRangeY && isZero) ? yAxis.getZeroColor() : majorHGridColor);
                        ctx.setLineWidth(majorLineWidth);
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else if (getMinorHGridLinesVisible()) {
                        ctx.setStroke((fullRangeY && isZero) ? yAxis.getZeroColor() : minorHGridColor);
                        ctx.setLineWidth(minorLineWidth);
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    }
                } else if (getMediumHGridLinesVisible() &&
                        Double.compare(minorTickSpaceBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck2).doubleValue(), 0.0) != 0.0 &&
                        Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck5).doubleValue(), 0.0) == 0.0) {
                    // Draw medium tick grid line
                    ctx.setStroke(mediumHGridColor);
                    ctx.setLineWidth(mediumLineWidth);
                    ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                } else if (getMinorHGridLinesVisible() && Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(minorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw minor tick grid line
                    ctx.setStroke(minorHGridColor);
                    ctx.setLineWidth(minorLineWidth);
                    ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                }

                counterBD = counterBD.add(minorTickSpaceBD);
                counter = counterBD.doubleValue();
                if (counter > maxY) break;
            }
        } else if (AxisType.LOGARITHMIC == yAxisType) {
            // ******************** Logarithmic *******************************
            double logUpperBound = Math.log10(yAxis.getMaxValue());
            double section = height / logUpperBound;
            boolean majorTickMarksVisible = yAxis.getMajorTickMarksVisible();
            boolean minorTickMarksVisible = yAxis.getMinorTickMarksVisible();
            double maxPosition = height;

            for (double i = 0; i <= logUpperBound; i += 1) {
                for (double j = 1; j <= 9; j++) {
                    BigDecimal value = new BigDecimal(j * Math.pow(10, i));
                    double stepSize = i > 0 ? (Math.log10(value.doubleValue()) % i) : Math.log10(value.doubleValue());
                    double startPointX = 0;
                    double startPointY = maxPosition - i * section - (stepSize * section);
                    double endPointX = width;
                    double endPointY = startPointY;

                    if (Helper.isPowerOf10(value.intValue())) {
                        if (majorTickMarksVisible) {
                            ctx.setStroke(majorHGridColor);
                            ctx.setLineWidth(majorLineWidth);
                        } else if (minorTickMarksVisible) {
                            ctx.setStroke(minorHGridColor);
                            ctx.setLineWidth(minorLineWidth);
                        }
                        ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                    } else {
                        if (minorTickMarksVisible) {
                            ctx.setStroke(minorHGridColor);
                            ctx.setLineWidth(minorLineWidth);
                            ctx.strokeLine(startPointX, startPointY, endPointX, endPointY);
                        }
                    }
                }
            }
        }
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        if (width > 0 && height > 0) {
            pane.setMaxSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            canvas.setWidth(width);
            canvas.setHeight(height);

            drawGrid();
        }
    }
}
