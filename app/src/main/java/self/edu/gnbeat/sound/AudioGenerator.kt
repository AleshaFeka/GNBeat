package self.edu.gnbeat.sound

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

class AudioGenerator(private val sampleRate: Int) {
    private var audioTrack: AudioTrack? = null

    fun createPlayer() {
        audioTrack = AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, sampleRate,
                AudioTrack.MODE_STREAM)

        audioTrack!!.play()
    }

    fun writeSound(samples: DoubleArray) {
        val generatedSnd = get16BitPcm(samples)
        audioTrack!!.write(generatedSnd, 0, generatedSnd.size)
    }

    fun destroyAudioTrack() {
        audioTrack!!.stop()
        audioTrack!!.release()
    }

    companion object {

        fun getSinusWave(sampleSize: Int, sampleRate: Int, frequencyOfTone: Double): DoubleArray {
            val sample = DoubleArray(sampleSize)
            for (i in 0 until sampleSize) {
                sample[i] = Math.sin(2.0 * Math.PI * i.toDouble() / (sampleRate / frequencyOfTone))
            }
            return sample
        }

        fun get16BitPcm(samples: DoubleArray): ByteArray {
            val generatedSound = ByteArray(2 * samples.size)
            var index = 0


            for (sample in samples) {
                // scale to maximum amplitude
                val maxSample = (sample * java.lang.Short.MAX_VALUE).toInt()
                // in 16 bit wav PCM, first byte is the low order byte
                generatedSound[index++] = (maxSample and 0x00ff).toByte()
                generatedSound[index++] = (maxSample and 0xff00).ushr(8).toByte()

            }
            return generatedSound
        }
    }

}
