package view

import WelcomeScreen
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import graphs.types.WeightedDirectedGraph
import graphs.types.WeightedUndirectedGraph
import view.algo.*
import view.graph.GraphView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import graphs.algo.LeaderRank

import viewmodel.MainScreenViewModel
import kotlin.math.exp
import kotlin.math.sign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <V, E> MainScreen(mainViewModel: MainScreenViewModel<V, E>) {
    var resolution = Pair(800, 700)
    val displayGraph = remember { mutableStateOf(true) }

    var scale by mainViewModel.scale
    fun scaleBox(delta: Int) {
        scale = (scale * exp(delta * 0.1f)).coerceIn(0.05f, 4.0f)
    }

    var vertindex: Int = 0
    var offset by mainViewModel.offset
    var textData by remember { mutableStateOf("") }
    val displayWeight = mainViewModel.displayWeight
    val displaySaveDialog = remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(true) }
    val backToWelcome = remember { mutableStateOf(false) }
    var LeaderRankDialog = remember { mutableStateOf(false) }
    var n by remember { mutableStateOf<Int?>(null) }
    var gap by remember { mutableStateOf<Double?>(null) }
    var leaderRankStart by remember { mutableStateOf(false) }

    val navigator = LocalNavigator.currentOrThrow
    if (mainViewModel.runLayout) {
        println(resolution)
        mainViewModel.runLayoutAlgorithm(resolution)
    }

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .background(Color(0xfa, 0xfa, 0xfa))
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = isExpanded) {
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

                        LeaderRankDialog.value = true

                    }
                ) { Text("Find key vertices with LeaderRank") }
                Button(
                    onClick = {
                        textData = drawPathOnGraph(mainViewModel.graphViewModel)

                    }
                ) { Text("Find path with Dijkstra") }
                if (mainViewModel.graph is WeightedUndirectedGraph) {
                    Button(
                        onClick = {
                            drawMst(mainViewModel.graphViewModel)
                        }
                    ) { Text("Find Minimal spanning tree with Prim") }
                    Button(
                        onClick = {
                            drawKruskalMST(mainViewModel.graphViewModel)
                        }
                    ) { Text("Find Minimal spanning tree with Kruskal") }
                }
                Button(
                    onClick = {

                        drawCommunities(mainViewModel.graphViewModel)

                    }
                ) { Text("Find Communities") }

                Button(
                    onClick = {
                        drawFindBridge(mainViewModel.graphViewModel)
                    }
                ) { Text("Find Bridge") }

                Button(
                    onClick = {
                        drawFordBellman(mainViewModel.graphViewModel)
                    }
                ) { Text("Find path with Ford-Bellman") }

                Button(
                    onClick = {
                        drawTarjan(mainViewModel.graphViewModel)
                    }
                ) { Text("Find articulated vertices Tarjan") }
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
                if (LeaderRankDialog.value) {

                    LeaderRankDisplay(
                        onDismissRequest = { LeaderRankDialog.value = false },
                        onResult = { amountOfKeyVertices, gapToCheck, StartAlgo ->
                            n = amountOfKeyVertices
                            gap = gapToCheck
                            leaderRankStart = StartAlgo
                        },
                        mainViewModel.graph
                    )


                }
                if ((n != null || gap != null) && leaderRankStart) {

                    LeaderRankView(mainViewModel.graphViewModel, n, gap)
                    leaderRankStart = false
                }
                if (displaySaveDialog.value) {
                    SaveToNeo4jDialog(onDismissRequest = {
                        displaySaveDialog.value = false
                    }, "save", mainViewModel)
                }
                Button(
                    onClick = {
                        displaySaveDialog.value = true
                    }
                ) { Text("Save to Neo4j") }
                Button(
                    onClick = {
                        backToWelcome.value = true
                    }
                ) { Text("New Graph") }
                if (backToWelcome.value) {
                    navigator.push(WelcomeScreen)
                }
            }
        }
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .width(24.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    isExpanded = !isExpanded
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(if (isExpanded) "<" else ">", color = Color.White)
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