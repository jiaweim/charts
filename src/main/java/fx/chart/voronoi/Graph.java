package fx.chart.voronoi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Graph<N> {

    private Map<N, Set<N>> neighbours = new HashMap<>();
    private Set<N> nodes = Collections.unmodifiableSet(neighbours.keySet());


    public Set<N> getNeighbours(final N node) throws NullPointerException {
        return Collections.unmodifiableSet(neighbours.get(node));
    }

    public Set<N> getNodes() {
        return nodes;
    }

    public void addNode(final N node) {
        if (neighbours.containsKey(node)) {return;}
        neighbours.put(node, new ArraySet<N>());
    }

    public void removeNode(final N node) {
        if (!neighbours.containsKey(node)) {return;}
        for (N neighbor : neighbours.get(node)) {
            neighbours.get(neighbor).remove(node);
        }
        neighbours.get(node).clear();
        neighbours.remove(node);
    }

    public void addConnection(final N nodeA, final N nodeB) throws NullPointerException {
        neighbours.get(nodeA).add(nodeB);
        neighbours.get(nodeB).add(nodeA);
    }

    public void removeConnection(final N nodeA, final N nodeB) throws NullPointerException {
        neighbours.get(nodeA).remove(nodeB);
        neighbours.get(nodeB).remove(nodeA);
    }
}
