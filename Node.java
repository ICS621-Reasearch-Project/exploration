import java.util.HashMap;
import java.util.Map;

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