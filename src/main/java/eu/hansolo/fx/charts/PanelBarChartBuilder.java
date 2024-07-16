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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class PanelBarChartBuilder<B extends PanelBarChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();
    private List<? extends Category>  categories;


    // ******************** Constructors **************************************
    protected PanelBarChartBuilder(final List<? extends Category> categories) {
        this.categories = categories;
    }


    // ******************** Methods *******************************************
    public static final PanelBarChartBuilder create(List<? extends Category> categories) {
        if (null == categories) { throw new IllegalArgumentException("categories cannot be null"); }
        return new PanelBarChartBuilder(categories);
    }


    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return (B)this;
    }

    public final B nameColor(final Color COLOR) {
        properties.put("nameColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B listOfSeries(final ChartItemSeries<ChartItem>... SERIES) {
        return listOfSeries(Arrays.asList(SERIES));
    }

    public final B listOfSeries(final List<ChartItemSeries<ChartItem>> SERIES) {
        properties.put("chartItemSeriesList", new SimpleObjectProperty<>(SERIES));
        return (B)this;
    }

    public final B chartBackground(final Color COLOR) {
        properties.put("chartBackground", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B categoryNameColor(final Color COLOR) {
        properties.put("categoryNameColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B categorySumColor(final Color COLOR) {
        properties.put("categorySumColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B seriesNameColor(final Color COLOR) {
        properties.put("seriesNameColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B seriesSumColor(final Color COLOR) {
        properties.put("seriesSumColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B gridColor(final Color COLOR) {
        properties.put("gridColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B colorByCategory(final boolean COLOR_BY_CATEGORY) {
        properties.put("colorByCategory", new SimpleBooleanProperty(COLOR_BY_CATEGORY));
        return (B)this;
    }

    public final B comparisonEnabled(final boolean ENABLED) {
        properties.put("comparisonEnabled", new SimpleBooleanProperty(ENABLED));
        return (B)this;
    }

    public final B comparisonName(final String NAME) {
        properties.put("comparisonName", new SimpleStringProperty(NAME));
        return (B)this;
    }

    public final B comparisonNameColor(final Color COLOR) {
        properties.put("comparisonNameColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B comparisonListOfSeries(final ChartItemSeries<ChartItem>... SERIES) {
        return comparisonListOfSeries(Arrays.asList(SERIES));
    }

    public final B comparisonListOfSeries(final List<ChartItemSeries<ChartItem>> SERIES) {
        properties.put("comparisonChartItemSeriesList", new SimpleObjectProperty<>(SERIES));
        return (B)this;
    }

    public final B comparisonCategorySumColor(final Color COLOR) {
        properties.put("comparisonCategorySumColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B comparisonSeriesNameColor(final Color COLOR) {
        properties.put("comparisonSeriesNameColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B comparisonSeriesSumColor(final Color COLOR) {
        properties.put("comparisonSeriesSumColor", new SimpleObjectProperty<>(COLOR));
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


    public final PanelBarChart build() {
        List<ChartItemSeries<ChartItem>> listOfSeries = new ArrayList<>();
        if(properties.keySet().contains("chartItemSeriesList")) {
            listOfSeries.addAll(((ObjectProperty<List<ChartItemSeries<ChartItem>>>) properties.get("chartItemSeriesList")).get());
        }

        final PanelBarChart control = new PanelBarChart(categories, listOfSeries);

        List<ChartItemSeries<ChartItem>> comparisonListOfSeries = new ArrayList<>();
        if (properties.keySet().contains("comparisonChartItemSeriesList")) {
            comparisonListOfSeries.addAll(((ObjectProperty<List<ChartItemSeries<ChartItem>>>) properties.get("comparisonChartItemSeriesList")).get());
        }
        control.setComparisonListOfSeries(comparisonListOfSeries);

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"                   -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"                    -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"                    -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"                  -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"                 -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"                   -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"                  -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"                   -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"                  -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"                     -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"                     -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"                    -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"                    -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"                 -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"                 -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"                    -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "chartBackground"            -> control.setChartBackground(((ObjectProperty<Color>) property).get());
                case "name"                       -> control.setName(((StringProperty) property).get());
                case "nameColor"                  -> control.setNameColor(((ObjectProperty<Color>) property).get());
                case "categoryNameColor"          -> control.setCategoryNameColor(((ObjectProperty<Color>) property).get());
                case "categorySumColor"           -> control.setCategorySumColor(((ObjectProperty<Color>) property).get());
                case "seriesNameColor"            -> control.setSeriesNameColor(((ObjectProperty<Color>) property).get());
                case "seriesSumColor"             -> control.setSeriesSumColor(((ObjectProperty<Color>) property).get());
                case "gridColor"                  -> control.setGridColor(((ObjectProperty<Color>) property).get());
                case "colorByCategory"            -> control.setColorByCategory(((BooleanProperty) property).get());
                case "comparisonEnabled"          -> control.setComparisonEnabled(((BooleanProperty) property).get());
                case "comparisonName"             -> control.setComparisonName(((StringProperty) property).get());
                case "comparisonNameColor"        -> control.setComparisonNameColor(((ObjectProperty<Color>) property).get());
                case "comparisonCategorySumColor" -> control.setComparisonCategorySumColor(((ObjectProperty<Color>) property).get());
                case "comparisonSeriesNameColor"  -> control.setComparisonSeriesNameColor(((ObjectProperty<Color>) property).get());
                case "comparisonSeriesSumColor"   -> control.setComparisonSeriesSumColor(((ObjectProperty<Color>) property).get());
            }
        });
        return control;
    }
}
