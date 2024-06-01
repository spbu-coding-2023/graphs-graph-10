package graphs.algo


import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class SCCTest {
    @Test
    fun `component count in undirected graph is 1`() {
        val graph = WeightedUndirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addVertex(4)
            addEdge(1, 2, 1)
            addEdge(2, 1, 2)
            addEdge(2, 3, 3)
            addEdge(3, 2, 4)
            addEdge(3, 4, 5)
            addEdge(4, 3, 6)
        }

        val components = findSCC(graph)
        assertEquals(1, components.size)
    }

    @Test
    fun `classic graph with SCC test`() {
        val graph = WeightedDirectedGraph().apply {
            addVertex(1)
            addVertex(2)
            addVertex(3)
            addVertex(4)
            addVertex(5)
            addVertex(6)
            addVertex(7)
            addVertex(8)
            addVertex(9)

            addEdge(1, 4, 1)
            addEdge(4, 7, 2)
            addEdge(7, 1, 3)
            addEdge(9, 7, 4)
            addEdge(9, 3, 5)
            addEdge(3, 6, 6)
            addEdge(6, 9, 7)
            addEdge(8, 6, 8)
            addEdge(2, 8, 9)
            addEdge(5, 2, 10)
            addEdge(8, 5, 11)
        }
        val components = findSCC(graph)
        assertEquals(3, components.size)
        val componentSets = mutableListOf<Set<Long>>()
        for (component in components) {
            val c = mutableSetOf<Long>()
            for (v in component)
                c.add(v.element)
            componentSets.add(c)
        }
        val component1 = mutableSetOf<Long>()
        val component2 = mutableSetOf<Long>()
        val component3 = mutableSetOf<Long>()
        component1.add(1)
        component1.add(4)
        component1.add(7)
        component2.add(9)
        component2.add(6)
        component2.add(3)
        component3.add(8)
        component3.add(2)
        component3.add(5)
        assert(component1 in componentSets)
        assert(component2 in componentSets)
        assert(component3 in componentSets)
    }
}