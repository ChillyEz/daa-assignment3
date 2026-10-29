import java.util.Arrays;

public class Problem05 {
    public int countDependencyGroups(int n, int[][] dependencies) {
        int m = dependencies.length;

        // Store the graph and its reverse as adjacency lists in arrays.
        int[] head = new int[n];
        int[] to = new int[m];
        int[] next = new int[m];
        Arrays.fill(head, -1);

        int[] reverseHead = new int[n];
        int[] reverseTo = new int[m];
        int[] reverseNext = new int[m];
        Arrays.fill(reverseHead, -1);

        for (int i = 0; i < m; i++) {
            int u = dependencies[i][0];
            int v = dependencies[i][1];

            to[i] = v;
            next[i] = head[u];
            head[u] = i;

            reverseTo[i] = u;
            reverseNext[i] = reverseHead[v];
            reverseHead[v] = i;
        }

        boolean[] visited = new boolean[n];
        int[] finishOrder = new int[n];
        int finishCount = 0;

        // Explicit stacks avoid recursion on long paths.
        int[] nodeStack = new int[n];
        int[] edgeStack = new int[n];

        // First pass: record vertices when DFS finishes.
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int top = 0;
                nodeStack[top] = i;
                edgeStack[top] = head[i];
                visited[i] = true;

                while (top >= 0) {
                    int u = nodeStack[top];
                    int e = edgeStack[top];

                    if (e != -1) {
                        edgeStack[top] = next[e];
                        int v = to[e];
                        if (!visited[v]) {
                            visited[v] = true;
                            top++;
                            nodeStack[top] = v;
                            edgeStack[top] = head[v];
                        }
                    } else {
                        finishOrder[finishCount++] = u;
                        top--;
                    }
                }
            }
        }

        // Second pass: visit the reversed graph in reverse finish order.
        Arrays.fill(visited, false);
        int sccCount = 0;

        for (int i = n - 1; i >= 0; i--) {
            int start = finishOrder[i];
            if (!visited[start]) {
                sccCount++;

                int top = 0;
                nodeStack[top] = start;
                visited[start] = true;

                while (top >= 0) {
                    int u = nodeStack[top--];
                    for (int e = reverseHead[u]; e != -1; e = reverseNext[e]) {
                        int v = reverseTo[e];
                        if (!visited[v]) {
                            visited[v] = true;
                            nodeStack[++top] = v;
                        }
                    }
                }
            }
        }

        return sccCount;
    }
}
