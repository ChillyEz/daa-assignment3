import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Problem03 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                int distance = Math.abs(points[u][0] - points[v][0])
                        + Math.abs(points[u][1] - points[v][1]);
                edges.add(new int[] {distance, u, v});
            }
        }

        edges.sort(Comparator.comparingInt(edge -> edge[0]));

        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int node = 0; node < n; node++) {
            parent[node] = node;
        }

        int minCost = 0;
        int edgesUsed = 0;

        for (int[] edge : edges) {
            if (union(edge[1], edge[2], parent, rank)) {
                minCost += edge[0];
                edgesUsed++;
                if (edgesUsed == n - 1) {
                    break;
                }
            }
        }

        return minCost;
    }

    private int find(int node, int[] parent) {
        if (parent[node] != node) {
            parent[node] = find(parent[node], parent);
        }

        return parent[node];
    }

    private boolean union(int u, int v, int[] parent, int[] rank) {
        int rootU = find(u, parent);
        int rootV = find(v, parent);

        if (rootU == rootV) {
            return false;
        }

        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }

        return true;
    }
}
