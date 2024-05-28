package view.graph.edge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import viewmodel.graph.EdgeViewModel

@Composable
fun EdgeView(
    edgeViewModel: EdgeViewModel,
    displayWeight: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawLine(
            start = Offset(
                edgeViewModel.u.x.toPx() + edgeViewModel.u.radius.toPx(),
                edgeViewModel.u.y.toPx() + edgeViewModel.u.radius.toPx(),
            ),
            end = Offset(
                edgeViewModel.v.x.toPx() + edgeViewModel.v.radius.toPx(),
                edgeViewModel.v.y.toPx() + edgeViewModel.v.radius.toPx(),
            ),
            color = edgeViewModel.color,
            strokeWidth = edgeViewModel.width
        )
    }

    if (displayWeight.value)
        WeightView(edgeViewModel, Modifier)
}
