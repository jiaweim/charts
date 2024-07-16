package eu.hansolo.fx.charts.forcedirectedgraph;

import java.util.ArrayList;
import java.util.PriorityQueue;


/**
 * authors: Michael L\u00E4uchli, MLaeuchli (github)
 *          Stefan Mettler, orizion (github)
 */
public class GraphCalculator {
    private static final String DEGREE_KEY               = "DegreeCentrality";
    private static final String CLOSENESS_KEY            = "ClosenessCentrality";
    private static final String BETWEENNESS_KEY          = "BetweennessCentrality";
    private static final String DEGREE_NORMALIZED_KEY    = "DegreeCentralityNormalized";
    private static final String CLOSENESS_NORMALIZED_KEY = "ClosenessCentrualityNormalized";
    double[] betweennessResults;

    private void recursiveBetweennesCalculatioin(ArrayList<GraphNode>[][] paths, ArrayList<GraphNode> graphNodes , double split, GraphNode from, GraphNode to){
        double nextSplit = split/paths[graphNodes.indexOf(from)][graphNodes.indexOf(to)].size();
        for(GraphNode node: paths[graphNodes.indexOf(from)][graphNodes.indexOf(to)] ){
            if(!node.equals(from)){
                betweennessResults[graphNodes.indexOf(node)] += split;
                recursiveBetweennesCalculatioin(paths, graphNodes, nextSplit, from, node);
            }
        }
    }

    private ArrayList<ArrayList<GraphNode>> createRealPathsRecursive(ArrayList<GraphNode>[][] paths, ArrayList<GraphNode> graphNodes, GraphNode from, GraphNode to){
        if(paths[graphNodes.indexOf(from)][graphNodes.indexOf(to)].size() == 1 && paths[graphNodes.indexOf(from)][graphNodes.indexOf(to)].get(0).equals(from)){
            return new ArrayList<>();
        } else{
            ArrayList<ArrayList<GraphNode>> ListOfLists = new ArrayList<>();
            for(GraphNode node: paths[graphNodes.indexOf(from)][graphNodes.indexOf(to)] ){
                //ListOfLists.add(createRealPathsRecursive(paths, graphNodes, node, from));
                ArrayList<ArrayList<GraphNode>> subpaths = createRealPathsRecursive(paths, graphNodes, node, from);
                if(subpaths.isEmpty()){
                    ArrayList<GraphNode> temp = new ArrayList<>();
                    temp.add(node);
                    ListOfLists.add(temp);
                } else {
                    for(ArrayList<GraphNode> ap: subpaths){
                        ap.add(node);
                        ListOfLists.add(ap);
                    }
                }
            }
            return ListOfLists;
        }
    }


    public NodeEdgeModel calculateDegreeCentrality(NodeEdgeModel nodeEdgeModel){
        for(GraphNode node: nodeEdgeModel.getNodes()) {
            node.setNumericAttribute(DEGREE_KEY, (double) node.getConnectedNodes().size());
        }
        return nodeEdgeModel;
    }

    public NodeEdgeModel calculateDegreeCentralityNormalized(NodeEdgeModel nodeEdgeModel){
        if(!nodeEdgeModel.getNodes().get(0).containsNumericAttribute(DEGREE_KEY)){
            calculateDegreeCentrality(nodeEdgeModel);
        }

        for(GraphNode node: nodeEdgeModel.getNodes()) {
            node.setNumericAttribute(DEGREE_NORMALIZED_KEY, node.getNumericAttribute(DEGREE_KEY) / (nodeEdgeModel.getNodes().size() - 1));
        }
        return nodeEdgeModel;
    }

