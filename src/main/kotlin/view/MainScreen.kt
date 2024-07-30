package view

import WelcomeScreen
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
import view.graph.graphView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import graphs.types.UndirectedGraph
import androidx.compose.ui.text.font.FontWeight
import io.json.writeInJsonGraph
import view.components.*
import view.utils.saveToNeo4jDialog
import view.utils.saveToSQLite
import viewmodel.MainScreenViewModel
import viewmodel.animateDpOffsetAsState
import kotlin.math.exp
import kotlin.math.sign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun mainScreen(mainViewModel: MainScreenViewModel) {
    var resolution by remember { mutableStateOf(Pair(0, 0)) }
    val displayGraph = remember { mutableStateOf(false) }
    var scale by mainViewModel.scale
    val animateScale by animateFloatAsState(scale, tween(300, 0, LinearOutSlowInEasing))
    fun scaleBox(delta: Int) {
        scale = (scale * exp(delta * 0.1f)).coerceIn(0.05f, 4.0f)
    }

    var offset by mainViewModel.offset
    val animateOffset by animateDpOffsetAsState(offset, tween(300, 0, LinearOutSlowInEasing))
    var textData by remember { mutableStateOf("Graph loaded successfully") }
    val displayWeight = mainViewModel.displayWeight
    val displaySaveDialog = remember { mutableStateOf(false) }
    val displaySaveDialogSQLite = remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(true) }
    val backToWelcome = remember { mutableStateOf(false) }
    val leaderRankDialog = remember { mutableStateOf(false) }
    var n by remember { mutableStateOf<Int?>(null) }
    var gap by remember { mutableStateOf<Double?>(null) }
    var leaderRankStart by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow
    val displayKey = mainViewModel.displayKey
    if (displayGraph.value && mainViewModel.runLayout) {
        mainViewModel.runLayoutAlgorithm(resolution)
        mainViewModel.runLayout = false
    }

    if (textData == "")
        textData = "Graph loaded successfully"

    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .background(Color(0xfa, 0xfa, 0xfa))
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = isExpanded)
        {
            Column(
                modifier = Modifier
                    .width(230.dp)
                    .padding(7.dp),

                verticalArrangement = Arrangement.Top
            ) {
                Row {
                    coolButton(
                        onClick = {
                            mainViewModel.restoreGraphState()
                            mainViewModel.runLayoutAlgorithm(resolution)
                            displayGraph.value = true
                            textData = ""
                        }, LargeBtn
                    ) { Text("Reload view") }
                    Spacer(modifier = Modifier.width(20.dp))
                    coolButton(
                        onClick = {
                            backToWelcome.value = true
                        }, LargeBtn
                    ) { Text("Back to menu") }
                }
                Spacer(modifier = Modifier.height(10.dp))
                coolButton(
                    onClick = {
                        leaderRankDialog.value = true
                    }, BigBtn
                ) { Text("Find key vertices") }

                Spacer(modifier = Modifier.height(10.dp))

                coolButton(
                    onClick = {
                        drawTarjan(mainViewModel.graphViewModel)
                    }, BigBtn
                ) { Text("Articulated vertices") }

                Spacer(modifier = Modifier.height(10.dp))


                if (mainViewModel.graph is UndirectedGraph ||
                    mainViewModel.graph is WeightedUndirectedGraph
                ) {
                    Column {
                        coolButton(
                            onClick = {
                                scope.drawCycles(mainViewModel.graphViewModel) { result ->
                                    textData = result
                                }
                            }, BigBtn
                        ) { Text("Cycles") }
                        Spacer(modifier = Modifier.height(10.dp))
                        coolButton(
                            onClick = {
                                drawCommunities(mainViewModel.graphViewModel)
                            }, BigBtn
                        ) { Text("Communities") }

                    }
                } else {
                    Row {
                        coolButton(
                            onClick = {
                                scope.drawCycles(mainViewModel.graphViewModel) { result ->
                                    textData = result
                                }
                            }, SmallBtn
                        ) { Text("Cycles") }
                        Spacer(modifier = Modifier.width(20.dp))
                        coolButton(
                            onClick = {
                                drawSCC(mainViewModel.graphViewModel)
                            }, SmallBtn
                        ) { Text("SCC") }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                coolButton(
                    onClick = {
                        drawFindBridge(mainViewModel.graphViewModel)
                    }, BigBtn
                ) { Text("Find Bridge") }
                Spacer(modifier = Modifier.height(10.dp))
                if (mainViewModel.graph is WeightedUndirectedGraph) {
                    Text(
                        text = "Draw MST",
                        fontWeight = FontWeight.Bold
                    )
                    Box {
                        Row {
                            coolButton(
                                onClick = {
                                    drawMst(mainViewModel.graphViewModel)
                                }, SmallBtn
                            ) { Text("Prim") }
                            Spacer(modifier = Modifier.width(20.dp))
                            coolButton(
                                onClick = {
                                    drawKruskalMST(mainViewModel.graphViewModel)
                                }, SmallBtn
                            ) { Text("Kruskal") }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (mainViewModel.graph is WeightedUndirectedGraph ||
                    mainViewModel.graph is WeightedDirectedGraph
                ) {
                    Text(
                        text = "Find minimal Path",
                        fontWeight = FontWeight.Bold
                    )
                    Column {
                        coolButton(
                            onClick = {
                                scope.drawDijkstra(mainViewModel.graphViewModel, "Dijkstra") { result ->
                                    textData = result
                                }
                            }, BigBtn
                        ) { Text("Dijkstra") }
                        Spacer(modifier = Modifier.height(10.dp))
                        coolButton(
                            onClick = {
                                scope.drawDijkstra(mainViewModel.graphViewModel, "Astar") { result ->
                                    textData = result
                                }
                            }, BigBtn
                        ) { Text("AStar") }
                        Spacer(modifier = Modifier.height(10.dp))
                        coolButton(
                            onClick = {
                                scope.drawFordBellman(mainViewModel.graphViewModel) { result ->
                                    textData = result
                                }
                            }, BigBtn

                        ) { Text("Ford-Bellman") }
                    }
                }


                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Text("Display keys:")
                    Checkbox(
                        checked = displayKey.value,
                        onCheckedChange = {
                            displayKey.value = it
                        }
                    )
                }
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
                if (leaderRankDialog.value) {
                    leaderRankDisplay(
                        onDismissRequest = { leaderRankDialog.value = false },
                        onResult = { amountOfKeyVertices, gapToCheck, startAlgo ->
                            n = amountOfKeyVertices
                            gap = gapToCheck
                            leaderRankStart = startAlgo
                        },
                        mainViewModel.graph
                    )


                }
                if ((n != null || gap != null) && leaderRankStart) {

                    leaderRankView(mainViewModel.graphViewModel, n, gap)
                    leaderRankStart = false
                }
                if (displaySaveDialog.value) {
                    saveToNeo4jDialog(onDismissRequest = {
                        displaySaveDialog.value = false
                    }, "save", mainViewModel)
                }
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.weight(1f)
                ) {
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            informationBox(textData)
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column {
                                Row {
                                    coolButton(
                                        onClick = {
                                            displaySaveDialog.value = true
                                        }, SmallBtn
                                    ) {
                                        Text("Neo4j")
                                    }

                                    Spacer(modifier = Modifier.width(20.dp))

                                    coolButton(
                                        onClick = {
                                            displaySaveDialogSQLite.value = true
                                        }, SmallBtn

                                    ) {
                                        Text("SQLite")
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                coolButton(
                                    onClick = {
                                        writeInJsonGraph(mainViewModel)
                                    }, BigBtn

                                ) {
                                    Text("JSON")
                                }
                            }
                        }
                    }
                }
            }
            if (backToWelcome.value) {
                navigator.push(WelcomeScreen)
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
                    displayGraph.value = true
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
            graphView(
                mainViewModel.graphViewModel,
                displayGraph,
                displayWeight,
                displayKey,
                animateScale,
                animateOffset
            )
        }

        if (displaySaveDialogSQLite.value) {
            saveToSQLite(onDismissRequest = {
                displaySaveDialogSQLite.value = false
            }, mainViewModel)
        }
    }
}
