package io.json

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import java.util.*
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


data class GraphItself(
    val vertices: MutableMap<Long, VertexInfo>,
    val edges: MutableMap<Long, EdgeInfo>,

    )

private data class GraphRepresentation(
    val graph: GraphItself,
    val display: Display,
)

fun writeInJsonGraph(mainScreenViewModel: MainScreenViewModel) {
    val jsow = jacksonObjectMapper().writer().withDefaultPrettyPrinter()
    var DisplayVertices = mutableMapOf<Long, VertexInfo>()
    var DisplayEdges = mutableMapOf<Long, EdgeInfo>()
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
            color = it.u.color,  // исправлено на it.u.color для vertex2
        )
        val edge = EdgeInfo(
            from = vertex2.element,
            to = vertex1.element,
            color = it.color,
            weight = it.e.weight,
            width = it.width
        )

        DisplayVertices.put(it.v.v.element, vertex1)
        DisplayVertices.put(it.u.v.element, vertex2)
        DisplayEdges.put(it.e.element, edge)
    }


    var display = Display(
        scale = mainScreenViewModel.scale.value,
        offsetX = mainScreenViewModel.offset.value.x,
        offsetY = mainScreenViewModel.offset.value.y,
        displayWeight = mainScreenViewModel.displayWeight.value,
        graphType = mainScreenViewModel.graph::class.simpleName.toString(),
        vertices = DisplayVertices,
        edges = DisplayEdges,
    )
    val jsonGraph = jsow.writeValueAsString(display)
    val file =
        File("C:\\Users\\dabze\\OneDrive\\Рабочий стол\\я говножуй\\jsonGraph.json")  // укажите желаемый путь к файлу
    file.writeText(jsonGraph)
    println("Graph has been written to ${file.absolutePath}")
}

fun readFromJsonGraph(mainScreenViewModel: MainScreenViewModel, path: String): MainScreenViewModel {
    val mapper = jacksonObjectMapper()
    val file = File(path)
    val jsonGraph = file.readText()
    val graphRepresentation = mapper.readValue<Display>(jsonGraph)

    val graph = when (graphRepresentation.graphType) {
        "DirectedGraph" -> DirectedGraph()
        "UndirectedGraph" -> UndirectedGraph()
        "WeightedDirectedGraph" -> WeightedDirectedGraph()
        "WeightedUndirectedGraph" -> WeightedUndirectedGraph()
        else -> throw Exception("Incorrect graph type.")
    }

    println(graphRepresentation.graphType)
    println(graph is WeightedUndirectedGraph)
    println(graph::class.simpleName)

    // Добавляем вершины в граф
    graphRepresentation.vertices.forEach { (_, vertexInfo) ->
        graph.addVertex(vertexInfo.element)
    }

    // Добавляем рёбра в граф
    graphRepresentation.edges.forEach { (key, edgeInfo) ->
        graph.addEdge(edgeInfo.from, edgeInfo.to, key, edgeInfo.weight)
    }

    mainScreenViewModel.graphViewModel = GraphViewModel(graph)
    println(mainScreenViewModel.graphViewModel.graph::class.simpleName)
    println(mainScreenViewModel.graph::class.simpleName)
    mainScreenViewModel.graph = graph

    // Обновляем свойства вершин в представлении
    mainScreenViewModel.graphViewModel.vertices.forEach {
        val element = graphRepresentation.vertices[it.v.element] ?: return@forEach
        it.x = element.x
        it.y = element.y
        it.color = element.color
    }

    // Обновляем свойства рёбер в представлении
    mainScreenViewModel.graphViewModel.edges.forEach {
        val edgeInfo = graphRepresentation.edges[it.e.element] ?: return@forEach
        it.color = edgeInfo.color
        it.width = edgeInfo.width // Пример преобразования веса в ширину ребра
    }

    mainScreenViewModel.scale.value = graphRepresentation.scale
    mainScreenViewModel.offset.value = DpOffset(graphRepresentation.offsetX, graphRepresentation.offsetY)
    mainScreenViewModel.displayWeight.value = graphRepresentation.displayWeight
    return mainScreenViewModel
}

