import java.util.ArrayList;
import java.util.List;

public class Problem07 {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int course = 0; course < numCourses; course++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }

        // 0 = unvisited, 1 = on the current DFS path, 2 = finished.
        int[] state = new int[numCourses];
        for (int course = 0; course < numCourses; course++) {
            if (state[course] == 0 && hasCycle(course, graph, state)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(int node, List<List<Integer>> graph, int[] state) {
        state[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (state[neighbor] == 1) {
                return true;
            }
            if (state[neighbor] == 0 && hasCycle(neighbor, graph, state)) {
                return true;
            }
        }

        state[node] = 2;
        return false;
    }
}
