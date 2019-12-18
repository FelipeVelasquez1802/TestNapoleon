package com.felipevelasquez.testnapoleon.objects

class Post : CorePost() {
    var userId: Int = 0
    var title: String = ""
    override fun toString(): String {
        return "Post(userId=$userId)"
    }

}