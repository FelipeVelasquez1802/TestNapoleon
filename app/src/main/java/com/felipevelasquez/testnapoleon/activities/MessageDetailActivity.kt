package com.felipevelasquez.testnapoleon.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.objects.User
import com.felipevelasquez.testnapoleon.tests.UserTest
import com.felipevelasquez.testnapoleon.tools.CastToJSON
import com.felipevelasquez.testnapoleon.tools.POST
import com.google.gson.Gson

class MessageDetailActivity : AppCompatActivity() {

    private lateinit var post: Post
    private lateinit var user: User
    private lateinit var userTest: UserTest

    private lateinit var email: TextView
    private lateinit var title: TextView
    private lateinit var body: TextView
    private lateinit var name: TextView
    private lateinit var phone: TextView
    private lateinit var favorite: ImageButton

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var castToJSON: CastToJSON

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)
        castToJSON = CastToJSON()
        post = Gson().fromJson(intent.getStringExtra(POST), Post::class.java)
        userTest = UserTest()
        user = User()
        requests(post.userId)
        sharedPreferences = getSharedPreferences(POST, Context.MODE_PRIVATE)
        initialElements()
        saveItem()
    }

    fun saveItem() {
        var editor = sharedPreferences.edit()
        editor.putString("${post.id}", post.title)
        editor.apply()
    }

    fun initialElements() {
        title = findViewById(R.id.tvTitle)
        title.text = post.title

        body = findViewById(R.id.tvBody)
        body.text = post.body

        email = findViewById(R.id.tvEmail)

        name = findViewById(R.id.tvName)

        phone = findViewById(R.id.tvPhone)

        dumpData()

        favorite = findViewById(R.id.ibFavorite)
        var flag: Boolean = sharedPreferences.getBoolean("flag_${post}", false)
        favorite.setBackgroundResource(if (flag) R.color.colorAccent else android.R.color.darker_gray)
    }

    fun dumpData() {
        email.text = user.email
        name.text = user.name
        phone.text = user.phone
    }

    fun requests(uid: Int) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/users/${uid}"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                user = CastToJSON().toUser(response)
                dumpData()
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
