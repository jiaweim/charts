package fx.chart;

import fx.chart.color.ColorUtils;
import fx.chart.data.XYChartItem;
import fx.chart.data.XYItem;
import fx.chart.event.ChartEvent;
import fx.chart.event.CursorEvent;
import fx.chart.event.CursorEventListener;
import fx.chart.event.SeriesEventListener;
import fx.chart.font.Fonts;
import fx.chart.series.Series;
import fx.chart.series.XYSeries;
import fx.chart.tools.Helper;
import fx.chart.tools.TooltipPopup;
import fx.chart.util.Point;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pdk.util.math.StatUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static fx.chart.ChartType.SMOOTH_POLAR;

/**
 * A pane to hold xy-chart
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 2:39 PM
 */
public class XYPane<T extends XYItem> extends ChartElement implements ChartArea {

    private static final double PREFERRED_WIDTH = 250;
    private static final double PREFERRED_HEIGHT = 250;

    private static final double MINIMUM_WIDTH = 0;
    private static final double MINIMUM_HEIGHT = 0;

    private static final double MIN_SYMBOL_SIZE = 2;
    private static final double MAX_SYMBOL_SIZE = 6;
    private static final int SUB_DIVISIONS = 24;

    private static double aspectRatio;
    private boolean keepAspect;
    private double size;
    private double width;
    /**
     * height of the pane
     */
    private double height;

    private Paint chartBackground_;
    private ObjectProperty<Paint> chartBackgroundProperty;

    private ObservableList<XYSeries<T>> listOfSeries;
    private Canvas canvas_;
    private GraphicsContext gc_;

    private Canvas cursorCanvas_;
    private GraphicsContext cursorGC_;

    /**
     * cursor position
     */
    private double cursorX_;
    private double cursorY_;
    /**
     * how many pixels for unit x
     */
    private double scaleX;
    /**
     * how many pixels for unit y
     */
    private double scaleY;
    private double symbolSize;
    private int noOfBands;

    private double lowerBoundX_;
    private DoubleProperty lowerBoundXProperty;
    private double upperBoundX_;
    private DoubleProperty upperBoundXProperty;
    private double lowerBoundY_;
    private DoubleProperty lowerBoundYProperty;
    private double upperBoundY_;
    private DoubleProperty upperBoundYProperty;

    private boolean referenceZero;

    private double thresholdY_;
    private DoubleProperty thresholdYProperty;

    private boolean thresholdYVisible_;
    private BooleanProperty thresholdYVisibleProperty;

    private Color _thresholdYColor;
    private ObjectProperty<Color> thresholdYColor;

    private PolarTickStep _polarTickStep;
    private ObjectProperty<PolarTickStep> polarTickStep;

    private Paint _envelopeFill;
    private ObjectProperty<Paint> envelopeFill;

    private Color _envelopeStroke;
    private ObjectProperty<Color> envelopeStroke;

    private Color _averageStroke;
    private ObjectProperty<Color> averageStroke;

    private Paint _stdDeviationFill;
    private ObjectProperty<Paint> stdDeviationFill;
    private Color _stdDeviationStroke;
    private ObjectProperty<Color> stdDeviationStrokeProperty;
    private boolean _envelopeVisible;
    private BooleanProperty envelopeVisibleProperty;
    private boolean _stdDeviationVisible;
    private BooleanProperty stdDeviationVisibleProperty;
    private double _averageStrokeWidth;
    private DoubleProperty averageStrokeWidthProperty;

    private boolean crossHairVisible_;
    private BooleanProperty crossHairVisibleProperty;

    private Color crossHairColor_;
    private ObjectProperty<Color> crossHairColorProperty;

    private boolean categoryTextVisible_;
    private BooleanProperty categoryTextVisibleProperty;

    private Color foregroundColor_;
    private ObjectProperty<Color> foregroundColorProperty;

    private ObservableList<String> categories_;

    private ObservableList<XYPaneOverlay> overlays_;
    private TooltipPopup popup_;
    private SeriesEventListener seriesListener_;
    private final EventHandler<MouseEvent> mouseHandler_;
    private final List<CursorEventListener> cursorEventListeners_;

    public XYPane(final List<XYSeries<T>> SERIES) {
        this(Color.TRANSPARENT, 1, SERIES.toArray(new XYSeries[0]));
    }

    /**
     * Create a XYPane containing a list of {@link XYSeries}
     *
     * @param series {@link XYSeries} array
     */
    @SafeVarargs
    public XYPane(final XYSeries<T>... series) {
        this(Color.TRANSPARENT, 1, series);
    }

    public XYPane(final int bands, final XYSeries<T>... series) {
        this(Color.TRANSPARENT, bands, series);
    }

