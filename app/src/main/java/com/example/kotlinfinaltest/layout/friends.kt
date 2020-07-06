package com.example.kotlinfinaltest.layout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinfinaltest.R
import com.example.kotlinfinaltest.adapter.FriendAdapter
import com.example.kotlinfinaltest.model.UserModel
import com.example.kotlinfinaltest.service.Repository
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response


private lateinit var linearLayoutManager: LinearLayoutManager
private lateinit var adapter: FriendAdapter

class friends : AppCompatActivity(), FriendAdapter.UserHolder.OnAdapterListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        adapter = FriendAdapter(ArrayList(), this)
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
            val response : Response<List<UserModel>> =  service.getUsers()
            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val users : List<UserModel>?  = response.body()
                        if( users != null) updateInfo(users)
                    }else{
                        Toast.makeText(this@friends, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@friends, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<UserModel>) {
        progressBar.invalidate()
        progressBar.visibility = View.GONE

        adapter.updateList(list)
    }

    override fun onItemClickListener(item: UserModel) {
        Toast.makeText(this, "Para comunicarte con ${item.name} puedes comunicarte al ${item.phone}", Toast.LENGTH_LONG).show()
        //val intent = Intent(Intent.ACTION_CALL);
        //intent.data = Uri.parse("tel:" +  item.phone)
        //startActivity(intent)
    }

}