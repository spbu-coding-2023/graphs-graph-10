package view.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import view.components.coolButton
import view.components.SmallBtn

@Composable
fun getGraphType(
    onDismissRequest: (GraphType?) -> Unit,
) {

    var selectedGraphType by remember { mutableStateOf<GraphType?>(null) }

    Dialog(onDismissRequest = { onDismissRequest(null) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Choose Graph Type",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedGraphType == GraphType.WEIGHTED,
                        onCheckedChange = { if (it) selectedGraphType = GraphType.WEIGHTED }
                    )
                    Text("Weighted Undirected", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedGraphType == GraphType.WEIGHTED_DIRECTED,
                        onCheckedChange = { if (it) selectedGraphType = GraphType.WEIGHTED_DIRECTED }
                    )
                    Text("Weighted Directed", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedGraphType == GraphType.UNWEIGHTED_DIRECTED,
                        onCheckedChange = { if (it) selectedGraphType = GraphType.UNWEIGHTED_DIRECTED }
                    )
                    Text("Unweighted Directed", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = selectedGraphType == GraphType.UNWEIGHTED_UNDIRECTED,
                        onCheckedChange = { if (it) selectedGraphType = GraphType.UNWEIGHTED_UNDIRECTED }
                    )
                    Text("Unweighted Undirected", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    coolButton(onClick = {
                        onDismissRequest(selectedGraphType)
                    }, SmallBtn) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}

// Enum for graph types
enum class GraphType {
    WEIGHTED,
    WEIGHTED_DIRECTED,
    UNWEIGHTED_DIRECTED,
    UNWEIGHTED_UNDIRECTED
}
