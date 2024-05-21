package graphs.algo


import graphs.primitives.Graph
import graphs.primitives.Vertex


fun <V, E> findSCC(graph: Graph<V, E>): List<List<Vertex<V>>> {
    val stack = mutableListOf<Vertex<V>>()
    val visited = mutableSetOf<Vertex<V>>()

    fun dfs1(vertex: Vertex<V>) {
        visited.add(vertex)
        for (edge in graph.edges) {
            if (edge.vertices.first == vertex && edge.vertices.second !in visited) {
                dfs1(edge.vertices.second)
            }
        }
        stack.add(vertex)
    }

    for (vertex in graph.vertices) {
        if (vertex !in visited)
            dfs1(vertex)
    }

//    val reverseGraph = getReverseGraph(graph)
//    visited.clear()
//
//    fun dfs2(vertex: Vertex<V>, component: MutableList<Vertex<V>>) {
//        visited.add(vertex)
//        component.add(vertex)
//        for (edge in reverseGraph.edges) {
//            if (edge.vertices.second == vertex && edge.vertices.first !in visited) {
//                dfs2(edge.vertices.first, component)
//            }
//        }
//    }
//
    val stronglyConnectedComponents = mutableListOf<List<Vertex<V>>>()
//
//    while (stack.isNotEmpty()) {
//        val vertex = stack.removeAt(stack.size - 1)
//        if (vertex !in visited) {
//            val component: MutableList<Vertex<V>> = mutableListOf()
//            dfs2(vertex, component)
//            stronglyConnectedComponents.add(component)
//        }
//    }

    return stronglyConnectedComponents
}

//fun <V, E> getReverseGraph(graph: Graph<V, E>): Graph<V, E> {
//    val reverseGraph = Graph<V, E>()
//    reverseGraph.vertices.addAll(graph.vertices)
//    for (edge in graph.edges) {
//        reverseGraph.edges.add(Edge(edge.element, edge.to, edge.from))
//    }
//    return reverseGraph
}