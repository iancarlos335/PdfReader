package com.example.pdf.reader

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PdfReader : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private lateinit var fabPlay: FloatingActionButton
    private lateinit var fabStop: FloatingActionButton
    private lateinit var fabPause: FloatingActionButton
    private lateinit var seekbar: SeekBar

    private var currentSong = mutableListOf(R.raw.audio_transcribed)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        setup()


        controlSound(currentSong[0])
    }

    private fun setup() {
        fabPlay = findViewById(R.id.fab_play)
        fabStop = findViewById(R.id.fab_stop)
        fabPause = findViewById(R.id.fab_pause)

        seekbar = findViewById(R.id.seekbar)
    }

    private fun controlSound(id: Int) {
        fabPlay.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, id)
                Log.d("PdfReaderClass", "ID: ${mp!!.audioSessionId}")

                initializeSeekBar()
            }
            mp?.start()
            Log.d("PdfReaderClass", "Duration: ${mp!!.duration / 1000} seconds")
        }

        fabPause.setOnClickListener {
            if (mp !== null) mp?.pause()
            Log.d("PdfReaderClass", "Paused at: ${mp!!.currentPosition / 1000} seconds")
        }

        fabStop.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun initializeSeekBar() {
        seekbar.max = mp!!.duration

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    seekbar.progress = 0
                }
            }
        }, 0)
    }

}