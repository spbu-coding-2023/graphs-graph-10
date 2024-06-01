package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import viewmodel.graph.GraphViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import viewmodel.graph.VertexViewModel

@Composable
fun vertexView(
    vertexViewModel: VertexViewModel,
    modifier: Modifier = Modifier,
    displayKey: MutableState<Boolean>,
    graphViewModel: GraphViewModel,
) {
    var savedVertexColor by remember { mutableStateOf(vertexViewModel.color) }
    var borderColor by remember { mutableStateOf(Color.Gray) }

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    if (isHovered) {
        borderColor = Color.Red
    } else {
        borderColor = Color.Gray
    }

    Box(modifier = modifier
        .size(vertexViewModel.radius * 2, vertexViewModel.radius * 2)
        .offset(vertexViewModel.x, vertexViewModel.y)
        .background(
            color = vertexViewModel.color,
            shape = CircleShape
        )
        .border(5.dp, borderColor, CircleShape)
        .pointerInput(vertexViewModel) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                vertexViewModel.onDrag(dragAmount)
            }
        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    // picking vertex
                    if (!graphViewModel.pickedVertices.contains(vertexViewModel.v.element)) {
                        vertexViewModel.color = Color.Cyan
                        graphViewModel.pickedVertices.add(vertexViewModel.v.element)
                    } else { // unpicking vertex
                        vertexViewModel.color = Color.Gray
                        graphViewModel.pickedVertices.remove(vertexViewModel.v.element)
                    }
                    println("Count of picked vertices: ${graphViewModel.pickedVertices.size}")
                    savedVertexColor = vertexViewModel.color
                },
            )
        }
        .hoverable(interactionSource = interactionSource)
    ) {
        if (displayKey.value) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(0.dp, 0.dp),
                text = vertexViewModel.v.element.toString(),
            )
        }
    }
}