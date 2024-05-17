package graphs.algo


import graphs.primitives.Edge
import graphs.primitives.Graph
import java.util.*


fun <V, E>searchMstPrim(graph: Graph<V, E>, startVertex: V): List<Pair<V, V>> {
    val mst = mutableListOf<Pair<V, V>>()
    val priorityQueue = PriorityQueue<Edge<E, V>>(compareBy { it.weight as Long})
    val addedVertices = mutableSetOf<V>()
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
