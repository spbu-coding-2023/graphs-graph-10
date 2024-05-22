package graphs.algo

import graphs.primitives.Graph

fun <V, E> findWeightPath(graph: Graph<V, E>, path : List<V>) : Long? {
    val n = path.size
    if (n < 1) {
        println("There is no such way")
        return null
    }
    var resultSum: Long = 0
    for (i in 0 until n - 1) {
        val result = findWeightEdge(graph, path[i], path[i + 1])
        if (result != null)
            resultSum += result
        else {
            println("There is no such way")
            return null
        }
    }
    return resultSum
}