package com.example.karan.messageblocker.utils

import android.content.ContentResolver
import android.net.Uri
import com.example.karan.messageblocker.models.Conversation
import com.example.karan.messageblocker.models.Sms

/**
 * This class will have all the helpers functions related to sms.
 * Created by karan on 7/6/17.
 */
class MessagesManager(private var contentResolver: ContentResolver) {

    companion object {
        private var instance: MessagesManager? = null

        fun getInstance(contentResolver: ContentResolver): MessagesManager {
            if (null == instance) {
                instance = MessagesManager(contentResolver)
            }
            return instance!!
        }
    }

    /**
     * To get all the conversations.
     */
    fun getConversations(): List<Conversation> {
        var listOfConversations = listOf<Conversation>()
        val messageUri = Uri.parse("content://sms/conversations")
        val c = this.contentResolver.query(messageUri, null, null, null, null)

        if (c.count > 0)
            c.moveToFirst()
        else
            return listOfConversations

        //fill the conversation list
        for (i in 0..c.count - 1) {
            val thread_id = c.getInt(c.getColumnIndex("thread_id"))
            val msg_count = c.getInt(c.getColumnIndex("msg_count"))
            val snippet = c.getString(c.getColumnIndex("snippet"))

            var smsList = getSmsForThread(thread_id)

            if (smsList.size > 0) {
                //take the first item of the sms, for address.
                val conversation = Conversation(msg_count, thread_id, snippet, smsList.get(0).address)
                listOfConversations = listOfConversations.plus(conversation)
            }

            c.moveToNext()
        }

        c.close()
        return listOfConversations
    }

    /**
     * To get the sms list for a specific thread
     */
    fun getSmsForThread(threadId: Int): List<Sms> {

        var listOfSms = listOf<Sms>()
        val messageUri = Uri.parse("content://sms/")
        val c = this.contentResolver.query(messageUri, null, "thread_id = ?", arrayOf(threadId.toString()), null)

        if (c.count > 0)
            c.moveToFirst()
        else
            return listOfSms

        for (i in 0..c.count - 1) {
            val _id = c.getInt(c.getColumnIndex("_id"))
            val address = c.getString(c.getColumnIndex("address"))
            val body = c.getString(c.getColumnIndex("body"))
            val thread_id = c.getInt(c.getColumnIndex("thread_id"))
            val type = c.getInt(c.getColumnIndex("type"))
            val sms = Sms(_id, thread_id, address, body, type)

            listOfSms = listOfSms.plus(sms)

            c.moveToNext()
        }

        c.close()
        return listOfSms
    }
}