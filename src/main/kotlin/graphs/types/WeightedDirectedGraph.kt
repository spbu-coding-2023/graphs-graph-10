package graphs.types

import graphs.abstracts.WeightedGraph

class WeightedDirectedGraph<V, E>: WeightedGraph<V, E>() {

    fun findBridges(){
        TODO("Not yet implemented")
    }

    override fun MinimalPathFloidBellman(start: V, target: V): WeightedGraph<V, E> {
        TODO("Not yet implemented")
    }

    override fun findCycle(v: V): List<V> {
        TODO("Not yet implemented")
    }
}
