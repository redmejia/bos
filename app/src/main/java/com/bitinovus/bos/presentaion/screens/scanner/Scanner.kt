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
import android.util.Log
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bitinovus.bos.domain.usecases.analyzer.BarcodeAnalyzer
import com.bitinovus.bos.presentaion.screens.scanner.scannerbox.ScannerBox

@Composable
fun Scanner() {

    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    var isCameraPermissionDenied by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    var barcodeID by remember { mutableStateOf("") }
    var isDetected by remember { mutableStateOf(false) }


    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS) // IMAGE_ANALYSIS for scan barcodes
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                BarcodeAnalyzer(onDetectedBarcode = { barcode ->

                    if (barcode.isNotBlank()) {
                        barcodeID = barcode
                        isDetected = true
                    }

                })
            )
            bindToLifecycle(lifecycle)
        }
    }

    LaunchedEffect(key1 = isDetected) {
        Log.d("BARCODE", "Scanner>>>>>>>>>>>>>: $barcodeID")
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
                    cameraController.clearImageAnalysisAnalyzer()
                    cameraController.unbind()
                }
            )
            ScannerBox(
                boxHeightSize = 0.2f,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}

