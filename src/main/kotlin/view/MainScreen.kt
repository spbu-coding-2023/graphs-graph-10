package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import view.graph.GraphView
import viewmodel.MainScreenViewModel

@Composable
fun <V, E> MainScreen(viewModel: MainScreenViewModel<V, E>) {
    var resolution = Pair(0, 0)
    val displayGraph = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.background(Color.White)
    ) {
        Column(modifier = Modifier.width(350.dp).padding(7.dp)) {
            Button(
                onClick = {
                    viewModel.runLayoutAlgorithm(resolution)
                    displayGraph.value = true
                }
            ) {
                Text("Reload visualization")
            }
        }

        Surface(
            modifier = Modifier.weight(1f)
                .onSizeChanged {
                    viewModel.updateOnResize(resolution, Pair(it.width, it.height))
                    resolution = Pair(it.width, it.height)
                }
        ) {
            GraphView(viewModel.graphViewModel, displayGraph)
        }
    }
}
