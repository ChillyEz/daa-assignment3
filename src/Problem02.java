public class Problem02 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        int[] rank = new int[n];
        int components = n;

        for (int node = 0; node < n; node++) {
            parent[node] = node;
        }

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (isConnected[u][v] == 1 && union(u, v, parent, rank)) {
                    components--;
                }
            }
        }

        return components;
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
