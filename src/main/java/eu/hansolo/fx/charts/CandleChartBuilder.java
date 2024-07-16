/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2022 Gerrit Grunwald.
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

import eu.hansolo.fx.charts.data.CandleChartItem;
import eu.hansolo.fx.charts.data.ChartItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class CandleChartBuilder<B extends CandleChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected CandleChartBuilder() {}


    // ******************** Methods *******************************************
    public static final CandleChartBuilder create() {
        return new CandleChartBuilder();
    }

    public final B items(final ChartItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B items(final List<ChartItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B decimals(final int DECIMALS) {
        properties.put("decimals", new SimpleIntegerProperty(DECIMALS));
        return (B)this;
    }

    public final B locale(final Locale LOCALE) {
        properties.put("locale", new SimpleObjectProperty<>(LOCALE));
        return (B)this;
    }

    public final B backgroundColor(final Color BACKGROUND_COLOR) {
        properties.put("backgroundColor", new SimpleObjectProperty<>(BACKGROUND_COLOR));
        return (B)this;
    }

    public final B bullishColor(final Color BULLISH_COLOR) {
        properties.put("bullishColor", new SimpleObjectProperty<>(BULLISH_COLOR));
        return (B)this;
    }

    public final B bearishColor(final Color BEARISH_COLOR) {
        properties.put("bearishColor", new SimpleObjectProperty<>(BEARISH_COLOR));
        return (B)this;
    }

    public final B strokeColor(final Color STROKE_COLOR) {
        properties.put("strokeColor", new SimpleObjectProperty<>(STROKE_COLOR));
        return (B)this;
    }

    public final B endLinesVisible(final boolean END_LINES_VISIBLE) {
        properties.put("endLinesVisible", new SimpleBooleanProperty(END_LINES_VISIBLE));
        return (B)this;
    }

    public final B useItemColorForStroke(final boolean USE_ITEM_COLOR_FOR_STROKE) {
        properties.put("useItemColorForStroke", new SimpleBooleanProperty(USE_ITEM_COLOR_FOR_STROKE));
        return (B)this;
    }

    public final B minNumberOfItems(final int MIN_NUMBER_OF_ITEMS) {
        properties.put("minNumberOfItems", new SimpleIntegerProperty(MIN_NUMBER_OF_ITEMS));
        return (B)this;
    }

    public final B useMinNumberOfItems(final boolean USE_MIN_NUMBER_OF_ITEMS) {
        properties.put("useMinNumberOfItems", new SimpleBooleanProperty(USE_MIN_NUMBER_OF_ITEMS));
        return (B)this;
    }

    public final B popupTimeout(final long POPUP_TIMEOUT) {
        properties.put("popupTimeout", new SimpleLongProperty(POPUP_TIMEOUT));
        return (B)this;
    }

    public final B yAxis(final Axis Y_AXIS) {
        properties.put("yAxis", new SimpleObjectProperty<>(Y_AXIS));
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


    public final CandleChart build() {
        final CandleChart control = new CandleChart();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<CandleChartItem[]>) properties.get("itemsArray")).get());
        } else if(properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<CandleChartItem>>) properties.get("itemsList")).get());
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
                case "prefWidth"             -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"            -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"              -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"             -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"              -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"             -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"                -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"                -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"               -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"               -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"            -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"            -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"               -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "decimals"              -> control.setDecimals(((IntegerProperty) property).get());
                case "locale"                -> control.setLocale(((ObjectProperty<Locale>) property).get());
                case "backgroundColor"       -> control.setBackgroundColor(((ObjectProperty<Color>) property).get());
                case "bullishColor"          -> control.setBullishColor(((ObjectProperty<Color>) property).get());
                case "bearishColor"          -> control.setBearishColor(((ObjectProperty<Color>) property).get());
                case "strokeColor"           -> control.setStrokeColor(((ObjectProperty<Color>) property).get());
                case "endLinesVisible"       -> control.setEndLinesVisible(((BooleanProperty) property).get());
                case "useItemColorForStroke" -> control.setUseItemColorForStroke(((BooleanProperty) property).get());
                case "minNumberOfItems"      -> control.setMinNumberOfItems(((IntegerProperty) property).get());
                case "useMinNumberOfItems"   -> control.setUseMinNumberOfItems(((BooleanProperty) property).get());
                case "popupTimeout"          -> control.setPopupTimeout(((LongProperty) property).get());
            }
        });
        if (properties.containsKey("yAxis")) { control.setYAxis(((ObjectProperty<Axis>) properties.get("yAxis")).get()); }
        return control;
    }
}
