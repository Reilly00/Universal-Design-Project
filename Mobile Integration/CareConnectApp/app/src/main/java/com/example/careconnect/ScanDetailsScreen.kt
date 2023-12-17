package com.example.careconnect

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
@Composable
fun ScanDetailsScreen(navController: NavController? = null, ) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraView = remember { PreviewView(context) }
    val boundingBox = remember { mutableStateOf<Rect?>(null) }
    val scannedMessage = remember { mutableStateOf<String?>(null) }
    val barcodeScanner = remember {
        BarcodeScanner(context, lifecycleOwner, cameraView, boundingBox, scannedMessage, navController)
    }
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    } else {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Display the QR code
                    CameraPreview(lifecycleOwner = lifecycleOwner, scannedMessage = scannedMessage)
                    Spacer(modifier = Modifier.weight(2f))
                    // Display the scanned message at the bottom
                    DisplayScannedMessage(scannedMessage = scannedMessage.value)
                }
            }
        }
    }
}

@Composable
fun CameraPreview(lifecycleOwner: LifecycleOwner, scannedMessage: MutableState<String?>) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val cameraView = remember { PreviewView(context) }
    val boundingBox = remember { mutableStateOf<Rect?>(null) }

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(
            context as ComponentActivity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    } else {
        val barcodeScanner = remember {
            BarcodeScanner(context, lifecycleOwner, cameraView, boundingBox, scannedMessage)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CameraPreviewContent(
                cameraProviderFuture = cameraProviderFuture,
                context = context,
                lifecycleOwner = lifecycleOwner,
                cameraView = cameraView,
                boundingBox = boundingBox,
                barcodeScanner = barcodeScanner
            )
            BarcodeBoundingBox(boundingBox = boundingBox.value)
        }
    }
}

@Composable
private fun CameraPreviewContent(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    context: Context,
    lifecycleOwner: LifecycleOwner,
    cameraView: PreviewView,
    boundingBox: MutableState<Rect?>,
    barcodeScanner: BarcodeScanner
) {
    AndroidView(
        factory = { context ->
            cameraView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            cameraView
        },
        update = { view ->

            cameraProviderFuture.addListener({
                try {
                    val cameraProvider = cameraProviderFuture.get()

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val preview = Preview.Builder()
                        .setTargetResolution(Size(640, 500))
                        .setTargetRotation(view.display.rotation)
                        .build()
                    preview.setSurfaceProvider(view.surfaceProvider)

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(640, 500))
                        .setTargetRotation(view.display.rotation)
                        .build()
                        .also {
                            it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                                barcodeScanner.processImage(imageProxy)
                            }
                        }

                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageAnalysis
                    )

                    barcodeScanner.startScanning()

                } catch (e: Exception) {
                    Log.e("CameraPreview", "Error initializing camera: ${e.message}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}


@Composable
fun BarcodeBoundingBox(boundingBox: Rect?) {
    if (boundingBox != null) {
        DrawBox(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = (boundingBox.left + boundingBox.right) / 5f,
                    translationY = (boundingBox.top + boundingBox.bottom) / 5f,
                    scaleX = boundingBox.width().toFloat() / 600f,
                    scaleY = boundingBox.height().toFloat() / 600f
                ),
            color = Color.Yellow
        )
    }
}

@Composable
fun DrawBox(modifier: Modifier = Modifier, color: Color = Color.Yellow) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .graphicsLayer(
                translationX = 0.5f,
                translationY = 0.5f,
                scaleX = 1f,
                scaleY = 1f
            )
            .border(4.dp, color = color, shape = RoundedCornerShape(4.dp))
    )
}

@Composable
fun DisplayScannedMessage(scannedMessage: String?) {
    if (!scannedMessage.isNullOrBlank()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Scanned QR Code: $scannedMessage",
                    color = Color.Magenta,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalGetImage::class)
class BarcodeScanner(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val cameraView: PreviewView,
    private val boundingBox: MutableState<Rect?>,
    private val scannedMessage: MutableState<String?>,
    private val navController: NavController? = null
) {
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    )

    fun startScanning() {
        Log.d("BarcodeScanner", "startScanning called")
    }

    fun processImage(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val inputImage = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val value = barcode.displayValue
                        Log.d("BarcodeScanner", "QR Code Value: $value")
                        boundingBox.value = barcode.boundingBox

                        // Set the scanned message
                        scannedMessage.value = value

                        if (!value.isNullOrBlank()) {
                            navController?.navigate("patientsList/$value")
                        }

                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

}

private const val CAMERA_PERMISSION_REQUEST_CODE = 123
