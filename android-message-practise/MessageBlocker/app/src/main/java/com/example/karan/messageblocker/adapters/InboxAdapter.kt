package com.example.karan.messageblocker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.karan.messageblocker.R
import com.example.karan.messageblocker.models.Conversation

/**
 * Adapter for the recycler view.
 * Created by karan on 8/6/17.
 */
class InboxAdapter constructor(var conversations: List<Conversation>) : RecyclerView.Adapter<InboxAdapter.InboxViewHolder>() {

    /**
     * The view holder for the recycler view.
     */
    class InboxViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvContact: TextView? = null
        var cbIsSelected: CheckBox? = null

        init {
            tvContact = view.findViewById(R.id.tvContact) as TextView
            cbIsSelected = view.findViewById(R.id.cbIsSelected) as CheckBox
        }

    }

    /**
     * Fill the views in the view holder.
     */
    override fun onBindViewHolder(holder: InboxViewHolder?, position: Int) {
        val sms = conversations[position]
        holder!!.tvContact!!.text = sms.address
    }

    /**
     * Create the view for each row from the xml.
     */
    override fun onCreateViewHolder(parent: ViewGroup?, p1: Int): InboxViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_sms_list_row, parent, false)
        return InboxViewHolder(view)
    }

    /**
     * Number of rows in the recycler view.
     */
    override fun getItemCount(): Int {
        return conversations.size
    }

}