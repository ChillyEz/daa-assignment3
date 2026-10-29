import java.util.Arrays;

public class Problem04 {
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        boolean[] visited = new boolean[n];
        int[] minDistance = new int[n];
        Arrays.fill(minDistance, Integer.MAX_VALUE);
        minDistance[0] = 0;

        int minCost = 0;

        for (int count = 0; count < n; count++) {
            int node = -1;
            for (int candidate = 0; candidate < n; candidate++) {
                if (!visited[candidate]
                        && (node == -1 || minDistance[candidate] < minDistance[node])) {
                    node = candidate;
                }
            }

            visited[node] = true;
            minCost += minDistance[node];

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (!visited[neighbor]) {
                    int distance = Math.abs(points[node][0] - points[neighbor][0])
                            + Math.abs(points[node][1] - points[neighbor][1]);
                    minDistance[neighbor] = Math.min(minDistance[neighbor], distance);
                }
            }
        }

        return minCost;
    }
}
