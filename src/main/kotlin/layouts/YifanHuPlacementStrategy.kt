package layouts

import androidx.compose.ui.unit.dp

import org.gephi.graph.api.Edge
import org.gephi.graph.api.GraphController
import org.gephi.graph.api.Node
import org.gephi.layout.plugin.force.StepDisplacement
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout
import org.gephi.project.api.ProjectController
import org.openide.util.Lookup

import viewmodel.graph.GraphViewModel
import viewmodel.graph.RepresentationStrategy
import viewmodel.graph.VertexViewModel

import java.util.Random

class YifanHuPlacementStrategy : RepresentationStrategy {
    override fun place(width: Double, height: Double, originalGraph: GraphViewModel) {
        val random = Random(1L)

        val pc = Lookup.getDefault().lookup(ProjectController::class.java)
        pc.newProject()

        val graphModel = Lookup.getDefault().lookup(GraphController::class.java).graphModel
        val graph = graphModel.undirectedGraph

        val map = mutableMapOf<Long, Node>()
        for (vert in originalGraph.vertices) {
            val n: Node = graphModel.factory().newNode(vert.v.element.toString())
            n.setX(random.nextFloat()*10)
            n.setY(random.nextFloat()*10)
            map[vert.v.element] = n

            graph.addNode(n)
        }

        for (edge in originalGraph.edges) {
            val e: Edge = graphModel.factory().newEdge(
                map[edge.v.v.element],
                map[edge.u.v.element],
                1,
                false
            )
            graph.addEdge(e)
        }

        // Run YifanHuLayout for 100 passes
        val layout = YifanHuLayout(null, StepDisplacement(1f))
        layout.setGraphModel(graphModel)
        layout.initAlgo()
        layout.resetPropertiesValues()
        layout.optimalDistance = 100f
        layout.relativeStrength = 0.2f
        layout.initialStep = 20.0f
        layout.stepRatio = 0.95f
        layout.isAdaptiveCooling = true

        var i = 0
        while (i < 1000 && layout.canAlgo()) {
            layout.goAlgo()
            i++
        }
        layout.endAlgo()

        for (vertex in originalGraph.vertices) {
            val n: Node = graph.getNode(vertex.v.element.toString())
            vertex.x = ((width/2 + n.x()* 3 / 1)).dp
            vertex.y = ((height/2 + n.y()* 3 / 1)).dp
        }
    }

    override fun move(old: Pair<Int, Int>, new: Pair<Int, Int>, vertices: Collection<VertexViewModel>) {
        if (old.first == 0 || old.second == 0) return

        val xOffset: Float = new.first.toFloat() / 2 - old.first.toFloat() / 2
        val yOffset: Float = new.second.toFloat() / 2 - old.second.toFloat() / 2

        for (v in vertices) {
            v.x += xOffset.dp
            v.y += yOffset.dp
        }
    }
}
