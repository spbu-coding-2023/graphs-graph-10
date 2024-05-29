package io.sqlite

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import io.neo4j.VertexData
import io.sqlite.connectedEntity.GraphDBFormat
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel


fun convertToDBFormat(existedModel: MainScreenViewModel): GraphDBFormat {
    val newGraph = GraphDBFormat()

    newGraph.scale = existedModel.scale.value
    newGraph.offsetX = existedModel.offset.value.x.value
    newGraph.offsetY = existedModel.offset.value.y.value
    newGraph.displayWeight = if (existedModel.displayWeight.value) 1 else 0
    newGraph.graphType = if (existedModel.graph::class.simpleName is String)
        existedModel.graph::class.simpleName!! else "None"

    val vertices = existedModel.graphViewModel.vertices
    val edges = existedModel.graphViewModel.edges
    for (vertex in vertices) {
        val element = vertex.v.element
        val r = vertex.color.red
        val g = vertex.color.green
        val b = vertex.color.blue
        val color = "${r}e${g}e${b}"
        val x = vertex.x.value
        val y = vertex.y.value
        newGraph.addVertex(element, color, x, y)
    }
    for (edge in edges) {
        val element = edge.e.element
        val weight = edge.e.weight?: 0
        val r = edge.color.red
        val g = edge.color.green
        val b = edge.color.blue
        val color = "${r}e${g}e${b}"
        val firstVertex = edge.u.v.element
        val secondVertex = edge.v.v.element
        val width = edge.width
        newGraph.addEdge(element, weight, color, firstVertex, secondVertex, width)
    }
    return newGraph
}

fun installGraph(mainScreenViewModel: MainScreenViewModel, graphDB: GraphDBFormat) {
    val graph: Graph = when (graphDB.graphType) {
        DirectedGraph::class.simpleName ->
            DirectedGraph()
        UndirectedGraph::class.simpleName ->
            UndirectedGraph()
        WeightedDirectedGraph::class.simpleName ->
            WeightedDirectedGraph()
        WeightedUndirectedGraph::class.simpleName ->
            WeightedUndirectedGraph()
        else -> throw Exception("Incorrect graph type.")
    }

    val vertexMap = mutableMapOf<Long, VertexData>()
    val edgeMap = mutableMapOf<Long, Pair<Color, Float>>()

    for (vertex in graphDB.vertices) {
        graph.addVertex(vertex.element)
        val color = vertex.color.split("e")
        vertexMap[vertex.element] =
            VertexData(
                vertex.posX,
                vertex.posY,
                Color(color[0].toFloat(), color[1].toFloat(), color[2].toFloat())
            )
    }
    for (edge in graphDB.edges) {
        graph.addEdge(edge.firstVertex, edge.secondVertex, edge.element, if (edge.weight == 0L) null else edge.weight)
        val color = edge.color.split("e")
        edgeMap[edge.element] = Pair(Color(color[0].toFloat(), color[1].toFloat(), color[2].toFloat()), edge.width)
    }

    mainScreenViewModel.graphViewModel = GraphViewModel(graph)

    mainScreenViewModel.graphViewModel.vertices.forEach {
        val element = vertexMap[it.v.element] ?: return@forEach
        it.x = element.x.dp
        it.y = element.y.dp
        it.color = element.color
    }

    mainScreenViewModel.graphViewModel.edges.forEach {
        val (color, width) = edgeMap[it.e.element] ?: return@forEach
        it.color = color
        it.width = width
    }

    mainScreenViewModel.scale.value = graphDB.scale
    mainScreenViewModel.offset.value = DpOffset(graphDB.offsetX.dp, graphDB.offsetY.dp)
    mainScreenViewModel.displayWeight.value = graphDB.displayWeight == 1
}
