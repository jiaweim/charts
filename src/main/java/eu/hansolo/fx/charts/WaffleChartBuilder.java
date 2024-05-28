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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;

import java.util.HashMap;


public class WaffleChartBuilder  <B extends WaffleChartBuilder<B>> {
    private HashMap<String, Property>  properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected WaffleChartBuilder() { }


    // ******************** Methods *******************************************
    public static final WaffleChartBuilder create() {
        return new WaffleChartBuilder();
    }

    public final B value(final double value) {
        properties.put("value", new SimpleDoubleProperty(value));
        return (B)this;
    }

    public final B backgroundFill(final Paint backgroundFill) {
        properties.put("backgroundFill", new SimpleObjectProperty<>(backgroundFill));
        return (B)this;
    }

    public final B cellFill(final Paint cellFill) {
        properties.put("cellFill", new SimpleObjectProperty<>(cellFill));
        return (B)this;
    }

    public final B emptyCellFill(final Paint emptyCellFill) {
        properties.put("emptyCellFill", new SimpleObjectProperty<>(emptyCellFill));
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


    public final WaffleChart build() {
        final WaffleChart waffleChart = new WaffleChart();

        for (String key : properties.keySet()) {
            switch (key) {
                case "prefSize"       -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                    waffleChart.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"        -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                    waffleChart.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"        -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) properties.get(key)).get();
                    waffleChart.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"      -> waffleChart.setPrefWidth(((DoubleProperty) properties.get(key)).get());
                case "prefHeight"     -> waffleChart.setPrefHeight(((DoubleProperty) properties.get(key)).get());
                case "minWidth"       -> waffleChart.setMinWidth(((DoubleProperty) properties.get(key)).get());
                case "minHeight"      -> waffleChart.setMinHeight(((DoubleProperty) properties.get(key)).get());
                case "maxWidth"       -> waffleChart.setMaxWidth(((DoubleProperty) properties.get(key)).get());
                case "maxHeight"      -> waffleChart.setMaxHeight(((DoubleProperty) properties.get(key)).get());
                case "scaleX"         -> waffleChart.setScaleX(((DoubleProperty) properties.get(key)).get());
                case "scaleY"         -> waffleChart.setScaleY(((DoubleProperty) properties.get(key)).get());
                case "layoutX"        -> waffleChart.setLayoutX(((DoubleProperty) properties.get(key)).get());
                case "layoutY"        -> waffleChart.setLayoutY(((DoubleProperty) properties.get(key)).get());
                case "translateX"     -> waffleChart.setTranslateX(((DoubleProperty) properties.get(key)).get());
                case "translateY"     -> waffleChart.setTranslateY(((DoubleProperty) properties.get(key)).get());
                case "padding"        -> waffleChart.setPadding(((ObjectProperty<Insets>) properties.get(key)).get());
                case "backgroundFill" -> waffleChart.setBackgroundFill(((ObjectProperty<Paint>) properties.get(key)).get());
                case "cellFill"       -> waffleChart.setCellFill(((ObjectProperty<Paint>) properties.get(key)).get());
                case "emptyCellFill"  -> waffleChart.setEmptyCellFill(((ObjectProperty<Paint>) properties.get(key)).get());
            }
        }
        return waffleChart;
    }
}
