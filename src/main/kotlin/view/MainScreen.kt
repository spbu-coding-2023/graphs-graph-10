package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.background(Color.White)
    ) {
        Column(modifier = Modifier.width(350.dp)) {
            Text(
                text = "There will be some controls.",
            )
        }

        Surface(
            modifier = Modifier.weight(1f)
                .onSizeChanged {
                viewModel.updateOnResize(it.width, it.height)
            }
        ) {
            GraphView(viewModel.graphViewModel)
        }
    }
}
