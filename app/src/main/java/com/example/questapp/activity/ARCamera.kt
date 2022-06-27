package com.example.questapp.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.questapp.R
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.gorisse.thomas.sceneform.scene.await

class ARCamera : AppCompatActivity() {
    private lateinit var arFragment: ArFragment
    private val arSceneView get() = arFragment.arSceneView
    private val scene get() = arSceneView.scene
    private var model: Renderable? = null
    private var modelView: ViewRenderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcamera)
        (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
            .setOnTapPlaneGlbModel("models/text.glb")

        arFragment =
            (supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment).apply {
                setOnSessionConfigurationListener { session, config ->
                    // Modify the AR session configuration here
                }
                setOnViewCreatedListener { arSceneView ->
                    arSceneView.setFrameRateFactor(SceneView.FrameRate.FULL)
                }
                setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
                    if (model == null || modelView == null) {
                        Toast.makeText(this@ARCamera, "Loading...", Toast.LENGTH_SHORT).show()
                        return@setOnTapArPlaneListener
                    }

                    // Create the Anchor.
                    scene.addChild(AnchorNode(hitResult.createAnchor()).apply {
                        // Create the transformable model and add it to the anchor.
                        addChild(TransformableNode(arFragment.transformationSystem).apply {
                            renderable = model
                            renderableInstance.setCulling(false)
                            renderableInstance.animate(true).start()
                            // Add the View
                            addChild(Node().apply {
                                // Define the relative position
                                localPosition = Vector3(0.0f, 1f, 0.0f)
                                localScale = Vector3(0.7f, 0.7f, 0.7f)
                                renderable = modelView
                            })
                        })
                    })
                }

            }

        lifecycleScope.launchWhenCreated {
            loadModels()
        }
    }

    private suspend fun loadModels() {
        model = ModelRenderable.builder()
            .setSource(this, Uri.parse("models/text.glb"))
            .setIsFilamentGltf(true)
            .await()
    }
}