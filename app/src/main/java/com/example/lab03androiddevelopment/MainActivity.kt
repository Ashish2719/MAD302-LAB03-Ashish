/**
 * Course Code: MAD302
 * Lab Number: LAB 3
 * Name: Ashish Prajapati
 * Student ID: A00194842
 * Date: April 10, 2026
 *
 * Description:
 * This Android app requests Camera permission and simulates
 * fetching data using a coroutine with delay(2000). The result
 * is displayed in the UI. It also handles permission denial
 * and simulated network failure using try-catch.
 */

package com.example.lab03androiddevelopment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.lab03androiddevelopment.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * MainActivity is the main screen of the app.
 * It handles permission checking and mock data fetching.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Requests Camera permission from the user.
     * If granted, the app fetches mock data.
     * If denied, the app shows a message in the UI.
     */
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                fetchMockData()
            } else {
                showMessage("Permission denied. Camera permission is required to fetch data.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFetchData.setOnClickListener {
            checkPermissionAndFetch()
        }
    }

    /**
     * Checks if Camera permission is already granted.
     * If yes, starts fetching data.
     * If not, requests permission.
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

    private fun fetchMockData() {
        lifecycleScope.launch {
            try {
                setLoading(true)
                showMessage("Fetching data...")

                // Simulated API delay
                delay(2000)

                // Randomly simulate a network error
                val shouldFail = Random.nextBoolean()
                if (shouldFail) {
                    throw Exception("Simulated network failure")
                }

                showMessage("Data fetched successfully!")
            } catch (e: Exception) {
                showMessage("Error: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnFetchData.isEnabled = !isLoading
    }

    private fun showMessage(message: String) {
        binding.tvResult.text = message
    }
}