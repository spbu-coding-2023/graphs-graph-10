package view.graph

import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
@Composable
fun FileExplorer(fileType: String, onFileSelected: (String) -> Unit) {
    var showFilePicker by remember { mutableStateOf(true) }

    if (showFilePicker) {
        FilePicker(show = showFilePicker, fileExtensions = listOf(fileType)) { platformFile ->
            showFilePicker = false
            platformFile?.let { file ->
                onFileSelected(file.path)
            }
        }
    }
}