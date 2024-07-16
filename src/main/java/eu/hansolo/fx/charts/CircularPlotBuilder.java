/*
 * Copyright (c) 2017 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.charts;

import eu.hansolo.fx.charts.data.PlotItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;


public class CircularPlotBuilder<B extends CircularPlotBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected CircularPlotBuilder() {}


    // ******************** Methods *******************************************
    public static final CircularPlotBuilder create() {
        return new CircularPlotBuilder();
    }

    public final B items(final PlotItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B items(final List<PlotItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B tickMarkColor(final Color COLOR) {
        properties.put("tickMarkColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B textColor(final Color COLOR) {
        properties.put("textColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B decimals(final int DECIMALS) {
        properties.put("decimals", new SimpleIntegerProperty(DECIMALS));
        return (B)this;
    }

    public final B segmentGap(final double GAP) {
        properties.put("segmentGap", new SimpleDoubleProperty(GAP));
        return (B)this;
    }

    public final B showFlowDirection(final boolean SHOW) {
        properties.put("showFlowDirection", new SimpleBooleanProperty(SHOW));
        return (B)this;
    }

    public final B minorTickMarksVisible(final boolean VISIBLE) {
        properties.put("minorTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B mediumTickMarksVisible(final boolean VISIBLE) {
        properties.put("mediumTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B majorTickMarksVisible(final boolean VISIBLE) {
        properties.put("majorTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B tickLabelsVisible(final boolean VISIBLE) {
        properties.put("tickLabelsVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B onlyFirstAndLastTickLabelVisible(final boolean VISIBLE) {
        properties.put("onlyFirstAndLastTickLabelVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B connectionOpacity(final double OPACITY) {
        properties.put("connectionOpacity", new SimpleDoubleProperty(OPACITY));
        return (B)this;
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B minSize(final double WIDTH, final double HEIGHT) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }
    public final B maxSize(final double WIDTH, final double HEIGHT) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return (B)this;
    }

    public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B)this;
    }
    public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B)this;
    }

    public final B minWidth(final double MIN_WIDTH) {
        properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return (B)this;
    }
    public final B minHeight(final double MIN_HEIGHT) {
        properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return (B)this;
    }

    public final B maxWidth(final double MAX_WIDTH) {
        properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return (B)this;
    }
    public final B maxHeight(final double MAX_HEIGHT) {
        properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return (B)this;
    }

    public final B scaleX(final double SCALE_X) {
        properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return (B)this;
    }
    public final B scaleY(final double SCALE_Y) {
        properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return (B)this;
    }

    public final B layoutX(final double LAYOUT_X) {
        properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return (B)this;
    }
    public final B layoutY(final double LAYOUT_Y) {
        properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return (B)this;
    }

    public final B translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return (B)this;
    }
    public final B translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return (B)this;
    }

    public final B padding(final Insets INSETS) {
        properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return (B)this;
    }


    public final CircularPlot build() {
        final CircularPlot control = new CircularPlot();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<PlotItem[]>) properties.get("itemsArray")).get());
        }
        if(properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<PlotItem>>) properties.get("itemsList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"                         -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"                          -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"                          -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"                        -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"                       -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"                         -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"                        -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"                         -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"                        -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"                           -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"                           -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"                          -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"                          -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"                       -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"                       -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"                          -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "tickMarkColor"                    -> control.setTickMarkColor(((ObjectProperty<Color>) property).get());
                case "textColor"                        -> control.setTextColor(((ObjectProperty<Color>) property).get());
                case "decimals"                         -> control.setDecimals(((IntegerProperty) property).get());
                case "segmentGap"                       -> control.setSegmentGap(((DoubleProperty) property).get());
                case "showFlowDirection"                -> control.setShowFlowDirection(((BooleanProperty) property).get());
                case "minorTickMarksVisible"            -> control.setMinorTickMarksVisible(((BooleanProperty) property).get());
                case "mediumTickMarksVisible"           -> control.setMediumTickMarksVisible(((BooleanProperty) property).get());
                case "majorTickMarksVisible"            -> control.setMajorTickMarksVisible(((BooleanProperty) property).get());
                case "tickLabelsVisible"                -> control.setTickLabelsVisible(((BooleanProperty) property).get());
                case "onlyFirstAndLastTickLabelVisible" -> control.setOnlyFirstAndLastTickLabelVisible(((BooleanProperty) property).get());
                case "connectionOpactiy"                -> control.setConnectionOpacity(((DoubleProperty) property).get());
            }
        });
        return control;
    }
}
