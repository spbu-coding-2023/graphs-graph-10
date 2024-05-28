package io.sqlite.connectedEntity


class GraphDBFormat() {
    var scale: Float = 1.0F
    var offsetX: Float = 1.0F
    var offsetY: Float = 1.0F
    var displayWeight: Int = 0
    var graphType: String = "None"

    val vertices = mutableListOf<VertexDBFormat>()
    val edges = mutableListOf<EdgeDBFormat>()


    fun addVertex(element: Long, color: ULong, posX: Float, posY: Float) {
        vertices.add(VertexDBFormat(element, color, posX, posY))
    }
    fun addEdge(element: Long, weight: Long, color: ULong, firstVertex: Long, secondVertex: Long, width: Float) {
        edges.add(EdgeDBFormat(element, weight, color, firstVertex, secondVertex, width))
    }
}