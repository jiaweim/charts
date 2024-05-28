/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2024 Gerrit Grunwald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.charts;

import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.series.Series;
import eu.hansolo.fx.charts.tools.Helper;
import javafx.beans.DefaultProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.image.BufferedImage;


@DefaultProperty("children")
public class WaffleChart extends Region {
    private static final double                PREFERRED_WIDTH  = 250;
    private static final double                PREFERRED_HEIGHT = 250;
    private static final double                MINIMUM_WIDTH    = 50;
    private static final double                MINIMUM_HEIGHT   = 50;
    private static final double                MAXIMUM_WIDTH    = 4096;
    private static final double                MAXIMUM_HEIGHT   = 4096;
    private              double                size;
    private              double                width;
    private              double                height;
    private              Canvas                canvas;
    private              GraphicsContext       ctx;
    private              Pane                  pane;
    private              double                _value;
    private              DoubleProperty        value;
    private              Paint                 _backgroundFill;
    private              ObjectProperty<Paint> backgroundFill;
    private              Paint                 _cellFill;
    private              ObjectProperty<Paint> cellFill;
    private              Paint                 _emptyCellFill;
    private              ObjectProperty<Paint> emptyCellFill;
    private              double                inset;
    private              double                chartWidth;
    private              double                chartHeight;
    private              double                gap;
    private              double                cellWidth;
    private              double                cellHeight;
    private              double                cellRadius;


