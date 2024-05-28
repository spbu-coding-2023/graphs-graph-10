import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import view.MainScreen

import view.Welcome
import viewmodel.MainScreenViewModel

object WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        Welcome()
    }
}

data class GraphScreen(val mainViewModel: MainScreenViewModel) : Screen {
    @Composable
    override fun Content() {
        MainScreen(mainViewModel)
    }
}

@Composable
@Preview
fun App() {
    Navigator(WelcomeScreen)
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 1200.dp, height = 800.dp),
        title = "Graphs 10"
    ) {
        App()
    }
}
