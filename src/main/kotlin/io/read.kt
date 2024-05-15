package io

import graphs.primitives.Graph

import java.io.File
import java.io.BufferedReader

fun Graph<String, Long>.reading(fileName: String){
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
        }
    }

    var element: Long = 1

    for (i in 1..countVertex) {
        for (j in i + 1..countVertex) {
            val firstVert = edgesIndex[i - 1]
            val secondVert = edgesIndex[j - 1]

            val isEdge = data[i][j].toLong()
            if (firstVert != null)
                this.addVertex(firstVert)
            if (secondVert != null)
                this.addVertex(secondVert)

            if (isEdge >= 1 && firstVert != null && secondVert != null) {
                this.addEdge(firstVert, secondVert, element, isEdge)
                element++
            }
        }
    }
}