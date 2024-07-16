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


import eu.hansolo.fx.charts.SankeyPlot.StreamFillMode;
import eu.hansolo.fx.charts.data.PlotItem;
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
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class SankeyPlotBuilder<B extends SankeyPlotBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected SankeyPlotBuilder() {}


    // ******************** Methods *******************************************
    public static final SankeyPlotBuilder create() {
        return new SankeyPlotBuilder();
    }

    public final B items(final PlotItem... ITEMS) {
        properties.put("itemsArray", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B items(final List<PlotItem> ITEMS) {
        properties.put("itemsList", new SimpleObjectProperty<>(ITEMS));
        return (B)this;
    }

    public final B streamFillMode(final StreamFillMode MODE) {
        properties.put("streamFillMode", new SimpleObjectProperty<>(MODE));
        return (B)this;
    }

    public final B streamColor(final Color COLOR) {
        properties.put("streamColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B textColor(final Color COLOR) {
        properties.put("textColor", new SimpleObjectProperty(COLOR));
        return (B)this;
    }

    public final B selectionColor(final Color COLOR) {
        properties.put("selectionColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B decimals(final int DECIMALS) {
        properties.put("decimals", new SimpleIntegerProperty(DECIMALS));
        return (B)this;
    }

    public final B showFlowDirection(final boolean SHOW) {
        properties.put("showFlowDirection", new SimpleBooleanProperty(SHOW));
        return (B)this;
    }

    public final B itemWidth(final int WIDTH) {
        properties.put("itemWidth", new SimpleIntegerProperty(WIDTH));
        return (B)this;
    }

    public final B autoItemWidth(final boolean AUTO) {
        properties.put("autoItemWidth", new SimpleBooleanProperty(AUTO));
        return (B)this;
    }

    public final B itemGap(final int GAP) {
        properties.put("itemGap", new SimpleIntegerProperty(GAP));
        return (B)this;
    }

    public final B autoItemGap(final boolean AUTO) {
        properties.put("autoItemGap", new SimpleBooleanProperty(AUTO));
        return (B)this;
    }

    public final B useItemColor(final boolean USE) {
        properties.put("useItemColor", new SimpleBooleanProperty(USE));
        return (B)this;
    }

    public final B itemColor(final Color COLOR) {
        properties.put("itemColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B connectionOpacity(final double OPACITY) {
        properties.put("connectionOpacity", new SimpleDoubleProperty(OPACITY));
        return (B)this;
    }

    public final B locale(final Locale LOCALE) {
        properties.put("locale", new SimpleObjectProperty<>(LOCALE));
        return (B)this;
    }

    public final B useCustomFont(final boolean useCustomFont) {
        properties.put("useCustomFont", new SimpleBooleanProperty(useCustomFont));
        return (B)this;
    }

    public final B customFont(final Font customFont) {
        properties.put("customFont", new SimpleObjectProperty<>(customFont));
        return (B)this;
    }

    public final B useItemTextColor(final boolean useItemTextColor) {
        properties.put("useItemTextColor", new SimpleBooleanProperty(useItemTextColor));
        return (B)this;
    }

    public final B autoAdjustVerticalTextPosition(final boolean autoAdjustVerticalTextPosition) {
        properties.put("autoAdjustVerticalTextPosition", new SimpleBooleanProperty(autoAdjustVerticalTextPosition));
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


    public final SankeyPlot build() {
        final SankeyPlot control = new SankeyPlot();

        if (properties.keySet().contains("itemsArray")) {
            control.setItems(((ObjectProperty<PlotItem[]>) properties.get("itemsArray")).get());
        }
        if(properties.keySet().contains("itemsList")) {
            control.setItems(((ObjectProperty<List<PlotItem>>) properties.get("itemsList")).get());
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize"          -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "minSize"           -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMinSize(dim.getWidth(), dim.getHeight());
                }
                case "maxSize"           -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setMaxSize(dim.getWidth(), dim.getHeight());
                }
                case "prefWidth"                      -> control.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight"                     -> control.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth"                       -> control.setMinWidth(((DoubleProperty) property).get());
                case "minHeight"                      -> control.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth"                       -> control.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight"                      -> control.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX"                         -> control.setScaleX(((DoubleProperty) property).get());
                case "scaleY"                         -> control.setScaleY(((DoubleProperty) property).get());
                case "layoutX"                        -> control.setLayoutX(((DoubleProperty) property).get());
                case "layoutY"                        -> control.setLayoutY(((DoubleProperty) property).get());
                case "translateX"                     -> control.setTranslateX(((DoubleProperty) property).get());
                case "translateY"                     -> control.setTranslateY(((DoubleProperty) property).get());
                case "padding"                        -> control.setPadding(((ObjectProperty<Insets>) property).get());
                case "streamFillMode"                 -> control.setStreamFillMode(((ObjectProperty<StreamFillMode>) property).get());
                case "streamColor"                    -> control.setStreamColor(((ObjectProperty<Color>) property).get());
                case "textColor"                      -> control.setTextColor(((ObjectProperty<Color>) property).get());
                case "selectionColor"                 -> control.setSelectionColor(((ObjectProperty<Color>) property).get());
                case "decimals"                       -> control.setDecimals(((IntegerProperty) property).get());
                case "showFlowDirection"              -> control.setShowFlowDirection(((BooleanProperty) property).get());
                case "itemWidth"                      -> control.setItemWidth(((IntegerProperty) property).get());
                case "autoItemWidth"                  -> control.setAutoItemWidth(((BooleanProperty) property).get());
                case "itemGap"                        -> control.setItemGap(((IntegerProperty) property).get());
                case "autoItemGap"                    -> control.setAutoItemGap(((BooleanProperty) property).get());
                case "useItemColor"                   -> control.setUseItemColor(((BooleanProperty) property).get());
                case "itemColor"                      -> control.setItemColor(((ObjectProperty<Color>) property).get());
                case "connectionOpacity"              -> control.setConnectionOpacity(((DoubleProperty) property).get());
                case "locale"                         -> control.setLocale(((ObjectProperty<Locale>) property).get());
                case "useCustomFont"                  -> control.setUseCustomFont(((BooleanProperty) property).get());
                case "customFont"                     -> control.setCustomFont(((ObjectProperty<Font>) property).get());
                case "useItemTextColor"               -> control.setUseItemTextColor(((BooleanProperty) property).get());
                case "autoAdjustVerticalTextPosition" -> control.setAutoAdjustVerticalTextPosition(((BooleanProperty) property).get());
            }
        });
        return control;
    }
}
