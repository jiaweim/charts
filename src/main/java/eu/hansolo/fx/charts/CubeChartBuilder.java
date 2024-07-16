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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;


public class CubeChartBuilder<B extends CubeChartBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected CubeChartBuilder() {}


    // ******************** Methods *******************************************
    public static final CubeChartBuilder create() {
        return new CubeChartBuilder();
    }

    public final B chartBackground(final Paint chartBackground) {
        properties.put("chartBackground", new SimpleObjectProperty<>(chartBackground));
        return (B)this;
    }

    public final B cubeColor(final Color cubeColor) {
        properties.put("cubeColor", new SimpleObjectProperty<>(cubeColor));
        return (B)this;
    }

    public final B cubeFrameColor(final Color cubeFrameColor) {
        properties.put("cubeFrameColor", new SimpleObjectProperty<>(cubeFrameColor));
        return (B)this;
    }

    public final B leftFill(final Paint leftFill) {
        properties.put("leftFill", new SimpleObjectProperty<>(leftFill));
        return (B)this;
    }

    public final B rightFill(final Paint rightFill) {
        properties.put("rightFill", new SimpleObjectProperty<>(rightFill));
        return (B)this;
    }

    public final B leftTextColor(final Color leftTextColor) {
        properties.put("leftTextColor", new SimpleObjectProperty(leftTextColor));
        return (B)this;
    }

    public final B leftEmptyTextColor(final Color leftEmptyTextColor) {
        properties.put("leftEmptyTextColor", new SimpleObjectProperty(leftEmptyTextColor));
        return (B)this;
    }

    public final B rightTextColor(final Color rightTextColor) {
        properties.put("rightTextColor", new SimpleObjectProperty(rightTextColor));
        return (B)this;
    }

    public final B rightEmptyTextColor(final Color rightEmptyTextColor) {
        properties.put("rightEmptyTextColor", new SimpleObjectProperty(rightEmptyTextColor));
        return (B)this;
    }

    public final B leftValue(final double leftValue) {
        properties.put("leftValue", new SimpleDoubleProperty(leftValue));
        return (B)this;
    }

    public final B rightValue(final double rightValue) {
        properties.put("rightValue", new SimpleDoubleProperty(rightValue));
        return (B)this;
    }

    public final B leftText(final String leftText) {
        properties.put("leftText", new SimpleStringProperty(leftText));
        return (B)this;
    }

    public final B rightText(final String rightText) {
        properties.put("rightText", new SimpleStringProperty(rightText));
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


    public final CubeChart build() {
        final CubeChart control = new CubeChart();

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
                case "chartBackground"     -> control.setChartBackground(((ObjectProperty<Paint>) property).get());
                case "cubeColor"           -> control.setCubeColor(((ObjectProperty<Color>) property).get());
                case "cubeFrameColor"      -> control.setCubeFrameColor(((ObjectProperty<Color>) property).get());
                case "leftFill"            -> control.setLeftFill(((ObjectProperty<Paint>) property).get());
                case "rightFill"           -> control.setRightFill(((ObjectProperty<Paint>) property).get());
                case "leftTextColor"       -> control.setLeftTextColor(((ObjectProperty<Color>) property).get());
                case "leftEmptyTextColor"  -> control.setLeftEmptyTextColor(((ObjectProperty<Color>) property).get());
                case "rightTextColor"      -> control.setLeftTextColor(((ObjectProperty<Color>) property).get());
                case "rightEmptyTextColor" -> control.setLeftEmptyTextColor(((ObjectProperty<Color>) property).get());
                case "leftValue"           -> control.setLeftValue(((DoubleProperty) property).get());
                case "rightValue"          -> control.setRightValue(((DoubleProperty) property).get());
                case "leftText"            -> control.setLeftText(((StringProperty) property).get());
                case "rightText"           -> control.setRightText(((StringProperty) property).get());
            }
        });
        return control;
    }
}
