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
    }

    private fun connect() {
        connection = DriverManager.getConnection("jdbc:sqlite:$databaseUrl")
    }

    private fun createGraphLayoutIfNotExists(name: String) {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/vertices (element INTEGER," +
                        " color INTEGER, posX REAL, posY REAL);"
            )
        }
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/edges (element INTEGER," +
                        " weight INTEGER, color INTEGER, firstVertex INTEGER, secondVertex INTEGER);"
            )
        }
    }

    private fun clearGraphData(graphName: String) {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "DELETE FROM ${graphName}/vertices;" +
                        "DELETE FROM ${graphName}/edges;"
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
                connection?.createStatement()?.use { statement: Statement ->
                    statement.execute(
                        "INSERT INTO ${graphName}/edges (element, weight, color, firstVertex, secondVertex) VALUES" +
                                "(${element}, ${weight}, ${color}, ${firstVertex}, ${secondVertex});"
                    )
                }
            }
        }
    }

    fun loadGraph(graphName: String): GraphDBFormat {
        val vertexes: Collection<VertexViewModel>;
        val edges: Collection<EdgeViewModel>;
        val graph = GraphDBFormat()
        val statement: Statement? = connection?.createStatement()
        val edgeSet = statement?.executeQuery("SELECT * FROM ${graphName}/edges")
        if (edgeSet != null) {
            while (edgeSet.next()) {
                val element = edgeSet.getLong("element")
                val weight = edgeSet.getLong("weight")
                val color = edgeSet.getLong("color").toULong()
                val firstVertex = edgeSet.getLong("firstVertex")
                val secondVertex = edgeSet.getLong("secondVertex")
                graph.addEdge(element, weight, color, firstVertex, secondVertex)
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
}
