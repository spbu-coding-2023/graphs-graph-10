package database


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
                "CREATE TABLE IF NOT EXISTS ${name}/vertexes (element TEXT," +
                        " color INTEGER, posX REAL, posY REAL);"
            )
        }
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "CREATE TABLE IF NOT EXISTS ${name}/edges (element INTEGER," +
                        " weight INTEGER, color INTEGER, firstVertex TEXT, secondVertex TEXT);"
            )
        }
    }

    private fun clearGraphData(graphName: String) {
        connection?.createStatement()?.use { statement: Statement ->
            statement.execute(
                "DELETE FROM ${graphName}/vertexes;" +
                        "DELETE FROM ${graphName}/edges;"
            )
        }
    }

    fun createGraphLayout(graphName: String) {
        createGraphLayoutIfNotExists(graphName)
    }

    fun saveGraph(
        graphName: String,
        vertexes: Collection<VertexViewModel<String>>,
        edges: Collection<EdgeViewModel<Long, String>>
    ) {
        clearGraphData(graphName)
        for (vertex in vertexes) {
            val element = vertex.v.element
            val color = vertex.color.value
            val x = vertex.x.value
            val y = vertex.y.value
            connection?.createStatement()?.use { statement: Statement ->
                statement.execute(
                    "INSERT INTO ${graphName}/vertexes (element, color, posX, posY) VALUES" +
                            "(${element}, ${color}, ${x}, ${y});"
                )
            }
        }
        for (edge in edges) {
            val element = edge.e.element
            val weight = edge.e.weight
            val color = edge.color.value
            val firstVertex = edge.u.v.element
            val secondVertex = edge.v.v.element
            connection?.createStatement()?.use { statement: Statement ->
                statement.execute(
                    "INSERT INTO ${graphName}/vertexes (element, weight, color, firstVertex, secondVertex) VALUES" +
                            "(${element}, ${weight}, ${color}, ${firstVertex}, ${secondVertex});"
                )
            }
        }
    }

    fun loadGraph(graphName: String) {
        val vertexes: Collection<VertexViewModel<String>>;
        val edges: Collection<EdgeViewModel<Long, String>>;
        val statement: Statement? = connection?.createStatement()
        val resultSet = statement?.executeQuery("SELECT * FROM ${graphName}/edges")
        if (resultSet != null) {
            while (resultSet.next()) {
                val element = resultSet.getInt("element")
                val weight = resultSet.getInt("weight")
                val color = resultSet.getInt("color")
                val firstVertex = resultSet.getString("firstVertex")
                val secondVertex = resultSet.getString("secondVertex")
            }
        }
    }
}
