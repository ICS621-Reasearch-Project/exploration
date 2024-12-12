import java.util.*;

class Node {
    int id;
    Map<Node, Integer> children = new HashMap<>(); // Child to weight mapping
    int prediction = -1; // f(x) value
    boolean isPredictionCorrect = true;

    Node(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Node" + id + "(f(x)=" + prediction + ")";
    }
}

public class WeightedTreeXKnownDist {
    static Random random = new Random();

    public static Node generateRandomTree(int numNodes, int maxWeight) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node(i));
        }

        // Generate random edges
        for (int i = 1; i < numNodes; i++) {
            int parentIndex = random.nextInt(i); // Ensure tree structure
            Node parent = nodes.get(parentIndex);
            Node child = nodes.get(i);
            int weight = random.nextInt(maxWeight) + 1; // Positive non-zero weight
            parent.children.put(child, weight);
        }

        return nodes.get(0); // Return root node
    }

    public static List<Node> WeightedTreeXKnownDist(Node root, Node goal, int budget) {
        Map<Node, Integer> visitedCosts = new HashMap<>();
        Queue<Path> queue = new LinkedList<>();
        queue.add(new Path(root, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();
            Node currentNode = currentPath.node;
            int currentCost = currentPath.cost;

            // If the current node is the goal
            if (currentNode == goal) {
                if (currentCost <= budget) {
                    currentPath.path.add(currentNode);
                    return currentPath.path; // Return the successful path
                } else {
                    break; // Exceeded budget
                }
            }

            if (visitedCosts.containsKey(currentNode) && visitedCosts.get(currentNode) <= currentCost) {
                continue;
            }

            // Flag as visited
            visitedCosts.put(currentNode, currentCost);

            // Traverse children
            for (Map.Entry<Node, Integer> entry : currentNode.children.entrySet()) {
                Node child = entry.getKey();
                int edgeWeight = entry.getValue();
                if (currentCost + edgeWeight <= budget) {
                    List<Node> newPath = new ArrayList<>(currentPath.path);
                    newPath.add(currentNode);
                    queue.add(new Path(child, currentCost + edgeWeight, newPath));
                }
            }
        }

        // If no path found within budget
        return null;
    }

    static class Path {
        Node node;
        int cost;
        List<Node> path;

        Path(Node node, int cost, List<Node> path) {
            this.node = node;
            this.cost = cost;
            this.path = path;
        }
    }

    public static void main(String[] args) {
        int numNodes = 100;
        int maxWeight = 10;
        int budget = 150; //Test budget by changing here

        Node root = generateRandomTree(numNodes, maxWeight);
        Node goal = findGoalNode(root);

        System.out.println("Tree Structure:");
        printTree(root, 0);

        System.out.println("\nFinding path from root to goal within budget:");
        List<Node> path = WeightedTreeXKnownDist(root, goal, budget);

        if (path != null) {
            System.out.println("Path found within budget:");
            path.forEach(node -> System.out.print(node.id + " -> "));
            System.out.println("Goal");
        } else {
            System.out.println("No path found within the budget.");
        }
    }

    private static Node findGoalNode(Node root) {

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node child : current.children.keySet()) {
                if (random.nextBoolean()) {
                    return child; // Randomly select a goal node
                }
                queue.add(child);
            }
        }
        return root;
    }

    private static void printTree(Node node, int level) {
        if (node == null) return;
        System.out.println("  ".repeat(level) + node);
        for (Node child : node.children.keySet()) {
            printTree(child, level + 1);
        }
    }
}
