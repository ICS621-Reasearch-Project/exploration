import java.util.*;

public class Exploration {
    private final RandomTreeGenerator.RandomTree tree;
    private final RandomTreeGenerator.Node root;
    private final RandomTreeGenerator.Node goal;

    public Exploration(int numNodes) {

        tree = new RandomTreeGenerator.RandomTree();
        tree.generateTree(numNodes);

        // Extract root and goal information
        root = tree.getRoot();
        goal = tree.getGoalNode();
    }

    public void runExploration() {
        System.out.println("Starting exploration...");
        System.out.println("Root Node: " + root.id);
        System.out.println("Goal Node: " + goal.id);

        // Calculate the distance from root to goal
        double distance = calculateDistance(root, goal);
        System.out.println("Distance from Root to Goal: " + distance);

    }

    private int calculateDistance(RandomTreeGenerator.Node start, RandomTreeGenerator.Node target) {
        // BFS
        Queue<RandomTreeGenerator.Node> queue = new LinkedList<>();
        Map<RandomTreeGenerator.Node, Integer> distances = new HashMap<>();
        Set<RandomTreeGenerator.Node> visited = new HashSet<>();

        queue.add(start);
        distances.put(start, 0);
        visited.add(start);

        while (!queue.isEmpty()) {
            RandomTreeGenerator.Node currentNode = queue.poll();

            // Goal reached
            if (currentNode == target) {
                return distances.get(currentNode);
            }

            // Explore neighbors
            for (RandomTreeGenerator.Edge edge : currentNode.edges) {
                RandomTreeGenerator.Node neighbor = edge.targetNode;

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    distances.put(neighbor, distances.get(currentNode) + 1);
                    queue.add(neighbor);
                }
            }
        }

        return -1; // No feasible path
    }



    public static void main(String[] args) {

        Exploration exploration = new Exploration(20); // Choose how large tree is
        exploration.runExploration();
    }
}