    public XYPane(final Paint BACKGROUND, final int BANDS, final XYSeries<T>... SERIES) {
        getStylesheets().add(XYPane.class.getResource("chart.css").toExternalForm());
        aspectRatio = PREFERRED_HEIGHT / PREFERRED_WIDTH;
        cursorEventListeners_ = new CopyOnWriteArrayList<>();
        keepAspect = false;
        chartBackground_ = BACKGROUND;
        listOfSeries = FXCollections.observableArrayList(SERIES);
        scaleX = 1;
        scaleY = 1;
        symbolSize = 2;
        noOfBands = Math.clamp(BANDS, 1, 5);
        lowerBoundX_ = 0;
        upperBoundX_ = 100;
        lowerBoundY_ = 0;
        upperBoundY_ = 100;
        referenceZero = true;
        thresholdY_ = 100;
        thresholdYVisible_ = false;
        _thresholdYColor = Color.RED;
        _polarTickStep = PolarTickStep.FOURTY_FIVE;
        _envelopeFill = Color.rgb(120, 120, 120, 0.2);
        _envelopeStroke = Color.rgb(120, 120, 120);
        _averageStroke = Color.BLACK;
        _stdDeviationFill = Color.rgb(200, 0, 0, 0.2);
        _stdDeviationStroke = Color.rgb(200, 0, 0);
        _envelopeVisible = false;
        _stdDeviationVisible = true;
        _averageStrokeWidth = 1;
        crossHairVisible_ = false;
        crossHairColor_ = Color.GRAY;
        categoryTextVisible_ = false;
        foregroundColor_ = Color.BLACK;
        categories_ = FXCollections.observableArrayList();
        overlays_ = FXCollections.observableArrayList();
        cursorX_ = -1;
        cursorY_ = -1;
        popup_ = new TooltipPopup(2000);
        seriesListener_ = e -> redraw();
        mouseHandler_ = e -> {
            cursorX_ = e.getX();
            cursorY_ = e.getY();
            drawCursor();
            for (XYSeries<T> series : listOfSeries) {
                double radius = series.getSymbolSize() * 0.5;
                for (T item : series.getItems()) {
                    Point2D pointInScene = localToScene(new Point2D((item.getX() - getLowerBoundX()) * scaleX, height - (item.getY() - getLowerBoundY()) * scaleY));
                    if (Helper.isInCircle(e.getSceneX(), e.getSceneY(), pointInScene.getX(), pointInScene.getY(), radius) && !item.getTooltipText().isEmpty() && !popup_.getText().equals(item.getTooltipText())) {
                        popup_.setX(e.getScreenX());
                        popup_.setY(e.getScreenY() - popup_.getHeight());
                        popup_.setText(item.getTooltipText());
                        popup_.animatedShow(getScene().getWindow());
                        break;
                    }
                }
            }
        };
        popup_.setOnHiding(e -> popup_.setText(""));

        categories_.addAll("", "", "", "", "", "", "", "");

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

        getStyleClass().setAll("chart", "xy-chart");

        canvas_ = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        gc_ = canvas_.getGraphicsContext2D();

        cursorCanvas_ = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        cursorCanvas_.setMouseTransparent(true);

        Helper.enableNode(cursorCanvas_, true);
        cursorGC_ = cursorCanvas_.getGraphicsContext2D();

        getChildren().setAll(canvas_, cursorCanvas_);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        listOfSeries.addListener((ListChangeListener<XYSeries<T>>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(series -> series.addSeriesEventListener(seriesListener_));
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(series -> series.removeSeriesEventListener(seriesListener_));
                }
            }
            redraw();
        });
        listOfSeries.forEach(series -> {
            if (null != series) {
                series.addSeriesEventListener(seriesEvent -> redraw());
            }
        });
        overlays_.addListener((ListChangeListener<? super XYPaneOverlay>) c -> {
            redraw();
        });
        canvas_.addEventHandler(MouseEvent.MOUSE_MOVED, mouseHandler_);
    }

    @Override
    protected double computeMinWidth(final double height) {return MINIMUM_WIDTH;}

    @Override
    protected double computeMinHeight(final double width) {return MINIMUM_HEIGHT;}

    public void dispose() {
        canvas_.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseHandler_);
        removeAllCursorEventListeners();
    }

    public Paint getChartBackground() {return null == chartBackgroundProperty ? chartBackground_ : chartBackgroundProperty.get();}

    public void setChartBackground(final Paint paint) {
        if (chartBackgroundProperty == null) {
            chartBackground_ = paint;
            redraw();
        } else {
            chartBackgroundProperty.set(paint);
        }
    }

    public ObjectProperty<Paint> chartBackgroundProperty() {
        if (null == chartBackgroundProperty) {
            chartBackgroundProperty = new ObjectPropertyBase<>(chartBackground_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "chartBackground";}
            };
            chartBackground_ = null;
        }
        return chartBackgroundProperty;
    }

    public int getNoOfBands() {return noOfBands;}

    public void setNoOfBands(final int bands) {
        noOfBands = Math.clamp(bands, 1, 5);
        redraw();
    }

    /**
     * lower bound of x-values
     *
     * @return lower bound
     */
    public double getLowerBoundX() {
        return lowerBoundXProperty == null ? lowerBoundX_ : lowerBoundXProperty.get();
    }

    public void setLowerBoundX(final double value) {
        if (null == lowerBoundXProperty) {
            lowerBoundX_ = value;
            resize();
        } else {
            lowerBoundXProperty.set(value);
        }
    }

    public DoubleProperty lowerBoundXProperty() {
        if (null == lowerBoundXProperty) {
            lowerBoundXProperty = new DoublePropertyBase(lowerBoundX_) {
                @Override
                protected void invalidated() {resize();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "lowerBoundX";}
            };
        }
        return lowerBoundXProperty;
    }

    public double getUpperBoundX() {return null == upperBoundXProperty ? upperBoundX_ : upperBoundXProperty.get();}

    public void setUpperBoundX(final double VALUE) {
        if (null == upperBoundXProperty) {
            upperBoundX_ = VALUE;
            resize();
        } else {
            upperBoundXProperty.set(VALUE);
        }
    }

    public DoubleProperty upperBoundXProperty() {
        if (null == upperBoundXProperty) {
            upperBoundXProperty = new DoublePropertyBase(upperBoundX_) {
                @Override
                protected void invalidated() {resize();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "upperBoundX";}
            };
        }
        return upperBoundXProperty;
    }

    public double getLowerBoundY() {return null == lowerBoundYProperty ? lowerBoundY_ : lowerBoundYProperty.get();}

    public void setLowerBoundY(final double VALUE) {
        if (null == lowerBoundYProperty) {
            lowerBoundY_ = VALUE;
            resize();
        } else {
            lowerBoundYProperty.set(VALUE);
        }
    }

    public DoubleProperty lowerBoundYProperty() {
        if (null == lowerBoundYProperty) {
            lowerBoundYProperty = new DoublePropertyBase(lowerBoundY_) {
                @Override
                protected void invalidated() {resize();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "lowerBoundY";}
            };
        }
        return lowerBoundYProperty;
    }

    public double getUpperBoundY() {return null == upperBoundYProperty ? upperBoundY_ : upperBoundYProperty.get();}

    public void setUpperBoundY(final double VALUE) {
        if (null == upperBoundYProperty) {
            upperBoundY_ = VALUE;
            resize();
        } else {
            upperBoundYProperty.set(VALUE);
        }
    }

    public DoubleProperty upperBoundYProperty() {
        if (null == upperBoundYProperty) {
            upperBoundYProperty = new DoublePropertyBase(upperBoundY_) {
                @Override
                protected void invalidated() {resize();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "upperBoundY";}
            };
        }
        return upperBoundYProperty;
    }

    public boolean isReferenceZero() {return referenceZero;}

    public void setReferenceZero(final boolean IS_ZERO) {
        referenceZero = IS_ZERO;
        redraw();
    }

    /**
     * Return the pixel range of the x
     *
     * @return range of x
     */
    public double getRangeX() {return getUpperBoundX() - getLowerBoundX();}

    public double getRangeY() {return getUpperBoundY() - getLowerBoundY();}

    public double getDataMinX() {return listOfSeries.stream().mapToDouble(XYSeries::getMinX).min().getAsDouble();}

    public double getDataMaxX() {return listOfSeries.stream().mapToDouble(XYSeries::getMaxX).max().getAsDouble();}

    public double getDataMinY() {return listOfSeries.stream().mapToDouble(XYSeries::getMinY).min().getAsDouble();}

    public double getDataMaxY() {return listOfSeries.stream().mapToDouble(XYSeries::getMaxY).max().getAsDouble();}

    public double getDataRangeX() {return getDataMaxX() - getDataMinX();}

    public double getDataRangeY() {return getDataMaxY() - getDataMinY();}

    public List<XYSeries<T>> getListOfSeries() {return listOfSeries;}

    public double getThresholdY() {return null == thresholdYProperty ? thresholdY_ : thresholdYProperty.get();}

    public void setThresholdY(final double THRESHOLD) {
        if (null == thresholdYProperty) {
            thresholdY_ = THRESHOLD;
            redraw();
        } else {
            thresholdYProperty.set(THRESHOLD);
        }
    }

    public DoubleProperty thresholdYProperty() {
        if (null == thresholdYProperty) {
            thresholdYProperty = new DoublePropertyBase(thresholdY_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "thresholdY";}
            };
        }
        return thresholdYProperty;
    }

    public boolean isThresholdYVisible() {return null == thresholdYVisibleProperty ? thresholdYVisible_ : thresholdYVisibleProperty.get();}

    public void setThresholdYVisible(final boolean VISIBLE) {
        if (null == thresholdYVisibleProperty) {
            thresholdYVisible_ = VISIBLE;
            redraw();
        } else {
            thresholdYVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty thresholdYVisibleProperty() {
        if (null == thresholdYVisibleProperty) {
            thresholdYVisibleProperty = new BooleanPropertyBase(thresholdYVisible_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "thresholdYVisible";}
            };
        }
        return thresholdYVisibleProperty;
    }

    public Color getThresholdYColor() {return null == thresholdYColor ? _thresholdYColor : thresholdYColor.get();}

    public void setThresholdYColor(final Color COLOR) {
        if (null == thresholdYColor) {
            _thresholdYColor = COLOR;
            redraw();
        } else {
            thresholdYColor.set(COLOR);
        }
    }

    public ObjectProperty<Color> thresholdYColorProperty() {
        if (null == thresholdYColor) {
            thresholdYColor = new ObjectPropertyBase<Color>(_thresholdYColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "thresholdYColor";}
            };
            _thresholdYColor = null;
        }
        return thresholdYColor;
    }

    public PolarTickStep getPolarTickStep() {return null == polarTickStep ? _polarTickStep : polarTickStep.get();}

    public void setPolarTickStep(final PolarTickStep STEP) {
        if (null == polarTickStep) {
            _polarTickStep = STEP;
            drawChart();
        } else {
            polarTickStep.set(STEP);
        }
    }

    public ObjectProperty<PolarTickStep> polarTickStepProperty() {
        if (null == polarTickStep) {
            polarTickStep = new ObjectPropertyBase<PolarTickStep>() {
                @Override
                protected void invalidated() {drawChart();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "polarTickStep";}
            };
            _polarTickStep = null;
        }
        return polarTickStep;
    }

    public Paint getEnvelopeFill() {return null == envelopeFill ? _envelopeFill : envelopeFill.get();}

    public void setEnvelopeFill(final Paint ENVELOPE_FILL) {
        if (null == envelopeFill) {
            _envelopeFill = ENVELOPE_FILL;
            redraw();
        } else {
            envelopeFill.set(ENVELOPE_FILL);
        }
    }

    public ObjectProperty<Paint> envelopeFillProperty() {
        if (null == envelopeFill) {
            envelopeFill = new ObjectPropertyBase<>(_envelopeFill) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "envelopeFill";}
            };
            _envelopeFill = null;
        }
        return envelopeFill;
    }

    public Color getEnvelopeStroke() {return null == envelopeStroke ? _envelopeStroke : envelopeStroke.get();}

    public void setEnvelopeStroke(final Color ENVELOPE_STROKE) {
        if (null == envelopeStroke) {
            _envelopeStroke = ENVELOPE_STROKE;
            redraw();
        } else {
            envelopeStroke.set(ENVELOPE_STROKE);
        }
    }

    public ObjectProperty<Color> envelopeStrokeProperty() {
        if (null == envelopeStroke) {
            envelopeStroke = new ObjectPropertyBase<>(_envelopeStroke) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "envelopeStroke";}
            };
            _envelopeStroke = null;
        }
        return envelopeStroke;
    }

    public Color getAverageStroke() {return null == averageStroke ? _averageStroke : averageStroke.get();}

    public void setAverageStroke(final Color AVERAGE_STROKE) {
        if (null == averageStroke) {
            _averageStroke = AVERAGE_STROKE;
            redraw();
        } else {
            averageStroke.set(AVERAGE_STROKE);
        }
    }

    public ObjectProperty<Color> averageStrokeProperty() {
        if (null == averageStroke) {
            averageStroke = new ObjectPropertyBase<>(_averageStroke) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "averageStroke";}
            };
            _averageStroke = null;
        }
        return averageStroke;
    }

    public Paint getStdDeviationFill() {return null == stdDeviationFill ? _stdDeviationFill : stdDeviationFill.get();}

    public void setStdDeviationFill(final Paint STD_DEVIATION_FILL) {
        if (null == stdDeviationFill) {
            _stdDeviationFill = STD_DEVIATION_FILL;
            redraw();
        } else {
            stdDeviationFill.set(STD_DEVIATION_FILL);
        }
    }

    public ObjectProperty<Paint> stdDeviationFillProperty() {
        if (null == stdDeviationFill) {
            stdDeviationFill = new ObjectPropertyBase<>(_stdDeviationFill) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "stdDeviationFill";}
            };
            _stdDeviationFill = null;
        }
        return stdDeviationFill;
    }

    public Color getStdDeviationStroke() {return null == stdDeviationStrokeProperty ? _stdDeviationStroke : stdDeviationStrokeProperty.get();}

    public void setStdDeviationStroke(final Color STD_DEVIATION_STROKE) {
        if (null == stdDeviationStrokeProperty) {
            _stdDeviationStroke = STD_DEVIATION_STROKE;
            redraw();
        } else {
            stdDeviationStrokeProperty.set(STD_DEVIATION_STROKE);
        }
    }

    public ObjectProperty<Color> stdDeviationStrokeProperty() {
        if (null == stdDeviationStrokeProperty) {
            stdDeviationStrokeProperty = new ObjectPropertyBase<>(_stdDeviationStroke) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "stdDeviationStroke";}
            };
            _stdDeviationStroke = null;
        }
        return stdDeviationStrokeProperty;
    }

    public boolean isEnvelopeVisible() {return null == envelopeVisibleProperty ? _envelopeVisible : envelopeVisibleProperty.get();}

    public void setEnvelopeVisible(final boolean VISIBLE) {
        if (null == envelopeVisibleProperty) {
            _envelopeVisible = VISIBLE;
            redraw();
        } else {
            envelopeVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty envelopeVisibleProperty() {
        if (null == envelopeVisibleProperty) {
            envelopeVisibleProperty = new BooleanPropertyBase() {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "envelopeVisible";}
            };
        }
        return envelopeVisibleProperty;
    }

    public boolean isStdDeviationVisible() {return null == stdDeviationVisibleProperty ? _stdDeviationVisible : stdDeviationVisibleProperty.get();}

    public void setStdDeviationVisbile(final boolean VISIBLE) {
        if (null == stdDeviationVisibleProperty) {
            _stdDeviationVisible = VISIBLE;
            redraw();
        } else {
            stdDeviationVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty stdDeviationVisibleProperty() {
        if (null == stdDeviationVisibleProperty) {
            stdDeviationVisibleProperty = new BooleanPropertyBase(_stdDeviationVisible) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "stdDeviationVisible";}
            };
        }
        return stdDeviationVisibleProperty;
    }

    public double getAverageStrokeWidth() {return null == averageStrokeWidthProperty ? _averageStrokeWidth : averageStrokeWidthProperty.get();}

    public void setAverageStrokeWidth(final double WIDTH) {
        if (null == averageStrokeWidthProperty) {
            _averageStrokeWidth = Math.clamp(WIDTH, 0.1, 10);
            redraw();
        } else {
            averageStrokeWidthProperty.set(WIDTH);
        }
    }

    public DoubleProperty averageStrokeWidthProperty() {
        if (null == averageStrokeWidthProperty) {
            averageStrokeWidthProperty = new DoublePropertyBase(_averageStrokeWidth) {
                @Override
                protected void invalidated() {set(Math.clamp(get(), 0.1, 10));}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "averageStrokeWidth";}
            };
        }
        return averageStrokeWidthProperty;
    }

    /**
     * @return Whether to display crosshair under the cursor
     */
    public boolean isCrossHairVisible() {return crossHairVisibleProperty == null ? crossHairVisible_ : crossHairVisibleProperty.get();}

    public void setCrossHairVisible(final boolean visible) {
        if (null == crossHairVisibleProperty) {
            crossHairVisible_ = visible;
            Helper.enableNode(cursorCanvas_, visible);
            drawCursor();
        } else {
            crossHairVisibleProperty.set(visible);
        }
    }

    public BooleanProperty crossHairVisibleProperty() {
        if (null == crossHairVisibleProperty) {
            crossHairVisibleProperty = new BooleanPropertyBase(crossHairVisible_) {
                @Override
                protected void invalidated() {
                    Helper.enableNode(cursorCanvas_, get());
                    drawCursor();
                }

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "crossHairVisible";}
            };
        }
        return crossHairVisibleProperty;
    }

    public Color getCrossHairColor() {return null == crossHairColorProperty ? crossHairColor_ : crossHairColorProperty.get();}

    public void setCrossHairColor(final Color COLOR) {
        if (null == crossHairColorProperty) {
            crossHairColor_ = COLOR;
            drawCursor();
        } else {
            crossHairColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> crossHairColorProperty() {
        if (null == crossHairColorProperty) {
            crossHairColorProperty = new ObjectPropertyBase<>(crossHairColor_) {
                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "crossHairColor";}
            };
            crossHairColor_ = null;
        }
        return crossHairColorProperty;
    }

    public boolean isCategoryTextVisible() {
        return categoryTextVisibleProperty == null ? categoryTextVisible_ : categoryTextVisibleProperty.get();
    }

    public void setCategoryTextVisible(final boolean visible) {
        if (categoryTextVisibleProperty == null) {
            categoryTextVisible_ = visible;
            drawCursor();
        } else {
            categoryTextVisibleProperty.set(visible);
        }
    }

    public BooleanProperty categoryTextVisibleProperty() {
        if (categoryTextVisibleProperty == null) {
            categoryTextVisibleProperty = new BooleanPropertyBase(categoryTextVisible_) {
                @Override
                protected void invalidated() {
                    drawChart();
                }

                @Override
                public Object getBean() {
                    return XYPane.this;
                }

                @Override
                public String getName() {
                    return "categoryTextVisible";
                }
            };
        }
        return categoryTextVisibleProperty;
    }

    public List<String> getCategories() {
        return new ArrayList<>(categories_);
    }

    public void setCategories(final String... CATEGORIES) {setCategories(Arrays.asList(CATEGORIES));}

    public void setCategories(final List<String> CATEGORIES) {
        if (null == CATEGORIES || CATEGORIES.size() != 360 / getPolarTickStep().get()) {
            throw new IllegalArgumentException("Number of categories must fit the polar tick steps");
        }
        this.categories_.setAll(CATEGORIES);
        redraw();
    }


    public Color getForegroundColor() {
        return null == foregroundColorProperty ? foregroundColor_ : foregroundColorProperty.get();
    }

    public void setForegroundColor(final Color COLOR) {
        if (null == this.foregroundColorProperty) {
            foregroundColor_ = COLOR;
            redraw();
        } else {
            foregroundColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> foregroundColorProperty() {
        if (null == foregroundColorProperty) {
            foregroundColorProperty = new ObjectPropertyBase<>(foregroundColor_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return XYPane.this;}

                @Override
                public String getName() {return "foregroundColor";}
            };
            foregroundColor_ = null;
        }
        return foregroundColorProperty;
    }

    public ObservableList<XYPaneOverlay> getOverlays() {return this.overlays_;}

    public void setOverlays(final List<XYPaneOverlay> overlays) {this.overlays_.setAll(overlays);}

    public boolean containsPolarChart() {
        for (XYSeries<T> series : listOfSeries) {
            if (null == series) {
                continue;
            }
            ChartType type = series.getChartType();
            if (ChartType.POLAR == type || SMOOTH_POLAR == type) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSpiderChart() {
        for (XYSeries<T> series : listOfSeries) {
            if (null == series) {
                continue;
            }
            ChartType type = series.getChartType();
            if (ChartType.SPIDER == type) {
                return true;
            }
        }
        return false;
    }


    protected void redraw() {
        drawChart();
        drawCursor();
        fireChartEvent(new ChartEvent(XYPane.this, ChartEvent.UPDATE));
    }

    private void drawChart() {
        if (null == listOfSeries || listOfSeries.isEmpty()) {
            return;
        }

        gc_.clearRect(0, 0, width, height);
        gc_.setFill(getChartBackground());
        gc_.fillRect(0, 0, width, height);

        if (listOfSeries.size() == 2) {
            boolean deltaChart = false;
            ChartType[] chartTypes = new ChartType[2];
            int count = 0;
            for (Series series : listOfSeries) {
                chartTypes[count] = series.getChartType();
                deltaChart = ChartType.LINE_DELTA == chartTypes[count] || ChartType.SMOOTH_LINE_DELTA == chartTypes[count];
                count++;
            }
            if (deltaChart && chartTypes[0] == chartTypes[1]) {
                switch (chartTypes[0]) {
                    case LINE_DELTA:
                        drawLineDelta(listOfSeries.get(0), listOfSeries.get(1));
                        return;
                    case SMOOTH_LINE_DELTA:
                        drawSmoothLineDelta(listOfSeries.get(0), listOfSeries.get(1));
                        return;
                }
            }
        }

        List<XYSeries<T>> listOfMultiTimeSeries = listOfSeries.stream()
                .filter(series -> ChartType.MULTI_TIME_SERIES == series.getChartType())
                .collect(Collectors.toList());
        List<XYSeries<T>> listOfSmoothedMultiTimeSeries = listOfSeries.stream()
                .filter(series -> ChartType.SMOOTHED_MULTI_TIME_SERIES == series.getChartType())
                .collect(Collectors.toList());
        if (listOfMultiTimeSeries.isEmpty() && listOfSmoothedMultiTimeSeries.isEmpty()) {
            for (XYSeries<T> series : listOfSeries) {
                final ChartType TYPE = series.getChartType();
                final boolean SHOW_POINTS = series.getSymbolsVisible();
                switch (TYPE) {
                    case LINE -> drawLine(series, SHOW_POINTS);
                    case SMOOTH_LINE -> drawSmoothLine(series, SHOW_POINTS);
                    case AREA -> drawArea(series, SHOW_POINTS);
                    case SMOOTH_AREA -> drawSmoothArea(series, SHOW_POINTS);
                    case SCATTER -> drawScatter(series);
                    case POINCARE -> drawPoincare(series);
                    case HORIZON -> drawHorizon(series, false);
                    case RIDGE_LINE -> drawRidgeLine(series);
                    case SMOOTHED_HORIZON -> drawHorizon(series, true);
                    case POLAR, SMOOTH_POLAR -> drawPolar(series);
                    case SPIDER -> drawSpider(series);
                }
            }
        } else {
            if (listOfMultiTimeSeries.size() == listOfSeries.size()) {
                drawMultiTimeSeries(listOfMultiTimeSeries);
            } else if (listOfSmoothedMultiTimeSeries.size() == listOfSeries.size()) {
                drawSmoothedMultiTimeSeries(listOfSmoothedMultiTimeSeries);
            }
        }

        if (!getOverlays().isEmpty()) {
            for (XYPaneOverlay overlay : getOverlays()) {
                switch (overlay.getType()) {
                    case PREDICTION -> drawPredictionOverlay(overlay);
                }
            }
        }
    }

    private void drawCursor() {
        cursorGC_.clearRect(0, 0, width, height);
        if (isCrossHairVisible()) {
            cursorGC_.setStroke(getCrossHairColor());
            cursorGC_.strokeLine(0, cursorY_, width, cursorY_);
            cursorGC_.strokeLine(cursorX_, 0, cursorX_, height);

            double x = cursorX_ / scaleX + getLowerBoundX();
            double y = ((cursorY_ - height) / scaleY - getLowerBoundY()) * -1;
            fireCursorEvent(new CursorEvent(x, y));
        }
    }

    /**
     * draw a line chart
     *
     * @param series     {@link XYSeries}
     * @param showPoints true if show symbol
     */
    private void drawLine(final XYSeries<T> series, final boolean showPoints) {
        if (series == null || !series.isVisible() || series.getItems().isEmpty()) {
            return;
        }
        final double minX = getLowerBoundX();
        final double minY = getLowerBoundY();
        List<T> items = series.getItems();
        double oldX = (items.get(0).getX() - minX) * scaleX;
        double oldY = height - (items.get(0).getY() - minY) * scaleY;
        boolean wasEmpty = items.get(0).isEmptyItem();

        gc_.save();

        gc_.setLineWidth(series.getStrokeWidth() > -1 ? series.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(series.getStroke());
        gc_.setFill(Color.TRANSPARENT);
        double[] dashes = series.getLineDashes();
        if (dashes != null) {
            gc_.setLineDashes(dashes);
        }

        for (T item : series.getItems()) {
            double x = (item.getX() - minX) * scaleX;
            double y = height - (item.getY() - minY) * scaleY;
            boolean isEmpty = item.isEmptyItem();
            if (!isEmpty && !wasEmpty) {
                gc_.strokeLine(oldX, oldY, x, y);
            }
            oldX = x;
            oldY = y;
            wasEmpty = isEmpty;
        }

        if (showPoints) {
            drawSymbols(series);
        }
        gc_.restore();
    }

    private void drawArea(final XYSeries<T> SERIES, final boolean SHOW_POINTS) {
        if (SERIES == null || !SERIES.isVisible() || SERIES.getItems().isEmpty()) {
            return;
        }
        final double lowerBoundX = getLowerBoundX();
        final double lowerBoundY = getLowerBoundY();
        List<T> items = SERIES.getItems();
        int noOfItems = items.size();
        double oldX = (items.get(0).getX() - lowerBoundX) * scaleX;
        double oldY = height - (items.get(0).getY() - lowerBoundY) * scaleY;
        boolean wasEmpty = items.get(0).isEmptyItem();

        // Fill Area
        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());
        gc_.setFill(SERIES.getFill());
        gc_.beginPath();
        gc_.moveTo(oldX, oldY);

        for (int i = 1; i < noOfItems; i++) {
            T item = items.get(i);
            double x = (item.getX() - lowerBoundX) * scaleX;
            double y = height - (item.getY() - lowerBoundY) * scaleY;
            boolean isEmpty = item.isEmptyItem();
            if (isEmpty) {
                gc_.lineTo(oldX, height - (lowerBoundY) * scaleY);
                gc_.lineTo(x, height - (lowerBoundY) * scaleY);
            } else if (wasEmpty) {
                gc_.lineTo(x, height - (lowerBoundY) * scaleY);
                gc_.lineTo(x, y);
            } else {
                gc_.lineTo(x, y);
            }
            oldX = x;
            wasEmpty = isEmpty;
        }
        gc_.lineTo(oldX, height);
        gc_.lineTo((items.get(0).getX() - lowerBoundX) * scaleX, height);
        gc_.closePath();
        gc_.fill();

        // Draw Line
        oldX = (items.get(0).getX() - lowerBoundX) * scaleX;
        oldY = height - (items.get(0).getY() - lowerBoundY) * scaleY;
        for (T item : SERIES.getItems()) {
            double x = (item.getX() - lowerBoundX) * scaleX;
            double y = height - (item.getY() - lowerBoundY) * scaleY;
            boolean isEmpty = item.isEmptyItem();
            if (!isEmpty && !wasEmpty) {
                gc_.strokeLine(oldX, oldY, x, y);
            }
            oldX = x;
            oldY = y;
            wasEmpty = isEmpty;
        }

        if (SHOW_POINTS) {
            drawSymbols(SERIES);
        }
    }

    private void drawScatter(final XYSeries<T> series) {
        if (series == null || !series.isVisible() || series.getItems().isEmpty()) {
            return;
        }
        final double lowerBoundX = getLowerBoundX();
        final double lowerBoundY = getLowerBoundY();
        gc_.setStroke(Color.TRANSPARENT);
        gc_.setFill(Color.TRANSPARENT);

        Symbol seriesSymbol = series.getSymbol();
        Paint symbolFill = series.getSymbolFill();
        Paint symbolStroke = series.getSymbolStroke();
        double size = series.getSymbolSize() > -1 ? series.getSymbolSize() : symbolSize;

        for (T item : series.getItems()) {
            double x = (item.getX() - lowerBoundX) * scaleX;
            double y = height - (item.getY() - lowerBoundY) * scaleY;

            Symbol itemSymbol = item.getSymbol();
            if (itemSymbol == Symbol.NONE) {
                drawSymbol(x, y, symbolFill, symbolStroke, seriesSymbol, size);
            } else {
                drawSymbol(x, y, item.getFillColor(), item.getStrokeColor(), itemSymbol, size);
            }
        }
    }

    private void drawPoincare(final XYSeries<T> SERIES) {
        if (null == SERIES || !SERIES.isVisible() || SERIES.getItems().isEmpty()) {
            return;
        }
        gc_.setStroke(Color.TRANSPARENT);
        gc_.setFill(Color.TRANSPARENT);

        Symbol seriesSymbol = SERIES.getSymbol();
        Paint symbolFill = SERIES.getSymbolFill();
        Paint symbolStroke = SERIES.getSymbolStroke();
        double size = SERIES.getSymbolSize() > -1 ? SERIES.getSymbolSize() : symbolSize;

        for (int i = 0; i < SERIES.getItems().size() - 2; i++) {
            final T item = SERIES.getItems().get(i);
            final T nextItem = SERIES.getItems().get(i + 1);

            double x = item.getY() * scaleX;
            double y = (getUpperBoundY() - nextItem.getY() + getLowerBoundY()) * scaleY;

            Symbol itemSymbol = item.getSymbol();
            if (Symbol.NONE == itemSymbol) {
                drawSymbol(x, y, symbolFill, symbolStroke, seriesSymbol, size);
            } else {
                drawSymbol(x, y, item.getFillColor(), item.getStrokeColor(), itemSymbol, size);
            }
        }
    }

    private void drawSmoothLine(final XYSeries<T> SERIES, final boolean SHOW_POINTS) {
        if (null == SERIES || !SERIES.isVisible() || SERIES.getItems().isEmpty()) {
            return;
        }
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());
        gc_.setFill(Color.TRANSPARENT);

        List<Point> points = new ArrayList<>(SERIES.getItems().size());
        SERIES.getItems().forEach(item -> points.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));

        Point[] interpolatedPoints = Helper.subdividePoints(points.toArray(new Point[0]), SUB_DIVISIONS);

        gc_.beginPath();
        for (Point p : interpolatedPoints) {
            if (p.isEmpty()) {
                gc_.moveTo((p.getX() - LOWER_BOUND_X) * scaleX, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
            } else {
                gc_.lineTo((p.getX() - LOWER_BOUND_X) * scaleX, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
            }
        }
        gc_.stroke();

        if (SHOW_POINTS) {
            drawSymbols(SERIES);
        }
    }

    private void drawSmoothArea(final XYSeries<T> SERIES, final boolean SHOW_POINTS) {
        if (null == SERIES || !SERIES.isVisible() || SERIES.getItems().isEmpty()) {
            return;
        }
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();
        List<T> items = SERIES.getItems();
        double oldX = (items.get(0).getX() - LOWER_BOUND_X) * scaleX;
        double oldY = height - (items.get(0).getY() - LOWER_BOUND_Y) * scaleY;
        boolean wasEmpty = items.get(0).isEmptyItem();

        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());
        gc_.setFill(SERIES.getFill());

        List<Point> points = new ArrayList<>(items.size());
        items.forEach(item -> points.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));

        Point[] interpolatedPoints = Helper.subdividePoints(points.toArray(new Point[0]), SUB_DIVISIONS);

        gc_.beginPath();
        gc_.moveTo(oldX, oldY);
        for (Point p : interpolatedPoints) {
            double x = (p.getX() - LOWER_BOUND_X) * scaleX;
            double y = height - (p.getY() - LOWER_BOUND_Y) * scaleY;
            boolean isEmpty = p.isEmpty();

            if (isEmpty) {
                gc_.lineTo(oldX, height - (LOWER_BOUND_Y) * scaleY);
                gc_.lineTo(x, height - (LOWER_BOUND_Y) * scaleY);
            } else if (wasEmpty) {
                gc_.lineTo(x, height - (LOWER_BOUND_Y) * scaleY);
                gc_.lineTo(x, y);
            } else {
                gc_.lineTo(x, y);
            }

            oldX = x;
            wasEmpty = isEmpty;
        }

        gc_.lineTo(oldX, height);
        gc_.lineTo((items.get(0).getX() - LOWER_BOUND_X) * scaleX, height);
        gc_.closePath();
        gc_.fill();

        gc_.beginPath();
        for (Point p : interpolatedPoints) {
            if (p.isEmpty()) {
                gc_.moveTo((p.getX() - LOWER_BOUND_X) * scaleX, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
            } else {
                gc_.lineTo((p.getX() - LOWER_BOUND_X) * scaleX, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
            }
        }
        gc_.stroke();

        if (SHOW_POINTS) {
            drawSymbols(SERIES);
        }
    }

    private void drawHorizon(final XYSeries<T> SERIES, final boolean SMOOTHED) {
        if (null == SERIES || SERIES.getItems().isEmpty() || !SERIES.isVisible()) {
            return;
        }

        Color positiveBaseColor;
        Color negativeBaseColor;
        if (SERIES.getFill() instanceof Color) {
            positiveBaseColor = (Color) SERIES.getFill();
            if (positiveBaseColor.equals(Color.BLACK) ||
                    positiveBaseColor.equals(Color.WHITE) ||
                    positiveBaseColor.equals(Color.TRANSPARENT)) {
                positiveBaseColor = Color.BLUE;
                negativeBaseColor = Color.RED;
            } else {
                negativeBaseColor = ColorUtils.getComplementaryColor(positiveBaseColor);
            }
        } else {
            positiveBaseColor = Color.BLUE;
            negativeBaseColor = Color.RED;
        }

        // Create colors
        List<Color> aboveColors = Helper.createColorVariations(positiveBaseColor, noOfBands);
        List<Color> belowColors = Helper.createColorVariations(negativeBaseColor, noOfBands);

        int noOfItems = SERIES.getItems().size();

        // Create list of points
        List<Point> points = new ArrayList<>(noOfItems);
        for (int i = 0; i < noOfItems; i++) {
            points.add(new Point(i, SERIES.getItems().get(i).getY()));
        }

        double refValue = isReferenceZero() ? 0 : (points.isEmpty() ? 0 : points.get(0).getY());
        double minY = points.stream().mapToDouble(Point::getY).min().getAsDouble();
        double maxY = points.stream().mapToDouble(Point::getY).max().getAsDouble();
        double bandWidth = (maxY - minY) / noOfBands;

        scaleX = width / (noOfItems - 1);
        scaleY = height / ((maxY - minY) / (getNoOfBands()));

        // Normalize y values to 0
        points.forEach(point -> point.setY(point.getY() - refValue));

        // Subdivide points
        Point[] subdividedPoints;
        if (SMOOTHED) {
            subdividedPoints = Helper.subdividePoints(points.toArray(new Point[0]), SUB_DIVISIONS);
        } else {
            subdividedPoints = Helper.subdividePointsLinear(points.toArray(new Point[0]), SUB_DIVISIONS);
        }

        // Split in points above and below 0
        List<Point>[] splittedPoints = splitIntoAboveAndBelow(Arrays.asList(subdividedPoints));
        List<Point> aboveRefPoints = splittedPoints[0];
        List<Point> belowRefPoints = splittedPoints[1];

        // Split points above and below 0 into noOfBands
        Map<Integer, List<Point>> aboveRefPointsSplitToBands = splitIntoBands(aboveRefPoints, bandWidth);
        Map<Integer, List<Point>> belowRefPointsSplitToBands = splitIntoBands(belowRefPoints, bandWidth);

        // Draw values above 0
        if (!aboveRefPoints.isEmpty()) {
            drawPath(aboveRefPointsSplitToBands, bandWidth, aboveColors);
        }

        // Draw values below 0
        if (!belowRefPoints.isEmpty()) {
            drawPath(belowRefPointsSplitToBands, bandWidth, belowColors);
        }
    }

    private void drawRidgeLine(final XYSeries<T> SERIES) {
        if (null == SERIES || SERIES.getItems().isEmpty() || !SERIES.isVisible()) {
            return;
        }
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY() - SERIES.getStrokeWidth();
        List<T> items = SERIES.getItems();
        double oldX = (items.get(0).getX() - LOWER_BOUND_X) * scaleX;
        double oldY = height - (items.get(0).getY() - LOWER_BOUND_Y) * scaleY;

        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());
        gc_.setFill(SERIES.getFill());

        List<Point> points = new ArrayList<>(items.size());
        items.forEach(item -> points.add(new Point(item.getX(), item.getY())));

        Point[] interpolatedPoints = Helper.subdividePoints(points.toArray(new Point[0]), SUB_DIVISIONS);

        gc_.beginPath();
        gc_.moveTo(oldX, oldY);
        for (Point p : interpolatedPoints) {
            double x = (p.getX() - LOWER_BOUND_X) * scaleX;
            gc_.lineTo(x, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
            oldX = x;
        }

        gc_.lineTo(oldX, height);
        gc_.lineTo((items.get(0).getX() - LOWER_BOUND_X) * scaleX, height);
        gc_.closePath();
        gc_.fill();

        gc_.beginPath();
        for (Point p : interpolatedPoints) {
            gc_.lineTo((p.getX() - LOWER_BOUND_X) * scaleX, height - (p.getY() - LOWER_BOUND_Y) * scaleY);
        }
        gc_.stroke();
    }

    private void drawLineDelta(final XYSeries<T> series1, final XYSeries<T> series2) {
        if (series1 == null || series1.getItems().isEmpty() || !series1.isVisible() ||
                series2 == null || series2.getItems().isEmpty() || !series2.isVisible()) {
            return;
        }
        if (series1.getItems().size() != series2.getItems().size()) {
            throw new IllegalArgumentException("Both series must have the same number of items!");
        }

        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        int noOfItems = series1.getItems().size();
        List<T> cachedItems = new LinkedList<>();
        Point lastPointForClose = new Point();

        T series1Item0 = series1.getItems().get(0);
        T series2Item0 = series2.getItems().get(0);
        int currentSeries = series1Item0.getY() > series2Item0.getY() ? 1 : 2;

        Paint series1Stroke = series1.getStroke();
        Paint series1Fill = series1.getFill();

        Paint series2Stroke = series2.getStroke();
        Paint series2Fill = series2.getFill();

        // Start path
        gc_.setLineWidth(size * 0.0025);
        gc_.beginPath();
        switch (currentSeries) {
            case 1:
                gc_.moveTo((series1Item0.getX() - LOWER_BOUND_X) * scaleX, height - (series1Item0.getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(series2Item0.getX(), series2Item0.getY());
                break;
            case 2:
                gc_.moveTo((series2Item0.getX() - LOWER_BOUND_X) * scaleX, height - (series2Item0.getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(series1Item0.getX(), series1Item0.getY());
                break;
            default:
                gc_.moveTo((series1Item0.getX() - LOWER_BOUND_X) * scaleX, height - (series1Item0.getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(series2Item0.getX(), series2Item0.getY());
                break;
        }
        // Draw path
        List<T> items1 = series1.getItems();
        List<T> items2 = series2.getItems();
        for (int i = 1; i < noOfItems; i++) {
            T lastXyData1 = items1.get(i - 1);
            T lastXyData2 = items2.get(i - 1);

            T xyData1 = items1.get(i);
            T xyData2 = items2.get(i);

            if (lastXyData1.getY() > lastXyData2.getY() && xyData1.getY() < xyData2.getY()) {
                // Lines crossed Line1 is now below lower Line2
                Point intersectionPoint = Helper.calcIntersectionOfTwoLines(lastXyData1.getX(), lastXyData1.getY(), xyData1.getX(), xyData1.getY(),
                        lastXyData2.getX(), lastXyData2.getY(), xyData2.getX(), xyData2.getY());
                gc_.lineTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);

                Collections.reverse(cachedItems);
                for (T item : cachedItems) {
                    gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
                }
                gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.closePath();
                gc_.setFill(series1Fill);
                gc_.fill();
                cachedItems.clear();

                gc_.beginPath();
                gc_.moveTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.lineTo((xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
                currentSeries = 2;
                cachedItems.add(xyData1);
                lastPointForClose.set(intersectionPoint.getX(), intersectionPoint.getY());
            } else if (lastXyData1.getY() < lastXyData2.getY() && xyData1.getY() > xyData2.getY()) {
                // Lines crossed and Line1 is now above Line2
                Point intersectionPoint = Helper.calcIntersectionOfTwoLines(lastXyData1.getX(), lastXyData1.getY(), xyData1.getX(), xyData1.getY(),
                        lastXyData2.getX(), lastXyData2.getY(), xyData2.getX(), xyData2.getY());
                gc_.lineTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);

                Collections.reverse(cachedItems);
                for (T item : cachedItems) {
                    gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
                }
                gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.closePath();
                gc_.setFill(series2Fill);
                gc_.fill();
                cachedItems.clear();

                gc_.beginPath();
                gc_.moveTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.lineTo((xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);
                currentSeries = 1;
                cachedItems.add(xyData2);
                lastPointForClose.set(intersectionPoint.getX(), intersectionPoint.getY());
            } else {
                // Lines did not cross
                switch (currentSeries) {
                    case 1:
                        gc_.lineTo((xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);
                        cachedItems.add(xyData2);
                        break;
                    case 2:
                        gc_.lineTo((xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
                        cachedItems.add(xyData1);
                        break;
                }
            }

            gc_.setLineWidth(series1.getStrokeWidth() > -1 ? series1.getStrokeWidth() : size * 0.0025);
            gc_.setStroke(series1Stroke);
            gc_.strokeLine((lastXyData1.getX() - LOWER_BOUND_X) * scaleX, height - (lastXyData1.getY() - LOWER_BOUND_Y) * scaleY, (xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);

            gc_.setLineWidth(series2.getStrokeWidth() > -1 ? series2.getStrokeWidth() : size * 0.0025);
            gc_.setStroke(series2Stroke);
            gc_.strokeLine((lastXyData2.getX() - LOWER_BOUND_X) * scaleX, height - (lastXyData2.getY() - LOWER_BOUND_Y) * scaleY, (xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
        }
        Collections.reverse(cachedItems);
        for (T item : cachedItems) {
            gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
        }
        gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
        gc_.closePath();
        switch (currentSeries) {
            case 1:
                gc_.setFill(series1Fill);
                break;
            case 2:
                gc_.setFill(series2Fill);
                break;
        }
        gc_.fill();
        cachedItems.clear();


        if (series1.getSymbolsVisible()) {
            drawSymbols(series1);
        }
        if (series2.getSymbolsVisible()) {
            drawSymbols(series2);
        }
    }

    private void drawSmoothLineDelta(final XYSeries<T> SERIES_1, final XYSeries<T> SERIES_2) {
        if (null == SERIES_1 || SERIES_1.getItems().isEmpty() || !SERIES_1.isVisible() ||
                null == SERIES_2 || SERIES_2.getItems().isEmpty() || !SERIES_2.isVisible()) {
            return;
        }
        if (SERIES_1.getItems().size() != SERIES_2.getItems().size()) {
            throw new IllegalArgumentException("Both series must have the same number of items!");
        }
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        // Smooth series
        List<Point> points1 = new ArrayList<>(SERIES_1.getItems().size());
        SERIES_1.getItems().forEach(item -> points1.add(new Point(item.getX(), item.getY())));
        Point[] interpolatedPoints1 = Helper.subdividePoints(points1.toArray(new Point[0]), SUB_DIVISIONS);

        List<Point> points2 = new ArrayList<>(SERIES_2.getItems().size());
        SERIES_2.getItems().forEach(item -> points2.add(new Point(item.getX(), item.getY())));
        Point[] interpolatedPoints2 = Helper.subdividePoints(points2.toArray(new Point[0]), SUB_DIVISIONS);

        int noOfItems = interpolatedPoints1.length;
        List<Point> cachedItems = new LinkedList<>();
        Point lastPointForClose = new Point();

        T series1Item0 = SERIES_1.getItems().get(0);
        T series2Item0 = SERIES_2.getItems().get(0);
        int currentSeries = series1Item0.getY() > series2Item0.getY() ? 1 : 2;

        Paint series1Stroke = SERIES_1.getStroke();
        Paint series1Fill = SERIES_1.getFill();

        Paint series2Stroke = SERIES_2.getStroke();
        Paint series2Fill = SERIES_2.getFill();

        // Start path
        gc_.setLineWidth(size * 0.0025);
        gc_.beginPath();
        switch (currentSeries) {
            case 1:
                gc_.moveTo((interpolatedPoints1[0].getX() - LOWER_BOUND_X) * scaleX, height - (interpolatedPoints1[0].getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(interpolatedPoints2[0].getX(), interpolatedPoints2[0].getY());
                break;
            case 2:
                gc_.moveTo((interpolatedPoints2[0].getX() - LOWER_BOUND_X) * scaleX, height - (interpolatedPoints2[0].getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(interpolatedPoints1[0].getX(), interpolatedPoints1[0].getY());
                break;
            default:
                gc_.moveTo((interpolatedPoints1[0].getX() - LOWER_BOUND_X) * scaleX, height - (interpolatedPoints1[0].getY() - LOWER_BOUND_Y) * scaleY);
                lastPointForClose.set(interpolatedPoints2[0].getX(), interpolatedPoints2[0].getY());
                break;
        }
        // Draw path
        for (int i = 1; i < noOfItems; i++) {
            Point lastXyData1 = interpolatedPoints1[i - 1];
            Point lastXyData2 = interpolatedPoints2[i - 1];

            Point xyData1 = interpolatedPoints1[i];
            Point xyData2 = interpolatedPoints2[i];

            if (lastXyData1.getY() > lastXyData2.getY() && xyData1.getY() < xyData2.getY()) {
                // Lines crossed Line1 is now below lower Line2
                Point intersectionPoint = Helper.calcIntersectionOfTwoLines(lastXyData1.getX(), lastXyData1.getY(), xyData1.getX(), xyData1.getY(),
                        lastXyData2.getX(), lastXyData2.getY(), xyData2.getX(), xyData2.getY());
                gc_.lineTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);

                Collections.reverse(cachedItems);
                for (Point item : cachedItems) {
                    gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
                }
                gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.closePath();
                gc_.setFill(series1Fill);
                gc_.fill();
                cachedItems.clear();

                gc_.beginPath();
                gc_.moveTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.lineTo((xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
                currentSeries = 2;
                cachedItems.add(xyData1);
                lastPointForClose.set(intersectionPoint.getX(), intersectionPoint.getY());
            } else if (lastXyData1.getY() < lastXyData2.getY() && xyData1.getY() > xyData2.getY()) {
                // Lines crossed and Line1 is now above Line2
                Point intersectionPoint = Helper.calcIntersectionOfTwoLines(lastXyData1.getX(), lastXyData1.getY(), xyData1.getX(), xyData1.getY(),
                        lastXyData2.getX(), lastXyData2.getY(), xyData2.getX(), xyData2.getY());
                gc_.lineTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);

                Collections.reverse(cachedItems);
                for (Point item : cachedItems) {
                    gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
                }
                gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.closePath();
                gc_.setFill(series2Fill);
                gc_.fill();
                cachedItems.clear();

                gc_.beginPath();
                gc_.moveTo((intersectionPoint.getX() - LOWER_BOUND_X) * scaleX, height - (intersectionPoint.getY() - LOWER_BOUND_Y) * scaleY);
                gc_.lineTo((xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);
                currentSeries = 1;
                cachedItems.add(xyData2);
                lastPointForClose.set(intersectionPoint.getX(), intersectionPoint.getY());
            } else {
                // Lines did not cross
                switch (currentSeries) {
                    case 1:
                        gc_.lineTo((xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);
                        cachedItems.add(xyData2);
                        break;
                    case 2:
                        gc_.lineTo((xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
                        cachedItems.add(xyData1);
                        break;
                }
            }

            gc_.setLineWidth(SERIES_1.getStrokeWidth() > -1 ? SERIES_1.getStrokeWidth() : size * 0.0025);
            gc_.setStroke(series1Stroke);
            gc_.strokeLine((lastXyData1.getX() - LOWER_BOUND_X) * scaleX, height - (lastXyData1.getY() - LOWER_BOUND_Y) * scaleY, (xyData1.getX() - LOWER_BOUND_X) * scaleX, height - (xyData1.getY() - LOWER_BOUND_Y) * scaleY);

            gc_.setLineWidth(SERIES_2.getStrokeWidth() > -1 ? SERIES_2.getStrokeWidth() : size * 0.0025);
            gc_.setStroke(series2Stroke);
            gc_.strokeLine((lastXyData2.getX() - LOWER_BOUND_X) * scaleX, height - (lastXyData2.getY() - LOWER_BOUND_Y) * scaleY, (xyData2.getX() - LOWER_BOUND_X) * scaleX, height - (xyData2.getY() - LOWER_BOUND_Y) * scaleY);
        }
        Collections.reverse(cachedItems);
        for (Point item : cachedItems) {
            gc_.lineTo((item.getX() - LOWER_BOUND_X) * scaleX, height - (item.getY() - LOWER_BOUND_Y) * scaleY);
        }
        gc_.lineTo((lastPointForClose.getX() - LOWER_BOUND_X) * scaleX, height - (lastPointForClose.getY() - LOWER_BOUND_Y) * scaleY);
        gc_.closePath();
        switch (currentSeries) {
            case 1:
                gc_.setFill(series1Fill);
                break;
            case 2:
                gc_.setFill(series2Fill);
                break;
        }
        gc_.fill();
        cachedItems.clear();


        if (SERIES_1.getSymbolsVisible()) {
            drawSymbols(SERIES_1);
        }
        if (SERIES_2.getSymbolsVisible()) {
            drawSymbols(SERIES_2);
        }
    }

    private void drawPolar(final XYSeries<T> SERIES) {
        if (null == SERIES || SERIES.getItems().isEmpty() || !SERIES.isVisible()) {
            return;
        }
        final double CENTER_X = 0.5 * size;
        final double CENTER_Y = CENTER_X;
        final double CIRCLE_SIZE = 0.9 * size;
        final double LOWER_BOUND_Y = getLowerBoundY() - SERIES.getStrokeWidth();
        final double DATA_RANGE = getRangeY();
        final double RANGE = 0.35714 * CIRCLE_SIZE;
        final double OFFSET = 0.14286 * CIRCLE_SIZE;
        final int NO_OF_ITEMS = SERIES.getItems().size();
        final boolean SHOW_POINTS = SERIES.getSymbolsVisible();

        drawPolarOverlay(getPolarTickStep().get());

        // draw the chart data
        gc_.save();
        if (SERIES.getFill() instanceof RadialGradient) {
            gc_.setFill(new RadialGradient(0, 0, size * 0.5, size * 0.5, size * 0.45, false, CycleMethod.NO_CYCLE, ((RadialGradient) SERIES.getFill()).getStops()));
        } else {
            gc_.setFill(SERIES.getFill());
        }
        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());

        double radAngle = Math.toRadians(180);
        Point[] points = new Point[NO_OF_ITEMS + 1];

        T item = SERIES.getItems().get(0);
        double r1 = (CENTER_Y - (CENTER_Y - OFFSET - ((item.getY() - LOWER_BOUND_Y) / DATA_RANGE) * RANGE));
        double phi = Math.toRadians(Math.clamp(item.getX(), 0.0, 360.0));
        double x = CENTER_X + (-Math.sin(radAngle + phi) * r1);
        double y = CENTER_Y + (+Math.cos(radAngle + phi) * r1);
        points[0] = new Point(x, y);

        for (int i = 1; i < NO_OF_ITEMS; i++) {
            item = SERIES.getItems().get(i);
            r1 = (CENTER_Y - (CENTER_Y - OFFSET - ((item.getY() - LOWER_BOUND_Y) / DATA_RANGE) * RANGE));
            phi = Math.toRadians(Math.clamp(item.getX(), 0.0, 360.0));
            x = CENTER_X + (-Math.sin(radAngle + phi) * r1);
            y = CENTER_Y + (+Math.cos(radAngle + phi) * r1);
            points[i] = new Point(x, y);
        }
        points[points.length - 1] = points[0]; // last point == first point

        if (SMOOTH_POLAR == SERIES.getChartType()) {
            //Use the subdividePointsRadial method if wrapping required.
            Point[] interpolatedPoints = SERIES.isWithWrapping() ? Helper.subdividePointsRadial(points, 16) : Helper.subdividePoints(points, 16);
            gc_.beginPath();
            gc_.moveTo(interpolatedPoints[0].getX(), interpolatedPoints[0].getY());
            for (int i = 0; i < interpolatedPoints.length - 1; i++) {
                Point point = interpolatedPoints[i];
                gc_.lineTo(point.getX(), point.getY());
            }
            gc_.lineTo(interpolatedPoints[interpolatedPoints.length - 1].getX(), interpolatedPoints[interpolatedPoints.length - 1].getY());
            gc_.closePath();
        } else {
            gc_.beginPath();
            gc_.moveTo(points[0].getX(), points[0].getY());
            for (int i = 0; i < points.length - 1; i++) {
                Point point = points[i];
                gc_.lineTo(point.getX(), point.getY());
            }
            gc_.lineTo(points[points.length - 1].getX(), points[points.length - 1].getY());
            gc_.closePath();
        }

        gc_.fill();
        gc_.stroke();

        gc_.restore();

        if (SHOW_POINTS) {
            Symbol seriesSymbol = SERIES.getSymbol();
            Paint symbolFill = SERIES.getSymbolFill();
            Paint symbolStroke = SERIES.getSymbolStroke();
            double size = SERIES.getSymbolSize() > -1 ? SERIES.getSymbolSize() : symbolSize;
            for (Point point : points) {
                Symbol itemSymbol = item.getSymbol();
                if (Symbol.NONE == itemSymbol) {
                    drawSymbol(point.getX(), point.getY(), symbolFill, symbolStroke, seriesSymbol, size);
                } else {
                    drawSymbol(point.getX(), point.getY(), item.getFillColor(), item.getStrokeColor(), itemSymbol, size);
                }
            }
        }

    }

    private void drawPolarOverlay(final double ANGLE_STEP) {
        final double CENTER_X = 0.5 * size;
        final double CENTER_Y = CENTER_X;
        final double CIRCLE_SIZE = 0.90 * size;
        final double DATA_RANGE = getRangeY();
        final double MIN_VALUE = getDataMinY();
        final double RANGE = 0.35714 * CIRCLE_SIZE;
        final double OFFSET = 0.14286 * CIRCLE_SIZE;
        final double NO_OF_SECTORS = 360.0 / ANGLE_STEP;

        // draw concentric rings
        gc_.setLineWidth(1);
        gc_.setStroke(Color.GRAY);

        double ringStepSize = size / 20.0;
        double pos = 0.5 * (size - CIRCLE_SIZE);
        double ringSize = CIRCLE_SIZE;
        for (int i = 0; i < 11; i++) {
            gc_.strokeOval(pos, pos, ringSize, ringSize);
            pos += ringStepSize;
            ringSize -= 2 * ringStepSize;
        }

        // draw star lines
        gc_.save();
        for (int i = 0; i < NO_OF_SECTORS; i++) {
            gc_.strokeLine(CENTER_X, 0.05 * size, CENTER_X, 0.5 * size);
            Helper.rotateCtx(gc_, CENTER_X, CENTER_Y, ANGLE_STEP);
        }
        gc_.restore();

        // draw threshold line
        if (isThresholdYVisible()) {
            double r = ((getThresholdY() - MIN_VALUE) / DATA_RANGE);
            gc_.setLineWidth(Math.clamp(size * 0.005, 1d, 3d));
            gc_.setStroke(getThresholdYColor());
            gc_.strokeOval(0.5 * size - OFFSET - r * RANGE, 0.5 * size - OFFSET - r * RANGE,
                    2 * (r * RANGE + OFFSET), 2 * (r * RANGE + OFFSET));
        }

        gc_.setTextAlign(TextAlignment.CENTER);
        gc_.setTextBaseline(VPos.CENTER);
        gc_.setFill(getForegroundColor());

        // draw min and max Text
        Font font = Fonts.latoRegular(0.025 * size);
        String minValueText = String.format(Locale.US, "%.0f", getLowerBoundY());
        String maxValueText = String.format(Locale.US, "%.0f", getUpperBoundY());
        gc_.save();
        gc_.setFont(font);
        Helper.drawTextWithBackground(gc_, minValueText, font, Color.WHITE, Color.BLACK, CENTER_X, CENTER_Y - size * 0.018);
        Helper.drawTextWithBackground(gc_, maxValueText, font, Color.WHITE, Color.BLACK, CENTER_X, CENTER_Y - CIRCLE_SIZE * 0.48);
        gc_.restore();

        // draw axis text
        gc_.save();
        gc_.setFont(Fonts.latoRegular(0.04 * size));
        for (int i = 0; i < NO_OF_SECTORS; i++) {
            if (isCategoryTextVisible()) {
                gc_.fillText(categories_.get(i), CENTER_X, size * 0.02);
            } else {
                gc_.fillText(String.format(Locale.US, "%.0f", i * ANGLE_STEP), CENTER_X, size * 0.02);
            }
            Helper.rotateCtx(gc_, CENTER_X, CENTER_Y, ANGLE_STEP);
        }
        gc_.restore();
    }

    private void drawSpider(final XYSeries<T> SERIES) {
        if (null == SERIES || SERIES.getItems().isEmpty() || !SERIES.isVisible()) {
            return;
        }
        final double centerX = 0.5 * size;
        final double centerY = centerX;
        final double circleSize = 0.9 * size;
        final double lowerBoundY = 0;
        final double dataRangeY = 100;
        final double range = 0.35714 * circleSize;
        final double offset = 0.14286 * circleSize;
        final int noOfItems = SERIES.getItems().size();
        final boolean showPoints = SERIES.getSymbolsVisible();
        final double angleStep = getPolarTickStep().getAngleStep();

        drawSpiderOverlay(getPolarTickStep().get());

        // draw the chart data
        gc_.save();
        if (SERIES.getFill() instanceof RadialGradient) {
            gc_.setFill(new RadialGradient(0, 0, size * 0.5, size * 0.5, size * 0.45, false, CycleMethod.NO_CYCLE, ((RadialGradient) SERIES.getFill()).getStops()));
        } else {
            gc_.setFill(SERIES.getFill());
        }
        gc_.setLineWidth(SERIES.getStrokeWidth() > -1 ? SERIES.getStrokeWidth() : size * 0.0025);
        gc_.setStroke(SERIES.getStroke());

        double radAngle = Math.toRadians(180);
        Point[] points = new Point[noOfItems + 1];

        T item = SERIES.getItems().get(0);
        double r1 = (centerY - (centerY - offset - ((item.getY() - lowerBoundY) / dataRangeY) * range));
        double phi = Math.toRadians(Math.clamp(item.getX(), 0.0, 360.0));
        double x = centerX + (-Math.sin(radAngle + phi) * r1);
        double y = centerY + (+Math.cos(radAngle + phi) * r1);
        points[0] = new Point(x, y);
        double angle = 0;

        for (int i = 0; i < noOfItems; i++) {
            item = SERIES.getItems().get(i);
            r1 = (centerY - (centerY - offset - ((item.getY() - lowerBoundY) / dataRangeY) * range));
            //phi  = Math.toRadians(Helper.clamp(0.0, 360.0, item.getX()));
            phi = Math.toRadians(Math.clamp(angle, 0.0, 360.0));
            x = centerX + (-Math.sin(radAngle + phi) * r1);
            y = centerY + (+Math.cos(radAngle + phi) * r1);
            points[i] = new Point(x, y);
            angle += angleStep;
        }
        points[points.length - 1] = points[0]; // last point == first point

        gc_.beginPath();
        gc_.moveTo(points[0].getX(), points[0].getY());
        for (int i = 0; i < points.length - 1; i++) {
            Point point = points[i];
            gc_.lineTo(point.getX(), point.getY());
        }
        gc_.lineTo(points[points.length - 1].getX(), points[points.length - 1].getY());
        gc_.closePath();

        gc_.fill();
        gc_.stroke();

        gc_.restore();

        if (showPoints) {
            Symbol seriesSymbol = SERIES.getSymbol();
            Paint symbolFill = SERIES.getSymbolFill();
            Paint symbolStroke = SERIES.getSymbolStroke();
            double size = SERIES.getSymbolSize() > -1 ? SERIES.getSymbolSize() : symbolSize;
            for (Point point : points) {
                Symbol itemSymbol = item.getSymbol();
                if (Symbol.NONE == itemSymbol) {
                    drawSymbol(point.getX(), point.getY(), symbolFill, symbolStroke, seriesSymbol, size);
                } else {
                    drawSymbol(point.getX(), point.getY(), item.getFillColor(), item.getStrokeColor(), itemSymbol, size);
                }
            }
        }

    }

    private void drawSpiderOverlay(final double ANGLE_STEP) {
        final double CENTER_X = 0.5 * size;
        final double CENTER_Y = CENTER_X;
        final double CIRCLE_SIZE = 0.90 * size;
        final double DATA_RANGE = getRangeY();
        final double MIN_VALUE = getDataMinY();
        final double RANGE = 0.35714 * CIRCLE_SIZE;
        final double OFFSET = 0.14286 * CIRCLE_SIZE;
        final double NO_OF_SECTORS = 360.0 / ANGLE_STEP;

        // draw scale
        gc_.setLineWidth(1);
        gc_.setStroke(Color.GRAY);

        final double radAngle = Math.toRadians(180);
        final double rangeY = getUpperBoundY() - getLowerBoundY();
        final double spacing = getDataRangeY() / 5;
        double lastX = CENTER_X;
        double lastY = CENTER_Y;
        for (int yr = 0; yr < 6; yr++) {
            for (int i = 0; i < NO_OF_SECTORS + 1; i++) {
                double phi = Math.toRadians(Math.clamp(i * getPolarTickStep().get(), 0.0, 360.0));
                double r1 = (CENTER_Y - (CENTER_Y - OFFSET - ((rangeY - yr * spacing) / DATA_RANGE) * RANGE));
                double x = CENTER_X + (-Math.sin(radAngle + phi) * r1);
                double y = CENTER_Y + (+Math.cos(radAngle + phi) * r1);
                gc_.strokeLine(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
            }
        }

        // draw star lines
        gc_.save();
        for (int i = 0; i < NO_OF_SECTORS; i++) {
            gc_.strokeLine(CENTER_X, 0.05 * size, CENTER_X, 0.5 * size);
            Helper.rotateCtx(gc_, CENTER_X, CENTER_Y, ANGLE_STEP);
        }
        gc_.restore();

        // draw threshold line
        if (isThresholdYVisible()) {
            double r = ((getThresholdY() - MIN_VALUE) / DATA_RANGE);
            gc_.setLineWidth(Math.clamp(size * 0.005, 1d, 3d));
            gc_.setStroke(getThresholdYColor());
            gc_.strokeOval(0.5 * size - OFFSET - r * RANGE, 0.5 * size - OFFSET - r * RANGE,
                    2 * (r * RANGE + OFFSET), 2 * (r * RANGE + OFFSET));
        }

        gc_.setTextAlign(TextAlignment.CENTER);
        gc_.setTextBaseline(VPos.CENTER);
        gc_.setFill(getForegroundColor());

        /* draw min and max Text
        Font   font         = Fonts.latoRegular(0.025 * size);
        String minValueText = String.format(Locale.US, "%.0f", getLowerBoundY());
        String maxValueText = String.format(Locale.US, "%.0f", getUpperBoundY());
        ctx.save();
        ctx.setFont(font);
        Helper.drawTextWithBackground(ctx, minValueText, font, Color.WHITE, Color.BLACK, CENTER_X, CENTER_Y - size * 0.018);
        Helper.drawTextWithBackground(ctx, maxValueText, font, Color.WHITE, Color.BLACK, CENTER_X, CENTER_Y - CIRCLE_SIZE * 0.48);
        ctx.restore();
        */

        // draw axis text
        gc_.save();
        gc_.setFont(Fonts.latoRegular(0.04 * size));
        for (int i = 0; i < NO_OF_SECTORS; i++) {
            if (isCategoryTextVisible()) {
                gc_.fillText(categories_.get(i), CENTER_X, size * 0.02);
            } else {
                gc_.fillText(String.format(Locale.US, "%.0f", i * ANGLE_STEP), CENTER_X, size * 0.02);
            }
            Helper.rotateCtx(gc_, CENTER_X, CENTER_Y, ANGLE_STEP);
        }
        gc_.restore();
    }

    private void drawPath(final Map<Integer, List<Point>> MAP_OF_BANDS, final double BAND_WIDTH, final List<Color> COLORS) {
        double oldX = 0;
        for (int band = 0; band < getNoOfBands(); band++) {
            gc_.beginPath();
            for (Point p : MAP_OF_BANDS.get(band)) {
                double x = p.getX() * scaleX;
                double y = height - (p.getY() * scaleY);
                gc_.lineTo(x, y + (band * BAND_WIDTH) * scaleY);
                oldX = x;
            }
            gc_.lineTo(oldX, height);
            gc_.lineTo(0, height);
            gc_.closePath();
            gc_.setFill(COLORS.get(band));
            gc_.fill();
        }
    }

    private void drawPredictionOverlay(final XYPaneOverlay overlay) {
        if (null == this.listOfSeries || this.listOfSeries.isEmpty()) {
            return;
        }
        final Prediction prediction = (Prediction) overlay;
        // Aggregating data
        List<XYItem> predictionItems = prediction.getPredictionSeries().getItems();
        XYSeries<T> targetSeries = this.listOfSeries.get(0);
        if (null == targetSeries.getItems() || targetSeries.getItems().isEmpty()) {
            return;
        }

        // Prediction
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        double oldX = (predictionItems.get(0).getX() - LOWER_BOUND_X) * scaleX;
        double oldY = height - (predictionItems.get(0).getY() - LOWER_BOUND_Y) * scaleY;
        boolean wasEmpty = predictionItems.get(0).isEmptyItem();

        gc_.setLineWidth(prediction.getPredictionSeries().getStrokeWidth() > -1 ? prediction.getPredictionSeries().getStrokeWidth() : size * 0.0025);
        gc_.setStroke(prediction.getPredictionSeries().getStroke());
        gc_.setFill(Color.TRANSPARENT);

        for (XYItem item : predictionItems) {
            double x = (item.getX() - LOWER_BOUND_X) * scaleX;
            double y = height - (item.getY() - LOWER_BOUND_Y) * scaleY;
            boolean isEmpty = item.isEmptyItem();
            if (!isEmpty && !wasEmpty) {
                gc_.strokeLine(oldX, oldY, x, y);
            }
            oldX = x;
            oldY = y;
            wasEmpty = isEmpty;
        }

        // Quantile 50
        gc_.setLineWidth(prediction.getQuantile50Series().getStrokeWidth());
        gc_.setStroke(prediction.getQuantile50Series().getStroke());
        gc_.setFill(prediction.getQuantile50Series().getFill());

        List<Point> quantile50Points = prediction.getQuantile50Points();
        gc_.beginPath();
        gc_.moveTo((quantile50Points.get(0).getX() - LOWER_BOUND_X) * scaleX, height - (quantile50Points.get(0).getY() - LOWER_BOUND_Y) * scaleY);
        for (int i = 1; i < quantile50Points.size(); i++) {
            Point p = quantile50Points.get(i);
            double x = (p.x - LOWER_BOUND_X) * scaleX;
            double y = height - (p.y - LOWER_BOUND_Y) * scaleY;
            gc_.lineTo(x, y);
        }
        gc_.closePath();
        gc_.fill();

        // Quantile 90
        gc_.setLineWidth(prediction.getQuantile90Series().getStrokeWidth());
        gc_.setStroke(prediction.getQuantile90Series().getStroke());
        gc_.setFill(prediction.getQuantile90Series().getFill());

        List<Point> quantile90Points = prediction.getQuantile90Points();
        gc_.beginPath();
        gc_.moveTo((quantile90Points.get(0).getX() - LOWER_BOUND_X) * scaleX, height - (quantile90Points.get(0).getY() - LOWER_BOUND_Y) * scaleY);
        for (int i = 1; i < quantile90Points.size(); i++) {
            Point p = quantile90Points.get(i);
            double x = (p.x - LOWER_BOUND_X) * scaleX;
            double y = height - (p.y - LOWER_BOUND_Y) * scaleY;
            gc_.lineTo(x, y);
        }
        gc_.closePath();
        gc_.fill();
    }

    private void drawMultiTimeSeries(final List<XYSeries<T>> LIST_OF_SERIES) {
        if (LIST_OF_SERIES.isEmpty()) {
            return;
        }
        // Aggregating data
        List<XYItem> minItems = new LinkedList<>();
        List<XYItem> maxItems = new LinkedList<>();
        List<XYItem> avgItems = new LinkedList<>();
        List<XYItem> stdDevItems = new LinkedList<>();
        XYSeries<T> series0 = LIST_OF_SERIES.get(0);
        if (null == series0.getItems() || series0.getItems().isEmpty()) {
            return;
        }
        for (int i = 0; i < series0.getItems().size(); i++) {
            T item = series0.getItems().get(i);
            double x = item.getX();
            double minYForX = Double.MAX_VALUE;
            double maxYForX = Double.MIN_VALUE;
            List<Double> valuesForX = new LinkedList<>();
            for (int j = 0; j < LIST_OF_SERIES.size(); j++) {
                XYSeries<T> series = LIST_OF_SERIES.get(j);
                if (null == series.getItems() || series.getItems().isEmpty()) {
                    continue;
                }
                Optional<T> optionalValue = series.getItems().stream().filter(si -> Double.compare(x, si.getX()) == 0).findFirst();
                if (optionalValue.isPresent()) {
                    minYForX = Math.min(minYForX, optionalValue.get().getY());
                    maxYForX = Math.max(maxYForX, optionalValue.get().getY());
                    valuesForX.add(optionalValue.get().getY());
                }
            }
            minItems.add(new XYChartItem(x, minYForX));
            maxItems.add(new XYChartItem(x, maxYForX));
            avgItems.add(new XYChartItem(x, StatUtils.mean(valuesForX)));
            stdDevItems.add(new XYChartItem(x, StatUtils.standardDeviation(valuesForX)));
        }

        // Visualize data
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        double startX = (maxItems.get(0).getX() - LOWER_BOUND_X) * scaleX;
        double startY = height - (maxItems.get(0).getY() - LOWER_BOUND_Y) * scaleY;

        if (isEnvelopeVisible()) {
            gc_.setFill(getEnvelopeFill());
            gc_.setStroke(getEnvelopeStroke());
            gc_.setLineWidth(0.5);
            gc_.beginPath();
            gc_.moveTo(startX, startY);
            for (int i = 1; i < maxItems.size(); i++) {
                XYItem item = maxItems.get(i);
                double x = (item.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (item.getY() - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            for (int i = minItems.size() - 1; i >= 0; i--) {
                XYItem item = minItems.get(i);
                double x = (item.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (item.getY() - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            gc_.lineTo(startX, startY);
            gc_.closePath();
            gc_.fill();
            gc_.stroke();
        }

        for (XYSeries<T> SERIES : LIST_OF_SERIES) {
            if (!SERIES.isVisible()) {
                continue;
            }
            if (SERIES.getSymbolsVisible()) {
                drawSymbols(SERIES);
            }
        }

        double oldX;
        double oldY;

        if (isStdDeviationVisible()) {
            // Std. Deviation area
            gc_.setFill(getStdDeviationFill());
            gc_.setStroke(getStdDeviationStroke());
            gc_.setLineWidth(0.5);
            gc_.beginPath();
            startX = (stdDevItems.get(0).getX() - LOWER_BOUND_X) * scaleX;
            startY = height - (avgItems.get(0).getY() - stdDevItems.get(0).getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
            gc_.moveTo(startX, startY);
            for (int i = 0; i < stdDevItems.size(); i++) {
                XYItem stdItem = stdDevItems.get(i);
                XYItem avgItem = avgItems.get(i);
                double x = (avgItem.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (avgItem.getY() - stdItem.getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            for (int i = stdDevItems.size() - 1; i >= 0; i--) {
                XYItem stdItem = stdDevItems.get(i);
                XYItem avgItem = avgItems.get(i);
                double x = (avgItem.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (avgItem.getY() + stdItem.getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            gc_.lineTo(startX, startY);
            gc_.fill();
            gc_.stroke();
        }

        // Average
        gc_.setLineWidth(getAverageStrokeWidth());
        gc_.setStroke(getAverageStroke());
        gc_.beginPath();
        oldX = (avgItems.get(0).getX() - LOWER_BOUND_X) * scaleX;
        oldY = height - (avgItems.get(0).getY() - LOWER_BOUND_Y) * scaleY;
        gc_.moveTo(oldX, oldY);
        for (int i = 1; i < avgItems.size(); i++) {
            XYItem item = avgItems.get(i);
            double x = (item.getX() - LOWER_BOUND_X) * scaleX;
            double y = height - (item.getY() - LOWER_BOUND_Y) * scaleY;
            gc_.lineTo(x, y);
        }
        gc_.stroke();
    }

    private void drawSmoothedMultiTimeSeries(final List<XYSeries<T>> LIST_OF_SERIES) {
        if (LIST_OF_SERIES.isEmpty()) {
            return;
        }
        // Aggregating data
        List<XYItem> minItems = new LinkedList<>();
        List<XYItem> maxItems = new LinkedList<>();
        List<XYItem> avgItems = new LinkedList<>();
        List<XYItem> stdDevItems = new LinkedList<>();
        XYSeries<T> series0 = LIST_OF_SERIES.get(0);
        if (null == series0.getItems() || series0.getItems().isEmpty()) {
            return;
        }
        for (int i = 0; i < series0.getItems().size(); i++) {
            T item = series0.getItems().get(i);
            double x = item.getX();
            double minYForX = Double.MAX_VALUE;
            double maxYForX = Double.MIN_VALUE;
            List<Double> valuesForX = new LinkedList<>();
            for (int j = 0; j < LIST_OF_SERIES.size(); j++) {
                XYSeries<T> series = LIST_OF_SERIES.get(j);
                if (null == series.getItems() || series.getItems().isEmpty()) {
                    continue;
                }
                Optional<T> optionalValue = series.getItems().stream().filter(si -> Double.compare(x, si.getX()) == 0).findFirst();
                if (optionalValue.isPresent()) {
                    minYForX = Math.min(minYForX, optionalValue.get().getY());
                    maxYForX = Math.max(maxYForX, optionalValue.get().getY());
                    valuesForX.add(optionalValue.get().getY());
                }
            }
            minItems.add(new XYChartItem(x, minYForX));
            maxItems.add(new XYChartItem(x, maxYForX));
            avgItems.add(new XYChartItem(x, StatUtils.mean(valuesForX)));
            stdDevItems.add(new XYChartItem(x, StatUtils.standardDeviation(valuesForX)));
        }

        List<Point> avgItemsPoints = new ArrayList<>(avgItems.size());
        avgItems.forEach(item -> avgItemsPoints.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));
        Point[] avgInterpolatedPoints = Helper.subdividePoints(avgItemsPoints.toArray(new Point[0]), SUB_DIVISIONS);


        // Visualize data
        final double LOWER_BOUND_X = getLowerBoundX();
        final double LOWER_BOUND_Y = getLowerBoundY();

        if (isEnvelopeVisible()) {
            List<Point> minItemsPoints = new ArrayList<>(minItems.size());
            minItems.forEach(item -> minItemsPoints.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));
            Point[] minInterpolatedPoints = Helper.subdividePoints(minItemsPoints.toArray(new Point[0]), SUB_DIVISIONS);

            List<Point> maxItemsPoints = new ArrayList<>(maxItems.size());
            maxItems.forEach(item -> maxItemsPoints.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));
            Point[] maxInterpolatedPoints = Helper.subdividePoints(maxItemsPoints.toArray(new Point[0]), SUB_DIVISIONS);

            double startX = (maxInterpolatedPoints[0].getX() - LOWER_BOUND_X) * scaleX;
            double startY = height - (maxInterpolatedPoints[0].getY() - LOWER_BOUND_Y) * scaleY;

            gc_.setFill(getEnvelopeFill());
            gc_.setStroke(getEnvelopeStroke());
            gc_.setLineWidth(0.5);
            gc_.beginPath();
            gc_.moveTo(startX, startY);
            for (int i = 1; i < maxInterpolatedPoints.length; i++) {
                Point point = maxInterpolatedPoints[i];
                double x = (point.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (point.getY() - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            for (int i = minInterpolatedPoints.length - 1; i >= 0; i--) {
                Point point = minInterpolatedPoints[i];
                double x = (point.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (point.getY() - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            gc_.lineTo(startX, startY);
            gc_.closePath();
            gc_.fill();
            gc_.stroke();
        }

        for (XYSeries<T> SERIES : LIST_OF_SERIES) {
            if (!SERIES.isVisible()) {
                continue;
            }
            if (SERIES.getSymbolsVisible()) {
                drawSymbols(SERIES);
            }
        }

        double oldX;
        double oldY;

        if (isStdDeviationVisible()) {
            List<Point> stdDevItemsPoints = new ArrayList<>(stdDevItems.size());
            stdDevItems.forEach(item -> stdDevItemsPoints.add(new Point(item.getX(), item.getY(), item.isEmptyItem())));
            Point[] stdDevInterpolatedPoints = Helper.subdividePoints(stdDevItemsPoints.toArray(new Point[0]), SUB_DIVISIONS);

            // Std. Deviation area
            gc_.setFill(getStdDeviationFill());
            gc_.setStroke(getStdDeviationStroke());
            gc_.setLineWidth(0.5);
            gc_.beginPath();
            double startX = (stdDevInterpolatedPoints[0].getX() - LOWER_BOUND_X) * scaleX;
            double startY = height - (avgInterpolatedPoints[0].getY() - stdDevInterpolatedPoints[0].getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
            gc_.moveTo(startX, startY);
            for (int i = 0; i < stdDevInterpolatedPoints.length; i++) {
                Point stdPoint = stdDevInterpolatedPoints[i];
                Point avgPoint = avgInterpolatedPoints[i];
                double x = (avgPoint.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (avgPoint.getY() - stdPoint.getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            for (int i = stdDevInterpolatedPoints.length - 1; i >= 0; i--) {
                Point stdPoint = stdDevInterpolatedPoints[i];
                Point avgPoint = avgInterpolatedPoints[i];
                double x = (avgPoint.getX() - LOWER_BOUND_X) * scaleX;
                double y = height - (avgPoint.getY() + stdPoint.getY() * 0.5 - LOWER_BOUND_Y) * scaleY;
                gc_.lineTo(x, y);
            }
            gc_.lineTo(startX, startY);
            gc_.fill();
            gc_.stroke();
        }

        // Average
        gc_.setLineWidth(getAverageStrokeWidth());
        gc_.setStroke(getAverageStroke());
        gc_.beginPath();
        oldX = (avgInterpolatedPoints[0].getX() - LOWER_BOUND_X) * scaleX;
        oldY = height - (avgItems.get(0).getY() - LOWER_BOUND_Y) * scaleY;
        gc_.moveTo(oldX, oldY);
        for (int i = 1; i < avgInterpolatedPoints.length; i++) {
            Point point = avgInterpolatedPoints[i];
            double x = (point.getX() - LOWER_BOUND_X) * scaleX;
            double y = height - (point.getY() - LOWER_BOUND_Y) * scaleY;
            gc_.lineTo(x, y);
        }
        gc_.stroke();
    }

    private List<Point>[] splitIntoAboveAndBelow(final List<Point> POINTS) {
        ArrayList<Point> aboveReferencePoints = new ArrayList<>();
        ArrayList<Point> belowReferencePoints = new ArrayList<>();
        Point last = POINTS.get(0);
        boolean isAbove = Double.compare(last.getY(), 0.0) >= 0;
        int noOfPoints = POINTS.size();
        for (int i = 0; i < noOfPoints; i++) {
            Point current = POINTS.get(i);
            Point next = i < noOfPoints - 1 ? POINTS.get(i + 1) : POINTS.get(noOfPoints - 1);

            if (Double.compare(current.getY(), 0.0) >= 0) {
                if (!isAbove) {
                    Point p = Helper.calcIntersectionPoint(last, current, 0.0);
                    aboveReferencePoints.add(p);
                    belowReferencePoints.add(p);
                }
                aboveReferencePoints.add(current);
                isAbove = true;
            } else {
                if (isAbove) {
                    Point p = Helper.calcIntersectionPoint(current, next, 0.0);
                    aboveReferencePoints.add(p);
                    belowReferencePoints.add(p);
                }
                // Invert y values that are below the reference point
                belowReferencePoints.add(new Point(current.getX(), -current.getY()));
                isAbove = false;
            }
            last = current;
        }
        return new ArrayList[]{aboveReferencePoints, belowReferencePoints};
    }

    private Map<Integer, List<Point>> splitIntoBands(final List<Point> POINTS, final double BAND_WIDTH) {
        Map<Integer, List<Point>> mapOfBands = new HashMap<>(getNoOfBands());
        if (POINTS.isEmpty()) {
            return mapOfBands;
        }

        int noOfPoints = POINTS.size();
        double currentBandMinY;
        double currentBandMaxY;
        double currentBandMinYScaled;
        double currentBandMaxYScaled;

        // Add first point to all noOfBands
        Point firstPoint = new Point(POINTS.get(0).getX(), POINTS.get(0).getY());
        for (int band = 0; band < getNoOfBands(); band++) {
            List<Point> listOfPointsInBand = new ArrayList<>(noOfPoints);
            listOfPointsInBand.add(firstPoint);
            mapOfBands.put(band, listOfPointsInBand);
        }

        // Iterate over all points and check for each band
        for (int i = 1; i < noOfPoints - 1; i++) {
            Point last = POINTS.get(i - 1);
            double lastY = height - (last.getY() * scaleY);
            Point current = POINTS.get(i);
            double currentY = height - (current.getY() * scaleY);
            Point next = POINTS.get(i + 1);
            double nextY = height - (next.getY() * scaleY);

            for (int band = 0; band < getNoOfBands(); band++) {
                currentBandMinY = band * BAND_WIDTH;
                currentBandMaxY = currentBandMinY + BAND_WIDTH;
                currentBandMinYScaled = height - currentBandMinY * scaleY;
                currentBandMaxYScaled = height - currentBandMaxY * scaleY;

                if (Double.compare(lastY, currentBandMinYScaled) >= 0) {             // last <= currentBandMinY
                    // Calculate intersection with currentBandMinY
                    mapOfBands.get(band).add(Helper.calcIntersectionPoint(last, current, currentBandMinY));
                } else if (Double.compare(currentY, currentBandMinYScaled) <= 0 &&
                        Double.compare(currentY, currentBandMaxYScaled) >= 0) {   // currentBandMinY < current < currentBandMaxY
                    mapOfBands.get(band).add(new Point(current.getX(), current.getY()));
                } else if (Double.compare(nextY, currentBandMaxYScaled) <= 0) {      // next >= currentBandMaxY
                    // Calculate intersection with currentBandMaxY
                    mapOfBands.get(band).add(Helper.calcIntersectionPoint(current, next, currentBandMaxY));
                }
            }
        }

        // Add last point to all bands
        Point lastPoint = new Point(POINTS.get(noOfPoints - 1).getX(), Math.clamp(POINTS.get(noOfPoints - 1).getY(), 0, BAND_WIDTH));
        mapOfBands.forEach((band, pointsInBand) -> {
            Point lastPointInBand = pointsInBand.get(pointsInBand.size() - 1);
            if (noOfPoints - lastPointInBand.getX() > 2) {
                pointsInBand.add(new Point(noOfPoints - 1, lastPointInBand.getY()));
            }
            pointsInBand.add(lastPoint);
        });

        return mapOfBands;
    }

    private void drawSymbols(final XYSeries<T> series) {
        if (!series.isVisible()) {
            return;
        }
        final double lowerBoundX = getLowerBoundX();
        final double lowerBoundY = getLowerBoundY();
        Symbol seriesSymbol = series.getSymbol();
        Color symbolFill = series.getSymbolFill();
        Color symbolStroke = series.getSymbolStroke();
        double size = series.getSymbolSize() > -1 ? series.getSymbolSize() : symbolSize;
        for (T item : series.getItems()) {
            double x = (item.getX() - lowerBoundX) * scaleX;
            double y = height - (item.getY() - lowerBoundY) * scaleY;
            Symbol itemSymbol = item.getSymbol();
            if (item.isEmptyItem()) {
                continue;
            }
            if (itemSymbol == Symbol.NONE) {
                drawSymbol(x, y, symbolFill, symbolStroke, seriesSymbol, size);
            } else {
                drawSymbol(x, y, item.getFillColor(), item.getStrokeColor(), itemSymbol, size);
            }
        }
    }

    /**
     * draw a symbol of a point
     *
     * @param x          center x coordinate of the symbol
     * @param y          center y coordinate of the symbol
     * @param fill       fill color
     * @param stroke     stroke color
     * @param symbol     {@link Symbol}
     * @param symbolSize size
     */
    private void drawSymbol(final double x, final double y, final Paint fill, final Paint stroke,
            final Symbol symbol, final double symbolSize) {
        double halfSymbolSize = symbolSize * 0.5;
        gc_.save();
        switch (symbol) {
            case NONE:
                break;
            case SQUARE:
                gc_.setStroke(stroke);
                gc_.setFill(fill);
                gc_.fillRect(x - halfSymbolSize, y - halfSymbolSize, symbolSize, symbolSize);
                gc_.strokeRect(x - halfSymbolSize, y - halfSymbolSize, symbolSize, symbolSize);
                break;
            case TRIANGLE:
                gc_.setStroke(stroke);
                gc_.setFill(fill);
                gc_.beginPath();
                gc_.moveTo(x, y - halfSymbolSize);
                gc_.lineTo(x + halfSymbolSize, y + halfSymbolSize);
                gc_.lineTo(x - halfSymbolSize, y + halfSymbolSize);
                gc_.lineTo(x, y - halfSymbolSize);
                gc_.closePath();
                gc_.fill();
                gc_.stroke();
                break;
            case STAR:
                gc_.setStroke(stroke);
                gc_.setFill(null);
                gc_.strokeLine(x - halfSymbolSize, y, x + halfSymbolSize, y);
                gc_.strokeLine(x, y - halfSymbolSize, x, y + halfSymbolSize);
                gc_.strokeLine(x - halfSymbolSize, y - halfSymbolSize, x + halfSymbolSize, y + halfSymbolSize);
                gc_.strokeLine(x + halfSymbolSize, y - halfSymbolSize, x - halfSymbolSize, y + halfSymbolSize);
                break;
            case CROSS:
                gc_.setStroke(stroke);
                gc_.setFill(null);
                gc_.strokeLine(x - halfSymbolSize, y, x + halfSymbolSize, y);
                gc_.strokeLine(x, y - halfSymbolSize, x, y + halfSymbolSize);
                break;
            case PEAK:
                gc_.setStroke(stroke);
                gc_.setFill(null);

            case CIRCLE:
            default:
                gc_.setStroke(stroke);
                gc_.setFill(fill);
                gc_.fillOval(x - halfSymbolSize, y - halfSymbolSize, symbolSize, symbolSize);
                gc_.strokeOval(x - halfSymbolSize, y - halfSymbolSize, symbolSize, symbolSize);
                break;
        }
        gc_.restore();
    }

    public void addCursorEventListener(final CursorEventListener listener) {
        if (cursorEventListeners_.contains(listener)) {
            return;
        }
        cursorEventListeners_.add(listener);
    }

    public void removeCursorEventListener(final CursorEventListener listener) {
        cursorEventListeners_.remove(listener);
    }

    public void removeAllCursorEventListeners() {cursorEventListeners_.clear();}

    public void fireCursorEvent(final CursorEvent EVT) {
        cursorEventListeners_.forEach(listener -> listener.handleCursorEvent(EVT));
    }

    private void resize() {
        width = getWidth(); // - getInsets().getLeft() - getInsets().getRight();
        height = getHeight(); // - getInsets().getTop() - getInsets().getBottom();
        size = Math.min(width, height);

        if (keepAspect) {
            if (aspectRatio * width > height) {
                width = 1 / (aspectRatio / height);
            } else if (1 / (aspectRatio / height) > width) {
                height = aspectRatio * width;
            }
        }

        if (width > 0 && height > 0) {
            canvas_.setWidth(width);
            canvas_.setHeight(height);
            canvas_.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            cursorCanvas_.setWidth(width);
            cursorCanvas_.setHeight(height);
            cursorCanvas_.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            symbolSize = Math.clamp(size * 0.016, MIN_SYMBOL_SIZE, MAX_SYMBOL_SIZE);

            scaleX = width / getRangeX();
            scaleY = height / getRangeY();

            redraw();
        }
    }
}
