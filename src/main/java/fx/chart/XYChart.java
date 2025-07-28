package fx.chart;

import fx.chart.data.XYItem;
import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.font.Fonts;
import fx.chart.tools.Helper;
import fx.chart.tools.Marker;
import fx.chart.util.Bounds;
import javafx.beans.DefaultProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * a XYChart
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 03 Jul 2025, 4:08 PM
 */
@DefaultProperty("children")
public class XYChart<T extends XYItem> extends Region {

    private static final double PREFERRED_WIDTH = 400;
    private static final double PREFERRED_HEIGHT = 250;
    private static final double MINIMUM_WIDTH = 50;
    private static final double MINIMUM_HEIGHT = 50;
    private static final double MAXIMUM_WIDTH = 4096;
    private static final double MAXIMUM_HEIGHT = 4096;
    private double width;
    private double height;
    private double size;

    private final ObservableList<XYPane<T>> xyPanes_;
    private final ObservableList<Axis> axes_;

    private Canvas markerCanvas_;
    private GraphicsContext markerCtx_;

    private Axis yAxisL;
    private Axis yAxisC;
    private Axis yAxisR;
    private Axis xAxisT;
    private Axis xAxisC;
    private Axis xAxisB;
    private double topAxisHeight;
    private double rightAxisWidth;
    private double bottomAxisHeight;
    private double leftAxisWidth;
    private Grid grid_;
    private boolean hasLeftYAxis;
    private boolean hasCenterYAxis;
    private boolean hasRightYAxis;
    private boolean hasTopXAxis;
    private boolean hasCenterXAxis;
    private boolean hasBottomXAxis;

    private String title_;
    private StringProperty titleProperty;

    private String subTitle_;
    private StringProperty subTitleProperty;

    private Color titleColor_;
    private ObjectProperty<Color> titleColorProperty;

    private Color subTitleColor_;
    private ObjectProperty<Color> subTitleColorProperty;

    /**
     * pane to hold all rendering elements
     */
    private AnchorPane pane_;
    private BooleanBinding showing_;
    private final ChartEventListener<ChartEvent> axisListener_;
    private final ChartEventListener<ChartEvent> updateListener_;
    private final List<Marker> markers_;

    /**
     * Create a {@link XYChart} without grid
     *
     * @param xyPane {@link XYPane}
     * @param axes   {@link Axis}
     */
    public XYChart(final XYPane<T> xyPane, final Axis... axes) {
        this(List.of(xyPane), null, axes);
    }

    public XYChart(final XYPane<T> xyPane, final Grid grid, final Axis... axes) {
        this(List.of(xyPane), grid, axes);
    }

    public XYChart(final List<XYPane<T>> xyPanes, final Axis... axes) {
        this(xyPanes, null, axes);
    }

