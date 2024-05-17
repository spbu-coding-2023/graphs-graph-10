package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.neo4j.Neo4jRepository
import viewmodel.MainScreenViewModel


@Composable
fun <V, E> SaveToNeo4jDialog(
    onDismissRequest: () -> Unit,
    actionType: String,
    mainScreenViewModel: MainScreenViewModel<V, E>
) {
    var addressPort by remember { mutableStateOf("localhost:7687") }
    var loginPassword by remember { mutableStateOf("neo4j:password") }
    var actionStatus by remember { mutableStateOf("") }

    fun parseURI(s: String) = "bolt://$s"
    fun parseCredentials(s: String): List<String> {
        return s.split(':')
    }

    fun makeAction() {
        val uri = parseURI(addressPort)
        val credentials = parseCredentials(loginPassword)
        if (credentials.size != 2) {
            actionStatus = "Wrong credentials"
            return
        }
        val (login, password) = credentials

        val db = Neo4jRepository()
        try { db.connect(uri, login, password) }
        catch (e: Exception) {
            e.printStackTrace()
            actionStatus = "Wrong credentials or network error"
            return
        }
        when (actionType) {
            "save" -> {
                try { db.writeData(mainScreenViewModel) }
                catch (e: Exception) {
                    e.printStackTrace()
                    actionStatus = "Unexpected error while writing data"
                    return
                }
                actionStatus = "Graph successfully saved"
            }
            "load" -> {
                try {
                    db.readData(mainScreenViewModel)
                    onDismissRequest()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    actionStatus = "Unexpected error while reading data"
                    return
                }
                actionStatus = "Graph successfully loaded"
            }
        }
        db.close()
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Enter db credentials", modifier = Modifier.padding(10.dp), fontSize = 18.sp)
                Row {
                    TextField(
                        value = addressPort,
                        onValueChange = { addressPort = it},
                        maxLines = 1,
                        placeholder = { Text("address:port") },
                        modifier = Modifier.height(50.dp).background(Color.White)
                    )
                }
                Row {
                    TextField(
                        value = loginPassword,
                        onValueChange = { loginPassword = it},
                        maxLines = 1,
                        placeholder = { Text("login:password") },
                        modifier = Modifier.height(50.dp).background(Color.White)
                    )
                }
                Button(
                    onClick = { makeAction() }
                ) {
                    when (actionType) {
                        "save" -> Text("Connect & save")
                        "load" -> Text("Connect & load")
                    }
                }
                Text(actionStatus)
            }
        }
    }
}
