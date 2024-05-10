package graphs.types

import graphs.primitives.Graph
import graphs.abstracts.WeightedGraph
import graphs.primitives.Vertex

class WeightedDirectedGraph<V, E>: WeightedGraph<V, E>() {

    fun findBridges(){
        TODO("Not yet implemented")
    }


    override fun MinimalPathDeikstra(start: V, target: V): WeightedGraph<V, E> {
        TODO("Not yet implemented")
    }

    override fun MinimalPathFloidBellman(start: V, target: V): WeightedGraph<V, E> {
        TODO("Not yet implemented")
    }

    override fun findCycle(v: V): List<V> {
        TODO("Not yet implemented")
    }
}
