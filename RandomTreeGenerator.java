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
        private Node rootNode;
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

            // Randomly set one node as the root
            rootNode = nodeList.get(random.nextInt(nodeList.size()));

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
            return rootNode;
        }

        // Get all nodes in tree instance
        public Collection<Node> getAllNodes() {
            return nodes.values();
        }

        // Extract individual node
        public Node getNodeById(String nodeId) {
            return nodes.get(nodeId);
        }

        // To visualize path to goal
        public List<String> findPathToGoal() {
            Node root = getRoot();
            List<String> path = new ArrayList<>();
            Set<Node> visited = new HashSet<>();
            if (dfs(root, goalNode, path, visited)) {
                return path;
            } else {
                System.out.println("Goal not reachable from root.");
                return Collections.emptyList();
            }
        }

        // DFS to find optimal path
        private boolean dfs(Node current, Node goal, List<String> path, Set<Node> visited) {
            if (current == null || visited.contains(current)) {
                return false;
            }

            visited.add(current);
            path.add(current.id);

            // Goal found
            if (current == goal) {
                return true;
            }

            // Explore the neighbors
            for (Edge edge : current.edges) {
                if (dfs(edge.targetNode, goal, path, visited)) {
                    return true;
                }
            }

            // Backtrack if the goal isn't found along this path
            path.remove(path.size() - 1);
            return false;
        }
    }
}