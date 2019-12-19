package com.felipevelasquez.testnapoleon.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.adapters.MessageAdapter
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.tests.PostTest
import com.felipevelasquez.testnapoleon.tools.CastToJSON
import com.felipevelasquez.testnapoleon.tools.POST
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MessageAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var castToJSON: CastToJSON
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var postsList: ArrayList<Post>
    private lateinit var postTest: PostTest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(POST, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        postTest = PostTest()
        castToJSON = CastToJSON()
        requests()

        postsList = ArrayList()

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
                val i = viewHolder.adapterPosition
                postsList.removeAt(i)
                viewAdapter.notifyDataSetChanged()
                val post = postsList.get(i)
                editor.remove("${post.id}")
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.mn_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.i_delete_all -> {
                postsList.clear()
                viewAdapter.notifyDataSetChanged()
                true
            }
            R.id.i_update -> {
                postsList.clear()
                requests()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(post: Post) {
        val intent = Intent(this, MessageDetailActivity::class.java)
        intent.putExtra(POST, post._string_json())
        startActivity(intent)
    }

    fun requests() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users/1/posts"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                postsList.addAll(castToJSON.toListPost(response))
                viewAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    "Error: $error",
                    Toast.LENGTH_LONG
                ).show()
            })

        queue.add(stringRequest)
    }
}
