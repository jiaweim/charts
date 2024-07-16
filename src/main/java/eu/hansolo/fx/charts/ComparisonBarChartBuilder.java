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

import eu.hansolo.fx.charts.data.ChartItem;
import eu.hansolo.fx.charts.series.ChartItemSeries;
import eu.hansolo.fx.charts.tools.NumberFormat;
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


public class ComparisonBarChartBuilder<B extends ComparisonBarChartBuilder<B>> {
    private HashMap<String, Property>  properties = new HashMap<>();
    private ChartItemSeries<ChartItem> series1;
    private ChartItemSeries<ChartItem> series2;


    // ******************** Constructors **************************************
    protected ComparisonBarChartBuilder(final ChartItemSeries<ChartItem> SERIES_1, final ChartItemSeries<ChartItem> SERIES_2) {
        series1 = SERIES_1;
        series2 = SERIES_2;
    }


    // ******************** Methods *******************************************
    public static final ComparisonBarChartBuilder create(final ChartItemSeries<ChartItem> series1, final ChartItemSeries<ChartItem> series2) {
        return new ComparisonBarChartBuilder(series1, series2);
    }

    public final B backgroundFill(final Paint backgroundFill) {
        properties.put("backgroundFill", new SimpleObjectProperty<>(backgroundFill));
        return (B)this;
    }

    public final B categoryBackgroundFill(final Paint categoryBackgroundFill) {
        properties.put("categoryBackgroundFill", new SimpleObjectProperty<>(categoryBackgroundFill));
        return (B)this;
    }

    public final B barBackgroundFill(final Color barBackgroundFill) {
        properties.put("barBackgroundFill", new SimpleObjectProperty<>(barBackgroundFill));
        return (B)this;
    }

    public final B textFill(final Color textFill) {
        properties.put("textFill", new SimpleObjectProperty<>(textFill));
        return (B)this;
    }

    public final B categoryTextFill(final Color categoryTextFill) {
        properties.put("categoryTextFill", new SimpleObjectProperty<>(categoryTextFill));
        return (B)this;
    }

    public final B betterColor(final Color betterColor) {
        properties.put("betterColor", new SimpleObjectProperty<>(betterColor));
        return (B)this;
    }

    public final B poorerColor(final Color poorerColor) {
        properties.put("poorerColor", new SimpleObjectProperty<>(poorerColor));
        return (B)this;
    }

    public final B betterDarkerColor(final Color betterDarkerColor) {
        properties.put("betterDarkerColor", new SimpleObjectProperty<>(betterDarkerColor));
        return (B)this;
    }

    public final B betterBrighterColor(final Color betterBrighterColor) {
        properties.put("betterBrighterColor", new SimpleObjectProperty<>(betterBrighterColor));
        return (B)this;
    }

    public final B poorerDarkerColor(final Color poorerDarkerColor) {
        properties.put("poorerDarkerColor", new SimpleObjectProperty<>(poorerDarkerColor));
        return (B)this;
    }

    public final B poorerBrighterColor(final Color poorerBrighterColor) {
        properties.put("poorerBrighterColor", new SimpleObjectProperty<>(poorerBrighterColor));
        return (B)this;
    }

    public final B barBackgroundVisible(final boolean barBackgroundVisible) {
        properties.put("barBackgroundVisible", new SimpleBooleanProperty(barBackgroundVisible));
        return (B)this;
    }

    public final B shadowsVisible(final boolean shadowsVisible) {
        properties.put("shadowsVisible", new SimpleBooleanProperty(shadowsVisible));
        return (B)this;
    }

    public final B categorySumVisible(final boolean categorySumVisible) {
        properties.put("categorySumVisible", new SimpleBooleanProperty(categorySumVisible));
        return (B)this;
    }

    public final B numberFormat(final NumberFormat numberFormat) {
        properties.put("numberFormat", new SimpleObjectProperty(numberFormat));
        return (B)this;
    }

    public final B doCompare(final boolean doCompare) {
        properties.put("doCompare", new SimpleBooleanProperty(doCompare));
        return (B)this;
    }

    public final B useItemTextFill(final boolean useItemTextFill) {
        properties.put("useItemTextFill", new SimpleBooleanProperty(useItemTextFill));
        return (B)this;
    }

    public final B useCategoryTextFill(final boolean useCategoryTextFill) {
        properties.put("useCategoryTextFill", new SimpleBooleanProperty(useCategoryTextFill));
        return (B)this;
    }

    public final B shortenNumbers(final boolean shortenNumbers) {
        properties.put("shortenNumbers", new SimpleBooleanProperty(shortenNumbers));
        return (B)this;
    }

    public final B sorted(final boolean sorted) {
        properties.put("sorted", new SimpleBooleanProperty(sorted));
        return (B)this;
    }

