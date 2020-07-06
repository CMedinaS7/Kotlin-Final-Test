package com.example.kotlinfinaltest.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.Util.mSharedPreferences
import com.example.kotlinfinaltest.adapter.CommentAdapter
import com.example.kotlinfinaltest.adapter.FriendAdapter
import com.example.kotlinfinaltest.adapter.PostAdapter
import com.example.kotlinfinaltest.model.CommentModel
import com.example.kotlinfinaltest.model.PostModel
import com.example.kotlinfinaltest.service.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.activity_posts.postRecyclerView
import kotlinx.android.synthetic.main.activity_posts.progressBar
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

private lateinit var linearLayoutManager: LinearLayoutManager
private lateinit var adapter: CommentAdapter

class comments : AppCompatActivity(), CommentAdapter.CommentHolder.OnAdapterListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        adapter = CommentAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        postRecyclerView.layoutManager= linearLayoutManager
        postRecyclerView.adapter = adapter

        progressBar.isIndeterminate = true
        progressBar.animate()


        val sf = mSharedPreferences(this)
        val postString = sf.getKey("session")

        val post : PostModel = Gson().fromJson(postString, PostModel::class.java)
        var comments : List<CommentModel> = post.comment

        btn_comment.setOnClickListener { view ->

                val comment: CommentModel = CommentModel(
                    100,
                    "cmedina",
                    "https://i.stack.imgur.com/aQWCC.jpg?s=328&g=1",
                    "My new comment... ♥ !!!"
                )
            comments+=comment
            setData(comments)
        }
    }

    private fun setData(comments: List<CommentModel>) {
        //progressBar.invalidate()
        //progressBar.visibility = View.GONE
        adapter.updateList(comments)
    }

    override fun onItemClickListener(item: CommentModel) {

    }
}