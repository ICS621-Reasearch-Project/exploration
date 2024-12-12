import java.util.*;

public class Exploration {
    public static void main(String[] args) {
        int numNodes = 10;       // Number of nodes in the tree
        int maxWeight = 5;       // Maximum edge weight
        int budget = 10;         // Travel budget

        // Generate the tree
        WeightedTreeXKnownDist.Node root = WeightedTreeXKnownDist.generateRandomTree(numNodes, maxWeight);

        // Identify goal node
        WeightedTreeXKnownDist.Node goal = WeightedTreeXKnownDist.findGoalNode(root);
        System.out.println("Goal node is: " + goal);

        // Print the tree structure for visualization
        System.out.println("Tree Structure:");
        WeightedTreeXKnownDist.printTree(root, 0);

        // Find path within budget
        System.out.println("\nFinding path from root to goal within budget:");
        List<WeightedTreeXKnownDist.Node> path = WeightedTreeXKnownDist.findPathWithinBudget(root, goal, budget);

        if (path != null) {
            System.out.println("Path found within budget:");
            System.out.println(path);
        } else {
            System.out.println("Cannot travel to goal within budget.");
        }
    }
}
