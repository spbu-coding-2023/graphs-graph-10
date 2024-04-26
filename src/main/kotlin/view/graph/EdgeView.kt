package view.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import viewmodel.graph.EdgeViewModel

@Composable
fun <E, V> EdgeView(
    viewModel: EdgeViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawLine(
            start = Offset(
                viewModel.u.x.toPx() + viewModel.u.radius.toPx(),
                viewModel.u.y.toPx() + viewModel.u.radius.toPx(),
            ),
            end = Offset(
                viewModel.v.x.toPx() + viewModel.v.radius.toPx(),
                viewModel.v.y.toPx() + viewModel.v.radius.toPx(),
            ),
            color = Color.Black
        )
    }
}