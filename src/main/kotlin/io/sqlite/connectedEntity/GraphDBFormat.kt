package io.sqlite.connectedEntity


class GraphDBFormat() {
    val vertices: MutableList<VertexDBFormat>? = null
    val edges: MutableList<EdgeDBFormat>? = null

    fun addVertex(element: Long, color: ULong, posX: Float, posY: Float) {
        vertices?.add(VertexDBFormat(element, color, posX, posY))
    }
    fun addEdge(element: Long, weight: Long, color: ULong, firstVertex: Long, secondVertex: Long) {
        edges?.add(EdgeDBFormat(element, weight, color, firstVertex, secondVertex))
    }
}