    public final B order(final Order order) {
        properties.put("order", new SimpleObjectProperty<>(order));
        return (B)this;
    }

    // General properties
    public final B prefSize(final double width, final double height) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }
    public final B minSize(final double width, final double height) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }
    public final B maxSize(final double width, final double height) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(width, height)));
        return (B) this;
    }

    public final B prefWidth(final double prefWidth) {
        properties.put("prefWidth", new SimpleDoubleProperty(prefWidth));
        return (B) this;
    }
    public final B prefHeight(final double prefHeight) {
        properties.put("prefHeight", new SimpleDoubleProperty(prefHeight));
        return (B) this;
    }

    public final B minWidth(final double minWidth) {
        properties.put("minWidth", new SimpleDoubleProperty(minWidth));
        return (B) this;
    }
    public final B minHeight(final double minHeight) {
        properties.put("minHeight", new SimpleDoubleProperty(minHeight));
        return (B) this;
    }

    public final B maxWidth(final double maxWidth) {
        properties.put("maxWidth", new SimpleDoubleProperty(maxWidth));
        return (B) this;
    }
    public final B maxHeight(final double maxHeight) {
        properties.put("maxHeight", new SimpleDoubleProperty(maxHeight));
        return (B) this;
    }

    public final B scaleX(final double scaleX) {
        properties.put("scaleX", new SimpleDoubleProperty(scaleX));
        return (B) this;
    }
    public final B scaleY(final double scaleY) {
        properties.put("scaleY", new SimpleDoubleProperty(scaleY));
        return (B) this;
    }

    public final B layoutX(final double layoutX) {
        properties.put("layoutX", new SimpleDoubleProperty(layoutX));
        return (B) this;
    }
    public final B layoutY(final double layoutY) {
        properties.put("layoutY", new SimpleDoubleProperty(layoutY));
        return (B) this;
    }

    public final B translateX(final double translateX) {
        properties.put("translateX", new SimpleDoubleProperty(translateX));
        return (B) this;
    }
    public final B translateY(final double translateY) {
        properties.put("translateY", new SimpleDoubleProperty(translateY));
        return (B) this;
    }

    public final B padding(final Insets insets) {
        properties.put("padding", new SimpleObjectProperty<>(insets));
        return (B) this;
    }


    public final ComparisonBarChart build() {
        final ComparisonBarChart control = new ComparisonBarChart(series1, series2);

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"               -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"                -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"                -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"              -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"             -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"               -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"              -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"               -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"              -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"                 -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"                 -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"                -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"                -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"             -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"             -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"                -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "backgroundFill"         -> control.setBackgroundFill(((ObjectProperty<Paint>) property).get());
                case "categoryBackgroundFill" -> control.setCategoryBackgroundFill(((ObjectProperty<Paint>) property).get());
                case "barBackgroundFill"      -> control.setBarBackgroundFill(((ObjectProperty<Color>) property).get());
                case "textFill"               -> control.setTextFill(((ObjectProperty<Color>) property).get());
                case "categoryTextFill"       -> control.setCategoryTextFill(((ObjectProperty<Color>) property).get());
                case "betterColor"            -> control.setBetterColor(((ObjectProperty<Color>) property).get());
                case "poorer"                 -> control.setPoorerColor(((ObjectProperty<Color>) property).get());
                case "betterDarkerColor"      -> control.setBetterDarkerColor(((ObjectProperty<Color>) property).get());
                case "betterBrighterColor"    -> control.setBetterBrighterColor(((ObjectProperty<Color>) property).get());
                case "poorerDarkerColor"      -> control.setPoorerDarkerColor(((ObjectProperty<Color>) property).get());
                case "poorerBrighterColor"    -> control.setPoorerBrighterColor(((ObjectProperty<Color>) property).get());
                case "barBackgroundVisible"   -> control.setBarBackgroundVisible(((BooleanProperty) property).get());
                case "shadowsVisible"         -> control.setShadowsVisible(((BooleanProperty) property).get());
                case "categorySumVisible"     -> control.setCategorySumVisible(((BooleanProperty) property).get());
                case "numberFormat"           -> control.setNumberFormat(((ObjectProperty<NumberFormat>) property).get());
                case "doCompare"              -> control.setDoCompare(((BooleanProperty) property).get());
                case "useItemTextFill"        -> control.setUseItemTextFill(((BooleanProperty) property).get());
                case "useCategoryTextFill"    -> control.setUseCategoryTextFill(((BooleanProperty) property).get());
                case "shortenNumbers"         -> control.setShortenNumbers(((BooleanProperty) property).get());
                case "sorted"                 -> control.setSorted(((BooleanProperty) property).get());
                case "order"                  -> control.setOrder(((ObjectProperty<Order>) property).get());
            }
        });
        return control;
    }
}
