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

import eu.hansolo.fx.charts.PixelMatrix.PixelShape;
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


public class PixelMatrixBuilder<B extends PixelMatrixBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected PixelMatrixBuilder() {}


    // ******************** Methods *******************************************
    public static final PixelMatrixBuilder create() {
        return new PixelMatrixBuilder();
    }

    public final B colsAndRows(final int[] COLS_AND_ROWS) { return colsAndRows(COLS_AND_ROWS[0], COLS_AND_ROWS[1]); }
    public final B colsAndRows(final int COLS, final int ROWS) {
        properties.put("cols", new SimpleIntegerProperty(COLS));
        properties.put("rows", new SimpleIntegerProperty(ROWS));
        return (B)this;
    }

    public final B pixelOnColor(final Color COLOR) {
        properties.put("pixelOnColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }
    public final B pixelOffColor(final Color COLOR) {
        properties.put("pixelOffColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B pixelShape(final PixelShape SHAPE) {
        properties.put("pixelShape", new SimpleObjectProperty(SHAPE));
        return (B)this;
    }

    public final B useSpacer(final boolean USE) {
        properties.put("useSpacer", new SimpleBooleanProperty(USE));
        return (B)this;
    }

    public final B squarePixels(final boolean SQUARE) {
        properties.put("squarePixels", new SimpleBooleanProperty(SQUARE));
        return (B)this;
    }

    public final B spacerSizeFactor(final double FACTOR) {
        properties.put("spacerSizeFactor", new SimpleDoubleProperty(FACTOR));
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


    public final PixelMatrix build() {
        final PixelMatrix control;
        if (properties.keySet().contains("cols") && properties.keySet().contains("rows")) {
            int cols = ((IntegerProperty) properties.get("cols")).get();
            int rows = ((IntegerProperty) properties.get("rows")).get();
            control = new PixelMatrix(cols, rows);
        } else {
            control = new PixelMatrix();
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"         -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"          -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"          -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"        -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"       -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"         -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"        -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"         -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"        -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"           -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"           -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"          -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"          -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"       -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"       -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"          -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "pixelOnColor"     -> control.setPixelOnColor(((ObjectProperty<Color>) property).get());
                case "pixelOffColor"    -> control.setPixelOffColor(((ObjectProperty<Color>) property).get());
                case "pixelShape"       -> control.setPixelShape(((ObjectProperty<PixelShape>) property).get());
                case "useSpacer"        -> control.setUseSpacer(((BooleanProperty) property).get());
                case "spacerSizeFactor" -> control.setSpacerSizeFactor(((DoubleProperty) property).get());
                case "squarePixels"     -> control.setSquarePixels(((BooleanProperty) property).get());
            }
        });
        return control;
    }
}