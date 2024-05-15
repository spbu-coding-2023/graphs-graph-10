package view.graph.edge

import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import viewmodel.graph.EdgeViewModel


import androidx.compose.material.Text


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