    /*
    Implemented with Breadth first algorithm
     */
    public NodeEdgeModel calculateClosenessCentrality(NodeEdgeModel nodeEdgeModel){
        PriorityQueue<GraphNode> queue = new PriorityQueue<>();

        ArrayList<GraphNode> visited = new ArrayList<>();
        int level;
        int counterUntilNextLevel;
        int counterOfNextLevel;
        double result;
        GraphNode currentNode;
        ArrayList<GraphNode> GraphNodes = nodeEdgeModel.getNodes();

        for(GraphNode node: GraphNodes){
            visited.clear();
            queue.clear();
            queue.add(node);
            result = 0;
            level = 0;
            counterUntilNextLevel = 1;
            counterOfNextLevel = 0;

            while(!queue.isEmpty()){
                currentNode = queue.poll();
                counterOfNextLevel += currentNode.getConnectedNodes().size();
                visited.add(currentNode);
                ArrayList<GraphNode> currentNodeList = new ArrayList<>(currentNode.getConnectedNodes());
                for(GraphNode gNode: currentNodeList){
                    if(!visited.contains(gNode) && !queue.contains(gNode)){
                        queue.add(gNode);
                    }
                }
                if(level>0) {
                    result += 1.0 / (double) level;
                }
                counterUntilNextLevel--;
                if(counterUntilNextLevel == 0){
                    level++;
                    counterUntilNextLevel = counterOfNextLevel;
                    counterOfNextLevel = 0;
                }
            }
            node.setNumericAttribute(CLOSENESS_KEY, result);
        }
        return nodeEdgeModel;
    }


    public NodeEdgeModel calculateClosenessCentralityNormalized(NodeEdgeModel nodeEdgeModel){
        if(!nodeEdgeModel.getNodes().get(0).containsNumericAttribute(CLOSENESS_KEY)){
            calculateClosenessCentrality(nodeEdgeModel);
        }

        for(GraphNode node: nodeEdgeModel.getNodes()) {
            node.setNumericAttribute(CLOSENESS_NORMALIZED_KEY, node.getNumericAttribute(CLOSENESS_KEY) / (nodeEdgeModel.getNodes().size() - 1));
        }
        return nodeEdgeModel;
    }


    public void calculateBetweennessCentrality(NodeEdgeModel nodeEdgeModel){

        PriorityQueue<GraphNode> currentLevelQueue = new PriorityQueue<>();
        PriorityQueue<GraphNode> nextLevelQueue = new PriorityQueue<>();
        ArrayList<GraphNode> visited = new ArrayList<>();

        GraphNode currentNode;
        ArrayList<GraphNode> graphNodes = nodeEdgeModel.getNodes();
        betweennessResults = new double[graphNodes.size()];
        ArrayList<GraphNode>[][] paths = new ArrayList[graphNodes.size()][graphNodes.size()];
        ArrayList<ArrayList<GraphNode>>[][] realPaths = new ArrayList[graphNodes.size()][graphNodes.size()];

        for(int i=0; i<graphNodes.size(); i++){
            for(int j=0; j<graphNodes.size(); j++){
                paths[i][j] = new ArrayList<>();
            }
        }

        for(GraphNode node: graphNodes){
            visited.clear();
            currentLevelQueue.clear();
            currentLevelQueue.add(node);

            //save paths from node to all other nodes
            while(!currentLevelQueue.isEmpty()){
                currentNode = currentLevelQueue.poll();
                visited.add(currentNode);
                for(GraphNode gNode: currentNode.getConnectedNodes()){
                    if(!visited.contains(gNode) && !currentLevelQueue.contains(gNode)){
                        paths[graphNodes.indexOf(node)][graphNodes.indexOf(gNode)].add(currentNode);
                        if(!nextLevelQueue.contains(gNode)){
                            nextLevelQueue.add(gNode);
                        }
                    }
                }
                if(currentLevelQueue.isEmpty()){
                    while(!nextLevelQueue.isEmpty()){
                        currentLevelQueue.add(nextLevelQueue.poll());
                    }
                }
            }
        }

        for(int i=0; i<betweennessResults.length; i++){
            betweennessResults[i]=0;
        }

        for(int i=0; i<paths.length; i++){
            for(int j=0; j<paths[i].length; j++){
                if(i != j){
                    //recursiveBetweennesCalculatioin(paths, graphNodes,0.5, graphNodes.get(i), graphNodes.get(j));
                    realPaths[i][j] = createRealPathsRecursive(paths, graphNodes, graphNodes.get(i), graphNodes.get(j));
                }
            }
        }

        for(int i=0; i<paths.length; i++){
            for(int j=0; j<paths[i].length; j++){
                if(i != j){
                    for(ArrayList<GraphNode> al: realPaths[i][j]){
                        if(!al.isEmpty()){
                            for(GraphNode graphNode: al){
                                betweennessResults[graphNodes.indexOf(graphNode)] += 0.5/realPaths[i][j].size();
                            }
                        }
                    }
                }
            }
        }

        for(GraphNode node: graphNodes){
            node.setNumericAttribute(BETWEENNESS_KEY, betweennessResults[graphNodes.indexOf(node)]);
        }
    }




}
