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
import io.json.readFromJsonGraph
import view.components.coolButton
import view.components.SmallBtn
import view.components.fileExplorer
import view.utils.getGraphType
import view.utils.GraphType
import view.utils.saveToNeo4jDialog
import view.utils.loadFromSQLite

fun createGraph(graphType: GraphType): Graph {
    return when (graphType) {
        GraphType.WEIGHTED -> WeightedUndirectedGraph()
        GraphType.WEIGHTED_DIRECTED -> WeightedDirectedGraph()
        GraphType.UNWEIGHTED_DIRECTED -> DirectedGraph()
        GraphType.UNWEIGHTED_UNDIRECTED -> UndirectedGraph()
    }
}

@Composable
fun welcome() {
    var currentFileType by remember { mutableStateOf<String?>(null) }
    var graphType by remember { mutableStateOf<GraphType?>(null) }
    var showTypeDialog by remember { mutableStateOf(false) }
    var showFilePicker by remember { mutableStateOf(false) }
    var graph by remember { mutableStateOf<Graph?>(null) }
    val displayLoadDialog = remember { mutableStateOf(false) }
    val displaySaveDialogSQLite = remember { mutableStateOf(false) }
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
                coolButton(onClick = {
                    currentFileType = "csv"
                    showTypeDialog = true
                }, SmallBtn) {
                    Text("CSV")
                }
                Spacer(Modifier.width(4.dp))
                coolButton(onClick = {
                    currentFileType = "sqlite"
                    displaySaveDialogSQLite.value = true
                }, SmallBtn) {
                    Text("SQLite")
                }
                Spacer(Modifier.width(4.dp))
                coolButton(
                    onClick = {
                        currentFileType = "neo4j"
                        displayLoadDialog.value = true
                    }, SmallBtn
                ) { Text("Neo4j") }
                Spacer(Modifier.width(4.dp))
                coolButton(
                    onClick = {
                        currentFileType = "JSON"

                    }, SmallBtn
                ) { Text("JSON") }
            }
        }
        if (currentFileType == "csv") {
            if (showTypeDialog) {
                getGraphType(onDismissRequest = { selectedGraphType ->
                    showTypeDialog = false
                    selectedGraphType?.let {
                        graphType = it
                        showFilePicker = true
                    }
                })
            }
            if (showFilePicker) {
                fileExplorer(fileType = "csv") { selectedFilePath ->
                    graphType?.let {
                        graph = createGraph(it)
                        graph!!.reading(selectedFilePath)
                    }
                }
                showFilePicker = false
            }
        }
        if (currentFileType == "JSON") {
            showFilePicker = true
            currentFileType = ""
            val tempGraph: Graph = DirectedGraph()
            val mainViewModel = MainScreenViewModel(tempGraph)
            if (showFilePicker) {
                fileExplorer(fileType = "json") { selectedFilePath ->
                    val updatedMainViewModel = readFromJsonGraph(mainViewModel, selectedFilePath)
                    navigator.push(GraphScreen(updatedMainViewModel))
                }
                showFilePicker = false
            }
        }

        if (displayLoadDialog.value && currentFileType == "neo4j") {
            val tempGraph: Graph = DirectedGraph()
            val mainViewModel = MainScreenViewModel(tempGraph)
            saveToNeo4jDialog(onDismissRequest = {
                displayLoadDialog.value = false
            }, "load", mainViewModel, {
                navigator.push(GraphScreen(mainViewModel))
            })
        }

        if (displaySaveDialogSQLite.value && currentFileType == "sqlite") {
            val tempGraph: Graph = DirectedGraph()
            val mainViewModel = MainScreenViewModel(tempGraph)
            loadFromSQLite(onDismissRequest = {
                displaySaveDialogSQLite.value = false
            }, mainViewModel, {
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
