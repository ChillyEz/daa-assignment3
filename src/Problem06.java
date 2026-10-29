import java.util.Arrays;

public class Problem06 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        int m = dependencies.length;

        // Store the graph as adjacency lists in arrays.
        int[] head = new int[n];
        int[] to = new int[m];
        int[] next = new int[m];
        Arrays.fill(head, -1);

        for (int i = 0; i < m; i++) {
            int u = dependencies[i][0];
            int v = dependencies[i][1];
            to[i] = v;
            next[i] = head[u];
            head[u] = i;
        }

        int[] discovery = new int[n];
        int[] low = new int[n];
        Arrays.fill(discovery, -1);

        boolean[] onStack = new boolean[n];
        int[] tarjanStack = new int[n];
        int tarjanTop = -1;

        // Explicit stacks avoid recursion on long paths.
        int[] nodeStack = new int[n];
        int[] edgeStack = new int[n];

        int timer = 0;
        int sccCount = 0;

        for (int i = 0; i < n; i++) {
            if (discovery[i] == -1) {
                int dfsTop = 0;
                nodeStack[dfsTop] = i;
                edgeStack[dfsTop] = head[i];

                discovery[i] = low[i] = timer++;
                tarjanStack[++tarjanTop] = i;
                onStack[i] = true;

                while (dfsTop >= 0) {
                    int u = nodeStack[dfsTop];
                    int e = edgeStack[dfsTop];

                    if (e != -1) {
                        edgeStack[dfsTop] = next[e];
                        int v = to[e];

                        if (discovery[v] == -1) {
                            discovery[v] = low[v] = timer++;
                            tarjanStack[++tarjanTop] = v;
                            onStack[v] = true;

                            dfsTop++;
                            nodeStack[dfsTop] = v;
                            edgeStack[dfsTop] = head[v];
                        } else if (onStack[v]) {
                            // Only vertices still on the Tarjan stack can lower this value.
                            low[u] = Math.min(low[u], discovery[v]);
                        }
                    } else {
                        if (low[u] == discovery[u]) {
                            sccCount++; // u is the root of a strongly connected component.
                            while (true) {
                                int member = tarjanStack[tarjanTop--];
                                onStack[member] = false;
                                if (member == u) {
                                    break;
                                }
                            }
                        }

                        // Return from DFS and update the parent's low-link value.
                        dfsTop--;
                        if (dfsTop >= 0) {
                            int parent = nodeStack[dfsTop];
                            low[parent] = Math.min(low[parent], low[u]);
                        }
                    }
                }
            }
        }

        return sccCount;
    }
}
