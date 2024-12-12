import java.util.*;

public class WeightedTreeXKnownDist {
    private static Random random = new Random();

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

    public static List<Node> findPathWithinBudget(Node root, Node goal, int budget) {
        Map<Node, Integer> visitedCosts = new HashMap<>();
        Queue<WeightedTreeXKnownDist.Path> queue = new LinkedList<>();
        queue.add(new WeightedTreeXKnownDist.Path(root, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            WeightedTreeXKnownDist.Path currentPath = queue.poll();
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
                    queue.add(new WeightedTreeXKnownDist.Path(child, currentCost + edgeWeight, newPath));
                }
            }
        }

        // If no path found within budget
        return null;
    }

    public static Node findGoalNode(Node root) {

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

    public static void printTree(Node node, int level) {
        if (node == null) return;
        System.out.println("  ".repeat(level) + node);
        for (Node child : node.children.keySet()) {
            printTree(child, level + 1);
        }
    }
}