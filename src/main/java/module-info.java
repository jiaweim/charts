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
    requires transitive eu.hansolo.fx.countries;

    opens fx.geometry to eu.hansolo.fx.countries;
    opens fx.geometry.tools to eu.hansolo.fx.countries;
    opens fx.geometry.transform to eu.hansolo.fx.countries;
    opens fx.charts to eu.hansolo.fx.countries;
    opens fx.charts.areaheatmap to eu.hansolo.fx.countries;
    opens fx.charts.color to eu.hansolo.fx.countries;
    opens fx.charts.data to eu.hansolo.fx.countries;
    opens fx.charts.event to eu.hansolo.fx.countries;
    opens fx.charts.forcedirectedgraph to eu.hansolo.fx.countries;
    opens fx.charts.pareto to eu.hansolo.fx.countries;
    opens fx.charts.series to eu.hansolo.fx.countries;
    opens fx.charts.tools to eu.hansolo.fx.countries;
    opens fx.charts.world to eu.hansolo.fx.countries;
    opens fx.charts.voronoi to eu.hansolo.fx.countries;
    opens fx.charts.wafermap to eu.hansolo.fx.countries;

    exports fx.geometry;
    exports fx.geometry.tools;
    exports fx.geometry.transform;
    exports fx.charts;
    exports fx.charts.areaheatmap;
    exports fx.charts.color;
    exports fx.charts.data;
    exports fx.charts.event;
    exports fx.charts.forcedirectedgraph;
    exports fx.charts.pareto;
    exports fx.charts.series;
    exports fx.charts.tools;
    exports fx.charts.world;
    exports fx.charts.voronoi;
    exports fx.charts.wafermap;

}
