package view.algo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import graphs.algo.LeaderRank
import graphs.primitives.Graph
import io.neo4j.Neo4jRepository
import viewmodel.MainScreenViewModel
import viewmodel.graph.GraphViewModel

@Composable
fun <V, E> LeaderRankDisplay(
    onDismissRequest: () -> Unit,
    onResult: (Int?, Double?, Boolean) -> Unit,
    graph: Graph<V, E>,
) {
    var textAmountOfKeyVertices by remember { mutableStateOf("") }
    var textGapToCheck by remember { mutableStateOf("") }
    var LeaderRankstart by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Your graph contains ${graph.vertices.size} vertices",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 18.sp
                )


                TextField(
                    value = textAmountOfKeyVertices,
                    onValueChange = { textAmountOfKeyVertices = it },
                    maxLines = 1,
                    placeholder = { Text("Amount of key vertices:") },
                    modifier = Modifier.height(50.dp).background(Color.White)
                )
                Text("Or")
                TextField(
                    value = textGapToCheck,
                    onValueChange = { textGapToCheck = it },
                    maxLines = 1,
                    placeholder = { Text("Vertices with rank higher than:") },
                    modifier = Modifier.height(50.dp).background(Color.White)
                )

                if (textAmountOfKeyVertices.isNotEmpty() && textGapToCheck.isNotEmpty()) {
                    Text("Please enter only one parameter.")
                }

                if (textAmountOfKeyVertices.isNotEmpty() && textGapToCheck.isEmpty() || textAmountOfKeyVertices.isEmpty() && textGapToCheck.isNotEmpty()) {
                    Button(
                        onClick = {
                            LeaderRankstart = true
                            val amountOfKeyVertices = textAmountOfKeyVertices.toIntOrNull()
                            val gapToCheck = textGapToCheck.toDoubleOrNull()
                            onResult(amountOfKeyVertices, gapToCheck, LeaderRankstart)
                            onDismissRequest()
                        }
                    ) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}

@Composable
fun <V, E> LeaderRankView(graphViewModel: GraphViewModel<V, E>, topKeys: Int? = 0, gap: Double? = null) {
    var rankedList = LeaderRank(graphViewModel.graph, d = 0.15, epsilon = 0.008)
    if (topKeys != null) {
        rankedList = rankedList.take(topKeys)
    }
    else if (gap != null) {
        rankedList = rankedList.filter{it.second > gap}
    }
    rankedList.forEach { topranked ->
        graphViewModel.vertices.forEach {
            if (topranked.first.element == it.v.element) {
                it.color = Color.Red
            }
        }
    }
}

