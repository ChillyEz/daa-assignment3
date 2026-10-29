public class Problem01 {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int components = 0;

        for (int node = 0; node < n; node++) {
            if (!visited[node]) {
                dfs(node, isConnected, visited);
                components++;
            }
        }

        return components;
    }

    private void dfs(int node, int[][] isConnected, boolean[] visited) {
        visited[node] = true;

        for (int neighbor = 0; neighbor < isConnected.length; neighbor++) {
            if (isConnected[node][neighbor] == 1 && !visited[neighbor]) {
                dfs(neighbor, isConnected, visited);
            }
        }
    }
}
