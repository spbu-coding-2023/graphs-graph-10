package view.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.skiko.Cursor

interface BtnSize {
    val width: Dp
    val height: Dp
}

object BigBtn: BtnSize {
    override var width: Dp = 220.dp
    override var height: Dp = 40.dp
}

object SmallBtn: BtnSize {
    override var width: Dp = 100.dp
    override var height: Dp = 40.dp
}

object LargeBtn: BtnSize {
    override var width: Dp = 100.dp
    override var height: Dp = 60.dp
}

@Composable
fun coolButton(
    onClick: () -> Unit,
    size: BtnSize,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width = size.width, height = size.height)
            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color(112,102,119),
            contentColor = Color.White
        ),
        content = content
    )
}
