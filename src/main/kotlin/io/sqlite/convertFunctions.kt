package io.sqlite

import io.sqlite.connectedEntity.GraphDBFormat
import viewmodel.graph.GraphViewModel

fun convertToDBFormat(existedGraph: GraphViewModel<String, Long>): GraphDBFormat<String, Long> {
    val newGraph = GraphDBFormat<String, Long>()
    val vertexes = existedGraph.vertices
    val edges = existedGraph.edges
    for (vertex in vertexes) {
        val element = vertex.v.element
        val color = vertex.color.value
        val x = vertex.x.value
        val y = vertex.y.value
        newGraph.addVertex(element, color, x, y)
    }
    for (edge in edges) {
        val element = edge.e.element
        val weight = edge.e.weight!!
        val color = edge.color.value
        val firstVertex = edge.u.v.element
        val secondVertex = edge.v.v.element
        newGraph.addEdge(element, weight, color, firstVertex, secondVertex)
    }
    return newGraph
}