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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bitinovus.bos.domain.usecases.analyzer.BarcodeAnalyzer
import com.bitinovus.bos.presentaion.screens.scanner.scannerbox.ScannerBox
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack98
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scanner(
    productViewmodel: ProductViewmodel,
) {
    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    var isCameraPermissionDenied by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    var barcodeID by remember { mutableStateOf("") }
    var isDetected by remember { mutableStateOf(false) }
    var total by remember { mutableLongStateOf(0) }

    val product by productViewmodel.uiState.collectAsState()
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

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isDetected) {
        Log.d("BARCODE", "Scanner >>>: $barcodeID")
        if (isDetected) {
            productViewmodel.getProduct(barcodeID)
            showBottomSheet = true
            isDetected = false
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
                    cameraController.clearImageAnalysisAnalyzer()
                    cameraController.unbind()
                }
            )
            ScannerBox(
                boxHeightSize = 0.2f,
                modifier = Modifier.matchParentSize()
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(text = "Total: $total", color = Color.White)
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    product?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),

                            ) {
                            Text(text = "Product: ${it.product.name}")
                            Text(text = "Price: $${it.product.price}")
                            HorizontalDivider()
                            OutlinedButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlack80
                                ),
                                border = BorderStroke(1.dp, PrimaryBlack98),
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            product?.product?.price?.let { total += it }
                                            showBottomSheet = false
                                        }
                                    }
                                }) {
                                Text("add to order".uppercase(), style = TextStyle())
                            }
                        }
                    }

                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {

            }
        }
    }
}

