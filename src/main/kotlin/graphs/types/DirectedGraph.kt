package graphs.types

import graphs.abstracts.UnweightedGraph
import graphs.primitives.Graph

class DirectedGraph<V, E> : UnweightedGraph<V, E>() {
    override fun findcycle(v: V): Graph<V, E> {
        TODO("Not yet implemented")
    }
    fun findBridges(){
        TODO("Not yet implemented")
    }


}