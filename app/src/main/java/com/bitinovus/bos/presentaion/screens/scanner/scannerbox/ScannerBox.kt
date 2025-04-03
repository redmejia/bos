package com.bitinovus.bos.presentaion.screens.scanner.scannerbox

import android.R
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80


@Composable
fun ScannerBox(
    modifier: Modifier = Modifier,
    boxWidthSize: Float = 0.9f,
    boxHeightSize: Float = 0.1f,
    hideBoxScannerArea: Boolean = false,
) {
    Box(
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithContent {
                drawContent()
                drawRoundRect(color = PrimaryBlack80)
                if (!hideBoxScannerArea) {

                    val scannerWidth = size.width * boxWidthSize
                    val scannerHeight = size.height * boxHeightSize

                    // Position
                    val scannerX = (size.width - scannerWidth) / 2
                    val scannerY = (size.height - scannerHeight) / 2

                    // Clear scanning area
                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = Offset(scannerX, scannerY), // box position
                        size = Size(scannerWidth, scannerHeight),
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                        blendMode = BlendMode.Clear
                    )

                    // Border around the transparent area
                    drawRoundRect(
                        color = Color.White,
                        topLeft = Offset(scannerX, scannerY), // box position
                        size = Size(scannerWidth, scannerHeight), // box size
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

            }
    )
}