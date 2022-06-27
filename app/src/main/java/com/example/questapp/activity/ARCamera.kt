package com.example.questapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.questapp.R
import com.google.ar.core.ArCoreApk
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException
import io.github.sceneview.SceneView
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.utils.getResourceUri
import io.github.sceneview.utils.setFullScreen

class ARCamera : AppCompatActivity() {
    var mUserRequestedInstall = true
    lateinit var mSession: Session

    lateinit var sceneView: ArSceneView

    lateinit var modelNode: ArModelNode


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcamera)
        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        mSession = Session(this)
        var sceneView: SceneView = findViewById(R.id.sceneView)

        modelNode = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = this.getResourceUri(R.raw.cargo),
            autoAnimate = true,
            autoScale = false,
            // Place the model origin at the bottom center
            centerOrigin = Position(y = -1.0f)
        ).apply {
            placementMode = PlacementMode.BEST_AVAILABLE
            editableTransforms = EditableTransform.ALL
        }
        sceneView.apply {
            addChild(modelNode)
            // Select the model node by default (the model node is also selected on tap)
        }
    }
    override fun onResume() {
        super.onResume()

        // ARCore requires camera permission to operate.
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this)
            return
        }
        try {
            if (mSession == null) {
                when (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    ArCoreApk.InstallStatus.INSTALLED -> {
                        // Success: Safe to create the AR session.
                        mSession = Session(this)
                    }
                    ArCoreApk.InstallStatus.INSTALL_REQUESTED -> {
                        // When this method returns `INSTALL_REQUESTED`:
                        // 1. ARCore pauses this activity.
                        // 2. ARCore prompts the user to install or update Google Play
                        //    Services for AR (market://details?id=com.google.ar.core).
                        // 3. ARCore downloads the latest device profile data.
                        // 4. ARCore resumes this activity. The next invocation of
                        //    requestInstall() will either return `INSTALLED` or throw an
                        //    exception if the installation or update did not succeed.
                        mUserRequestedInstall = false
                        return
                    }
                }
            }
        } catch (e: UnavailableUserDeclinedInstallationException) {
            // Display an appropriate message to the user and return gracefully.
            Toast.makeText(this, "TODO: handle exception " + e, Toast.LENGTH_LONG)
                .show()
            return

        }
    }
        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            results: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, results)
            if (!CameraPermissionHelper.hasCameraPermission(this)) {
                Toast.makeText(
                    this,
                    "Camera permission is needed to run this application",
                    Toast.LENGTH_LONG
                )
                    .show()
                if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                    // Permission denied with checking "Do not ask again".
                    CameraPermissionHelper.launchPermissionSettings(this)
                }
                finish()
            }
        }
    fun createSession() {
        // Create a new ARCore session.
        mSession = Session(this)

        // Create a session config.
        val config = Config(mSession)

        // Do feature-specific operations here, such as enabling depth or turning on
        // support for Augmented Faces.

        // Configure the session.
        mSession.configure(config)
    }

    override fun onDestroy() {
        mSession.close()
        super.onDestroy()
    }

}