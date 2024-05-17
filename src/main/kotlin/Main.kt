import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import io.sqlite.GraphDatabase
import graphs.primitives.Graph
import graphs.types.WeightedDirectedGraph
import io.reading
import view.MainScreen
import viewmodel.MainScreenViewModel

/*
    DirectedGraph
    UndirectedGraph
    WeightedDirectedGraph
    WeightedUndirectedGraph
*/
val sampleGraph: Graph<String, Long> = WeightedDirectedGraph()

@Composable
@Preview
fun App() {
    sampleGraph.reading("examples/weighted_undirected.csv")
    val database = GraphDatabase("AppStateDB.db")
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
