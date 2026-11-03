# DAA Assignment 3 - Report

`n` means vertices or points, `m` means edges. Space is extra memory.

## Problems 1 and 2 - Number of Provinces

- **DFS:** I use Depth-First Search to count the number of connected components, where each component represents one province. Initially, all cities are marked as unvisited. The recursive `dfs` scans a city's matrix row and marks reachable cities in `visited`. Each call from the outer loop starts a new province. Time Complexity: `O(n²)` because each city is visited once, and DFS scans n matrix entries per city; Space Complexity: `O(n)` for the visited array and recursion stack.
- **DSU:** The `parent[]` array stores the parent of each city in the DSU forest, while `rank[]` helps keep the trees shallow during merging. `find` compresses paths; `union` returns `true` only for different groups, so I decrease the province count then. Time Complexity: `O(n² α(n))` because I check `O(n²)` city pairs, and each DSU operation takes amortized `O(α(n))` time; Space Complexity: `O(n)` for the `parent[]` and `rank[]` arrays

## Problems 3 and 4 - Minimum Spanning Tree

Weights are Manhattan distances.

- **Kruskal:** First, I generate all possible undirected edges between pairs of points. Each `edge` stores its `Manhattan distance` and the indices of its two endpoints. I use `v = u + 1` to avoid generating duplicate edges. Next, I sort all edges by weight in ascending order. I initialize a `Disjoint Set Union` structure where each point starts in its own component. Then I process the sorted edges. For each edge, I call union() to check whether its endpoints belong to different components. If the union succeeds, I add the edge's weight to minCost and increment edgesUsed. Otherwise, I skip the edge because it would create a cycle. Time Complexity: `O(n² log n)`. The complete graph contains `O(n²)` edges, and sorting them takes `O(E log E)` = `O(n² log n)`. DSU operations are amortized `O(α(n))` each and do not dominate sorting; Space Complexity: `O(n²)` for storing all edges, plus `O(n)` for the DSU arrays.
- **Prim:** The `visited[]` array tracks which points are already included in the MST. The `minDistance[]` array stores the minimum cost of connecting each unvisited point to any point already in the tree. In each iteration, I select the unvisited point with the smallest `minDistance`, mark it as visited, and add its connection cost to `minCost`. Then I calculate the Manhattan distance from this point to every unvisited point and update minDistance whenever a cheaper connection is found. Time Complexity: `O(n²)` because each of the n iterations scans all points to find the next vertex and updates the distances to unvisited points; Space Complexity: `O(n)` for the `visited[]` and `minDistance[]` arrays, without explicitly storing all edges.

## Problems 5 and 6 - Software Dependency Groups

Groups are strongly connected components, including single modules. Arrays `head`, `to`, and `next` store adjacency lists.

- **Kosaraju:** First, I build the original graph and a reversed graph with every edge pointing in the opposite direction. I use `nodeStack` and `edgeStack` to simulate DFS and record each vertex in `finishOrder` after all its outgoing edges are processed. Then I reset `visited` and traverse the reversed graph in reverse finishing order. Each new traversal from an unvisited vertex finds one strongly connected component, so I increment `sccCount`. Time Complexity: `O(n + m)` because building the graphs and both DFS passes take linear time; Space Complexity: `O(n + m)` for the two graphs, stacks, and auxiliary arrays.
- **Tarjan:** I use one DFS pass and assign each new vertex a discovery time, initially setting `low` to the same value. For an edge to a vertex still in `tarjanStack`, I update the current vertex's `low` using that neighbor's discovery time. When returning from a child, I update the parent's `low` using the child's value. The `onStack` array prevents edges to completed components from affecting these updates. When `low[u] == discovery[u]`, I pop vertices through `u` and count them as one component. Time Complexity: `O(n + m)` because each vertex and edge is processed a constant number of times; Space Complexity: `O(n + m)` for the graph, stacks, and auxiliary arrays.

Both implementations use explicit DFS stacks to avoid recursive calls on long paths.

## Problems 7 and 8 - Course Schedule

`graph` is an adjacency list with edges from prerequisites to dependent courses.

- **DFS:** I build an adjacency list and use `state[]` to distinguish unvisited vertices (0), vertices on the current DFS path (1), and fully processed vertices (2). The recursive `hasCycle` marks a vertex as active before exploring its neighbors. An edge to another active vertex closes a cycle, so `canFinish` returns `false`. An edge to a finished vertex does not indicate a cycle. I start DFS from every unvisited course to cover disconnected parts of the graph. Time Complexity: `O(n + m)` because each vertex and edge is examined once; Space Complexity: `O(n + m)` for the graph, state array, and recursion stack.
- **Kahn:** I count each course's prerequisites in `inDegree[]` and put all courses with zero incoming edges into an `ArrayDeque`. Each time I remove a course, I increment `count` and decrease the incoming degrees of its dependent courses. A dependent course enters the queue when its degree becomes zero, meaning all its prerequisites have been processed. Finally, I return `count == numCourses`; if some courses remain, a cycle prevents completion. Time Complexity: `O(n + m)` because each course enters the queue once and each edge causes one decrement; Space Complexity: `O(n + m)` for the graph, degree array, and queue.

## Problems 9 and 10 - Course Schedule II

- **DFS:** I use the same three-state cycle detection as in Problem 7, but also append each course to `finishOrder` after processing all its neighbors. If a cycle is found, I return an empty array. Otherwise, I copy `finishOrder` backwards into `order`. In an acyclic graph, the destination of each edge finishes before its source, so reversing the list places every prerequisite before its dependent course. Time Complexity: `O(n + m)` for DFS and reversing the finishing order; Space Complexity: `O(n + m)` for the graph, state array, recursion stack, and result lists or arrays.
- **Kahn:** I use the same incoming-degree array and queue as in Problem 8. The difference is that I save each removed course in `order[count++]`. A course is recorded only after all its prerequisites have been processed, so this directly produces a valid order without reversing it. If `count` equals `numCourses`, I return `order`; otherwise, I return an empty array because a complete schedule does not exist. Time Complexity: `O(n + m)` because each vertex and edge is processed once; Space Complexity: `O(n + m)` for the graph, degree array, queue, and result array.
