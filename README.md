# Graph analysis app

Application for **`graph`** visualization. Has the ability to work with [***Directed***](https://en.wikipedia.org/wiki/Directed_graph), [***Undirected***](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics)), [***Weighted***](https://www.baeldung.com/cs/weighted-vs-unweighted-graphs), [***Unweighted***](https://www.baeldung.com/cs/weighted-vs-unweighted-graphs) **graphs**.

![2024-05-28-15-41-18](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/5a418fb4-8165-4923-8944-c8711a80214b)

___
# Algorithms

### Layout
- [**Yifan Hu**](http://yifanhu.net/PUB/graph_draw_small.pdf)

### PathFinding
- [**Dijkstra**](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)
- [**Ford Bellman**](https://en.wikipedia.org/wiki/Bellman–Ford_algorithm)
- [**A***](https://en.wikipedia.org/wiki/A*_search_algorithm)

### MST
- [**Kruskal**](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)
- [**Prim**](https://en.wikipedia.org/wiki/Prim%27s_algorithm)

### Communities
- [**Louvain**](https://en.wikipedia.org/wiki/Louvain_method)

### Key vertices
- [**LeaderRank**](https://www.sciencedirect.com/science/article/abs/pii/S0378437114001502)

### Other
- [**Find Bridges**](https://en.wikipedia.org/wiki/Bridge_(graph_theory))
- [**Find cycle for vertex**](https://en.wikipedia.org/wiki/Cycle_(graph_theory))
- [**Articulated vertices Tarjan**](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm)
- SCC naive
___
# Input/Output operations
- Load **any** graph into this app if it's represents CSV with [adjacency matrix](https://en.wikipedia.org/wiki/Adjacency_matrix).
- Find some graph files in csv format in **examples** directory.
- Store current app state into [Neo4J](https://en.wikipedia.org/wiki/Neo4j) or [SQLite](https://en.wikipedia.org/wiki/SQLite) or [JSON](https://en.wikipedia.org/wiki/JSON) databases.

# Installation and launch
- **Installation**
```
git clone https://github.com/spbu-coding-2023/graphs-graph-10.git
```
- **Launch**
```
./gradlew run
```

# Some illustrated examples of work

## Communities
[**Louvain**](https://en.wikipedia.org/wiki/Louvain_method) **algorithm** is used to *find communities*

![2024-05-28-15-41-39](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/9968e370-9719-4fc1-a070-d56822f2942d)
## Find Cycle
You need to select **one** `vertex` and the app will draw all the `cycles` passing through this `vertex`

![2024-05-28-16-23-14](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/c8a562f8-cbf9-4c12-aa07-7d81e98f4f1c)

## Articulated vertices
To implement the functionality, the [**Tarjan**](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm) **algorithm** is used

 ![2024-05-28-16-34-00](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/6d1a76f9-3256-47cd-a4e0-1682d5dc0246)
## Find key vertices
In order to identify the most important or influential `vertices` in the `graph`, used `Leader Rank` function, which accepts 2 parameters of the `Double` type

![2024-05-28-17-34-46](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/8f6b3276-9526-45db-a0bd-0994ea54746d)
## Path Finding
In our app, you can find the **`path`** between two vertices using `3 algorithms`

All 3 algorithms identify the **minimum path** between two `vertices`

An example would be [**Dijkstra**](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) algorithm

![2024-05-29-10-47-26-_online-video-cutter com_](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/7453e26b-b059-45a7-a18d-3d01e875af71)

## Draw [**MST**](https://ru.wikipedia.org/wiki/MST)

The application supports 2 algorithms: [**Kruskal**](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm) and [**Prim**](https://en.wikipedia.org/wiki/Prim%27s_algorithm)

![2024-05-29-11-08-45](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/f4774cc5-55c2-4c23-b293-2edc9213b302)

[**LICENSE**](https://github.com/spbu-coding-2023/graphs-graph-10/blob/main/LICENSE)



