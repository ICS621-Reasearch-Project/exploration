import java.util.List;

public class Path {
    Node node;
    int cost;
    List<Node> path;

    Path(Node node, int cost, List<Node> path) {
        this.node = node;
        this.cost = cost;
        this.path = path;
    }
}