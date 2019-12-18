package com.felipevelasquez.testnapoleon.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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
    private lateinit var view: View

    interface OnItemClickListener {
        fun onItemClick(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_message, parent, false)

        sharedPreferences = parent.context.getSharedPreferences(POST, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return MessageViewHolder(view)
    }

    override fun getItemCount() = posts.size

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val post = posts.get(position)
        holder.title.text = post.title
        Log.d("MessageAdapterLog", post.title)
        holder.body.text = post.body
        if (position < 5) {
            if (sharedPreferences.getString("${post.id}", null) == null)
                holder.tag.visibility = View.VISIBLE
        }
        var flag: Boolean = !sharedPreferences.getBoolean("$post", false)
        if (flag) {
            holder.favorite.setBackgroundResource(R.color.colorAccent)
        } else {
            holder.favorite.setBackgroundResource(android.R.color.darker_gray)
        }
        holder.favorite.setOnClickListener { v ->
            run {
                if (flag) {
                    holder.favorite.setBackgroundResource(R.color.colorAccent)
                } else {
                    holder.favorite.setBackgroundResource(android.R.color.darker_gray)
                }
                editor.putBoolean("$post", flag)
                editor.commit()
            }
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
        val favorite: ImageButton = itemView.ibFavorite
    }
}