package com.felipevelasquez.testnapoleon.objects

import com.google.gson.Gson

open class CorePost {

    val id: Int = 0
    val body: String = ""

    fun _string_json(): String? {
        return Gson().toJson(this)
    }
}