import java.util.*;

public class Exploration {
    public static void main(String[] args) {
        int numNodes = 100;
        int maxWeight = 10;
        int budget = 150;

        Node root = WeightedTreeXKnownDist.generateRandomTree(numNodes, maxWeight);
        Node goal = WeightedTreeXKnownDist.findGoalNode(root);

        System.out.println("Tree Structure:");
        WeightedTreeXKnownDist.printTree(root, 0);

        System.out.println("\nFinding path from root to goal within budget:");
        List<Node> path = WeightedTreeXKnownDist.findPathWithinBudget(root, goal, budget);

        if (path != null) {
            System.out.println("Path found within budget:");
            path.forEach(node -> System.out.print(node.id + " -> "));
            System.out.println("Goal");
        } else {
            System.out.println("No path found within the budget.");
        }
    }
}