    public XYChart(final List<XYPane<T>> xyPanes, final Grid grid, final Axis... axes) {
        if (xyPanes == null) {
            throw new IllegalArgumentException("XYPanes cannot be null");
        }
        long noOfPolarCharts = xyPanes.stream().filter(xyPane -> xyPane.containsPolarChart()).count();
        if (noOfPolarCharts > 0) {
            throw new IllegalArgumentException("XYPane contains Polar chart type");
        }
        xyPanes_ = FXCollections.observableList(new LinkedList<>(xyPanes));
        axes_ = FXCollections.observableList(Arrays.asList(axes));
        grid_ = grid;
        width = PREFERRED_WIDTH;
        height = PREFERRED_HEIGHT;
        axisListener_ = evt -> adjustChartRange();
        updateListener_ = evt -> drawMarkerCanvas();
        markers_ = new ArrayList<>();
        title_ = "";
        subTitle_ = "";
        xyPanes.forEach(xyPane -> xyPane.addChartEvtObserver(ChartEvent.UPDATE, updateListener_));
        checkReferenceZero();

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

        checkForAxis();

        if (xyPanes_.size() > 1) {
            xyPanes_.forEach(xyPane -> xyPane.setChartBackground(Color.TRANSPARENT));
        }

        adjustChartRange();

        adjustAxisAnchors();

        pane_ = new AnchorPane();
        xyPanes_.forEach(xyPane -> pane_.getChildren().add(xyPane));

        pane_.getChildren().addAll(axes_);
        setGrid(grid_);

        markerCanvas_ = new Canvas(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        markerCtx_ = markerCanvas_.getGraphicsContext2D();

        markerCanvas_.setMouseTransparent(true);

        pane_.getChildren().addAll(markerCanvas_);

        getChildren().setAll(pane_);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        xyPanes_.addListener((ListChangeListener<XYPane<T>>) c -> {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(xyPane -> xyPane.addChartEvtObserver(ChartEvent.UPDATE, updateListener_));
            } else if (c.wasRemoved()) {
                c.getRemoved().forEach(xyPane -> xyPane.removeChartEvtObserver(ChartEvent.UPDATE, updateListener_));
            }
            if (xyPanes_.size() > 1) {
                xyPanes_.forEach(xyPane -> xyPane.setChartBackground(Color.TRANSPARENT));
            }
            checkReferenceZero();
            refresh();
        });
        if (null != getScene()) {
            setupBinding();
        } else {
            sceneProperty().addListener((o1, ov1, nv1) -> {
                if (null == nv1) {
                    return;
                }
                if (null != getScene().getWindow()) {
                    setupBinding();
                } else {
                    sceneProperty().get().windowProperty().addListener((o2, ov2, nv2) -> {
                        if (null == nv2) {
                            return;
                        }
                        setupBinding();
                    });
                }
            });
        }

        axes_.addListener((ListChangeListener<Axis>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(axis -> axis.addChartEventListener(ChartEvent.AXIS_RANGE_CHANGED, axisListener_));
                } else if (c.wasRemoved()) {
                    c.getAddedSubList().forEach(axis -> axis.removeChartEvtObserver(ChartEvent.AXIS_RANGE_CHANGED, axisListener_));
                }
            }
        });
        axes_.forEach(axis -> axis.addChartEventListener(ChartEvent.AXIS_RANGE_CHANGED, axisListener_));
    }

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

    public void dispose() {
        axes_.forEach(axis -> axis.removeChartEvtObserver(ChartEvent.AXIS_RANGE_CHANGED, axisListener_));
        xyPanes_.forEach(xyPane -> xyPane.dispose());
    }

    public String getTitle() {return null == titleProperty ? title_ : titleProperty.get();}

    public void setTitle(final String TITLE) {
        if (null == titleProperty) {
            title_ = TITLE;
            refresh();
        } else {
            titleProperty.set(TITLE);
        }
    }

    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new StringPropertyBase(title_) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return XYChart.this;}

                @Override
                public String getName() {return "title";}
            };
            title_ = null;
        }
        return titleProperty;
    }

    public String getSubTitle() {return null == subTitleProperty ? subTitle_ : subTitleProperty.get();}

    public void setSubTitle(final String SUB_TITLE) {
        if (null == subTitleProperty) {
            subTitle_ = SUB_TITLE;
            xyPanes_.forEach(xyPane -> xyPane.redraw());
        } else {
            subTitleProperty.set(SUB_TITLE);
        }
    }

    public StringProperty subTitleProperty() {
        if (null == subTitleProperty) {
            subTitleProperty = new StringPropertyBase(subTitle_) {
                @Override
                protected void invalidated() {xyPanes_.forEach(xyPane -> xyPane.redraw());}

                @Override
                public Object getBean() {return XYChart.this;}

                @Override
                public String getName() {return "subTitle";}
            };
            subTitle_ = null;
        }
        return subTitleProperty;
    }

    public Color getTitleColor() {return null == titleColorProperty ? titleColor_ : titleColorProperty.get();}

    public void setTitleColor(final Color TITLE_COLOR) {
        if (null == titleColorProperty) {
            titleColor_ = TITLE_COLOR;
            refresh();
        } else {
            titleColorProperty.set(TITLE_COLOR);
        }
    }

    public ObjectProperty<Color> titleColorProperty() {
        if (null == titleColorProperty) {
            titleColorProperty = new ObjectPropertyBase<Color>(titleColor_) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return XYChart.this;}

                @Override
                public String getName() {return "titleColor";}
            };
            titleColor_ = null;
        }
        return titleColorProperty;
    }

    public Color getSubTitleColor() {return null == subTitleColorProperty ? subTitleColor_ : subTitleColorProperty.get();}

    public void setSubTitleColor(final Color SUB_TITLE_COLOR) {
        if (null == subTitleColorProperty) {
            subTitleColor_ = SUB_TITLE_COLOR;
            refresh();
        } else {
            subTitleColorProperty.set(SUB_TITLE_COLOR);
        }
    }

    public ObjectProperty<Color> subTitleColorProperty() {
        if (null == subTitleColorProperty) {
            subTitleColorProperty = new ObjectPropertyBase<>(subTitleColor_) {
                @Override
                protected void invalidated() {refresh();}

                @Override
                public Object getBean() {return XYChart.this;}

                @Override
                public String getName() {return "subTitleColor";}
            };
            subTitleColor_ = null;
        }
        return subTitleColorProperty;
    }

    public boolean isReferenceZero() {
        if (xyPanes_.size() > 0) {
            return xyPanes_.get(0).isReferenceZero();
        } else {
            return true;
        }
    }

    public void setReferenceZero(final boolean IS_ZERO) {
        xyPanes_.forEach(xyPane -> xyPane.setReferenceZero(IS_ZERO));
    }

    /**
     * set the grid lines
     *
     * @param grid {@link Grid}
     */
    public void setGrid(final Grid grid) {
        if (grid == null) return;
        if (grid_ != null) {
            pane_.getChildren().remove(grid_);
        }
        grid_ = grid;
        pane_.getChildren().addFirst(grid_);
        adjustGridAnchors();
    }

    public XYPane<T> getXYPane() {
        return !xyPanes_.isEmpty() ? xyPanes_.getFirst() : null;
    }

    public List<XYPane<T>> getXYPanes() {return xyPanes_;}

    public void addXYPane(final XYPane<T> xyPane) {
        xyPanes_.add(xyPane);
    }

    public void removeXYPane(final XYPane<T> xyPane) {
        xyPanes_.remove(xyPane);
    }

    public List<Marker> getMarkers() {return new ArrayList<>(markers_);}

    public void setMarkers(final List<Marker> markers) {
        List<Marker> validatedMarkers = validateMarkers(markers);
        this.markers_.clear();
        validatedMarkers.forEach(marker -> this.markers_.add(marker));
        refresh();
    }

    public void addMarker(final Marker marker) {
        if (markers_.contains(marker)) {
            return;
        }
        markers_.add(marker);
        drawMarkerCanvas();
    }

    public void refresh() {
        xyPanes_.forEach(xyPane -> xyPane.redraw());
        drawMarkerCanvas();
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     *
     * @param filename The path and name of the file  /Users/hansolo/Desktop/plot.png
     * @param width    The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height   The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return True if the procedure was successful, otherwise false
     */
    public boolean renderToImage(final String filename, final int width, final int height) {
        return Helper.renderToImage(XYChart.this, width, height, filename);
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     *
     * @param width  The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return A BufferedImage of this chart in the given dimension
     */
    public BufferedImage renderToImage(final int width, final int height) {
        return Helper.renderToImage(XYChart.this, width, height);
    }

    private void drawMarkerCanvas() {
        if (markerCanvas_ == null) {
            return;
        }
        markerCtx_.clearRect(0, 0, width, height);
        markerCtx_.setTextBaseline(VPos.BASELINE);
        markerCtx_.setLineCap(StrokeLineCap.BUTT);
        markers_.forEach(marker -> {
            markerCtx_.save();
            Axis axis = marker.getAxis();
            Bounds axisBounds = axis.getAxisBounds();
            String formatString = marker.getFormatString();
            double stepSize = axis.getStepSize();
            double value = marker.getValue();
            double fontSize = axis.getTitleFontSize();
            double lineWidth = marker.getLineWidth();
            String markerText = marker.getText() + " (" + String.format(Locale.US, formatString, value) + ")";
            markerCtx_.setLineWidth(lineWidth);
            markerCtx_.setStroke(marker.getStroke());
            switch (marker.getLineStyle()) {
                case SOLID:
                    markerCtx_.setLineDashes();
                    break;
                case DASHED:
                    markerCtx_.setLineDashes(2 * lineWidth, lineWidth);
                    break;
                case DOTTED:
                    markerCtx_.setLineDashes(lineWidth, lineWidth);
                    break;
            }
            markerCtx_.setFont(Fonts.latoLight(fontSize * 1.5));
            markerCtx_.setFill(marker.getTextFill());
            switch (axis.getOrientation()) {
                case VERTICAL -> {
                    double textOffsetX = 5;
                    double textOffsetY = fontSize * 0.8;
                    double y = axisBounds.getMinY() + axis.getMinValue() * stepSize + axisBounds.getHeight() - value * stepSize;
                    markerCtx_.strokeLine(axisBounds.getWidth(), y, width - axisBounds.getWidth(), y);
                    markerCtx_.fillText(markerText, axisBounds.getWidth() + textOffsetX, y - textOffsetY);
                }
                case HORIZONTAL -> {
                    double textOffsetX = 5;
                    double textOffsetY = fontSize * 0.8;
                    double x = axisBounds.getHeight() - axis.getMinValue() * stepSize + value * stepSize;
                    markerCtx_.strokeLine(x, axisBounds.getMinY(), x, height - axisBounds.getHeight());
                    markerCtx_.fillText(markerText, x + textOffsetX, height - axisBounds.getHeight() - textOffsetY);
                }
            }
            markerCtx_.restore();
        });

        markerCtx_.setFill(getTitleColor());
        markerCtx_.setFont(Font.font(size * 0.035));
        markerCtx_.setTextAlign(TextAlignment.CENTER);
        markerCtx_.setTextBaseline(VPos.CENTER);
        markerCtx_.fillText(getTitle(), width * 0.5, height * 0.1, width);

        markerCtx_.setFill(getSubTitleColor());
        markerCtx_.setFont(Font.font(size * 0.0175));
        markerCtx_.fillText(getSubTitle(), width * 0.5, height * 0.1 + size * 0.035 * 1.25, width);
    }

    private void checkForAxis() {
        axes_.forEach(axis -> {
            Position position = axis.getPosition();
            switch (axis.getOrientation()) {
                case HORIZONTAL:
                    switch (position) {
                        case TOP:
                            hasTopXAxis = true;
                            topAxisHeight = axis.getPrefHeight();
                            xAxisT = axis;
                            break;
                        case CENTER:
                            hasCenterXAxis = true;
                            xAxisC = axis;
                            break;
                        case BOTTOM:
                            hasBottomXAxis = true;
                            bottomAxisHeight = axis.getPrefHeight();
                            xAxisB = axis;
                            break;
                        default:
                            hasTopXAxis = false;
                            hasCenterXAxis = false;
                            hasBottomXAxis = false;
                            break;
                    }
                    break;
                case VERTICAL:
                    switch (position) {
                        case LEFT:
                            hasLeftYAxis = true;
                            leftAxisWidth = axis.getPrefWidth();
                            yAxisL = axis;
                            break;
                        case CENTER:
                            hasCenterYAxis = true;
                            yAxisC = axis;
                            break;
                        case RIGHT:
                            hasRightYAxis = true;
                            rightAxisWidth = axis.getPrefWidth();
                            yAxisR = axis;
                            break;
                        default:
                            hasLeftYAxis = false;
                            hasCenterYAxis = false;
                            hasRightYAxis = false;
                            break;
                    }
                    break;
            }
        });
    }

    private void adjustChartRange() {
        xyPanes_.forEach(xyPane -> {
            if (hasBottomXAxis) {
                xyPane.setLowerBoundX(xAxisB.getMinValue());
                xyPane.setUpperBoundX(xAxisB.getMaxValue());
            } else if (hasTopXAxis) {
                xyPane.setLowerBoundX(xAxisT.getMinValue());
                xyPane.setUpperBoundX(xAxisT.getMaxValue());
            } else if (hasCenterXAxis) {
                xyPane.setLowerBoundX(xAxisC.getMinValue());
                xyPane.setUpperBoundX(xAxisC.getMaxValue());
            }

            if (hasLeftYAxis) {
                xyPane.setLowerBoundY(yAxisL.getMinValue());
                xyPane.setUpperBoundY(yAxisL.getMaxValue());
            } else if (hasRightYAxis) {
                xyPane.setLowerBoundY(yAxisR.getMinValue());
                xyPane.setUpperBoundY(yAxisR.getMaxValue());
            } else if (hasCenterYAxis) {
                xyPane.setLowerBoundY(yAxisC.getMinValue());
                xyPane.setUpperBoundY(yAxisC.getMaxValue());
            }
        });
    }

    private void adjustAxisAnchors() {
        xyPanes_.forEach(xyPane -> {
            axes_.forEach(axis -> {
                if (axis.getOrientation() == Orientation.HORIZONTAL) {
                    AnchorPane.setLeftAnchor(axis, hasLeftYAxis ? leftAxisWidth : 0d);
                    AnchorPane.setRightAnchor(axis, hasRightYAxis ? rightAxisWidth : 0d);

                    AnchorPane.setLeftAnchor(xyPane, hasLeftYAxis ? leftAxisWidth : 0d);
                    AnchorPane.setRightAnchor(xyPane, hasRightYAxis ? rightAxisWidth : 0d);
                } else {
                    AnchorPane.setTopAnchor(axis, hasTopXAxis ? topAxisHeight : 0d);
                    AnchorPane.setBottomAnchor(axis, hasBottomXAxis ? bottomAxisHeight : 0d);

                    AnchorPane.setTopAnchor(xyPane, hasTopXAxis ? topAxisHeight : 0d);
                    AnchorPane.setBottomAnchor(xyPane, hasBottomXAxis ? bottomAxisHeight : 0d);
                }
            });
        });
    }

    private void adjustCenterAxisAnchors() {
        if (hasCenterYAxis) {
            if (hasBottomXAxis) {
                if (hasLeftYAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisB.getZeroPosition() + yAxisL.getWidth());
                } else if (hasRightYAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisB.getZeroPosition());
                } else if (hasCenterXAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisC.getZeroPosition());
                }
            } else {
                if (hasLeftYAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisT.getZeroPosition() + yAxisL.getWidth());
                } else if (hasRightYAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisT.getZeroPosition());
                } else if (hasCenterXAxis) {
                    AnchorPane.setLeftAnchor(yAxisC, xAxisC.getZeroPosition());
                }
            }
        }
        if (hasCenterXAxis) {
            if (hasLeftYAxis) {
                if (hasTopXAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisL.getZeroPosition() + xAxisT.getHeight());
                } else if (hasBottomXAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisL.getZeroPosition());
                } else if (hasCenterYAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisC.getZeroPosition());
                }
            } else {
                if (hasTopXAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisR.getZeroPosition() + xAxisT.getHeight());
                } else if (hasBottomXAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisR.getZeroPosition());
                } else if (hasCenterYAxis) {
                    AnchorPane.setTopAnchor(xAxisC, yAxisC.getZeroPosition());
                }
            }
        }
    }

    private void adjustGridAnchors() {
        if (null == grid_) return;
        AnchorPane.setLeftAnchor(grid_, hasLeftYAxis ? leftAxisWidth : 0d);
        AnchorPane.setRightAnchor(grid_, hasRightYAxis ? rightAxisWidth : 0d);
        AnchorPane.setTopAnchor(grid_, hasTopXAxis ? topAxisHeight : 0d);
        AnchorPane.setBottomAnchor(grid_, hasBottomXAxis ? bottomAxisHeight : 0d);
    }

    private void setupBinding() {
        showing_ = Bindings.selectBoolean(sceneProperty(), "window", "showing");
        showing_.addListener((o, ov, nv) -> {
            if (nv) {
                adjustCenterAxisAnchors();
            }
        });
    }

    private void checkReferenceZero() {
        boolean isReferenceZero = true;
        if (xyPanes_.size() > 0) {
            isReferenceZero = xyPanes_.get(0).isReferenceZero();
        }
        setReferenceZero(isReferenceZero);
    }

    private List<Marker> validateMarkers(final List<Marker> markersToValidate) {
        List<Marker> validatedMarkers = new ArrayList<>();
        markersToValidate.forEach(marker -> {
            if (axes_.contains(marker.getAxis())) {
                validatedMarkers.add(marker);
            }
        });
        return validatedMarkers;
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size = Math.max(width, height);

        if (width > 0 && height > 0) {
            pane_.setMaxSize(width, height);
            pane_.setPrefSize(width, height);
            pane_.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            markerCanvas_.setWidth(width);
            markerCanvas_.setHeight(height);
            markerCanvas_.relocate(getInsets().getLeft(), getInsets().getTop());

            adjustCenterAxisAnchors();

            drawMarkerCanvas();
        }
    }
}
