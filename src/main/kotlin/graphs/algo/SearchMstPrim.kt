package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import java.util.*

fun searchMstPrim(graph: Graph, startVertex: Long): List<Pair<Long, Long>> {
    val mst = mutableListOf<Pair<Long, Long>>()
    val priorityQueue = PriorityQueue<Edge>(compareBy { it.weight as Long })
    val addedVertices = mutableSetOf<Long>()

    fun addEdges(vertex: Long) {
        graph.edges.filter {
            (it.vertices.first.element == vertex || it.vertices.second.element == vertex) &&
                    (it.vertices.first.element !in addedVertices || it.vertices.second.element !in addedVertices)
        }.forEach { priorityQueue.add(it) }
    }

    addedVertices.add(startVertex)
    addEdges(startVertex)

    while (addedVertices.size < graph.vertices.size) {
        if (priorityQueue.isEmpty()) {

            val newStartVertex = graph.vertices.map { it.element }.firstOrNull { it !in addedVertices }
            if (newStartVertex != null) {
                addedVertices.add(newStartVertex)
                addEdges(newStartVertex)
            } else {
                break
            }
        } else {
            val edge = priorityQueue.poll()
            val newVertex = if (edge.vertices.first.element in addedVertices)
                edge.vertices.second.element else edge.vertices.first.element

            if (newVertex !in addedVertices) {
                mst.add(Pair(edge.vertices.first.element, edge.vertices.second.element))
                addedVertices.add(newVertex)
                addEdges(newVertex)
            }
        }
    }

    return mst
}