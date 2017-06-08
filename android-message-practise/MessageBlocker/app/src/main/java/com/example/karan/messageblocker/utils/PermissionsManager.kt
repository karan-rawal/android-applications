package com.example.karan.messageblocker.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * To check and request permissions.
 * Created by karan on 8/6/17.
 */
class PermissionsManager constructor() {

    companion object {
        val REQUEST_CODE_READ_SMS = 0

        fun requestForPermission(activity: Activity, permission: String, permissionCode: Int) {
            val permissions = arrayOf(permission)
            ActivityCompat.requestPermissions(activity, permissions, permissionCode)
        }

        fun checkPermission(activity: Activity, permission: String): Boolean {
            val permissionStatus: Int = ContextCompat.checkSelfPermission(activity, permission)
            return permissionStatus == PackageManager.PERMISSION_GRANTED
        }
    }
}