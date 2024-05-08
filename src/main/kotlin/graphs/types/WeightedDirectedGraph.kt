package graphs.types

import graphs.primitives.Graph
import graphs.abstracts.WeightedGraph

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


    override fun findcycle(v: V): Graph<V, E> {
        TODO("Not yet implemented")
    }
}