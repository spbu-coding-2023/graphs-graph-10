package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <V> VertexView(
    vertexViewModel: VertexViewModel<V>,
    modifier: Modifier = Modifier,
    graphViewModel: GraphViewModel<V, *>
) {
    var savedVertexColor by remember{ mutableStateOf(vertexViewModel.color) }

    Box(modifier = modifier
        .size(vertexViewModel.radius * 2, vertexViewModel.radius * 2)
        .offset(vertexViewModel.x, vertexViewModel.y)
        .background(
            color = vertexViewModel.color,
            shape = CircleShape
        )
        .pointerInput(vertexViewModel) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                vertexViewModel.onDrag(dragAmount)
            }
        }
        .pointerInput(Unit) {
            detectTapGestures (
                onTap = {
                    // picking vertex
                    if (!graphViewModel.pickedVertices.contains(vertexViewModel.v.element)) {
                        vertexViewModel.color = Color.Cyan
                        graphViewModel.pickedVertices.add(vertexViewModel.v.element)
                    } else { // unpicking vertex
                        vertexViewModel.color = Color.Gray
                        graphViewModel.pickedVertices.remove(vertexViewModel.v.element)
                    }
                    savedVertexColor = vertexViewModel.color
                },
            )
        }
        .onPointerEvent(PointerEventType.Enter) {
            savedVertexColor = vertexViewModel.color
            vertexViewModel.color = Color.Cyan
        }
        .onPointerEvent(PointerEventType.Exit) {
            vertexViewModel.color = savedVertexColor
        }
    )
}