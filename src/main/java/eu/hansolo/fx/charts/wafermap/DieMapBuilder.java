/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2023 Gerrit Grunwald.
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

package eu.hansolo.fx.charts.wafermap;

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

import java.util.HashMap;
import java.util.Map;


public class DieMapBuilder <B extends DieMapBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected DieMapBuilder() { }


    // ******************** Methods *******************************************
    public static final DieMapBuilder create() {
        return new DieMapBuilder();
    }


    public final B die(final Die die) {
        properties.put("die", new SimpleObjectProperty<>(die));
        return (B)this;
    }

    public final B dieFill(final Color dieFill) {
        properties.put("dieFill", new SimpleObjectProperty<>(dieFill));
        return (B)this;
    }

    public final B dieStroke(final Color dieStroke) {
        properties.put("dieStroke", new SimpleObjectProperty<>(dieStroke));
        return (B)this;
    }

    public final B defectFill(final Color defectFill) {
        properties.put("defectFill", new SimpleObjectProperty<>(defectFill));
        return (B)this;
    }

    public final B defectStroke(final Color defectStroke) {
        properties.put("defectStroke", new SimpleObjectProperty<>(defectStroke));
        return (B)this;
    }

    public final B dieTextFill(final Color dieTextFill) {
        properties.put("dieTextFill", new SimpleObjectProperty<>(dieTextFill));
        return (B)this;
    }

    public final B dieTextVisible(final boolean dieTextVisible) {
        properties.put("dieTextVisible", new SimpleBooleanProperty(dieTextVisible));
        return (B)this;
    }

    public final B densityColorsVisible(final boolean densityColorsVisible) {
        properties.put("densityColorsVisible", new SimpleBooleanProperty(densityColorsVisible));
        return (B)this;
    }

    public final B classConfigMap(final Map<Integer, ClassConfig> classConfigMap) {
        properties.put("classConfigMap", new SimpleObjectProperty<>(classConfigMap));
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


    public final DieMap build() {
        final DieMap control = new DieMap();
        
        if (properties.keySet().contains("die")) { control.setDie(((ObjectProperty<Die>) properties.get("die")).get()); }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"             -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"              -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"              -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"            -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"           -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"             -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"            -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"             -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"            -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"               -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"               -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"              -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"              -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"           -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"           -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"              -> control.setPadding(((ObjectProperty<Insets>) property).get());

                case "waferFill"            -> control.setDieFill(((ObjectProperty<Color>) property).get());
                case "waferStroke"          -> control.setDieStroke(((ObjectProperty<Color>) property).get());
                case "defectFill"           -> control.setDefectFill(((ObjectProperty<Color>) property).get());
                case "defectStroke"         -> control.setDefectStroke(((ObjectProperty<Color>) property).get());
                case "dieTextFill"          -> control.setDieTextFill(((ObjectProperty<Color>) property).get());
                case "dieTextVisible"       -> control.setDieTextVisible(((BooleanProperty) property).get());
                case "densityColorsVisible" -> control.setDensityColorsVisible(((BooleanProperty) property).get());
                case "classConfigMap"       -> control.setClassConfigMap(((ObjectProperty<Map<Integer, ClassConfig>>) property).get());
            }
        });
        return control;
    }
}
