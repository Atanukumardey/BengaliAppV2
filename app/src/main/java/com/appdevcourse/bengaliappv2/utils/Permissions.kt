package com.appdevcourse.bengaliappv2.utils

import android.os.Build
import android.os.Environment

fun checkPermission(): Boolean {
    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
        return Environment.isExternalStorageManager();
    }
    else{
        return true;
    }
}