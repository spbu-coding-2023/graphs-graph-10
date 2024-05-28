# Graph analysis app(team 10)


Application for **`graph`** visualization. Has the ability to work with [[***Directed***](https://en.wikipedia.org/wiki/Directed_graph)/[***Undirected***](https://en.wikipedia.org/wiki/Graph_(discrete_mathematics))]  [[***Weighted***](https://www.baeldung.com/cs/weighted-vs-unweighted-graphs)/[***Unweighted***](https://www.baeldung.com/cs/weighted-vs-unweighted-graphs)] **`graphs`**
![2024-05-28-15-41-18](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/5a418fb4-8165-4923-8944-c8711a80214b)

___
# Algorithms

- [**Kruskal**](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)

- [**Dijkstra's**](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)

- [**Tarjan**](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm)


- [**Find Bridge**](https://en.wikipedia.org/wiki/Bridge_(graph_theory))

- [**Ford Bellman**](https://en.wikipedia.org/wiki/Bellman–Ford_algorithm)

- [**Prim**](https://en.wikipedia.org/wiki/Prim%27s_algorithm)

- [**Louvain**](https://en.wikipedia.org/wiki/Louvain_method)
___
# Other Functionality
- Read and wirte [CSV](https://en.wikipedia.org/wiki/Comma-separated_values)
- Read and write [Neo4J](https://en.wikipedia.org/wiki/Neo4j)
- Read and write [SQLite](https://en.wikipedia.org/wiki/SQLite)

**Detailed instructions are provided in** [**this package**](https://github.com/spbu-coding-2023/graphs-graph-10/tree/main/src/main/kotlin/io)
# Installation and launch
- **Installation**
  ```
  git clone git@github.com:spbu-coding-2023/graphs-graph-10.git
  ```
- **launch**
   ```
  ./gradlew run
  ```
# How to use
**Creating a graph**:

**`vertices`** and `edge` **`weights`** (if any) must be of type ***`Long`***
```kotlin
val graph1 : Graph = WeightedDirectedGraph()
val graph2 : Graph = WeightedUndirectedGraph()
val graph3 : Graph = UndirectedGraph()
val graph4 : Graph = DirectedGraph()
```
To create add `vertices` and `edges`, as well as `weights` (for WeightedGraph only), **follow these steps**:
```kotlin
val graph1 : Graph = WeightedDirectedGraph().apply {
        addVertex(1)
        addVertex(2)
        addEdge(1, 2, 1, 1000) // for WeightedGraph
    }

    val graph2 : Graph = DirectedGraph().apply {
        addVertex(1)
        addVertex(2)
        addEdge(1, 2, 1) // for UnweightedGraph
    }
```


# Communities
[**Louvain**](https://en.wikipedia.org/wiki/Louvain_method) **algorithm** is used to *find communities*

Accepts **only** the `graph` itself and returns a list of lists that store vertices that form a `community`
```kotlin
fun louvain(graph: Graph): List<Set<Long>>
```
![2024-05-28-15-41-39](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/9968e370-9719-4fc1-a070-d56822f2942d)
# Find Cycle
It takes the `graph` itself and the `vertex` for which we want to **`find cycle`**  and `return` an `list of vertices`
``` kotlin
fun findCycle(graph: Graph, v: Long): List<Long>
```
![2024-05-28-16-23-14](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/c8a562f8-cbf9-4c12-aa07-7d81e98f4f1c)

# Articulated vertices
To implement the functionality, the [**Tarjan**](https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm) **algorithm** is used

Accepts **only** the `graph` itself and returns a list of vertices
```kotlin
fun findArticulationVerticesTarjan(graph: Graph): List<Long> {
```
 ![2024-05-28-16-34-00](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/6d1a76f9-3256-47cd-a4e0-1682d5dc0246)
 # Find key vertices
In order to identify the most important or influential `vertices` in the `graph`, used `Leader Rank` function, which accepts 2 parameters of the `Double` type
```kotlin
fun LeaderRank(G: Graph, d: Double, epsilon: Double): List<Pair<Vertex, Double>> 
```
![2024-05-28-17-34-46](https://github.com/spbu-coding-2023/graphs-graph-10/assets/117384050/8f6b3276-9526-45db-a0bd-0994ea54746d)
