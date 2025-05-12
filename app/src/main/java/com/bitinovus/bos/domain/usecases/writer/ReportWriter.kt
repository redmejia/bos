package com.bitinovus.bos.domain.usecases.writer

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.bitinovus.bos.data.local.entities.OrderList
import com.bitinovus.bos.domain.usecases.time.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportWriter @Inject constructor(
    private val context: Context,
    private val time: Time,
) {
    private lateinit var page: PdfDocument.Page
    private lateinit var canvas: Canvas

    private fun drawHeader(
        title: String,
        marginTop: Float,
        headersList: List<String>,
        titlePaint: Paint,
        yPosition: Float,
        paint: Paint,
        columnStartX: List<Float>,
    ) {
        canvas.drawText(title, 200f, marginTop, titlePaint)

        val subtitleY = marginTop + titlePaint.textSize + 10f // Spacing after title
        canvas.drawText(
            time.formater(time.now(), "MM/dd/yyyy"),
            200f,
            subtitleY,
            paint
        )
        headersList.forEachIndexed { index, header ->
            canvas.drawText(header, columnStartX[index], yPosition + 30f, paint)
        }
    }

    private fun headerTitles(
        headerTitle: String,
        headerMarginTop: Float,
        titlePaint: Paint,
        paint: Paint,
    ) {
        canvas.drawText(headerTitle, 200f, headerMarginTop, titlePaint)

        val subtitleY = headerMarginTop + titlePaint.textSize + 10f // Spacing after title
        canvas.drawText(
            time.formater(time.now(), "MM/dd/yyyy"),
            200f,
            subtitleY,
            paint
        )

    }

    suspend fun generateGropedReport(
        fileName: String,
        orderList: List<OrderList>,
    ): Boolean {
        try {
            val pdfDocument = PdfDocument()
            val paint = Paint()
            val titlePaint = Paint().apply {
                textSize = 18f
            }

            val pageWidth = 595
            val pageHeight = 842
            val marginTop = 50f
            val marginBottom = 50f
            val lineHeight = 20f

            var yPosition = 70f
            var pageNumber = 1

            fun newPage(header: () -> Unit = {}) {
                val pageInfo =
                    PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                pageNumber++

                val headerYPosition = marginTop + 10f
                header()
                yPosition = headerYPosition + lineHeight
            }

            newPage(header = {
                headerTitles(
                    headerTitle = "Bos POS Report",
                    headerMarginTop = marginTop,
                    titlePaint = titlePaint,
                    paint = paint,
                )
            })


            yPosition += 40f

            orderList.forEachIndexed { orderIndex, orderListItem ->

                if (yPosition + lineHeight > pageHeight - marginBottom) {
                    pdfDocument.finishPage(page)
                    newPage()
                }

                // Print Order Number
                canvas.drawText(String.format("# %s", orderListItem.id), 50f, yPosition, paint)
                yPosition += lineHeight

                // Print each product in the order
                orderListItem.order.forEach { item ->
                    // Product Line
                    canvas.drawText("Product: ${item.name}", 50f, yPosition, paint)
                    yPosition += lineHeight

                    // Quantity Line
                    canvas.drawText("Qty: ${item.items}", 50f, yPosition, paint)
                    yPosition += lineHeight

                    // Price Line
                    val priceFormatted = String.format("%.2f", item.price / 100.0)
                    canvas.drawText("Price: $priceFormatted", 50f, yPosition, paint)
                    yPosition += lineHeight
                }

                // Separator Line
                canvas.drawText("*".repeat(30), 50f, yPosition, paint)
                yPosition += lineHeight

                // Print Transaction Details (Total, Payment, Change, Type)
                val totalFormatted = String.format("%.2f", orderListItem.transaction.total / 100.0)
                val paymentFormatted =
                    String.format("%.2f", orderListItem.transaction.trxAmount / 100.0)
                val changeFormatted =
                    String.format("%.2f", orderListItem.transaction.change / 100.0)

                canvas.drawText("Total: $totalFormatted", 50f, yPosition, paint)
                yPosition += lineHeight

                canvas.drawText("Payment: $paymentFormatted", 50f, yPosition, paint)
                yPosition += lineHeight

                canvas.drawText("Change: $changeFormatted", 50f, yPosition, paint)
                yPosition += lineHeight

                canvas.drawText("Type: ${orderListItem.transaction.type}", 50f, yPosition, paint)

                canvas.drawText("=".repeat(20), 50f, yPosition + 16f, paint)
                yPosition += lineHeight * 3

            }

            withContext(Dispatchers.IO) {
                val contentValue = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    // put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                    // create a directory with sales reports
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DOCUMENTS + "/MyReports/"
                    )
                }

                pdfDocument.finishPage(page)

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValue)

                uri?.let {
                    resolver.openOutputStream(it).use { outputStream ->
                        pdfDocument.writeTo(outputStream!!)
                    }
                }

                pdfDocument.close()
            }
            // report generated
            return true

        } catch (e: Exception) {
            // error report generated
            return false
            Log.e("ERROR", "generateSalesReport: $e")
        }
    }

    suspend fun generateSalesReport(
        fileName: String,
        orderList: List<OrderList>,
    ) {
        try {
            val pdfDocument = PdfDocument()
            val paint = Paint()
            val titlePaint = Paint().apply {
                textSize = 18f
            }

            val pageWidth = 595
            val pageHeight = 842
            val marginTop = 50f
            val marginBottom = 50f
            val lineHeight = 20f

            var yPosition = 70f
            var pageNumber = 1

            val columnStartX = listOf(
                30f,   // Order ID
                80f,   // Product Name
                250f,  // Quantity
                310f,  // Price
                370f,  // Total
                430f,  // Payment
                490f,  // Change
                550f   // Type (moved a bit to the left)
            )

            fun newPage(header: () -> Unit = {}) {
                val pageInfo =
                    PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                pageNumber++

                val headerYPosition = marginTop + 10f
                header()
                yPosition = headerYPosition + lineHeight
            }

            newPage(header = {
                drawHeader(
                    title = "Bos POS Report",
                    headersList = listOf(
                        "#",
                        "Product",
                        "Qty",
                        "Price",
                        "Total",
                        "Payment",
                        "Change",
                        "Type"
                    ),
                    marginTop = marginTop,
                    titlePaint = titlePaint,
                    yPosition = yPosition,
                    paint = paint,
                    columnStartX = columnStartX
                )
            })

            yPosition += 40f

            orderList.forEach { order ->

                if (yPosition + lineHeight > pageHeight - marginBottom) {
                    pdfDocument.finishPage(page)
                    newPage()
                }

                order.order.forEachIndexed { index, item ->
                    canvas.drawText(item.orderID.toString(), columnStartX[0], yPosition, paint)
                    canvas.drawText(item.name, columnStartX[1], yPosition, paint)
                    canvas.drawText(item.items.toString(), columnStartX[2], yPosition, paint)
                    canvas.drawText(
                        (item.price / 100.0).toString(),
                        columnStartX[3],
                        yPosition,
                        paint
                    )
                    canvas.drawText(
                        (order.transaction.total / 100.00).toString(),
                        columnStartX[4],
                        yPosition,
                        paint
                    )
                    canvas.drawText(
                        (order.transaction.trxAmount / 100.00).toString(),
                        columnStartX[5],
                        yPosition,
                        paint
                    )
                    canvas.drawText(
                        (order.transaction.change / 100.00).toString(),
                        columnStartX[6],
                        yPosition,
                        paint
                    )
                    canvas.drawText(order.transaction.type, columnStartX[7], yPosition, paint)
                    yPosition += lineHeight
                }
            }


            withContext(Dispatchers.IO) {
                val contentValue = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.pdf")
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    // put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
                    // create a directory with sales reports
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DOCUMENTS + "/MyReports/"
                    )
                }

                pdfDocument.finishPage(page)

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValue)

                uri?.let {
                    resolver.openOutputStream(it).use { outputStream ->
                        pdfDocument.writeTo(outputStream!!)
                    }
                }

                pdfDocument.close()
                Log.d("WR", "PDF file saved successfully.")
            }

        } catch (e: Exception) {
            Log.e("ERROR", "generateSalesReport: $e")
        }
    }

}

