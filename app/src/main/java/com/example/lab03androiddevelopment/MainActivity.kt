/**
 * Course Code: MAD302
 * Lab Number: LAB 3
 * Name: Ashish Prajapati
 * Student ID: A00194842
 * Date: April 10, 2026
 *
 * Description:
 * This Android app requests Camera permission before performing
 * any action. If permission is granted, it proceeds to fetch data.
 * If permission is denied, it shows a message in the UI.
 */

package com.example.lab03androiddevelopment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lab03androiddevelopment.databinding.ActivityMainBinding

/**
 * MainActivity handles Camera permission checking
 * and controls user interaction for fetching data.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Permission launcher for Camera permission.
     * If granted → proceed to fetch data.
     * If denied → show message.
     */
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchMockData()
            } else {
                showMessage("Permission denied. Camera permission is required.")
            }
        }

    /**
     * Called when activity is created.
     * Sets up UI and button click listener.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetchData.setOnClickListener {
            checkPermissionAndFetch()
        }
    }

    /**
     * Checks if Camera permission is granted.
     * If granted → fetch data.
     * If not → request permission.
     */
    private fun checkPermissionAndFetch() {
        val permissionStatus = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            fetchMockData()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    /**
     * Temporary placeholder method.
     * In next branch, this will be replaced with coroutine-based API simulation.
     */
    private fun fetchMockData() {
        showMessage("Permission granted. Ready to fetch data...")
    }

    /**
     * Displays message in UI.
     */
    private fun showMessage(message: String) {
        binding.tvResult.text = message
    }
}