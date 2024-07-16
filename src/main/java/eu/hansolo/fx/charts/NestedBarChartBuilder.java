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

import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.series.ChartItemSeries;
import eu.hansolo.fx.charts.tools.Order;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;


public class NestedBarChartBuilder<B extends NestedBarChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected NestedBarChartBuilder() {}


    // ******************** Methods *******************************************
    public static final NestedBarChartBuilder create() {
        return new NestedBarChartBuilder();
    }

    public final B series(final ChartItemSeries<ChartItem>... SERIES) {
        properties.put("seriesArray", new SimpleObjectProperty<>(SERIES));
        return (B)this;
    }

    public final B series(final List<ChartItemSeries<ChartItem>> SERIES) {
        properties.put("seriesList", new SimpleObjectProperty<>(SERIES));
        return (B)this;
    }

    public final B order(final Order ORDER) {
        properties.put("order", new SimpleObjectProperty<>(ORDER));
        return (B)this;
    }

    public final B chartBackground(final Paint BACKGROUND) {
        properties.put("chartBackground", new SimpleObjectProperty<>(BACKGROUND));
        return (B)this;
    }

    public final B spacer(final double SPACER) {
        properties.put("spacer", new SimpleDoubleProperty(SPACER));
        return (B)this;
    }

    public final B seriesTitleVisible(final boolean VISIBLE) {
        properties.put("seriesTitleVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B seriesTitleColor(final Color COLOR) {
        properties.put("seriesTitleColor", new SimpleObjectProperty<>(COLOR));
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


    public final NestedBarChart build() {
        final NestedBarChart control = new NestedBarChart();

        if (properties.keySet().contains("seriesArray")) {
            control.setSeries(((ObjectProperty<ChartItemSeries<ChartItem>[]>) properties.get("seriesArray")).get());
        }
        if(properties.keySet().contains("seriesList")) {
            control.setSeries(((ObjectProperty<List<ChartItemSeries<ChartItem>>>) properties.get("seriesList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"           -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"            -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"            -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"          -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"         -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"           -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"          -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"           -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"          -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"             -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"             -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"            -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"            -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"         -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"         -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"            -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "order"              -> control.setOrder(((ObjectProperty<Order>) property).get());
                case "chartBackground"    -> control.setChartBackground(((ObjectProperty<Paint>) property).get());
                case "spacer"             -> control.setSpacer(((DoubleProperty) property).get());
                case "seriesTitleColor"   -> control.setSeriesTitleColor(((ObjectProperty<Color>) property).get());
                case "seriesTitleVisible" -> control.setSeriesTitleVisible(((BooleanProperty) property).get());
            }
        });
        return control;
    }
}
