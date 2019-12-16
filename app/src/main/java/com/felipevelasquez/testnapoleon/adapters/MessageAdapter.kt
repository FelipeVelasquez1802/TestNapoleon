package com.felipevelasquez.testnapoleon.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.objects.Message
import kotlinx.android.synthetic.main.adapter_message.view.*

class MessageAdapter(
    private val dataset: Array<Message>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_message, parent, false)
        return MessageViewHolder(view, onItemClickListener)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = dataset.get(position)
        holder?.email?.text = message.email
        holder?.name?.text = message.name
        holder?.body?.text = message.body
        if (position < 5) {
            holder?.tag?.visibility = View.VISIBLE
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View?)
    }

    class MessageViewHolder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val email = itemView.tvEmail
        val name = itemView.tvName
        val body = itemView.tvBody
        val tag = itemView.tvFlag
        val onItemClickListener = onItemClickListener

        override fun onClick(v: View?) {
            onItemClickListener.onClick(v)
        }
    }
}