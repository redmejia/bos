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
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bitinovus.bos.domain.usecases.analyzer.BarcodeAnalyzer
import com.bitinovus.bos.presentaion.screens.scanner.scannerbox.ScannerBox
import com.bitinovus.bos.presentaion.viewmodels.productviewmodel.ProductViewmodel
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bitinovus.bos.data.remote.models.Product
import com.bitinovus.bos.presentaion.screens.scanner.cart.Cart
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack80
import com.bitinovus.bos.presentaion.ui.theme.PrimaryBlack98
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scanner(
    productViewmodel: ProductViewmodel,
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
    var total by remember { mutableLongStateOf(0) }
    var cart = remember { mutableStateListOf<Product>() }

    var itemsInCart = remember { mutableIntStateOf(0) }
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

    LaunchedEffect(key1 = isDetected, key2 = showBottomSheet, key3 = product) {
        if (isDetected) {
            productViewmodel.getProduct(barcodeID)
            if (product?.product != null) {
                showBottomSheet = true
                isDetected = false
            }

        }

        if (showBottomSheet) {
            // change to capture to stop scanning
            cameraController.setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }

        if (cart.isNotEmpty<Product>() && product?.product != null) {
            itemsInCart.intValue = cart.sumOf { it.items }
            total = cart.sumOf { it.price * it.items }
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
            if (cart.isNotEmpty()) {
                Cart(cart = cart, itemsInCart = itemsInCart.intValue, total = total)
            }
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
                                    Text(text = "Product: ${it.name}")
                                    Text(text = "Price: $${it.price / 100.0}") // add format 0.00
                                }
                            }
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
                                            val existingProductIndex =
                                                cart.indexOfFirst { it.productID == barcodeID }

                                            if (existingProductIndex != -1) {
                                                val existingProduct = cart[existingProductIndex]
                                                val updateProduct = existingProduct.copy(
                                                    items = existingProduct.items + 1
                                                )
                                                cart[existingProductIndex] = updateProduct
                                            } else {
                                                val newProduct = product?.product?.copy(items = 1)
                                                newProduct?.let { element -> cart.add(element) }
                                            }
                                            showBottomSheet = false
                                            // If camera has the option
                                            // IMAGE_CAPTURE change to IMAGE_ANALYSIS
                                            cameraController
                                                .setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                                        }
                                    }
                                }) {
                                Text("add to cart".uppercase(), style = TextStyle())
                            }
                        }
                    }
                }
            }
        }
    }
}