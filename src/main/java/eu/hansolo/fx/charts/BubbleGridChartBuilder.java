/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2021 Gerrit Grunwald.
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

import eu.hansolo.fx.charts.data.BubbleGridChartItem;
import eu.hansolo.fx.charts.tools.Order;
import eu.hansolo.fx.charts.tools.Topic;
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
import javafx.scene.paint.LinearGradient;

import java.util.HashMap;
import java.util.List;


public class BubbleGridChartBuilder<B extends BubbleGridChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected BubbleGridChartBuilder() {}


    // ******************** Methods *******************************************
    public static final BubbleGridChartBuilder create() {
        return new BubbleGridChartBuilder();
    }

    public final B items(final BubbleGridChartItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B items(final List<BubbleGridChartItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B chartBackground(final Color COLOR) {
        properties.put("chartBackground", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B gridColor(final Color COLOR) {
        properties.put("gridColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B textColor(final Color COLOR) {
        properties.put("textColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B autoBubbleTextColor(final boolean AUTO) {
        properties.put("autoBubbleTextColor", new SimpleBooleanProperty(AUTO));
        return (B)this;
    }

    public final B showGrid(final boolean SHOW) {
        properties.put("showGrid", new SimpleBooleanProperty(SHOW));
        return (B)this;
    }

    public final B showValues(final boolean SHOW) {
        properties.put("showValues", new SimpleBooleanProperty(SHOW));
        return (B)this;
    }

    public final B showPercentage(final boolean SHOW) {
        properties.put("showPercentage", new SimpleBooleanProperty(SHOW));
        return (B)this;
    }

    public final B useXCategoryFill() {
        properties.put("useXCategoryFill", null);
        return (B)this;
    }

    public final B useYCategoryFill() {
        properties.put("useXCategoryFill", null);
        return (B)this;
    }

    public final B sortCategoryX(final Topic TOPIC, final Order ORDER) {
        properties.put("sortCategoryXTopic", new SimpleObjectProperty<>(TOPIC));
        properties.put("sortCategoryXOrder", new SimpleObjectProperty<>(ORDER));
        return (B) this;
    }

    public final B sortCategoryY(final Topic TOPIC, final Order ORDER) {
        properties.put("sortCategoryYTopic", new SimpleObjectProperty<>(TOPIC));
        properties.put("sortCategoryYOrder", new SimpleObjectProperty<>(ORDER));
        return (B) this;
    }

    public final B useGradientFill(final boolean USE) {
        properties.put("useGradientFill", new SimpleBooleanProperty(USE));
        return (B)this;
    }

    public final B shortenNumbers(final boolean SHORTEN) {
        properties.put("shortenNumbers", new SimpleBooleanProperty(SHORTEN));
        return (B)this;
    }

    public final B minColor(final Color MIN_COLOR) {
        properties.put("minColor", new SimpleObjectProperty<>(MIN_COLOR));
        return (B)this;
    }

    public final B maxColor(final Color MAX_COLOR) {
        properties.put("maxColor", new SimpleObjectProperty<>(MAX_COLOR));
        return (B)this;
    }

    public final B gradient(final LinearGradient GRADIENT) {
        properties.put("gradient", new SimpleObjectProperty<>(GRADIENT));
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


    public final BubbleGridChart build() {
        final BubbleGridChart control = new BubbleGridChart();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<BubbleGridChartItem[]>) properties.get("itemsArray")).get());
        }
        if(properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<BubbleGridChartItem>>) properties.get("itemsList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"            -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"             -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"             -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"           -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"          -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"            -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"           -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"            -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"           -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"              -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"              -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"             -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"             -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"          -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"          -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"             -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "chartBackground"     -> control.setChartBackground(((ObjectProperty<Color>) property).get());
                case "textColor"           -> control.setTextColor(((ObjectProperty<Color>) property).get());
                case "autoBubbleTextColor" -> control.setAutoBubbleTextColor(((BooleanProperty) property).get());
                case "gridColor"           -> control.setGridColor(((ObjectProperty<Color>) property).get());
                case "showGrid"            -> control.setShowGrid(((BooleanProperty) property).get());
                case "showValues"          -> control.setShowValues(((BooleanProperty) property).get());
                case "showPercentage"      -> control.setShowPercentage(((BooleanProperty) property).get());
                case "useXCategoryFill"    -> control.useXCategoryFill();
                case "useYCategoryFill"    -> control.useYCategoryFill();
                case "sortCategoryXTopic"  -> control.sortCategoryX(((ObjectProperty<Topic>) properties.get("sortCategoryXTopic")).get(), ((ObjectProperty<Order>) properties.get("sortCategoryXOrder")).get());
                case "sortCategoryYTopic"  -> control.sortCategoryY(((ObjectProperty<Topic>) properties.get("sortCategoryYTopic")).get(), ((ObjectProperty<Order>) properties.get("sortCategoryYOrder")).get());
                case "useGradientFill"     -> control.setUseGradientFill(((BooleanProperty) property).get());
                case "shortenNumbers"      -> control.setShortenNumbers(((BooleanProperty) property).get());
                case "minColor"            -> control.setMinColor(((ObjectProperty<Color>) property).get());
                case "maxColor"            -> control.setMaxColor(((ObjectProperty<Color>) property).get());
                case "gradient"            -> control.setGradient(((ObjectProperty<LinearGradient>) property).get());
            }
        });
        return control;
    }
}
