package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph

fun kruskal(graph: Graph): List<Edge> {
    val edgeList = mutableListOf<Edge>()
    val sccmap = HashMap<Long, Long>()
    val mst = mutableListOf<Edge>()

    graph.edges.forEach { edgeList.add(it) }
    graph.vertices.forEach { sccmap[it.element] = it.element }
    edgeList.sortBy { it.weight }

    fun repaintTheMap(to: Long, from: Long, sccmap: HashMap<Long, Long>) {
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
            mst.add(edge)
            repaintTheMap(fromComponent!!, toComponent!!, sccmap)
        }
    }

    return mst
}
