package com.felipevelasquez.testnapoleon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.felipevelasquez.testnapoleon.R
import com.felipevelasquez.testnapoleon.objects.Message
import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.objects.User
import com.felipevelasquez.testnapoleon.tests.UserTest
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_detail)
        post = Gson().fromJson(intent.getStringExtra(POST), Post::class.java)
        userTest = UserTest()
        user = Gson().fromJson(userTest.json, User::class.java)
        initialElements()
    }

    fun initialElements() {
        title = findViewById(R.id.tvTitle)
        title.text = post.title

        body = findViewById(R.id.tvBody)
        body.text = post.body

        email = findViewById(R.id.tvEmail)
        email.text = user.email

        name = findViewById(R.id.tvName)
        name.text = user.name

        phone = findViewById(R.id.tvPhone)
        phone.text = user.phone
    }
}
