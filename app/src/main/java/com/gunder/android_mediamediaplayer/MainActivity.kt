package com.gunder.android_mediamediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gunder.android_mediamediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var isReady: Boolean = false
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        binding.apply {
            btnPlay.setOnClickListener {
                if (!isReady) {
                    mediaPlayer?.prepareAsync()
                } else {
                    if (mediaPlayer?.isPlaying as Boolean) {
                        mediaPlayer?.pause()
                    } else {
                        mediaPlayer?.start()
                    }
                }
            }
            btnPause.setOnClickListener {
                if (mediaPlayer?.isPlaying as Boolean || isReady) {
                    mediaPlayer?.stop()
                    isReady = false
                }
            }
        }
    }

    private fun init() {
        mediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mediaPlayer?.setAudioAttributes(attribute)
        val afd = applicationContext.resources.openRawResourceFd(R.raw.guitar_background)
        try {
            mediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaPlayer?.setOnPreparedListener {
            isReady = true
            mediaPlayer?.start()
        }
        mediaPlayer?.setOnErrorListener { _, _, _ -> false }
    }
}