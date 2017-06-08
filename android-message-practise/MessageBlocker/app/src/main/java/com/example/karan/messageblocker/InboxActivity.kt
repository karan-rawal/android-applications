package com.example.karan.messageblocker

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.example.karan.messageblocker.adapters.InboxAdapter
import com.example.karan.messageblocker.utils.MessagesManager
import com.example.karan.messageblocker.utils.PermissionsManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager



class InboxActivity : AppCompatActivity() {

    var rvInbox: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        rvInbox = findViewById(R.id.rvInbox) as RecyclerView

        if (PermissionsManager.checkPermission(this, Manifest.permission.READ_SMS)) {
            initializeRV()
        } else {
            PermissionsManager.requestForPermission(this, Manifest.permission.READ_SMS, PermissionsManager.REQUEST_CODE_READ_SMS)
        }
    }

    private fun initializeRV() {
        var conversations = MessagesManager.getInstance(this.contentResolver).getConversations()
        var inboxAdapter = InboxAdapter(conversations)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvInbox?.layoutManager = mLayoutManager
        rvInbox?.itemAnimator = DefaultItemAnimator()
        rvInbox?.adapter = inboxAdapter

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PermissionsManager.REQUEST_CODE_READ_SMS -> {
                initializeRV()
            }
        }
    }
}
