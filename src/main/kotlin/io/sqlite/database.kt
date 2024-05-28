package io.sqlite


import io.sqlite.connectedEntity.GraphDBFormat
import viewmodel.graph.EdgeViewModel
import viewmodel.graph.VertexViewModel
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


class GraphDatabase(private val databaseUrl: String) {
    private var connection: Connection? = null

    init {
        connect()
        createGraphListTable()
    }

    private fun connect() {
        connection = DriverManager.getConnection("jdbc:sqlite:$databaseUrl")
    }

    private fun createGraphListTable() {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS graphs (name TEXT);"
            )
        }
    }

    private fun createGraphLayoutIfNotExists(name: String) {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute("INSERT INTO graphs (name) VALUES (${name});")}
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/graph " +
                        "(scale REAL, offsetX REAL, offsetY REAL, displayWeight INTEGER, graphType TEXT);"
            )
        }
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/vertices" +
                        "(element INTEGER, color INTEGER, posX REAL, posY REAL);"
            )
        }
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/edges (element INTEGER," +
                        " weight INTEGER, color INTEGER, firstVertex INTEGER, secondVertex INTEGER, width REAL);"
            )
        }
    }

    fun clearGraphData(graphName: String) {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                    "DELETE FROM ${graphName}/vertices;" +
                        "DELETE FROM ${graphName}/edges;" +
                        "DELETE FROM ${graphName}/graph;"
            )
        }
    }

    fun createGraphLayout(graphName: String) {
        createGraphLayoutIfNotExists(graphName)
    }

    fun saveGraph(graphName: String, graph: GraphDBFormat) {
        clearGraphData(graphName)
        val vertices = graph.vertices
        val edges = graph.edges

        val scale = graph.scale
        val offsetX = graph.offsetX
        val offsetY = graph.offsetY
        val displayWeight = graph.displayWeight
        val graphType = graph.graphType

        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "INSERT INTO ${graphName}/graph (scale, offsetX, offsetY, displayWeight, graphType) VALUES" +
                        "(${scale}, ${offsetX}, ${offsetY}, ${displayWeight}, ${graphType});"
            )

            if (vertices != null) {
                for (vertex in vertices) {
                    val element = vertex.element
                    val color = vertex.color
                    val x = vertex.posX
                    val y = vertex.posY
                    connection?.createStatement()?.use { statement: Statement ->
                        statement.execute(
                            "INSERT INTO ${graphName}/vertices (element, color, posX, posY) VALUES" +
                                    "(${element}, ${color}, ${x}, ${y});"
                        )
                    }
                }
            }
            if (edges != null) {
                for (edge in edges) {
                    val element = edge.element
                    val weight = edge.weight
                    val color = edge.color
                    val firstVertex = edge.firstVertex
                    val secondVertex = edge.secondVertex
                    val width = edge.width
                    connection?.createStatement()?.use { statement: Statement ->
                        statement.execute(
                            "INSERT INTO ${graphName}/edges (element, weight," +
                                    " color, firstVertex, secondVertex, width) VALUES" +
                                    "(${element}, ${weight}, ${color}, ${firstVertex}, ${secondVertex}, ${width});"
                        )
                    }
                }
            }
        }
    }

    fun loadGraph(graphName: String): GraphDBFormat {
        val graph = GraphDBFormat()
        val statement: Statement? = connection?.createStatement()

        val graphSet = statement?.executeQuery("SELECT * FROM ${graphName}/graph")
        if (graphSet != null) {
            while (graphSet.next()) {
                val scale = graphSet.getFloat("scale")
                val offsetX = graphSet.getFloat("offsetX")
                val offsetY = graphSet.getFloat("offsetY")
                val displayWeight = graphSet.getInt("displayWeight")
                val graphType = graphSet.getString("graphType")

                graph.scale = scale
                graph.offsetX = offsetX
                graph.offsetY = offsetY
                graph.displayWeight = displayWeight
                graph.graphType = graphType
            }
        }

        val edgeSet = statement?.executeQuery("SELECT * FROM ${graphName}/edges")
        if (edgeSet != null) {
            while (edgeSet.next()) {
                val element = edgeSet.getLong("element")
                val weight = edgeSet.getLong("weight")
                val color = edgeSet.getLong("color").toULong()
                val firstVertex = edgeSet.getLong("firstVertex")
                val secondVertex = edgeSet.getLong("secondVertex")
                val width = edgeSet.getFloat("width")
                graph.addEdge(element, weight, color, firstVertex, secondVertex, width)
            }
        }

        val vertexSet = statement?.executeQuery("SELECT * FROM ${graphName}/vertexes")
        if (vertexSet != null) {
            while (vertexSet.next()) {
                val element = vertexSet.getLong("element")
                val color = vertexSet.getLong("color").toULong()
                val posX = vertexSet.getFloat("posX")
                val posY = vertexSet.getFloat("posY")
                graph.addVertex(element, color, posX, posY)
            }
        }
        return graph
    }

    fun graphsList(): List<String> {
        val result = mutableListOf<String>()
        val statement: Statement? = connection?.createStatement()
        val graphsSet = statement?.executeQuery("SELECT * FROM graphs")
        if (graphsSet != null) {
            while (graphsSet.next()) {
                val name = graphsSet.getString("name")
                result.add(name)
            }
        }
        return result
    }
}
