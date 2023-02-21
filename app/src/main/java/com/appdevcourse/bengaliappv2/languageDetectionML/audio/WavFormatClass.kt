package com.appdevcourse.bengaliappv2.languageDetectionML.audio

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.compose.runtime.currentCompositionLocalContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


public class WavFormatClass() {

    var filePath: String? = null
    var tempRawFile = "temp_record2.raw"
    var tempWavFile = "final_record3.wav"
    val bpp = 16
    val numberOfChannel = 1;
    var sampleRate = 16000
    var channel = AudioFormat.CHANNEL_IN_MONO
    var audioEncoding = AudioFormat.ENCODING_PCM_16BIT
    var recorder: AudioRecord? = null
    var bufferSize = 0
    var recordingThread: Thread? = null
    var isRecording = false
    var samples = 0
    var dataSize:Long = 0;
    var context:Context?=null;

    constructor(context: Context) : this() {
        try {
            filePath = "Record"
            Log.e("path", filePath!!)
            this.context = context;
            bufferSize = AudioRecord.getMinBufferSize(
                16000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )
            //Log.e("bufferSize", bufferSize.toString());
            samples = 0
            dataSize = 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getPath(name: String): String? {
        return try {
            "$filePath/$name"
        } catch (e: Exception) {
            null
        }
    }


    private fun writeRawData() {
        try {
            if (filePath != null) {
                val data = ByteArray(bufferSize)
                //val path = getPath(tempRawFile)
                val f = File(context?.getExternalFilesDir(filePath),tempRawFile)
                if(f.exists()){
                    f.delete();
                }else{
                    f.createNewFile();
                }
                var fileOutputStream:FileOutputStream?=null;
                try{
                    fileOutputStream  = FileOutputStream(f);
                } catch (e:Exception){
                    Log.e("FileError",e.toString());
                }
                var read: Int
                while (isRecording) {
                    read = recorder!!.read(data, 0, bufferSize)
                    if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                        try {
                            fileOutputStream?.write(data)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
                fileOutputStream?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }





//    private fun writeRawData() {
//        try {
//            if (filePath != null) {
//                val buffer = ShortArray(bufferSize)
//                val byteData = ByteArray(bufferSize)
//                val path = getPath(tempRawFile)
//                if(Files.exists(Paths.get(path))){
//                    Files.delete(Paths.get(path))
//                }
//                val fileOutputStream = FileOutputStream(path)
//                var numberOfShort: Int
//                dataSize = 0;
//                while (isRecording) {
//                    numberOfShort = recorder!!.read(buffer, 0, bufferSize)
//                    if (AudioRecord.ERROR_INVALID_OPERATION != numberOfShort) {
//                        try {
//                            for (i in 0 until numberOfShort) {
//                                byteData[i * 2] = buffer[i].toInt().shr(8).toByte()
//                                byteData[i * 2 + 1] = buffer[i].toByte()
//                                dataSize+=2
//                            }
//                            fileOutputStream.write(byteData)
//                            samples+=numberOfShort;
////                            dataSize+=numberOfShort*2;
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//                fileOutputStream.close()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    private fun wavHeader(
        fileOutputStream: FileOutputStream,
        totalAudioLen: Long,
        totalDataLen: Long,
        channels: Int,
        byteRate: Long
    ) {
        try {
            val header = ByteArray(44)
            header[0] = 'R'.code.toByte() // RIFF/WAVE header
            header[1] = 'I'.code.toByte()
            header[2] = 'F'.code.toByte()
            header[3] = 'F'.code.toByte()
            header[4] = (totalDataLen and 0xff).toByte()
            header[5] = (totalDataLen shr 8 and 0xff).toByte()
            header[6] = (totalDataLen shr 16 and 0xff).toByte()
            header[7] = (totalDataLen shr 24 and 0xff).toByte()
            header[8] = 'W'.code.toByte()
            header[9] = 'A'.code.toByte()
            header[10] = 'V'.code.toByte()
            header[11] = 'E'.code.toByte()
            header[12] = 'f'.code.toByte() // 'fmt ' chunk
            header[13] = 'm'.code.toByte()
            header[14] = 't'.code.toByte()
            header[15] = ' '.code.toByte()
            header[16] = 16 // 4 bytes: size of 'fmt ' chunk
            header[17] = 0
            header[18] = 0
            header[19] = 0
            header[20] = 1 // format = 1
            header[21] = 0
            header[22] = channels.toByte()
            header[23] = 0
            header[24] = (sampleRate.toLong() and 0xff).toByte()
            header[25] = (sampleRate.toLong() shr 8 and 0xff).toByte()
            header[26] = (sampleRate.toLong() shr 16 and 0xff).toByte()
            header[27] = (sampleRate.toLong() shr 24 and 0xff).toByte()
            header[28] = (byteRate and 0xff).toByte()
            header[29] = (byteRate shr 8 and 0xff).toByte()
            header[30] = (byteRate shr 16 and 0xff).toByte()
            header[31] = (byteRate shr 24 and 0xff).toByte()
            header[32] = (numberOfChannel * 16 / 8).toByte() // block align
            header[33] = 0
            header[34] = bpp.toByte() // bits per sample
            header[35] = 0
            header[36] = 'd'.code.toByte()
            header[37] = 'a'.code.toByte()
            header[38] = 't'.code.toByte()
            header[39] = 'a'.code.toByte()
            header[40] = (totalAudioLen and 0xff).toByte()
            header[41] = (totalAudioLen shr 8 and 0xff).toByte()
            header[42] = (totalAudioLen shr 16 and 0xff).toByte()
            header[43] = (totalAudioLen shr 24 and 0xff).toByte()
            fileOutputStream.write(header, 0, 44)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createWavFile(tempPath: String?, wavPath: String?) {
        try {
//            val fileInputStream = FileInputStream(tempPath)
//            val fileOutputStream = FileOutputStream(wavPath)
            val inFile =  File(context?.getExternalFilesDir(filePath),tempPath)
            val outFile = File(context?.getExternalFilesDir(filePath),wavPath)
            if(outFile.exists()){
                outFile.delete();
            }else{
                outFile.createNewFile();
            }
            var fileInputStream:FileInputStream? = null
            var fileOutputStream:FileOutputStream? = null
            try {
                fileInputStream = FileInputStream(inFile)
                fileOutputStream = FileOutputStream(outFile)
            }catch (e:Exception){
                Log.e("CreateWaveFileError",e.toString());
            }
            val data = ByteArray(bufferSize)
            val channels = 1
            val byteRate = (bpp * sampleRate * channels / 8).toLong()
            val totalAudioLen =  fileInputStream!!.channel.size()
//            Log.e("dataSize: ", dataSize.toString())
            val totalDataLen = totalAudioLen + 36
            wavHeader(fileOutputStream!!, totalAudioLen, totalDataLen, channels, byteRate)
            while (fileInputStream.read(data) != -1) {
                fileOutputStream.write(data)
            }
            fileInputStream.close()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun startRecording() {
        try {
            recorder = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channel,
                audioEncoding,
                bufferSize
            )
            dataSize = 0
            val status = recorder!!.state
            if (status == 1) {
                recorder!!.startRecording()
                isRecording = true
            }
            recordingThread = Thread { writeRawData() }
            recordingThread!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun stopRecording() {
        try {
            if (recorder != null) {
//                Log.e("samples",samples.toString())
                samples = 0
                isRecording = false
                val status = recorder!!.state
                if (status == 1) {
                    recorder!!.stop()
                }
                recorder!!.release()
                recordingThread = null
                createWavFile(tempRawFile, tempWavFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}