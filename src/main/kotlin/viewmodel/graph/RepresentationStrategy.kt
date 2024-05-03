package viewmodel.graph

interface RepresentationStrategy {
    fun <V, T> place(width: Double, height: Double, originalGraph: GraphViewModel<V, T>)
    fun <V>    move(old: Pair<Int, Int>, new: Pair<Int, Int>, vertices: Collection<VertexViewModel<V>>)
}
