package com.appdevcourse.bengaliappv2.view.screens

import android.Manifest.permission.*
import android.content.ContentResolver
import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.languageDetectionML.audio.WavFormatClass
import com.appdevcourse.bengaliappv2.languageDetectionML.JLibrosa
import com.appdevcourse.bengaliappv2.ui.theme.LightGreen1
import com.appdevcourse.bengaliappv2.ui.theme.LightGreen2
import com.google.accompanist.permissions.*
import kotlinx.coroutines.*
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*


var AudioProcessor  =
    JLibrosa();
val sourceFile = File("fully_trained_edgeimpluse(.6487)(2048-128-100)(128-801)(BCNS)2.tflite")
var finalInputArray = Array(1){ Array(128) {Array(801){FloatArray(1){0f}}}}


var interpreter:Interpreter?=null
var region = arrayOf("Barishal", "Chittagong","Noakhali","Shylet")
var directory = "fully_trained_edgeimpluse(.6487)(2048-128-100)(128-801)(BCNS)2.tflite"
var filePath: String? = null;

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(mng: AssetManager, contentresolver: ContentResolver) {

    val mContext = LocalContext.current
    filePath = mContext.getExternalFilesDir("Record").toString();
    Log.d("filepath", filePath!!);
    var isRecording = false;
    var recordingStateText = remember { mutableStateOf("") }
    val multiplePermissionsState =  rememberMultiplePermissionsState(listOf(
        RECORD_AUDIO,
//        WRITE_EXTERNAL_STORAGE,
//        READ_EXTERNAL_STORAGE,
//        MANAGE_EXTERNAL_STORAGE
    ))
    var shouldShowRationale by remember { mutableStateOf(false) }
    var wavRecordObj = WavFormatClass(mContext)
//    Log.e("path",Environment.getExternalStorageDirectory().path)

    var permissionGiven = multiplePermissionsState.permissions.all {
        shouldShowRationale = it.status.shouldShowRationale
        it.status == PermissionStatus.Granted
    }
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = recordingStateText.value,
                modifier = Modifier.
                padding(top = 0.dp, bottom = 10.dp)
            )
            OutlinedButton(
                onClick = {
                    if(!isRecording) {
                        if (permissionGiven) {
                            recordingStateText.value = "Recording"
                            wavRecordObj.startRecording();
                            isRecording = !isRecording;
                            GlobalScope.launch {
                                delay(5500)
                                if(isRecording) {
                                    isRecording = !isRecording;
                                    wavRecordObj.stopRecording();
                                    recordingStateText.value = "Processing"
                                    var processedData:Array<FloatArray> = processAudio();

                                    recordingStateText.value = "PowerToDB"
                                    processedData = AudioProcessor.powerToDb(processedData, 1F, 80F);

                                    recordingStateText.value = "Normalizing"
                                    normalizeFloatArray(processedData)

                                    ////2d(128*801) -> 4d (1*128*801*1)
                                    for (i in 0 until 128) {
                                        for (j in 0 until 801) {
                                            finalInputArray[0][i][j][0] = processedData[i][j]
                                        }
                                    }


//                                    Log.e("Array: ", Arrays.deepToString(processedData))
                                    recordingStateText.value = "Model Inferencing";
                                    var modelOutput = Array(1){FloatArray(4) { 0.0f }}

                                    if(interpreter==null) {
                                        var modelFile = loadModelFile(mng, directory)
                                        interpreter = modelFile?.let { Interpreter(it) }
                                    }

                                    try {
                                        interpreter?.run(finalInputArray, modelOutput)
                                    }catch (e:Exception){
                                        Log.e("Inference",e.toString())
                                    }
                                    var outputString:String = ""
                                    var reg = 0;
                                    for(i in modelOutput[0]){
                                        outputString+= region[reg++]+": "+i.toString()+"\n"
                                    }
                                    recordingStateText.value = outputString
//                                    Log.e("Output: ", outputString)

                                }
                            }
                        } else {
                            multiplePermissionsState.launchMultiplePermissionRequest()
                            permissionGiven = multiplePermissionsState.permissions.all {
                                shouldShowRationale = it.status.shouldShowRationale
                                it.status == PermissionStatus.Granted
                            }
                            if (permissionGiven) {
                                recordingStateText.value = "Recording"
                                wavRecordObj.startRecording();
                                isRecording = !isRecording;
                                GlobalScope.launch {
                                    delay(5500)
                                    if(isRecording) {
                                        recordingStateText.value = ""
                                        isRecording = !isRecording;
                                        wavRecordObj.stopRecording();
                                        recordingStateText.value = "Processing"
                                        processAudio();
                                        recordingStateText.value = "";
                                    }
                                }
                            }
                        }
                    }else{
                        recordingStateText.value = ""
                        isRecording = !isRecording;
                        wavRecordObj.stopRecording();
                    }
//                    Toast.makeText(
//                        mContext,
//                        "This is a Circular Button with a + Icon",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    val navigate = Intent(activity, SecondActivity::class.java);
//                    startActivity(navigate)

                },
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                border = BorderStroke(2.dp, LightGreen2),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)
            ) {
                // Adding an Icon "Add" inside the Button
                Icon(
                    painterResource(id = R.drawable.ic_baseline_mic_24),
                    contentDescription = "content description",
                    tint = LightGreen1.copy(alpha = .5f),
                    modifier = Modifier
                        .size(100.dp)
                )

            }

