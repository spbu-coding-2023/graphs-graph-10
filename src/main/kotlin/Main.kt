import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import model.graph.Graph
import model.graph.UndirectedGraph
import view.MainScreen
import viewmodel.MainScreenViewModel

val sampleGraph: Graph<String, Long> = UndirectedGraph()

@Composable
@Preview
fun App() {
    sampleGraph.reading("examples/airports.csv")
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
