package com.bitinovus.bos.domain.usecases.analyzer

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer : ImageAnalysis.Analyzer {

    private val option = BarcodeScannerOptions
        .Builder()
        .setBarcodeFormats(Barcode.FORMAT_CODE_128, Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(option)

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image ?: return
        val imageInput = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

        scanner.process(imageInput)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    Log.d("ANA", "analyze: ${barcode.rawValue}")
                }
            }
            .addOnFailureListener { Log.e("ANA", "analyze: ERROR", it) }
            .addOnCompleteListener { image.close() }
    }
}