/*
 * Copyright (c) 2018 by Gerrit Grunwald
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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;

import java.util.HashMap;


public class GridBuilder<B extends GridBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();
    private Axis xAxis;
    private Axis yAxis;


    // ******************** Constructors **************************************
    protected GridBuilder(final Axis X_AXIS, final Axis Y_AXIS) {
        xAxis = X_AXIS;
        yAxis = Y_AXIS;
    }


    // ******************** Methods *******************************************
    public static final GridBuilder create(final Axis X_AXIS, final Axis Y_AXIS) {
        return new GridBuilder(X_AXIS, Y_AXIS);
    }

    public final B majorHGridLinePaint(final Paint PAINT) {
        properties.put("majorHGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B mediumHGridLinePaint(final Paint PAINT) {
        properties.put("mediumHGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B minorHGridLinePaint(final Paint PAINT) {
        properties.put("minorHGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B majorVGridLinePaint(final Paint PAINT) {
        properties.put("majorVGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B mediumVGridLinePaint(final Paint PAINT) {
        properties.put("mediumVGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B minorVGridLinePaint(final Paint PAINT) {
        properties.put("minorVGridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B gridLinePaint(final Paint PAINT) {
        properties.put("gridLinePaint", new SimpleObjectProperty<>(PAINT));
        return (B)this;
    }

    public final B majorHGridLinesVisible(final boolean VISIBLE) {
        properties.put("majorHGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B mediumHGridLinesVisible(final boolean VISIBLE) {
        properties.put("mediumHGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B minorHGridLinesVisible(final boolean VISIBLE) {
        properties.put("minorHGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B majorVGridLinesVisible(final boolean VISIBLE) {
        properties.put("majorVGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B mediumVGridLinesVisible(final boolean VISIBLE) {
        properties.put("mediumVGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B minorVGridLinesVisible(final boolean VISIBLE) {
        properties.put("minorVGridLinesVisible", new SimpleBooleanProperty(VISIBLE));
        return (B)this;
    }

    public final B gridOpacity(final double OPACITY) {
        properties.put("opacity", new SimpleDoubleProperty(OPACITY));
        return (B)this;
    }

    public final B gridLineDashes(final double... DASHES) {
        properties.put("dashesArray", new SimpleObjectProperty<>(DASHES));
        return (B)this;
    }

    // General properties
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


    public final Grid build() {
        final Grid control = new Grid(xAxis, yAxis);

        if (properties.keySet().contains("dashesArray")) {
            control.setGridLineDashes(((ObjectProperty<double[]>) properties.get("dashesArray")).get());
        }

        properties.forEach((key, property) -> {
            if ("prefSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setPrefSize(dim.getWidth(), dim.getHeight());
            } else if("minSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setMinSize(dim.getWidth(), dim.getHeight());
            } else if("maxSize".equals(key)) {
                Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                control.setMaxSize(dim.getWidth(), dim.getHeight());
            } else if("prefWidth".equals(key)) {
                control.setPrefWidth(((DoubleProperty) property).get());
            } else if("prefHeight".equals(key)) {
                control.setPrefHeight(((DoubleProperty) property).get());
            } else if("minWidth".equals(key)) {
                control.setMinWidth(((DoubleProperty) property).get());
            } else if("minHeight".equals(key)) {
                control.setMinHeight(((DoubleProperty) property).get());
            } else if("maxWidth".equals(key)) {
                control.setMaxWidth(((DoubleProperty) property).get());
            } else if("maxHeight".equals(key)) {
                control.setMaxHeight(((DoubleProperty) property).get());
            } else if("scaleX".equals(key)) {
                control.setScaleX(((DoubleProperty) property).get());
            } else if("scaleY".equals(key)) {
                control.setScaleY(((DoubleProperty) property).get());
            } else if ("layoutX".equals(key)) {
                control.setLayoutX(((DoubleProperty) property).get());
            } else if ("layoutY".equals(key)) {
                control.setLayoutY(((DoubleProperty) property).get());
            } else if ("translateX".equals(key)) {
                control.setTranslateX(((DoubleProperty) property).get());
            } else if ("translateY".equals(key)) {
                control.setTranslateY(((DoubleProperty) property).get());
            } else if ("padding".equals(key)) {
                control.setPadding(((ObjectProperty<Insets>) property).get());
            } // Control specific properties
              else if ("gridLinePaint".equals(key)) {
                control.setGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("majorHGridLinePaint".equals(key)) {
                control.setMajorHGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("mediumHGridLinePaint".equals(key)) {
                control.setMediumHGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("minorHGridLinePaint".equals(key)) {
                control.setMinorHGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("majorVGridLinePaint".equals(key)) {
                control.setMajorVGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("mediumVGridLinePaint".equals(key)) {
                control.setMediumVGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("minorVGridLinePaint".equals(key)) {
                control.setMinorVGridLinePaint(((ObjectProperty<Paint>) property).get());
            } else if ("majorHGridLinesVisible".equals(key)) {
                control.setMajorHGridLinesVisible(((BooleanProperty) property).get());
            } else if ("mediumHGridLinesVisible".equals(key)) {
                control.setMediumHGridLinesVisible(((BooleanProperty) property).get());
            } else if ("minorHGridLinesVisible".equals(key)) {
                control.setMinorHGridLinesVisible(((BooleanProperty) property).get());
            } else if ("majorVGridLinesVisible".equals(key)) {
                control.setMajorVGridLinesVisible(((BooleanProperty) property).get());
            } else if ("mediumVGridLinesVisible".equals(key)) {
                control.setMediumVGridLinesVisible(((BooleanProperty) property).get());
            } else if ("minorVGridLinesVisible".equals(key)) {
                control.setMinorVGridLinesVisible(((BooleanProperty) property).get());
            } else if ("gridOpacity".equals(key)) {
                control.setGridOpacity(((DoubleProperty) property).get());
            }
        });
        return control;
    }
}
