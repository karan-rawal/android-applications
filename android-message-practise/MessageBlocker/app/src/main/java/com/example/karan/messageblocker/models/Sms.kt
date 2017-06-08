package com.example.karan.messageblocker.models

/**
 * Model for the sms.
 * Created by karan on 29/5/17.
 */
data class Sms constructor(var _id: Int, var thread_id: Int, var address: String, var body: String, var type: Int)