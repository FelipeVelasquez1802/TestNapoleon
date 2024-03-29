package com.felipevelasquez.testnapoleon.tools

import com.felipevelasquez.testnapoleon.objects.Post
import com.felipevelasquez.testnapoleon.objects.User
import com.google.gson.Gson

class CastToJSON {
    fun toListPost(json: String): ArrayList<Post> {
        val list = ArrayList<Post>()
        list.addAll(Gson().fromJson(json, Array<Post>::class.java).toList())
        return list
    }

    fun toUser(json: String): User {
        return Gson().fromJson(json, User::class.java)
    }
}