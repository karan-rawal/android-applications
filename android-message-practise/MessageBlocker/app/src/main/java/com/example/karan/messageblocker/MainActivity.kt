package com.example.karan.messageblocker

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.karan.messageblocker.utils.MessagesManager

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(checkPermissions()){
            MessagesManager.getInstance(this.contentResolver).getConversations()
        }else{
            requestForPermissions()
        }
    }

    private fun requestForPermissions() {
        val permissions = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE)
    }

    private fun checkPermissions(): Boolean {
        val permission:Int = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
        return permission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSIONS_REQUEST_CODE -> {
                MessagesManager.getInstance(this.contentResolver).getConversations()
            }
        }
    }
}
