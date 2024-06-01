package io.json

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel
import java.io.File

data class VertexInfo(
    val element: Long,
    val x: Dp,
    val y: Dp,
    val color: Color,
)

data class EdgeInfo(
    val from: Long,
    val to: Long,
    val color: Color,
    val weight: Long?,
    val width: Float,
)

data class Display(
    val scale: Float,
    var offsetX: Dp,
    var offsetY: Dp,
    val displayWeight: Boolean,
    val graphType: String,
    var vertices: MutableMap<Long, VertexInfo>,
    var edges: MutableMap<Long, EdgeInfo>,
)


fun writeInJsonGraph(mainScreenViewModel: MainScreenViewModel) {
    val jsow = jacksonObjectMapper().writer().withDefaultPrettyPrinter()
    val displayVertices = mutableMapOf<Long, VertexInfo>()
    val displayEdges = mutableMapOf<Long, EdgeInfo>()
    mainScreenViewModel.graphViewModel.vertices.forEach {
        val vertex = VertexInfo(
            element = it.v.element,
            x = it.x,
            y = it.y,
            color = it.color,
        )

        displayVertices[it.v.element] = vertex

    }
    mainScreenViewModel.graphViewModel.edges.forEach {
        val vertex1 = VertexInfo(
            element = it.v.v.element,
            x = it.v.x,
            y = it.v.y,
            color = it.v.color,
        )
        val vertex2 = VertexInfo(
            element = it.u.v.element,
            x = it.u.x,
            y = it.u.y,
            color = it.u.color,
        )
        val edge = EdgeInfo(
            from = vertex2.element,
            to = vertex1.element,
            color = it.color,
            weight = it.e.weight,
            width = it.width
        )
        displayEdges[it.e.element] = edge
    }


    val display = Display(
        scale = mainScreenViewModel.scale.value,
        offsetX = mainScreenViewModel.offset.value.x,
        offsetY = mainScreenViewModel.offset.value.y,
        displayWeight = mainScreenViewModel.displayWeight.value,
        graphType = mainScreenViewModel.graph::class.simpleName.toString(),
        vertices = displayVertices,
        edges = displayEdges,
    )
    val jsonGraph = jsow.writeValueAsString(display)
    val file =
        File("jsonGraph.json")
    file.writeText(jsonGraph)
    println("Graph has been written to ${file.absolutePath}")
}

fun readFromJsonGraph(mainScreenViewModel: MainScreenViewModel, path: String): MainScreenViewModel {

    val mapper = jacksonObjectMapper()
    val file = File(path)
    val jsonGraph = file.readText()
    val graphRepresentation: Display
    try {
        graphRepresentation = mapper.readValue(jsonGraph)
    } catch (e: Exception) {
        return mainScreenViewModel
    }
    val graph = when (graphRepresentation.graphType) {
        "DirectedGraph" -> DirectedGraph()
        "UndirectedGraph" -> UndirectedGraph()
        "WeightedDirectedGraph" -> WeightedDirectedGraph()
        "WeightedUndirectedGraph" -> WeightedUndirectedGraph()
        else -> throw Exception("Incorrect graph type.")
    }

    graphRepresentation.vertices.forEach { (_, vertexInfo) ->
        graph.addVertex(vertexInfo.element)
    }

    graphRepresentation.edges.forEach { (key, edgeInfo) ->
        graph.addEdge(edgeInfo.from, edgeInfo.to, key, edgeInfo.weight)
    }

    mainScreenViewModel.graphViewModel = GraphViewModel(graph)
    println(mainScreenViewModel.graphViewModel.graph::class.simpleName)
    println(mainScreenViewModel.graph::class.simpleName)
    mainScreenViewModel.graph = graph


    mainScreenViewModel.graphViewModel.vertices.forEach {
        val element = graphRepresentation.vertices[it.v.element] ?: return@forEach
        it.x = element.x
        it.y = element.y
        it.color = element.color
    }


    mainScreenViewModel.graphViewModel.edges.forEach {
        val edgeInfo = graphRepresentation.edges[it.e.element] ?: return@forEach
        it.color = edgeInfo.color
        it.width = edgeInfo.width
    }

    mainScreenViewModel.scale.value = graphRepresentation.scale
    mainScreenViewModel.offset.value = DpOffset(graphRepresentation.offsetX, graphRepresentation.offsetY)
    mainScreenViewModel.displayWeight.value = graphRepresentation.displayWeight
    return mainScreenViewModel
}

