package viewmodel.graph

interface RepresentationStrategy {
    fun <V> place(width: Double, height: Double, vertices: Collection<VertexViewModel<V>>)
    fun <V> onResize(width: Double, height: Double, vertices: Collection<VertexViewModel<V>>)
}
