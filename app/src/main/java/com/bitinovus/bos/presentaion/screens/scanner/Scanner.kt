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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bitinovus.bos.domain.usecases.analyzer.BarcodeAnalyzer
import com.bitinovus.bos.presentaion.screens.scanner.scannerbox.ScannerBox
import com.bitinovus.bos.presentaion.viewmodels.scannerviewmodel.ScannerViewmodel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack98
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlue80
import com.bitinovus.bos.presentaion.viewmodels.cartviewmodel.CartViewmodel
import kotlinx.coroutines.launch
import com.bitinovus.bos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scanner(
    scannerViewmodel: ScannerViewmodel,
    cartViewmodel: CartViewmodel,
) {

    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current

    var isCameraPermissionGranted by remember { mutableStateOf(false) }
    var isCameraPermissionDenied by remember { mutableStateOf(false) }

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

    var barcodeID by remember { mutableStateOf("") }
    var isDetected by remember { mutableStateOf(false) }

    val product by scannerViewmodel.uiState.collectAsState()


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

    LaunchedEffect(key1 = isDetected, key2 = showBottomSheet, key3 = product) {
        if (isDetected) {
            scannerViewmodel.getProduct(barcodeID)
            if (product?.product != null) {
                showBottomSheet = true
                isDetected = false
            }

        }

        if (showBottomSheet) {
            // change to capture to stop scanning
            cameraController.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
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
                        scaleType = PreviewView.ScaleType.FIT_CENTER
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
                hideBoxScannerArea = showBottomSheet,
                boxHeightSize = 0.2f,
                modifier = Modifier.matchParentSize()
            )

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                        // No action to add product to list set to scan new product
                        cameraController.setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                    },
                    sheetState = sheetState
                ) {
                    product?.product?.let {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),

                            ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .background(// not need for business image product
                                            color = PrimaryBlack98,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                        .width(110.dp)
                                        .height(110.dp),
                                    model = it.productImage,
                                    contentDescription = it.name
                                )
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(
                                        text = it.name,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 20.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = stringResource(id = R.string.price)
                                                + " $${it.price / 100.0}"
                                    ) // add format 0.00
                                }
                            }
                            HorizontalDivider()
                            FilledTonalButton(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryBlue80
                                ),
                                onClick = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            product?.product?.let { product ->
                                                cartViewmodel.addToCart(product)
                                            }
                                            showBottomSheet = false
                                            // If camera has the option
                                            // IMAGE_CAPTURE change to IMAGE_ANALYSIS
                                            cameraController
                                                .setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                                        }
                                    }
                                }) {
                                Text(text = stringResource(id = R.string.add_to_cart).uppercase())
                            }
                        }
                    }
                }
            }
        }
    }
}