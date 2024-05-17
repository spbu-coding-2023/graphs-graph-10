import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import graphs.algo.Kraskal
import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import io.reading
import view.MainScreen
import viewmodel.MainScreenViewModel

/*
    DirectedGraph
    UndirectedGraph
    WeightedDirectedGraph
    WeightedUndirectedGraph
*/
val sampleGraph: Graph<String, Long> = WeightedUndirectedGraph<String, Long>()
//    .apply {
//        addVertex("A")
//        addVertex("B")
//        addVertex("C")
//        addVertex("D")
//        addVertex("E")
//        addVertex("F")
//        addEdge("A", "B", 1, 7)
//        addEdge("B", "C", 2, 11)
//        addEdge("C", "A", 3, 8)
//        //addEdge("B", "D", 4, 2)
//        //addEdge("C", "D", 5, 6)
//        //addEdge("C", "E", 6, 9)
//        addEdge("D", "F", 7, 9)
//        addEdge("E", "F", 8, 10)
//        addEdge("D", "E", 9, 11)
//    }

@Composable
@Preview
fun App() {
    sampleGraph.reading("examples/weighted_undirected.csv")
    MaterialTheme {
        MainScreen(MainScreenViewModel(sampleGraph))
    }
}

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 1150.dp, height = 700.dp),
        title = "Graphs 10"
    ) {
        App()
    }

}