    // ******************** Constructors **************************************
    public WaffleChart() {
        this(0);
    }
    public WaffleChart(final double value) {
        if (value < 0 || value > 1) { throw new IllegalArgumentException("Value must be between 0...1"); }
        this._value          = value;
        this._backgroundFill = Color.web("#f0f0f0");
        this._emptyCellFill  = Color.web("#cdcdcd");
        this._cellFill       = Color.web("#3ca9e2");
        this.inset           = 5;
        this.chartWidth      = this.size - 2 * inset;
        this.chartHeight     = this.size - 2 * inset;
        this.gap             = 1;
        this.cellWidth       = (chartWidth - (9 * gap)) / 10;
        this.cellHeight      = (chartHeight - (9 * gap)) / 10;
        this.cellRadius      = cellWidth * 0.25;
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

        getStyleClass().add("comparison-ring-chart");

        canvas = new Canvas(size * 0.9, 0.9);
        ctx    = canvas.getGraphicsContext2D();

        pane = new Pane(canvas);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override public void layoutChildren() {
        super.layoutChildren();
    }

    @Override protected double computeMinWidth(final double height) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double width) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double height) { return super.computePrefWidth(height); }
    @Override protected double computePrefHeight(final double width) { return super.computePrefHeight(width); }
    @Override protected double computeMaxWidth(final double height) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double width) { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }

    public double getValue() { return null == this.value ? this._value : this.value.get(); }
    public void setValue(final double value) {
        if (value < 0 || value > 1) { throw new IllegalArgumentException("Value must be between 0...1"); }
        if (null == this.value) {
            this._value = value;
            redraw();
        } else {
            this.value.set(value);
        }
    }
    public DoubleProperty valueProperty() {
        if (null == this.value) {
            this.value = new DoublePropertyBase(this._value) {
                @Override protected void invalidated() {
                    if (get() < 0 || get() > 1) { throw new IllegalArgumentException("Value must be between 0...1"); }
                    redraw();
                }
                @Override public Object getBean() { return WaffleChart.this; }
                @Override public String getName() { return "value"; }
            };
        }
        return this.value;
    }

    public Paint getBackgroundFill() { return null == backgroundFill ? _backgroundFill : backgroundFill.get(); }
    public void setBackgroundFill(final Paint backgroundFill) {
        if (null == this.backgroundFill) {
            _backgroundFill = backgroundFill;
            redraw();
        } else {
            this.backgroundFill.set(backgroundFill);
        }
    }
    public ObjectProperty<Paint> backgroundFillProperty() {
        if (null == backgroundFill) {
            backgroundFill = new ObjectPropertyBase<>(_backgroundFill) {
                @Override protected void invalidated() { redraw(); }
                @Override public Object getBean() { return WaffleChart.this; }
                @Override public String getName() { return "backgroundFill"; }
            };
            _backgroundFill = null;
        }
        return backgroundFill;
    }

    public Paint getCellFill() { return null == cellFill ? _cellFill : cellFill.get(); }
    public void setCellFill(final Paint cellFill) {
        if (null == this.cellFill) {
            _cellFill = cellFill;
            redraw();
        } else {
            this.cellFill.set(cellFill);
        }
    }
    public ObjectProperty<Paint> cellFillProperty() {
        if (null == cellFill) {
            cellFill = new ObjectPropertyBase<>(_cellFill) {
                @Override protected void invalidated() { redraw(); }
                @Override public Object getBean() { return WaffleChart.this; }
                @Override public String getName() { return "cellFill"; }
            };
            _cellFill = null;
        }
        return cellFill;
    }

    public Paint getEmptyCellFill() { return null == emptyCellFill ? _emptyCellFill : emptyCellFill.get(); }
    public void setEmptyCellFill(final Paint emptyCellFill) {
        if (null == this.emptyCellFill) {
            _emptyCellFill = emptyCellFill;
            redraw();
        } else {
            this.emptyCellFill.set(emptyCellFill);
        }
    }
    public ObjectProperty<Paint> emptyCellFillProperty() {
        if (null == emptyCellFill) {
            emptyCellFill = new ObjectPropertyBase<>(_emptyCellFill) {
                @Override protected void invalidated() { redraw(); }
                @Override public Object getBean() { return WaffleChart.this; }
                @Override public String getName() { return "emptyCellFill"; }
            };
            _emptyCellFill = null;
        }
        return emptyCellFill;
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     * @param filename The path and name of the file  /Users/hansolo/Desktop/plot.png
     * @param width The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return True if the procedure was successful, otherwise false
     */
    public boolean renderToImage(final String filename, final int width, final int height) {
        return Helper.renderToImage(WaffleChart.this, width, height, filename);
    }

    /**
     * Calling this method will render this chart/plot to a png given of the given width and height
     * @param width The width of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @param height The height of the final image in pixels (if &lt; 0 then 400 and if &gt; 4096 then 4096)
     * @return A BufferedImage of this chart in the given dimension
     */
    public BufferedImage renderToImage(final int width, final int height) {
        return Helper.renderToImage(WaffleChart.this, width, height);
    }


    // ******************** Drawing *******************************************
    private void prepareSeries(final Series<ChartItem> SERIES) {
        boolean animated          = SERIES.isAnimated();
        long    animationDuration = SERIES.getAnimationDuration();
        SERIES.getItems().forEach(item -> {
            if (animated) { item.setAnimated(animated); }
            item.setAnimationDuration(animationDuration);
        });
    }

    private void drawChart() {
        ctx.clearRect(0, 0, width, height);
        final Paint  emptyCellFill = getEmptyCellFill();
        final Paint  cellFill      = getCellFill();
        final double value         = getValue();
        final long   boxToChange   = Math.round(100 * value);
        ctx.setFill(getBackgroundFill());
        ctx.fillRect(0, 0, width, height);
        double x = inset;
        double y = inset + (chartHeight - cellHeight);
        for (int i = 0; i < 100; i++) {
            ctx.setFill(value > 0 && i <= boxToChange ? cellFill : emptyCellFill);
            ctx.fillRoundRect(x, y, cellWidth, cellHeight, cellRadius, cellRadius);
            x += (gap + cellWidth);
            if (x > chartWidth - inset) {
                x = inset;
                y -= (gap + cellHeight);
            }
        }
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (width > 0 && height > 0) {
            this.chartWidth  = this.size - 2 * inset;
            this.chartHeight = this.size - 2 * inset;
            this.gap         = 1;
            this.cellWidth   = (chartWidth - (9 * gap)) / 10;
            this.cellHeight  = (chartHeight - (9 * gap)) / 10;
            this.cellRadius  = cellWidth * 0.25;

            pane.setMaxSize(size, size);
            pane.setPrefSize(size, size);
            pane.relocate((getWidth() - size) * 0.5, (getHeight() - size) * 0.5);

            canvas.setWidth(size);
            canvas.setHeight(size);

            redraw();
        }
    }

    private void redraw() {
        drawChart();
    }
}
