import java.util.*;

class RandomTreeGenerator {
    static class Node {
        String id;
        boolean isGoal;
        List<Edge> edges;
        Map<Node, Double> distancePredictions;

        public Node(String id) {
            this.id = id;
            this.isGoal = false;
            this.edges = new ArrayList<>();
            this.distancePredictions = new HashMap<>();
        }
    }

    static class Edge {
        Node targetNode;
        double weight;

        public Edge(Node targetNode, double weight) {
            this.targetNode = targetNode;
            this.weight = weight;
        }
    }

    static class RandomTree {
        private final Map<String, Node> nodes;
        private Node goalNode;

        public RandomTree() {
            this.nodes = new HashMap<>();
        }

        // Generate a random tree
        public void generateTree(int numNodes) {
            Random random = new Random();

            // Create nodes
            for (int i = 0; i < numNodes; i++) {
                nodes.put("Node" + i, new Node("Node" + i));
            }

            // Randomly generate tree from nodes
            List<Node> nodeList = new ArrayList<>(nodes.values());
            for (int i = 1; i < nodeList.size(); i++) {
                Node parent = nodeList.get(random.nextInt(i));  // Pick a random existing node as parent
                Node child = nodeList.get(i);
                double weight = 1 + random.nextDouble() * 9;  // Random integer weight
                parent.edges.add(new Edge(child, weight));
                child.edges.add(new Edge(parent, weight));  // Undirected edge
            }

            // Randomly set one node as the goal
            goalNode = nodeList.get(random.nextInt(nodeList.size()));
            goalNode.isGoal = true;

            // Inject predictions with half correct and half incorrect distances
            for (Node node : nodeList) {
                for (Edge edge : node.edges) {
                    double trueDistance = edge.weight;
                    double predictedDistance = random.nextBoolean()
                            ? trueDistance  // Correct
                            : trueDistance + (random.nextDouble() - 0.5) * trueDistance;  // Incorrect
                    node.distancePredictions.put(edge.targetNode, predictedDistance);
                }
            }
        }

        public Node getGoalNode() {
            return goalNode;
        }

        public Node getRoot() {
            return nodes.values().iterator().next();  // Return any node as root
        }
    }
}