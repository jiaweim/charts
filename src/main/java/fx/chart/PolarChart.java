package fx.chart;

import fx.chart.data.XYItem;
import fx.chart.tools.Helper;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.awt.image.BufferedImage;


public class PolarChart<T extends XYItem> extends Region {

    private static final double PREFERRED_WIDTH = 400;
    private static final double PREFERRED_HEIGHT = 400;
    private static final double MINIMUM_WIDTH = 50;
    private static final double MINIMUM_HEIGHT = 50;
    private static final double MAXIMUM_WIDTH = 4096;
    private static final double MAXIMUM_HEIGHT = 4096;

    private double size_;
    private double width_;
    private double height_;
    private XYPane<T> xyPane_;
    private String title_;
    private StringProperty titleProperty;
    private String subTitle_;
    private StringProperty subTitleProperty;
    /**
     * the root pane to hold {@link XYPane}
     */
    private AnchorPane pane_;

    public PolarChart(final XYPane<T> XY_PANE) {
        if (XY_PANE == null) {
            throw new IllegalArgumentException("XYPane has not to be null");
        }
        if (!XY_PANE.containsPolarChart()) {
            throw new IllegalArgumentException("No Polar chart in XYPane");
        }
        xyPane_ = XY_PANE;
        width_ = PREFERRED_WIDTH;
        height_ = PREFERRED_HEIGHT;
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

        pane_ = new AnchorPane(xyPane_);

        getChildren().setAll(pane_);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
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

    public String getTitle() {
        return titleProperty == null ? title_ : titleProperty.get();
    }

    public void setTitle(final String TITLE) {
        if (titleProperty == null) {
            title_ = TITLE;
            xyPane_.redraw();
        } else {
            titleProperty.set(TITLE);
        }
    }

    public StringProperty titleProperty() {
        if (null == titleProperty) {
            titleProperty = new StringPropertyBase(title_) {
                @Override
                protected void invalidated() {xyPane_.redraw();}

                @Override
                public Object getBean() {return PolarChart.this;}

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
            xyPane_.redraw();
        } else {
            subTitleProperty.set(SUB_TITLE);
        }
    }

    public StringProperty subTitleProperty() {
        if (null == subTitleProperty) {
            subTitleProperty = new StringPropertyBase(subTitle_) {
                @Override
                protected void invalidated() {xyPane_.redraw();}

                @Override
                public Object getBean() {return PolarChart.this;}

                @Override
                public String getName() {return "subTitle";}
            };
            subTitle_ = null;
        }
        return subTitleProperty;
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
        return Helper.renderToImage(PolarChart.this, width, height, filename);
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     *
     * @param width  The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return A BufferedImage of this chart in the given dimension
     */
    public BufferedImage renderToImage(final int width, final int height) {
        return Helper.renderToImage(PolarChart.this, width, height);
    }

    public XYPane<T> getXYPane() {return xyPane_;}

    public void refresh() {xyPane_.redraw();}

    private void resize() {
        width_ = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height_ = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size_ = Math.min(width_, height_);

        if (width_ > 0 && height_ > 0) {
            pane_.setMaxSize(size_, size_);
            pane_.setPrefSize(size_, size_);
            pane_.relocate((getWidth() - size_) * 0.5, (getHeight() - size_) * 0.5);
            xyPane_.setMaxSize(size_, size_);
            xyPane_.setPrefSize(size_, size_);
        }
    }
}
