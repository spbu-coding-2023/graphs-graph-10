package io.csv

import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.WeightedDirectedGraph

import java.io.File
import java.io.BufferedReader

fun Graph.reading(fileName: String) {
    val csvFile = File(fileName)
    val reader = BufferedReader(csvFile.reader())
    val edgesIndex = HashMap<Int, String>()

    val lines = reader.readLines()
    val data = lines.map { it.split(';') }
    var countVertex = 0

    for (vertex in data[0]) {
        if (vertex != "") {
            edgesIndex[countVertex] = vertex
            countVertex++
            this.addVertex(vertex.toLong())
        }
    }
    var element: Long = 1

    for (i in 1..countVertex) {
        for (j in i + 1..countVertex) {
            val firstVert = edgesIndex[i - 1]
            val secondVert = edgesIndex[j - 1]

            val isEdge = data[i][j].toLong()
            if (isEdge >= 1 && firstVert != null && secondVert != null) {
                this.addEdge(firstVert.toLong(), secondVert.toLong(), element, isEdge)
                element++
            }
        }
    }

    if (this is WeightedDirectedGraph || this is DirectedGraph) {
        for (i in 1..countVertex) {
            for (j in i + 1..countVertex) {
                val firstVert = edgesIndex[j - 1]
                val secondVert = edgesIndex[i - 1]

                val isEdge = data[j][i].toLong()
                if (firstVert != null)
                    this.addVertex(firstVert.toLong())
                if (secondVert != null)
                    this.addVertex(secondVert.toLong())

                if (isEdge >= 1 && firstVert != null && secondVert != null) {
                    this.addEdge(firstVert.toLong(), secondVert.toLong(), element, isEdge)
                    element++
                }
            }
        }
    }
    println(this.vertices.size)
}
