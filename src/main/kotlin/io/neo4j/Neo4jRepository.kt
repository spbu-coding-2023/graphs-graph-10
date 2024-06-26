package io.neo4j

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

data class VertexData (
    val x: Float,
    val y: Float,
    val color: Color
)

class Neo4jRepository {
    private lateinit var driver: Driver
    private lateinit var session: Session

    fun connect(uri: String, user: String, password: String) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))
        driver.verifyConnectivity()
        session = driver.session()
        println("Connected to Neo4l db.")
    }

    fun writeData(mainScreenViewModel: MainScreenViewModel) {
        clearDB()
        val buffer = StringBuilder()
        mainScreenViewModel.graphViewModel.vertices.forEach {
            buffer.append(
                "CREATE (v${it.v.element}:Vertex {element: ${it.v.element}, x: ${it.x.value}, y: ${it.y.value}, color: '${it.color.value}'}) \n"
            )
        }
        session.executeWrite { tx -> tx.run(buffer.toString()) }

        session.executeWrite { tx ->
            val scale = mainScreenViewModel.scale.value
            val offsetX = mainScreenViewModel.offset.value.x.value
            val offsetY = mainScreenViewModel.offset.value.y.value
            val displayWeight = mainScreenViewModel.displayWeight.value
            val graphType = mainScreenViewModel.graph::class.simpleName
            println(graphType)
            tx.run(
                "CREATE (:GraphInfo {" +
                        "scale: $scale, " +
                        "offsetX: $offsetX, " +
                        "offsetY: $offsetY, " +
                        "displayWeight: $displayWeight, " +
                        "graphType: '$graphType'" +
                "})"
            )
        }

        mainScreenViewModel.graphViewModel.edges.forEach {
            val query =
                "MATCH (u:Vertex) WHERE u.element = ${it.u.v.element} \n" +
                "MATCH (v:Vertex) WHERE v.element = ${it.v.v.element} \n" +
                "CREATE (u)-[:Edge {weight: ${it.e.weight}, u: ${it.u.v.element}, " +
                        "v: ${it.v.v.element}, e: ${it.e.element}, color: '${it.color.value}', width: ${it.width}}]->(v) \n"
            session.executeWrite { tx ->
                tx.run(query)
            }
        }
        println("Graph was saved")
    }

    fun readData(mainScreenViewModel: MainScreenViewModel) {
        var graph: Graph
        val vertexMap = mutableMapOf<Long, VertexData>()
        val edgeMap = mutableMapOf<String, Pair<Color, Float>>()
        session.executeRead { tx ->
            var result =
                tx.run(
                    "MATCH (v:GraphInfo) return v.graphType as graphType, v.scale as scale, v.offsetX as offsetX, v.offsetY as offsetY, v.displayWeight as displayWeight"
                )

            val record = result.stream().findFirst().get()
            val graphType = record["graphType"].asString()
            val scale = record["scale"].toString().toFloat()
            val offsetX = record["offsetX"].toString().toFloat()
            val offsetY = record["offsetY"].toString().toFloat()
            val displayWeight = record["displayWeight"].toString().toBoolean()

            graph = when (graphType) {
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

            result =
                tx.run(
                    "MATCH (v:Vertex) RETURN v.element as element, v.x as x, v.y as y, v.color as color"
                )
            result.stream().forEach {
                graph.addVertex(it["element"].asLong())
                vertexMap[it["element"].asLong()] =
                    VertexData(
                        it["x"].toString().toFloat(),
                        it["y"].toString().toFloat(),
                        Color(it["color"].asString().toULong())
                    )
            }
            result =
                tx.run(
                    "MATCH ()-[e:Edge]->() RETURN e.u as u, e.v as v, e.e as e, e.weight as weight, e.color as color, e.width as width"
                )
            result.stream().forEach {
                graph.addEdge(
                    it["u"].asLong(),
                    it["v"].asLong(),
                    it["e"].asLong(),
                    weight = (it["weight"].toString().toLongOrNull())
                )
                edgeMap[it["e"].toString()] = Pair(Color(it["color"].asString().toULong()), it["width"].asFloat())
            }

            mainScreenViewModel.graphViewModel = GraphViewModel(graph)

            mainScreenViewModel.graphViewModel.vertices.forEach {
                val element = vertexMap[it.v.element] ?: return@forEach
                it.x = element.x.dp
                it.y = element.y.dp
                it.color = element.color
            }
            mainScreenViewModel.graphViewModel.edges.forEach {
                val (color, width) = edgeMap[it.e.element.toString()] ?: return@forEach
                it.color = color
                it.width = width
            }
            mainScreenViewModel.scale.value = scale
            mainScreenViewModel.offset.value = DpOffset(offsetX.dp, offsetY.dp)
            mainScreenViewModel.displayWeight.value = displayWeight
            mainScreenViewModel.graph = graph
        }
        println("Graph was loaded")
    }

    private fun clearDB() {
        session.executeWrite { tx ->
            tx.run("match (a) -[r] -> () delete a, r")
            tx.run("match (a) delete a")
            println("Remove all data from Neo4j db.")
        }
    }

    fun close() {
        session.close()
        driver.close()
    }
}
