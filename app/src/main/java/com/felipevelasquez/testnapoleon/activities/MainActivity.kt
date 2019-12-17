package com.felipevelasquez.testnapoleon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.adapters.MessageAdapter
import com.felipevelasquez.testnapoleon.objects.Message
import com.felipevelasquez.testnapoleon.tests.MessageTest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MessageAdapter.OnItemClickListener {
    override fun onItemClick(v: View?) {
        Log.d("MainActivityLog", "Hello!")
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val messageTest = MessageTest()

        val messages = Gson().fromJson(messageTest.json, Array<Message>::class.java)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MessageAdapter(messages, this)
        recyclerView = findViewById<RecyclerView>(R.id.rvList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
