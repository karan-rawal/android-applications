package com.example.karan.messageblocker

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.karan.messageblocker.utils.MessagesManager
import com.example.karan.messageblocker.utils.PermissionsManager

class InboxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        if (PermissionsManager.checkPermission(this, Manifest.permission.READ_SMS)) {
            MessagesManager.getInstance(this.contentResolver).getConversations()
        } else {
            PermissionsManager.requestForPermission(this, Manifest.permission.READ_SMS, PermissionsManager.REQUEST_CODE_READ_SMS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PermissionsManager.REQUEST_CODE_READ_SMS -> {
                MessagesManager.getInstance(this.contentResolver).getConversations()
            }
        }
    }
}
