package view

import GraphScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import graphs.primitives.Graph
import graphs.types.WeightedUndirectedGraph
import io.csv.reading
import viewmodel.MainScreenViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import graphs.types.DirectedGraph
import graphs.types.UndirectedGraph
import graphs.types.WeightedDirectedGraph
import view.components.CoolButton
import view.components.SmallBtn
import view.components.FileExplorer
import view.utils.GetGraphType
import view.utils.GraphType
import view.utils.SaveToNeo4jDialog

fun createGraph(graphType: GraphType): Graph {
    return when (graphType) {
        GraphType.WEIGHTED -> WeightedUndirectedGraph()
        GraphType.WEIGHTED_DIRECTED -> WeightedDirectedGraph()
        GraphType.UNWEIGHTED_DIRECTED -> DirectedGraph()
        GraphType.UNWEIGHTED_UNDIRECTED -> UndirectedGraph()
    }
}

@Composable
fun Welcome() {
    var currentFileType by remember { mutableStateOf<String?>(null) }
    var graphType by remember { mutableStateOf<GraphType?>(null) }
    var showTypeDialog by remember { mutableStateOf(false) }
    var showFilePicker by remember { mutableStateOf(false) }
    var textData by remember { mutableStateOf("") }
    var graph by remember { mutableStateOf<Graph?>(null) }
    val displayLoadDialog = remember { mutableStateOf(false) }
    val displayGraph = remember { mutableStateOf(false) }
    val navigator = LocalNavigator.currentOrThrow

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Getting started with",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CoolButton(onClick = {
                    currentFileType = "csv"
                    showTypeDialog = true
                }, SmallBtn) {
                    Text("CSV")
                }
                Spacer(Modifier.width(4.dp))
                CoolButton(onClick = {
                    currentFileType = "sqlite"
                }, SmallBtn) {
                    Text("SQLite")
                }
                Spacer(Modifier.width(4.dp))
                CoolButton(
                    onClick = {
                        currentFileType = "neo4j"
                        displayLoadDialog.value = true
                    }, SmallBtn
                ) { Text("Neo4j") }
            }
            if (currentFileType == "csv") {
                if (showTypeDialog) {
                    GetGraphType(onDismissRequest = { selectedGraphType ->
                        showTypeDialog = false
                        selectedGraphType?.let {
                            graphType = it
                            showFilePicker = true
                        }
                    })
                }

                if (showFilePicker) {
                    FileExplorer(fileType = "csv") { selectedFilePath ->
                        graphType?.let {
                            graph = createGraph(it)
                            graph!!.reading(selectedFilePath)
                            // Now graph is available outside the if blocks
                        }
                    }
                    showFilePicker = false
                }
            }
        }

        if (displayLoadDialog.value) {
            val tempGraph: Graph = DirectedGraph()
            val mainViewModel = MainScreenViewModel(tempGraph)
            SaveToNeo4jDialog(onDismissRequest = {
                displayLoadDialog.value = false
            }, "load", mainViewModel, {
                navigator.push(GraphScreen(mainViewModel))
            })
        }

        if (graph != null && currentFileType == "csv") {
            val mainScreenViewModel = MainScreenViewModel(graph!!)
            mainScreenViewModel.runLayout = true
            navigator.push(GraphScreen(mainScreenViewModel))
        }
    }
}
