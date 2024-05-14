package view.graph

import androidx.compose.foundation.Canvas

import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import viewmodel.graph.EdgeViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import viewmodel.graph.GraphViewModel
import viewmodel.graph.VertexViewModel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill

import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun <E, V> DirectedEdgeView(
    edgeViewModel: EdgeViewModel<E, V>,
    modifier: Modifier = Modifier,


    ) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // Рисуем линию
        drawLine(
            start = Offset(
                edgeViewModel.u.x.toPx() + edgeViewModel.u.radius.toPx(),
                edgeViewModel.u.y.toPx() + edgeViewModel.u.radius.toPx(),
            ),
            end = Offset(
                edgeViewModel.v.x.toPx() + edgeViewModel.v.radius.toPx(),
                edgeViewModel.v.y.toPx() + edgeViewModel.v.radius.toPx(),
            ),
            color = edgeViewModel.color,
            strokeWidth = edgeViewModel.width
        )


        val startX = edgeViewModel.u.x.toPx() + edgeViewModel.u.radius.toPx()
        val startY = edgeViewModel.u.y.toPx() + edgeViewModel.u.radius.toPx()
        val endX = edgeViewModel.v.x.toPx() + edgeViewModel.v.radius.toPx()
        val endY = edgeViewModel.v.y.toPx() + edgeViewModel.v.radius.toPx()


        val arrowLength = edgeViewModel.u.radius.toPx() / 2
        val arrowAngle = PI / 6


        val dx = endX - startX
        val dy = endY - startY
        val angle = atan2(dy, dx)
        val indentX = endX - edgeViewModel.u.radius.toPx() * cos(angle)
        val indentY = endY - edgeViewModel.u.radius.toPx() * sin(angle)
        val x1 = (indentX - arrowLength * cos(angle - arrowAngle)).toFloat()
        val y1 = (indentY - arrowLength * sin(angle - arrowAngle)).toFloat()
        val x2 = (indentX - arrowLength * cos(angle + arrowAngle)).toFloat()
        val y2 = (indentY - arrowLength * sin(angle + arrowAngle)).toFloat()


        drawPath(
            path = Path().apply {
                moveTo(indentX, indentY)
                lineTo(x1, y1)
                lineTo(x2, y2)
                close()
            },
            color = edgeViewModel.color,
        )

    }
}