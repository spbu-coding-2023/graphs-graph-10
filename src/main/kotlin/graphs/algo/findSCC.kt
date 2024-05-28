package graphs.algo


import graphs.primitives.Graph
import graphs.primitives.Vertex


fun findSCC(graph: Graph): List<List<Vertex>> {
    val stack = mutableListOf<Vertex>()
    val visited = mutableSetOf<Vertex>()

    fun dfs1(vertex: Vertex) {
        visited.add(vertex)
        for (edge in graph.edges) {
            if (edge.vertices.first == vertex && edge.vertices.second !in visited) {
                dfs1(edge.vertices.second)
            }
        }
        stack.add(vertex)
    }

    for (vertex in graph.vertices) {
        if (vertex !in visited) {
            dfs1(vertex)
        }
    }

    val reversedEdges = createReversedEdges(graph)

    visited.clear()
    val components = mutableListOf<List<Vertex>>()

    fun dfs2(vertex: Vertex, component: MutableList<Vertex>) {
        visited.add(vertex)
        component.add(vertex)
        for (edge in reversedEdges) {
            if (edge.first == vertex && edge.second !in visited) {
                dfs2(edge.second, component)
            }
        }
    }

    while (stack.isNotEmpty()) {
        val vertex = stack.removeAt(stack.size - 1)
        if (vertex !in visited) {
            val component = mutableListOf<Vertex>()
            dfs2(vertex, component)
            components.add(component)
        }
    }

    return components
}

private fun createReversedEdges(graph: Graph): List<Pair<Vertex, Vertex>> {
    val res = mutableListOf<Pair<Vertex, Vertex>>()
    for (e in graph.edges) {
        res.add(Pair(e.vertices.second, e.vertices.first))
    }
    return res
}