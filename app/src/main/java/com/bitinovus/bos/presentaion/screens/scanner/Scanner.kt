package com.bitinovus.bos.presentaion.screens.scanner

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.NotificationCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack95

@Composable
fun Scanner() {

    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    var isCameraPermissionDenied by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            bindToLifecycle(lifecycle)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isCameraPermissionGranted = isGranted
        isCameraPermissionDenied = !isGranted
    }

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                isCameraPermissionGranted = true
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    if (isCameraPermissionDenied) {
        Box {
            Text(text = "Camera need permission to scan")
        }
    }

    if (isCameraPermissionGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        controller = cameraController
                    }
                },
                onRelease = {
                    cameraController.unbind()
                }
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithContent {
                        drawContent()
                        val overlayColor = PrimaryBlack95

                        drawRoundRect(
                            color = overlayColor,
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                        )


                        val scannerWidth = size.width * 0.8f
                        val scannerHeight = size.height * 0.2f
                        val scannerX = (size.width - scannerWidth) / 2
                        val scannerY = (size.height - scannerHeight) / 2

                        drawRoundRect(
                            color = Color.Transparent,
                            topLeft = Offset(scannerX, scannerY),
                            size = Size(scannerWidth, scannerHeight),
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                            blendMode = BlendMode.Clear
                        )

                        // Border
                        drawRoundRect(
                            color = Color.White,
                            topLeft = Offset(scannerX, scannerY),
                            size = Size(scannerWidth, scannerHeight),
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }
            )
        }
    }
}