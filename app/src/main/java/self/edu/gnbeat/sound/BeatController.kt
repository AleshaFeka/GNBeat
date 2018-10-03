package self.edu.gnbeat.sound


import android.os.AsyncTask
import self.edu.gnbeat.model.Track


class BeatController {

    companion object {
        private const val DEFAULT_STRONG_PART = 1
        private const val DEFAULT_WEAK_PART = 2
        private const val TICK_SAMPLE_DURATION = 200
        private const val SAMPLE_RATE = 8000

        private const val STRONG_TICK_FREQUENCY = 859.26
        private const val NORMAL_TICK_FREQUENCY = 659.26
        private const val WEAK_TICK_FREQUENCY = 599.26

        private val NORMAL_TICK = AudioGenerator.getSinusWave(TICK_SAMPLE_DURATION, SAMPLE_RATE, NORMAL_TICK_FREQUENCY)
        private val STRONG_TICK = AudioGenerator.getSinusWave(TICK_SAMPLE_DURATION, SAMPLE_RATE, STRONG_TICK_FREQUENCY)
        private val WEAK_TICK = AudioGenerator.getSinusWave(TICK_SAMPLE_DURATION, SAMPLE_RATE, WEAK_TICK_FREQUENCY)

    }

    private var runner: AsyncTicker? = null
    private val generator: AudioGenerator

    private var isTicking: Boolean = false
    var strongPartsNumber: Int = 0
    var weakPartsNumber: Int = 0
    var track: Track? = null

    var tickState: Boolean
        get() = isTicking
        set(isTicking) {
            this.isTicking = isTicking

            if (isTicking) {
                runner!!.cancel(true)
                runner = AsyncTicker()
                runner!!.execute()
            } else {
                runner!!.cancel(true)
            }
        }

    init {
        this.track = Track()
        strongPartsNumber = DEFAULT_STRONG_PART
        weakPartsNumber = DEFAULT_WEAK_PART
        isTicking = false
        generator = AudioGenerator(SAMPLE_RATE)
        generator.createPlayer()
        runner = AsyncTicker()
    }

    fun destroy() {
        generator.destroyAudioTrack()
    }

    private inner class AsyncTicker : AsyncTask<Void, Boolean, Void>() {
        private var silence: DoubleArray? = null

        private fun getSilenceSampleForGivenConditions(beatsPerMinute: Int, weakPartsNumber: Int): DoubleArray {
            val SILENCE_FREQUENCY = 0.0

            val beatsPerSecond = beatsPerMinute / 60f
            val silenceDuration = (16000 / beatsPerSecond).toInt() //16000 - ? 0_o...При положенных 8000 - выдает в 2 раза меньшую паузу...

            return AudioGenerator.getSinusWave((silenceDuration - TICK_SAMPLE_DURATION) / weakPartsNumber, SAMPLE_RATE, SILENCE_FREQUENCY)
        }

        override fun doInBackground(vararg v: Void): Void? {
            var playedPartsCount = 0
            var isVisualClickActive = true
            var lastBpm = track!!.bpm
            var lastWeekPartsNumber = weakPartsNumber

            silence = getSilenceSampleForGivenConditions(track!!.bpm, weakPartsNumber)

            while (isTicking) {
                if (isCancelled) {
                    return null
                }

                if (lastBpm != track!!.bpm || lastWeekPartsNumber != weakPartsNumber) {
                    silence = getSilenceSampleForGivenConditions(track!!.bpm, weakPartsNumber)
                    lastBpm = track!!.bpm
                    lastWeekPartsNumber = weakPartsNumber
                }

                // обновляем счетчик
                playedPartsCount++
                isVisualClickActive = !isVisualClickActive

                //обновляем визуальный индикатор
                publishProgress(isVisualClickActive)

                if (weakPartsNumber < 2) {
                    if (playedPartsCount % strongPartsNumber == 0) {
                        // проигрываем сильную долю
                        playedPartsCount = 0
                        generator.writeSound(STRONG_TICK)
                    } else {
                        // проигрываем обычную долю
                        generator.writeSound(NORMAL_TICK)
                    }

                    // доигрываем оставшееся время тишиной.
                    generator.writeSound(silence!!)
                } else {
                    if (playedPartsCount % (strongPartsNumber * weakPartsNumber) == 0) {
                        // проигрываем сильную долю
                        playedPartsCount = 0
                        generator.writeSound(STRONG_TICK)
                    } else {
                        // проигрываем обычную долю
                        if (playedPartsCount % weakPartsNumber == 0) {
                            generator.writeSound(NORMAL_TICK)
                        } else {
                            generator.writeSound(WEAK_TICK)
                        }// проигрываем слабую долю
                    }

                    // доигрываем оставшееся время тишиной.
                    generator.writeSound(silence!!)
                }
            }

            return null
        }

        override fun onProgressUpdate(vararg values: Boolean?) {
            super.onProgressUpdate(*values)
        }
    }

}
