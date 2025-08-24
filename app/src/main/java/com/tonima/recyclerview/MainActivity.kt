package com.tonima.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tonima.recyclerview.adapters.VerticalAdapter
import com.tonima.recyclerview.utils.PerformanceLogger

class MainActivity : AppCompatActivity(), PerformanceLogger {

    private lateinit var logTextView: TextView
    private lateinit var scenarioStatusTextView: TextView
    private lateinit var createdCountTextView: TextView
    private lateinit var mainRecyclerView: RecyclerView
    private var createdViewCount = 0

    // Instância única do RecycledViewPool que será compartilhada
    private val sharedPool = RecyclerView.RecycledViewPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logTextView = findViewById(R.id.log_textview)
        scenarioStatusTextView = findViewById(R.id.scenario_status_textview)
        createdCountTextView = findViewById(R.id.created_count_textview)
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

    private fun showScenarioBefore() {
        createdViewCount = 0
        updateViewCount()
        logTextView.text = "Logs de Performance:\n"
        scenarioStatusTextView.text = "Cenário Atual: ANTES"
        mainRecyclerView.adapter = VerticalAdapter(this, null)
    }

    private fun showScenarioAfter() {
        createdViewCount = 0
        updateViewCount()
        logTextView.text = "Logs de Performance:\n"
        scenarioStatusTextView.text = "Cenário Atual: DEPOIS"
        mainRecyclerView.adapter = VerticalAdapter(this, sharedPool)
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