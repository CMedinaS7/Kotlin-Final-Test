package com.example.kotlinfinaltest.layout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.Util.mSharedPreferences
import com.example.kotlinfinaltest.adapter.PostAdapter
import com.example.kotlinfinaltest.model.PostModel
import com.example.kotlinfinaltest.service.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

private lateinit var linearLayoutManager: LinearLayoutManager
private lateinit var adapter: PostAdapter

class posts : AppCompatActivity(), PostAdapter.PostHolder.OnAdapterListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        adapter = PostAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        postRecyclerView.layoutManager= linearLayoutManager
        postRecyclerView.adapter = adapter

        progressBar.isIndeterminate = true
        progressBar.animate()
        callService()
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response : Response<List<PostModel>> =  service.getPosts()
            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val posts : List<PostModel>?  = response.body()
                        if( posts != null) updateInfo(posts)
                    }else{
                        Toast.makeText(this@posts, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@posts, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<PostModel>) {
        progressBar.invalidate()
        progressBar.visibility = View.GONE

        adapter.updateList(list)
    }

    override fun onItemClickListener(item: PostModel) {
        val commentsString : String = Gson().toJson(item, PostModel::class.java)
        val sf = mSharedPreferences(this)
        sf.put("session", commentsString)
        sf.save()
        val intent = Intent(this, comments::class.java)
        startActivity(intent)
    }

}