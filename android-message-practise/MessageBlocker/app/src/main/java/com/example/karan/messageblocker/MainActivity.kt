package com.example.karan.messageblocker

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.karan.messageblocker.models.Sms

class MainActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(checkPermissions()){
            getConversations()
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

    fun getConversations(): List<Sms> {

        var listOfConversations = listOf<Sms>()

        //the message uri
        val messageUri = Uri.parse("content://sms/conversations")

        //the content resolver
        val cr = this.contentResolver

        //query returns a cursor to traverse the result
        val c = cr.query(messageUri, null, null, null, null)

        //check if the count is 0
        if(c.count > 0)
            c.moveToFirst()
        else
            return listOfConversations

        //get sms for each conversation
        for(i in 0..c.count - 1){
            val thread_id = c.getString(c.getColumnIndex("thread_id"))
            println("Getting sms for $thread_id")
            var sms = getSmsForThread(thread_id)
            if(sms != null){
                listOfConversations.plus(sms)
            }
            c.moveToNext()
        }

        return listOfConversations
    }

    fun getSmsForThread(threadId: String): Sms? {
        val messageUri = Uri.parse("content://sms/inbox")

        val cr = this.contentResolver
        val c = cr.query(messageUri, null, "thread_id = ?", arrayOf(threadId), null)

        if(c.count > 0)
            c.moveToFirst()
        else
            return null

        for(i in 0..c.count - 1){
            val _id = c.getString(c.getColumnIndex("_id"))
            val address = c.getString(c.getColumnIndex("address"))
            val body = c.getString(c.getColumnIndex("body"))
            val thread_id = c.getString(c.getColumnIndex("thread_id"))

            val sms = Sms(_id, thread_id, address, body)
            println(sms)
            c.close()
            return sms
        }

        c.close()
        return null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode){
            PERMISSIONS_REQUEST_CODE -> {
                getConversations()
            }
        }
    }
}
