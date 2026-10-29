import java.util.ArrayList;
import java.util.List;

public class Problem09 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int course = 0; course < numCourses; course++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }

        // 0 = unvisited, 1 = on the current DFS path, 2 = finished.
        int[] state = new int[numCourses];
        List<Integer> finishOrder = new ArrayList<>(numCourses);

        for (int course = 0; course < numCourses; course++) {
            if (state[course] == 0 && hasCycle(course, graph, state, finishOrder)) {
                return new int[0];
            }
        }

        int[] order = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            order[i] = finishOrder.get(numCourses - 1 - i);
        }

        return order;
    }

    private boolean hasCycle(int node, List<List<Integer>> graph, int[] state,
            List<Integer> finishOrder) {
        state[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (state[neighbor] == 1) {
                return true;
            }
            if (state[neighbor] == 0 && hasCycle(neighbor, graph, state, finishOrder)) {
                return true;
            }
        }

        state[node] = 2;
        finishOrder.add(node);
        return false;
    }
}
