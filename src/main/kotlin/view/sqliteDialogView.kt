package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import io.sqlite.GraphDatabase
import io.sqlite.convertToDBFormat
import io.sqlite.installGraph
import view.components.BigBtn
import view.components.CoolButton
import viewmodel.MainScreenViewModel


@Composable
fun loadFromSQLite(
    onDismissRequest: () -> Unit,
    mainScreenViewModel: MainScreenViewModel,
    callback: () -> Unit = {}) {

    var graphName by remember { mutableStateOf("") }
    var actionStatus by remember { mutableStateOf("") }

    fun load() {
        val db = GraphDatabase("AppStateDB.db")
        val existedGraphsNames = db.graphsList()
        if (graphName in existedGraphsNames) {
            try {
                val gr = db.loadGraph(graphName)
                installGraph(mainScreenViewModel, gr)
            } catch (e: Exception) {
                e.printStackTrace()
                actionStatus = "Error"
                return
            }
            db.close()
            callback()
        }
        else
            actionStatus = "Graph not found"
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                Text("Enter graph name", modifier = Modifier.padding(10.dp), fontSize = 18.sp)
                Row {
                    TextField(
                        value = graphName,
                        onValueChange = { graphName = it },
                        maxLines = 1,
                        placeholder = { Text("name") },
                        modifier = Modifier.height(50.dp).background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CoolButton(
                    onClick = { load() }, BigBtn
                ) { Text("Load") }
                Text(actionStatus)
            }
        }
    }
}


@Composable
fun saveToSQLite(
    onDismissRequest: () -> Unit,
    mainScreenViewModel: MainScreenViewModel,
    callback: () -> Unit = {}) {

    var graphName by remember { mutableStateOf("") }
    var actionStatus by remember { mutableStateOf("") }

    fun save() {
        val db = GraphDatabase("AppStateDB.db")
        val g = convertToDBFormat(mainScreenViewModel)
        try {
            db.createGraphLayout(graphName)
            db.saveGraph(graphName, g)
            actionStatus = "Graph saved successfully"
        } catch (e: Exception) {
            e.printStackTrace()
            actionStatus = "Error"
            return
        }
        db.close()
        callback()
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
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
                Text("Enter graph name", modifier = Modifier.padding(10.dp), fontSize = 18.sp)
                Row {
                    TextField(
                        value = graphName,
                        onValueChange = { graphName = it },
                        maxLines = 1,
                        placeholder = { Text("name") },
                        modifier = Modifier.height(50.dp).background(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                CoolButton(
                    onClick = { save() }, BigBtn
                ) { Text("Save") }
                Text(actionStatus)
            }
        }
    }
}