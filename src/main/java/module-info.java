module fx.chart {

    // Java
    requires java.base;
    requires java.logging;
    requires java.management;

    // Java-FX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;

    // 3rd party
    requires org.slf4j;
    requires pdk.util;
    requires org.jspecify;
    requires org.apache.commons.statistics.descriptive;
    requires it.unimi.dsi.fastutil;

    opens fx.chart.heatmap to javafx.graphics;

    exports fx.chart;
    exports fx.chart.areaheatmap;
    exports fx.chart.geometry;
    exports fx.chart.geometry.transform;
    exports fx.chart.sample;
    exports fx.chart.color;
    exports fx.chart.data;
    exports fx.chart.event;
    exports fx.chart.forcedirectedgraph;
    exports fx.chart.pareto;
    exports fx.chart.series;
    exports fx.chart.tools;
    exports fx.chart.util;
    exports fx.chart.voronoi;
    exports fx.chart.wafermap;
    exports fx.chart.property;
    exports fx.chart.event.type;

}
