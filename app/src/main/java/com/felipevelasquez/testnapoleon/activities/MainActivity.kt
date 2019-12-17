package com.felipevelasquez.testnapoleon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.adapters.MessageAdapter
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.tests.PostTest
import com.felipevelasquez.testnapoleon.tools.POST
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), MessageAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postTest = PostTest()

        val posts = Gson().fromJson(postTest.json, Array<Post>::class.java)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(posts, this)
        recyclerView = findViewById<RecyclerView>(R.id.rvList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onItemClick(post: Post) {
        val intent = Intent(this, MessageDetailActivity::class.java)
        intent.putExtra(POST, post._string_json())
        startActivity(intent)
//        Log.d("MainActivityLog", message.name)
    }
}
