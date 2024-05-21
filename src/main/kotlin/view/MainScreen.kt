package view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.DpOffset
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import view.algo.*
import view.graph.GraphView
import viewmodel.MainScreenViewModel
import kotlin.math.exp
import kotlin.math.sign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <V, E> MainScreen(mainViewModel: MainScreenViewModel<V, E>) {
    var resolution = Pair(0, 0)
    val displayGraph = remember { mutableStateOf(false) }

    var scale by remember { mutableStateOf(1f) }
    val state = rememberTransformableState { zoomChange, _, _ ->
        scale *= zoomChange
    }

    fun scaleBox(delta: Int) {
        scale = (scale * exp(delta * 0.1f)).coerceIn(0.05f, 4.0f)
    }

    var offset by mainViewModel.offset
    var textData by remember{ mutableStateOf("") }
    val displayWeight = mainViewModel.displayWeight

    val displaySaveDialog = remember { mutableStateOf(false) }
    val displayLoadDialog = remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .background(Color(0xfa, 0xfa, 0xfa))
    ) {
        Column(
            modifier = Modifier
                .width(350.dp)
                .padding(7.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = {
                    mainViewModel.restoreGraphState()
                    mainViewModel.runLayoutAlgorithm(resolution)
                    displayGraph.value = true
                    textData = ""
                }
            ) { Text("Reload visualization") }
            Button(
                onClick = {
                    textData = drawCycleOnGraph(mainViewModel.graphViewModel)
                }
            ) { Text("Check cycles for vertex") }
            Button(
                onClick = {
                    textData = drawPathOnGraph(mainViewModel.graphViewModel)
                }
            ) { Text("Find path with Dijkstra") }
            Button(
                onClick = {
                    textData = drawMst(mainViewModel.graphViewModel)
                }
            ) { Text("Find Minimal spanning tree with Prim") }
            Text(textData)
            if (mainViewModel.graph is WeightedUndirectedGraph) {
                Button(
                    onClick = {
                        drawMST(mainViewModel.graphViewModel)
                    }
                ) { Text("Find Minimal spanning tree with Kraskal") }
            }
            Button(
                onClick = {
                    drawCommunities(mainViewModel.graphViewModel)
                }
            ) { Text("Find Communities") }
            if (mainViewModel.graph is WeightedDirectedGraph ||
                mainViewModel.graph is WeightedUndirectedGraph
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Display weights:")
                    Checkbox(
                        checked = displayWeight.value,
                        onCheckedChange = {
                            displayWeight.value = it
                        }
                    )
                }
            }

            if (displaySaveDialog.value) {
                SaveToNeo4jDialog(onDismissRequest = {
                    displaySaveDialog.value = false
                }, "save", mainViewModel)
            }
            if (displayLoadDialog.value) {
                SaveToNeo4jDialog(onDismissRequest = {
                    displayLoadDialog.value = false
                }, "load", mainViewModel)
                displayGraph.value = true
            }

            Row {
                Button(
                    onClick = {
                        displaySaveDialog.value = true
                    }
                ) { Text("Save to Neo4j") }
                Button(
                    onClick = {
                        displayLoadDialog.value = true
                    }
                ) { Text("Load from Neo4j") }
            }

        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .onSizeChanged {
                    mainViewModel.updateOnResize(resolution, Pair(it.width, it.height))
                    resolution = Pair(it.width, it.height)
                }
                .onPointerEvent(PointerEventType.Scroll) {
                    val change = it.changes.first()
                    val delta = change.scrollDelta.y.toInt().sign
                    scaleBox(delta)
                }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offset += DpOffset(
                            (dragAmount.x * (1 / scale)).toDp(),
                            (dragAmount.y * (1 / scale)).toDp()
                        )
                    }
                }
        ) {
            GraphView(
                mainViewModel.graphViewModel,
                displayGraph,
                displayWeight,
                scale,
                offset
            )
        }
    }
}
