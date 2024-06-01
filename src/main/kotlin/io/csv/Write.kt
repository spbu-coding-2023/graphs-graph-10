package io.csv

import graphs.primitives.Graph
import java.io.File
import java.io.PrintWriter

fun Graph.writing(fileName: String) {
    val writer = PrintWriter(File(fileName))
    val countVertex = this.vertices.size
    val table = Array(countVertex) { ByteArray(countVertex) }
    val edgesIndex = HashMap<Int, String>()

    this.vertices.forEach {
        val index = this.vertices.indexOf(it)
        edgesIndex[index] = it.element.toString()
    }
    this.edges.forEach {
        val first = it.vertices.first.element
        val second = it.vertices.second.element

        val indexFirst = this.vertices.indexOf(it.vertices.first)
        val indexSecond = this.vertices.indexOf(it.vertices.second)

        edgesIndex[indexFirst] = first.toString()
        edgesIndex[indexSecond] = second.toString()

        table[indexFirst][indexSecond] = 1
        table[indexSecond][indexFirst] = 1
    }

    writer.print(";;")

    this.vertices.forEach {
        if (it == this.vertices.last()) {
            writer.print(it.element.toString())
        } else writer.print(it.element.toString() + ';')
    }

    writer.println()

    for (i in 0 until countVertex) {
        writer.print("${edgesIndex[i]};")

        for (j in 0 until countVertex) {
            if (j + 1 == countVertex)
                writer.print("${table[i][j]}")
            else writer.print("${table[i][j]};")
        }

        writer.println()
    }

    writer.close()
}
