import java.util.*;

class WeightedTreeX {
    private static Set<Node> visitedNodes = new HashSet<>();
    private static Node goalNode; // Goal node will be assigned randomly

    public static void main(String[] args) {
        // Generate random nodes and random tree structure
        List<Node> nodes = generateRandomTree(10); // Can change number of nides here

        // Randomly select the goal node
        Random rand = new Random();
        goalNode = nodes.get(rand.nextInt(nodes.size())); // Random goal node
        System.out.println("Goal node selected: " + goalNode);

        runWeightedTreeX(nodes.get(0)); // Set root as first node
    }

    // Use prediction values
    private static int heuristic(Node node) {
        return node.prediction == -1 ? new Random().nextInt(10) : node.prediction;
    }

    private static List<Node> generateRandomTree(int numNodes) {
        List<Node> nodes = new ArrayList<>();
        Random rand = new Random();

        // Create nodes
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node(i));
        }

        // Randomly connect the nodes
        for (int i = 0; i < numNodes; i++) {
            Node node = nodes.get(i);
            int numEdges = rand.nextInt(numNodes / 2) + 1; // Each node has 1 to (numNodes/2) edges

            for (int j = 0; j < numEdges; j++) {
                int targetIndex = rand.nextInt(numNodes);
                if (targetIndex != i) { // Avoid self-loops
                    Node targetNode = nodes.get(targetIndex);
                    int weight = rand.nextInt(10) + 1;
                    node.children.put(targetNode, weight);
                    targetNode.children.put(node, weight); // Undirected
                }
            }
        }

        return nodes;
    }

    private static void runWeightedTreeX(Node root) {
        PriorityQueue<NodeWrapper> queue = new PriorityQueue<>(Comparator.comparingInt(nw -> nw.fCost));
        Map<Node, Integer> gCost = new HashMap<>();
        gCost.put(root, 0);

        // Add the start node to the priority queue
        queue.add(new NodeWrapper(root, 0, heuristic(root)));

        while (!queue.isEmpty()) {
            NodeWrapper currentWrapper = queue.poll();
            Node currentNode = currentWrapper.node;
            int currentCost = currentWrapper.gCost;

            // Goal reached
            if (currentNode == goalNode) {
                System.out.println("Goal reached at node " + currentNode + " with cost " + currentCost);
                return;
            }

            // Explore neighbors
            visitedNodes.add(currentNode);
            for (Map.Entry<Node, Integer> entry : currentNode.children.entrySet()) {
                Node neighbor = entry.getKey();
                int edgeWeight = entry.getValue();

                if (!visitedNodes.contains(neighbor)) {
                    int newCost = currentCost + edgeWeight;
                    int predictedCost = heuristic(neighbor);

                    // Calculate the total cost (g + h)
                    int fCost = newCost + predictedCost;
                    if (!gCost.containsKey(neighbor) || newCost < gCost.get(neighbor)) {
                        gCost.put(neighbor, newCost);
                        queue.add(new NodeWrapper(neighbor, newCost, predictedCost));
                    }

                    System.out.println("Exploring Node " + neighbor + " with cost " + newCost + ", predicted cost " + predictedCost);
                }
            }
        }

        System.out.println("No path to the goal found.");
    }

    static class NodeWrapper {
        Node node;
        int gCost; // Actual cost from the start node
        int hCost; // Heuristic estimate
        int fCost; // Total cost (gCost + hCost)

        NodeWrapper(Node node, int gCost, int hCost) {
            this.node = node;
            this.gCost = gCost;
            this.hCost = hCost;
            this.fCost = gCost + hCost; // fCost = gCost + hCost
        }
    }
}