//            var audioUri by remember { mutableStateOf<Uri?>(null)}
//            val launcher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.GetContent()){
//                audioUri = it
//            }

//            OutlinedButton(onClick = {
//                launcher.launch("audio/wav");
//            }) {
//                Text(text = "open from file")
//            }
//            audioUri?.let {
//                Log.e("uri",audioUri.toString())
//                val cursor = contentresolver.query(audioUri!!, null, null, null, null)
//                if (cursor != null) {
//                    cursor.use {
//                        if (it.moveToFirst()) {
//                            val columnIndex = it.getColumnIndex(MediaStore.Audio.Media.DATA)
//                            filePath = it.getString(columnIndex)
//                            Log.e("pathafter", filePath!!)
//                            var audio = AudioProcessor.loadAndRead(filePath, 16000, 5);
//                            var  processedData = AudioProcessor.generateMelSpectroGram(audio, 16000, 2048, 128, 100)
//
//
//                            processedData = AudioProcessor.powerToDb(processedData, 1F, 80F);
//
//                            recordingStateText.value = "Normalizing"
//                            normalizeFloatArray(processedData)
//
//                            ////2d(128*801) -> 4d (1*128*801*1)
//                            for (i in 0 until 128) {
//                                for (j in 0 until 801) {
//                                    finalInputArray[0][i][j][0] = processedData[i][j]
//                                }
//                            }
//
//
////                                    Log.e("Array: ", Arrays.deepToString(processedData))
//                            recordingStateText.value = "Model Inferencing";
//                            var modelOutput = Array(1){FloatArray(4) { 0.0f }}
//
//                            if(interpreter==null) {
//                                var modelFile = loadModelFile(mng, directory)
//                                interpreter = modelFile?.let { Interpreter(it) }
//                            }
//
//                            try {
//                                interpreter?.run(finalInputArray, modelOutput)
//                            }catch (e:Exception){
//                                Log.e("Inference",e.toString())
//                            }
//                            var outputString:String = ""
//                            var reg = 0;
//                            for(i in modelOutput[0]){
//                                outputString+= region[reg++]+": "+i.toString()+"\n"
//                            }
//                            recordingStateText.value = outputString
//                        }
//                    }
//                }
//            }

        }

    }
}


//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
private fun getPath(name: String): String? {
    return try {
        "$filePath/$name"
    } catch (e: Exception) {
        null
    }
}

@Throws(IOException::class)
private fun loadModelFile(assets: AssetManager, modelFilename: String): MappedByteBuffer? {
    val fileDescriptor = assets.openFd(modelFilename)
    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
    val fileChannel = inputStream.channel
    val startOffset = fileDescriptor.startOffset
    val declaredLength = fileDescriptor.declaredLength
    return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
}

fun processAudio(): Array<FloatArray> {
    var tempWavFile: String = "final_record3.wav"//"br1.wav" //
    var mel_spectrogram: Array<FloatArray> = Array(1){FloatArray(1){0F}};
    try {
        var audio = AudioProcessor.loadAndRead(getPath(tempWavFile), 16000, 5);
        mel_spectrogram = AudioProcessor.generateMelSpectroGram(audio, 16000, 2048, 128, 100)
    }catch (e: Exception){
        Log.e("Error",e.toString());
    }
    return mel_spectrogram
}

fun sumBigArray(array: Array<FloatArray>): Float {
    val minHeap = PriorityQueue<Float>()
    for (i in array.indices) {
        for (j in array[i].indices) {
            minHeap.offer(array[i][j])
        }
    }
    var sum = 0f
    while (minHeap.size > 1) {
        val smallest = minHeap.poll()
        val nextSmallest = minHeap.poll()
        sum += smallest + nextSmallest
        minHeap.offer(smallest + nextSmallest)
    }
    return minHeap.poll()
}


fun normalizeFloatArray(dbScaleMelSpectrogram: Array<FloatArray>){
    var mean = 0f//sumBigArray(dbScaleMelSpectrogram)
    var c = 0f
    for (i in dbScaleMelSpectrogram.indices) {
        for (j in dbScaleMelSpectrogram[i].indices) {
            var y = dbScaleMelSpectrogram[i][j] - c
            var t = mean+y
            c = (t-mean)-y
            mean = t
        }
    }
    mean /= (dbScaleMelSpectrogram.size * dbScaleMelSpectrogram[0].size).toFloat()
    var maxAbs = Float.NEGATIVE_INFINITY
    for (i in dbScaleMelSpectrogram.indices) {
        for (j in dbScaleMelSpectrogram[i].indices) {
            dbScaleMelSpectrogram[i][j] -= mean
            maxAbs = Math.max(maxAbs, Math.abs(dbScaleMelSpectrogram[i][j]))
        }
    }
    for (i in dbScaleMelSpectrogram.indices) {
        for (j in dbScaleMelSpectrogram[i].indices) {
            dbScaleMelSpectrogram[i][j] /= maxAbs
        }
    }
}

fun arrayReshape(array:Array<FloatArray>){
    var returnArray = Array(1){ Array(128) {Array(801){Array(1){0f}}}}
    var index = 0
    for(i in returnArray){
        for(j in i){
            for(k in j){
                for(l in k){
                    index++
                }
            }
        }
    }
}



@Composable
@Preview
fun HomeScreenPreview() {

}



