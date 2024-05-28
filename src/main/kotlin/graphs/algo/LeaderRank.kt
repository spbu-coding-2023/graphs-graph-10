package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import graphs.primitives.Vertex

import kotlin.math.abs


fun LeaderRank(G: Graph, d: Double, epsilon: Double): List<Pair<Vertex, Double>> {
    val adjacencyList = toAdjacencyList(G)
    val n = G.vertices.size
    val s = G.addVertex(-1L)
    G.vertices.forEach { vertex ->
        if (vertex.element != s.element) {
            G.addEdge(s.element, vertex.element, -1L, 1L)
            G.addEdge(vertex.element, s.element, -1L, 1L)
        }
    }

    var R = mutableMapOf<Vertex, Double>()
    G.vertices.forEach { vertex -> R[vertex] = 1.0 / (n + 1) }

    val deg = mutableMapOf<Long, Int>()
    adjacencyList.forEach { (vertex, neighbors) ->
        deg[vertex] = neighbors.size
    }

    var diff = Double.MAX_VALUE
    while (diff >= epsilon) {
        val R_new = mutableMapOf<Vertex, Double>()
        G.vertices.forEach { v ->
            var sum = 0.0
            adjacencyList.forEach { (u, neighbors) ->
                if (neighbors.any { it.first == v.element }) {
                    val weight = neighbors.find { it.first == v.element }?.second?.toDouble() ?: 0.0
                    sum += (d * weight / (deg[u]?.toDouble() ?: 1.0)) * (R[G.vertices.find { it.element == u } ?: s]
                        ?: 0.0)
                }
            }
            R_new[v] = (1 - d) / (n + 1) + sum
        }

        diff = 0.0
        G.vertices.forEach { v ->
            diff += abs(R_new[v]!! - R[v]!!)
        }

        R = R_new
    }
    remove(G, s)
    R.remove(s)

    val sortedRatings = R.toList().sortedByDescending { it.second }
    return sortedRatings
}

fun remove(G: Graph, vertex: Vertex) {
    val edgesToRemove = mutableListOf<Edge>()
    G.edges.forEach { edge ->
        if (edge.vertices.first == vertex || edge.vertices.second == vertex) {
            edgesToRemove.add(edge)
        }
    }
    edgesToRemove.forEach { edge ->
        (G.edges as MutableCollection<Edge>).remove(edge)
    }
    (G.vertices as MutableCollection<Vertex>).remove(vertex)
}