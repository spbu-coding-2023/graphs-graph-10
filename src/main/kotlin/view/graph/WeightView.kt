package view.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import viewmodel.graph.EdgeViewModel


import androidx.compose.material.Text


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun <E, V> WeightView(
    edgeViewModel: EdgeViewModel<E, V>,
    modifier: Modifier = Modifier,
) {
    val weightToDraw = edgeViewModel.e.weight.toString()


    Text(
        modifier = Modifier
            .offset(
                edgeViewModel.u.x + (edgeViewModel.v.x - edgeViewModel.u.x) / 2,
                edgeViewModel.u.y + (edgeViewModel.v.y - edgeViewModel.u.y) / 2
            ),
        text = weightToDraw
    )

}
