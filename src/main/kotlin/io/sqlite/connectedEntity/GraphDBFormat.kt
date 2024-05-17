package io.sqlite.connectedEntity


class GraphDBFormat<V, E>() {
    val vertexes: MutableList<VertexDBFormat<V>>? = null
    val edges: MutableList<EdgeDBFormat<E, V>>? = null

    fun addVertex(element: V, color: ULong, posX: Float, posY: Float) {
        vertexes?.add(VertexDBFormat(element, color, posX, posY))
    }
    fun addEdge(element: E, weight: E, color: ULong, firstVertex: V, secondVertex: V) {
        edges?.add(EdgeDBFormat(element, weight, color, firstVertex, secondVertex))
    }
}