package com.felipevelasquez.testnapoleon.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.adapters.MessageAdapter
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.tests.PostTest
import com.felipevelasquez.testnapoleon.tools.POST
import com.google.gson.Gson
import java.util.*

class MainActivity : AppCompatActivity(), MessageAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postTest = PostTest()

        var postsList: List<Post> =
            Arrays.asList(*Gson().fromJson(postTest.json, Array<Post>::class.java))

        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(postsList, this)
        recyclerView = findViewById<RecyclerView>(R.id.rvList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                postsList.
                viewAdapter.notifyDataSetChanged()
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(post: Post) {
        val intent = Intent(this, MessageDetailActivity::class.java)
        intent.putExtra(POST, post._string_json())
        startActivity(intent)
//        Log.d("MainActivityLog", message.name)
    }
}
