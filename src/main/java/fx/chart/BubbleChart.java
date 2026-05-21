package fx.chart;

import fx.chart.data.ChartItem;
import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.geometry.Circle;
import fx.chart.tools.Helper;
import fx.chart.tools.TooltipPopup;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Bubble chart.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 2:24 PM
 */
public class BubbleChart<T extends ChartItem> extends ChartElement {

    public static final Color DEFAULT_BACKGROUND_COLOR = Color.TRANSPARENT;
    private static final double PREFERRED_WIDTH = 400;
    private static final double PREFERRED_HEIGHT = 400;

    private double width;
    private double height;
    private double chartCenterX;
    private double chartCenterY;
    private Canvas canvas;
    private GraphicsContext ctx;
    private Color _backgroundColor;
    private ObjectProperty<Color> backgroundColor;
    private TooltipPopup popup;
    private ObservableList<T> items;
    private ChartEventListener<ChartEvent> itemObserver;
    private ListChangeListener<T> itemListListener;
    private List<BubbleNode> nodes;
    private double max;
    private long lastTimerCall;
    private AnimationTimer timer;


    public BubbleChart() {
        this(new ArrayList<>());
    }

    public BubbleChart(final List<T> ITEMS) {
        items = FXCollections.observableArrayList();
        itemObserver = e -> redraw();
        itemListListener = c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(addedItem -> addedItem.addEventListener(ChartEvent.ITEM_UPDATE, itemObserver));
                } else if (c.wasRemoved()) {
                    c.getRemoved().forEach(removedItem -> removedItem.removeEventListener(ChartEvent.ITEM_UPDATE, itemObserver));
                }
            }
            max = items.stream().max(Comparator.comparingDouble(ChartItem::getValue)).get().getValue();
        };
        nodes = new ArrayList<>();
        _backgroundColor = DEFAULT_BACKGROUND_COLOR;
        popup = new TooltipPopup("", 3500, true);

        items.setAll(null == ITEMS ? new ArrayList<>() : ITEMS);
        items.forEach(item -> nodes.add(new BubbleNode(item)));

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                if (now > lastTimerCall + 100_000_000) {
                    update();
                    lastTimerCall = now;
                }
            }
        };

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
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

        getChildren().setAll(canvas);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        items.addListener(itemListListener);
        canvas.setOnMousePressed(e -> {
            String tooltipText = new StringBuilder().toString();
            if (!tooltipText.isEmpty()) {
                popup.setX(e.getScreenX() - popup.getWidth() * 0.5);
                popup.setY(e.getScreenY() - 30);
                popup.setText(tooltipText);
                popup.animatedShow(getScene().getWindow());
            }
        });
    }

    public void dispose() {items.removeListener(itemListListener);}

    public List<T> getItems() {return items;}

    public void setItems(final T... ITEMS) {setItems(Arrays.asList(ITEMS));}

    public void setItems(final List<T> ITEMS) {items.setAll(ITEMS);}

    public void addItem(final T ITEM) {
        if (!items.contains(ITEM)) {
            items.add(ITEM);
        }
    }

    public void removeItem(final T ITEM) {
        if (items.contains(ITEM)) {
            items.remove(ITEM);
        }
    }

    public Color getBackgroundColor() {return null == backgroundColor ? _backgroundColor : backgroundColor.get();}

    public void setBackgroundColor(final Color backgroundColor) {
        if (null == this.backgroundColor) {
            _backgroundColor = backgroundColor;
            redraw();
        } else {
            this.backgroundColor.set(backgroundColor);
        }
    }

    public ObjectProperty<Color> backgroundColorProperty() {
        if (null == backgroundColor) {
            backgroundColor = new ObjectPropertyBase<>(_backgroundColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return BubbleChart.this;}

                @Override
                public String getName() {return "backgroundColor";}
            };
            _backgroundColor = null;
        }
        return backgroundColor;
    }

    public void setPopupTimeout(final long milliseconds) {popup.setTimeout(milliseconds);}

    public void start() {timer.start();}

    public void stop() {timer.stop();}

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     *
     * @param filename The path and name of the file  /Users/hansolo/Desktop/plot.png
     * @param width    The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height   The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return True if the procedure was successful, otherwise false
     */
    public boolean renderToImage(final String filename, final int width, final int height) {
        return Helper.renderToImage(BubbleChart.this, width, height, filename);
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     *
     * @param width  The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return A BufferedImage of this chart in the given dimension
     */
    public BufferedImage renderToImage(final int width, final int height) {
        return Helper.renderToImage(BubbleChart.this, width, height);
    }


    // ******************** Layout ********************************************
    private void update() {
        nodes.forEach(node -> {
            double distanceToCenter = node.distanceToCenter();
            if (distanceToCenter > node.getRadius() * 0.5) {
                double force = node.force();
                double[] xy = node.stepToCenter(force);
                node.setCenter(xy[0], xy[1]);
            }
        });
        redraw();
    }

    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();

        chartCenterX = width * 0.5;
        chartCenterY = height * 0.5;

        if (width > 0 && height > 0) {
            canvas.setWidth(width);
            canvas.setHeight(height);
            canvas.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            ctx.setTextBaseline(VPos.CENTER);
        }
        redraw();
    }

    protected void redraw() {
        ctx.clearRect(0, 0, width, height);
        ctx.setFill(getBackgroundColor());
        ctx.fillRect(0, 0, width, height);

        if (items.isEmpty()) {
            return;
        }

        double min = items.stream().min(Comparator.comparingDouble(ChartItem::getValue)).get().getValue();
        double max = items.stream().max(Comparator.comparingDouble(ChartItem::getValue)).get().getValue();

        double maxRadius = 50;
        double range = max - min;
        double scaleFactor = maxRadius / range;

        nodes.forEach(node -> {
            node.setScaleFactor(scaleFactor);
            ctx.setFill(node.getFill());
            ctx.fillOval(node.getX(), node.getY(), node.getWidth(), node.getHeight());
        });
    }


    // ******************** Inner Classes *************************************
    public class BubbleNode {
        private ChartItem item;
        private Circle circle;
        private double scaleFactor;


        public BubbleNode(final T item) {
            this.item = item;
            this.circle = new Circle(0, 0, item.getValue());
            this.scaleFactor = 1.0;
        }


        public double getValue() {return item.getValue();}

        public Circle getCircle() {return circle;}

        public double getX() {return circle.getX();}

        public double getY() {return circle.getY();}

        public double getWidth() {return circle.getWidth();}

        public double getHeight() {return circle.getHeight();}

        public double getRadius() {return circle.getRadius();}

        public double getCenterX() {return circle.getCenterX();}

        public void setCenterX(final double centerX) {circle.setCenterX(centerX);}

        public void setCenter(final double centerX, final double centerY) {
            circle.setCenter(centerX, centerY);
        }

        public double getCenterY() {return circle.getCenterY();}

        public void setCenterY(final double centerY) {circle.setCenterY(centerY);}

        public Color getFill() {return item.getFillColor();}

        public void setFill(final Color fill) {item.setFill(fill);}

        public boolean contains(final double x, final double y) {return circle.contains(x, y);}

        public boolean intersects(final BubbleNode other) {
            final double d = Math.sqrt((getCenterX() - other.getCenterX()) * (getCenterX() - other.getCenterX()) + (getCenterY() - other.getCenterY()) * (getCenterY() - other.getCenterY()));
            if (((d <= circle.getRadius() - other.getRadius() || d <= other.getRadius() - circle.getRadius()) || d < circle.getRadius() + other.getRadius()) || d == circle.getRadius() + other.getRadius()) {
                return true;
            } else {
                return false;
            }
        }

        public double distanceTo(final BubbleNode other) {
            return Helper.distance(getCenterX(), getCenterY(), other.getCenterX(), other.getCenterY());
        }

        public double distanceToCenter() {
            return Helper.distance(getCenterX(), getCenterY(), chartCenterX, chartCenterY);
        }

        public double[] stepToCenter(final double step) {
            double x = getCenterX() + ((step / distanceToCenter()) * (chartCenterX - getCenterX()));
            double y = getCenterY() + ((step / distanceToCenter()) * (chartCenterY - getCenterY()));
            return new double[]{x, y};
        }

        public double getScaleFactor() {return scaleFactor;}

        public void setScaleFactor(final double scaleFactor) {
            this.scaleFactor = scaleFactor;
            this.circle.setRadius(this.item.getValue() * scaleFactor);
        }

        public double force() {
            double g = 6.67 / 1e11;
            double distance = distanceToCenter() * 1000;
            double f = (g * getValue()) / (distance * distance);
            return f;
        }
    }
}
