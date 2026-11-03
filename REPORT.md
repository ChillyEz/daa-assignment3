# DAA Assignment 3 - Report

`n` means vertices or points, `m` means edges. Space is extra memory.

## Problems 1 and 2 - Number of Provinces

- **DFS:** I use Depth-First Search to count the number of connected components, where each component represents one province. Initially, all cities are marked as unvisited. The recursive `dfs` scans a city's matrix row and marks reachable cities in `visited`. Each call from the outer loop starts a new province. Time Complexity: `O(n²)` because each city is visited once, and DFS scans n matrix entries per city; Space Complexity: `O(n)` for the visited array and recursion stack.
- **DSU:** The `parent[]` array stores the parent of each city in the DSU forest, while `rank[]` helps keep the trees shallow during merging.. `find` compresses paths; `union` returns `true` only for different groups, so I decrease the province count then. Time Complexity: `O(n² α(n))` because I check `O(n²)` city pairs, and each DSU operation takes amortized `O(α(n))` time; Space Complexity: `O(n)` for the `parent[]` and `rank[]` arrays

## Problems 3 and 4 - Minimum Spanning Tree

Weights are Manhattan distances.

- **Kruskal:** First, I generate all possible undirected edges between pairs of points. Each `edge` stores its `Manhattan distance` and the indices of its two endpoints. I use `v = u + 1` to avoid generating duplicate edges. Next, I sort all edges by weight in ascending order. I initialize a `Disjoint Set Union` structure where each point starts in its own component.Then I process the sorted edges. For each edge, I call union() to check whether its endpoints belong to different components. If the union succeeds, I add the edge's weight to minCost and increment edgesUsed. Otherwise, I skip the edge because it would create a cycle. Time Complexity: `O(n² log n)`. The complete graph contains `O(n²)` edges, and sorting them takes `O(E log E)` = `O(n² log n)`. DSU operations are amortized `O(α(n))` each and do not dominate sorting.; Space Complexity: `O(n²)` for storing all edges, plus `O(n)` for the DSU arrays.
- **Prim:** The `visited[]` array tracks which points are already included in the MST. The `minDistance[]` array stores the minimum cost of connecting each unvisited point to any point already in the tree. In each iteration, I select the unvisited point with the smallest `minDistance`, mark it as visited, and add its connection cost to `minCost`. Then I calculate the Manhattan distance from this point to every unvisited point and update minDistance whenever a cheaper connection is found. Time Complexity: `O(n²)` because each of the n iterations scans all points to find the next vertex and updates the distances to unvisited points.; Space Complexity: `O(n)` for the `visited[]` and `minDistance[]` arrays, without explicitly storing all edges.

## Problems 5 and 6 - Software Dependency Groups

Groups are strongly connected components, including single modules. Arrays `head`, `to`, and `next` store adjacency lists.

- **Kosaraju:** `nodeStack` and `edgeStack` simulate DFS, adding finished vertices to `finishOrder`. I reset `visited`, then traverse the reversed graph in reverse finishing order, counting one component per traversal.
- **Tarjan:** `discovery` records visit indices; `low` is updated from DFS children and edges to vertices marked `onStack`. When `low[u] == discovery[u]`, I pop `tarjanStack` through `u` and count one component.

Both use `O(n + m)` time and space. Explicit stacks avoid recursion on long paths.

## Problems 7 and 8 - Course Schedule

`graph` is an adjacency list with edges from prerequisites to dependent courses.

- **DFS:** `state` is 0 for unvisited, 1 for the current path, and 2 for finished vertices. Recursive `hasCycle` detects a cycle when it finds an edge to state 1.
- **Kahn:** `inDegree` counts prerequisites. An `ArrayDeque` queues zero-degree courses; processing one decreases its neighbors' degrees and queues newly available courses. The result is `count == numCourses`.

Both use `O(n + m)` time and space.

## Problems 9 and 10 - Course Schedule II

- **DFS:** After visiting all neighbors, I append the course to `finishOrder`, then copy that list backwards into `order` so prerequisites come first.
- **Kahn:** I use the same queue processing as Problem 8, saving each removed course in `order[count++]`.

Both return an empty array for cycles and use `O(n + m)` time and space.
