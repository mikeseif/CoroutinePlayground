package org.npr.coroutineplayground

import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.npr.coroutineplayground.data.model.Shibe
import org.npr.coroutineplayground.viewmodel.ShibeViewModel
import java.util.*
import java.util.concurrent.TimeUnit

class DefaultActivity : AppCompatActivity() {

    private lateinit var pb1: ProgressBar
    private lateinit var pb2: ProgressBar
    private lateinit var pb3: ProgressBar

    private var job1: Job? = null
    private var job2: Job? = null
    private var job3: Job? = null

    private var shibeJob: Job? = null

    private var raceEnd = false

    lateinit var vm: ShibeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        pb1 = findViewById(R.id.pb1)
        pb2 = findViewById(R.id.pb2)
        pb3 = findViewById(R.id.pb3)

        findViewById<Button>(R.id.button).setOnClickListener {
            startRace()
        }

        findViewById<Button>(R.id.apiButton).setOnClickListener {
            vm.refreshShibe()
        }

        val iv = findViewById<ImageView>(R.id.imageView)

        vm = ViewModelProviders.of(this).get(ShibeViewModel::class.java)
        vm.shibe.observe(this, Observer {
            Log.d("Shibe", "Live Shibe spotted: $it")
            it?.let {
                Glide.with(this).load(it.imgUrl).into(iv)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        resetRun()
    }

    private fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start

    private fun startRace() {
        resetRun()

        job1 = launch(UI) { updateProgress(pb1) }
        job2 = launch(UI) { updateProgress(pb2) }
        job3 = launch(UI) { updateProgress(pb3) }
    }

    private suspend fun updateProgress(pb: ProgressBar) {
        pb.progress = 0
        Log.v("updateProgress", "In start : ${Thread.currentThread().name} for progressBar: ${pb.tooltipText}")

        while (pb.progress < 100 && !raceEnd) {
            delay(100)
            pb.progress += (1..10).random()
            Log.v("updateProgress", "In start : ${Thread.currentThread().name} for progress: ${pb.progress}")
        }

        if (!raceEnd) {
            Log.v("updateProgress", "Out start : ${Thread.currentThread().name} for progressBar: ${pb.tooltipText}")
            raceEnd = true
            Toast.makeText(this, "${pb.tooltipText} is the winner", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetRun() {
        raceEnd = false

        job1?.cancel()
        job2?.cancel()
        job3?.cancel()
    }
}
