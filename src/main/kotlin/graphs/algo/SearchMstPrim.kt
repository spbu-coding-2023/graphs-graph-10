package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import java.util.*

fun searchMstPrim(graph: Graph, startVertex: Long): List<Pair<Long, Long>> {
    val mst = mutableListOf<Pair<Long, Long>>()
    val priorityQueue = PriorityQueue<Edge>(compareBy { it.weight as Long })
    val addedVertices = mutableSetOf<Long>()
    addedVertices.add(startVertex)

    graph.edges.filter { it.vertices.first.element == startVertex ||
            it.vertices.second.element == startVertex }.forEach { priorityQueue.add(it) }

    while (!priorityQueue.isEmpty()) {
        val edge = priorityQueue.poll()
        val newVertex = if (edge.vertices.first.element in addedVertices)
            edge.vertices.second.element else edge.vertices.first.element

        if (newVertex !in addedVertices) {

            mst.add(Pair(edge.vertices.first.element, edge.vertices.second.element))

            addedVertices.add(newVertex)

            graph.edges.filter { (it.vertices.first.element == newVertex ||
                    it.vertices.second.element == newVertex) &&
                    (it.vertices.first.element !in addedVertices ||
                            it.vertices.second.element !in addedVertices) }.forEach { priorityQueue.add(it) }
        }
    }

    return mst
}
