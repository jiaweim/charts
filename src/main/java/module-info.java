module eu.hansolo.fx.charts {

    // Java
    requires java.base;
    requires java.logging;

    // Java-FX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;

    // 3rd party
    requires ch.qos.logback.classic;
    requires org.slf4j;
    requires java.management;

    opens fx.chart.country to javafx.graphics;
    opens fx.chart.heatmap to javafx.graphics;

    exports fx.chart.geometry;
    exports fx.chart.geometry.tools;
    exports fx.chart.geometry.transform;
    exports fx.chart;
    exports fx.chart.areaheatmap;
    exports fx.chart.color;
    exports fx.chart.data;
    exports fx.chart.event;
    exports fx.chart.forcedirectedgraph;
    exports fx.chart.pareto;
    exports fx.chart.series;
    exports fx.chart.tools;
    exports fx.chart.world;
    exports fx.chart.voronoi;
    exports fx.chart.wafermap;

}
