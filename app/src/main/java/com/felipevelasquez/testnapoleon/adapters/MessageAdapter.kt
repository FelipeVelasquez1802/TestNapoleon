package com.felipevelasquez.testnapoleon.adapters

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.tools.POST
import kotlinx.android.synthetic.main.adapter_message.view.*

class MessageAdapter(
    private val posts: List<Post>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_message, parent, false)

        sharedPreferences = parent.context.getSharedPreferences(POST, Context.MODE_PRIVATE)
        return MessageViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val post = posts.get(position)
        holder.title.text = post.title
        Log.d("MessageAdapterLog", post.title)
        holder.body.text = post.body
        if (position < 5) {
            if (sharedPreferences.getString("${post.id}", null) == null)
                holder.tag.visibility = View.VISIBLE
        }
        holder.init(post, listener)
    }

    class MessageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun init(post: Post, listener: OnItemClickListener) {
            itemView.setOnClickListener { v: View? ->
                listener.onItemClick(post)
            }
        }

        val title: TextView = itemView.tvTitle
        val body: TextView = itemView.tvBody
        val tag: TextView = itemView.tvFlag

    }
}