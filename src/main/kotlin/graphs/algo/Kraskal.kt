package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import graphs.primitives.Vertex


fun <V, E> Kraskal(graph: Graph<V, E>): List<Edge<E, V>> {
    val edgeList = mutableListOf<Edge<E, V>>()
    val sccmap = HashMap<V, V>()
    val MST = mutableListOf<Edge<E, V>>()

    graph.edges.forEach { edgeList.add(it) }
    graph.vertices.forEach { sccmap[it.element] = it.element }
    edgeList.sortBy { it.weight }

    fun repaintTheMap(to: V, from: V, sccmap: HashMap<V, V>) {
        sccmap.entries.forEach { entry ->
            if (entry.value == from) {
                entry.setValue(to)
            }
        }
    }

    edgeList.forEach { edge ->
        val fromComponent = sccmap[edge.vertices.first.element]
        val toComponent = sccmap[edge.vertices.second.element]

        if (fromComponent != toComponent) {
            MST.add(edge)
            repaintTheMap(fromComponent!!, toComponent!!, sccmap)
        }
    }

    return MST
}