package fx.chart;

import fx.chart.forcedirectedgraph.GraphNode;
import fx.chart.forcedirectedgraph.GraphPanel;
import fx.chart.forcedirectedgraph.NodeEdgeModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 Jul 2025, 8:55 AM
 */
public class GraphPanelTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        List<GraphNode> nodes = new ArrayList<>();

        NodeEdgeModel model = new NodeEdgeModel();

        GraphPanel panel = new GraphPanel();
    }
}
