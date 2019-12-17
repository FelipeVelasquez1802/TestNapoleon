package com.felipevelasquez.testnapoleon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.objects.Message
import kotlinx.android.synthetic.main.adapter_message.view.*

class MessageAdapter(
    private val dataset: Array<Message>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_message, parent, false)
        return MessageViewHolder(view, listener)
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

    class MessageViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(v)
        }

        val listener = listener
        val email = itemView.tvEmail
        val name = itemView.tvName
        val body = itemView.tvBody
        val tag = itemView.tvFlag

    }
}