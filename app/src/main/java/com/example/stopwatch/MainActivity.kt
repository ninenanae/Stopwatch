package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var chrometr : Chronometer
    private var running : Boolean = false
    private var offset : Long = 0
    val  OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base_key"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        chrometr = findViewById(R.id.textTime)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnReset = findViewById<Button>(R.id.btnReset)
        val btnPause = findViewById<Button>(R.id.btnPause)

        if(savedInstanceState != null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                chrometr.base =savedInstanceState.getLong(BASE_KEY)
                chrometr.start()
            } else setBaseTime()
        }

        chrometr.base = SystemClock.elapsedRealtime()

        btnStart.setOnClickListener {
            if(!running) {
                setBaseTime()
                chrometr.start()
                running = true
            }
        }

        btnPause.setOnClickListener {
            if (running){
                saveOfset()
                chrometr.stop()
                running = false
            }
        }

        btnReset.setOnClickListener {
            offset = 0
            setBaseTime()
            running = false
        }


    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong("offset",offset)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putLong("base_key", chrometr.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun saveOfset() {
        offset = SystemClock.elapsedRealtime() - chrometr.base
    }

    private fun setBaseTime() {
        chrometr.base = SystemClock.elapsedRealtime() - offset
    }


}