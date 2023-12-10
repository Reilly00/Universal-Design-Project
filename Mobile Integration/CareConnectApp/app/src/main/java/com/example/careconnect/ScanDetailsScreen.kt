package com.example.careconnect

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.common.util.concurrent.ListenableFuture

@Composable
fun ScanDetailsScreen(navController: NavController? = null) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Camera setup
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    CameraPreview(
        modifier = Modifier.aspectRatio(1f),
        cameraProviderFuture = cameraProviderFuture,
        lifecycleOwner = lifecycleOwner
    )
}

@Composable
fun CameraPreview(
    modifier: Modifier,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner
) {
    AndroidView(
        factory = { context ->
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            val imageCapture = ImageCapture.Builder().build()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis,
                imageCapture
            )

            val cameraView = PreviewView(context)
            preview.setSurfaceProvider(cameraView.surfaceProvider)
            cameraView
        },
        modifier = modifier
    )
}
