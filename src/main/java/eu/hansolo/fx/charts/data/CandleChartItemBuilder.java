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

package eu.hansolo.fx.charts.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;


public class CandleChartItemBuilder<B extends CandleChartItemBuilder<B>> {
    private HashMap<String, Property> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected CandleChartItemBuilder() {}


    // ******************** Methods *******************************************
    public static final CandleChartItemBuilder create() {
        return new CandleChartItemBuilder();
    }


    public final B name(final String NAME) {
        properties.put("name", new SimpleStringProperty(NAME));
        return (B)this;
    }

    public final B unit(final String UNIT) {
        properties.put("unit", new SimpleStringProperty(UNIT));
        return (B)this;
    }

    public final B description(final String DESCRIPTION) {
        properties.put("description", new SimpleStringProperty(DESCRIPTION));
        return (B)this;
    }

    public final B value(final double VALUE) {
        properties.put("value", new SimpleDoubleProperty(VALUE));
        return (B)this;
    }

    public final B timestamp(final ZonedDateTime DATE_TIME) {
        properties.put("timestampDateTime", new SimpleObjectProperty<>(DATE_TIME));
        return (B)this;
    }

    public final B timestamp(final Instant TIMESTAMP) {
        properties.put("timestamp", new SimpleObjectProperty<>(TIMESTAMP));
        return (B)this;
    }

    public final B timestampEpochSecond(final long TIMESTAMP_EPOCH_SECOND) {
        properties.put("timestampEpochSecond", new SimpleLongProperty(TIMESTAMP_EPOCH_SECOND));
        return (B)this;
    }

    public final B high(final double HIGH) {
        properties.put("high", new SimpleDoubleProperty(HIGH));
        return (B)this;
    }

    public final B low(final double LOW) {
        properties.put("low", new SimpleDoubleProperty(LOW));
        return (B)this;
    }

    public final B open(final double OPEN) {
        properties.put("open", new SimpleDoubleProperty(OPEN));
        return (B)this;
    }

    public final B close(final double CLOSE) {
        properties.put("close", new SimpleDoubleProperty(CLOSE));
        return (B)this;
    }

    public final B openTimestampEpochSecond(final long OPEN_TIMESTAMP_EPOCH_SECOND) {
        properties.put("openTimestampEpochSecond", new SimpleLongProperty(OPEN_TIMESTAMP_EPOCH_SECOND));
        return (B)this;
    }

    public final B closeTimestampEpochSecond(final long CLOSE_TIMESTAMP_EPOCH_SECOND) {
        properties.put("closeTimestampEpochSecond", new SimpleLongProperty(CLOSE_TIMESTAMP_EPOCH_SECOND));
        return (B)this;
    }

    public final CandleChartItem build() {
        final CandleChartItem control = new CandleChartItem();
        properties.forEach((key, property) -> {
            switch (key) {
                case "name"                      -> control.setName(((StringProperty) property).get());
                case "description"               -> control.setDescription(((StringProperty) property).get());
                case "timestamp"                 -> control.setTimestamp(((ObjectProperty<Instant>) property).get());
                case "timestampDateTime"         -> control.setTimestamp(((ObjectProperty<ZonedDateTime>) property).get());
                case "timestampEpochSecond"      -> control.setTimestamp(((LongProperty) property).get());
                case "high"                      -> control.setHigh(((DoubleProperty) property).get());
                case "low"                       -> control.setLow(((DoubleProperty) property).get());
                case "open"                      -> control.setOpen(((DoubleProperty) property).get());
                case "close"                     -> control.setClose(((DoubleProperty) property).get());
                case "openTimestampEpochSecond"  -> control.setOpenTimestamp(((LongProperty) property).get());
                case "closeTimestampEpochSecond" -> control.setCloseTimestamp(((LongProperty) property).get());
            }
        });
        return control;
    }
}
