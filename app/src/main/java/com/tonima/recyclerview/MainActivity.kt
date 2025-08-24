package com.tonima.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.tonima.recyclerview.adapters.VerticalAdapter
import com.tonima.recyclerview.utils.PerformanceLogger
import java.lang.Runtime

class MainActivity : AppCompatActivity(), PerformanceLogger {

    private lateinit var logTextView: TextView
    private lateinit var scenarioStatusTextView: TextView
    private lateinit var createdCountTextView: TextView
    private lateinit var ramUsageTextView: TextView
    private lateinit var mainRecyclerView: RecyclerView

    private var createdViewCount = 0
    private val sharedPool = RecyclerView.RecycledViewPool()

    private val runtime = Runtime.getRuntime()

    private val memoryRunnable = object : Runnable {
        override fun run() {
            try {
                Log.d("RAM_MONITOR", "Runnable is running...")

                val totalMemory = runtime.totalMemory()
                val freeMemory = runtime.freeMemory()
                val usedMemoryBytes = totalMemory - freeMemory
                val usedMemoryKb = usedMemoryBytes / 1024

                ramUsageTextView.text = "RAM: ${usedMemoryKb} KB"

            } catch (e: Exception) {
                Log.e("RAM_MONITOR", "Error during memory check", e)
            } finally {
                memoryHandler.postDelayed(this, 250)
            }
        }
    }
    private val memoryHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logTextView = findViewById(R.id.log_textview)
        scenarioStatusTextView = findViewById(R.id.scenario_status_textview)
        createdCountTextView = findViewById(R.id.created_count_textview)
        ramUsageTextView = findViewById(R.id.ram_usage_textview)
        mainRecyclerView = findViewById(R.id.main_recycler_view)

        val btnBefore: Button = findViewById(R.id.btn_before)
        val btnAfter: Button = findViewById(R.id.btn_after)

        mainRecyclerView.layoutManager = LinearLayoutManager(this)

        showScenarioBefore()

        btnBefore.setOnClickListener {
            showScenarioBefore()
        }

        btnAfter.setOnClickListener {
            showScenarioAfter()
        }
    }

    override fun onStop() {
        super.onStop()
        memoryHandler.removeCallbacks(memoryRunnable)
    }

    private fun showScenarioBefore() {
        memoryHandler.removeCallbacks(memoryRunnable)
        createdViewCount = 0
        updateViewCount()
        logTextView.text = "Logs de Performance:\n"
        scenarioStatusTextView.text = "Cenário Atual: ANTES"
        mainRecyclerView.adapter = VerticalAdapter(this, null)
        memoryHandler.post(memoryRunnable)
    }

    private fun showScenarioAfter() {
        memoryHandler.removeCallbacks(memoryRunnable)
        createdViewCount = 0
        updateViewCount()
        logTextView.text = "Logs de Performance:\n"
        scenarioStatusTextView.text = "Cenário Atual: DEPOIS"
        mainRecyclerView.adapter = VerticalAdapter(this, sharedPool)
        memoryHandler.post(memoryRunnable)
    }

    private fun updateViewCount() {
        createdCountTextView.text = "Views Criadas: $createdViewCount"
    }

    override fun onViewCreated() {
        createdViewCount++
        runOnUiThread {
            logTextView.append("-> Creating new horizontal item view...\n")
            updateViewCount()
        }
    }
}