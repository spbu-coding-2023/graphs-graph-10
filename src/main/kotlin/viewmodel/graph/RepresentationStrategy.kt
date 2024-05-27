package viewmodel.graph

interface RepresentationStrategy {
    fun place(width: Double, height: Double, originalGraph: GraphViewModel)
    fun move(old: Pair<Int, Int>, new: Pair<Int, Int>, vertices: Collection<VertexViewModel>)
}
