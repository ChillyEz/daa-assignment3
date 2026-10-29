# DAA Assignment 3 - Report

`n` means vertices or points, `m` means edges. Space is extra memory.

## Problems 1 and 2 - Number of Provinces

- **DFS:** The recursive `dfs` scans a city's matrix row and marks reachable cities in `visited`. Each call from the outer loop starts a new province. Time: `O(n²)`; space: `O(n)`.
- **DSU:** `parent` stores each city's DSU parent, and `rank` guides merging. `find` compresses paths; `union` returns `true` only for different groups, so I decrease the province count then. Time: `O(n² α(n))`; space: `O(n)`. Here, `α(n)` is practically constant.

## Problems 3 and 4 - Minimum Spanning Tree

Weights are Manhattan distances.

- **Kruskal:** I store edges as `[distance, u, v]` and sort by distance. A successful DSU merge adds the edge's cost; I stop after `n - 1` accepted edges. Time: `O(n² log n)`; space: `O(n²)`.
- **Prim:** `minDistance` stores each point's cheapest connection to the tree. I scan for the cheapest unvisited point, mark it in `visited`, and update distances to the remaining points. Time: `O(n²)`; space: `O(n)`.

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
